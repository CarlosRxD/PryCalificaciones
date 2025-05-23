package com.unpa.calificaciones.view_holders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.unpa.calificaciones.R

class SemestreViewHolder( private val vista : View):ViewHolder(vista) {
    val txtSemestre: TextView = vista.findViewById(R.id.txtSemestreLabel);
    val txtPromedio: TextView = vista.findViewById(R.id.txtPromedioSemestre);


    fun bind(semestre: Int, promedio: String?, onItemClick: (Int) -> Unit){
        txtSemestre.text=semestre.toString()
        txtPromedio.text=promedio.toString()
        vista.setOnClickListener{
            onItemClick(semestre);
        }

    }
}

