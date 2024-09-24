package com.example.beaEstilsApp

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

public class Cliente(nombre:String="",apellidos:String="",direccion:String="",genero:String="",mail:String="",telefono:String=""){


    public var nombre: String? = nombre
    public var apellidos: String? = apellidos
    public var direccion: String? = direccion
    public var genero: String? = genero
    public var mail: String? = mail
    public var telefono: String = telefono

    public fun getClienteString():String{
        return "$nombre $apellidos $telefono"
    }
    public fun getClienteByTelefono(telefono: String):Cliente{
        val db = Firebase.firestore
        val coleccion_clientes = db.collection("clientes")
        val cli=coleccion_clientes.document(telefono)
       var miCliente=Cliente()
        var cliente=Cliente()

        GlobalScope.launch(Dispatchers.IO) {
            delay(3000L)
            var cliente = cli.get().await().toObject(Cliente::class.java)!!

            Log.d("cli",cliente.nombre.toString())
            withContext(Dispatchers.Main) {
                  miCliente=cliente
                Log.d("cli",cliente.nombre.toString())
                Log.d("Micli",miCliente.nombre.toString())
                }
            }



        return miCliente



        }


    suspend fun getDocumentClienteByTelefono(telefono: String): QuerySnapshot?{

        val db = Firebase.firestore
        var cli:Cliente=Cliente()
        val coleccion_clientes = db.collection("clientes")

        val document =coleccion_clientes.whereEqualTo("telefono", telefono)
        .get()
        .await()
        return document


    }



}

