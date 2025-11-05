package com.example.routepiresfront

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.routepiresfront.databinding.FragmentPerfilVeiculoOuPlacaBinding

class PerfilVeiculoOuPlacaFragment : Fragment() {

    private var _binding: FragmentPerfilVeiculoOuPlacaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPerfilVeiculoOuPlacaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonAtualizarPerfil.setOnClickListener {
            // TODO: Implementar lógica de atualização
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding.buttonCancelarPerfil.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding.btnVoltar.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
