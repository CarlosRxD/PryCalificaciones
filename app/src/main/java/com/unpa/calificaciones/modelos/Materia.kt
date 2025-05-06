package com.unpa.calificaciones.modelos

import Notas
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.IgnoreExtraProperties

/**
 * Representa una materia con calificaciones y referencia al ciclo escolar.
 * Se utiliza una clase regular con un constructor vacío para compatibilidad con Firestore.
 */
@IgnoreExtraProperties
class Materia() {
    var activo: Boolean = false
    var calificacion: Notas = Notas()
    var cicloEscolarRef: DocumentReference? = null
    var materia: String = ""
    var semestre: String = ""

    /** Constructor secundario para crear instancias manualmente */
    constructor(
        activo: Boolean,
        calificacion: Notas,
        cicloEscolarRef: DocumentReference?,
        materia: String,
        semestre: String
    ) : this() {
        this.activo = activo
        this.calificacion = calificacion
        this.cicloEscolarRef = cicloEscolarRef
        this.materia = materia
        this.semestre = semestre
    }

    /** Devuelve true si la nota en el índice (0..5) es no nula y no vacía */
    fun tieneValor(indice: Int): Boolean {
        return calificacion.getNotaPorIndice(indice)?.isNotBlank() == true
    }

    /** Devuelve el valor de la nota en el índice (0..5), o cadena vacía si es nula */
    fun getValor(indice: Int): String {
        return calificacion.getNotaPorIndice(indice).orEmpty()
    }
}

/**
 * Representa las notas de una materia según el esquema en Firestore.
 * Incluye un constructor vacío para compatibilidad con Firestore.
 */
