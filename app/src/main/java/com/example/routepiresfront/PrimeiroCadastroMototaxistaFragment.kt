package com.example.routepiresfront

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.routepiresfront.databinding.FragmentCadastroMototaxista1Binding


class PrimeiroCadastroMototaxistaFragment : Fragment() {

    private var _binding: FragmentCadastroMototaxista1Binding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCadastroMototaxista1Binding.inflate(inflater, container, false)
        return binding.root
    }

    // Configura as interações da view após ela ser criada.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener {
            // Navega para o próximo passo do cadastro.
            (activity as? MainActivity)?.navigateToSecondStep()
        }
    }

    // Limpa a referência do binding quando a view é destruída para evitar vazamentos de memória.
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}