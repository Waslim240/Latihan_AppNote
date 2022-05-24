package waslim.binar.andlima.latihan_noteapp.view.fragment

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.asLiveData
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import waslim.binar.andlima.latihan_noteapp.R
import waslim.binar.andlima.latihan_noteapp.datastore.UserManager
import kotlin.math.log

@SuppressLint("SetTextI18n")
class ProfileFragment : Fragment() {
    lateinit var userManager: UserManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getDataUsername()
        logout()
    }


    private fun getDataUsername(){
        userManager = UserManager(requireContext())

        userManager.username.asLiveData().observe(requireActivity()){
            untuk_username.text = "Username, $it"
        }
        userManager.username.asLiveData().observe(requireActivity()){
            untuk_name.text = "Name, $it"
        }
    }

    private fun logout(){
        userManager = UserManager(requireContext())
        btn_logout.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("KONFIRMASI LOGOUT")
                .setMessage("Anda Yakin Ingin Logout ?")

                .setPositiveButton("YA"){ dialogInterface: DialogInterface, i: Int ->
                    GlobalScope.launch {
                        userManager.logout()
                    }
                    Navigation.findNavController(requireView()).navigate(R.id.action_profileFragment_to_splashFragment)
                }
                .setNegativeButton("TIDAK"){ dialogInterface: DialogInterface, i: Int ->
                    Toast.makeText(requireContext(), "Tidak Jadi Logout", Toast.LENGTH_SHORT).show()
                    dialogInterface.dismiss()
                }

                .setNeutralButton("NANTI"){ dialogInterface: DialogInterface, i: Int ->
                    dialogInterface.dismiss()
                    Toast.makeText(requireContext(), "Jangan Lama Mikirnya", Toast.LENGTH_SHORT).show()
                }
                .show()
        }
    }

}