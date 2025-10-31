package com.example.routepiresfront

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.routepiresfront.databinding.ActivityMenubarBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MenubarActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenubarBinding
    private lateinit var menuInferior: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenubarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            // Exibe o Fragmento da Lista de Exercícios na inicialização
            replaceFragment(ConfiguracaoMotoristaFragment(), R.id.fragment_principal, addToBackStack = false)
        }
        menuInferior = findViewById(R.id.menuInferior)
        configurarMenuInferior()
    }

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

    fun replaceFragment(fragment: Fragment, containerId: Int, addToBackStack: Boolean = true) {
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