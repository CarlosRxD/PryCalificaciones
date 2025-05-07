package com.unpa.calificaciones

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.unpa.calificaciones.adapters.CalificacionAdapter
import com.unpa.calificaciones.modelos.Materia
import com.unpa.calificaciones.modelos.Notas
import com.unpa.calificaciones.services.UsuarioService

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
        var ejemploLista: List<Materia> = listOf<Materia>()
        // Toma la lista de Notas directamente de cada Materia
        val alumno      = UsuarioService.alumnoActual

        if (alumno?.materias !=null){
            ejemploLista = alumno.materias!!
        }


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