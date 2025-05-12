package com.unpa.calificaciones.modelos

import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class Alumno(
    val apMaterno: String = "",
    val apPaterno: String = "",
    val matricula: String = "",
    val nombre: String = "",
    @Transient
    val isRegular: Boolean=true,
    @Transient
    var materias: List<Materia>? = null // Se ignora al guardar en Firestore
)

