package com.example.routepiresfront

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.routepiresfront.databinding.FragmentEscolhaMototaxistaBinding

class EscolhaMototaxistaFragment : DialogFragment() {

    private var _binding: FragmentEscolhaMototaxistaBinding? = null
    private val binding get() = _binding!!

    private var nome: String? = null
    private var avaliacao: Float = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Recupera os argumentos enviados pelo adapter
        arguments?.let {
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

        binding.driverName.text = nome
        binding.ratingBar.rating = avaliacao

        binding.naoButton.setOnClickListener {
            dismiss() // Fecha o pop-up
        }

        binding.simButton.setOnClickListener {
            // Aqui você pode colocar a lógica de "Negociar"
            dismiss()
        }

        return binding.root
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
        fun newInstance(nome: String, avaliacao: Float): EscolhaMototaxistaFragment {
            val fragment = EscolhaMototaxistaFragment()
            val args = Bundle()
            args.putString("nome", nome)
            args.putFloat("avaliacao", avaliacao)
            fragment.arguments = args
            return fragment
        }
    }
}
