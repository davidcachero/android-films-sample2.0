package com.example.peliculasejemplo

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.peliculasejemplo.database.DataRepository

class LoginFragment : Fragment() {

    lateinit var editTextUsuario: EditText
    lateinit var editTextPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_login, container, false)

        editTextUsuario = v.findViewById<EditText>(R.id.editTextUsuario)
        editTextPassword = v.findViewById<EditText>(R.id.editTextPassword)
        val buttonLoginOk = v.findViewById<Button>(R.id.buttonLoginOk)

        buttonLoginOk.setOnClickListener{
            procesarLogin()
        }

        return v
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    private fun procesarLogin(){
        val dataRepository = DataRepository(requireContext())
        if (dataRepository.countUsuario()==0) {
            val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            findNavController().navigate(action)
        }
        else{
            if (dataRepository.isLogin(editTextUsuario.text.toString(), editTextPassword.text.toString())){
                val action = LoginFragmentDirections.actionLoginFragmentToListFragment()
                findNavController().navigate(action)
            }
            else{
                Toast.makeText(requireContext(), "Datos incorrectos", Toast.LENGTH_LONG).show()
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {}
    }
}