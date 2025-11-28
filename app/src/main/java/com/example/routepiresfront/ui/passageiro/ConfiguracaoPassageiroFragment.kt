package com.example.routepiresfront.ui.passageiro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.routepiresfront.R
import com.example.routepiresfront.ui.comum.SairDialogFragment
import com.example.routepiresfront.databinding.FragmentConfiguracaoPassageiroBinding
import com.example.routepiresfront.viewModel.PassageiroPerfilViewModel
import coil.load

class ConfiguracaoPassageiroFragment : Fragment() {

    private var _binding: FragmentConfiguracaoPassageiroBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PassageiroPerfilViewModel by activityViewModels()
    private var userId: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConfiguracaoPassageiroBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // tenta pegar o USER_ID dos args ou da intent
        userId = arguments?.getString("USER_ID") ?: activity?.intent?.getStringExtra("USER_ID")
        userId?.let { viewModel.carregarPerfil(it) }

        viewModel.perfil.observe(viewLifecycleOwner) { perfil ->
            binding.tvNome.text = perfil.nome ?: ""
            binding.tvEmail.text = perfil.email ?: perfil.metodoPagamentoPreferido ?: ""
            binding.ivAvatar.load(perfil.fotoUrl) {
                crossfade(true)
                placeholder(R.drawable.profile_avatar_background)
                error(R.drawable.profile_avatar_background)
            }
        }

        binding.fabEditarFoto.setOnClickListener {
            // abrir mudar foto futuramente
        }

        binding.ivAvatar.setOnClickListener {
            // abrir mudar foto futuramente
        }

        binding.opcaoEditar.setOnClickListener {
            val bundle = Bundle().apply { putString("USER_ID", userId) }
            findNavController().navigate(R.id.action_configuracaoPassageiroFragment_to_editarPerfilFragment2, bundle)
        }

        binding.opcaoHistorico.setOnClickListener {
            val bundle = Bundle().apply { putString("USER_ID", userId); putString("USER_TYPE", "passageiro") }
            findNavController().navigate(R.id.action_configuracaoPassageiroFragment_to_historicoCorridasFragment2, bundle)
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
}