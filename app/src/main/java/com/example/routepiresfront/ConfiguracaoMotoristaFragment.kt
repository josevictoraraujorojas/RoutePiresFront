package com.example.routepiresfront

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.routepiresfront.databinding.FragmentConfiguracaoMotoristaBinding

/**
 * Tela de configuracao do motorista.
 * Exibe dados basicos, permite controlar disponibilidade e oferece atalhos para outras areas.
 */
class ConfiguracaoMotoristaFragment : Fragment() {

    private lateinit var binding : FragmentConfiguracaoMotoristaBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentConfiguracaoMotoristaBinding.inflate(inflater, container, false)
        return binding.root
    }



    /** Define as acoes dos botoes, exibindo mensagens temporarias. */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fabEditarFoto.setOnClickListener {

        }

        binding.ivAvatar.setOnClickListener {

        }

        binding.opcaoEditar.setOnClickListener {
            (activity as? MenubarActivity)?.replaceFragment(EditarPerfilFragment(), R.id.fragment_principal)
        }

        binding.opcaoVeiculo.setOnClickListener {
            (activity as? MenubarActivity)?.replaceFragment(PerfilVeiculoOuPlacaFragment(), R.id.fragment_principal)
        }

        binding.opcaoHistorico.setOnClickListener {
            (activity as? MenubarActivity)?.replaceFragment(HistoricoCorridasFragment(), R.id.fragment_principal)
        }

        binding.opcaoNotificacoes.setOnClickListener {
            (activity as? MenubarActivity)?.replaceFragment(NotificacaoFragment(), R.id.fragment_principal)
        }

        binding.opcaoSair.setOnClickListener {

        }

        binding.swDisponibilidade.setOnCheckedChangeListener { _, isChecked ->
            val mensagem = if (isChecked) {

            } else {

            }

        }
    }
}
