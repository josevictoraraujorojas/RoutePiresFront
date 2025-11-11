package com.example.routepiresfront.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.routepiresfront.R

class ChatAdapter(
    private val mensagens: List<Mensagem>,
    private val idUsuarioAtual: String
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // Constantes para identificar os tipos de view
    private const val TIPO_MENSAGEM_ENVIADA = 1
    private const val TIPO_MENSAGEM_RECEBIDA = 2

    // ViewHolder para mensagens ENVIADAS
    inner class EnviadaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Encontra o TextView no layout item_mensagem_enviada.xml
        private val textoMensagem: TextView = itemView.findViewById(R.id.texto_mensagem)

        fun bind(mensagem: Mensagem) {
            textoMensagem.text = mensagem.texto
        }
    }

    // ViewHolder para mensagens RECEBIDAS
    inner class RecebidaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Encontra o TextView no layout item_mensagem_recebida.xml
        private val textoMensagem: TextView = itemView.findViewById(R.id.texto_mensagem)

        fun bind(mensagem: Mensagem) {
            textoMensagem.text = mensagem.texto
        }
    }

    // Este método decide qual layout usar (enviado vs. recebido)
    override fun getItemViewType(position: Int): Int {
        val mensagem = mensagens[position]
        return if (mensagem.remetenteId == idUsuarioAtual) {
            TIPO_MENSAGEM_ENVIADA
        } else {
            TIPO_MENSAGEM_RECEBIDA
        }
    }

    // Este método cria o ViewHolder correto com base no tipo de view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TIPO_MENSAGEM_ENVIADA) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_mensagem_enviada, parent, false)
            EnviadaViewHolder(view)
        } else { // TIPO_MENSAGEM_RECEBIDA
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_mensagem_recebida, parent, false)
            RecebidaViewHolder(view)
        }
    }

    // Este método vincula os dados (a mensagem) ao ViewHolder
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val mensagem = mensagens[position]
        when (holder) {
            is EnviadaViewHolder -> holder.bind(mensagem)
            is RecebidaViewHolder -> holder.bind(mensagem)
        }
    }

    // Retorna o número total de itens na lista
    override fun getItemCount() = mensagens.size
}