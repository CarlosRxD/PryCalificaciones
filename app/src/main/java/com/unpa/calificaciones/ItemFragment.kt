package com.unpa.calificaciones

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.unpa.calificaciones.adapters.SemestreAdapter
import com.unpa.calificaciones.services.UsuarioService

class ItemFragment : Fragment() {

    private var columnCount = 1
    private var semestres: List<String> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
            semestres = it.getStringArray(ARG_SEMESTRES)?.toList() ?: emptyList()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_item_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = if (columnCount <= 1) {
                    LinearLayoutManager(context)
                } else {
                    GridLayoutManager(context, columnCount)
                }
                adapter = SemestreAdapter(semestres) { seleccion ->
                    onSemestreSeleccionado(seleccion)
                }
            }
        }
        return view
    }

    private fun onSemestreSeleccionado(semestre: String) {
        UsuarioService.seleccionarSemestre(semestre)
        Toast.makeText(context, "Seleccionado: $semestre", Toast.LENGTH_LONG).show()
    }

    companion object {
        private const val ARG_COLUMN_COUNT = "column-count"
        private const val ARG_SEMESTRES = "semestres"

        @JvmStatic
        fun newInstance(columnCount: Int, semestres: List<String>): ItemFragment {
            val fragment = ItemFragment()
            val args = Bundle().apply {
                putInt(ARG_COLUMN_COUNT, columnCount)
                putStringArray(ARG_SEMESTRES, semestres.toTypedArray())
            }
            fragment.arguments = args
            return fragment
        }
    }
}
