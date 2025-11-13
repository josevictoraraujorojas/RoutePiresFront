package com.example.routepiresfront

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.routepiresfront.databinding.FragmentNegociacaoBinding

/**
 * Fragmento responsavel por exibir a tela de negociacoes com a lista de conversas.
 */
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
        // Mensagens temporarias ate que as funcionalidades sejam implementadas.
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
        // Lista estatica apenas para representar o layout enquanto a API nao estiver integrada.
        val negociacoes = listOf(
            Negociacao(
                nome = "Haley James",
                mensagem = "Seu mototaxi esta a caminho e chega em 2 minutos.",
                quantidadeNaoLida = 9
            ),
            Negociacao(
                nome = "Nathan Scott",
                mensagem = "Consegue me levar do centro ate a rodoviaria as 14h?",
                quantidadeNaoLida = 0
            ),
            Negociacao(
                nome = "Brooke Davis",
                mensagem = "Preciso de duas corridas seguidas hoje a noite, consegue?",
                quantidadeNaoLida = 2
            ),
            Negociacao(
                nome = "Jamie Scott",
                mensagem = "Pode me buscar na escola e deixar na academia?",
                quantidadeNaoLida = 0
            ),
            Negociacao(
                nome = "Marvin McFadden",
                mensagem = "Qual o valor da corrida ate o aeroporto amanhã cedo?",
                quantidadeNaoLida = 0
            ),
            Negociacao(
                nome = "Antwon Taylor",
                mensagem = "Vou precisar que espere 5 minutos no endereco, tudo bem?",
                quantidadeNaoLida = 1
            ),
            Negociacao(
                nome = "Jake Jagielski",
                mensagem = "Da pra levar uma prancha pequena? Quero ir ate a praia.",
                quantidadeNaoLida = 0
            ),
            Negociacao(
                nome = "Peyton Sawyer",
                mensagem = "Voce tem capacete extra tamanho pequeno disponivel?",
                quantidadeNaoLida = 0
            ),
            Negociacao(
                nome = "Lucas Scott",
                mensagem = "Tem como fazer uma corrida rapida para o Rivercourt agora?",
                quantidadeNaoLida = 3
            ),
            Negociacao(
                nome = "Skills Taylor",
                mensagem = "Daria para agendar corrida corporativa para tres funcionarios?",
                quantidadeNaoLida = 0
            ),
            Negociacao(
                nome = "Quentin Fields",
                mensagem = "Consegue enviar o comprovante da corrida de ontem?",
                quantidadeNaoLida = 1
            )
        )

        val adaptador = NegociacaoAdapter(negociacoes)
        binding.recyclerNegociacoes.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerNegociacoes.adapter = adaptador
    }
}
