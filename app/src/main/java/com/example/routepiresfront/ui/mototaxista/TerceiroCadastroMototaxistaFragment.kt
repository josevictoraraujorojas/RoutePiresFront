package com.example.routepiresfront.ui.mototaxista

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.routepiresfront.data.remote.RetrofitProvider
import com.example.routepiresfront.data.repository.MototaxistaRepository
import com.example.routepiresfront.databinding.FragmentTerceiroCadastroMototaxistaBinding
import com.example.routepiresfront.ui.comum.LoginActivity
import com.example.routepiresfront.viewmodel.CadastroMototaxistaViewModel
import com.example.routepiresfront.viewmodel.CadastroUiState
import com.example.routepiresfront.viewmodel.MyViewModelFactory
import kotlinx.coroutines.flow.collectLatest

class TerceiroCadastroMototaxistaFragment : Fragment() {

    private var _binding: FragmentTerceiroCadastroMototaxistaBinding? = null

    private val binding get() = _binding!!

    private val viewModel: CadastroMototaxistaViewModel by activityViewModels {
        MyViewModelFactory(MototaxistaRepository(RetrofitProvider.createApi()))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTerceiroCadastroMototaxistaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.editPlaca.doAfterTextChanged {
            viewModel.updateForm { copy(placa = it.toString()) }
        }

        binding.editRenavam.doAfterTextChanged {
            viewModel.updateForm { copy(renavam = it.toString()) }
        }

        binding.editModeloMoto.doAfterTextChanged {
            viewModel.updateForm { copy(modeloMoto = it.toString()) }
        }

        binding.editAnoDaMoto.doAfterTextChanged {
            viewModel.updateForm { copy(anoMoto = it.toString()) }
        }

        // Botão
        binding.button.setOnClickListener {
            val isValid = viewModel.form.value.isThirdStepValid()

            if (!isValid) {
                Toast.makeText(requireContext(), "Preencha todos os campos corretamente", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.submitCadastro()
        }

        // Observa o resultado do cadastro
        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collectLatest { state ->
                when (state) {

                    is CadastroUiState.Loading -> {
                        Toast.makeText(requireContext(), "Enviando dados...", Toast.LENGTH_SHORT).show()
                    }

                    is CadastroUiState.Success -> {
                        Toast.makeText(requireContext(), "Cadastro concluído!", Toast.LENGTH_LONG).show()

                        // 👉 Vai para LoginActivity
                        startActivity(Intent(requireContext(), LoginActivity::class.java))
                        view.post {
                            requireActivity().finish()
                        }
                    }

                    is CadastroUiState.Error -> {
                        Toast.makeText(requireContext(), "Erro: ${state.message}", Toast.LENGTH_LONG).show()
                    }

                    else -> {}
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}