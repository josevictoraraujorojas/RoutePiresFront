package com.example.routepiresfront.ui.comum

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.routepiresfront.ui.comum.adapter.NotificacaoAdapter
import com.example.routepiresfront.databinding.FragmentNotificacaoBinding

class NotificacaoFragment : Fragment() {

    private lateinit var recyclerNotificacao: RecyclerView
    private lateinit var adapter: NotificacaoAdapter
    private lateinit var binding: FragmentNotificacaoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotificacaoBinding.inflate(inflater, container, false)

        recyclerNotificacao = binding.recyclerCorridas

        binding.btnVoltar.setOnClickListener {
            findNavController().navigateUp()
        }

        val listaNotificacao = listOf(
            Notificacao("João", "20/03/2025 08:30"),
            Notificacao("Jose", "19/03/2025 08:20"),
            Notificacao("Rodrigo", "14/03/2025 10:30"),
            Notificacao("Otavio", "20/03/2025"),
            Notificacao("Luan", "20/03/2025")
        )

        adapter = NotificacaoAdapter(listaNotificacao)
        recyclerNotificacao.layoutManager = LinearLayoutManager(requireContext())
        recyclerNotificacao.adapter = adapter

        return binding.root
    }
}