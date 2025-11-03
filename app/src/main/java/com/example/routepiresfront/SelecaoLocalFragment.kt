package com.example.routepiresfront

import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.routepiresfront.databinding.FragmentSelecaoLocalBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.android.libraries.places.api.model.Place
import java.util.*

class SelecaoLocalFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentSelecaoLocalBinding? = null
    private val binding get() = _binding!!

    private lateinit var map: GoogleMap
    private var selectedLatLng: LatLng? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelecaoLocalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!Places.isInitialized()) {
            Places.initialize(requireContext(), BuildConfig.MAPS_API_KEY)
        }

        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        setupAutocomplete()
        setupButton()
    }

    private fun setupAutocomplete() {
        val autocompleteFragment = childFragmentManager
            .findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment

        autocompleteFragment.setPlaceFields(
            listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG)
        )

        autocompleteFragment.setHint("Rua Exemplo")

        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                place.latLng?.let {
                    selectedLatLng = it
                    map.clear()
                    map.addMarker(MarkerOptions().position(it).title(place.name))
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(it, 15f))
                }
            }

            override fun onError(status: com.google.android.gms.common.api.Status) {
                Toast.makeText(
                    requireContext(),
                    "Erro ao buscar local: ${status.statusMessage ?: "Tente novamente"}",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    private fun setupButton() {
        binding.btnSelecionar.setOnClickListener {
            selectedLatLng?.let {
                val geocoder = Geocoder(requireContext(), Locale.getDefault())
                val address = geocoder.getFromLocation(it.latitude, it.longitude, 1)
                val localName = address?.firstOrNull()?.getAddressLine(0) ?: "Local selecionado"
                // Aqui você pode enviar o resultado de volta ou salvar
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        val defaultLocation = LatLng(-23.5505, -46.6333)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 12f))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
