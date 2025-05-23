package com.unpa.calificaciones.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.unpa.calificaciones.R
import com.unpa.calificaciones.modelos.Materia
import com.unpa.calificaciones.view_holders.CalificacionViewHolder

class CalificacionAdapter(
    private val calificaciones: List<Materia>,
    private var pos: Int
) : RecyclerView.Adapter<CalificacionViewHolder>() {
    private var listaFiltrada : List<Materia> = calificaciones
    // Crear el ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalificacionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.calificacion_simple, parent, false)
        return CalificacionViewHolder(view)
    }

    // Devuelve el número de elementos
    override fun getItemCount(): Int {
        return listaFiltrada.size
    }

    // Enlazar el dato al ViewHolder
    override fun onBindViewHolder(holder: CalificacionViewHolder, position: Int) {
        val calificacion = listaFiltrada[position]
        // Configurar título y fecha
        holder.tvTitulo.text = calificacion.materia

        if (calificacion.tieneValor(pos)) {
            holder.tvCalificacion.text = calificacion.getValor(pos).take(4);
            holder.tvCalificacion.visibility = View.VISIBLE
            holder.tvNoDisponible.visibility = View.GONE
        } else {
            holder.tvCalificacion.visibility = View.GONE
            holder.tvNoDisponible.visibility = View.VISIBLE
        }
    }
    fun setPos(newPos: Int) {
        if (pos != newPos) {
            pos = newPos
            notifyItemRangeChanged(0, itemCount)
            filtrarCalificaciones()
        }
    }
    fun filtrarCalificaciones(){
        listaFiltrada = calificaciones.filter { cal -> cal.tieneValor(pos) }
    }

}
