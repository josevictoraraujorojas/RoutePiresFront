package com.example.routepiresfront

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.routepiresfront.databinding.FragmentAguardandoInicioEntregaBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import java.text.SimpleDateFormat
import java.util.*

class AguardandoInicioEntregaFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentAguardandoInicioEntregaBinding? = null
    private val binding get() = _binding!!

    private lateinit var map: GoogleMap

    private var nomeSolicitante: String = "Karen Roe"
    private var avaliacaoSolicitante: Float = 4.8f
    private var pontosEntrega: Int = 1
    private var descricaoEntrega: String = "Perfect flat for 4 people. Peaceful and good location, close to bus stops and many restaurants."
    private var pesoKg: Int = 10
    private var tipoFragil: Boolean = true
    private lateinit var pontoPartida: LatLng
    private lateinit var pontoDestino: LatLng

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Aqui você pode receber os dados via arguments
        // arguments?.let {
        //     nomeSolicitante = it.getString("NOME_SOLICITANTE") ?: "Karen Roe"
        //     avaliacaoSolicitante = it.getFloat("AVALIACAO_SOLICITANTE", 4.8f)
        //     pontosEntrega = it.getInt("PONTOS_ENTREGA", 1)
        //     descricaoEntrega = it.getString("DESCRICAO_ENTREGA") ?: ""
        //     pesoKg = it.getInt("PESO_KG", 10)
        //     tipoFragil = it.getBoolean("TIPO_FRAGIL", true)
        // }

        pontoPartida = LatLng(-17.304889, -48.279548)
        pontoDestino = LatLng(-17.305980, -48.283300)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAguardandoInicioEntregaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        setupUI()

        setupListeners()
    }

    private fun setupUI() {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        binding.tvDataHora.text = dateFormat.format(Date())

        binding.tvPontosEntrega.text = "$pontosEntrega Ponto${if (pontosEntrega > 1) "s" else ""} de entrega"

        binding.tvDescricaoEntrega.text = descricaoEntrega

        binding.tvPeso.text = "PESO: ${pesoKg}KG"

        binding.tvNomeSolicitante.text = nomeSolicitante
        binding.tvAvaliacaoSolicitante.text = String.format("%.1f", avaliacaoSolicitante)

    }

    private fun setupListeners() {
        binding.btnVoltar.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.btnIniciarEntrega.setOnClickListener {
            Toast.makeText(requireContext(), "Iniciando entrega...", Toast.LENGTH_SHORT).show()
            // Aqui você navegaria para o fragmento de entrega em andamento
            // findNavController().navigate(R.id.action_aguardandoInicioEntrega_to_entregaEmAndamento)
        }

        binding.btnCancelarEntrega.setOnClickListener {
            Toast.makeText(requireContext(), "Cancelando entrega...", Toast.LENGTH_SHORT).show()
            parentFragmentManager.popBackStack()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        try {
            map.addMarker(
                MarkerOptions()
                    .position(pontoPartida)
                    .title("Ponto de Coleta")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
            )

            map.addMarker(
                MarkerOptions()
                    .position(pontoDestino)
                    .title("Ponto de Entrega")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
            )

            val polylineOptions = PolylineOptions()
                .add(pontoPartida)
                .add(pontoDestino)
                .color(resources.getColor(R.color.blue, null))
                .width(10f)
            map.addPolyline(polylineOptions)

            val boundsBuilder = LatLngBounds.Builder()
            boundsBuilder.include(pontoPartida)
            boundsBuilder.include(pontoDestino)

            val bounds = boundsBuilder.build()
            val padding = 150 // padding em pixels
            map.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding))

        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(requireContext(), "Erro ao carregar o mapa", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

