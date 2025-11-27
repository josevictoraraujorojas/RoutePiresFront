package com.example.routepiresfront.ui.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.routepiresfront.R
import com.example.routepiresfront.data.model.MensagemDTOResponse
import com.example.routepiresfront.databinding.ItemMensagemEnviadaBinding
import com.example.routepiresfront.databinding.ItemMensagemRecebidaBinding
import java.text.SimpleDateFormat
import java.util.*

class ChatAdapter(
    private val userIdLogado: String
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val mensagens = mutableListOf<MensagemDTOResponse>()

    companion object {
        private const val TIPO_ENVIADA = 1
        private const val TIPO_RECEBIDA = 2
    }

    fun atualizarMensagens(lista: List<MensagemDTOResponse>) {
        mensagens.clear()
        mensagens.addAll(lista)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        val msg = mensagens[position]

        return if (msg.remetente == userIdLogado) {
            TIPO_ENVIADA
        } else {
            TIPO_RECEBIDA
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            TIPO_ENVIADA -> {
                val binding = ItemMensagemEnviadaBinding.inflate(inflater, parent, false)
                MensagemEnviadaViewHolder(binding)
            }

            else -> {
                val binding = ItemMensagemRecebidaBinding.inflate(inflater, parent, false)
                MensagemRecebidaViewHolder(binding)
            }
        }
    }

    override fun getItemCount(): Int = mensagens.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val msg = mensagens[position]

        when (holder) {
            is MensagemEnviadaViewHolder -> holder.bind(msg)
            is MensagemRecebidaViewHolder -> holder.bind(msg)
        }
    }

    class MensagemEnviadaViewHolder(
        private val binding: ItemMensagemEnviadaBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(msg: MensagemDTOResponse) {
            binding.textoMensagem.text = msg.conteudo
            binding.horaMensagem.text = formatarHora(msg.horarioEnvio)
        }
    }

    class MensagemRecebidaViewHolder(
        private val binding: ItemMensagemRecebidaBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(msg: MensagemDTOResponse) {
            binding.textoMensagem.text = msg.conteudo
            binding.horaMensagem.text = formatarHora(msg.horarioEnvio)
        }
    }

}

/**
 * Formata Date → HH:mm
 */
private fun formatarHora(date: Date?): String {
    if (date == null) return ""
    val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
    return sdf.format(date)
}
