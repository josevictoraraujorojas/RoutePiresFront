package com.example.routepiresfront

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.routepiresfront.databinding.ActivityCadastroMototaxistaBinding

class CadastroMototaxistaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCadastroMototaxistaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroMototaxistaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_cadastro) as NavHostFragment
        val navController = navHostFragment.navController

        val appBarConfiguration = AppBarConfiguration(emptySet())
        setupActionBarWithNavController(navController, appBarConfiguration)

        // Função para setar o ícone e ação default
        fun applyCustomBack(onClick: () -> Unit) {
            binding.toolbar.navigationIcon =
                ContextCompat.getDrawable(this, R.drawable.ic_voltar_custom)
            binding.toolbar.setNavigationOnClickListener { onClick() }
        }

        // Listener das mudanças de tela
        navController.addOnDestinationChangedListener { _, destination, _ ->

            when (destination.id) {

                // Primeira tela → botão fecha Activity
                R.id.primeiroCadastroMototaxistaFragment -> {
                    binding.toolbar.title = "Cadastro - Passo 1/3"
                    applyCustomBack { finish() }
                }

                // Nas outras telas → voltar navegação
                R.id.segundoCadastroMototaxista -> {
                    binding.toolbar.title = "Cadastro - Passo 2/3"
                    applyCustomBack { navController.navigateUp() }
                }

                R.id.terceiroCadastroMototaxistaFragment -> {
                    binding.toolbar.title = "Cadastro - Passo 3/3"
                    applyCustomBack { navController.navigateUp() }
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
