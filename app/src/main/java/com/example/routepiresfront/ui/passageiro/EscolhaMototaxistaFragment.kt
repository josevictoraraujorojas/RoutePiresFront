package com.example.routepiresfront.ui.passageiro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.routepiresfront.core.Resultado
import com.example.routepiresfront.data.model.passageiro.MototaxistaPerfil
import com.example.routepiresfront.databinding.FragmentEscolhaMototaxistaBinding
import com.example.routepiresfront.ui.passageiro.viewmodel.EscolhaMototaxistaViewModel
import com.google.android.material.snackbar.Snackbar

class EscolhaMototaxistaFragment : DialogFragment() {

    private var _binding: FragmentEscolhaMototaxistaBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EscolhaMototaxistaViewModel by viewModels()

    private var mototaxistaId: String? = null
    private var nome: String? = null
    private var avaliacao: Float = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Recupera os argumentos enviados pelo adapter
        arguments?.let {
            mototaxistaId = it.getString("id")
            nome = it.getString("nome")
            avaliacao = it.getFloat("avaliacao", 0f)
        }
        // Define o estilo do pop-up (sem bordas e com fundo transparente)
        setStyle(STYLE_NO_FRAME, android.R.style.Theme_Translucent_NoTitleBar)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEscolhaMototaxistaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.driverName.text = nome
        binding.ratingBar.rating = avaliacao
        binding.tvDisponibilidade.text = ""
        binding.tvVeiculo.text = ""
        binding.tvServicos.text = ""

        observarPerfil()
        mototaxistaId?.let { viewModel.carregarPerfil(it) }

        binding.naoButton.setOnClickListener { dismiss() }
        binding.simButton.setOnClickListener {
            retornarEscolha()
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
    }

    companion object {
        const val REQUEST_KEY = "escolha_mototaxista_request"
        const val RESULT_ID = "mototaxista_id"

        fun newInstance(id: String, nome: String?, avaliacao: Float): EscolhaMototaxistaFragment {
            val fragment = EscolhaMototaxistaFragment()
            val args = Bundle()
            args.putString("id", id)
            args.putString("nome", nome)
            args.putFloat("avaliacao", avaliacao)
            fragment.arguments = args
            return fragment
        }
    }

    private fun observarPerfil() {
        viewModel.perfilMototaxista.observe(viewLifecycleOwner) { resultado ->
            when (resultado) {
                Resultado.Carregando -> binding.pbPerfil.visibility = View.VISIBLE
                is Resultado.Sucesso -> {
                    binding.pbPerfil.visibility = View.GONE
                    preencherDados(resultado.dado)
                }

                is Resultado.Erro -> {
                    binding.pbPerfil.visibility = View.GONE
                    Snackbar.make(binding.root, resultado.mensagem, Snackbar.LENGTH_LONG).show()
                }

                else -> {}
            }
        }
    }

    private fun preencherDados(perfil: MototaxistaPerfil) {
        binding.driverName.text = perfil.nome ?: nome ?: "Mototaxista"
        binding.ratingBar.rating = perfil.avaliacaoMedia ?: avaliacao
        binding.tvDisponibilidade.text =
            if (perfil.disponivel == true) "Disponível" else "Indisponível"

        binding.tvVeiculo.text = perfil.veiculo?.let { veiculo ->
            listOfNotNull(veiculo.modelo, veiculo.placa, veiculo.ano?.toString())
                .joinToString(" • ")
        } ?: "Veículo não informado"

        binding.tvServicos.text = perfil.servicosOferecidos?.takeIf { it.isNotEmpty() }
            ?.joinToString(", ") { servico ->
                servico.name.lowercase().replace("_", " ")
                    .replaceFirstChar { it.uppercase() }
            } ?: "Serviços não informados"
    }

    private fun retornarEscolha() {
        val id = mototaxistaId ?: return
        parentFragmentManager.setFragmentResult(
            REQUEST_KEY,
            Bundle().apply { putString(RESULT_ID, id) }
        )
    }
}
