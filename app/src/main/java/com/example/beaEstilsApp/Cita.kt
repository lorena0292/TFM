package com.example.beaEstilsApp

data class Cita(
    val cliente: String? = null,
    val dia: String? = null,
    val hora: String? = null,
    val tiempo: Int? = null,
    val servicios : ArrayList<String>? = null,
)