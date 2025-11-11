package com.example.routepiresfront

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.routepiresfront.databinding.FragmentBuscaMotoristaBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.card.MaterialCardView

/**
 * Mostra o mapa de busca junto com o bottom sheet que lista mototaxistas disponíveis.
 * Simula o carregamento inicial antes de exibir a lista e permite alternar tipo do mapa.
 */
class BuscaMotoristaFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentBuscaMotoristaBinding? = null
    private val binding get() = _binding!!

    private var mapa: GoogleMap? = null
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<MaterialCardView>
    private lateinit var mototaxistaAdapter: MototaxistaAdapter

    // Handler usado para simular um retorno assíncrono da API.
    private val handler = Handler(Looper.getMainLooper())
    private val mostrarResultadosRunnable = Runnable { mostrarResultados() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBuscaMotoristaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupMap()
        setupBottomSheet()
        setupRecyclerView()
        setupMapTypeToggle()
        showLoadingState()

        binding.cancelSearchButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun setupMap() {
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.mapFragment) as? SupportMapFragment
                ?: return
        mapFragment.getMapAsync(this)
    }

    private fun setupBottomSheet() {
        // BottomSheetBehavior controla colapso/expansão e animação do ícone central.
        bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        bottomSheetBehavior.addBottomSheetCallback(object :
                BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                val rotation = if (newState == BottomSheetBehavior.STATE_EXPANDED) 180f else 0f
                binding.expandIcon.animate().rotation(rotation).setDuration(180).start()
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                if (slideOffset >= 0) {
                    binding.expandIcon.rotation = slideOffset * 180f
                }
            }
        })

        val toggleListener = View.OnClickListener { toggleBottomSheet() }
        binding.bottomSheetHandle.setOnClickListener(toggleListener)
        binding.expandIcon.setOnClickListener(toggleListener)
    }

    private fun toggleBottomSheet() {
        val nextState =
            if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED) {
                BottomSheetBehavior.STATE_COLLAPSED
            } else {
                BottomSheetBehavior.STATE_EXPANDED
            }
        bottomSheetBehavior.state = nextState
    }

    private fun setupRecyclerView() {
        // Lista fixa por enquanto, mas o adapter já aceita atualizações dinâmicas.
        mototaxistaAdapter = MototaxistaAdapter()
        binding.mototaxistasRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mototaxistaAdapter
            setHasFixedSize(true)
        }
    }

    private fun setupMapTypeToggle() {
        binding.mapTypeToggle.check(R.id.mapButton)
        binding.mapTypeToggle.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (!isChecked) return@addOnButtonCheckedListener
            // Ajusta o tipo do mapa mantendo estado entre toques.
            mapa?.mapType = when (checkedId) {
                R.id.satelliteButton -> GoogleMap.MAP_TYPE_SATELLITE
                else -> GoogleMap.MAP_TYPE_NORMAL
            }
        }
    }

    private fun showLoadingState() {
        // Mostra o loader enquanto esperamos o "retorno" da busca simulada.
        binding.loadingContainer.isVisible = true
        binding.mototaxistasRecyclerView.isVisible = false
        handler.postDelayed(mostrarResultadosRunnable, 2800)
    }

    private fun mostrarResultados() {
        // Mock com nomes fixos até termos integração com API.
        val mototaxistas = listOf(
            Mototaxista("Haley James", 5f, R.drawable.avatar_pic),
            Mototaxista("Nathan Scott", 4.5f, R.drawable.avatar_pic),
            Mototaxista("Brooke Davis", 4.8f, R.drawable.avatar_pic),
            Mototaxista("Jamie Scott", 4.6f, R.drawable.avatar_pic),
            Mototaxista("Marvin McFadden", 4.9f, R.drawable.avatar_pic),
            Mototaxista("Antwon Taylor", 4.7f, R.drawable.avatar_pic)
        )

        mototaxistaAdapter.submitList(mototaxistas)
        binding.loadingContainer.isVisible = false
        binding.mototaxistasRecyclerView.isVisible = true
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mapa = googleMap.apply {
            uiSettings.isCompassEnabled = false
            uiSettings.isMapToolbarEnabled = false
        }

        val piresDoRio = LatLng(-17.3019, -48.2795)
        // Foca a câmera na região central e adiciona marcador genérico.
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(piresDoRio, 14.5f))
        googleMap.addMarker(
            MarkerOptions()
                .position(piresDoRio)
                .title(getString(R.string.procurando_corrida))
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(mostrarResultadosRunnable)
        _binding = null
    }
}
