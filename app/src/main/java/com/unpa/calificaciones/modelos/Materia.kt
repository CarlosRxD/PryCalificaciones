package com.unpa.calificaciones.modelos

import Notas

data class Materia(
    val activo: Boolean = false,
    val calificacion: Notas,
    val id: String = "",
    val materia: String = "",
    val semestre: String = ""
) {
    fun tieneValor(nota : Int):Boolean{
       return calificacion.getNotaPorIndice(nota)!=null
    }
    fun getValor(nota : Int):String{
        return calificacion.getNotaPorIndice(nota).orEmpty()
    }
}
