package com.example.routepiresfront.ui.comum

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.routepiresfront.ui.comum.adapter.CorridaAdapter
import com.example.routepiresfront.databinding.FragmentHistoricoCorridasBinding
import com.example.routepiresfront.data.model.Corrida
import com.example.routepiresfront.data.model.Passageiro
import com.example.routepiresfront.data.model.Localizacao
import com.example.routepiresfront.data.model.StatusCorrida

class HistoricoCorridasFragment : Fragment() {

    private lateinit var recyclerCorridas: RecyclerView
    private lateinit var adapter: CorridaAdapter
    private lateinit var binding: FragmentHistoricoCorridasBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoricoCorridasBinding.inflate(inflater, container, false)

        recyclerCorridas = binding.recyclerCorridas

        // ✅ Botão voltar com Navigation Component
        binding.btnVoltar.setOnClickListener {
            findNavController().navigateUp()
        }

        // Mock data usando novo modelo (IDs como String para compatibilidade com backend)
        val listaCorridas = listOf(
            Corrida(
                id = "1",
                passageiro = Passageiro(id = "1", nome = "João Silva", avaliacao = 4.5f),
                status = StatusCorrida.CANCELADA,
                valorEstimado = 25.50,
                distanciaKm = 5.2,
                dataHoraCriacao = "2025-03-20T08:30:00",
                pontoPartida = Localizacao(latitude = 0.0, longitude = 0.0, endereco = "Rua A, 123"),
                pontoDestino = Localizacao(latitude = 0.0, longitude = 0.0, endereco = "Av. B, 456")
            ),
            Corrida(
                id = "2",
                passageiro = Passageiro(id = "2", nome = "Jose Santos", avaliacao = 5.0f),
                status = StatusCorrida.FINALIZADA,
                valorEstimado = 18.00,
                distanciaKm = 3.5,
                dataHoraCriacao = "2025-03-19T08:20:00",
                pontoPartida = Localizacao(latitude = 0.0, longitude = 0.0, endereco = "Centro"),
                pontoDestino = Localizacao(latitude = 0.0, longitude = 0.0, endereco = "Bairro Sul")
            ),
            Corrida(
                id = "3",
                passageiro = Passageiro(id = "3", nome = "Rodrigo Lima", avaliacao = 4.8f),
                status = StatusCorrida.FINALIZADA,
                valorEstimado = 32.00,
                distanciaKm = 8.1,
                dataHoraCriacao = "2025-03-14T10:30:00",
                pontoPartida = Localizacao(latitude = 0.0, longitude = 0.0, endereco = "Zona Norte"),
                pontoDestino = Localizacao(latitude = 0.0, longitude = 0.0, endereco = "Aeroporto")
            ),
            Corrida(
                id = "4",
                passageiro = Passageiro(id = "4", nome = "Otavio Costa", avaliacao = 4.2f),
                status = StatusCorrida.CANCELADA,
                valorEstimado = 15.00,
                distanciaKm = 2.8,
                dataHoraCriacao = "2025-03-20T14:00:00",
                pontoPartida = Localizacao(latitude = 0.0, longitude = 0.0, endereco = "Shopping"),
                pontoDestino = Localizacao(latitude = 0.0, longitude = 0.0, endereco = "Residencial")
            ),
            Corrida(
                id = "5",
                passageiro = Passageiro(id = "5", nome = "Luan Campos", avaliacao = 4.9f),
                status = StatusCorrida.CANCELADA,
                valorEstimado = 22.50,
                distanciaKm = 4.5,
                dataHoraCriacao = "2025-03-20T16:30:00",
                pontoPartida = Localizacao(latitude = 0.0, longitude = 0.0, endereco = "Universidade"),
                pontoDestino = Localizacao(latitude = 0.0, longitude = 0.0, endereco = "Casa")
            )
        )

        adapter = CorridaAdapter(listaCorridas)
        recyclerCorridas.layoutManager = LinearLayoutManager(requireContext())
        recyclerCorridas.adapter = adapter

        return binding.root
    }
}

