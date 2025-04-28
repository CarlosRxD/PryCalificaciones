package com.unpa.calificaciones.view_holders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.unpa.calificaciones.R

class CalificacionViewHolder(vista : View):ViewHolder(vista) {
    val tvTitulo: TextView = vista.findViewById(R.id.tvTitulo)
    val tvCalificacion: TextView = vista.findViewById(R.id.tvCalificacion)
    val tvNoDisponible: TextView = vista.findViewById(R.id.tvNoDisponible)
    val tvFecha: TextView = vista.findViewById(R.id.tvFecha)
}