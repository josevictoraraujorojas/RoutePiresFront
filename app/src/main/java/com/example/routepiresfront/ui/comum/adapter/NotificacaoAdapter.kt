package com.example.routepiresfront.ui.comum.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.routepiresfront.R
import com.example.routepiresfront.data.model.NotificacaoDTOResponse
import java.text.SimpleDateFormat
import java.util.Locale

class NotificacaoAdapter(private var lista: List<NotificacaoDTOResponse>) :
    RecyclerView.Adapter<NotificacaoAdapter.NotificacaoViewHolder>() {

    // ViewHolder interno para o item da notificação
    class NotificacaoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titulo: TextView = view.findViewById(R.id.txtNome) // Reutilizando o ID do layout do item
        val dataHora: TextView = view.findViewById(R.id.txtDataHora)
        val icone: ImageView = view.findViewById(R.id.imgMensagem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificacaoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_notificacao, parent, false)
        return NotificacaoViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotificacaoViewHolder, position: Int) {
        val notificacao = lista[position]
        holder.titulo.text = notificacao.titulo ?: "Notificação sem título"
        holder.dataHora.text = notificacao.dataCriacao?.let { formatDateTime(it) } ?: "Data indisponível"
        // Poderíamos mudar o ícone com base no tipo de notificação no futuro
    }

    override fun getItemCount(): Int = lista.size

    // Método para atualizar a lista de notificações e notificar o RecyclerView
    fun updateNotificacoes(novasNotificacoes: List<NotificacaoDTOResponse>) {
        this.lista = novasNotificacoes
        notifyDataSetChanged()
    }

    // Função para formatar a data
    private fun formatDateTime(dateTimeString: String): String {
        return try {
            // Tenta o formato com milissegundos e timezone
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.getDefault())
            val outputFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
            inputFormat.parse(dateTimeString)?.let { outputFormat.format(it) } ?: dateTimeString
        } catch (e: Exception) {
            try {
                // Tenta um formato mais simples sem milissegundos
                val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
                val outputFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                inputFormat.parse(dateTimeString)?.let { outputFormat.format(it) } ?: dateTimeString
            } catch (e2: Exception) {
                dateTimeString // Retorna a string original se ambos os parses falharem
            }
        }
    }
}
