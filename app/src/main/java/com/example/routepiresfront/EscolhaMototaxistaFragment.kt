package com.example.routepiresfront

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.routepiresfront.databinding.FragmentEscolhaMototaxistaBinding


class EscolhaMototaxistaFragment : Fragment() {

    private var _binding: FragmentEscolhaMototaxistaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEscolhaMototaxistaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.simButton.setOnClickListener {
            // logica para aceitar a corrida
        }

        binding.naoButton.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()

        }

    }

}