package com.example.routepiresfront.ui.mototaxista

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.routepiresfront.R
import com.example.routepiresfront.databinding.FragmentCorridaMototaxistaBinding
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior

class CorridaMototaxistaFragment : Fragment() {

    private var _binding: FragmentCorridaMototaxistaBinding? = null
    private val binding get() = _binding!!

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
    private lateinit var bottomSheetNavController: androidx.navigation.NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCorridaMototaxistaBinding.inflate(inflater, container, false)

        // Inicializa mapa
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapFragment) as? SupportMapFragment
        mapFragment?.getMapAsync { googleMap ->
            googleMap.uiSettings.isZoomControlsEnabled = false
            googleMap.uiSettings.isMyLocationButtonEnabled = true
        }

        // Inicializa BottomSheet
        bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet)
        bottomSheetBehavior.peekHeight = resources.getDimensionPixelSize(R.dimen.bottom_sheet_peek_height)
        bottomSheetBehavior.isHideable = false
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED

        // NavController do BottomSheet
        val navHostFragment = childFragmentManager
            .findFragmentById(R.id.bottomSheetContainer) as NavHostFragment
        bottomSheetNavController = navHostFragment.navController

        // Exibe lista de corridas após 2 segundos
        Handler(Looper.getMainLooper()).postDelayed({
            if (bottomSheetNavController.currentDestination?.id != R.id.listaCorridasFragment) {
                bottomSheetNavController.navigate(R.id.listaCorridasFragment)
            }
        }, 2000)

        return binding.root
    }

    /**
     * Mostra detalhes da corrida ou entrega
     */
    fun mostrarDetalhesCorrida(passageiro: Passageiro) {
        val destinationId = when (passageiro.tipo) {
            "corrida" -> R.id.selecionaCorridaFragment
            "entrega" -> R.id.selecionaEntregaFragment
            else -> null
        }

        destinationId?.let {
            bottomSheetNavController.navigate(it)
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    fun toggleBottomSheet() {
        if (::bottomSheetBehavior.isInitialized) {
            bottomSheetBehavior.state =
                if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED)
                    BottomSheetBehavior.STATE_COLLAPSED
                else
                    BottomSheetBehavior.STATE_EXPANDED
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
