// AlumnoProvider.kt

package com.unpa.calificaciones.providers

import android.util.Log
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.DocumentSnapshot
import com.unpa.calificaciones.modelos.Alumno
import com.unpa.calificaciones.modelos.Materia
import com.unpa.calificaciones.modelos.Usuario

class AlumnoProvider {

    private val db = FirebaseFirestore.getInstance()
    private val usuariosCollection = db.collection("usuarios")

    fun obtenerAlumnoConMateriasDeUsuario(
        usuarioId: String,
        callback: (Alumno?) -> Unit
    ) {
        usuariosCollection.document(usuarioId)
            .get()
            .addOnSuccessListener { userDoc: DocumentSnapshot ->
                val usuario = userDoc.toObject(Usuario::class.java)
                val alumnoRef = usuario?.alumnoRef

                if (alumnoRef == null) {
                    callback(null)
                    return@addOnSuccessListener
                }

                alumnoRef.get()
                    .addOnSuccessListener { alumnoDoc: DocumentSnapshot ->
                        val alumno = alumnoDoc.toObject(Alumno::class.java)
                            ?: run {
                                callback(null)
                                return@addOnSuccessListener
                            }

                        // Obtener el nombre de la carrera leyendo el padre planesEstudio
                        val planEstudioDocRef: DocumentReference? = alumnoDoc.reference.parent.parent?.parent?.parent
                        if (planEstudioDocRef != null) {
                            planEstudioDocRef.get()
                                .addOnSuccessListener { planDoc: DocumentSnapshot ->
                                    alumno.nombreCarrera = planDoc.get("nombre")?.toString()
                                    cargarMaterias(alumnoRef, alumno, callback)
                                }
                                .addOnFailureListener {
                                    Log.e("AlumnoProvider", "No se pudo leer planEstudio", it)
                                    cargarMaterias(alumnoRef, alumno, callback)
                                }
                        } else {
                            // Estructura inesperada, continuar sin carreraNombre
                            cargarMaterias(alumnoRef, alumno, callback)
                        }
                    }
                    .addOnFailureListener {
                        Log.e("AlumnoProvider", "Error al leer alumno", it)
                        callback(null)
                    }
            }
            .addOnFailureListener {
                Log.e("AlumnoProvider", "Error al leer usuario", it)
                callback(null)
            }
    }

    private fun cargarMaterias(
        alumnoRef: DocumentReference,
        alumno: Alumno,
        callback: (Alumno?) -> Unit
    ) {
        alumnoRef.collection("materias")
            .get()
            .addOnSuccessListener { snap ->
                alumno.materias = snap.mapNotNull { it.toObject(Materia::class.java) }
                callback(alumno)
            }
            .addOnFailureListener {
                Log.e("AlumnoProvider", "Error al obtener materias", it)
                callback(alumno)
            }
    }
}
