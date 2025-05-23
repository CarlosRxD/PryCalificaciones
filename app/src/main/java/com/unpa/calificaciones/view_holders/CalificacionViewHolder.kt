package com.unpa.calificaciones.view_holders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.unpa.calificaciones.R
import com.unpa.calificaciones.modelos.Materia

class CalificacionViewHolder(vista: View) : RecyclerView.ViewHolder(vista) {
    private val tvTitulo: TextView = vista.findViewById(R.id.tvTitulo)
    private val tvCalificacion: TextView = vista.findViewById(R.id.tvCalificacion)
    private val tvNoDisponible: TextView = vista.findViewById(R.id.tvNoDisponible)

    fun bind(materia: Materia, pos: Int) {
        tvTitulo.text = materia.materia
        if (materia.tieneValor(pos)) {
            tvCalificacion.text = materia.getValor(pos).take(4)
            tvCalificacion.visibility = View.VISIBLE
            tvNoDisponible.visibility = View.GONE
        } else {
            tvCalificacion.visibility = View.GONE
            tvNoDisponible.visibility = View.VISIBLE
        }
    }
}
