package com.example.routepiresfront.ui.comum

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.routepiresfront.data.model.CorridaDTOResponse
import com.example.routepiresfront.databinding.FragmentHistoricoCorridasBinding
import com.example.routepiresfront.ui.comum.adapter.CorridaAdapter
import com.example.routepiresfront.viewModel.MototaxistaPerfilViewModel
import com.example.routepiresfront.viewModel.PassageiroPerfilViewModel

class HistoricoCorridasFragment : Fragment() {

    private var _binding: FragmentHistoricoCorridasBinding? = null
    private val binding get() = _binding!!

    private val passViewModel: PassageiroPerfilViewModel by activityViewModels()
    private val motoViewModel: MototaxistaPerfilViewModel by activityViewModels()

    private var userId: String? = null
    private var userType: String? = null

    private lateinit var adapter: CorridaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            userId = it.getString("USER_ID")
            userType = it.getString("USER_TYPE")
            Log.d("HistoricoFragment", "onCreate: userId=$userId, userType=$userType")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHistoricoCorridasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializa o adapter com o userType correto
        adapter = CorridaAdapter(emptyList(), userType)
        binding.recyclerCorridas.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerCorridas.adapter = adapter

        binding.btnVoltar.setOnClickListener {
            findNavController().navigateUp()
        }

        fun handleReceivedList(corridas: List<CorridaDTOResponse>?) {
            val size = corridas?.size ?: 0
            Log.d("HistoricoFragment", "Corridas recebidas: $size. Atualizando adapter.")
            if (corridas.isNullOrEmpty()) {
                binding.tvEmptyHistorico.visibility = View.VISIBLE
                binding.recyclerCorridas.visibility = View.GONE
            } else {
                binding.tvEmptyHistorico.visibility = View.GONE
                binding.recyclerCorridas.visibility = View.VISIBLE
                adapter.updateCorridas(corridas)
            }
        }

        Log.d("HistoricoFragment", "ViewCreated: userType=$userType")

        if (userType == "mototaxista") {
            Log.d("HistoricoFragment", "Observando histórico do mototaxista...")
            motoViewModel.historico.observe(viewLifecycleOwner, ::handleReceivedList)
            motoViewModel.error.observe(viewLifecycleOwner) { error ->
                error?.let { Log.e("HistoricoFragment", "Erro mototaxista: $it") }
            }
        } else {
            Log.d("HistoricoFragment", "Observando histórico do passageiro...")
            passViewModel.historico.observe(viewLifecycleOwner, ::handleReceivedList)
            passViewModel.error.observe(viewLifecycleOwner) { error ->
                error?.let { Log.e("HistoricoFragment", "Erro passageiro: $it") }
            }
        }

        userId?.let {
            if (userType == "mototaxista") {
                Log.d("HistoricoFragment", "Carregando histórico para mototaxista (id=$it)")
                motoViewModel.carregarHistorico(it)
            } else {
                Log.d("HistoricoFragment", "Carregando histórico para passageiro (id=$it)")
                passViewModel.carregarHistorico(it)
            }
        } ?: Log.e("HistoricoFragment", "userId é nulo, não foi possível carregar o histórico.")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}