package com.example.peliculasejemplo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.onNavDestinationSelected
import com.example.peliculasejemplo.database.DataRepository
import com.example.peliculasejemplo.utils.JsonReader
import com.google.android.material.appbar.CollapsingToolbarLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val layout = findViewById<CollapsingToolbarLayout>(R.id.collapsing_toolbar_layout)

        val toolbar = findViewById<Toolbar>(R.id.toolbar);
        setSupportActionBar(toolbar)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        var dataRepository = DataRepository(this)
        var tipos = dataRepository.getTipos()

        if (tipos.isEmpty()){
            var jsonReader = JsonReader(resources)
            var listaPeliculas = jsonReader.readPeliculas()
            dataRepository.insert(listaPeliculas)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }
}