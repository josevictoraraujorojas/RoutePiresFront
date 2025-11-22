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
import com.example.routepiresfront.viewModel.PassageiroPerfilViewModel
import android.util.Log

class HistoricoCorridasFragment : Fragment() {

    private var _binding: FragmentHistoricoCorridasBinding? = null
    private val binding get() = _binding!!

    // Observamos apenas o ViewModel do passageiro enquanto o endpoint do mototaxista estiver indisponível
    private val passViewModel: PassageiroPerfilViewModel by activityViewModels()
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

            // Observa somente o ViewModel do passageiro enquanto o endpoint do mototaxista estiver indisponível
            fun handleReceivedList(corridas: List<com.example.routepiresfront.data.model.CorridaDTOResponse>?) {
                val size = corridas?.size ?: 0
                Log.d("HistoricoFragment", "corridas passageiro recebidas: size=$size")
                if (corridas == null || corridas.isEmpty()) {
                    binding.tvEmptyHistorico.visibility = View.VISIBLE
                    binding.recyclerCorridas.visibility = View.GONE
                } else {
                    binding.tvEmptyHistorico.visibility = View.GONE
                    binding.recyclerCorridas.visibility = View.VISIBLE
                    adapter.updateCorridas(corridas)
                }
            }

            passViewModel.historico.observe(viewLifecycleOwner) { corridas ->
                Log.d("HistoricoFragment", "corridas passageiro emitidas: size=${corridas?.size}")
                handleReceivedList(corridas)
            }

            passViewModel.error.observe(viewLifecycleOwner) { err ->
                err?.let {
                    Log.e("HistoricoFragment", "Erro passageiro ao obter histórico: $it")
                    try { android.widget.Toast.makeText(requireContext(), "Erro ao obter histórico: $it", android.widget.Toast.LENGTH_LONG).show() } catch (_: Exception) {}
                }
            }

            // Solicita carregamento apenas do histórico do passageiro (mototaxista temporariamente sem endpoint)
            userId?.let {
                Log.d("HistoricoFragment", "carregarHistorico passageiro para userId=$it")
                passViewModel.carregarHistorico(it)
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
