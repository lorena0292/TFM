package com.example.beaEstilsApp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.slider.RangeSlider
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore


class NuevaCita : AppCompatActivity() {

    //Elementos
    lateinit var rngTiempo: RangeSlider
    lateinit var txtHoras: TextView
    lateinit var cbMechas: CheckBox
    lateinit var cbTinte: CheckBox
    lateinit var cbCortar: CheckBox
    lateinit var cbAlisado: CheckBox
    lateinit var cbTratamiento: CheckBox
    lateinit var cbHombre: CheckBox
    lateinit var cbLavarMarcar: CheckBox
    lateinit var cbExtraCorto: CheckBox
    lateinit var cbExtraLargo: CheckBox
    lateinit var cbs: Array<CheckBox>
    lateinit var etCliente: EditText
    lateinit var etFecha:EditText
    lateinit var etHora:EditText
    lateinit var btnGuardar: Button

    var min: Float =0.0F
    var cliente:Cliente=Cliente()
    //var cliente:String=""
    var listaServicios:ArrayList<String>? = null
    var tiempo: Int = 0
    var dia: String= "dd/mm/aaaa"
    var hora:String="00:00"


    // BDD
    val db = Firebase.firestore
    val coleccion_servicios = db.collection("servicios")
    val coleccion_citas=db.collection("citas")



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_nueva_cita)

        initComponents()
        initListeners()
    }


    private fun initComponents() {
        etCliente=findViewById(R.id.etCliente)
        etFecha=findViewById(R.id.etFecha)
        etHora=findViewById(R.id.etHora)
        btnGuardar=findViewById(R.id.btnGuardar)
        rngTiempo = findViewById(R.id.rngTiempo)
        txtHoras = findViewById(R.id.txtTiempo)
        txtHoras.text = "${tiempo.toString()} horas"
        cbMechas = findViewById(R.id.cbMechas)
        cbTinte = findViewById(R.id.cbTinte)
        cbCortar = findViewById(R.id.cbCortar)
        cbAlisado = findViewById(R.id.cbAlisado)
        cbTratamiento = findViewById(R.id.cbTratamiento)
        cbLavarMarcar = findViewById(R.id.cbLavarMarcar)
        cbHombre = findViewById(R.id.cbHombre)
        cbExtraCorto = findViewById(R.id.cbExtraCorto)
        cbExtraLargo = findViewById(R.id.cbExtraLargo)
        cbs=arrayOf(cbMechas, cbTinte, cbCortar,cbAlisado,cbTratamiento,cbHombre,cbLavarMarcar,cbExtraCorto,cbExtraLargo)
    }

    private fun initListeners() {
        //RangeSlider Tiempo Listener
        rngTiempo.addOnChangeListener { slider, value, fromUser ->
            if (tiempo == 0) {
                txtHoras.text = "Menos de 1 hora"
            } else {
                txtHoras.text = " ${tiempo.toString()} minutossssss"
            }

        }
        // calculaTiempo()
        //Checkboxes Servicios Listeners
        cbMechas.setOnCheckedChangeListener { _, isChecked ->
            getMinutos("Mechas")
            if (isChecked) {
                suma(min.toInt())
                txtHoras.text = "${tiempo.toString()} minutos"
            } else {
                resta(min.toInt())
                txtHoras.text = "${tiempo.toString()} minutos"
            }
        }

        //Cliente listener
        etCliente.setOnClickListener {}
        //Fecha y Hora Listeners
        etFecha.setOnClickListener { showDatePickerDialog() }
        etHora.setOnClickListener { showTimePickerDialog() }

        //Boton Guardar Listener
        btnGuardar.setOnClickListener {
            if(comprobarCampos()){
                val intent = Intent(this, Resultado::class.java)
                cliente.telefono=etCliente.text.toString()
                listaServicios= arrayListOf("")
                dia=etFecha.text.toString()
                hora=etHora.text.toString()

                val cita = Cita(
                    cliente,
                    dia,
                    hora,
                    tiempo,
                    listaServicios,
                )
                guardaEnBDD(cita)

                intent.putExtra("cliente",cliente.telefono)
                intent.putExtra("listaServicios",listaServicios)
                intent.putExtra("dia",dia)
                intent.putExtra("hora",hora)
                startActivity(intent)
            }
        }
    }

    private fun showDatePickerDialog() {
        val datePicker = DatePickerFragment { day, month, year -> onDateSelected(day, month, year) }
        datePicker.show(supportFragmentManager, "datePicker")
    }
    private fun onDateSelected(day: Int, month: Int, year: Int) {
        etFecha.setText("Día: $day / $month / $year")
    }

    private fun showTimePickerDialog() {
        val timePicker = TimePickerFragment { onTimeSelected(it) }
        timePicker.show(supportFragmentManager, "timePicker")
    }

    private fun onTimeSelected(time: String) {
        etHora.setText("Hora: $time")
    }

    public fun calculaTiempo() {

        val nombreServicio = ""
        for (cb in cbs) {
            cb.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    getMinutos(cb.text.toString())
                    tiempo += min.toInt()
                    rngTiempo.setValues(tiempo.toFloat())
                    txtHoras.text = "${tiempo.toString()} minutitos"
                }
                else  {
                    getMinutos(cb.text.toString())
                    val t=tiempo - min.toInt()
                        tiempo = t
                        rngTiempo.setValues(tiempo.toFloat())
                        txtHoras.text = "${tiempo.toString()} minutos"
                }
            }
        }
    }

public fun getMinutos(n:String){

    val doc = coleccion_servicios.whereEqualTo("nombre", n)
        .get()
        .addOnSuccessListener { result ->
            for (document in result) {
                min = "${document.data.getValue("minutos")}".toFloat()
    }}}

    public fun suma(cantidad: Int){
        tiempo+=cantidad
    }
    public fun resta(cantidad: Int){
        tiempo-=cantidad

    }

    public fun comprobarCampos():Boolean{
        return true
    }

    public fun guardaEnBDD(cita: Cita){
        coleccion_citas
            .add(cita)
            .addOnSuccessListener { documentReference ->
                Log.d("TAG", "DocumentSnapshot written with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w("TAG", "Error añadiendo documento", e)
            }



    }
   }

