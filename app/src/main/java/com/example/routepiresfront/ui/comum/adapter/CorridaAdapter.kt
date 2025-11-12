package com.example.routepiresfront.ui.comum.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.routepiresfront.R
import com.example.routepiresfront.model.Corrida

class CorridaAdapter(private val lista: List<Corrida>) :
    RecyclerView.Adapter<CorridaAdapter.CorridaViewHolder>() {

    class CorridaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nome: TextView = view.findViewById(R.id.txtNome)
        val dataHora: TextView = view.findViewById(R.id.txtDataHora)
        val status: TextView = view.findViewById(R.id.txtStatus)
        val mensagem: ImageView = view.findViewById(R.id.imgMensagem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CorridaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_corrida, parent, false)
        return CorridaViewHolder(view)
    }

    override fun onBindViewHolder(holder: CorridaViewHolder, position: Int) {
        val corrida = lista[position]
        holder.nome.text = corrida.nome
        holder.dataHora.text = corrida.dataHora
        holder.status.text = corrida.status
    }

    override fun getItemCount(): Int = lista.size
}