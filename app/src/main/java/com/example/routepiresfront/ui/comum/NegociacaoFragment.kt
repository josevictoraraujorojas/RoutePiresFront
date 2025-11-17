package com.example.routepiresfront.ui.comum

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.routepiresfront.R
import com.example.routepiresfront.databinding.FragmentNegociacaoBinding
import com.example.routepiresfront.data.model.Negociacao
import com.example.routepiresfront.ui.comum.adapter.NegociacaoAdapter

class NegociacaoFragment : Fragment() {

    private var _binding: FragmentNegociacaoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNegociacaoBinding.inflate(inflater, container, false)

        configurarCabecalho()
        configurarListaNegociacoes()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun configurarCabecalho() {
        binding.textEditar.setOnClickListener {
            Toast.makeText(
                requireContext(),
                getString(R.string.negociacao_placeholder_editar),
                Toast.LENGTH_SHORT
            ).show()
        }
        binding.buttonCompose.setOnClickListener {
            Toast.makeText(
                requireContext(),
                getString(R.string.negociacao_placeholder_nova),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun configurarListaNegociacoes() {
        val negociacoes = listOf(
            Negociacao("Haley James", "Seu mototaxi está a caminho e chega em 2 minutos.", 9),
            Negociacao("Nathan Scott", "Consegue me levar do centro até a rodoviária às 14h?", 0),
            Negociacao("Brooke Davis", "Preciso de duas corridas seguidas hoje à noite, consegue?", 2),
            Negociacao("Jamie Scott", "Pode me buscar na escola e deixar na academia?", 0),
            Negociacao("Marvin McFadden", "Qual o valor da corrida até o aeroporto amanhã cedo?", 0),
            Negociacao("Antwon Taylor", "Vou precisar que espere 5 minutos no endereço, tudo bem?", 1),
            Negociacao("Jake Jagielski", "Dá pra levar uma prancha pequena? Quero ir até a praia.", 0),
            Negociacao("Peyton Sawyer", "Você tem capacete extra tamanho pequeno disponível?", 0),
            Negociacao("Lucas Scott", "Tem como fazer uma corrida rápida para o Rivercourt agora?", 3),
            Negociacao("Skills Taylor", "Daria para agendar corrida corporativa para três funcionários?", 0),
            Negociacao("Quentin Fields", "Consegue enviar o comprovante da corrida de ontem?", 1)
        )

        val adaptador = NegociacaoAdapter(negociacoes) { negociacao ->
            // 1️⃣ Obter o NavController do NavHost da aba Negociação
            val navController = requireActivity()
                .supportFragmentManager
                .findFragmentById(R.id.nav_host_negociacao_moto)  // <- id do NavHostFragment da aba
                ?.findNavController()

            // 2️⃣ Navegar usando a action declarada no nav graph da aba
            navController?.navigate(R.id.action_negociacao_para_chat)

//            // 1️⃣ Obter o NavController do NavHost da aba Negociação
//            val navController = requireActivity()
//                .supportFragmentManager
//                .findFragmentById(R.id.nav_host_container)  // <- id do NavHostFragment da aba
//                ?.findNavController()
//
//            // 2️⃣ Navegar usando a action declarada no nav graph da aba
//            navController?.navigate(R.id.action_negociacaoFragment2_to_chatFragment2)
        }

        binding.recyclerNegociacoes.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerNegociacoes.adapter = adaptador
    }
}
