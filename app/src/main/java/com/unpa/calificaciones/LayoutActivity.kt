package com.unpa.calificaciones

import android.content.Intent
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

open class LayoutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun setContentView(layoutResID: Int) {
        val baseLayout = layoutInflater.inflate(R.layout.activity_layout, null)
        val contentFrame = baseLayout.findViewById<FrameLayout>(R.id.content_frame)
        layoutInflater.inflate(layoutResID, contentFrame, true)
        super.setContentView(baseLayout)
        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav)

        bottomNav.setOnItemSelectedListener { item ->
            Toast.makeText(this,item.toString(), Toast.LENGTH_LONG).show()
            when (item.itemId) {
                R.id.nav_calificaciones -> {
                    if (this !is ContenedorCalificaciones) {
                        startActivity(Intent(this, ContenedorCalificaciones::class.java))
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                        finish()
                    }
                    true
                }
                R.id.nav_perfil -> {
                    if (this !is Perfil) {
                        startActivity(Intent(this, Perfil::class.java))
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                        finish()
                    }
                    true
                }
                R.id.nav_notificaciones -> {
                    if (this !is Notificaciones) {
                        startActivity(Intent(this, Notificaciones::class.java))
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                        finish()
                    }
                    true
                }
                else -> false
            }
        }

        // Marcar el ítem actual
        bottomNav.selectedItemId = when (this) {
            is ContenedorCalificaciones -> R.id.nav_calificaciones
            is Perfil -> R.id.nav_perfil
            is Notificaciones -> R.id.nav_notificaciones
            else -> R.id.nav_calificaciones
        }
    }
}
