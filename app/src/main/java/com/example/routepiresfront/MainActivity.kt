package com.example.routepiresfront

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.routepiresfront.databinding.ActivityCadastroMototaxistaBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCadastroMototaxistaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroMototaxistaBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Cadastro de Mototaxista - Passo 1/3"
            setDisplayHomeAsUpEnabled(true)
        }
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_voltar_custom)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        if (savedInstanceState == null) {
            // Exibe o Fragmento da Lista de Exercícios na inicialização
            replaceFragment(PrimeiroCadastroMototaxistaFragment(), R.id.fragment_cadastro)
        }
    }
    // Método para trocar fragmentos
    private fun replaceFragment(fragment: Fragment, containerId: Int) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(containerId, fragment)
        fragmentTransaction.commit()
    }
}