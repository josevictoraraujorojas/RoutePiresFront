package com.example.routepiresfront

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.routepiresfront.databinding.ItemMototaxistaBinding

/**
 * Adapter simples para a lista da busca de mototaxistas.
 * Mantém um buffer interno para permitir atualizações rápidas.
 */
class MototaxistaAdapter :
    RecyclerView.Adapter<MototaxistaAdapter.MototaxistaViewHolder>() {

    private val itens = mutableListOf<Mototaxista>()

    fun submitList(novosItens: List<Mototaxista>) {
        // Estratégia simples de substituição total; futura otimização pode usar DiffUtil.
        itens.clear()
        itens.addAll(novosItens)
        notifyDataSetChanged()
    }

    inner class MototaxistaViewHolder(val binding: ItemMototaxistaBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MototaxistaViewHolder {
        val binding = ItemMototaxistaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MototaxistaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MototaxistaViewHolder, position: Int) {
        val mototaxista = itens[position]

        // Preenche os dados do item
        holder.binding.txtNome.text = mototaxista.nome
        holder.binding.ratingAvaliacao.rating = mototaxista.avaliacao
        holder.binding.imgPerfil.setImageResource(mototaxista.imagemRes)

        // Ao clicar abrimos o popup de negociação com os dados do mototaxista.
        holder.itemView.setOnClickListener {
            val activity = holder.itemView.context as AppCompatActivity
            val fragment = EscolhaMototaxistaFragment.newInstance(
                mototaxista.nome,
                mototaxista.avaliacao
            )
            fragment.show(activity.supportFragmentManager, "EscolhaMototaxistaFragment")
        }
    }

    override fun getItemCount() = itens.size
}
