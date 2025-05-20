package com.unpa.calificaciones.services

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.unpa.calificaciones.modelos.Alumno

object UsuarioService {
    var alumnoActual: Alumno? = null

    // LiveData para observar el semestre seleccionado
    private val _semestreSeleccionado = MutableLiveData<String>()
    val semestreSeleccionado: LiveData<String> = _semestreSeleccionado

    fun seleccionarSemestre(semestre: String) {
        _semestreSeleccionado.value = semestre
    }
}
