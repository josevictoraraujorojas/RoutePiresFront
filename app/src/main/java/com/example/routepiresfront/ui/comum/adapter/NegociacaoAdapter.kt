package com.example.routepiresfront.ui.comum.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.routepiresfront.R
import com.example.routepiresfront.model.Negociacao

/**
 * Adaptador responsavel por vincular os itens de negociacao ao RecyclerView.
 * Recebe um callback que será chamado ao clicar em um item.
 */
class NegociacaoAdapter(
    private val negociacoes: List<Negociacao>,
    private val onItemClick: (Negociacao) -> Unit // callback de clique
) : RecyclerView.Adapter<NegociacaoAdapter.ItemNegociacaoViewHolder>() {

    class ItemNegociacaoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imagemAvatar: ImageView = itemView.findViewById(R.id.imageAvatar)
        val textoNome: TextView = itemView.findViewById(R.id.textNome)
        val textoMensagem: TextView = itemView.findViewById(R.id.textMensagem)
        val seloQuantidade: TextView = itemView.findViewById(R.id.textBadge)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemNegociacaoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_negociacao, parent, false)
        return ItemNegociacaoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemNegociacaoViewHolder, position: Int) {
        val negociacao = negociacoes[position]
        holder.textoNome.text = negociacao.nome
        holder.textoMensagem.text = negociacao.mensagem
        holder.imagemAvatar.setImageResource(R.drawable.ic_avatar_placeholder)

        holder.seloQuantidade.isVisible = negociacao.quantidadeNaoLida > 0
        if (negociacao.quantidadeNaoLida > 0) {
            holder.seloQuantidade.text = negociacao.quantidadeNaoLida.toString()
        }

        // Clique no item chama o callback
        holder.itemView.setOnClickListener {
            onItemClick(negociacao)
        }
    }

    override fun getItemCount(): Int = negociacoes.size
}
