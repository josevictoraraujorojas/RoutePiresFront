package com.example.routepiresfront

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.routepiresfront.databinding.ItemListaLocaisBinding

class LocalSugestaoAdapter(
    private val fullList: List<String>,
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<LocalSugestaoAdapter.ViewHolder>() {

    private val filteredList = fullList.toMutableList()

    inner class ViewHolder(val binding: ItemListaLocaisBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemListaLocaisBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = filteredList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val local = filteredList[position]

        holder.binding.txtLocal.text = local
        holder.binding.iconLocal.setImageResource(R.drawable.ic_local) // usa seu ícone

        holder.itemView.setOnClickListener {
            onItemClick(local)
        }
    }

    fun filter(query: String) {
        filteredList.clear()

        filteredList.addAll(
            fullList.filter { it.contains(query, ignoreCase = true) }
        )

        notifyDataSetChanged()
    }
}
