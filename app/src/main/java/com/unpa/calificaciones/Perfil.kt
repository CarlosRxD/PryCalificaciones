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
        enableEdgeToEdge()
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


        fun configurarRutas(){
            val bottomNavView: BottomNavigationView = findViewById(R.id.bottom_nav)
            bottomNavView.setOnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.nav_calificaciones -> {
                        val intent = Intent(this, ContenedorCalificaciones::class.java)
                        startActivity(intent)
                        true
                    }
                    R.id.nav_perfil -> {
                        val intent = Intent(this, this::class.java)
                        startActivity(intent)
                        true
                    }
                    else -> false // Si no es ninguno de los ítems definidos
                }
            }
        }


    }
}