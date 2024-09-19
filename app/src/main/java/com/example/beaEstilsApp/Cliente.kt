package com.example.beaEstilsApp

public class Cliente(nombre:String="",apellidos:String="",direccion:String="",genero:String="",mail:String="",telefono:String=""){


    var nombre: String? = nombre
    var apellidos: String? = apellidos
    var direccion: String? = direccion
    var genero: String? = genero
    var mail: String? = mail
    var telefono: String = telefono

    public fun getClienteString():String{
        return "$nombre $apellidos $telefono"
    }


}