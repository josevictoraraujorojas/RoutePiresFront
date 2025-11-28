package com.example.routepiresfront.ui.comum.adapter

import android.util.Log
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

class CorridaAdapter(
    private var lista: List<CorridaDTOResponse>,
    private val userType: String?
) : RecyclerView.Adapter<CorridaAdapter.CorridaViewHolder>() {

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
        // Debug: logar objeto recebido para inspecionar campos vazios
        Log.d("CorridaAdapter", "onBindViewHolder posição=$position corrida=$corrida")

        // O DTO não tem o nome do outro usuário, então usamos um placeholder
        holder.nome.text = "Corrida #${position + 1}"
        // Prioriza `dataHoraSolicitacao` (solicitação/início), senão `dataHoraFim`, `dataFim`, `dataInicio`
        val dateToShow = corrida.dataHoraSolicitacao ?: corrida.dataHoraFim ?: corrida.dataFim ?: corrida.dataInicio
        holder.dataHora.text = dateToShow?.let { formatDateTime(it) } ?: "Data indisponível"

        // Status: normaliza e aceita várias variações que o backend pode retornar
        val rawStatus = corrida.status
        val normalized = rawStatus
            ?.trim()
            ?.replace(Regex("[\\s-]+"), "_") // converte espaços/hífens para underscore
            ?.uppercase(Locale.ROOT)

        val statusText = when (normalized) {
            "FINALIZADO" -> "FINALIZADO"
            "PENDENTE" -> "PENDENTE"
            "ANDAMENTO" -> "ANDAMENTO"
            "CANCELADO" -> "CANCELADO"
            null, "", "NULL" -> "Status desconhecido"
            else -> {
                // Tenta capitalizar a primeira letra para exibir algo legível
                rawStatus.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
            }
        }
        holder.status.text = statusText
    }

    override fun getItemCount(): Int = lista.size

    fun updateCorridas(novasCorridas: List<CorridaDTOResponse>) {
        this.lista = novasCorridas
        Log.d("CorridaAdapter", "Atualizando corridas, nova contagem: ${novasCorridas.size}")
        notifyDataSetChanged()
    }

    private fun formatDateTime(dateTimeString: String): String {
        return try {
            // Patterns cobrindo ISO/offsets e formatos simples
            val patterns = listOf(
                "yyyy-MM-dd'T'HH:mm:ss.SSSX",
                "yyyy-MM-dd'T'HH:mm:ssX",
                "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                "yyyy-MM-dd'T'HH:mm:ss'Z'",
                "yyyy-MM-dd'T'HH:mm:ss",
                "yyyy-MM-dd HH:mm:ss"
            )

            var parsed: java.util.Date? = null
            for (p in patterns) {
                try {
                    val sdf = SimpleDateFormat(p, Locale.getDefault())
                    // Para padrões sem offset explícito assumimos UTC como origem
                    if (!p.contains("X") && !p.contains("'Z'")) {
                        sdf.timeZone = java.util.TimeZone.getTimeZone("UTC")
                    }
                    parsed = sdf.parse(dateTimeString)
                    if (parsed != null) break
                } catch (_: Exception) {
                }
            }

            val outputFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
            outputFormat.timeZone = java.util.TimeZone.getDefault()
            parsed?.let { outputFormat.format(it) } ?: dateTimeString
        } catch (e: Exception) {
            // Se tudo falhar, retorna a string original para evitar crash
            dateTimeString
        }
    }
}