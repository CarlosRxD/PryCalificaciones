package com.unpa.calificaciones.providers

import com.google.firebase.firestore.FirebaseFirestore
import com.unpa.calificaciones.modelos.Alumno
import com.unpa.calificaciones.modelos.Materia

class AlumnoProvider {

    private val db = FirebaseFirestore.getInstance()
    private val alumnosCollection = db.collection("alumnos")

    fun obtenerAlumnoConMaterias(
        alumnoId: String,
        callback: (Alumno?) -> Unit
    ) {
        val alumnoRef = alumnosCollection.document(alumnoId)

        alumnoRef.get().addOnSuccessListener { doc ->
            val alumno = doc.toObject(Alumno::class.java)
            if (alumno != null) {
                alumnoRef.collection("materias").get().addOnSuccessListener { snap ->
                    val materias = snap.mapNotNull { it.toObject(Materia::class.java) }
                    alumno.materias = materias
                    callback(alumno)
                }.addOnFailureListener {
                    callback(null)
                }
            } else {
                callback(null)
            }
        }.addOnFailureListener {
            callback(null)
        }
    }
}
