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
    var genero: String = "Mujer"

    // Access a Cloud Firestore instance from your Activity
    val db = Firebase.firestore
    val coleccion_clientes = db.collection("clientes")



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_nuevo_cliente)

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

            radioGroup?.setOnCheckedChangeListener { group, checkedId ->
                comprobarGenero(checkedId)
            }
            guardarCliente()

            startActivity(intent)
        }
    }


    public fun guardarCliente() {

        nombre = txtNombre.text.toString()
        apellidos = txtApellidos.text.toString()
        telefono = txtTelefono.text.toString()
        mail = txtMail.text.toString()
        direccion = txtDireccion.text.toString()
        val cliente= Cliente(nombre,apellidos, direccion, genero, mail, telefono)

        coleccion_clientes.document(cliente?.telefono ?: " " )
            .set(cliente)
            .addOnSuccessListener { Log.d("ok", "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w("error", "Error writing document", e) }



    }

    public fun comprobarGenero(checkedId:Int) {
            when (checkedId) {
                R.id.rbHombre -> {
                    genero = "Hombre"
                }
                R.id.rbMujer -> {
                    genero = "Mujer"
                }
            }

    }
}
