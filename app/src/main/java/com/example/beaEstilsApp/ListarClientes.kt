package com.example.beaEstilsApp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.beaEstilsApp.adapters.ClientesAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ListarClientes : AppCompatActivity() {



    //Recycler clientes
    private lateinit var rvClientes: RecyclerView
    private lateinit var clientesArrayList:ArrayList<Cliente>


    // private val clientesInit= mutableListOf<ClienteResponseItem>()


    // Access a Cloud Firestore instance from your Activity

    val coleccionClientes = FirebaseDatabase.getInstance().getReference("clientes")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.lista_clientes)

        initComponents()
        getCliente()


        //Montamos el recycler de clientes

        rvClientes.layoutManager = LinearLayoutManager(this)

        clientesArrayList= arrayListOf<Cliente>()

    }


    private fun initComponents() {

        rvClientes = findViewById(R.id.listaClientes)
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
