package com.example.routepiresfront.ui.mototaxista

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.routepiresfront.R
import com.example.routepiresfront.databinding.FragmentSelecionaCorridaBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class SelecionaCorridaFragment : Fragment() {

    private var _binding: FragmentSelecionaCorridaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelecionaCorridaBinding.inflate(inflater, container, false)

        // Botão cancelar
        binding.btnCancelarNegociacao.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        // Botão iniciar negociação
        binding.btnIniciarNegociacao.setOnClickListener {
            // 1️⃣ Troca para a aba "Negociação" (ou Chat)
            val bottom = requireActivity().findViewById<BottomNavigationView>(R.id.menuInferior)
            bottom.selectedItemId = R.id.bottom_negociacao // ID da aba de negociação

            // 2️⃣ Pega o NavController do NavHost da aba de negociação
            val navController = requireActivity()
                .supportFragmentManager
                .findFragmentById(R.id.nav_host_negociacao_moto)  // <- NavHost da aba de negociação
                ?.findNavController()

            // 3️⃣ Navega para o ChatFragment2 dentro do NavHost da aba
            navController?.navigate(R.id.chatFragment2)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
