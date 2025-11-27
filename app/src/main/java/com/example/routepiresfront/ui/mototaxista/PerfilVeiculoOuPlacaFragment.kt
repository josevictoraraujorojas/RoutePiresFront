package com.example.routepiresfront.ui.mototaxista

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.routepiresfront.data.remote.ApiClient
import com.example.routepiresfront.data.repository.MototaxistaRepository
import com.example.routepiresfront.databinding.FragmentPerfilVeiculoOuPlacaBinding
import com.example.routepiresfront.viewmodel.EditarVeiculoViewModel
import com.example.routepiresfront.viewmodel.EditarVeiculoViewModelFactory

class PerfilVeiculoOuPlacaFragment : Fragment() {

    private var _binding: FragmentPerfilVeiculoOuPlacaBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EditarVeiculoViewModel by viewModels {
        EditarVeiculoViewModelFactory(
            repository = MototaxistaRepository(ApiClient.mototaxistaApi),
            "OYwpWzjyda6DsIE0UnwT"
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPerfilVeiculoOuPlacaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Observers
        viewModel.veiculo.observe(viewLifecycleOwner) { veiculo ->
            binding.editPlacaPerfil.setText(veiculo?.placa ?: "")
            binding.editModeloMotoPerfil.setText(veiculo?.modelo ?: "")
            binding.editRenavamPerfil.setText(veiculo?.renavam ?: "")
            binding.editAnoDaMotoPerfil.setText(veiculo?.ano?.toString() ?: "")
        }

        viewModel.resultadoEdicao.observe(viewLifecycleOwner) { resultado ->
            resultado.onSuccess {
                Toast.makeText(requireContext(), "Veículo atualizado!", Toast.LENGTH_SHORT).show()
                parentFragmentManager.popBackStack()
            }
            resultado.onFailure {
                Toast.makeText(requireContext(), "Erro ao atualizar veículo!", Toast.LENGTH_SHORT).show()
            }
        }

        // Carrega dados do veículo
        viewModel.carregarDados()

        // Botão salvar
        binding.buttonAtualizarPerfil.setOnClickListener {
            viewModel.editarVeiculo(
                placa = binding.editPlacaPerfil.text.toString(),
                modelo = binding.editModeloMotoPerfil.text.toString(),
                renavam = binding.editRenavamPerfil.text.toString(),
                ano = binding.editAnoDaMotoPerfil.text.toString().toIntOrNull() ?: 0
            )
        }

        // Voltar
        binding.buttonCancelarPerfil.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.btnVoltar.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}