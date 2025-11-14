package com.example.routepiresfront.ui.mototaxista

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.routepiresfront.databinding.FragmentCorridaAndamentoBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.example.routepiresfront.R

class CorridaAndamentoFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentCorridaAndamentoBinding? = null
    private val binding get() = _binding!!

    private var googleMap: GoogleMap? = null

    // Exemplo de coordenadas (poderão vir da API ou GPS do motorista)
    private val motoristaLocation = LatLng(-16.678, -49.626) // exemplo: Pires do Rio
    private val destinoLocation = LatLng(-16.675, -49.620)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCorridaAndamentoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializa o fragmento do mapa
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
            ?: SupportMapFragment.newInstance().also {
                childFragmentManager.beginTransaction()
                    .replace(R.id.map, it)
                    .commit()
            }
        mapFragment.getMapAsync(this)

        // Botão finalizar corrida
        binding.btnFinalizar.setOnClickListener {
            // Ação futura para finalizar a corrida
        }
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        // Configurações básicas do mapa
        googleMap?.apply {
            uiSettings.isZoomControlsEnabled = true
            uiSettings.isMyLocationButtonEnabled = true
            // Se quiser ativar a localização do dispositivo, lembre-se de pedir permissão antes
            // isMyLocationEnabled = true
        }

        // Adiciona marcadores
        googleMap?.addMarker(
            MarkerOptions()
                .position(motoristaLocation)
                .title("Motorista")
        )
        googleMap?.addMarker(
            MarkerOptions()
                .position(destinoLocation)
                .title("Destino")
        )

        // Centraliza câmera entre os pontos
        val centro = LatLng(
            (motoristaLocation.latitude + destinoLocation.latitude) / 2,
            (motoristaLocation.longitude + destinoLocation.longitude) / 2
        )
        googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(centro, 15f))

        // Exemplo de linha entre motorista e destino
        googleMap?.addPolyline(
            PolylineOptions()
                .add(motoristaLocation, destinoLocation)
                .width(6f)
                .color(resources.getColor(R.color.blue, null))
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
