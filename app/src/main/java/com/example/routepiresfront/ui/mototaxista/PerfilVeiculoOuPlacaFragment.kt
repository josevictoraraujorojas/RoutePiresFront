package com.example.routepiresfront.ui.mototaxista

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.routepiresfront.databinding.FragmentPerfilVeiculoOuPlacaBinding
import com.example.routepiresfront.data.model.VeiculoDTOUpdate
import com.example.routepiresfront.viewModel.MototaxistaPerfilViewModel

class PerfilVeiculoOuPlacaFragment: Fragment() {

    private var _binding: FragmentPerfilVeiculoOuPlacaBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MototaxistaPerfilViewModel by activityViewModels()
    private var userId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userId = arguments?.getString("USER_ID") ?: activity?.intent?.getStringExtra("USER_ID")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPerfilVeiculoOuPlacaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Preenche campos quando o veiculo for carregado (pelo ViewModel) ou se vier via perfil
        viewModel.veiculo.observe(viewLifecycleOwner) { veiculo ->
            binding.editPlacaPerfil.setText(veiculo?.placa ?: "")
            binding.editRenavamPerfil.setText(veiculo?.renavam ?: "")
            binding.editModeloMotoPerfil.setText(veiculo?.modelo ?: "")
            binding.editAnoDaMotoPerfil.setText(veiculo?.ano?.toString() ?: "")
        }

        // Também observa perfil caso o veiculo venha embutido no perfil inicial
        viewModel.perfil.observe(viewLifecycleOwner) { perfil ->
            perfil.veiculoDTO?.let { v ->
                binding.editPlacaPerfil.setText(v.placa ?: "")
                binding.editRenavamPerfil.setText(v.renavam ?: "")
                binding.editModeloMotoPerfil.setText(v.modelo ?: "")
                binding.editAnoDaMotoPerfil.setText(v.ano?.toString() ?: "")
            }
        }

        binding.buttonAtualizarPerfil.setOnClickListener {
            val placa = binding.editPlacaPerfil.text.toString().trim()
            val renavam = binding.editRenavamPerfil.text.toString().trim()
            val modelo = binding.editModeloMotoPerfil.text.toString().trim()
            val anoText = binding.editAnoDaMotoPerfil.text.toString().trim()

            if (placa.isEmpty() || modelo.isEmpty() || anoText.isEmpty()) {
                Toast.makeText(requireContext(), "Preencha placa, modelo e ano", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val ano = try { anoText.toInt() } catch (_: NumberFormatException) { -1 }
            if (ano <= 0) {
                Toast.makeText(requireContext(), "Ano inválido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (userId == null) {
                Toast.makeText(requireContext(), "Usuário não identificado", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Monta um objeto VeiculoDTOUpdate com campos obrigatórios; usa valores default para os demais
            val update = VeiculoDTOUpdate(
                placa = placa,
                modelo = modelo,
                renavam = renavam,
                ano = ano,
                capacidade = 1,
                fotoUrl = ""
            )

            // Chama update; aguarda confirmação via updateStatus
            viewModel.atualizarVeiculo(userId!!, update)
            Toast.makeText(requireContext(), "Enviando atualização de veículo...", Toast.LENGTH_SHORT).show()
        }

        binding.buttonCancelarPerfil.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnVoltar.setOnClickListener {
            findNavController().navigateUp()
        }
        // Se tivermos userId, tenta carregar os dados do veículo
        userId?.let { id ->
            viewModel.carregarVeiculo(id)
        }

        // Observa erros do ViewModel
        viewModel.error.observe(viewLifecycleOwner) { err ->
            err?.let {
                Toast.makeText(requireContext(), "Erro: $it", Toast.LENGTH_LONG).show()
            }
        }

        // Observa status de update para navegar após confirmação
        viewModel.updateStatus.observe(viewLifecycleOwner) { status ->
            status?.let {
                if (it) {
                    Toast.makeText(requireContext(), "Veículo atualizado com sucesso", Toast.LENGTH_SHORT).show()
                    findNavController().navigateUp()
                } else {
                    Toast.makeText(requireContext(), "Falha ao atualizar veículo", Toast.LENGTH_LONG).show()
                }
                viewModel.resetUpdateStatus()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
