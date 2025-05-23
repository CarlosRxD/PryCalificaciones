package com.unpa.calificaciones.services

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.unpa.calificaciones.modelos.Alumno

object UsuarioService {
    var alumnoActual: Alumno? = null
    var ultimoSemestre : Int = 0
    var matricula : String? = null;
    // LiveData para observar el semestre seleccionado
    private val _semestreSeleccionado = MutableLiveData<Int>()
    val semestreSeleccionado: LiveData<Int> = _semestreSeleccionado

    fun seleccionarSemestre(semestre: Int) {
        _semestreSeleccionado.value = semestre;
    }
}
