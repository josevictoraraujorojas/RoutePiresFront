package com.example.routepiresfront.ui.comum.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.routepiresfront.data.model.Corrida
import com.example.routepiresfront.databinding.ItemCorridaBinding
import java.text.SimpleDateFormat
import java.util.*


class CorridaAdapter(private val lista: List<Corrida>) :
    RecyclerView.Adapter<CorridaAdapter.CorridaViewHolder>() {

    class CorridaViewHolder(val binding: ItemCorridaBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CorridaViewHolder {
        val binding = ItemCorridaBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CorridaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CorridaViewHolder, position: Int) {
        val corrida = lista[position]
        holder.binding.apply {
            tvNomePassageiro.text = corrida.passageiro?.nome ?: "Passageiro"

            tvAvaliacaoPassageiro.text = String.format("%.1f", corrida.passageiro?.avaliacao ?: 0f)

            tvTipoServico.text = if (corrida.tipo == "entrega") "Entrega" else "Corrida"

            corrida.valorEstimado?.let {
                tvValorEstimado.text = String.format("R$ %.2f", it)
            }

            corrida.distanciaKm?.let {
                tvDistancia.text = String.format("%.1f km", it)
            }

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

            tvEnderecoOrigem.text = corrida.pontoPartida?.endereco ?: "Origem"
            tvEnderecoDestino.text = corrida.pontoDestino?.endereco ?: "Destino"

            val numParadas = corrida.pontosIntermediarios?.size ?: 0
            tvParadas.text = if (numParadas > 0) "$numParadas parada${if (numParadas > 1) "s" else ""}" else "Sem paradas"
        }
    }

    override fun getItemCount(): Int = lista.size
}

