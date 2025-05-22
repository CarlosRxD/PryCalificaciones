package com.unpa.calificaciones.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.unpa.calificaciones.R
import com.unpa.calificaciones.services.SemestreStringService
import com.unpa.calificaciones.view_holders.SemestreViewHolder

class SemestreAdapter (private val semestre:List<Int>,private val onItemClick:(Int)-> Unit) :RecyclerView.Adapter<SemestreViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SemestreViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_item, parent, false)
        return SemestreViewHolder(view)
    }

    override fun getItemCount(): Int {
        return semestre.size
    }

    override fun onBindViewHolder(holder: SemestreViewHolder, position: Int) {
        val semestre = semestre[position]

        holder.bind(semestre,onItemClick);

        holder.txtSemestre.text = SemestreStringService.semestres[semestre-1]
    }
}