package com.example.beaEstilsApp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class Citas : AppCompatActivity() {

    //Elementos
    private lateinit var btnNuevaCita: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_citas)
        //Inicializamos componentes y listeners
        initComponents()
        initListeners()

    }

    private fun initComponents() {
        btnNuevaCita = findViewById(R.id.btnNuevaCita)
    }

    private fun initListeners() {

        //ingreso a nuevas citas
        btnNuevaCita.setOnClickListener {
            val intent = Intent(this, NuevaCita::class.java)
            startActivity(intent)
        }
    }
}
