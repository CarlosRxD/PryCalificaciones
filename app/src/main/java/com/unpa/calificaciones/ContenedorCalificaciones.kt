package com.unpa.calificaciones

import Notas
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.unpa.calificaciones.adapters.CalificacionAdapter
import com.unpa.calificaciones.modelos.Materia

class ContenedorCalificaciones : AppCompatActivity() {
    private lateinit var adapter: CalificacionAdapter

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
            Materia(true, ejemploNotas, "M1", "Programación Estructurada", "1er Semestre"),
            Materia(true, ejemploNotas, "M2", "Matemáticas Discretas", "1er Semestre")
        )
        val recyclerView = findViewById<RecyclerView>(R.id.vistaCalificaciones)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = CalificacionAdapter(ejemploLista, 0)
        recyclerView.adapter = adapter
        configurarChips()
    }
    fun configurarChips() {
        val chipGroup = findViewById<ChipGroup>(R.id.chipGroupFilters)

        // Asumimos que el orden en el ChipGroup corresponde al índice esperado (0=Parcial1, 1=Parcial2, etc.)
        for (i in 0 until chipGroup.childCount) {
            val chip = chipGroup.getChildAt(i) as? Chip ?: continue

            chip.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    adapter.setPos(i)
                }
            }
        }
    }

}