package com.example.routepiresfront
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ProcurandoCorrida : AppCompatActivity() {

    private lateinit var tvNomePassageiro: TextView
    private lateinit var tvRating: TextView
    private lateinit var btnIniciarNegociacao: Button
    private lateinit var btnCancelarNegociacao: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_seleciona_corrida)

        inicializarViews()
        configurarDados()
        configurarBotoes()
    }

    private fun inicializarViews() {
        tvNomePassageiro = findViewById(R.id.tvNomePassageiro)
        tvRating = findViewById(R.id.tvRating)
        btnIniciarNegociacao = findViewById(R.id.btnIniciarNegociacao)
        btnCancelarNegociacao = findViewById(R.id.btnCancelarNegociacao)
    }

    private fun configurarDados() {
        tvNomePassageiro.text = "Karen Roe"
        tvRating.text = "4.8"
    }

    private fun configurarBotoes() {
        btnIniciarNegociacao.setOnClickListener {
        }

        btnCancelarNegociacao.setOnClickListener {
            finish()
        }
    }
}