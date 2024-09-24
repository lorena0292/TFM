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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

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


     var clienteBuscado: Cliente =Cliente()
    /*  var cita: Cita = Cita("")
  */

   // var clienteBuscado: Cliente = Cliente()
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





        //creaListaCitas()


        coleccion_citas
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {



                    //  buscarCliente(telefono)
                    GlobalScope.launch(Dispatchers.IO) {
                        delay(3000L)
                        var telefono = document.data.getValue("telCliente").toString()
                        Log.d("tel", telefono)
                        val cli = coleccion_clientes.document(telefono)

                        // var cliente = Cliente()



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

    /*
    public fun creaListaCitas() {

        citasList = ArrayList()
        coleccion_citas
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {

                    var telefono = document.data.getValue("telCliente").toString()
                    Log.d("tel", telefono)

                    var doc = coleccion_clientes.document(telefono)
                    GlobalScope.launch(Dispatchers.IO) {
                        delay(3000L)
                        clienteBuscado=doc.get().await().toObject(Cliente::class.java)!!

/*
                            .addOnSuccessListener { doc ->
                                var nombre = doc.data?.getValue("nombre").toString()
                                var apellidos = doc.data?.getValue("apellidos").toString()
                                var telefono = doc.data?.getValue("telefono").toString()
                                clienteBuscado = Cliente(nombre, apellidos, "", "", "", telefono)


                            }
                            */

                        withContext(Dispatchers.Main) {

                            if (clienteBuscado != null) {
                                cita.telCliente = clienteBuscado!!.telefono
                            }
                            Log.d("cli", clienteBuscado?.nombre.toString())
                            cita.dia = document.data.getValue("dia")?.toString()
                            cita.hora = document.data.getValue("hora")?.toString()
                            citasList.add(cita.getCitaString())
                        }
                    }
                }


            }
            .addOnFailureListener { exception ->
                Log.d("Error", "Error getting documents: ", exception)
            }

    }



    public fun buscarCliente(telefono: String) {
        var cliente: Cliente = Cliente()
        val cli = coleccion_clientes.document(telefono)
        GlobalScope.launch(Dispatchers.IO) {
            delay(3000L)
            cliente = cli.get().await().toObject(Cliente::class.java)!!
            withContext(Dispatchers.Main) {
                clienteBuscado = cliente
            }
        }
    }
    */

    public fun creaListaCitas() {

        coleccion_citas
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {



                 //  buscarCliente(telefono)
                    GlobalScope.launch(Dispatchers.IO) {
                        delay(3000L)
                        var telefono = document.data.getValue("telCliente").toString()
                        Log.d("tel",telefono)
                        val cli = coleccion_clientes.document(telefono)

                       // var cliente = Cliente()
                        clienteBuscado= cli.get().await().toObject(Cliente::class.java)!!


                        withContext(Dispatchers.Main) {
                            Log.d("cli", clienteBuscado.nombre.toString())
                            cita.telCliente = clienteBuscado.telefono
                            Log.d("cli",clienteBuscado.nombre.toString())
                            cita.dia = document.data.getValue("dia")?.toString()
                            cita.hora = document.data.getValue("hora")?.toString()
                            var citaString=cita.getCitaString(clienteBuscado.nombre,clienteBuscado.apellidos)
                            Log.d("citaString",citaString)
                            citasList.add(citaString)

                            }
                        }





                   // clienteBuscado=clienteBuscado.getClienteByTelefono(telefono)
                    //if (clienteBuscado != null)

                }
            }
            .addOnFailureListener { exception ->
                Log.d("Error", "Error getting documents: ", exception)
            }


    }
/*
    public fun buscarCliente(telefono: String)   {

        coleccion_clientes.document(telefono)
            .get()
            .addOnSuccessListener { document ->

                if (document != null) {
                    Log.d("ok", "DocumentSnapshot data: ${document.data}")
                    Log.d("ok", "DocumentSnapshot data: ${document.data?.getValue("nombre").toString()}")
                    clienteBuscado?.nombre= document.data?.getValue("nombre").toString()
                    clienteBuscado?.apellidos= document.data?.getValue("apellidos").toString()
                    clienteBuscado?.telefono=telefono


                } else {
                    Log.d("Error", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("TAG", "get failed with ", exception)
            }


    }
*/
    public fun buscarCliente(telefono: String) {
        val db = Firebase.firestore
        val coleccion_clientes = db.collection("clientes")
        val cli = coleccion_clientes.document(telefono)

        var cliente = Cliente()

        GlobalScope.launch(Dispatchers.IO) {
            delay(3000L)
            cliente= cli.get().await().toObject(Cliente::class.java)!!

            Log.d("cli", cliente.nombre.toString())
            withContext(Dispatchers.Main) {
                clienteBuscado = cliente
                Log.d("cli", clienteBuscado.nombre.toString())

            }
        }
    }

}
