package com.example.beaEstilsApp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class Citas : AppCompatActivity() {

    //Elementos
    private lateinit var btnNuevaCita: Button
    private lateinit var btnBuscarCita: Button
    lateinit var logo: ImageView

    var cita: Cita = Cita()
    var clienteBuscado: Cliente =Cliente()
    // Access a Cloud Firestore instance from your Activity
    val db = Firebase.firestore
    val coleccion_citas = db.collection("citas")
    val coleccion_clientes = db.collection("clientes")



    // creating variables for listview
    lateinit var citasLV: ListView

    // creating array adapter for listview
    lateinit var listAdapter: ArrayAdapter<String>

    // creating array list for listview
    lateinit var citasList: ArrayList<String>;
    // initializing list

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
        btnBuscarCita = findViewById(R.id.btnBuscarCita)
        citasLV = findViewById(R.id.idLV)
        citasList = ArrayList()
        logo=findViewById(R.id.logo)
    }

    private fun initListeners() {

        //ingreso a nuevas citas
        btnNuevaCita.setOnClickListener {
            val intent = Intent(this, NuevaCita::class.java)
            startActivity(intent)
        }
        btnBuscarCita.setOnClickListener {
            val intent = Intent(this, BuscarCitas::class.java)
            startActivity(intent)
        }
        logo.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


        coleccion_citas
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {

                    GlobalScope.launch(Dispatchers.IO) {

                        var telefono = document.data.getValue("telCliente").toString()
                        Log.d("tel", telefono)
                        val cli = coleccion_clientes.document(telefono)

                        withContext(Dispatchers.Main) {
                            clienteBuscado = cli.get().await().toObject(Cliente::class.java)!!
                            Log.d("cli", clienteBuscado.nombre.toString())
                            cita.telCliente = clienteBuscado.telefono
                            Log.d("cli", clienteBuscado.nombre.toString())
                            cita.dia = document.data.getValue("dia")?.toString()
                            cita.hora = document.data.getValue("hora")?.toString()
                            var citaString =
                                cita.getCitaString(clienteBuscado.nombre, clienteBuscado.apellidos)
                            Log.d("citaString", citaString)
                            citasList.add(citaString)

                            continua()
                        }
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.d("Error", "Error getting documents: ", exception)
            }


    }

    public fun continua(){
        // initializing list adapter and setting layout
        // for each list view item and adding array list to it.
        listAdapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_list_item_1,
            citasList
        )

        // on below line setting list
        // adapter to our list view.
        citasLV.adapter = listAdapter
    }

}
