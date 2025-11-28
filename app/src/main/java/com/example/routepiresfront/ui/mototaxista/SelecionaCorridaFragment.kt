package com.example.routepiresfront.ui.mototaxista

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import com.example.routepiresfront.R
import com.example.routepiresfront.databinding.FragmentSelecionaCorridaBinding
import com.example.routepiresfront.ui.mototaxista.viewmodel.CorridaMototaxistaViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class SelecionaCorridaFragment : Fragment() {

    private var _binding: FragmentSelecionaCorridaBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CorridaMototaxistaViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelecionaCorridaBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
        setupListeners()
    }

    private fun setupObservers() {
        // Observa a corrida atual selecionada
        viewModel.corridaAtual.observe(viewLifecycleOwner) { corrida ->
            corrida?.let {
                binding.corrida = it
            }
        }

        viewModel.navegarParaNegociacao.observe(viewLifecycleOwner) {
            navegarParaNegociacao()
            viewModel.resetarNavegacao()
        }

        // Observa erros
        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
                viewModel.limparErro()
            }
        }

        // Observa loading
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.btnIniciarNegociacao.isEnabled = !isLoading
            binding.btnCancelarNegociacao.isEnabled = !isLoading
        }
    }

    private fun setupListeners() {
        binding.btnIniciarNegociacao.setOnClickListener {
            viewModel.aceitarCorrida()
        }

        binding.btnCancelarNegociacao.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun navegarParaNegociacao() {
        val bottom = requireActivity().findViewById<BottomNavigationView>(R.id.menuInferior)
        bottom.selectedItemId = R.id.bottom_negociacao

        val navHostFragment = requireActivity()
            .supportFragmentManager
            .findFragmentById(R.id.nav_host_negociacao_moto) as? NavHostFragment

        navHostFragment?.navController?.navigate(R.id.chatFragment2)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
