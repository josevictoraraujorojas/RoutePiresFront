package com.example.routepiresfront.ui.comum.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.routepiresfront.R
import com.example.routepiresfront.data.model.CorridaDTOResponse
import java.text.SimpleDateFormat
import java.util.Locale

class CorridaAdapter(private var lista: List<CorridaDTOResponse>) :
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
        // O DTO não tem o nome do outro usuário, então usamos um placeholder
        holder.nome.text = "Corrida #${position + 1}"
        holder.dataHora.text = corrida.dataInicio?.let { formatDateTime(it) } ?: "Data não disponível"
        holder.status.text = corrida.status?.replaceFirstChar { it.titlecase(Locale.getDefault()) } ?: "Status desconhecido"
    }

    override fun getItemCount(): Int = lista.size

    fun updateCorridas(novasCorridas: List<CorridaDTOResponse>) {
        this.lista = novasCorridas
        notifyDataSetChanged()
    }

    // Formata a data para um formato mais amigável
    private fun formatDateTime(dateTimeString: String): String {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
            val outputFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
            inputFormat.parse(dateTimeString)?.let { outputFormat.format(it) } ?: dateTimeString
        } catch (e: Exception) {
            dateTimeString // Retorna a string original se o parse falhar
        }
    }
}