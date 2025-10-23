package com.example.routepiresfront

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.example.routepiresfront.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // ViewBinding para acessar os componentes do layout de forma segura.
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupOverlay()
    }

    // Configura os cliques necessários para abrir e fechar a sobreposição.
    private fun setupOverlay() {
        binding.registerButton.setOnClickListener { toggleRegistrationOverlay(true) }
        binding.cancelButton.setOnClickListener { toggleRegistrationOverlay(false) }
        binding.registrationScrim.setOnClickListener { toggleRegistrationOverlay(false) }
    }

    // Exibe ou oculta a sobreposição conforme a interação do usuário.
    private fun toggleRegistrationOverlay(visible: Boolean) {
        binding.registrationOverlay.isVisible = visible
    }
}
