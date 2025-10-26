package com.example.routepiresfront

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.routepiresfront.databinding.FragmentPrimeiroCadastroMototaxistaBinding

class PrimeiroCadastroMototaxistaFragment : Fragment() {

    private var _binding: FragmentPrimeiroCadastroMototaxistaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPrimeiroCadastroMototaxistaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configura termos e política clicáveis
        val texto = "Li e concordo com os Termos de uso e a Política de Privacidade"
        val spannable = SpannableString(texto)

        // "Termos de uso" clicável
        val termosStart = texto.indexOf("Termos de uso")
        val termosEnd = termosStart + "Termos de uso".length
        spannable.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {
                Toast.makeText(requireContext(), "Termos clicado", Toast.LENGTH_SHORT).show()
                // Aqui você pode abrir Activity ou link dos Termos
            }
        }, termosStart, termosEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        // "Política de Privacidade" clicável
        val privStart = texto.indexOf("Política de Privacidade")
        val privEnd = privStart + "Política de Privacidade".length
        spannable.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {
                Toast.makeText(requireContext(), "Política clicada", Toast.LENGTH_SHORT).show()
                // Aqui você pode abrir Activity ou link da Política
            }
        }, privStart, privEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        binding.txtTermosCompletos.text = spannable
        binding.txtTermosCompletos.movementMethod = LinkMovementMethod.getInstance()

        // Botão continuar
        binding.button.setOnClickListener {
            if (!binding.chkTermos.isChecked) {
                Toast.makeText(requireContext(), "Você deve aceitar os termos de uso", Toast.LENGTH_SHORT).show()
            } else {
                // Navega para o próximo passo do cadastro.
                (activity as? CadastroMototaxistaActivity)?.navigateToSecondStep()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
