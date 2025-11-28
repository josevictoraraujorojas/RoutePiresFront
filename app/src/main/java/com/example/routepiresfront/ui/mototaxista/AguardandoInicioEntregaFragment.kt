package com.example.routepiresfront.ui.mototaxista

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.routepiresfront.R
import com.example.routepiresfront.databinding.FragmentAguardandoInicioEntregaBinding
import com.example.routepiresfront.ui.mototaxista.viewmodel.CorridaMototaxistaViewModel
import com.google.android.gms.maps.model.LatLng
import java.text.SimpleDateFormat
import java.util.*

class AguardandoInicioEntregaFragment : Fragment() {

    private var _binding: FragmentAguardandoInicioEntregaBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CorridaMototaxistaViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAguardandoInicioEntregaBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
        setupListeners()
    }

    private fun setupObservers() {
        viewModel.corridaAtual.observe(viewLifecycleOwner) { corrida ->
            corrida?.let {
                binding.corrida = it
                
                // Atualiza UI específica de entrega
                it.descricaoEntrega?.let { desc ->
                    binding.tvDescricaoEntrega.text = desc
                }
                it.pesoKg?.let { peso ->
                    binding.tvPeso.text = "PESO: ${peso}KG"
                }
                it.passageiro?.let { passageiro ->
                    binding.tvNomeSolicitante.text = passageiro.nome
                    binding.tvAvaliacaoSolicitante.text = String.format("%.1f", passageiro.avaliacao)
                }
            }
        }

        viewModel.navegarParaAndamento.observe(viewLifecycleOwner) {
            findNavController().navigate(
                R.id.action_aguardandoInicioEntregaFragment_to_corridaAndamentoFragment
            )
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
                viewModel.limparErro()
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.btnIniciarEntrega.isEnabled = !isLoading
            binding.btnCancelarEntrega.isEnabled = !isLoading
        }

        viewModel.fecharFluxoCorrida.observe(viewLifecycleOwner) {
            parentFragmentManager.popBackStack()
        }
    }

    private fun setupListeners() {
        binding.btnIniciarEntrega.setOnClickListener {
            viewModel.iniciarCorrida()
        }

        binding.btnCancelarEntrega.setOnClickListener {
            viewModel.cancelarCorrida("Cancelado pelo mototaxista antes de iniciar a entrega")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

