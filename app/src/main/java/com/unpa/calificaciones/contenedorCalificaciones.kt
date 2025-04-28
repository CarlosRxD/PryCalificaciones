package com.unpa.calificaciones

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.FirebaseFirestore
import com.unpa.calificaciones.modelos.Periodo
import java.util.Date
import java.util.LinkedList

class contenedorCalificaciones : AppCompatActivity() {
    val db = FirebaseFirestore.getInstance();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_contenedor_calificaciones)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onStart() {
        super.onStart()

        this.obtenerPeriodoActual();
    }

    fun obtenerPeriodoActual() {
        val periodos: LinkedList<Periodo> = LinkedList();
        db.collection("ciclosEscolares").get().addOnSuccessListener { result ->
            for (document in result) {
                //Periodo("2024-2025 B", Date(2025-))
                //periodos.add(Periodo(document.id, Date(document.data.get("fechaFin"))),Date(document.data.get("fechaInicio")));
                Toast.makeText(this, "${document.id} => ${document.data}",Toast.LENGTH_LONG).show();
                //Log.d("Firestore", "${document.id} => ${document.data}")
            }
        }
            .addOnFailureListener { exception ->
                Toast.makeText(this,exception.toString(),Toast.LENGTH_LONG).show();
                Log.w("Firestore", "Error al obtener documentos", exception)
            }
    }

    /*fun obtenerPeriodoActual(): String {
        val calendar=java.util.Calendar.getInstance();
        val year=java.util.Calendar.YEAR;
        val month= calendar.get(java.util.Calendar.MONTH)+1;
        val periodoActua=if(month in 10..2){
             "${year-1}-$year" + "A";
        }else{
             "$year-{$year+1}" + "B";
        }
    Log.d(periodoActua);
        return periodoActua;
    }*/
}