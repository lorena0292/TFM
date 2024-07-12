package com.example.beaEstilsApp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore


class NuevoCliente : AppCompatActivity() {

    //Elementos
    lateinit var txtNombre: EditText
    lateinit var txtApellidos: EditText
    lateinit var txtMail: EditText
    lateinit var txtTelefono: EditText
    lateinit var txtDireccion: EditText
    lateinit var rbHombre: RadioButton
    lateinit var rbMujer: RadioButton
    lateinit var btGuardar: Button
    var radioGroup: RadioGroup? = null

    lateinit var nombre: String
    lateinit var apellidos: String
    lateinit var mail: String
    lateinit var direccion: String
    lateinit var telefono: String
    lateinit var genero: String

    // Access a Cloud Firestore instance from your Activity
    val db = Firebase.firestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_nueva_cita)

        initComponents()
        initListeners()

    }


    private fun initComponents() {
        txtNombre = findViewById(R.id.EditNombre)
        txtApellidos = findViewById(R.id.EditApellidos)
        txtMail = findViewById(R.id.EditMail)
        txtDireccion = findViewById(R.id.EditDireccion)
        txtTelefono = findViewById(R.id.EditTelefono)
        rbHombre = findViewById(R.id.rbHombre)
        rbMujer = findViewById(R.id.rbMujer)
        btGuardar = findViewById(R.id.btnGuardar)
        radioGroup = findViewById(R.id.radioGroup)

    }

    private fun initListeners() {
        btGuardar.setOnClickListener {
            val intent = Intent(this, Clientes::class.java)

            guardarRegistro()
            startActivity(intent)
        }
    }

    public fun guardarRegistro() {


        nombre = txtNombre.text.toString()
        apellidos = txtApellidos.text.toString()
        telefono = txtTelefono.text.toString()
        mail = txtMail.text.toString()
        direccion = txtDireccion.text.toString()
        comprobarGenero()

        val cliente = hashMapOf(
            "nombre" to nombre,
            "apellidos" to apellidos,
            "telefono" to telefono,
            "mail" to mail,
            "direccion" to direccion,
            "genero" to genero
        )

        db.collection("clientes")
            .add(cliente)
            .addOnSuccessListener { documentReference ->
                Log.d("TAG", "DocumentSnapshot escrito con ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w("TAG", "Error añadiendo documento", e)
            }
    }

    public fun comprobarGenero() {
        radioGroup?.setOnCheckedChangeListener { group, checkedId ->

            when (checkedId) {
                R.id.rbHombre -> {
                    genero = rbHombre.text.toString()
                }
                R.id.rbMujer -> {
                    genero = rbMujer.text.toString()
                }
            }
        }
    }
}
