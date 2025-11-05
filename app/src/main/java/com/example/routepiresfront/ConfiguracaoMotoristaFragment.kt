package com.example.routepiresfront

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.routepiresfront.databinding.FragmentConfiguracaoMotoristaBinding

class ConfiguracaoMotoristaFragment : Fragment() {

    private lateinit var binding: FragmentConfiguracaoMotoristaBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentConfiguracaoMotoristaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = Navigation.findNavController(view)

        binding.opcaoEditar.setOnClickListener {
            navController.navigate(R.id.action_configuracaoMotoristaFragment_to_editarPerfilFragment)
        }

        binding.opcaoVeiculo.setOnClickListener {
            navController.navigate(R.id.action_configuracaoMotoristaFragment_to_perfilVeiculoOuPlacaFragment)
        }

        binding.opcaoHistorico.setOnClickListener {
            navController.navigate(R.id.action_configuracaoMotoristaFragment_to_historicoCorridasFragment)
        }

        binding.opcaoNotificacoes.setOnClickListener {
            navController.navigate(R.id.action_configuracaoMotoristaFragment_to_notificacaoFragment)
        }

        binding.opcaoSair.setOnClickListener {
            val dialog = SairDialogFragment()
            dialog.show(parentFragmentManager, "SairDialog")
        }
    }
}
