package com.unpa.calificaciones

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.FrameLayout
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.unpa.calificaciones.adapters.CalificacionAdapter
import com.unpa.calificaciones.adapters.SemestreAdapter
import com.unpa.calificaciones.modelos.Materia
import com.unpa.calificaciones.services.UsuarioService

class ContenedorCalificaciones : AppCompatActivity() {

    private lateinit var simpleListView: Spinner
    private lateinit var adapter: CalificacionAdapter
    private lateinit var semestreAdapter: SemestreAdapter


    var semestre: Array<String> = arrayOf(
        "Primero", "Segundo", "Tercero", "Cuarto",
        "Quinto", "Sexto", "Séptimo", "Octavo",
        "Noveno", "Décimo"
    )


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
        val alumno = UsuarioService.alumnoActual
        if (alumno?.materias != null) {
            ejemploLista = alumno.materias!!
        }
        val recyclerView = findViewById<RecyclerView>(R.id.vistaCalificaciones)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = CalificacionAdapter(ejemploLista, 0)
        recyclerView.adapter = adapter

        val promedioGeneral = calcularPromedioGeneral(ejemploLista)

        val lblGeneral = findViewById<TextView>(R.id.lblGeneral)

        if (promedioGeneral != null) {
            lblGeneral.text = String.format("PromG: %.2f", promedioGeneral)
        } else {
            lblGeneral.text = "PromG: N/A"
        }



        configurarChips()
    }

    fun configurarChips() {
        val chipGroup = findViewById<ChipGroup>(R.id.chipGroupFilters)

        // Asumimos que el orden en el ChipGroup corresponde al ├¡ndice esperado (0=Parcial1, 1=Parcial2, etc.)
        for (i in 0 until chipGroup.childCount) {
            val chip = chipGroup.getChildAt(i) as? Chip ?: continue

            chip.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    adapter.setPos(i)
                    Toast.makeText(this, "Filtro ${i + 1} activado", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun llamarFragmento(view: View) {
        val fm = supportFragmentManager
        val fragmentTag = "ItemFragmentTag"
        val existingFragment = fm.findFragmentByTag(fragmentTag)

        val transaction = fm.beginTransaction()

        if (existingFragment != null && existingFragment.isVisible) {
            // El fragmento ya est├í visible: lo eliminamos
            transaction.remove(existingFragment)
            fm.popBackStack() // Opcional, si se usa addToBackStack
            transaction.commit()

            findViewById<FrameLayout>(R.id.contenedorSpinner).visibility = View.INVISIBLE
        } else {
            val fragmento = ItemFragment()
            transaction.replace(R.id.contenedorSpinner, fragmento, fragmentTag)
            transaction.addToBackStack(null) // Opcional, si quieres volver con "atr├ís"
            transaction.commit()

            findViewById<FrameLayout>(R.id.contenedorSpinner).visibility = View.VISIBLE
        }
    }

    fun getPeriodos(lista: List<Materia>): Map<DocumentReference?, List<Materia>> {
        return lista.groupBy { it.cicloEscolarRef }
    }

    private fun calcularPromedioGeneral(materias: List<Materia>): Double {
        var suma = 0.0
        var contador = 0

        for (materia in materias) {
            val promedioMateria = materia.calificaciones.calcularPromedio()
            if (promedioMateria != null) {
                suma += promedioMateria
                contador++
            }
        }

        return if (contador > 0) suma / contador else 0.0
    }


}