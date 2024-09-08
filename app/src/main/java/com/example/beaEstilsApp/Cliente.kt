package com.example.beaEstilsApp

data class Cliente(
    val nombre: String? = null,
    val dia: String? = null,
    val hora: String? = null,
    val tiempo: Int? = null,
   val servicios : ArrayList<String>? = null,
)