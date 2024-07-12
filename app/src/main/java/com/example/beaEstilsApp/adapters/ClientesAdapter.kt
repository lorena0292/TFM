package com.example.beaEstilsApp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.beaEstilsApp.R
import com.example.beaEstilsApp.models.ClienteResponseItem
import com.example.beaEstilsApp.views.ClientesViewHolder

class ClientesAdapter (private val clientes: List<ClienteResponseItem>): RecyclerView.Adapter<ClientesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientesViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cliente,parent,false)
        return ClientesViewHolder(view)

    }

    override fun getItemCount(): Int {
        return clientes.size
    }

    override fun onBindViewHolder(holder: ClientesViewHolder, position: Int) {
        holder.render(clientes[position])
    }
}