package com.example.routepiresfront.ui.mototaxista

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.routepiresfront.R
import com.example.routepiresfront.databinding.FragmentCorridaMototaxistaBinding
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior

class CorridaMototaxistaFragment : Fragment() {

    private var _binding: FragmentCorridaMototaxistaBinding? = null
    private val binding get() = _binding!!

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCorridaMototaxistaBinding.inflate(inflater, container, false)

        // Inicializa o mapa
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapFragment)
                as? SupportMapFragment
        mapFragment?.getMapAsync { googleMap ->
            googleMap.uiSettings.isZoomControlsEnabled = false
            googleMap.uiSettings.isMyLocationButtonEnabled = true
        }

        // Configura o BottomSheet
        bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet)
        bottomSheetBehavior.peekHeight =
            resources.getDimensionPixelSize(R.dimen.bottom_sheet_peek_height) // altura mínima (metade da tela)
        bottomSheetBehavior.isHideable = false
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED // começa metade da tela

        // Adiciona o fragmento da lista dentro do BottomSheet após 2 segundos
        Handler(Looper.getMainLooper()).postDelayed({
            childFragmentManager.beginTransaction()
                .replace(R.id.bottomSheetContainer, ListaCorridasFragment())
                .commit()
        }, 2000)

        return binding.root
    }

    fun toggleBottomSheet() {
        if (::bottomSheetBehavior.isInitialized) {
            bottomSheetBehavior.state =
                if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED) {
                    BottomSheetBehavior.STATE_COLLAPSED
                } else {
                    BottomSheetBehavior.STATE_EXPANDED
                }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
