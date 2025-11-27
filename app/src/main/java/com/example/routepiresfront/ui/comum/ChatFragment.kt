package com.example.routepiresfront.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.navigation.fragment.findNavController
import com.example.routepiresfront.R
import com.example.routepiresfront.databinding.FragmentChatBinding
import com.example.routepiresfront.data.model.Mensagem
import com.example.routepiresfront.ui.comum.adapter.MensagensAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView

class ChatFragment : Fragment() {

    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!

    private lateinit var chatAdapter: MensagensAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Cabeçalho do chat
        binding.chatToolbar.textViewName.text = "Lourielton João"
        binding.chatToolbar.ratingBar.rating = 4.5f
        binding.chatToolbar.textViewRatingValue.text = "4.5"
        binding.chatToolbar.imgUser.setImageResource(com.example.routepiresfront.R.drawable.ic_google)

        // Botão de voltar no cabeçalho
        binding.chatToolbar.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.chatToolbar.buttonReport.setOnClickListener {
            mostrarPopupDenuncia()
        }

        // Lista de mensagens de exemplo
        val mensagens = listOf(
            Mensagem("Olá! Tudo bem?", "10:00", Mensagem.TIPO_RECEBIDA),
            Mensagem("Tudo sim, e com você?", "10:01", Mensagem.TIPO_ENVIADA),
            Mensagem("Estou bem também, obrigado!", "10:01", Mensagem.TIPO_RECEBIDA),
            Mensagem("Onde você está?", "10:02", Mensagem.TIPO_ENVIADA)
        )

        // Configura RecyclerView
        chatAdapter = MensagensAdapter(mensagens)
        binding.recyclerViewChat.apply {
            adapter = chatAdapter
            layoutManager = LinearLayoutManager(context).apply {
                reverseLayout = false
            }
        }

        // Botão recusar corrida
        binding.buttonRecusarCorrida.setOnClickListener {
            findNavController().popBackStack()
        }

        // Botão aceitar corrida (aqui você pode adicionar navegação se necessário)
//        binding.buttonAceitarCorrida.setOnClickListener {

//            // 1️⃣ Trocar para a aba "Corrida"
//            val bottom = requireActivity().findViewById<BottomNavigationView>(R.id.menuInferior)
//            bottom.selectedItemId = R.id.bottom_corrida
//
//            // 2️⃣ Obter NavController da aba Corrida
//            val corridaNav = requireActivity()
//                .supportFragmentManager
//                .findFragmentById(R.id.nav_host_corrida)
//                ?.findNavController()
//
//            // 3️⃣ Navegar dentro do fluxo da aba Corrida
//            corridaNav?.navigate(R.id.action_global_mototaxistaCaminhoFragment)

//
//        }

        binding.buttonAceitarCorrida.setOnClickListener {
            // 1️⃣ Seleciona a aba Corrida
            val bottom = requireActivity().findViewById<BottomNavigationView>(R.id.menuInferior)
            bottom.selectedItemId = R.id.bottom_home

            // 2️⃣ Envia sinal para CorridaMototaxistaFragment abrir o agurandoiniciocorrida
            val corridaNav = requireActivity()
                .supportFragmentManager
                .findFragmentById(R.id.nav_host_home_moto)
                ?.findNavController()

            corridaNav?.navigate(R.id.action_global_aguardandoInicioCorridaFragment2)
        }

    }

        private fun mostrarPopupDenuncia() {
            val dialogView = layoutInflater.inflate(R.layout.dialog_denuncia, null)

            val dialog = androidx.appcompat.app.AlertDialog.Builder(requireContext())
                .setView(dialogView)
                .create()

            dialogView.findViewById<Button>(R.id.btnCancelar).setOnClickListener {
                dialog.dismiss()
            }

            dialogView.findViewById<Button>(R.id.btnConfirmar).setOnClickListener {
                findNavController().navigate(R.id.action_chat_para_denuncia)
                dialog.dismiss()

            }

            dialog.show()
        }

        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }
    }

