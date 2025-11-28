package com.example.routepiresfront.ui.mototaxista

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import coil.load
import com.example.routepiresfront.R
import com.example.routepiresfront.databinding.FragmentConfiguracaoMotoristaBinding
import com.example.routepiresfront.data.model.MototaxistaDTOUpdate
import com.example.routepiresfront.ui.comum.SairDialogFragment
import com.example.routepiresfront.viewModel.MototaxistaPerfilViewModel

class ConfiguracaoMotoristaFragment : Fragment() {

    private var _binding: FragmentConfiguracaoMotoristaBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MototaxistaPerfilViewModel by activityViewModels()

    private var userId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Primeiro tenta buscar dos argumentos do nav graph, se não encontrar tenta buscar da intent da Activity
        userId = arguments?.getString("USER_ID") ?: activity?.intent?.getStringExtra("USER_ID")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConfiguracaoMotoristaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("ConfiguracaoFragment", "onViewCreated - userId=$userId")

        // Observador do perfil (sempre ativo) — atualizará UI quando o ViewModel emitir dados
        viewModel.perfil.observe(viewLifecycleOwner) { perfil ->
            Log.d("ConfiguracaoFragment", "perfil recebido: $perfil")
            // Atualiza textos com fallback para string vazia (evita manter textos default do layout)
            binding.tvNome.text = perfil.nome ?: ""
            binding.tvApelido.text = perfil.email ?: ""

            binding.ivAvatar.load(perfil.fotoUrl) {
                crossfade(true)
                placeholder(R.drawable.profile_avatar_background)
                error(R.drawable.profile_avatar_background)
            }

            // Atualiza switch de disponibilidade sem disparar o listener
            // remove listener temporariamente
            binding.swDisponibilidade.setOnCheckedChangeListener(null)
            binding.swDisponibilidade.isChecked = perfil.disponivel ?: false
            // Re-associa o listener para persistir a alteração
            binding.swDisponibilidade.setOnCheckedChangeListener { _, isChecked ->
                Log.d("ConfiguracaoFragment", "swDisponibilidade toggled: $isChecked (userId=$userId)")
                Toast.makeText(requireContext(), if (isChecked) "Disponibilidade: ON" else "Disponibilidade: OFF", Toast.LENGTH_SHORT).show()
                // Se tivermos um userId, solicita atualização do backend
                userId?.let { id ->
                    // Chama o ViewModel para atualizar apenas o campo 'disponivel'
                    viewModel.atualizarPerfil(id, MototaxistaDTOUpdate(disponivel = isChecked))
                } ?: run {
                    Log.w("ConfiguracaoFragment", "Tentativa de atualizar disponibilidade sem userId")
                    Toast.makeText(requireContext(), "Erro: usuário não identificado", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Observa erros do ViewModel e exibe ao usuário
        viewModel.error.observe(viewLifecycleOwner) { err ->
            err?.let {
                Log.e("ConfiguracaoFragment", "Erro no ViewModel: $it")
                Toast.makeText(requireContext(), "Erro: $it", Toast.LENGTH_LONG).show()
            }
        }

        // Sempre configura os listeners das opções — navegação funciona mesmo que userId seja nulo
        val navController = view.findNavController()
        binding.opcaoEditar.setOnClickListener {
            val bundle = Bundle().apply { putString("USER_ID", userId); putString("USER_TYPE", "mototaxista") }
            navController.navigate(R.id.action_configuracaoMotoristaFragment_to_editarPerfilFragment, bundle)
        }

        binding.opcaoVeiculo.setOnClickListener {
            val bundle = Bundle().apply { putString("USER_ID", userId); putString("USER_TYPE", "mototaxista") }
            navController.navigate(R.id.action_configuracaoMotoristaFragment_to_perfilVeiculoOuPlacaFragment, bundle)
        }

        binding.opcaoHistorico.setOnClickListener {
            val bundle = Bundle().apply { putString("USER_ID", userId); putString("USER_TYPE", "mototaxista") }
            navController.navigate(R.id.action_configuracaoMotoristaFragment_to_historicoCorridasFragment, bundle)
        }

        binding.opcaoNotificacoes.setOnClickListener {
            val bundle = Bundle().apply { putString("USER_ID", userId); putString("USER_TYPE", "mototaxista") }
            navController.navigate(R.id.action_configuracaoMotoristaFragment_to_notificacaoFragment, bundle)
        }

        // Se tivermos um userId válido, solicita o carregamento do perfil
        userId?.let { id ->
            viewModel.carregarPerfil(id)
        }

        binding.opcaoSair.setOnClickListener {
            SairDialogFragment().show(parentFragmentManager, "SairDialog")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
