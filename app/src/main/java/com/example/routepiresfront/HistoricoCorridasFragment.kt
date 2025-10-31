package com.example.routepiresfront

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.routepiresfront.databinding.FragmentHistoricoCorridasBinding

class HistoricoCorridasFragment : Fragment() {
    private lateinit var recyclerCorridas: RecyclerView
    private lateinit var adapter: CorridaAdapter

    private lateinit var binding: FragmentHistoricoCorridasBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoricoCorridasBinding.inflate(inflater, container, false)
        recyclerCorridas = binding.recyclerCorridas
        val btnVoltar = binding.btnVoltar

        btnVoltar.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        val listaCorridas = listOf(
            Corrida("João", "20/03/2025 08:30", "Cancelado"),
            Corrida("Jose", "19/03/2025 08:20", "Finalizado"),
            Corrida("Rodrigo", "14/03/2025 10:30", "Finalizado"),
            Corrida("Otavio", "20/03/2025", "Cancelado"),
            Corrida("Luan", "20/03/2025", "Cancelado")
        )

        adapter = CorridaAdapter(listaCorridas)
        recyclerCorridas.layoutManager = LinearLayoutManager(requireContext())
        recyclerCorridas.adapter = adapter

        return binding.root
    }
}
