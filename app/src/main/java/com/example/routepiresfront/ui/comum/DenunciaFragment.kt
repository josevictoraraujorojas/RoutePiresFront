package com.example.routepiresfront.ui.comum

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.gov.ifgoiano.routepires.data.remote.ChatService
import br.gov.ifgoiano.routepiresfront.data.remote.DenunciaService
import com.example.routepiresfront.data.remote.ApiClient
import com.example.routepiresfront.databinding.FragmentDenunciaBinding
import com.example.routepiresfront.repository.DenunciaRepository
import com.example.routepiresfront.viewmodel.DenunciaViewModel
import com.example.routepiresfront.viewmodel.DenunciaViewModelFactory

class DenunciaFragment : Fragment() {

    private var _binding: FragmentDenunciaBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: DenunciaViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDenunciaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val denunciaApi = ApiClient.getService(DenunciaService::class.java)

        // 👉 Recupera argumentos
        val args = DenunciaFragmentArgs.fromBundle(requireArguments())

        // 👉 Cria repository + factory
        val denunciaRepository = DenunciaRepository(denunciaApi)
        val factory = DenunciaViewModelFactory(
            denunciaRepository,
            args.usuarioLogadoId,
            args.usuarioDenunciadoId
        )

        // 👉 Cria o ViewModel
        viewModel = ViewModelProvider(this, factory)[DenunciaViewModel::class.java]

        // 👉 Liga o ViewModel ao layout
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        // 👉 Observa o sucesso da denúncia
        viewModel.sucesso.observe(viewLifecycleOwner) { ok ->
            if (ok == true) {
                Toast.makeText(requireContext(), "Denúncia enviada com sucesso!", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
        }

        // 👉 Observa erros
        viewModel.erro.observe(viewLifecycleOwner) { msg ->
            msg?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                viewModel.limparErro()
            }
        }

        // 👉 Botão voltar
        binding.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }

        // 👉 Observa o loading (opcional: para ProgressBar se você adicionar no layout)
        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            // Por exemplo: binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        // Os EditTexts já estão conectados ao ViewModel via Data Binding:
        // edtMotivoDenuncia -> viewModel.motivo
        // edtDescricaooDenuncia -> viewModel.descricao
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
