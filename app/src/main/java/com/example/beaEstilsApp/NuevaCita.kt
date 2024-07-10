package com.example.beaEstilsApp

import android.os.Bundle
import android.util.Log
import android.widget.CheckBox
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.slider.RangeSlider
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore


class NuevaCita : AppCompatActivity() {

    //Elementos
    lateinit var rngTiempo: RangeSlider
    lateinit var txtHoras: TextView
    lateinit var cbMechas: CheckBox
    lateinit var cbTinte: CheckBox
    lateinit var cbCortar: CheckBox
    lateinit var cbAlisado: CheckBox
    lateinit var cbTratamiento: CheckBox
    lateinit var cbHombre: CheckBox
    lateinit var cbLavarMarcar: CheckBox
    lateinit var cbExtraCorto: CheckBox
    lateinit var cbExtraLargo: CheckBox

    private var tiempo: Int = 0

    // Access a Cloud Firestore instance from your Activity
    val db = Firebase.firestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_nueva_cita)



        initComponents()
        initListeners()
        leeColeccion()
    }


    private fun initComponents() {
        rngTiempo = findViewById(R.id.rngTiempo)
        txtHoras = findViewById(R.id.txtTiempo)
        txtHoras.text = "${tiempo.toString()} horas"
        cbMechas = findViewById(R.id.cbMechas)
        cbTinte = findViewById(R.id.cbTinte)
        cbCortar = findViewById(R.id.cbCortar)
        cbAlisado = findViewById(R.id.cbAlisado)
        cbTratamiento = findViewById(R.id.cbTratamiento)
        cbLavarMarcar = findViewById(R.id.cbLavarMarcar)
        cbHombre = findViewById(R.id.cbHombre)
        cbExtraCorto = findViewById(R.id.cbExtraCorto)
        cbExtraLargo = findViewById(R.id.cbExtraLargo)
    }

    private fun initListeners() {
//RangeSlider Tiempo Listener
        rngTiempo.addOnChangeListener { slider, value, fromUser ->
            tiempo = value.toInt()
            if (tiempo == 0) {
                txtHoras.text = "Menos de 1 hora"
            } else {
                txtHoras.text = " ${tiempo.toString()} horas"
            }
        }

    }

    public fun leeColeccion() {
        db.collection("servicios")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("", "${document.id} => ${document.data.getValue("minutos")}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w("TAG", "Error getting documents.", exception)
            }
    }

    public fun calculaTiempo() {
        cbMechas.setOnCheckedChangeListener { _, isChecked ->
            // if (isChecked) tiempo+=


        }
    }
}
