package com.example.routepiresfront

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.routepiresfront.databinding.FragmentConfiguracaoPassageiroBinding

class ConfiguracaoPassageiroFragment : Fragment() {

    private lateinit var binding : FragmentConfiguracaoPassageiroBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentConfiguracaoPassageiroBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fabEditarFoto.setOnClickListener {

        }

        binding.ivAvatar.setOnClickListener {

        }

        binding.opcaoEditar.setOnClickListener {
            (activity as? MenubarActivity)?.replaceFragment(EditarPerfilFragment(), R.id.fragment_principal)
        }


        binding.opcaoHistorico.setOnClickListener {
            (activity as? MenubarActivity)?.replaceFragment(HistoricoCorridasFragment(), R.id.fragment_principal)
        }

        binding.opcaoNotificacoes.setOnClickListener {
            (activity as? MenubarActivity)?.replaceFragment(NotificacaoFragment(), R.id.fragment_principal)
        }

        binding.opcaoSair.setOnClickListener {
            val dialog = SairDialogFragment()
            dialog.show(parentFragmentManager, "SairDialog")
        }
    }
}
