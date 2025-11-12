package com.example.routepiresfront.ui.passageiro

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.routepiresfront.R
import com.example.routepiresfront.model.Mototaxista
import com.example.routepiresfront.ui.passageiro.adapter.MototaxistaAdapter
import com.example.routepiresfront.databinding.FragmentBuscaMotoristaBinding

class BuscaMotoristaFragment : Fragment() {

    private var _binding: FragmentBuscaMotoristaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBuscaMotoristaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startAnimations()

        // Simula o tempo de busca (ex: 4 segundos)
        Handler(Looper.getMainLooper()).postDelayed({
            mostrarResultados()
        }, 4000)

        binding.cancelButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun startAnimations() {
        val rotateAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_animation)
        binding.searchIcon.startAnimation(rotateAnimation)

        val pulseAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.pulse_animation)
        binding.procurandoCorridaButton.startAnimation(pulseAnimation)
    }

    private fun stopAnimations() {
        binding.searchIcon.clearAnimation()
        binding.procurandoCorridaButton.clearAnimation()
    }

    private fun mostrarResultados() {
        stopAnimations()

        // Esconde as animações e mostra o RecyclerView
        binding.layoutAnimacoes.visibility = View.GONE
        binding.recyclerViewMototaxistas.visibility = View.VISIBLE

        // Dados de exemplo (você pode depois puxar da API)
        val mototaxistas = listOf(
            Mototaxista("João Silva", 4.8f, R.drawable.ic_launcher_foreground),
            Mototaxista("Carlos Souza", 4.5f, R.drawable.ic_launcher_foreground),
            Mototaxista("Marcos Lima", 4.9f, R.drawable.ic_launcher_foreground)
        )

        // Configura o RecyclerView
        val adapter = MototaxistaAdapter(mototaxistas)
        binding.recyclerViewMototaxistas.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewMototaxistas.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        stopAnimations()
        _binding = null
    }
}