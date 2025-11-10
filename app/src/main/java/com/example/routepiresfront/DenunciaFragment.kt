package com.example.routepiresfront

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.routepiresfront.databinding.FragmentDenunciaBinding

class DenunciaFragment : Fragment() {

    private var _binding: FragmentDenunciaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDenunciaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnEnviarDenuncia.setOnClickListener {
            val motivo = binding.edtMotivoDenuncia.text.toString().trim()
            if (motivo.isEmpty()) {
                Toast.makeText(requireContext(), "Digite o motivo da denúncia!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Denúncia enviada com sucesso!", Toast.LENGTH_SHORT).show()
                parentFragmentManager.popBackStack()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}