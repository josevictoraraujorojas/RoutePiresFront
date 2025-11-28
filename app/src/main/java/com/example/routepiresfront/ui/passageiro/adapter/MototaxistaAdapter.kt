package com.example.routepiresfront.ui.passageiro.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.routepiresfront.R
import com.example.routepiresfront.data.model.passageiro.MototaxistaResumo
import com.example.routepiresfront.databinding.ItemMototaxistaBinding

class MototaxistaAdapter(
    private var lista: List<MototaxistaResumo>,
    private val onItemClick: (MototaxistaResumo) -> Unit
) : RecyclerView.Adapter<MototaxistaAdapter.MototaxistaViewHolder>() {

    inner class MototaxistaViewHolder(val binding: ItemMototaxistaBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MototaxistaViewHolder {
        val binding =
            ItemMototaxistaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MototaxistaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MototaxistaViewHolder, position: Int) {
        val mototaxista = lista[position]

        holder.binding.txtNome.text = mototaxista.nome ?: "Mototaxista"
        holder.binding.ratingAvaliacao.rating = mototaxista.avaliacaoMedia ?: 0f
        holder.binding.imgPerfil.setImageResource(R.drawable.ic_user_avatar)

        holder.itemView.setOnClickListener { onItemClick(mototaxista) }
    }

    override fun getItemCount() = lista.size

    fun submitList(novaLista: List<MototaxistaResumo>) {
        lista = novaLista
        notifyDataSetChanged()
    }
}
