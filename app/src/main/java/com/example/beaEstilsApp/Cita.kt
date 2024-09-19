package com.example.beaEstilsApp

import android.util.Log

class Cita(
     cliente: Cliente = Cliente(),
     dia: String? = null,
     hora: String? = null,
     tiempo: Int? = null,
     servicios : ArrayList<String>? = null,
){
    var cliente: Cliente = cliente
    var dia: String? = dia
    var hora: String? = hora
    var tiempo: Int? = tiempo
    var servicios : ArrayList<String>? = servicios

    public fun getCitaString():String{
        Log.d("CITA",cliente.nombre.toString())
        return "${cliente?.getClienteString()} ${dia} ${hora} "
    }

}