package waslim.binar.andlima.latihan_noteapp.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import waslim.binar.andlima.latihan_noteapp.R
import waslim.binar.andlima.latihan_noteapp.datastore.UserManager
import waslim.binar.andlima.latihan_noteapp.local.user.User
import waslim.binar.andlima.latihan_noteapp.local.user.UserDatabase

class RegisterFragment : Fragment() {
    private var dataBase : UserDatabase? = null
    lateinit var userManager: UserManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        daftar()
        goToLogin()

    }

    private fun daftar(){
        btn_register.setOnClickListener {
           if (masukan_username_register.text.toString().isEmpty()){
               Toast.makeText(requireContext(), "Username Harus Di Isi", Toast.LENGTH_SHORT).show()
           } else if (masukan_name_register.text.toString().isEmpty()){
               Toast.makeText(requireContext(), "Name Harus Di Isi", Toast.LENGTH_SHORT).show()
           } else if (masukan_password_register.text.toString().isEmpty()){
               Toast.makeText(requireContext(), "Password Harus Di Isi", Toast.LENGTH_SHORT).show()
           } else if (masukan_konfpassword_register.text.toString().isEmpty()){
               Toast.makeText(requireContext(), "Konfirmasi Password Harus Di Isi", Toast.LENGTH_SHORT).show()
           } else if (masukan_password_register.text.toString() != masukan_konfpassword_register.text.toString()){
               Toast.makeText(requireContext(), "Password Dan Konfirmasi Passsword Harus Sama", Toast.LENGTH_SHORT).show()
           } else {
               prosesRgst()
               Navigation.findNavController(requireView()).navigate(R.id.action_registerFragment_to_loginFragment)
           }
        }
    }

    private fun prosesRgst(){
        dataBase = UserDatabase.getInstance(requireContext())
        userManager = UserManager(requireContext())

        GlobalScope.async {
            val username = masukan_username_register.text.toString()
            val name = masukan_name_register.text.toString()
            val pass = masukan_password_register.text.toString()

            val register = dataBase?.userDao()?.insertUser(User(null, username, name, pass))

            activity?.runOnUiThread {
                if (register != 0.toLong()){
                    Toast.makeText(requireContext(), "Pendaftaran telah berhasil", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Pendaftaran Gagal", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    private fun goToLogin(){
        login.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }

}