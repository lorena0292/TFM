package com.example.beaEstilsApp


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen


class MainActivity : AppCompatActivity() {
    //Splash time
    private var splashScreenStays :Boolean = true
    private val DELAY:Long = 1500L

    //Elementos
    private lateinit var btnCitas: Button
    private lateinit var btnClientes: Button


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
    }





}

