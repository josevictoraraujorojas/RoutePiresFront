package com.example.routepiresfront.UI.mototaxista

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.routepiresfront.R
import com.example.routepiresfront.databinding.FragmentCorridaAndamentoBinding
import com.google.android.gms.maps.SupportMapFragment

class CorridaAndamentoFragment : Fragment() {

    private lateinit var binding: FragmentCorridaAndamentoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_corrida_andamento, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configura o mapa dinamicamente
        val mapFragment = childFragmentManager.findFragmentById(R.id.map)
            ?: SupportMapFragment.newInstance().also {
                childFragmentManager.beginTransaction()
                    .replace(R.id.map, it)
                    .commit()
            }


        // Ações simples de UI
        binding.btnVoltar.setOnClickListener {
        }

        binding.btnFinalizar.setOnClickListener {

        }
    }
}