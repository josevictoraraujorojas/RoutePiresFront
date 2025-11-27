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

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Checa argumento para decidir se abre direto o iniciar corrida
        val abrirIniciarCorrida = arguments?.getBoolean("abrirIniciarCorrida") ?: false

        // Delay curto para garantir que o NavHost do BottomSheet esteja pronto
        Handler(Looper.getMainLooper()).postDelayed({
            if (abrirIniciarCorrida) {
                // Navega direto para "Aguardando Início Corrida"
                bottomSheetNavController.navigate(
                    R.id.aguardandoInicioCorridaFragment
                )
            } else {
                // Navega para a lista de corridas normalmente
                bottomSheetNavController.navigate(
                    R.id.listaCorridasFragment
                )
            }
        }, 200) // delay reduzido, suficiente para inicializar NavHost
    }
    fun abrirBottomSheetCorrida() {
        bottomSheetNavController.navigate(R.id.aguardandoInicioCorridaFragment)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
