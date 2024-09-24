package com.example.beaEstilsApp

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Resultado :AppCompatActivity(){
    private var cliente: String? = null
    private var tiempo: Int? = 0
    private var listaServicios: ArrayList<String>? = null
    private var dia: String? = null
    private var hora: String? = null



    private lateinit var txtCliente: TextView
    private lateinit var txtServicios: TextView
    private lateinit var txtDia: TextView
    private lateinit var txtHora: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_resultado)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.resultado)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        //Obtain animal from main_activity
        val paquete: Bundle? = intent.extras
        cliente = paquete?.getString("cliente")
        if (paquete != null) {
            listaServicios= paquete.getStringArrayList("listaServicios")

        }
        dia = paquete?.getString("dia")
        tiempo = paquete?.getInt("tiempo")
        hora = paquete?.getString("hora")

        initComponents()

    }

    fun initComponents(){


        txtCliente = findViewById(R.id.txtCliente)
        txtServicios = findViewById(R.id.txtListaServicios)
        txtDia = findViewById(R.id.txtDia)
        txtHora = findViewById(R.id.txtHora)

        txtCliente.text = cliente
        var arrayServicios=""
        for (servicio in listaServicios!!){
            arrayServicios+=servicio
        }

        txtServicios.text=arrayServicios
        txtDia.text = dia
        txtHora.text = hora


    }

    fun actualizaDatos(){

    }
}