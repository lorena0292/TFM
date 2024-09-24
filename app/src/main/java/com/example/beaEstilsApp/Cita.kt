package com.example.beaEstilsApp

import android.util.Log

class Cita(
     telCliente: String=String(),
     dia: String? = null,
     hora: String? = null,
     tiempo: Int? = null,
     servicios : ArrayList<String>? = null,
) {
    var telCliente: String = telCliente
    var dia: String? = dia
    var hora: String? = hora
    var tiempo: Int? = tiempo
    var servicios: ArrayList<String>? = servicios

    /*
    public fun getCitaString(): String {
        var cadena = ""
        cadena = " ${dia} ${hora} "
        var objCliente = Cliente()
        val db = Firebase.firestore
        val coleccion_clientes = db.collection("clientes")
        val cli = coleccion_clientes.document(telCliente)
        GlobalScope.launch(Dispatchers.IO) {
            delay(3000L)
            objCliente = cli.get().await().toObject(Cliente::class.java)!!

            withContext(Dispatchers.Main) {
                cadena = "${objCliente.getClienteString()} ${dia} ${hora} "
            }

        }

        return cadena
    }


    public fun getCitaString():String{
        var cliente:Cliente= Cliente()

    //    cliente= cliente.getClienteByTelefono(this.telCliente)
        Log.d("CITA",cliente.nombre.toString())
      //  return "${cliente.getClienteString()} ${dia} ${hora} "
       return " ${dia} ${hora} "
    }
*/
    public fun getCitaString(nombre: String?, apellidos:String?):String{
        var cliente:Cliente= Cliente()

        //    cliente= cliente.getClienteByTelefono(this.telCliente)
        Log.d("CITA",nombre.toString())
        //  return "${cliente.getClienteString()} ${dia} ${hora} "
        return " ${nombre} ${apellidos} ${dia} ${hora} "
    }
}