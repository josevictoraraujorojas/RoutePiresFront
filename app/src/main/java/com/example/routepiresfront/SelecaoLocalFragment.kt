package com.example.routepiresfront

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.routepiresfront.databinding.FragmentSelecaoLocalBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class SelecionarLocalFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentSelecaoLocalBinding? = null
    private val binding get() = _binding!!

    private lateinit var map: GoogleMap
    private lateinit var adapter: LocalSugestaoAdapter

    private val locais = mapOf(
        "Praça Central" to LatLng(-17.304889, -48.279548),
        "Rodoviária de Pires do Rio" to LatLng(-17.303458, -48.276640),
        "IF Goiano" to LatLng(-17.305980, -48.283300),
        "Supermercado Mega" to LatLng(-17.300890, -48.281210),
        "Hospital Municipal" to LatLng(-17.304130, -48.277900),
        "Prefeitura Municipal" to LatLng(-17.304720, -48.278540)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelecaoLocalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializa o mapa
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // Configura o adapter e RecyclerView
        adapter = LocalSugestaoAdapter(locais.keys.toList()) { local ->
            val latLng = locais[local] ?: return@LocalSugestaoAdapter
            binding.searchView.setQuery(local, false)
            binding.layoutBusca.visibility = View.GONE
            map.clear()
            map.addMarker(MarkerOptions().position(latLng).title(local))
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16f))
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        // Mostrar/esconder sugestões enquanto digita
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false
            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter(newText ?: "")
                binding.layoutBusca.visibility =
                    if (newText.isNullOrEmpty()) View.GONE else View.VISIBLE
                return true
            }
        })

        // Botão "Selecionar"
        binding.btnSelecionar.setOnClickListener {
            Toast.makeText(requireContext(), "Local selecionado!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        val piresDoRio = LatLng(-17.3019, -48.2795)
        map.addMarker(MarkerOptions().position(piresDoRio).title("Pires do Rio"))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(piresDoRio, 15f))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
