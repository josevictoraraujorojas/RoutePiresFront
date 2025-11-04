package com.example.routepiresfront

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
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

        binding.cancelButton.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
            stopAnimations()
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

    override fun onDestroyView() {
        super.onDestroyView()
        stopAnimations()
        _binding = null
    }
}
