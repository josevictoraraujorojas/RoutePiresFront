package com.example.routepiresfront.ui.mototaxista

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.routepiresfront.R
import com.example.routepiresfront.databinding.FragmentListaCorridasBinding
import com.example.routepiresfront.ui.mototaxista.adapter.CorridaAdapter
import com.example.routepiresfront.ui.mototaxista.viewmodel.CorridaMototaxistaViewModel

class ListaCorridasFragment : Fragment() {

    private var _binding: FragmentListaCorridasBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CorridaMototaxistaViewModel by activityViewModels()

    private lateinit var corridaAdapter: CorridaAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListaCorridasBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupObservers()

        viewModel.carregarCorridasDisponiveis()
    }

    private fun setupRecyclerView() {
        corridaAdapter = CorridaAdapter { corrida ->
            viewModel.selecionarCorrida(corrida)

            val navController = parentFragment?.childFragmentManager
                ?.findFragmentById(R.id.bottomSheetContainer)
                ?.findNavController()

            val destinationId = when (corrida.tipo) {
                "corrida" -> R.id.selecionaCorridaFragment
                "entrega" -> R.id.selecionaEntregaFragment
                else -> null
            }

            destinationId?.let {
                navController?.navigate(it)
            }
        }

        binding.recyclerPassageiros.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = corridaAdapter
        }
    }

    private fun setupObservers() {
        viewModel.corridasDisponiveis.observe(viewLifecycleOwner) { corridas ->
            corridaAdapter.submitList(corridas)

            if (corridas.isEmpty()) {
                binding.tvListaVazia.visibility = View.VISIBLE
                binding.recyclerPassageiros.visibility = View.GONE
            } else {
                binding.tvListaVazia.visibility = View.GONE
                binding.recyclerPassageiros.visibility = View.VISIBLE
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
                viewModel.limparErro()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
