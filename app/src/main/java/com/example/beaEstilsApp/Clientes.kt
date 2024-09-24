package com.example.beaEstilsApp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.beaEstilsApp.adapters.ClientesAdapter
import com.google.firebase.Firebase
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.firestore


class Clientes : AppCompatActivity() {

    //Elementos
    lateinit var btnNuevoCliente: Button
    lateinit var btnBuscarCliente: Button

    // creating array list for listview
    lateinit var clientesList: ArrayList<String>
    var cliente :Cliente= Cliente()

    //Recycler clientes
    private lateinit var rvClientes: RecyclerView
    private lateinit var clientesArrayList:ArrayList<Cliente>
    private lateinit var clientesAdapter: ClientesAdapter

    // creating variables for listview
    // lateinit var clientesLV: ListView

    // creating array adapter for listview
    lateinit var listAdapter: ArrayAdapter<String>



    // private val clientesInit= mutableListOf<ClienteResponseItem>()

    // Access a Cloud Firestore instance from your Activity
    val db = Firebase.firestore
    val coleccion_clientes = db.collection("clientes")
    // Access a Cloud Firestore instance from your Activity

    val coleccionClientes = FirebaseDatabase.getInstance().getReference("clientes")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_clientes)

        initComponents()
        initListeners()




        /*

        //Montamos el recycler de clientes

        rvClientes.layoutManager = LinearLayoutManager(this)

        clientesArrayList= arrayListOf<Cliente>()
*/
    }


    private fun initComponents() {
        btnNuevoCliente = findViewById(R.id.btnNuevoCliente)
        btnBuscarCliente = findViewById(R.id.btnBuscarCliente)
        rvClientes = findViewById(R.id.rvClientes)


        // initializing list
        clientesList = ArrayList()
        creaListaClientes()
        // initializing list adapter and setting layout
        // for each list view item and adding array list to it.
        listAdapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_list_item_1,
            clientesList        )

        // on below line setting list
        // adapter to our list view.
       //
    // rvClientes.adapter=listAdapter
    }



    private fun initListeners() {

        btnNuevoCliente.setOnClickListener{
            val intent = Intent(this, NuevoCliente::class.java)
            startActivity(intent)
        }
        btnBuscarCliente.setOnClickListener{
            val intent = Intent(this, BuscarClientes::class.java)
            startActivity(intent)
        }
       // getCliente()


    }
/*
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
    */

    public fun creaListaClientes(){

        clientesList = ArrayList()


        coleccion_clientes
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {

                    cliente.nombre=document.data.getValue("nombre").toString()
                    cliente.apellidos=document.data.getValue("apellidos").toString()
                    cliente.telefono=document.data.getValue("telefono").toString()
                    clientesList.add(cliente.getClienteString())
                }
            }
            .addOnFailureListener { exception ->
                Log.d("Error", "Error getting documents: ", exception)
            }


    }



}
