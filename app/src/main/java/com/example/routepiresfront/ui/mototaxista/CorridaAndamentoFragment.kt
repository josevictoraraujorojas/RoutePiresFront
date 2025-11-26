package com.example.routepiresfront.ui.mototaxista

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.routepiresfront.databinding.FragmentCorridaAndamentoBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.example.routepiresfront.R
import com.example.routepiresfront.ui.mototaxista.viewmodel.CorridaMototaxistaViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class CorridaAndamentoFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentCorridaAndamentoBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CorridaMototaxistaViewModel by activityViewModels()

    private var googleMap: GoogleMap? = null

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

        setupObservers()
        setupListeners()
    }

    private fun setupObservers() {
        // Observa corrida atual para atualizar mapa
        viewModel.corridaAtual.observe(viewLifecycleOwner) { corrida ->
            corrida?.let {
                updateMap(it.pontoPartida?.let { origem ->
                    LatLng(origem.latitude, origem.longitude)
                }, it.pontoDestino?.let { destino ->
                    LatLng(destino.latitude, destino.longitude)
                })
            }
        }

        // Observa navegação para avaliação
        viewModel.navegarParaAvaliacao.observe(viewLifecycleOwner) { navegar ->
            if (navegar) {
                navegarParaAvaliacao()
                viewModel.resetarNavegacao()
            }
        }

        // Observa erros
        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
                viewModel.limparErro()
            }
        }

        // Observa loading
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.btnFinalizar.isEnabled = !isLoading
        }
    }

    private fun setupListeners() {
        // Botão finalizar corrida
        binding.btnFinalizar.setOnClickListener {
            viewModel.finalizarCorrida()
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

        // Atualiza mapa com dados da corrida atual
        viewModel.corridaAtual.value?.let { corrida ->
            updateMap(
                corrida.pontoPartida?.let { LatLng(it.latitude, it.longitude) },
                corrida.pontoDestino?.let { LatLng(it.latitude, it.longitude) }
            )
        }
    }

    private fun updateMap(origem: LatLng?, destino: LatLng?) {
        if (origem == null || destino == null) return

        googleMap?.apply {
            clear()

            // Adiciona marcadores
            addMarker(
                MarkerOptions()
                    .position(origem)
                    .title("Motorista")
            )
            addMarker(
                MarkerOptions()
                    .position(destino)
                    .title("Destino")
            )

            // Centraliza câmera entre os pontos
            val centro = LatLng(
                (origem.latitude + destino.latitude) / 2,
                (origem.longitude + destino.longitude) / 2
            )
            moveCamera(CameraUpdateFactory.newLatLngZoom(centro, 15f))

            // Exemplo de linha entre motorista e destino
            addPolyline(
                PolylineOptions()
                    .add(origem, destino)
                    .width(6f)
                    .color(R.color.primary)
            )
        }

        // Simula atualização de localização (em produção, usar LocationManager)
        // viewModel.atualizarLocalizacao(origem.latitude, origem.longitude)
    }

    private fun navegarParaAvaliacao() {
        findNavController().navigate(R.id.action_corridaAndamentoFragment_to_corridaMototaxistaFragment)

        val bottom = requireActivity().findViewById<BottomNavigationView>(R.id.menuInferior)
        bottom.selectedItemId = R.id.bottom_avaliacao

        val navHostFragment = requireActivity()
            .supportFragmentManager
            .findFragmentById(R.id.nav_host_avaliacao_moto) as? NavHostFragment

        navHostFragment?.navController?.navigate(R.id.action_global_avaliacaoFragment2)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
