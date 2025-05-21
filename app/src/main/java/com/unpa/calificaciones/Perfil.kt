package com.unpa.calificaciones

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.unpa.calificaciones.services.UsuarioService

class Perfil : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val alumno = UsuarioService.alumnoActual

        val nombreTextView = findViewById<TextView>(R.id.txtNombreAlumno)

        val tipoAlumno = findViewById<TextView>(R.id.txtTipoAlumno)

        nombreTextView.text = alumno?.nombre.toString() + " " + alumno?.apPaterno.toString()  + " " + alumno?.apMaterno.toString();

        if(alumno?.esRegular==true){
            tipoAlumno.text = "Regular";
        }else{
            tipoAlumno.text = "Irregular";
        }

    }
}