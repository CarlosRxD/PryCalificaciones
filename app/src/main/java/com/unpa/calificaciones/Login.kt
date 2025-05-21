package com.unpa.calificaciones

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.airbnb.lottie.LottieAnimationView
import com.unpa.calificaciones.providers.AlumnoProvider
import com.unpa.calificaciones.providers.AuthProvider
import com.unpa.calificaciones.services.UsuarioService


import kotlin.math.log

class LoginActivity : AppCompatActivity() {
    private lateinit var authProvider: AuthProvider
    private lateinit var alumnoProvider: AlumnoProvider
    private lateinit var lottie: LottieAnimationView
    private lateinit var etMat: EditText
    private lateinit var etPass: EditText
    private lateinit var btnLogin: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        lottie = findViewById(R.id.lottieLoading)
        authProvider    = AuthProvider()
        alumnoProvider  = AlumnoProvider()

        etMat = findViewById<EditText>(R.id.etMatricula)
        etPass = findViewById<EditText>(R.id.etPassword)
        btnLogin = findViewById<Button>(R.id.btnLogin)

        btnLogin.setOnClickListener {

            val matricula = etMat.text.toString().trim()
            val password  = etPass.text.toString().trim()
            if (matricula.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Llena todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val email = "$matricula@unpaloma.com"
            showLoading(true)
            authProvider.login(email, password)
                .addOnSuccessListener {
                    // Autenticación correcta → obtener datos del alumno
                    fetchAlumno(matricula)
                }
                .addOnFailureListener { ex ->
                    showLoading(false)
                    Toast.makeText(this, "Error al iniciar sesión: ${ex.message}", Toast.LENGTH_LONG).show()
                    Log.e("LoginError", "Error al iniciar sesión: ${ex.message}", ex)
                }
        }
    }
    private fun showLoading(show: Boolean) {
        val overlay = findViewById<View>(R.id.blurOverlay)
        if (show) {
            overlay.visibility = View.VISIBLE
            lottie.visibility = View.VISIBLE
            lottie.playAnimation()
        } else {
            lottie.cancelAnimation()
            lottie.visibility = View.GONE
            overlay.visibility = View.GONE
        }
        btnLogin.isEnabled = !show
        etMat.isEnabled     = !show
        etPass.isEnabled    = !show
    }
    private fun fetchAlumno(matricula: String) {
        AlumnoProvider().obtenerAlumnoConMateriasDeUsuario(matricula) { alumno ->
            showLoading(false)
            if (alumno != null) {
                UsuarioService.alumnoActual = alumno
                // Alumno encontrado → iniciar la actividad principal
                val intent = Intent(this, LayoutActivity::class.java)
                //intent.putExtra("alumno", alumno)
                startActivity(intent)
                finish()
            } else {
                Log.i("No se pudo cargar el alumno", authProvider.getId().toString())
                Toast.makeText(this, "No se pudo cargar el alumno", Toast.LENGTH_SHORT).show()
            }
        }

    }
}
