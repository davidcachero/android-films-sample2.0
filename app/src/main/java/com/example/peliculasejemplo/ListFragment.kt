package com.example.peliculasejemplo

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.Spinner
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.peliculasejemplo.adapters.PeliculasAdapter
import com.example.peliculasejemplo.database.DataRepository
import com.example.peliculasejemplo.database.Pelicula
import com.example.peliculasejemplo.model.PeliculasViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListFragment : Fragment() {
    var peliculaSeleccionada: Pelicula? = null
    lateinit var recyclerViewLista: RecyclerView

    lateinit var adapter: PeliculasAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        val v =  inflater.inflate(R.layout.fragment_list, container, false)
        recyclerViewLista = v.findViewById<RecyclerView>(R.id.recyclerviewlista)
        var spinnerTipo = v.findViewById<Spinner>(R.id.spinnerTipo)
        var dataRepository = DataRepository(requireContext())
        var tipos = dataRepository.getTipos()
        var tipoAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, tipos)
        spinnerTipo.adapter = tipoAdapter

        spinnerTipo.onItemSelectedListener= object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?,
                                        view: View?, position: Int, id: Long) {
                rellenarListaPeliculas(tipos[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // write code to perform some action
            }
        }

        var fab = v.findViewById<FloatingActionButton>(R.id.floatingActionButton)

        fab.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToInsertFragment()
            findNavController().navigate(action)
        }


        return v
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu)
        var menuItemSearch = menu.findItem(R.id.app_bar_search)
        menuItemSearch.setVisible(true)

        var searchView = menuItemSearch.actionView as SearchView
        searchView.queryHint = "Buscar pelicula"

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return true
            }

        })

        super.onCreateOptionsMenu(menu, inflater)
    }


    fun rellenarListaPeliculas(tipo:String){
        var dataRepository = DataRepository(requireContext())
        var peliculas = dataRepository.getPeliculas(tipo)

        var viewModel = ViewModelProvider(requireActivity()).get(PeliculasViewModel::class.java)

        adapter = PeliculasAdapter(peliculas, viewModel)
        recyclerViewLista.setAdapter(adapter)
        recyclerViewLista.setLayoutManager(LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false))
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ListFragment().apply {  }
    }
}