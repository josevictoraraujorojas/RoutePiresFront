package com.example.routepiresfront

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.routepiresfront.databinding.FragmentConfiguracaoPassageiroBinding

class ConfiguracaoPassageiroFragment : Fragment() {

    private var _binding: FragmentConfiguracaoPassageiroBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConfiguracaoPassageiroBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
}
