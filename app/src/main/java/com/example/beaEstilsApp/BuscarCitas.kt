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

public class BuscarCitas: AppCompatActivity() {
    // on below line we are
    // creating variables for listview
    lateinit var citasLV: ListView

    // creating array adapter for listview
    lateinit var listAdapter: ArrayAdapter<String>

    // creating array list for listview
    lateinit var citasList: ArrayList<String>;

    // creating variable for searchview
    lateinit var searchView: SearchView

    var clienteBuscado: Cliente = Cliente()
    var cita: Cita = Cita()

    // Access a Cloud Firestore instance from your Activity
    val db = Firebase.firestore
    val coleccion_citas = db.collection("citas")
    val coleccion_clientes = db.collection("clientes")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buscar_citas)

        // initializing variables of list view with their ids.
        citasLV = findViewById(R.id.idLV)
        searchView = findViewById(R.id.idSV)

        // initializing list
        citasList = ArrayList()
        creaListaCitas()

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

        // on below line we are adding on query
        // listener for our search view.
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // on below line we are checking
                // if query exist or not.
                if (citasList.contains(query)) {
                    // if query exist within list we
                    // are filtering our list adapter.
                    listAdapter.filter.filter(query)
                } else {
                    // if query is not present we are displaying
                    // a toast message as no  data found..
                    Toast.makeText(this@BuscarCitas, "No existe el cliente..", Toast.LENGTH_LONG)
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

    public fun creaListaCitas() {

        citasList = ArrayList()


        coleccion_citas
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {

                    var telefono = document.data.getValue("telefono").toString()
                    Log.d("tel",telefono)
                    buscarCliente(telefono)
                    if (clienteBuscado != null)
                        cita.cliente = clienteBuscado
                    Log.d("cli",clienteBuscado.nombre.toString())
                    cita.dia = document.data.getValue("dia")?.toString()
                    cita.hora = document.data.getValue("hora")?.toString()
                    citasList.add(cita.getCitaString())
                }
            }
            .addOnFailureListener { exception ->
                Log.d("Error", "Error getting documents: ", exception)
            }


    }

    public fun buscarCliente(telefono: String)   {

        coleccion_clientes.document(telefono)
            .get()
            .addOnSuccessListener { document ->

                if (document != null) {
                    Log.d("ok", "DocumentSnapshot data: ${document.data}")
                    Log.d("ok", "DocumentSnapshot data: ${document.data?.getValue("nombre").toString()}")
                    clienteBuscado.nombre= document.data?.getValue("nombre").toString()
                    clienteBuscado.apellidos= document.data?.getValue("apellidos").toString()
                    clienteBuscado.telefono=telefono


                } else {
                    Log.d("Error", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("TAG", "get failed with ", exception)
            }


    }
}
