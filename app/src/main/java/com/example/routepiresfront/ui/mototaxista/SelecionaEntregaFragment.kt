package com.example.routepiresfront.ui.mototaxista

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.routepiresfront.R
import com.example.routepiresfront.databinding.FragmentSelecionaEntregaBinding

class SelecionaEntregaFragment : Fragment() {
    private var _binding: FragmentSelecionaEntregaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelecionaEntregaBinding.inflate(inflater, container, false)

        binding.btnCancelarNegociacao.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        return binding.root
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
