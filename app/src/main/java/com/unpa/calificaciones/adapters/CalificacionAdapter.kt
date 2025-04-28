package com.unpa.calificaciones.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.unpa.calificaciones.R
import com.unpa.calificaciones.modelos.Calificacion
import com.unpa.calificaciones.view_holders.CalificacionViewHolder

class CalificacionAdapter(private val calificaciones: List<Calificacion>,private val pos :Int ) :RecyclerView.Adapter<CalificacionViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalificacionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.calificacion_simple, parent, false)
        return CalificacionViewHolder(view)    }

    override fun getItemCount(): Int {
        return calificaciones.size
    }

    override fun onBindViewHolder(holder: CalificacionViewHolder, position: Int) {
        val calificacion = calificaciones[position]

        holder.tvTitulo.text = calificacion.materia
        holder.tvFecha.text = "aplicado: ayer, yo creo"

        if (calificacion.tieneValor(pos)) {
            holder.tvCalificacion.text = calificacion.getValor(pos)
            holder.tvCalificacion.visibility = View.VISIBLE
            holder.tvNoDisponible.visibility = View.GONE
        } else {
            holder.tvCalificacion.visibility = View.GONE
            holder.tvNoDisponible.visibility = View.VISIBLE
        }
    }
}