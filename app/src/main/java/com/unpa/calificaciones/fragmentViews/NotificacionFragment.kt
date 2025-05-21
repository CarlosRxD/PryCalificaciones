package com.unpa.calificaciones.fragmentViews

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.unpa.calificaciones.R
import com.unpa.calificaciones.services.UsuarioService

class NotificacionFragment : Fragment(R.layout.activity_notificaciones) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Ajuste de inset de sistema
        ViewCompat.setOnApplyWindowInsetsListener(
            view.findViewById(R.id.main)
        ) { v, insets ->
            val sys = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(sys.left, sys.top, sys.right, sys.bottom)
            insets
        }
    }
}