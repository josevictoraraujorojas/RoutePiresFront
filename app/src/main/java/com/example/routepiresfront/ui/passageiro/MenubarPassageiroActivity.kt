package com.example.routepiresfront.ui.passageiro

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.findNavController
import com.example.routepiresfront.R
import com.example.routepiresfront.databinding.ActivityMenubarPassageiroBinding

class MenubarPassageiroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenubarPassageiroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenubarPassageiroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userId = intent.getStringExtra("USER_ID")
        val bundle = Bundle().apply {
            putString("USER_ID", userId)
        }

        val navHostCorrida = findViewById<FragmentContainerView>(R.id.nav_host_corrida)
        val navHostAvaliacao = findViewById<FragmentContainerView>(R.id.nav_host_avaliacao)
        val navHostNegociacao = findViewById<FragmentContainerView>(R.id.nav_host_negociacao)
        val navHostConfiguracao = findViewById<FragmentContainerView>(R.id.nav_host_configuracao)

        navHostCorrida.post {
            navHostCorrida.findNavController().setGraph(R.navigation.nav_corrida, bundle)
        }
        navHostAvaliacao.post {
            navHostAvaliacao.findNavController().setGraph(R.navigation.nav_avaliacao, bundle)
        }
        navHostNegociacao.post {
            navHostNegociacao.findNavController().setGraph(R.navigation.nav_negociacao, bundle)
        }
        navHostConfiguracao.post {
            navHostConfiguracao.findNavController().setGraph(R.navigation.nav_configuracao, bundle)
        }

        showNavHost(R.id.bottom_corrida) // Show initial tab

        binding.menuInferior.setOnItemSelectedListener { item ->
            showNavHost(item.itemId)
            true
        }
    }

    private fun showNavHost(itemId: Int) {
        val hosts = mapOf(
            R.id.bottom_corrida to R.id.nav_host_corrida,
            R.id.bottom_avaliacao to R.id.nav_host_avaliacao,
            R.id.bottom_negociacao to R.id.nav_host_negociacao,
            R.id.bottom_configuracao to R.id.nav_host_configuracao
        )

        hosts.forEach { (menuId, navHostId) ->
            findViewById<View>(navHostId).visibility = if (itemId == menuId) View.VISIBLE else View.GONE
        }
    }
}