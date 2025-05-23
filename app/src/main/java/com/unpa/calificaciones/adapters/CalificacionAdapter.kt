package com.unpa.calificaciones.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.unpa.calificaciones.R
import com.unpa.calificaciones.modelos.Materia
import com.unpa.calificaciones.view_holders.CalificacionViewHolder

class CalificacionAdapter(
    private val calificaciones: List<Materia>,
    private var pos: Int
) : RecyclerView.Adapter<CalificacionViewHolder>() {

    private var listaFiltrada: List<Materia> = calificaciones.filter { it.tieneValor(pos) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalificacionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.calificacion_simple, parent, false)
        return CalificacionViewHolder(view)
    }

    override fun getItemCount(): Int = listaFiltrada.size

    override fun onBindViewHolder(holder: CalificacionViewHolder, position: Int) {
        holder.bind(listaFiltrada[position], pos)
    }

    fun setPos(newPos: Int) {
        if (pos == newPos) return

        pos = newPos
        listaFiltrada = calificaciones.filter { it.tieneValor(pos) }

        // Solo notificar que se actualizó el contenido visual
        notifyDataSetChanged()
    }
}
