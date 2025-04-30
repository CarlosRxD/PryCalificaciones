package com.unpa.calificaciones

import Notas
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.FrameLayout
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.unpa.calificaciones.adapters.CalificacionAdapter
import com.unpa.calificaciones.modelos.Calificacion

class ContenedorCalificaciones : AppCompatActivity() {

    private lateinit var simpleListView: Spinner


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
        //llenarChips()
    }
    /*fun llenarChips(){
        val chipGroup = findViewById<ChipGroup>(R.id.chipGroupFilters)
        val labels = listOf("")
        labels.forEachIndexed { index, text ->
            val chip = Chip(this).apply {
                id = View.generateViewId()
                layoutParams = ViewGroup.MarginLayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                ).also {
                    it.setMargins(0, 0, 16, 0)
                }
                this.text = text
                isCheckable = true
                isChecked = (index == 0)
                setOnCheckedChangeListener { _, isChecked ->
                    // filtra tu RecyclerView aquí
                }
            }
            chipGroup.addView(chip)
        }

    }*/

    fun llamarFragmento(view:View){
        val fm: FragmentManager= getSupportFragmentManager();
        val fragmento: ItemFragment= ItemFragment();
        val transaction = fm.beginTransaction()
        fm.findFragmentById(R.id.contenedorSpinner);

        transaction.replace(R.id.contenedorSpinner, fragmento) // Asegúrate de que R.id.list sea un FrameLayout o contenedor válido
        transaction.addToBackStack(null) // Opcional, para permitir volver con el botón "Atrás"
        transaction.commit()

        findViewById<FrameLayout>(R.id.contenedorSpinner).visibility = View.VISIBLE

    }

    /*val spinner: Spinner = findViewById(R.id.optSemestre)
    // Create an ArrayAdapter using the string array and a default spinner layout.
    ArrayAdapter.createFromResource(this,R.array.planets,
    android.R.layout.simple_spinner_item
    ).also { adapter ->
        // Specify the layout to use when the list of choices appears.
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Apply the adapter to the spinner.
        spinner.adapter = adapter
    }*/
}