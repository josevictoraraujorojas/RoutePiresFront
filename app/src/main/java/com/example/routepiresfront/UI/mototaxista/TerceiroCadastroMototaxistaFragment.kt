package com.example.routepiresfront.UI.mototaxista

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.routepiresfront.databinding.FragmentTerceiroCadastroMototaxistaBinding

class TerceiroCadastroMototaxistaFragment : Fragment() {

    private var _binding: FragmentTerceiroCadastroMototaxistaBinding? =null

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTerceiroCadastroMototaxistaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener {
            // Navega para finalizar o cadastro.
            // (activity as? MainActivity)?.navigateToSecondStep()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}