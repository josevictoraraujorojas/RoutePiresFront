package com.example.routepiresfront.ui.mototaxista

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.routepiresfront.R
import com.example.routepiresfront.databinding.ActivityMenubarMototaxistaBinding


class MenubarMototaxistaActivity: AppCompatActivity() {
    private lateinit var binding: ActivityMenubarMototaxistaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenubarMototaxistaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtém o NavHostFragment
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_principal) as NavHostFragment

        // Obtém o navController
        val navController = navHostFragment.navController

        // Liga o BottomNavigationView ao Navigation Component
        binding.menuInferior.setupWithNavController(navController)

        // Mantém o ícone correto selecionado para fragmentos filhos
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id) {
                // Configuração / Perfil
                R.id.configuracaoMotoristaFragment,
                R.id.historicoCorridasFragment,
                R.id.notificacaoFragment,
                R.id.editarPerfilFragment,
                R.id.perfilVeiculoOuPlacaFragment -> {
                    binding.menuInferior.menu.findItem(R.id.configuracaoMotoristaFragment).isChecked = true
                }

                R.id.negociacaoFragment,
                R.id.chatFragment,
                R.id.denunciaFragment -> {
                    binding.menuInferior.menu.findItem(R.id.negociacaoFragment).isChecked = true
                }


                R.id.corridaAndamentoFragment,
                R.id.corridaMototaxistaFragment2-> {
                    binding.menuInferior.menu.findItem(R.id.corridaMototaxistaFragment2).isChecked = true
                }
            }
        }
    }
}