package com.example.routepiresfront

import android.os.Bundle
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

        btnVoltar.setOnClickListener { finish() }

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
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}
