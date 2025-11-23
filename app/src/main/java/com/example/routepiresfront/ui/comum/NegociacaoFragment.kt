package com.example.routepiresfront.ui.comum

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.routepiresfront.R
import com.example.routepiresfront.databinding.FragmentNegociacaoBinding
import com.example.routepiresfront.ui.comum.adapter.NegociacaoAdapter
import com.example.routepiresfront.ui.comum.viewmodel.NegociacaoViewModel
import com.example.routepiresfront.ui.comum.viewmodel.NegociacaoViewModelFactory
import br.gov.ifgoiano.routepiresfront.repository.ChatRepository
import com.example.routepiresfront.data.remote.ApiClient
import br.gov.ifgoiano.routepires.data.remote.ChatService

class NegociacaoFragment : Fragment() {

    private var _binding: FragmentNegociacaoBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: NegociacaoViewModel
    private lateinit var adaptador: NegociacaoAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNegociacaoBinding.inflate(inflater, container, false)

        // NECESSÁRIO para funcionar DataBinding no XML
        binding.lifecycleOwner = viewLifecycleOwner

        configurarViewModel()
        configurarCabecalho()
        configurarRecycler()
        configurarBusca()

        viewModel.carregarNegociacoes()

        return binding.root
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

    private fun configurarRecycler() {
        adaptador = NegociacaoAdapter(emptyList()) { negociacao ->
            val navController = requireActivity()
                .supportFragmentManager
                .findFragmentById(R.id.nav_host_negociacao_moto)
                ?.findNavController()

            navController?.navigate(R.id.action_negociacao_para_chat)
        }

        binding.recyclerNegociacoes.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerNegociacoes.adapter = adaptador
    }

    private fun configurarViewModel() {
        val api = ApiClient.getService(ChatService::class.java)
        val repository = ChatRepository(api)
        val factory = NegociacaoViewModelFactory(repository)

        viewModel = ViewModelProvider(this, factory).get(NegociacaoViewModel::class.java)

        // 🔥 ESSENCIAL PARA FUNCIONAR O DATABINDING DO XML
        binding.viewModel = viewModel

        // Observers
        viewModel.negociacoes.observe(viewLifecycleOwner) { lista ->
            adaptador.atualizar(lista)
            if (lista.isEmpty()) {
                Toast.makeText(requireContext(), "Nenhuma negociação encontrada", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.erro.observe(viewLifecycleOwner) { erro ->
            erro?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun configurarBusca() {
        binding.editSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.filtrar(s?.toString().orEmpty())
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
