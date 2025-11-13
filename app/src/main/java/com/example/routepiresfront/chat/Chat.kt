package com.example.routepiresfront.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.routepiresfront.R
import com.google.android.material.imageview.ShapeableImageView

class Chat : Fragment() {

    private lateinit var recyclerViewChat: RecyclerView
    private lateinit var chatAdapter: ChatAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        // Popula o cabeçalho com dados de exemplo
        val userImage: ShapeableImageView = view.findViewById(R.id.img_user)
        val userName: TextView = view.findViewById(R.id.text_view_name)
        val ratingBar: RatingBar = view.findViewById(R.id.rating_bar)
        val ratingValue: TextView = view.findViewById(R.id.text_view_rating_value)

        userName.text = "Lourielton João"
        ratingBar.rating = 4.5f
        ratingValue.text = "4.5"
        userImage.setImageResource(R.drawable.ic_google)

        recyclerViewChat = view.findViewById(R.id.recycler_view_chat)

        // Cria dados de exemplo para as mensagens
        val mensagens = listOf(
            Mensagem("Olá! Tudo bem?", "10:00", Mensagem.TIPO_RECEBIDA),
            Mensagem("Tudo sim, e com você?", "10:01", Mensagem.TIPO_ENVIADA),
            Mensagem("Estou bem também, obrigado!", "10:01", Mensagem.TIPO_RECEBIDA),
            Mensagem("Onde você está?", "10:02", Mensagem.TIPO_ENVIADA)
        )

        // Configura o adapter
        chatAdapter = ChatAdapter(mensagens)
        recyclerViewChat.adapter = chatAdapter
        recyclerViewChat.layoutManager = LinearLayoutManager(context)
    }
}