package com.example.routepiresfront.ui.passageiro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.routepiresfront.R
import com.example.routepiresfront.data.model.Mototaxista
import com.example.routepiresfront.data.repository.CorridaPassageiroRepository
import com.example.routepiresfront.databinding.FragmentBuscaMotoristaBinding
import com.example.routepiresfront.ui.passageiro.adapter.MototaxistaAdapter
import com.example.routepiresfront.viewmodel.CorridaPassageiroViewModelFactory

class BuscaMotoristaFragment : Fragment() {

    private var _binding: FragmentBuscaMotoristaBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: CorridaPassageiroViewModel
    private var corridaId: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBuscaMotoristaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Recuperar o ID passado pela tela anterior
        corridaId = arguments?.getString("corrida_id")

        if (corridaId == null) {
            Toast.makeText(context, "Erro: ID da corrida não encontrado", Toast.LENGTH_SHORT).show()
            parentFragmentManager.popBackStack()
            return
        }

        // 2. Configurar ViewModel
        val repository = CorridaPassageiroRepository()
        val factory = CorridaPassageiroViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(CorridaPassageiroViewModel::class.java)

        startAnimations()

        // Inicia o monitoramento da corrida
        viewModel.iniciarMonitoramento(corridaId!!)

        setupObservers()
        setupListeners()
    }

    private fun setupListeners() {
        // Botão Cancelar
        binding.cancelButton.setOnClickListener {
            binding.cancelButton.text = "Cancelando..."
            binding.cancelButton.isEnabled = false
            viewModel.cancelarCorridaAtual(corridaId!!)
        }
    }

    private fun setupObservers() {
        // A. Observa se a corrida mudou de status (foi aceita)
        viewModel.statusCorrida.observe(viewLifecycleOwner) { corrida ->
            if (corrida != null && corrida.mototaxistaId != null) {
                binding.procurandoCorridaButton.text = "Motorista a caminho!"
                stopAnimations()
                // Nota: A lista só vai aparecer quando chegarem os 'dadosMotorista' no observer abaixo
            }
        }

        // B. Observa os dados detalhados do motorista (Nome, Placa, etc)
        viewModel.dadosMotorista.observe(viewLifecycleOwner) { motorista ->
            if (motorista != null) {
                mostrarResultadoReal(motorista)
            }
        }

        // C. Observa o sucesso do cancelamento
        viewModel.cancelamentoState.observe(viewLifecycleOwner) { cancelado ->
            if (cancelado) {
                Toast.makeText(context, "Corrida cancelada com sucesso.", Toast.LENGTH_SHORT).show()
                parentFragmentManager.popBackStack()
            } else {
                binding.cancelButton.text = "Cancelar"
                binding.cancelButton.isEnabled = true
                Toast.makeText(context, "Erro ao cancelar. Tente novamente.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun startAnimations() {
        val rotateAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_animation)
        binding.searchIcon.startAnimation(rotateAnimation)

        val pulseAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.pulse_animation)
        binding.procurandoCorridaButton.startAnimation(pulseAnimation)
    }

    private fun stopAnimations() {
        if (_binding != null) {
            binding.searchIcon.clearAnimation()
            binding.procurandoCorridaButton.clearAnimation()
        }
    }

    private fun mostrarResultadoReal(motorista: Mototaxista) {
        binding.layoutAnimacoes.visibility = View.GONE
        binding.recyclerViewMototaxistas.visibility = View.VISIBLE

        // Cria uma lista com o motorista recebido da API
        val lista = listOf(motorista)

        val adapter = MototaxistaAdapter(lista)
        binding.recyclerViewMototaxistas.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewMototaxistas.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.pararMonitoramento()
        stopAnimations()
        _binding = null
    }
}