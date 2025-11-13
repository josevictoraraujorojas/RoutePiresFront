package com.example.routepiresfront.ui.mototaxista

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.routepiresfront.R
import com.example.routepiresfront.databinding.FragmentListaCorridasBinding

class ListaCorridasFragment : Fragment() {

    private var _binding: FragmentListaCorridasBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListaCorridasBinding.inflate(inflater, container, false)

        val passageiros = listOf(
            Passageiro("João Silva", 4.8f, R.drawable.ic_user_avatar),
            Passageiro("Maria Souza", 5.0f, R.drawable.ic_user_avatar),
            Passageiro("Carlos Lima", 4.5f, R.drawable.ic_user_avatar)
        )

        val adapter = PassageiroAdapter(passageiros) {
            // ação ao clicar no passageiro
        }

        binding.recyclerPassageiros.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerPassageiros.adapter = adapter

        // Botão de expandir/recolher o BottomSheet
        binding.expandIcon.setOnClickListener {
            val parent = parentFragment
            if (parent is CorridaMototaxistaFragment) {
                parent.toggleBottomSheet()
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
