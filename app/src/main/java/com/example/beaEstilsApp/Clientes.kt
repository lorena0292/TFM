package com.example.beaEstilsApp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.beaEstilsApp.adapters.ClientesAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Clientes : AppCompatActivity() {

    //Elementos
    lateinit var btnNuevoCliente: Button
    lateinit var btnBuscarCliente: Button

    //Recycler clientes
    private lateinit var rvClientes: RecyclerView
    private lateinit var clientesArrayList:ArrayList<Cliente>
    private lateinit var clientesAdapter: ClientesAdapter

   // private val clientesInit= mutableListOf<ClienteResponseItem>()


    // Access a Cloud Firestore instance from your Activity

    val coleccionClientes = FirebaseDatabase.getInstance().getReference("clientes")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_clientes)

        initComponents()
        initListeners()


        //Montamos el recycler de clientes

        rvClientes.layoutManager = LinearLayoutManager(this)

        clientesArrayList= arrayListOf<Cliente>()

    }


    private fun initComponents() {
        btnNuevoCliente = findViewById(R.id.btnNuevoCliente)
        btnBuscarCliente = findViewById(R.id.btnBuscarCliente)
        rvClientes = findViewById(R.id.rvClientes)
    }

    private fun initListeners() {

        btnNuevoCliente.setOnClickListener{
            val intent = Intent(this, NuevoCliente::class.java)
            startActivity(intent)
        }
        btnBuscarCliente.setOnClickListener{
            val intent = Intent(this, ListarClientes::class.java)
            startActivity(intent)
        }
        getCliente()


    }

    private fun getCliente(){
        coleccionClientes.addValueEventListener(object :ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (clienteSnapshot in snapshot.children){
                        val cliente=clienteSnapshot.getValue(Cliente::class.java)
                        clientesArrayList.add(cliente!!)
                    }
                    rvClientes.adapter=ClientesAdapter(clientesArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

}
