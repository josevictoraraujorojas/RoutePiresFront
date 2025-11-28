package com.example.routepiresfront.ui.passageiro

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.routepiresfront.databinding.ActivityCadastroPassageiroBinding
import com.example.routepiresfront.ui.passageiro.viewmodel.CadastroPassageiroViewModel

/**
 * Activity de cadastro usando Data Binding + ViewModel.
 * Mantém o formulário reativo e fornece feedback rápido via Toast.
 */
class CadastroPassageiroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCadastroPassageiroBinding
    private val viewModel: CadastroPassageiroViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroPassageiroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        // Botão voltar fecha a tela; evita state leak
        binding.btnVoltar.setOnClickListener { finish() }
        configurarTermosClickaveis()

        observarMensagens()
    }

    /**
     * Replica o texto clicável de Termos/Política e mantém a referência ao TextView do layout.
     */
    private fun configurarTermosClickaveis() {
        val texto = "Li e concordo com os Termos de uso e a Política de Privacidade"
        val spannable = SpannableString(texto)

        val termosStart = texto.indexOf("Termos de uso")
        val termosEnd = termosStart + "Termos de uso".length
        spannable.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {
                Toast.makeText(
                    this@CadastroPassageiroActivity,
                    "Termos clicado",
                    Toast.LENGTH_SHORT
                ).show()
                // Aqui você pode abrir a Activity ou link dos Termos
            }
        }, termosStart, termosEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        val privStart = texto.indexOf("Política de Privacidade")
        val privEnd = privStart + "Política de Privacidade".length
        spannable.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {
                Toast.makeText(
                    this@CadastroPassageiroActivity,
                    "Política clicada",
                    Toast.LENGTH_SHORT
                ).show()
                // Aqui você pode abrir a Activity ou link da Política
            }
        }, privStart, privEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        binding.txtTermosCompletos.text = spannable
        binding.txtTermosCompletos.movementMethod = LinkMovementMethod.getInstance()
    }

    /**
     * Observa mensagens únicas do ViewModel para apresentar feedback ao usuário.
     */
    private fun observarMensagens() {
        viewModel.mensagem.observe(this) { mensagem ->
            mensagem?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                viewModel.limparMensagem()
            }
        }
    }
}
