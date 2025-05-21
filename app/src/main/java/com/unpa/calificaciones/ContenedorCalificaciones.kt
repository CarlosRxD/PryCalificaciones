/*package com.unpa.calificaciones

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
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
import com.airbnb.lottie.parser.IntegerParser
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.unpa.calificaciones.adapters.CalificacionAdapter
import com.unpa.calificaciones.adapters.SemestreAdapter
import com.unpa.calificaciones.modelos.Materia
import com.unpa.calificaciones.services.UsuarioService
import java.util.LinkedHashMap

class ContenedorCalificaciones : AppCompatActivity() {

    private lateinit var adapter: CalificacionAdapter
    private lateinit var ejemploLista : Map<String, List<Materia>>;
    private lateinit var overlay : View ;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contenedor_calificaciones)

        UsuarioService.semestreSeleccionado.observe(this) { semestre ->
            if (semestre != null) {
                actualizarVistaConSemestre(semestre)
                ocultarFragmentoSiVisible()
            }
        }
        ejemploLista = LinkedHashMap();

        val alumno = UsuarioService.alumnoActual
        if (alumno?.materias != null) {
            ejemploLista = getPeriodos(alumno.materias!!)
        }

        val recyclerView = findViewById<RecyclerView>(R.id.vistaCalificaciones)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val ordenSemestres = listOf("Primer Semestre", "Segundo Semestre", "Tercer Semestre", "Cuarto Semestre", "Quinto Semestre", "Sexto Semestre", "Séptimo Semestre", "Octavo Semestre", "Noveno Semestre", "Décimo Semestre")

        val semestreSeleccionado = ejemploLista.keys.maxByOrNull { ordenSemestres.indexOf(it) }

        adapter = CalificacionAdapter(ejemploLista[semestreSeleccionado]!!, 0)
        recyclerView.adapter = adapter

        val promedioGeneral = calcularPromedioGeneral(ejemploLista[semestreSeleccionado]!!)
        val lblGeneral = findViewById<TextView>(R.id.lblGeneral)

        if (promedioGeneral != null) {
            lblGeneral.text = String.format("PromG: %.1f", promedioGeneral)
        } else {
            lblGeneral.text = "PromG: N/A"
        }


        overlay = findViewById<View>(R.id.blurOverlaySpinner);
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
        overlay.visibility = View.VISIBLE
        val fm = supportFragmentManager
        val fragmentTag = "ItemFragmentTag"
        val existingFragment = fm.findFragmentByTag(fragmentTag)

        val transaction = fm.beginTransaction()

        if (existingFragment != null && existingFragment.isVisible) {
            // El fragmento ya est├í visible: lo eliminamos
            overlay.visibility = View.GONE
            transaction.remove(existingFragment)
            fm.popBackStack() // Opcional, si se usa addToBackStack
            transaction.commit()

            findViewById<FrameLayout>(R.id.contenedorSpinner).visibility = View.INVISIBLE
        } else {
            val fragmento = ItemFragment.newInstance(1,ejemploLista.keys.toList().reversed())
            transaction.replace(R.id.contenedorSpinner, fragmento, fragmentTag)
            transaction.addToBackStack(null) // Opcional, si quieres volver con "atr├ís"
            transaction.commit()

            findViewById<FrameLayout>(R.id.contenedorSpinner).visibility = View.VISIBLE
        }
    }

    fun getPeriodos(lista: List<Materia>): Map<String, List<Materia>> {
        return lista.groupBy { it.semestre }
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

    private fun actualizarVistaConSemestre(semestre: String) {
        val materias = ejemploLista[semestre] ?: return

        adapter = CalificacionAdapter(materias, 0)
        findViewById<RecyclerView>(R.id.vistaCalificaciones).adapter = adapter
    }

    private fun ocultarFragmentoSiVisible() {
        overlay.visibility = View.GONE
        val fragmentManager = supportFragmentManager
        val fragment = fragmentManager.findFragmentByTag("ItemFragmentTag")
        if (fragment != null && fragment.isVisible) {
            fragmentManager.beginTransaction()
                .remove(fragment)
                .commit()

            // También ocultamos el contenedor visual
            findViewById<FrameLayout>(R.id.contenedorSpinner).visibility = View.GONE
        }
    }



}*/