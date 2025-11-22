package com.example.routepiresfront.ui.mototaxista

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.fragment.NavHostFragment
import com.example.routepiresfront.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MenubarMototaxistaActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var navHostHome: FragmentContainerView
    private lateinit var navHostNegociacao: FragmentContainerView
    private lateinit var navHostAvaliacao: FragmentContainerView
    private lateinit var navHostConfiguracao: FragmentContainerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menubar_mototaxista)

        bottomNavigationView = findViewById(R.id.menuInferior)
        navHostHome = findViewById(R.id.nav_host_home_moto)
        navHostNegociacao = findViewById(R.id.nav_host_negociacao_moto)
        navHostAvaliacao = findViewById(R.id.nav_host_avaliacao_moto)
        navHostConfiguracao = findViewById(R.id.nav_host_configuracao_moto)

        val userId = intent.getStringExtra("USER_ID")
        val bundle = Bundle().apply {
            putString("USER_ID", userId) // Passa o ID para todos os grafos
        }

        // Encontra os NavHostFragments e define o grafo de navegação para cada um com o USER_ID
        val homeNavHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_home_moto) as NavHostFragment
        homeNavHostFragment.navController.setGraph(R.navigation.home_mototaxista_nav, bundle)

        val negociacaoNavHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_negociacao_moto) as NavHostFragment
        negociacaoNavHostFragment.navController.setGraph(R.navigation.negociacao_mototaxista_nav, bundle)

        val avaliacaoNavHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_avaliacao_moto) as NavHostFragment
        avaliacaoNavHostFragment.navController.setGraph(R.navigation.avaliacao_mototaxista_nav, bundle)

        val configuracaoNavHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_configuracao_moto) as NavHostFragment
        configuracaoNavHostFragment.navController.setGraph(R.navigation.configuracao_mototaxista_nav, bundle)

        // Lógica para mostrar/esconder as abas
        mostrarAba(navHostHome)

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_home -> mostrarAba(navHostHome)
                R.id.bottom_negociacao -> mostrarAba(navHostNegociacao)
                R.id.bottom_avaliacao -> mostrarAba(navHostAvaliacao)
                R.id.bottom_configuracao -> mostrarAba(navHostConfiguracao)
            }
            true
        }
    }

    private fun mostrarAba(aba: View) {
        val todasAbas = listOf(navHostHome, navHostNegociacao, navHostAvaliacao, navHostConfiguracao)
        todasAbas.forEach { it.visibility = if (it == aba) View.VISIBLE else View.GONE }
    }
}
