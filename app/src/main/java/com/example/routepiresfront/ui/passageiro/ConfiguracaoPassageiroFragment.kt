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
import com.example.routepiresfront.core.SessionManager
import androidx.core.os.bundleOf

class ConfiguracaoPassageiroFragment : Fragment() {

    private var _binding: FragmentConfiguracaoPassageiroBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ConfiguracaoPassageiroViewModel by viewModels()

    private val passageiroId: String by lazy {
        arguments?.getString("passageiroId")?.trim().takeUnless { it.isNullOrEmpty() }
            ?: SessionManager.obterUsuarioId(requireContext())?.trim().takeUnless { it.isNullOrEmpty() }
            ?: ""
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

        // Carrega dados iniciais do passageiro ao abrir a tela, somente se houver id
        if (passageiroId.isNotBlank()) {
            viewModel.carregarDados(passageiroId)
        }
        observarMensagens()

        binding.fabEditarFoto.setOnClickListener {
            // abrir mudar foto futuramente
        }

        binding.ivAvatar.setOnClickListener {
            // abrir mudar foto futuramente
        }

        binding.opcaoEditar.setOnClickListener {
            val args = bundleOf("passageiroId" to passageiroId)
            findNavController().navigate(
                R.id.action_configuracaoPassageiroFragment_to_editarPerfilFragment2,
                args
            )
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
