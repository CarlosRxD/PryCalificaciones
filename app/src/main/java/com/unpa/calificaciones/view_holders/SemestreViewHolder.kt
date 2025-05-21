package com.unpa.calificaciones.view_holders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.unpa.calificaciones.R

class SemestreViewHolder( private val vista : View):ViewHolder(vista) {
    val txtSemestre: TextView = vista.findViewById(R.id.textView);

    fun bind(semestre:Int,onItemClick:(Int)-> Unit){
        txtSemestre.text=semestre.toString();
        vista.setOnClickListener{
            onItemClick(semestre);
        }

    }
}

