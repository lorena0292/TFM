package com.example.beaEstilsApp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.slider.RangeSlider

class NuevaCita : AppCompatActivity() {

    //Elementos
    lateinit var rngTiempo: RangeSlider
    lateinit var txtHoras: TextView
    private var tiempo:Int=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_nueva_cita)



        initComponents()
        initListeners()
    }


    private fun initComponents() {
        rngTiempo = findViewById(R.id.rngTiempo)
        txtHoras=findViewById(R.id.txtTiempo)
        txtHoras.text="${tiempo.toString()} horas"
    }

    private fun initListeners() {
//RangeSlider Tiempo Listener
        rngTiempo.addOnChangeListener{slider, value, fromUser ->
            tiempo=value.toInt()
            if(tiempo==0){
                txtHoras.text = "Menos de 1 hora"
            }
            else {
                txtHoras.text = " ${tiempo.toString()} horas"
            }
        }

    }
}
