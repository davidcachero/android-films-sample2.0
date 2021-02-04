package com.example.peliculasejemplo.model

import androidx.lifecycle.ViewModel
import com.example.peliculasejemplo.database.Pelicula

class PeliculasViewModel: ViewModel() {
    private var peliculaSelecionada: Pelicula

    init{
        peliculaSelecionada = Pelicula(0, "", "")
    }

    fun getPeliculaSeleccionada():Pelicula{
        return peliculaSelecionada
    }

    fun setPeliculaSeleccionada(pelicula: Pelicula){
        peliculaSelecionada = pelicula
    }

}