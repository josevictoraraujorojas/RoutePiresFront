package com.example.routepiresfront.chat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.routepiresfront.R

class Chat : Fragment() {
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var recyclerViewChat: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // --- DADOS DE EXEMPLO ---
        // Simula o ID do usuário logado
        val idUsuarioAtual = "meuId"
        // Cria uma lista de mensagens para teste (use timestamps fixos para consistência)
        val listaDeMensagens = mutableListOf(
            Mensagem("Olá, tudo bem?", "outroId", "1672531200000L"), // Ex: 01/01/2023 00:00:00
            Mensagem("Tudo ótimo, e com você?", "meuId", "1672531260000L"), // Ex: 01/01/2023 00:01:00
            Mensagem("Estou bem também! Onde você está?", "outroId", "1672531320000L"), // Ex: 01/01/2023 00:02:00
            Mensagem("Estou a caminho!", "meuId", "1672531380000L") // Ex: 01/01/2023 00:03:00
        )

        // 1. Encontra o RecyclerView
        recyclerViewChat = view.findViewById(R.id.recycler_view_chat)

        // 2. Cria e configura o Adapter
        chatAdapter = ChatAdapter(listaDeMensagens, idUsuarioAtual)
        recyclerViewChat.adapter = chatAdapter

        // 3. Define o LayoutManager
        recyclerViewChat.layoutManager = LinearLayoutManager(requireContext())

        // 4. Aplica o espaçamento que você já tinha
        val espacamentoEmPixels = 12
        recyclerViewChat.addItemDecoration(SpacingItemDecoration(espacamentoEmPixels))
    }
}