package com.example.routepiresfront.ui.mototaxista

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.routepiresfront.R
import com.example.routepiresfront.databinding.FragmentAguardandoInicioCorridaBinding
import com.example.routepiresfront.ui.mototaxista.viewmodel.CorridaMototaxistaViewModel

class AguardandoInicioCorridaFragment : Fragment() {

    private var _binding: FragmentAguardandoInicioCorridaBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CorridaMototaxistaViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAguardandoInicioCorridaBinding.inflate(inflater, container, false)

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
        viewModel.corridaAtual.observe(viewLifecycleOwner) { corrida ->
            corrida?.let {
                binding.corrida = it
            }
        }

        viewModel.navegarParaAndamento.observe(viewLifecycleOwner) {
            findNavController().navigate(
                R.id.action_aguardandoInicioCorridaFragment2_to_corridaAndamentoFragment
            )
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
                viewModel.clearErrorMessage()
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.btnIniciarCorrida.isEnabled = !isLoading
            binding.btnCancelarCorrida.isEnabled = !isLoading
        }

        viewModel.fecharFluxoCorrida.observe(viewLifecycleOwner) {
            parentFragmentManager.popBackStack()
        }
    }

    private fun setupListeners() {
        binding.btnIniciarCorrida.setOnClickListener {
            viewModel.iniciarCorrida()
        }
        binding.btnCancelarCorrida.setOnClickListener {
            viewModel.cancelarCorrida("Cancelado pelo mototaxista antes de iniciar")
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

