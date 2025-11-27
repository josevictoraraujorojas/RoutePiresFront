package com.example.routepiresfront.ui.comum

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.routepiresfront.databinding.FragmentAvaliacaoBinding
import com.example.routepiresfront.repository.AvaliacaoRepository
import com.example.routepiresfront.viewmodel.AvaliacaoViewModel
import com.example.routepiresfront.viewmodel.AvaliacaoViewModelFactory

class AvaliacaoFragment : Fragment() {

    private var _binding: FragmentAvaliacaoBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AvaliacaoViewModel by viewModels {
        AvaliacaoViewModelFactory(AvaliacaoRepository())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAvaliacaoBinding.inflate(inflater, container, false)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        observarViewModel()

        // 🔥 ATUALIZA O VIEWMODEL QUANDO O USUÁRIO TOCAR NO RATING
        binding.ratingBar.setOnRatingBarChangeListener { _, rating, _ ->
            viewModel.nota.value = rating.toInt()
        }

        binding.btnVoltar.setOnClickListener {
        }

        return binding.root
    }


    private fun observarViewModel() {
        viewModel.sucesso.observe(viewLifecycleOwner) { sucesso ->
            if (sucesso == true) {
                Toast.makeText(requireContext(), "Avaliação enviada!", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.erro.observe(viewLifecycleOwner) { erro ->
            erro?.let {
                Toast.makeText(requireContext(), erro, Toast.LENGTH_LONG).show()
            }
        }

        viewModel.loading.observe(viewLifecycleOwner) { carregando ->
            binding.btnEnviar.isEnabled = !carregando
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}