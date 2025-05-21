package com.unpa.calificaciones.fragmentViews

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.unpa.calificaciones.ItemFragment
import com.unpa.calificaciones.R
import com.unpa.calificaciones.adapters.CalificacionAdapter
import com.unpa.calificaciones.modelos.Materia
import com.unpa.calificaciones.services.UsuarioService

class CalificacionesFragment : Fragment(R.layout.activity_contenedor_calificaciones) {
    private lateinit var adapter: CalificacionAdapter
    private lateinit var ejemploLista: Map<Int, List<Materia>>
    private lateinit var overlay: View
    private lateinit var contenedorSpinner: FrameLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var lblGeneral: TextView
    private lateinit var chipGroup: ChipGroup
    private lateinit var txtSpinner: TextView


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Ahora buscamos TODO en el view del fragment
        overlay = view.findViewById(R.id.blurOverlaySpinner)
        contenedorSpinner = view.findViewById(R.id.contenedorSpinner)   // <<< aquí
        recyclerView = view.findViewById(R.id.vistaCalificaciones)
        lblGeneral = view.findViewById(R.id.lblGeneral)
        chipGroup = view.findViewById(R.id.chipGroupFilters)
        txtSpinner = view.findViewById(R.id.txtSemestreSeleccionado)
        // RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Llenar la lista agrupada por semestre
        ejemploLista = UsuarioService.alumnoActual?.materias
            ?.groupBy { it.semestre }
            ?: emptyMap()

        val semestreSeleccionado = ejemploLista.keys.first()

        // Adaptador inicial
        val materiasIniciales = ejemploLista[semestreSeleccionado].orEmpty()
        adapter = CalificacionAdapter(materiasIniciales, 0)
        recyclerView.adapter = adapter

        // Promedio general
        val promedio = calcularPromedioGeneral(materiasIniciales)
        lblGeneral.text = if (promedio > 0) "PromG: %.1f".format(promedio) else "PromG: N/A"

        // Observador de semestre (desde el Service)
        UsuarioService.semestreSeleccionado.observe(viewLifecycleOwner) { semestre ->
            semestre?.let {
                actualizarVistaConSemestre(it)
                ocultarSpinnerSiVisible()
            }
        }

        // Configurar filtros (chips)
        configurarChips()

        // Botón de abrir spinner
        view.findViewById<View>(R.id.lblGeneral4).setOnClickListener {
            llamarFragmento()
        }
    }

    private fun configurarChips() {
        for (i in 0 until chipGroup.childCount) {
            val chip = chipGroup.getChildAt(i) as Chip
            chip.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    adapter.setPos(i)
                }
            }
        }
    }

    private fun llamarFragmento() {
        overlay.visibility = View.VISIBLE
        val fm = parentFragmentManager
        val tag = "ItemFragmentTag"
        val existing = fm.findFragmentByTag(tag)
        val tx = fm.beginTransaction()
        if (existing != null && existing.isVisible) {
            // Si ya está visible, lo removemos
            overlay.visibility = View.GONE
            tx.remove(existing).commit()
            contenedorSpinner.visibility = View.INVISIBLE
        } else {
            // Si no, lo agregamos/reemplazamos
            val listaSemestres = ejemploLista.keys.toList().reversed()
            val fragmento = ItemFragment.newInstance(1, listaSemestres)
            tx.replace(R.id.contenedorSpinner, fragmento, tag)
                .addToBackStack(null)
                .commit()
            contenedorSpinner.visibility = View.VISIBLE
        }
    }

    private fun actualizarVistaConSemestre(semestre: Int) {
        txtSpinner.text = semestre.toString()
        val materias = ejemploLista[semestre].orEmpty()
        adapter = CalificacionAdapter(materias, 0)
        recyclerView.adapter = adapter
        // actualizar promedio
        val prom = calcularPromedioGeneral(materias)
        lblGeneral.text = if (prom > 0) "PromG: %.1f".format(prom) else "PromG: N/A"
    }

    private fun ocultarSpinnerSiVisible() {
        overlay.visibility = View.GONE
        val fm = parentFragmentManager
        fm.findFragmentByTag("ItemFragmentTag")?.let {
            fm.beginTransaction().remove(it).commit()
            contenedorSpinner.visibility = View.GONE
        }
    }

    private fun calcularPromedioGeneral(materias: List<Materia>): Double {
        var suma = 0.0
        var cnt = 0
        materias.forEach { m ->
            m.calificaciones.calcularPromedio()?.let { p ->
                suma += p
                cnt++
            }
        }
        return if (cnt > 0) suma / cnt else 0.0
    }
}
