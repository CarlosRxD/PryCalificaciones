package com.unpa.calificaciones.providers

import android.widget.Toast
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
        val usuarioRef = db.collection("usuarios").document(usuarioId)

        usuarioRef.get().addOnSuccessListener { userDoc ->
            // 1) Convertir a Usuario y extraer el DocumentReference
            val usuario = userDoc.toObject(Usuario::class.java)
            val alumnoRef = usuario?.alumnoRef

            if (alumnoRef == null) {
                callback(null)
                return@addOnSuccessListener
            }

            // 2) Traer el documento de Alumno
            alumnoRef.get().addOnSuccessListener { alumnoDoc ->
                val alumno = alumnoDoc.toObject(Alumno::class.java)
                if (alumno == null) {
                    callback(null)
                    return@addOnSuccessListener
                }

                // 3) Traer la subcolección “materias”
                alumnoRef.collection("materias")
                    .get()
                    .addOnSuccessListener { snap ->
                        alumno.materias = snap.mapNotNull { it.toObject(Materia::class.java) }
                        callback(alumno)
                    }
                    .addOnFailureListener {
                        callback(null)
                    }
            }
                .addOnFailureListener {
                    callback(null)
                }
        }
            .addOnFailureListener {
                callback(null)
            }
    }

}
