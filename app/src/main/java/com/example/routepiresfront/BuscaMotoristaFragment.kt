package com.example.routepiresfront

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView

class BuscaMotoristaFragment : Fragment() {

    private lateinit var searchIcon: ImageView
    private lateinit var procurandoButton: Button
    private lateinit var cancelButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_busca_motorista, container, false)
        
        // Inicializar views
        searchIcon = view.findViewById(R.id.search_icon)
        procurandoButton = view.findViewById(R.id.procurando_corrida_button)
        cancelButton = view.findViewById(R.id.cancel_button)
        
        // Iniciar animações
        startAnimations()
        
        // Configurar listener do botão cancelar
        cancelButton.setOnClickListener {
            // Adicionar lógica de cancelamento aqui
            stopAnimations()
        }
        
        return view
    }
    
    private fun startAnimations() {
        // Animação de rotação para o ícone de busca
        val rotateAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_animation)
        searchIcon.startAnimation(rotateAnimation)
        
        // Animação de pulse para o botão "Procurando Corrida"
        val pulseAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.pulse_animation)
        procurandoButton.startAnimation(pulseAnimation)
    }
    
    private fun stopAnimations() {
        searchIcon.clearAnimation()
        procurandoButton.clearAnimation()
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        stopAnimations()
    }
}