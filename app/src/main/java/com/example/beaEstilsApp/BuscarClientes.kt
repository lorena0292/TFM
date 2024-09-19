package com.example.beaEstilsApp

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

public class BuscarClientes: AppCompatActivity(){
    // on below line we are
    // creating variables for listview
    lateinit var clientesLV: ListView

    // creating array adapter for listview
    lateinit var listAdapter: ArrayAdapter<String>

    // creating array list for listview
    lateinit var clientesList: ArrayList<String>;

    // creating variable for searchview
    lateinit var searchView: SearchView

    var cliente :Cliente= Cliente()

    // Access a Cloud Firestore instance from your Activity
    val db = Firebase.firestore
    val coleccion_clientes = db.collection("clientes")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buscar_clientes)

        // initializing variables of list view with their ids.
        clientesLV = findViewById(R.id.idLV)
        searchView = findViewById(R.id.idSV)

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
        clientesLV.adapter = listAdapter

        // on below line we are adding on query
        // listener for our search view.
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // on below line we are checking
                // if query exist or not.
                if (clientesList.contains(query)) {
                    // if query exist within list we
                    // are filtering our list adapter.
                    listAdapter.filter.filter(query)
                } else {
                    // if query is not present we are displaying
                    // a toast message as no  data found..
                    Toast.makeText(this@BuscarClientes, "No existe el cliente..", Toast.LENGTH_LONG)
                        .show()
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // if query text is change in that case we
                // are filtering our adapter with
                // new text on below line.
                listAdapter.filter.filter(newText)
                return false
            }
        })
    }

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
