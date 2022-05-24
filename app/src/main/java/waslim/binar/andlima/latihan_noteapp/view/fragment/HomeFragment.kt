package waslim.binar.andlima.latihan_noteapp.view.fragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.asLiveData
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.custom_layout.view.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import waslim.binar.andlima.latihan_noteapp.R
import waslim.binar.andlima.latihan_noteapp.datastore.UserManager
import waslim.binar.andlima.latihan_noteapp.local.note.Note
import waslim.binar.andlima.latihan_noteapp.local.note.NoteDatabase
import waslim.binar.andlima.latihan_noteapp.view.adapter.AdapterNote
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@SuppressLint("SetTextI18n")
class HomeFragment : Fragment() {
    private var dB : NoteDatabase? = null
    lateinit var userManager: UserManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dB = NoteDatabase.getInstance(requireContext())
        getDataUsername()
        add()
        getDataNote()
        goToProfile()
    }

    private fun getDataUsername(){
        userManager = UserManager(requireContext())

        userManager.username.asLiveData().observe(requireActivity()){
            get.text = "Welcome, $it"
        }
    }


    @SuppressLint("NewApi")
    private fun add(){
        addData.setOnClickListener {
            val alertA = LayoutInflater.from(requireContext()).inflate(R.layout.custom_layout, null, false)
            val alertB = AlertDialog.Builder(requireContext())
                .setView(alertA)
                .create()

            alertA.btn_input.setOnClickListener {

                GlobalScope.async {
                    val jdl = alertA.masukan_judul.text.toString()
                    val ctt = alertA.masukan_catatan.text.toString()
                    val current = LocalDateTime.now()
                    val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                    val formatted = current.format(formatter)

                    val result = dB?.noteDao()?.insertNoteTaking(Note(null, jdl,formatted, ctt))

                    requireActivity().runOnUiThread {
                        if (result != 0.toLong()){
                            Toast.makeText(requireContext(), "Berhasil Menambahkan", Toast.LENGTH_LONG).show()
                            alertB.dismiss()
                        } else{
                            Toast.makeText(requireContext(), "Gagal Menambahkan", Toast.LENGTH_LONG).show()
                        }
                        activity?.recreate()

                    }
                }
            }
            alertB.show()
        }
    }


    fun getDataNote() {
        rvNote.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        GlobalScope.launch {
            val listD = dB?.noteDao()?.getAllNoteTaking()

            activity?.runOnUiThread {
                listD.let {
                    val adp = AdapterNote(it!!){
                        val detail = bundleOf("datadetail" to it)
                        Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_detailFragment, detail)
                    }
                    rvNote.adapter = adp
                }
            }
        }
    }


    private fun goToProfile(){
        goprofile.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_profileFragment)
        }
    }




}