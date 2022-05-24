package waslim.binar.andlima.latihan_noteapp.view.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.asLiveData
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import waslim.binar.andlima.latihan_noteapp.R
import waslim.binar.andlima.latihan_noteapp.datastore.UserManager
import waslim.binar.andlima.latihan_noteapp.local.user.User
import waslim.binar.andlima.latihan_noteapp.local.user.UserDatabase


class LoginFragment : Fragment() {
    private var dataUser: UserDatabase? = null
    lateinit var userManager: UserManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        daftar()
        masuk()

    }

    private fun masuk (){
        btn_login.setOnClickListener {
            dataUser = UserDatabase.getInstance(requireContext())
            userManager = UserManager(requireContext())

            val eml = masukan_username_login.text.toString()
            val pss = masukan_password_login.text.toString()

            val msk = dataUser?.userDao()?.getPengguna(eml, pss)

            when {
                eml == "" || pss == "" -> {
                    Toast.makeText(requireContext(), "Lengkapi Data", Toast.LENGTH_SHORT).show()
                }
                msk.isNullOrEmpty() -> {
                    Toast.makeText(requireContext(), "Emai & Password Tidak Cocok", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    GlobalScope.launch {
                        userManager.checkData(true)
                        userManager.saveData("", eml, pss, "")
                    }
                    Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_homeFragment)
                    Toast.makeText(requireContext(), "login sukses", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    private fun daftar(){
        daftar.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }


}