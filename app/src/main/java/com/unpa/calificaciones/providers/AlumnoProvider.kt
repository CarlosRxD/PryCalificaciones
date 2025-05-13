package com.unpa.calificaciones.providers

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.unpa.calificaciones.modelos.Alumno
import com.unpa.calificaciones.modelos.Materia
import com.unpa.calificaciones.modelos.Usuario

class AlumnoProvider {

    private val db = FirebaseFirestore.getInstance()
    private val alumnosCollection = db.collection("usuarios")

    fun obtenerAlumnoConMateriasDeUsuario(
        usuarioId: String,
        callback: (Alumno?) -> Unit
    ) {
        val usuarioRef = alumnosCollection.document(usuarioId)

        usuarioRef.get().addOnSuccessListener { userDoc ->
            val usuario = userDoc.toObject(Usuario::class.java)
            val alumnoRef = usuario?.alumnoRef

            if (alumnoRef == null) {
                callback(null)
                return@addOnSuccessListener
            }

            alumnoRef.get().addOnSuccessListener { alumnoDoc ->
                val alumno = alumnoDoc.toObject(Alumno::class.java)
                if (alumno == null) {
                    callback(null)
                    return@addOnSuccessListener
                }

                alumnoRef.collection("materias")
                    .get()
                    .addOnSuccessListener { snap ->
                        // Log 1: datos crudos desde Firestore
                        for (doc in snap.documents) {
                            Log.d("FirebaseMateriaRaw", "Raw data: ${doc.data}")
                        }

                        // Mapear a objetos Materia
                        alumno.materias = snap.mapNotNull { it.toObject(Materia::class.java) }

                        // Log 2: mostrar solo notas
                        for ((index, materia) in alumno.materias?.withIndex()!!) {
                            Log.d(
                                "NotasDeserializadas",
                                "Materia $index: ${materia.materia}, Notas: ${materia.calificaciones.listaNotas}"
                            )
                        }

                        callback(alumno)
                    }
                    .addOnFailureListener {
                        Log.e("AlumnoProvider", "Error al obtener materias", it)
                        callback(null)
                    }
            }.addOnFailureListener {
                Log.e("AlumnoProvider", "Error al obtener documento de alumno", it)
                callback(null)
            }
        }.addOnFailureListener {
            Log.e("AlumnoProvider", "Error al obtener usuario", it)
            callback(null)
        }
    }
}
