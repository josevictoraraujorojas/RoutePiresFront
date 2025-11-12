package com.example.routepiresfront.UI.menu

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.routepiresfront.R
import com.example.routepiresfront.databinding.ActivityMenubarBinding

class MenubarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenubarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenubarBinding.inflate(layoutInflater)
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
                R.id.configuracaoPassageiroFragment,
                R.id.editarPerfilFragment2,
                R.id.historicoCorridasFragment2,
                R.id.notificacaoFragment2 -> {
                    binding.menuInferior.menu.findItem(R.id.configuracaoPassageiroFragment).isChecked = true
                }

                // Selecionar Local / Criar Corrida / Buscar Motorista
                R.id.selecionarLocalFragment,
                R.id.criacaoCorridaFragment,
                R.id.buscaMotoristaFragment -> {
                    binding.menuInferior.menu.findItem(R.id.selecionarLocalFragment).isChecked = true
                }
            }
        }
    }
}