package com.unpa.calificaciones

import Notas
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.unpa.calificaciones.adapters.CalificacionAdapter
import com.unpa.calificaciones.modelos.Calificacion

class ContenedorCalificaciones : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_contenedor_calificaciones)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Aquí van tus datos de ejemplo o reales
        val ejemploNotas = Notas(
            primerParcial = "8",
            segundoParcial = "9",
            tercerParcial = null,
            ordinario = null,
            final = "9.5",
            ex1 = null,
            ex2 = null
        )

        val ejemploLista = listOf(
            Calificacion(true, ejemploNotas, "M1", "Programación Estructurada", "1er Semestre"),
            Calificacion(true, ejemploNotas, "M2", "Matemáticas Discretas", "1er Semestre")
        )

        val recyclerView = findViewById<RecyclerView>(R.id.vistaCalificaciones)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = CalificacionAdapter(ejemploLista, 0) // ← Aquí puedes cambiar el índice de la nota
    }
}