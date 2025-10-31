package com.example.routepiresfront

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class NotificacaoActivity : AppCompatActivity() {

    private lateinit var recyclerCorridas: RecyclerView
    private lateinit var adapter: NotificacaoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notificacao)

        recyclerCorridas = findViewById(R.id.recyclerCorridas)
        val btnVoltar = findViewById<ImageButton>(R.id.btnVoltar)

        btnVoltar.setOnClickListener { finish() }

        val listaCorridas = listOf(
            Notificacao("João", "20/03/2025 08:30"),
            Notificacao("Jose", "19/03/2025 08:20"),
            Notificacao("Rodrigo", "14/03/2025 10:30"),
            Notificacao("Otavio", "20/03/2025"),
            Notificacao("Luan", "20/03/2025")
        )

        adapter = NotificacaoAdapter(listaCorridas)
        recyclerCorridas.layoutManager = LinearLayoutManager(this)
        recyclerCorridas.adapter = adapter
    }
}