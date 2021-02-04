package com.example.peliculasejemplo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.peliculasejemplo.database.Pelicula
import com.example.peliculasejemplo.model.PeliculasViewModel

class FichaFragment : Fragment() {
    lateinit var textViewTitulo: TextView
    lateinit var textViewTipo: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_ficha, container, false)
        textViewTitulo = v.findViewById(R.id.textViewFichaTitulo)
        textViewTipo = v.findViewById(R.id.textViewFichaTipo)

        var viewModel = ViewModelProvider(requireActivity()).get(PeliculasViewModel::class.java)
        var pelicula = viewModel.getPeliculaSeleccionada()

        if (pelicula!=null) {
            textViewTitulo.text = pelicula.titulo
            textViewTipo.text = pelicula.tipo
        }

        return v
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FichaFragment().apply {}
    }
}