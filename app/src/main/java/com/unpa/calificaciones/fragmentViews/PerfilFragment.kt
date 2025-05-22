package com.unpa.calificaciones.fragmentViews

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.MaterialToolbar
import com.unpa.calificaciones.R
import com.unpa.calificaciones.services.UsuarioService

class PerfilFragment : Fragment(R.layout.activity_perfil) {


    private lateinit var toolbar: MaterialToolbar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar = requireActivity().findViewById<MaterialToolbar>(R.id.top_app_bar)
        toolbar.title = "Perfil"
        // Ajuste de inset de sistema
        ViewCompat.setOnApplyWindowInsetsListener(
            view.findViewById(R.id.main)
        ) { v, insets ->
            val sys = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(sys.left, sys.top, sys.right, sys.bottom)
            insets
        }

        // Poblado de datos
        UsuarioService.alumnoActual?.let { alumno ->
            view.findViewById<TextView>(R.id.txtNombreAlumno).text =
                "${alumno.nombre} ${alumno.apPaterno} ${alumno.apMaterno}"
            view.findViewById<TextView>(R.id.txtTipoAlumno).text =
                if (alumno.esRegular) "Regular" else "Irregular"
        }
    }
}