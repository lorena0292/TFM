package com.example.beaEstilsApp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Date


class Clientes : AppCompatActivity() {

    //Elementos
    lateinit var btnNuevoCliente: Button
    lateinit var btnBuscarCliente: Button
    lateinit var texto: TextView
    lateinit var logo: ImageView
    // creating array list for listview

    var cliente :Cliente= Cliente()

    // creating variables for listview
    lateinit var citasLV: ListView

    // creating array adapter for listview
    lateinit var listAdapter: ArrayAdapter<String>

    // creating array list for listview
    lateinit var citasList: ArrayList<String>;
    // initializing list

    //Recycler clientes
 //   private lateinit var rvClientes: RecyclerView
//    private lateinit var clientesArrayList:ArrayList<Cliente>
  //  private lateinit var clientesAdapter: ClientesAdapter

    // creating variables for listview
    // lateinit var clientesLV: ListView
    var cita: Cita = Cita()
    var clienteBuscado: Cliente =Cliente()


    // private val clientesInit= mutableListOf<ClienteResponseItem>()

    // Access a Cloud Firestore instance from your Activity
    val db = Firebase.firestore
    val coleccion_clientes = db.collection("clientes")
    val coleccion_citas = db.collection("citas")

    val now = LocalDateTime.now()

    // Obtener los componentes de fecha y hora individuales

    // Obtener los componentes de fecha y hora individuales
    val year = now.year
    val month = now.monthValue
    val day = now.dayOfMonth
    val fechaHoy="${day}/${month}/${year}"
    val hour = now.hour
    val minute = now.minute
    val second = now.second


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
        citasLV = findViewById(R.id.idLV)
        citasList = ArrayList()
        texto=findViewById(R.id.texto)
        texto.text="PROXIMAS CITAS A FECHA ${fechaHoy}"
        logo=findViewById(R.id.logo)
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



                            val sdf = SimpleDateFormat("dd/MM/yyyy")
                            Log.d("citas","citaDia ${cita.dia!!} ${fechaHoy}" )

                            val diaCita:Date = sdf.parse(cita.dia)
                            Log.d("diaCita", diaCita.toString())

                            val diaHoy:Date = sdf.parse(fechaHoy.toString())
                            Log.d("diaHoy", diaHoy.toString())
                            when {
                                diaCita.after(diaHoy) -> {
                                    var citaString =
                                        cita.getCitaString(clienteBuscado.nombre, clienteBuscado.apellidos)
                                    Log.d("citaString", citaString)
                                    citasList.add(citaString)
                                    Log.d("fechasmas tarde a hoy"," ${diaCita}")
                                }
                                diaCita.before(diaHoy) -> {
                               //     System.out.printf("%s is before %s", diaCita, diaHoy)
                                }
                                diaCita == diaHoy -> {
                                    Log.d("mismo dia"," ${diaCita}")
                                    var citaString =
                                        cita.getCitaString(clienteBuscado.nombre, clienteBuscado.apellidos)
                                    Log.d("citaString", citaString)
                                    citasList.add(citaString)
                                }
                            }




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

        coleccion_clientes.orderBy("nombre")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {

                    cliente.nombre=document.data.getValue("nombre").toString()
                    cliente.apellidos=document.data.getValue("apellidos").toString()
                    cliente.telefono=document.data.getValue("telefono").toString()
                  //  Log.d("cliente", cliente.nombre.toString())
                    citasList.add(cliente.getClienteString())
                }
            }
            .addOnFailureListener { exception ->
                Log.d("Error", "Error getting documents: ", exception)
            }
        listAdapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_list_item_1,
            citasList        )

        for (i in citasList)  Log.d("cliente", i)

        // on below line setting list
        // adapter to our list view.
        citasLV.adapter = listAdapter


    }



}
