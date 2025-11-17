package com.example.routepiresfront.ui.comum.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.routepiresfront.R
import com.example.routepiresfront.data.model.Mensagem

class ChatAdapter(private val mensagens: List<Mensagem>) :
    RecyclerView.Adapter<ChatAdapter.MensagemViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return mensagens[position].tipo
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MensagemViewHolder {
        val layoutId = if (viewType == Mensagem.Companion.TIPO_ENVIADA) {
            R.layout.item_mensagem_enviada
        } else {
            R.layout.item_mensagem_recebida
        }
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return MensagemViewHolder(view)
    }

    override fun onBindViewHolder(holder: MensagemViewHolder, position: Int) {
        val mensagem = mensagens[position]
        holder.textoMensagem.text = mensagem.texto
        holder.horaMensagem.text = mensagem.hora
    }

    override fun getItemCount() = mensagens.size

    class MensagemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textoMensagem: TextView = view.findViewById(R.id.texto_mensagem)
        val horaMensagem: TextView = view.findViewById(R.id.hora_mensagem)
    }
}