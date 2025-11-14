package com.example.routepiresfront.ui.mototaxista

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
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

        // Inicializa views
        bottomNavigationView = findViewById(R.id.menuInferior)
        navHostHome = findViewById(R.id.nav_host_home_moto)
        navHostNegociacao = findViewById(R.id.nav_host_negociacao_moto)
        navHostAvaliacao = findViewById(R.id.nav_host_avaliacao_moto)
        navHostConfiguracao = findViewById(R.id.nav_host_configuracao_moto)

        // Mostrar a aba inicial (Home)
        mostrarAba(navHostHome)

        // Configura BottomNavigationView
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

    /**
     * Exibe apenas a aba passada e esconde as demais
     */
    private fun mostrarAba(aba: FragmentContainerView) {
        val todasAbas = listOf(navHostHome, navHostNegociacao, navHostAvaliacao, navHostConfiguracao)
        for (navHost in todasAbas) {
            navHost.visibility = if (navHost == aba) View.VISIBLE else View.GONE
        }
    }

    fun abrirCorridaSelecionada(idCorrida: String) {
        // Garante que a aba Corrida está selecionada
        val bottom = findViewById<BottomNavigationView>(R.id.menuInferior)
        bottom.selectedItemId = R.id.bottom_corrida

        // Encontra o fragmento CorridaMototaxistaFragment
        val corridaFragment = supportFragmentManager.findFragmentById(R.id.corrida_mototaxista_nav)
            ?.childFragmentManager
            ?.fragments
            ?.firstOrNull { it is CorridaMototaxistaFragment } as? CorridaMototaxistaFragment

        // Abre o BottomSheet passando o ID da corrida
        corridaFragment?.abrirBottomSheetCorrida()
    }


    /**
     * Função utilitária para navegar dentro de uma aba específica
     */
    fun navegarNaAba(navHost: FragmentContainerView, destinoId: Int) {
        val navController = supportFragmentManager.findFragmentById(navHost.id)?.findNavController()
        navController?.navigate(destinoId)
    }
}
