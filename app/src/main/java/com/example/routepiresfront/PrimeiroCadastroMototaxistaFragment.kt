package com.example.routepiresfront

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.routepiresfront.databinding.FragmentCadastroMototaxista1Binding


class PrimeiroCadastroMototaxistaFragment : Fragment() {

    private var _binding: FragmentCadastroMototaxista1Binding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCadastroMototaxista1Binding.inflate(inflater, container, false)
        return binding.root
    }

}