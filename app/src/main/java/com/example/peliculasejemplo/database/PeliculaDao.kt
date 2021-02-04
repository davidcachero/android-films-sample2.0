package com.example.peliculasejemplo.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PeliculaDao {

    @Query("SELECT distinct(tipo) FROM pelicula")
    suspend fun getTipos(): List<String>

    @Query("SELECT * FROM pelicula where tipo = :tipo")
    suspend fun getPeliculasByTipo(tipo:String): List<Pelicula>

    @Query("SELECT * FROM pelicula where codigo = :id")
    suspend fun getPeliculasById(id:Int): Pelicula

    @Insert
    suspend fun insert(pelicula: Pelicula)

    @Insert
    suspend fun insert(peliculas: List<Pelicula>)
}