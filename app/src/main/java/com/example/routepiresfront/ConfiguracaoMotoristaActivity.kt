package com.example.routepiresfront

import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.switchmaterial.SwitchMaterial

/**
 * Tela de configuracao do motorista.
 * Exibe dados basicos, permite controlar disponibilidade e oferece atalhos para outras areas.
 */
class ConfiguracaoMotoristaActivity : AppCompatActivity() {

    private lateinit var switchDisponibilidade: SwitchMaterial
    private lateinit var menuInferior: BottomNavigationView
    private lateinit var fabEditarFoto: FloatingActionButton
    private lateinit var avatar: ImageView
    private lateinit var opcaoEditar: LinearLayout
    private lateinit var opcaoVeiculo: LinearLayout
    private lateinit var opcaoHistorico: LinearLayout
    private lateinit var opcaoNotificacoes: LinearLayout
    private lateinit var opcaoSair: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configuracao_motorista)

        inicializarComponentes()
        configurarMenuInferior()
        configurarListeners()
    }

    /** Localiza os componentes usados na tela. */
    private fun inicializarComponentes() {
        switchDisponibilidade = findViewById(R.id.swDisponibilidade)
        menuInferior = findViewById(R.id.menuInferior)
        fabEditarFoto = findViewById(R.id.fabEditarFoto)
        avatar = findViewById(R.id.ivAvatar)
        opcaoEditar = findViewById(R.id.opcaoEditar)
        opcaoVeiculo = findViewById(R.id.opcaoVeiculo)
        opcaoHistorico = findViewById(R.id.opcaoHistorico)
        opcaoNotificacoes = findViewById(R.id.opcaoNotificacoes)
        opcaoSair = findViewById(R.id.opcaoSair)
    }

    /** Configura o menu inferior, destacando a aba de configuracao. */
    private fun configurarMenuInferior() {
        menuInferior.selectedItemId = R.id.navigation_configuracao
        menuInferior.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_configuracao -> true
                else -> {
                    exibirMensagemPlaceholder(
                        getString(
                            R.string.configuracao_mensagem_placeholder,
                            item.title.toString()
                        )
                    )
                    true
                }
            }
        }
        menuInferior.setOnItemReselectedListener { item ->
            if (item.itemId != R.id.navigation_configuracao) {
                exibirMensagemPlaceholder(
                    getString(
                        R.string.configuracao_mensagem_placeholder,
                        item.title.toString()
                    )
                )
            }
        }
    }

    /** Define as acoes dos botoes, exibindo mensagens temporarias. */
    private fun configurarListeners() {
        fabEditarFoto.setOnClickListener {
            exibirMensagemParaOpcao(R.string.configuracao_acao_editar)
        }

        avatar.setOnClickListener {
            exibirMensagemParaOpcao(R.string.configuracao_acao_editar)
        }

        opcaoEditar.setOnClickListener {
            exibirMensagemParaOpcao(R.string.configuracao_opcao_editar)
        }

        opcaoVeiculo.setOnClickListener {
            exibirMensagemParaOpcao(R.string.configuracao_opcao_veiculo)
        }

        opcaoHistorico.setOnClickListener {
            exibirMensagemParaOpcao(R.string.configuracao_opcao_historico)
        }

        opcaoNotificacoes.setOnClickListener {
            exibirMensagemParaOpcao(R.string.configuracao_opcao_notificacoes)
        }

        opcaoSair.setOnClickListener {
            exibirMensagemParaOpcao(R.string.configuracao_opcao_sair)
        }

        switchDisponibilidade.setOnCheckedChangeListener { _, isChecked ->
            val mensagem = if (isChecked) {
                getString(R.string.configuracao_status_disponivel)
            } else {
                getString(R.string.configuracao_status_indisponivel)
            }
            exibirMensagemPlaceholder(mensagem)
        }
    }

    private fun exibirMensagemParaOpcao(@StringRes textoRes: Int) {
        exibirMensagemPlaceholder(
            getString(
                R.string.configuracao_mensagem_placeholder,
                getString(textoRes)
            )
        )
    }

    private fun exibirMensagemPlaceholder(mensagem: CharSequence) {
        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show()
    }
}
