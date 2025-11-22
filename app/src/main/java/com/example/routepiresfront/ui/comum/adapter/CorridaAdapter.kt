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
        // Prioriza `dataHoraFim` (campo do BD), senão usa `dataFim`, senão `dataInicio`
        val dateToShow = corrida.dataHoraFim ?: corrida.dataFim ?: corrida.dataInicio
        holder.dataHora.text = dateToShow?.let { formatDateTime(it) } ?: "Data não disponível"
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
            // Aceita várias variações ISO (com ou sem millis, com Z, etc.)
            val patterns = listOf(
                "yyyy-MM-dd'T'HH:mm:ss'Z'",
                "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                "yyyy-MM-dd'T'HH:mm:ss",
                "yyyy-MM-dd HH:mm:ss"
            )
            var parsed: java.util.Date? = null
            for (p in patterns) {
                try {
                    val sdf = SimpleDateFormat(p, Locale.getDefault())
                    sdf.timeZone = java.util.TimeZone.getTimeZone("UTC") // Define UTC para o parser
                    parsed = sdf.parse(dateTimeString)
                    if (parsed != null) break
                } catch (_: Exception) {}
            }
            val outputFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
            outputFormat.timeZone = java.util.TimeZone.getDefault() // Converte para o fuso horário local
            parsed?.let { outputFormat.format(it) } ?: dateTimeString
        } catch (e: Exception) {
            dateTimeString // Retorna a string original se o parse falhar
        }
    }
}