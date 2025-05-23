package com.unpa.calificaciones.fragmentViews

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.unpa.calificaciones.ItemFragment
import com.unpa.calificaciones.R
import com.unpa.calificaciones.adapters.CalificacionAdapter
import com.unpa.calificaciones.modelos.Materia
import com.unpa.calificaciones.services.SemestreStringService
import com.unpa.calificaciones.services.UsuarioService

class CalificacionesFragment : Fragment(R.layout.activity_contenedor_calificaciones) {
    private lateinit var adapter: CalificacionAdapter
    private lateinit var ejemploLista: Map<Int, List<Materia>>
    private lateinit var overlay: View
    private lateinit var contenedorSpinner: FrameLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var chipGroup: ChipGroup
    private lateinit var botonSpinner: MaterialButton
    private lateinit var botonPrev: MaterialButton
    private lateinit var botonNext: MaterialButton
    private var semestreActual = -1
    private lateinit var toolbar: MaterialToolbar
    private lateinit var listaSemestres: List<Int>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar = requireActivity().findViewById(R.id.top_app_bar)
        overlay = view.findViewById(R.id.blurOverlaySpinner)
        contenedorSpinner = view.findViewById(R.id.contenedorSpinner)
        recyclerView = view.findViewById(R.id.vistaCalificaciones)
        chipGroup = view.findViewById(R.id.chipGroupFilters)
        botonSpinner = view.findViewById(R.id.btnSemestre)
        botonPrev = view.findViewById(R.id.btnIzquierdo)
        botonNext = view.findViewById(R.id.btnDerecho)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Agrupo materias por semestre y genero lista ordenada
        ejemploLista = UsuarioService.alumnoActual?.materias
            ?.groupBy { it.semestre }
            ?: emptyMap()
        listaSemestres = ejemploLista.keys.sorted()

        // Semestre inicial: el último disponible
        semestreActual = listaSemestres.lastOrNull() ?: 1
        UsuarioService.seleccionarSemestre(semestreActual)
        UsuarioService.ultimoSemestre = semestreActual

        // Observador de cambio de semestre
        UsuarioService.semestreSeleccionado.observe(viewLifecycleOwner) { semestre ->
            semestre?.let {
                semestreActual = it
                actualizarVistaConSemestre()
                ocultarSpinnerSiVisible()
            }
        }

        // Inicializar vista
        actualizarVistaConSemestre()

        // Botón de abrir selector
        botonSpinner.setOnClickListener { llamarFragmento() }

        // Navegación anterior
        botonPrev.setOnClickListener {
            if (tieneAnterior()) {
                UsuarioService.seleccionarSemestre(listaSemestres[listaSemestres.indexOf(semestreActual) - 1])
            }
        }
        // Navegación siguiente
        botonNext.setOnClickListener {
            if (tieneSiguiente()) {
                UsuarioService.seleccionarSemestre(listaSemestres[listaSemestres.indexOf(semestreActual) + 1])
            }
        }
    }

    private fun configurarChips() {
        chipGroup.setOnCheckedChangeListener(null) // Limpia listeners previos

        for (i in 0 until chipGroup.childCount) {
            val chip = chipGroup.getChildAt(i) as Chip
            val hayValor = ejemploLista[semestreActual]?.any { it.tieneValor(i) } == true

            chip.visibility = if (hayValor) View.VISIBLE else View.GONE
            chip.text = SemestreStringService.chipLabels[i]
            chip.tag = i
        }

        // Selecciona por defecto el primer chip visible
        val primerChipVisible = (0 until chipGroup.childCount)
            .firstOrNull { chipGroup.getChildAt(it).visibility == View.VISIBLE }

        primerChipVisible?.let {
            val chip = chipGroup.getChildAt(it) as Chip
            chip.isChecked = true
            adapter.setPos(chip.tag as Int)
            chip.text = SemestreStringService.activeChipLabels[chip.tag as Int]
        }

        chipGroup.setOnCheckedChangeListener { group, checkedId ->
            for (i in 0 until group.childCount) {
                val chip = group.getChildAt(i) as Chip
                val index = chip.tag as Int
                if (chip.id == checkedId) {
                    chip.text = SemestreStringService.activeChipLabels[index]
                    adapter.setPos(index)
                } else {
                    chip.text = SemestreStringService.chipLabels[index]
                }
            }
        }
    }


    private fun llamarFragmento() {
        overlay.visibility = View.VISIBLE
        val fm = parentFragmentManager
        val tag = "ItemFragmentTag"
        val existing = fm.findFragmentByTag(tag)
        fm.beginTransaction().apply {
            if (existing != null && existing.isVisible) {
                ocultarSpinnerSiVisible()
            } else {
                val fragmento = ItemFragment.newInstance(1,generarMapaSemestres())
                replace(R.id.contenedorSpinner, fragmento, tag)
                addToBackStack(null)
                commit()
                contenedorSpinner.visibility = View.VISIBLE
            }
        }
    }

    private fun actualizarVistaConSemestre() {
        botonSpinner.text = SemestreStringService.semestres[semestreActual - 1]
        adapter = CalificacionAdapter(ejemploLista[semestreActual].orEmpty(), 0)
        recyclerView.adapter = adapter
        toolbar.title = calcularPromedioGeneral(ejemploLista[semestreActual].orEmpty())
            .takeIf { it > 0 }?.let { "PromG: %.1f".format(it) } ?: "PromG: N/A"
        configurarChips()
        botonPrev.isEnabled = tieneAnterior()
        botonNext.isEnabled = tieneSiguiente()
        botonPrev.isVisible = tieneAnterior()
        botonNext.isVisible = tieneSiguiente()
    }

    private fun tieneAnterior(): Boolean {
        val idx = listaSemestres.indexOf(semestreActual)
        return idx > 0
    }

    private fun tieneSiguiente(): Boolean {
        val idx = listaSemestres.indexOf(semestreActual)
        return idx >= 0 && idx < listaSemestres.size - 1
    }

    private fun ocultarSpinnerSiVisible() {
        overlay.visibility = View.GONE
        parentFragmentManager.findFragmentByTag("ItemFragmentTag")?.let {
            parentFragmentManager.beginTransaction().remove(it).commit()
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
    private fun generarMapaSemestres(): Map<Int, String> {
        return ejemploLista.mapValues { (_, materias) ->
            val promedios = materias.mapNotNull { it.calificaciones.calcularPromedio() }
            if (promedios.isNotEmpty()) {
                val promedio = promedios.average()
                if (promedio == 0.0) "N/A" else "%.1f".format(promedio)
            } else {
                "N/A"
            }
        }
    }

}
