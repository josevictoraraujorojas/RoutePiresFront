package com.example.routepiresfront

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
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
    }
}
