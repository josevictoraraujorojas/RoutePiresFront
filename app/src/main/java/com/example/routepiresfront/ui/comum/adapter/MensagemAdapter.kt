package com.example.routepiresfront.ui.comum.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.routepiresfront.R
import com.example.routepiresfront.model.Mensagem

class MensagensAdapter(
    private val mensagens: List<Mensagem>
) : RecyclerView.Adapter<MensagensAdapter.MensagemViewHolder>() {

    // 1. ViewHolder genérico
    class MensagemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val textoMensagem: TextView = view.findViewById(R.id.texto_mensagem)
        private val horaMensagem: TextView = view.findViewById(R.id.hora_mensagem)

        fun bind(mensagem: Mensagem) {
            textoMensagem.text = mensagem.texto
            horaMensagem.text = mensagem.hora
        }
    }

    // 2. Decide qual layout usar com base no tipo
    override fun getItemViewType(position: Int): Int {
        return mensagens[position].tipo
    }

    // 3. Cria o ViewHolder correto com base no tipo de mensagem
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MensagemViewHolder {
        val layoutId = if (viewType == Mensagem.TIPO_ENVIADA) {
            R.layout.item_mensagem_enviada
        } else {
            R.layout.item_mensagem_recebida
        }
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return MensagemViewHolder(view)
    }

    // 4. Associa os dados à view
    override fun onBindViewHolder(holder: MensagemViewHolder, position: Int) {
        holder.bind(mensagens[position])
    }

    override fun getItemCount() = mensagens.size
}
