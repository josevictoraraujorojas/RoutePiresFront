package com.example.routepiresfront.ui.passageiro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.routepiresfront.R
import com.example.routepiresfront.core.Resultado
import com.example.routepiresfront.data.model.passageiro.GeoPonto
import com.example.routepiresfront.data.model.passageiro.Localizacao
import com.example.routepiresfront.databinding.FragmentSelecaoLocalBinding
import com.example.routepiresfront.ui.passageiro.adapter.LocalSugestaoAdapter
import com.example.routepiresfront.ui.passageiro.viewmodel.SelecaoLocalViewModel
import com.example.routepiresfront.core.SessionManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import java.util.Date

class SelecionarLocalFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentSelecaoLocalBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SelecaoLocalViewModel by viewModels()

    private lateinit var map: GoogleMap
    private lateinit var adapter: LocalSugestaoAdapter
    private var origemMarker: Marker? = null
    private var destinoMarker: Marker? = null
    private var destinoNome: String? = null
    private var origemNome: String? = null

    private val passageiroId: String by lazy {
        arguments?.getString("passageiroId")?.trim().takeUnless { it.isNullOrEmpty() }
            ?: SessionManager.obterUsuarioId(requireContext())?.trim().takeUnless { it.isNullOrEmpty() }
            ?: ""
    }

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
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        // Inicializa o mapa
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // Configura o adapter e RecyclerView
        adapter = LocalSugestaoAdapter(locais.keys.toList()) { local ->
            val latLng = locais[local] ?: return@LocalSugestaoAdapter
            binding.searchView.setQuery(local, false)
            binding.layoutBusca.visibility = View.GONE
            definirDestino(latLng, local)
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
            if (passageiroId.isBlank()) {
                Snackbar.make(binding.root, "Usuário não identificado. Faça login novamente.", Snackbar.LENGTH_LONG).show()
            } else {
                viewModel.solicitarCorrida(passageiroId = passageiroId)
            }
        }

        observarEstado()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        val origemInicial = locais.entries.first()
        definirOrigem(origemInicial.value, origemInicial.key)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(origemInicial.value, 15f))

        map.setOnMapLongClickListener { latLng ->
            definirOrigem(latLng, "Origem definida no mapa")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun definirOrigem(latLng: LatLng, label: String) {
        if (!::map.isInitialized) return
        origemNome = label
        origemMarker?.remove()
        origemMarker = map.addMarker(
            MarkerOptions()
                .position(latLng)
                .title("Origem: $label")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
        )
        viewModel.definirOrigem(
            Localizacao(
                localizacao = GeoPonto(latLng.latitude, latLng.longitude),
                timestamp = Date()
            )
        )
        binding.tvOrigemSelecionada.text = "Origem: $label"
    }

    private fun definirDestino(latLng: LatLng, label: String) {
        if (!::map.isInitialized) return
        destinoNome = label
        destinoMarker?.remove()
        destinoMarker = map.addMarker(
            MarkerOptions()
                .position(latLng)
                .title("Destino: $label")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
        )
        viewModel.definirDestino(
            Localizacao(
                localizacao = GeoPonto(latLng.latitude, latLng.longitude),
                timestamp = Date()
            )
        )
        binding.tvDestinoSelecionado.text = "Destino: $label"
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16f))
    }

    private fun observarEstado() {
        viewModel.mensagem.observe(viewLifecycleOwner) { mensagem ->
            mensagem?.let {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
                viewModel.limparMensagem()
            }
        }

        viewModel.resultadoSolicitacao.observe(viewLifecycleOwner) { resultado ->
            when (resultado) {
                is Resultado.Sucesso -> {
                    findNavController().navigate(
                        R.id.action_selecionarLocalFragment_to_criacaoCorridaFragment
                    )
                    viewModel.limparResultado()
                }

                is Resultado.Erro -> viewModel.limparResultado()
                Resultado.Carregando, null -> {}
            }
        }
    }
}
