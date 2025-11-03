package com.example.routepiresfront

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.routepiresfront.databinding.FragmentSegundoCadastroMototaxistaBinding

class SegundoCadastroMototaxista : Fragment() {

    private var _binding: FragmentSegundoCadastroMototaxistaBinding? = null

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSegundoCadastroMototaxistaBinding.inflate(inflater, container, false)
        return binding.root
    }

    // Configura as interações da view após ela ser criada.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener {
            (activity as? CadastroMototaxistaActivity)?.navigateToThirdStep()
        }
    }

    // Limpa a referência do binding quando a view é destruída para evitar vazamentos de memória.
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}