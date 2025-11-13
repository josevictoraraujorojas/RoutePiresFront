package com.example.routepiresfront.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.routepiresfront.R

// 1. Modelo de dados para a mensagem
data class Mensagem(
    val texto: String,
    val hora: String,
    val senderId: String // ID de quem enviou
)

class MensagensAdapter(
    private val mensagens: List<Mensagem>,
    private val currentUserId: String // ID do usuário atual
) : RecyclerView.Adapter<MensagensAdapter.MensagemViewHolder>() {

    // 2. Constantes para os tipos de view
    private const val TIPO_MENSAGEM_ENVIADA = 1
    private const val TIPO_MENSAGEM_RECEBIDA = 2

    // 3. ViewHolder genérico
    class MensagemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val textoMensagem: TextView = view.findViewById(R.id.texto_mensagem)
        private val horaMensagem: TextView = view.findViewById(R.id.hora_mensagem)

        fun bind(mensagem: Mensagem) {
            textoMensagem.text = mensagem.texto
            horaMensagem.text = mensagem.hora
        }
    }

    // 4. Decide qual layout usar (enviado ou recebido)
    override fun getItemViewType(position: Int): Int {
        return if (mensagens[position].senderId == currentUserId) {
            TIPO_MENSAGEM_ENVIADA
        } else {
            TIPO_MENSAGEM_RECEBIDA
        }
    }

    // 5. Cria o ViewHolder correto com base no tipo de view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MensagemViewHolder {
        val layoutId = if (viewType == TIPO_MENSAGEM_ENVIADA) {
            R.layout.item_mensagem_enviada
        } else {
            R.layout.item_mensagem_recebida
        }
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return MensagemViewHolder(view)
    }

    // 6. Associa os dados à view
    override fun onBindViewHolder(holder: MensagemViewHolder, position: Int) {
        holder.bind(mensagens[position])
    }

    override fun getItemCount() = mensagens.size
}