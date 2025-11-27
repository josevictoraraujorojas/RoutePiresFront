package com.example.routepiresfront.ui.passageiro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.routepiresfront.R
import com.example.routepiresfront.databinding.FragmentBuscaMotoristaBinding
import com.example.routepiresfront.core.Resultado
import com.example.routepiresfront.ui.passageiro.adapter.MototaxistaAdapter
import com.example.routepiresfront.ui.passageiro.viewmodel.EscolhaMototaxistaViewModel
import com.google.android.material.snackbar.Snackbar

class BuscaMotoristaFragment : Fragment() {

    private var _binding: FragmentBuscaMotoristaBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EscolhaMototaxistaViewModel by viewModels()

    private lateinit var adapter: MototaxistaAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBuscaMotoristaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = MototaxistaAdapter(emptyList()) { mototaxista ->
            val id = mototaxista.id
            if (id.isNullOrBlank()) {
                Snackbar.make(binding.root, "Id do mototaxista não encontrado", Snackbar.LENGTH_SHORT)
                    .show()
                return@MototaxistaAdapter
            }
            EscolhaMototaxistaFragment.newInstance(
                id,
                mototaxista.nome,
                mototaxista.avaliacaoMedia ?: 0f
            ).show(parentFragmentManager, "EscolhaMototaxistaFragment")
        }

        binding.recyclerViewMototaxistas.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewMototaxistas.adapter = adapter

        startAnimations()
        viewModel.carregarMototaxistas()
        observarMototaxistas()

        binding.cancelButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun startAnimations() {
        if (_binding == null) return
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

    private fun observarMototaxistas() {
        viewModel.mototaxistas.observe(viewLifecycleOwner) { resultado ->
            when (resultado) {
                Resultado.Carregando -> {
                    binding.layoutAnimacoes.visibility = View.VISIBLE
                    binding.recyclerViewMototaxistas.visibility = View.GONE
                    startAnimations()
                }

                is Resultado.Sucesso -> {
                    stopAnimations()
                    binding.layoutAnimacoes.visibility = View.GONE
                    binding.recyclerViewMototaxistas.visibility = View.VISIBLE
                    adapter.submitList(resultado.dado)

                    if (resultado.dado.isEmpty()) {
                        Snackbar.make(binding.root, "Nenhum mototaxista disponível no momento", Snackbar.LENGTH_LONG).show()
                    }
                }

                is Resultado.Erro -> {
                    stopAnimations()
                    binding.layoutAnimacoes.visibility = View.GONE
                    Snackbar.make(binding.root, resultado.mensagem, Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        stopAnimations()
        _binding = null
    }
}
