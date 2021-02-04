package com.example.peliculasejemplo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.peliculasejemplo.ListFragmentDirections
import com.example.peliculasejemplo.R
import com.example.peliculasejemplo.database.Pelicula
import com.example.peliculasejemplo.model.PeliculasViewModel

class PeliculasAdapter(var peliculas: List<Pelicula>, var peliculaViewModel: PeliculasViewModel)
    : RecyclerView.Adapter<PeliculasAdapter.ViewHolder>(), Filterable {

    var peliculasFilterList = ArrayList<Pelicula>()

    init{
        peliculasFilterList = peliculas as ArrayList<Pelicula>
    }

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeliculasAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.pelicula_item, parent, false)
        val viewHolder = ViewHolder(v)
        return viewHolder
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: PeliculasAdapter.ViewHolder, position: Int) {
        holder.bindItems(peliculasFilterList[position])
        holder.itemView.setOnClickListener {

            peliculaViewModel.setPeliculaSeleccionada(peliculasFilterList[position])

            val bundle = bundleOf(Pair("pelicula", peliculasFilterList[position]))
            val action = ListFragmentDirections.actionListFragmentToFichaFragment(bundle)
            it.findNavController().navigate(action)

        }
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return peliculasFilterList.size
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(peliculas: Pelicula) {
            val textViewNombre = itemView.findViewById<TextView>(R.id.textViewTitulo)
            textViewNombre.text = peliculas.titulo
        }
    }

    override fun getFilter(): Filter {
       return object :Filter(){
           override fun performFiltering(constraint: CharSequence?): FilterResults {
               val charSequence = constraint.toString()
               if (charSequence.isEmpty()){
                   peliculasFilterList = peliculas as ArrayList<Pelicula>
               }
               else{
                   val resultList = ArrayList<Pelicula>()
                   for (row in peliculas){
                       if (row.titulo?.toLowerCase()?.contains(charSequence.toLowerCase())!!){
                           resultList.add(row)
                       }
                   }
                   peliculasFilterList = resultList
               }
               var filterResult = FilterResults()
               filterResult.values = peliculasFilterList
               return filterResult
           }

           override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
               peliculasFilterList = results?.values as ArrayList<Pelicula>
               notifyDataSetChanged()
           }
       }
    }
}