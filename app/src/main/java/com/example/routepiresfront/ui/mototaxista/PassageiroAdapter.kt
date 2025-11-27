package com.example.routepiresfront.ui.mototaxista

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.routepiresfront.databinding.ItemPassageiroBinding

// Modelo simples de exemplo (você pode adaptar para o seu modelo real)
data class Passageiro(
    val nome: String,
    val nota: Float,
    val imagemRes: Int,
    val tipo: String // "corrida" ou "entrega"
)

class PassageiroAdapter(
    private val passageiros: List<Passageiro>,
    private val onItemClick: (Passageiro) -> Unit
) : RecyclerView.Adapter<PassageiroAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemPassageiroBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(passageiro: Passageiro) {
            binding.txtNome.text = passageiro.nome
            binding.ratingAvaliacao.rating = passageiro.nota
            binding.imgPerfil.setImageResource(passageiro.imagemRes)

            // Clique no item
            binding.root.setOnClickListener {
                onItemClick(passageiro)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPassageiroBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(passageiros[position])
    }

    override fun getItemCount(): Int = passageiros.size
}
