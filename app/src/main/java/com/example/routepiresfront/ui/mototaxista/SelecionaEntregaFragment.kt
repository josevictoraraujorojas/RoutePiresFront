package com.example.routepiresfront.ui.mototaxista

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.routepiresfront.R
import com.example.routepiresfront.databinding.FragmentSelecionaEntregaBinding

class SelecionaEntregaFragment : Fragment() {
    private var _binding: FragmentSelecionaEntregaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelecionaEntregaBinding.inflate(inflater, container, false)

        binding.btnCancelarNegociacao.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.btnFragil.setOnClickListener {
            selecionarTipoEntrega(true)
        }

        binding.btnComum.setOnClickListener {
            selecionarTipoEntrega(false)
        }

        return binding.root
    }

    private fun selecionarTipoEntrega(fragil: Boolean) {
        if (fragil) {
            binding.iconFragil.setBackgroundResource(R.drawable.circle_background)
            binding.iconComum.setBackgroundResource(R.drawable.circle_gray)
            binding.tvFragil.setTextColor(Color.BLACK)
            binding.tvComum.setTextColor(Color.parseColor("#9E9E9E"))
        } else {
            binding.iconFragil.setBackgroundResource(R.drawable.circle_gray)
            binding.iconComum.setBackgroundResource(R.drawable.circle_background)
            binding.tvFragil.setTextColor(Color.parseColor("#9E9E9E"))
            binding.tvComum.setTextColor(Color.BLACK)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
