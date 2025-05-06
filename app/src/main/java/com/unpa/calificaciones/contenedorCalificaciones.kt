package com.unpa.calificaciones

import android.os.Bundle
import android.util.Log
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
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.firebase.firestore.FirebaseFirestore
import com.unpa.calificaciones.adapters.CalificacionAdapter
import com.unpa.calificaciones.modelos.Materia
import com.unpa.calificaciones.services.UsuarioService

class ContenedorCalificaciones : AppCompatActivity() {

    private lateinit var simpleListView: Spinner
    private lateinit var adapter: CalificacionAdapter

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
    fun getSemestreActual(){
        val db = FirebaseFirestore.getInstance()

        db.collection("ciclosEscolares")
            .whereEqualTo("actual", true)
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    val ciclo = documents.documents[0]
                    val nombre = ciclo.getString("nombre")
                    val fechaInicio = ciclo.getTimestamp("fechaInicio")
                    val fechaFin = ciclo.getTimestamp("fechaFin")

                    Log.d("CicloEscolar", "Ciclo actual: $nombre")
                    Log.d("CicloEscolar", "Inicia: $fechaInicio, Termina: $fechaFin")

                    // Aquí puedes guardar esta información o usarla como necesites
                } else {
                    Log.d("CicloEscolar", "No se encontró ningún ciclo activo.")
                }
            }
            .addOnFailureListener { e ->
                Log.e("CicloEscolar", "Error al obtener el ciclo activo", e)
            }

    }

}
