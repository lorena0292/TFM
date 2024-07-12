package com.example.beaEstilsApp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.beaEstilsApp.adapters.ClientesAdapter
import com.example.beaEstilsApp.models.ClienteResponseItem
import com.example.beaEstilsApp.service.RetrofitServiceFactory
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.launch

class Clientes : AppCompatActivity() {

    //Elementos
    lateinit var btnNuevoCliente: Button
    lateinit var btnBuscarCliente: Button

    //Recycler clientes
    private lateinit var rvClientes: RecyclerView
    private lateinit var clientesAdapter: ClientesAdapter

    private val clientesInit= mutableListOf<ClienteResponseItem>()


    // Access a Cloud Firestore instance from your Activity
    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_clientes)

        initComponents()
        initListeners()


        //Montamos el recycler de pets
        clientesAdapter = ClientesAdapter(clientesInit)
        rvClientes.layoutManager = LinearLayoutManager(this)
        rvClientes.adapter = clientesAdapter

        //Montamos el servicio para lanzar la petición contra el API
        val apiPets = RetrofitServiceFactory.getClientesRetrofit()

        lifecycleScope.launch {
            //   val data = apiPets.getPets("cliente")
            val data = apiPets.getClientes("posts")
            //Relleno los datos desde la respuesta
            val clientesData = data
            //Borro datos del RecyclerView
            clientesInit.clear()
            clientesInit.addAll(clientesData)
            //Repinta RecyclerView
            clientesAdapter.notifyDataSetChanged()
        }
    }


    private fun initComponents() {
        btnNuevoCliente = findViewById(R.id.btnNuevoCliente)
        btnBuscarCliente = findViewById(R.id.btnBuscarCliente)

    }

    private fun initListeners() {

        rvClientes = findViewById(R.id.rvClientes)

        btnNuevoCliente.setOnClickListener{
            val intent = Intent(this, NuevoCliente::class.java)
            startActivity(intent)
        }
        btnBuscarCliente.setOnClickListener{
            val intent = Intent(this, NuevoCliente::class.java)
            startActivity(intent)
        }


    }

}
