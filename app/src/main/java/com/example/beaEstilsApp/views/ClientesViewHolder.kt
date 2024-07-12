package com.example.beaEstilsApp.views

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.beaEstilsApp.R
import com.example.beaEstilsApp.models.ClienteResponseItem
class ClientesViewHolder (view: View) : RecyclerView.ViewHolder(view) {

    private val txtNombrePet: TextView =view.findViewById(R.id.txtNombreCliente)
    private val txtTelefono: TextView =view.findViewById(R.id.txtTelefonoCliente)

    fun render(cliente: ClienteResponseItem) {

        txtNombrePet.text = cliente.nombre
        txtTelefono.text = cliente.telefono
    }
}