package com.example.routepiresfront

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class CadastroPassageiroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_passageiro)

        val btnVoltar = findViewById<ImageButton>(R.id.btnVoltar)
        val edtNome = findViewById<EditText>(R.id.edtNome)
        val edtEmail = findViewById<EditText>(R.id.edtEmail)
        val edtTelefone = findViewById<EditText>(R.id.edtTelefone)
        val edtSenha = findViewById<EditText>(R.id.edtSenha)
        val edtConfirmarSenha = findViewById<EditText>(R.id.edtConfirmarSenha)
        val chkTermos = findViewById<CheckBox>(R.id.chkTermos)
        val btnFinalizar = findViewById<Button>(R.id.btnFinalizar)
        val txtTermosCompletos = findViewById<TextView>(R.id.txtTermosCompletos)

        btnVoltar.setOnClickListener { finish() }

        // === Configurar termos e política clicáveis ===
        val texto = "Li e concordo com os Termos de uso e a Política de Privacidade"
        val spannable = SpannableString(texto)

        // "Termos de uso" clicável
        val termosStart = texto.indexOf("Termos de uso")
        val termosEnd = termosStart + "Termos de uso".length
        spannable.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {
                Toast.makeText(this@CadastroPassageiroActivity, "Termos clicado", Toast.LENGTH_SHORT).show()
                // Aqui você pode abrir a Activity ou link dos Termos
            }
        }, termosStart, termosEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        // "Política de Privacidade" clicável
        val privStart = texto.indexOf("Política de Privacidade")
        val privEnd = privStart + "Política de Privacidade".length
        spannable.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {
                Toast.makeText(this@CadastroPassageiroActivity, "Política clicada", Toast.LENGTH_SHORT).show()
                // Aqui você pode abrir a Activity ou link da Política
            }
        }, privStart, privEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        txtTermosCompletos.text = spannable
        txtTermosCompletos.movementMethod = LinkMovementMethod.getInstance()

        // === Botão finalizar ===
        btnFinalizar.setOnClickListener {
            val nome = edtNome.text.toString().trim()
            val email = edtEmail.text.toString().trim()
            val telefone = edtTelefone.text.toString().trim()
            val senha = edtSenha.text.toString()
            val confirmarSenha = edtConfirmarSenha.text.toString()

            when {
                nome.isEmpty() || email.isEmpty() || telefone.isEmpty() ||
                        senha.isEmpty() || confirmarSenha.isEmpty() -> {
                    showToast("Preencha todos os campos")
                }
                senha != confirmarSenha -> {
                    showToast("As senhas não coincidem")
                }
                !chkTermos.isChecked -> {
                    showToast("Você deve aceitar os termos de uso")
                }
                else -> {
                    showToast("Cadastro concluído com sucesso!")
                    // Aqui você pode enviar os dados para API ou banco local
                }
            }
            btnFinalizar.isEnabled = false
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}
