package com.example.routepiresfront.ui.mototaxista

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.routepiresfront.R
import com.example.routepiresfront.data.remote.RetrofitProvider
import com.example.routepiresfront.data.repository.MototaxistaRepository
import com.example.routepiresfront.databinding.FragmentSegundoCadastroMototaxistaBinding
import com.example.routepiresfront.viewmodel.CadastroMototaxistaViewModel
import com.example.routepiresfront.viewmodel.MyViewModelFactory

class SegundoCadastroMototaxista : Fragment() {

    private var _binding: FragmentSegundoCadastroMototaxistaBinding? = null

    private val binding get() = _binding!!

    private val viewModel: CadastroMototaxistaViewModel by activityViewModels {
        MyViewModelFactory(MototaxistaRepository(RetrofitProvider.createApi()))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSegundoCadastroMototaxistaBinding.inflate(inflater, container, false)
        return binding.root
    }

    // Configura as interações da view após ela ser criada.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configurarInputs()

        binding.button.setOnClickListener {

            if (!viewModel.form.value.isSecondStepValid()) {
                Toast.makeText(requireContext(), "Preencha todos os campos obrigatórios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            findNavController().navigate(
                R.id.action_segundoCadastroMototaxista_to_terceiroCadastroMototaxistaFragment
            )
        }
    }

    private fun configurarInputs() = with(binding) {

        editCNH.doAfterTextChanged {
            viewModel.updateForm { copy(cnh = it.toString()) }
        }

        editDataValidade.doAfterTextChanged {
            viewModel.updateForm { copy(validadeCnh = it.toString()) }
        }
    }

    // Limpa a referência do binding quando a view é destruída para evitar vazamentos de memória.
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}