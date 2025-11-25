package com.example.routepiresfront.ui.passageiro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.example.routepiresfront.R
import com.example.routepiresfront.ui.comum.SairDialogFragment
import com.example.routepiresfront.databinding.FragmentConfiguracaoPassageiroBinding
import com.example.routepiresfront.ui.passageiro.viewmodel.ConfiguracaoPassageiroViewModel

class ConfiguracaoPassageiroFragment : Fragment() {

    private var _binding: FragmentConfiguracaoPassageiroBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ConfiguracaoPassageiroViewModel by viewModels()

    // Em um app real esse ID viria do login/SharedPreferences. Mantemos o mock para a demo.
    private val passageiroId: String by lazy {
        arguments?.getString("passageiroId")?.trim().takeUnless { it.isNullOrEmpty() }
            ?: "cUKwPBdlmMt95OJuMleA"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConfiguracaoPassageiroBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Carrega dados iniciais do passageiro ao abrir a tela
        viewModel.carregarDados(passageiroId)
        observarMensagens()

        binding.fabEditarFoto.setOnClickListener {
            // abrir mudar foto futuramente
        }

        binding.ivAvatar.setOnClickListener {
            // abrir mudar foto futuramente
        }

        binding.opcaoEditar.setOnClickListener {
            findNavController().navigate(R.id.action_configuracaoPassageiroFragment_to_editarPerfilFragment2)
        }

        binding.opcaoHistorico.setOnClickListener {
            findNavController().navigate(R.id.action_configuracaoPassageiroFragment_to_historicoCorridasFragment2)
        }

        binding.opcaoNotificacoes.setOnClickListener {
            findNavController().navigate(R.id.action_configuracaoPassageiroFragment_to_notificacaoFragment2)
        }

        binding.opcaoSair.setOnClickListener {
            val dialog = SairDialogFragment()
            dialog.show(parentFragmentManager, "SairDialog")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Observa mensagens do ViewModel e exibe em Snackbar para não perder feedback de rede.
     */
    private fun observarMensagens() {
        viewModel.mensagem.observe(viewLifecycleOwner) { mensagem ->
            mensagem?.let {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
                viewModel.limparMensagem()
            }
        }
    }
}
