package com.example.routepiresfront.ui.mototaxista

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
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
import com.example.routepiresfront.databinding.FragmentPrimeiroCadastroMototaxistaBinding
import com.example.routepiresfront.viewmodel.CadastroMototaxistaViewModel
import com.example.routepiresfront.viewmodel.MyViewModelFactory

class PrimeiroCadastroMototaxistaFragment : Fragment() {

    private var _binding: FragmentPrimeiroCadastroMototaxistaBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CadastroMototaxistaViewModel by activityViewModels {
        MyViewModelFactory(MototaxistaRepository(RetrofitProvider.createApi()))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPrimeiroCadastroMototaxistaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Texto de termos clicável
        val texto = "Li e concordo com os Termos de uso e a Política de Privacidade"
        val spannable = SpannableString(texto)

        val termosStart = texto.indexOf("Termos de uso")
        val termosEnd = termosStart + "Termos de uso".length
        spannable.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {
                Toast.makeText(requireContext(), "Termos clicado", Toast.LENGTH_SHORT).show()
            }
        }, termosStart, termosEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        val privStart = texto.indexOf("Política de Privacidade")
        val privEnd = privStart + "Política de Privacidade".length
        spannable.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {
                Toast.makeText(requireContext(), "Política clicada", Toast.LENGTH_SHORT).show()
            }
        }, privStart, privEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        binding.txtTermosCompletos.text = spannable
        binding.txtTermosCompletos.movementMethod = LinkMovementMethod.getInstance()

        binding.editNome.doAfterTextChanged {
            viewModel.updateForm { copy(nomeCompleto = it.toString()) }
        }

        binding.editEmail.doAfterTextChanged {
            viewModel.updateForm { copy(email = it.toString()) }
        }

        binding.editTelefone.doAfterTextChanged {
            viewModel.updateForm { copy(telefone = it.toString()) }
        }

        binding.editSenha.doAfterTextChanged {
            viewModel.updateForm { copy(senha = it.toString()) }
        }

        binding.editSenhaConfirmacao.doAfterTextChanged {
            viewModel.updateForm { copy(confirmaSenha = it.toString()) }
        }

        // --- 3) Aqui fica a validação e navegação ---
        binding.button.setOnClickListener {

            if (!binding.chkTermos.isChecked) {
                Toast.makeText(requireContext(), "Você deve aceitar os termos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (viewModel.form.value.isFirstStepValid()) {
                findNavController().navigate(
                    R.id.action_primeiroCadastroMototaxistaFragment_to_segundoCadastroMototaxista
                )
            } else {
                Toast.makeText(requireContext(), "Preencha os dados corretamente", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}