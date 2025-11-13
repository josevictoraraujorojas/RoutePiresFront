package com.example.routepiresfront.ui.mototaxista

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.routepiresfront.R
import com.example.routepiresfront.databinding.FragmentAguardandoInicioCorridaBinding
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import java.text.SimpleDateFormat
import java.util.*

class AguardandoInicioCorridaFragment : Fragment() {

    private var _binding: FragmentAguardandoInicioCorridaBinding? = null
    private val binding get() = _binding!!

    private var nomePassageiro: String = "Karen Roe"
    private var avaliacaoPassageiro: Float = 4.8f
    private var pontosParada: Int = 2
    private lateinit var pontoPartida: LatLng
    private lateinit var pontoDestino: LatLng
    private val pontosIntermediarios = mutableListOf<LatLng>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Aqui você pode receber os dados via arguments
        // arguments?.let {
        //     nomePassageiro = it.getString("NOME_PASSAGEIRO") ?: "Karen Roe"
        //     avaliacaoPassageiro = it.getFloat("AVALIACAO_PASSAGEIRO", 4.8f)
        //     pontosParada = it.getInt("PONTOS_PARADA", 2)
        // }

        pontoPartida = LatLng(-17.304889, -48.279548)
        pontoDestino = LatLng(-17.305980, -48.283300)
        pontosIntermediarios.add(LatLng(-17.305200, -48.281000))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAguardandoInicioCorridaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment

        setupUI()

        setupListeners()
    }

    private fun setupUI() {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
//        binding.tvDataHora.text = dateFormat.format(Date())
//
//        binding.tvPontosParada.text = "$pontosParada Ponto${if (pontosParada > 1) "s" else ""} de parada"

        binding.tvNomePassageiro.text = nomePassageiro
        binding.tvAvaliacaoPassageiro.text = String.format("%.1f", avaliacaoPassageiro)
    }

    private fun setupListeners() {

        binding.btnIniciarCorrida.setOnClickListener {
            Toast.makeText(requireContext(), "Iniciando corrida...", Toast.LENGTH_SHORT).show()
            // Aqui você navegaria para o fragmento de corrida em andamento
//            findNavController().navigate(
////                R.id.action_aguardandoInicioCorridaFragment_to_corridaAndamentoFragment
//            )
            // findNavController().navigate(R.id.action_aguardandoInicioCorrida_to_corridaEmAndamento)
        }

        binding.btnCancelarCorrida.setOnClickListener {
            Toast.makeText(requireContext(), "Cancelando corrida...", Toast.LENGTH_SHORT).show()
            parentFragmentManager.popBackStack()
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

