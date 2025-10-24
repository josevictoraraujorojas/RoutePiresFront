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
            replaceFragment(PrimeiroCadastroMototaxistaFragment(), R.id.fragment_cadastro, addToBackStack = false)
        }

        supportFragmentManager.addOnBackStackChangedListener { updateTitle() }
    }

    // Atualiza o título da ActionBar com base no fragmento atualmente visível.
    private fun updateTitle() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_cadastro)
        when (currentFragment) {
            is PrimeiroCadastroMototaxistaFragment -> supportActionBar?.title =
                "Cadastro de Mototaxista - Passo 1/3"
            is SegundoCadastroMototaxista -> supportActionBar?.title =
                "Cadastro de Mototaxista - Passo 2/3"
        }
    }

    // Lida com o evento de clique no botão "Up" (voltar) na ActionBar.
    override fun onSupportNavigateUp(): Boolean {
        if (supportFragmentManager.popBackStackImmediate()) {
            return true
        }
        return super.onSupportNavigateUp()
    }

    //Substitui o fragmento atual em um container por um novo.
    private fun replaceFragment(fragment: Fragment, containerId: Int, addToBackStack: Boolean = true) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(containerId, fragment)
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(null)
        }
        fragmentTransaction.commit()
    }

    /**
     * Navega para o segundo passo do fluxo de cadastro.
     * Esta função é chamada a partir do primeiro fragmento de cadastro.
     */
    fun navigateToSecondStep() {
        replaceFragment(SegundoCadastroMototaxista(), R.id.fragment_cadastro)
    }
}