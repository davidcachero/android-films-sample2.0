package com.example.peliculasejemplo.database

import android.content.Context
import kotlinx.coroutines.*

class DataRepository(context: Context) {
    private val peliculaDao: PeliculaDao? = AppDatabase.getInstance(context)?.peliculaDao()
    private val usuarioDao: UsuarioDao? = AppDatabase.getInstance(context)?.usuarioDao()

    fun insert(pelicula: Pelicula):Int {
        if (peliculaDao != null){
            CoroutineScope(Dispatchers.IO).launch {
                peliculaDao!!.insert(pelicula)
            }
            return 0
        }
        return -1
    }

    fun insert(peliculas: List<Pelicula>):Int {
        if (peliculaDao != null){
            CoroutineScope(Dispatchers.IO).launch {
                peliculaDao!!.insert(peliculas)
            }
            return 0
        }
        return -1
    }

    fun insert(usuario: Usuario):Int {
        if (usuarioDao != null){
            CoroutineScope(Dispatchers.IO).launch {
                usuarioDao.insert(usuario)
            }
            return 0
        }
        return -1
    }

    fun isLogin(usuario: String, password:String): Boolean{

        var job : Job

        job = CoroutineScope(Dispatchers.IO).async {
            usuarioDao!!.countUsuarioByUsuarioPassword(usuario, password)!!
        }

        return runBlocking {
            job.await() == 1
        }
    }

    fun countUsuario():Int = runBlocking {
        usuarioDao!!.countUsuario()!!
    }

    fun getTipos():List<String> = runBlocking {
        peliculaDao!!.getTipos()
    }


    fun getPeliculas(tipo: String):List<Pelicula> = runBlocking {
        peliculaDao!!.getPeliculasByTipo(tipo)
    }

    fun getPelicula(id: Int):Pelicula?  = runBlocking {
        peliculaDao!!.getPeliculasById(id)
    }
}