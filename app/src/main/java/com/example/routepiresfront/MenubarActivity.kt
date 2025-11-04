package com.example.routepiresfront

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.routepiresfront.databinding.ActivityMenubarBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * Activity responsavel pela navegacao inferior do app.
 */
class MenubarActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenubarBinding
    private lateinit var menuInferior: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenubarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            // Abre a aba de perfil como tela inicial.
            replaceFragment(
                ConfiguracaoPassageiroFragment(),
                R.id.fragment_principal,
                addToBackStack = false
            )
        }

        menuInferior = findViewById(R.id.menuInferior)
        configurarMenuInferior()
    }

    private fun configurarMenuInferior() {
        menuInferior.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_negociacao -> {
                    // Mostra a tela de negociacoes.
                    replaceFragment(
                        NegociacaoFragment(),
                        R.id.fragment_principal,
                        addToBackStack = false
                    )
                    true
                }

                R.id.navigation_configuracao -> {
                    // Mantem a logica existente de configuracao do usuario.
                    replaceFragment(
                        ConfiguracaoPassageiroFragment(),
                        R.id.fragment_principal,
                        addToBackStack = false
                    )
                    true
                }

                else -> {
                    // Exibe uma mensagem temporaria para abas ainda nao implementadas.
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
            if (item.itemId != R.id.navigation_configuracao && item.itemId != R.id.navigation_negociacao) {
                exibirMensagemPlaceholder(
                    getString(
                        R.string.configuracao_mensagem_placeholder,
                        item.title.toString()
                    )
                )
            }
        }

        menuInferior.selectedItemId = R.id.navigation_configuracao
    }

    fun replaceFragment(
        fragment: Fragment,
        containerId: Int,
        addToBackStack: Boolean = true
    ) {
        // Centraliza a troca de fragmentos garantindo o uso uniforme da back stack.
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(containerId, fragment)
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(null)
        }
        fragmentTransaction.commit()
    }

    private fun exibirMensagemPlaceholder(mensagem: CharSequence) {
        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show()
    }
}
