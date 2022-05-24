package waslim.binar.andlima.latihan_noteapp.view.fragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.custom_layout.view.*
import kotlinx.android.synthetic.main.custom_layout_edit.*
import kotlinx.android.synthetic.main.custom_layout_edit.view.*
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import waslim.binar.andlima.latihan_noteapp.R
import waslim.binar.andlima.latihan_noteapp.local.note.Note
import waslim.binar.andlima.latihan_noteapp.local.note.NoteDatabase
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@SuppressLint("SetTextI18n", "NewApi")
class DetailFragment : Fragment() {
    private var dB : NoteDatabase? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dB = NoteDatabase.getInstance(requireContext())
        getDetail()
        share()
        delete()
        update()

    }


    private fun getDetail() {
        val detail = arguments?.getParcelable<Note>("datadetail")
        judul_detail.text = "Judul: ${detail?.judul}"
        catatan_detail.text = "Catatan: ${detail?.catatan}"
        waktu_detail.text = "Waktu: ${detail?.waktu}"
    }

    private fun share(){
        val detail = arguments?.getParcelable<Note>("datadetail")
        btn_share.setOnClickListener {
            val intent= Intent()
            intent.action=Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT,detail!!.catatan)
            intent.type="text/plain"
            startActivity(Intent.createChooser(intent,"Share To:"))
        }
    }

    private fun delete(){
        val detail = arguments?.getParcelable<Note>("datadetail")
        btn_hapus.setOnClickListener {
            GlobalScope.async {
                val res = dB?.noteDao()?.deleteNoteTaking(detail!!)
                Log.d("resApp", res.toString())
                requireActivity().runOnUiThread {
                    if (res == 1) {
                        Toast.makeText(requireContext(), "Berhasil Dihapus", Toast.LENGTH_SHORT).show()
                        Navigation.findNavController(requireView()).navigate(R.id.action_detailFragment_to_homeFragment)
                    } else {
                        Toast.makeText(requireContext(), "Gagal Menghapus", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }



    private fun update(){
        val detail = arguments?.getParcelable<Note>("datadetail")
        btn_update.setOnClickListener {
            val alertA = LayoutInflater.from(requireContext()).inflate(R.layout.custom_layout_edit, null, false)
            val alertB = AlertDialog.Builder(requireContext())
                .setView(alertA)
                .create()

            alertA.btn_edit.setOnClickListener {

                GlobalScope.async {

                    val jdl = alertA.masukan_judul_edit.text.toString()
                    val ctt = alertA.masukan_catatan_edit.text.toString()
                    val current = LocalDateTime.now()
                    val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                    val formatted = current.format(formatter)

                    val result = dB?.noteDao()?.updateNoteTaking(Note(detail?.id, jdl, formatted, ctt))

                    requireActivity().runOnUiThread {
                        if (result != 0){
                            Toast.makeText(requireContext(), "Berhasil update", Toast.LENGTH_LONG).show()
                            Navigation.findNavController(requireView()).navigate(R.id.action_detailFragment_to_homeFragment)
                            alertB.dismiss()
                        } else{
                            Toast.makeText(requireContext(), "Gagal Menambahkan", Toast.LENGTH_LONG).show()
                            alertB.dismiss()
                        }

                    }
                }
            }
            alertB.show()
        }
    }

}