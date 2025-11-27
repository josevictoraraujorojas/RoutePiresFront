package com.example.routepiresfront.ui.comum

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.routepiresfront.data.remote.ApiClient
import com.example.routepiresfront.data.repository.MototaxistaRepository
import com.example.routepiresfront.databinding.FragmentEditarPerfilBinding
import com.example.routepiresfront.viewmodel.EditarPerfilViewModel
import com.example.routepiresfront.viewmodel.EditarPerfilViewModelFactory

class EditarPerfilFragment : Fragment() {

    private var _binding: FragmentEditarPerfilBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EditarPerfilViewModel by viewModels {
        EditarPerfilViewModelFactory(
            repository = MototaxistaRepository(ApiClient.mototaxistaApi),
            "OYwpWzjyda6DsIE0UnwT"
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditarPerfilBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.carregarDados()

        viewModel.mototaxista.observe(viewLifecycleOwner) { user ->
            binding.editNomeCompleto.setText(user.nome)
            binding.editEmail.setText(user.email)
            binding.editTelefone.setText(user.telefone)
        }

        binding.buttonAtualizarPerfil.setOnClickListener {
            val nome = binding.editNomeCompleto.text.toString()
            val email = binding.editEmail.text.toString()
            val tel = binding.editTelefone.text.toString()
            val senha = binding.editSenha.text.toString()

            viewModel.editarPerfil(nome, email, tel, senha)
        }

        viewModel.resultadoEdicao.observe(viewLifecycleOwner) { resultado ->
            resultado.onSuccess {
                Toast.makeText(requireContext(), "Perfil atualizado com sucesso!", Toast.LENGTH_SHORT).show()
                findNavController().navigateUp()
            }
            resultado.onFailure {
                Toast.makeText(requireContext(), "Erro ao atualizar perfil!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.buttonCancelarPerfil.setOnClickListener { findNavController().navigateUp() }
        binding.btnVoltar.setOnClickListener { findNavController().navigateUp() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}