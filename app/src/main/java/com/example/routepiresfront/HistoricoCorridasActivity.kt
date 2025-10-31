package com.example.routepiresfront

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HistoricoCorridasActivity : AppCompatActivity() {

    private lateinit var recyclerCorridas: RecyclerView
    private lateinit var adapter: CorridaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historico_corridas)

        recyclerCorridas = findViewById(R.id.recyclerCorridas)
        val btnVoltar = findViewById<ImageButton>(R.id.btnVoltar)

        btnVoltar.setOnClickListener { finish() }

        val listaCorridas = listOf(
            Corrida("João", "20/03/2025 08:30", "Cancelado"),
            Corrida("Jose", "19/03/2025 08:20", "Finalizado"),
            Corrida("Rodrigo", "14/03/2025 10:30", "Finalizado"),
            Corrida("Otavio", "20/03/2025", "Cancelado"),
            Corrida("Luan", "20/03/2025", "Cancelado")
        )

        adapter = CorridaAdapter(listaCorridas)
        recyclerCorridas.layoutManager = LinearLayoutManager(this)
        recyclerCorridas.adapter = adapter
    }
}