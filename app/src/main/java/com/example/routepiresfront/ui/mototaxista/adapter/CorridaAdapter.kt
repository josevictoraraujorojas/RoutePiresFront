package com.example.routepiresfront.ui.mototaxista.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.routepiresfront.R
import com.example.routepiresfront.data.model.Corrida
import com.example.routepiresfront.databinding.ItemCorridaBinding
import java.text.SimpleDateFormat
import java.util.*

class CorridaAdapter(
    private val onCorridaClick: (Corrida) -> Unit
) : ListAdapter<Corrida, CorridaAdapter.CorridaViewHolder>(CorridaDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CorridaViewHolder {
        val binding = ItemCorridaBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CorridaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CorridaViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CorridaViewHolder(
        private val binding: ItemCorridaBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(corrida: Corrida) {
            binding.apply {
                // Nome do passageiro
                tvNomePassageiro.text = corrida.passageiro?.nome ?: "Passageiro"

                // Avaliação
                tvAvaliacaoPassageiro.text = String.format("%.1f", corrida.passageiro?.avaliacao ?: 0f)

                // Tipo de serviço
                val tipo = if (corrida.tipo == "entrega") "Entrega" else "Corrida"
                tvTipoServico.text = tipo

                // Valor estimado
                corrida.valorEstimado?.let {
                    tvValorEstimado.text = String.format("R$ %.2f", it)
                }

                // Distância
                corrida.distanciaKm?.let {
                    tvDistancia.text = String.format("%.1f km", it)
                }

                // Data/hora
                corrida.dataHoraCriacao?.let {
                    try {
                        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
                        val outputFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
                        val date = inputFormat.parse(it)
                        tvHorario.text = date?.let { d -> outputFormat.format(d) } ?: it
                    } catch (e: Exception) {
                        tvHorario.text = it
                    }
                }

                // Endereços
                tvEnderecoOrigem.text = corrida.pontoPartida?.endereco ?: "Origem não informada"
                tvEnderecoDestino.text = corrida.pontoDestino?.endereco ?: "Destino não informado"

                // Número de paradas
                val numParadas = corrida.pontosIntermediarios?.size ?: 0
                if (numParadas > 0) {
                    tvParadas.text = "$numParadas parada${if (numParadas > 1) "s" else ""}"
                } else {
                    tvParadas.text = "Sem paradas"
                }

                // Click listener
                root.setOnClickListener {
                    onCorridaClick(corrida)
                }

                // Avatar placeholder
                // TODO: Carregar imagem do passageiro com Glide/Coil se tiver URL
                avatarPassageiro.setImageResource(R.drawable.ic_user_avatar)
            }
        }
    }

    private class CorridaDiffCallback : DiffUtil.ItemCallback<Corrida>() {
        override fun areItemsTheSame(oldItem: Corrida, newItem: Corrida): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Corrida, newItem: Corrida): Boolean {
            return oldItem == newItem
        }
    }
}

