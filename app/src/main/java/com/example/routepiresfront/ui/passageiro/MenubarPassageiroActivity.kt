package com.example.routepiresfront.ui.passageiro

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.routepiresfront.R
import com.example.routepiresfront.databinding.ActivityMenubarPassageiroBinding

class MenubarPassageiroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenubarPassageiroBinding

    private var currentNavId = R.id.bottom_corrida

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenubarPassageiroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBottomNav()

        // Deixa a aba Corrida visível inicialmente
        showNavHost(R.id.bottom_corrida)
    }

    private fun setupBottomNav() {

        binding.menuInferior.setOnItemSelectedListener { item ->

            if (currentNavId == item.itemId) return@setOnItemSelectedListener true

            currentNavId = item.itemId
            showNavHost(item.itemId)

            true
        }
    }

    private fun showNavHost(itemId: Int) {

        val hosts = listOf(
            R.id.nav_host_corrida,
            R.id.nav_host_avaliacao,
            R.id.nav_host_negociacao,
            R.id.nav_host_configuracao
        )

        hosts.forEach { id ->
            findViewById<View>(id).visibility =
                if (id == navHostForMenu(itemId)) View.VISIBLE else View.GONE
        }
    }

    private fun navHostForMenu(itemId: Int): Int {
        return when (itemId) {
            R.id.bottom_corrida -> R.id.nav_host_corrida
            R.id.bottom_avaliacao -> R.id.nav_host_avaliacao
            R.id.bottom_negociacao -> R.id.nav_host_negociacao
            R.id.bottom_configuracao -> R.id.nav_host_configuracao
            else -> R.id.nav_host_corrida
        }
    }
}
