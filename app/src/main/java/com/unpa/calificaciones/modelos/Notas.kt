package com.unpa.calificaciones.modelos

import com.google.firebase.firestore.IgnoreExtraProperties

/**
 * Representa las notas de una materia según el esquema en Firestore:
 * - parcial1, parcial2, parcial3
 * - ordinario
 * - extraOrdinario1, extraOrdinario2
 */
@IgnoreExtraProperties
data class Notas(
    val parcial1: String? = null,
    val parcial2: String? = null,
    val parcial3: String? = null,
    val ordinario: String? = null,
    val extraOrdinario1: String? = null,
    val extraOrdinario2: String? = null
) {
    private val listaNotas = listOf(
        parcial1,
        parcial2,
        parcial3,
        ordinario,
        extraOrdinario1,
        extraOrdinario2
    )

    /**
     * Devuelve la nota correspondiente al índice (0..5):
     * 0 = parcial1, 1 = parcial2, 2 = parcial3,
     * 3 = ordinario, 4 = extraOrdinario1, 5 = extraOrdinario2
     */
    fun getNotaPorIndice(indice: Int): String? {
        return listaNotas.getOrNull(indice)
    }

    /**
     * Indica si en el índice dado hay un valor no nulo y no en blanco.
     */
    fun tieneValor(indice: Int): Boolean {
        return getNotaPorIndice(indice)?.isNotBlank() == true
    }

    /**
     * Devuelve el índice (0..5) de la última nota no nula y no en blanco.
     * Si no hay ninguna nota, retorna -1.
     */
    fun getUltimaNotaDisponible(): Int {
        for (i in listaNotas.indices.reversed()) {
            val nota = listaNotas[i]
            if (!nota.isNullOrBlank()) {
                return i
            }
        }
        return -1
    }
}
