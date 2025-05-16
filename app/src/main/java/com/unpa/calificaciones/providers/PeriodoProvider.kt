package com.unpa.calificaciones.providers

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

class PeriodoProvider {

    fun getPeriodoActual(){
        val db = FirebaseFirestore.getInstance()

        db.collection("ciclosEscolares")
            .whereEqualTo("actual", true)
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    val ciclo = documents.documents[0]
                    val nombre = ciclo.getString("nombre")
                    val fechaInicio = ciclo.getTimestamp("fechaInicio")
                    val fechaFin = ciclo.getTimestamp("fechaFin")

                    Log.d("CicloEscolar", "Ciclo actual: $nombre")
                    Log.d("CicloEscolar", "Inicia: $fechaInicio, Termina: $fechaFin")

                    // Aqu├¡ puedes guardar esta informaci├│n o usarla como necesites
                } else {
                    Log.d("CicloEscolar", "No se encontr├│ ning├║n ciclo activo.")
                }
            }
            .addOnFailureListener { e ->
                Log.e("CicloEscolar", "Error al obtener el ciclo activo", e)
            }

    }
}