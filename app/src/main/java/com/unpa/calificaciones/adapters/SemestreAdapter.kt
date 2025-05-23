package com.unpa.calificaciones.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.unpa.calificaciones.R
import com.unpa.calificaciones.services.SemestreStringService
import com.unpa.calificaciones.view_holders.SemestreViewHolder

class SemestreAdapter(
    private val semestresPromedios: Map<Int, String>,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<SemestreViewHolder>() {

    // Guardar lista ordenada de semestres
    private val semestreList = semestresPromedios.keys.sorted().reversed()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SemestreViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_item, parent, false)
        return SemestreViewHolder(view)
    }

    override fun getItemCount(): Int = semestreList.size

    override fun onBindViewHolder(holder: SemestreViewHolder, position: Int) {
        val semestre = semestreList[position]
        val promedio = semestresPromedios[semestre] ?: 0.0

        // Bind labels y valores
        holder.txtSemestre.text = SemestreStringService.semestres[semestre - 1]
        holder.txtPromedio.text = promedio.toString()

        // Click listener
        holder.itemView.setOnClickListener {
            onItemClick(semestre)
        }
    }
}
