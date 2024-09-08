package com.example.beaEstilsApp.models


enum class GeneroClientes {
    MUJER, HOMBRE
}

data class Cliente(var nombre:String,
                   var genero:GeneroClientes,
                   var apellidos:String,
                   var telefono:String,
                   var mail:String,
                   var direccion:String,
                   var isSelected:Boolean=false,
                )
