package com.example.routepiresfront.ui.passageiro.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.routepiresfront.ui.passageiro.EscolhaMototaxistaFragment
import com.example.routepiresfront.databinding.ItemMototaxistaBinding
import com.example.routepiresfront.model.Mototaxista

class MototaxistaAdapter(private val lista: List<Mototaxista>) :
    RecyclerView.Adapter<MototaxistaAdapter.MototaxistaViewHolder>() {

    inner class MototaxistaViewHolder(val binding: ItemMototaxistaBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MototaxistaViewHolder {
        val binding = ItemMototaxistaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MototaxistaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MototaxistaViewHolder, position: Int) {
        val mototaxista = lista[position]

        // Preenche os dados do item
        holder.binding.txtNome.text = mototaxista.nome
        holder.binding.ratingAvaliacao.rating = mototaxista.avaliacao
        holder.binding.imgPerfil.setImageResource(mototaxista.imagemRes)

        // 🔹 Ao clicar no item, abre o pop-up do fragmento
        holder.itemView.setOnClickListener {
            val activity = holder.itemView.context as AppCompatActivity
            val fragment = EscolhaMototaxistaFragment.newInstance(
                mototaxista.nome,
                mototaxista.avaliacao
            )
            fragment.show(activity.supportFragmentManager, "EscolhaMototaxistaFragment")
        }
    }

    override fun getItemCount() = lista.size
}
