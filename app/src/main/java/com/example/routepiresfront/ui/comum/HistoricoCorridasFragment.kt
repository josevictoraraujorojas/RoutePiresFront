package com.example.routepiresfront.ui.comum

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.routepiresfront.databinding.FragmentHistoricoCorridasBinding
import com.example.routepiresfront.ui.comum.adapter.CorridaAdapter
import com.example.routepiresfront.viewModel.MototaxistaPerfilViewModel

class HistoricoCorridasFragment : Fragment() {

    private var _binding: FragmentHistoricoCorridasBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MototaxistaPerfilViewModel by activityViewModels()
    private var userId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userId = arguments?.getString("USER_ID") ?: activity?.intent?.getStringExtra("USER_ID")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHistoricoCorridasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = CorridaAdapter(emptyList()) // Começa com a lista vazia
        binding.recyclerCorridas.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerCorridas.adapter = adapter

        binding.btnVoltar.setOnClickListener {
            findNavController().navigateUp()
        }

        // Observa o histórico de corridas e atualiza o adapter
        viewModel.historico.observe(viewLifecycleOwner) { corridas ->
            adapter.updateCorridas(corridas) // O Adapter precisa de um método para atualizar a lista
        }

        // Carrega o histórico de corridas usando o ID do usuário
        userId?.let {
            viewModel.carregarHistorico(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
