package com.example.routepiresfront
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ProcurandoEntregaActivity : AppCompatActivity() {

    private lateinit var tvNomeSolicitante: TextView
    private lateinit var tvRating: TextView
    private lateinit var tvDescricao: TextView
    private lateinit var btnAceitarNegociacao: Button
    private lateinit var btnCancelarNegociacao: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_procurando_entrega)

        inicializarViews()
        configurarDados()
        configurarBotoes()
    }

    private fun inicializarViews() {
        tvNomeSolicitante = findViewById(R.id.tvNomeSolicitante)
        tvRating = findViewById(R.id.tvRating)
        tvDescricao = findViewById(R.id.tvDescricao)
        btnAceitarNegociacao = findViewById(R.id.btnAceitarNegociacao)
        btnCancelarNegociacao = findViewById(R.id.btnCancelarNegociacao)
    }

    private fun configurarDados() {
        tvNomeSolicitante.text = "Karen Roe"
        tvRating.text = "4.8"
        tvDescricao.text = "Perfect flat for 4 people. Peaceful and good location, close to bus stops and many restaurants."
    }

    private fun configurarBotoes() {
        btnAceitarNegociacao.setOnClickListener {

        }

        btnCancelarNegociacao.setOnClickListener {

            finish()
        }
    }
}