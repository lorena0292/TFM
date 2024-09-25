package com.example.beaEstilsApp


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.AbsListView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity() {
    //Splash time
    private var splashScreenStays :Boolean = true
    private val DELAY:Long = 1500L

    //Elementos
    private lateinit var btnCitas: Button
    private lateinit var btnClientes: Button



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
        //Usamos el splash creado
        val screenSplash: SplashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        //Incrementamos el tiempo del Splash
        screenSplash.setKeepOnScreenCondition{splashScreenStays}
        Handler(Looper.getMainLooper()).postDelayed({ splashScreenStays = false }, DELAY)

        //Inicializamos componentes y listeners
        initComponents()
        initListeners()

    }
    private fun initComponents() {
        btnCitas=findViewById(R.id.btnCitas)
        btnClientes=findViewById(R.id.btnClientes)
        citasLV = findViewById(R.id.idLV)
        citasList = ArrayList()
    }

    private fun initListeners(){

        //ingreso a citas
        btnCitas.setOnClickListener{
            val intent = Intent(this, Citas::class.java)
            startActivity(intent)
        }
        //ingreso a citas
        btnClientes.setOnClickListener{
            val intent = Intent(this, Clientes::class.java)
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


        citasLV.setOnScrollListener(object : AbsListView.OnScrollListener {
            private var lastFirstVisibleItem = 0
            override fun onScrollStateChanged(view: AbsListView, scrollState: Int) {}
            override fun onScroll(
                view: AbsListView,
                firstVisibleItem: Int,
                visibleItemCount: Int,
                totalItemCount: Int
            )
            {
                if (lastFirstVisibleItem < firstVisibleItem) {
                    Toast.makeText(
                        applicationContext, "Bajando en la lista ..",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                if (lastFirstVisibleItem > firstVisibleItem) {
                    Toast.makeText(
                        applicationContext, "Subiendo en la lista",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                lastFirstVisibleItem = firstVisibleItem
            }
        })
    }



}

