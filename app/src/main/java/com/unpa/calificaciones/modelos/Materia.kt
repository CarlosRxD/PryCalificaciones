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
    var calificaciones: Notas = Notas()
    var cicloEscolarRef: DocumentReference? = null
    var materia: String = ""
    var semestre: Int=-1;

    /** Constructor secundario para crear instancias manualmente */
    constructor(
        activo: Boolean,
        calificacion: Notas,
        cicloEscolarRef: DocumentReference?,
        materia: String,
        semestre: Int
    ) : this() {
        this.activo = activo
        this.calificaciones = calificacion
        this.cicloEscolarRef = cicloEscolarRef
        this.materia = materia
        this.semestre = semestre
    }

    /** Devuelve true si la nota en el índice (0..5) es no nula y no vacía */
    fun tieneValor(indice: Int): Boolean {
        return calificaciones.getNotaPorIndice(indice)?.isNotBlank() == true
    }

    /** Devuelve el valor de la nota en el índice (0..5), o cadena vacía si es nula */
    fun getValor(indice: Int): String {
        return calificaciones.getNotaPorIndice(indice).orEmpty()
    }
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Materia) return false

        return materia == other.materia &&
                semestre == other.semestre &&
                activo == other.activo &&
                calificaciones == other.calificaciones
    }

    override fun hashCode(): Int {
        var result = materia.hashCode()
        result = 31 * result + semestre
        result = 31 * result + activo.hashCode()
        result = 31 * result + calificaciones.hashCode()
        return result
    }

}

/**
 * Representa las notas de una materia según el esquema en Firestore.
 * Incluye un constructor vacío para compatibilidad con Firestore.
 */
