package com.example.routepiresfront

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

// Fragmento responsável por exibir um mapa e permitir que o usuário selecione um local.
class SelecionarLocalFragment : Fragment(), OnMapReadyCallback {

    // View Binding para acessar os componentes do layout de forma segura.
    private var _binding: FragmentSelecaoLocalBinding? = null
    private val binding get() = _binding!!

    // Objeto do Google Maps que será inicializado quando o mapa estiver pronto.
    private lateinit var map: GoogleMap

    // Infla o layout do fragmento (fragment_selecao_local.xml).
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelecaoLocalBinding.inflate(inflater, container, false)
        return binding.root
    }

    // Chamado quando a View do fragmento foi criada.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Obtém a referência do fragmento do mapa definido no XML (SupportMapFragment).
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment

        // Registra o callback para ser notificado quando o mapa estiver pronto.
        mapFragment.getMapAsync(this)

        // Configura o botão "Selecionar" para exibir uma mensagem simples.
        binding.btnSelecionar.setOnClickListener {
            Toast.makeText(requireContext(), "Local selecionado!", Toast.LENGTH_SHORT).show()
        }
    }

    // Método chamado quando o mapa estiver pronto para uso.
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        try {
            // Define uma posição (latitude e longitude) — neste caso, Birmingham (EUA).
            val birmingham = LatLng(33.5186, -86.8104)

            // Adiciona um marcador no mapa na posição definida.
            map.addMarker(MarkerOptions().position(birmingham).title("Birmingham"))

            // Move a câmera do mapa para a posição do marcador com zoom 13.
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(birmingham, 13f))
        } catch (e: Exception) {
            // Exibe uma mensagem de erro caso algo dê errado ao carregar o mapa.
            Toast.makeText(requireContext(), "Erro ao carregar o mapa: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    // Libera o binding ao destruir a View, evitando vazamentos de memória.
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
