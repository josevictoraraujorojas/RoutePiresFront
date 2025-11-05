package com.example.routepiresfront

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.Color
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.routepiresfront.databinding.ActivityCriacaoCorridaBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import java.util.Locale

class CriacaoCorridaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCriacaoCorridaBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val LOCATION_PERMISSION_CODE = 1001

    private lateinit var chipGroupTipoCorrida: ChipGroup
    private lateinit var chipGroupPagamento: ChipGroup
    private lateinit var tvLimpar: TextView

    private val AUTOCOMPLETE_REQUEST_CODE_INICIO = 2001
    private val AUTOCOMPLETE_REQUEST_CODE_TERMINO = 2002

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCriacaoCorridaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        checkLocationPermission()

        chipGroupTipoCorrida = findViewById(R.id.chipGroupTipoCorrida)
        chipGroupPagamento = findViewById(R.id.chipGroupPagamento)
        tvLimpar = findViewById(R.id.tvLimpar)

        chipGroupTipoCorrida.isSingleSelection = true
        chipGroupPagamento.isSingleSelection = true
        setupChipSelection(chipGroupTipoCorrida)
        setupChipSelection(chipGroupPagamento)

        // Botão Buscar
        binding.btnBuscar.setOnClickListener {
            Toast.makeText(this, "Buscando corrida...", Toast.LENGTH_SHORT).show()
        }

        // Botão Limpar
        tvLimpar.setOnClickListener {
            chipGroupTipoCorrida.clearCheck()
            chipGroupPagamento.clearCheck()
            resetChipColors(chipGroupTipoCorrida)
            resetChipColors(chipGroupPagamento)
            binding.tvLocalInicio.text = "Obtendo localização..."
            binding.tvLocalTermino.text = "Escolha o destino"
        }

        // Clique em Local de Início e Término
        binding.tvLocalInicio.setOnClickListener { abrirSeletorDeLocal(true) }
        binding.tvLocalTermino.setOnClickListener { abrirSeletorDeLocal(false) }
    }

    private fun setupChipSelection(chipGroup: ChipGroup) {
        for (i in 0 until chipGroup.childCount) {
            val chip = chipGroup.getChildAt(i) as Chip
            chip.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    chip.chipBackgroundColor = ColorStateList.valueOf(Color.WHITE)
                    chip.setTextColor(Color.BLACK)
                } else {
                    when (chip.id) {
                        R.id.btnCorrida -> chip.chipBackgroundColor =
                            ColorStateList.valueOf(ContextCompat.getColor(this, R.color.yellow))
                        R.id.btnFreteSimples -> chip.chipBackgroundColor =
                            ColorStateList.valueOf(ContextCompat.getColor(this, R.color.red))
                        R.id.btnFrete -> chip.chipBackgroundColor =
                            ColorStateList.valueOf(ContextCompat.getColor(this, R.color.green))
                        R.id.btnPix -> chip.chipBackgroundColor =
                            ColorStateList.valueOf(ContextCompat.getColor(this, R.color.blue))
                        R.id.btnDebito -> chip.chipBackgroundColor =
                            ColorStateList.valueOf(ContextCompat.getColor(this, R.color.orange))
                        R.id.btnCredito -> chip.chipBackgroundColor =
                            ColorStateList.valueOf(ContextCompat.getColor(this, R.color.purple))
                        R.id.btnDinheiro -> chip.chipBackgroundColor =
                            ColorStateList.valueOf(ContextCompat.getColor(this, R.color.green))
                    }
                    chip.setTextColor(Color.WHITE)
                }
            }
        }
    }

    private fun resetChipColors(chipGroup: ChipGroup) {
        for (i in 0 until chipGroup.childCount) {
            val chip = chipGroup.getChildAt(i) as Chip
            chip.chipBackgroundColor = null
            chip.setTextColor(ContextCompat.getColor(this, android.R.color.white))
        }
    }

    private fun abrirSeletorDeLocal(isInicio: Boolean) {
        val fields = listOf(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG)
        val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
            .build(this)
        startActivityForResult(intent,
            if (isInicio) AUTOCOMPLETE_REQUEST_CODE_INICIO else AUTOCOMPLETE_REQUEST_CODE_TERMINO
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && data != null) {
            val place = Autocomplete.getPlaceFromIntent(data)
            when (requestCode) {
                AUTOCOMPLETE_REQUEST_CODE_INICIO -> binding.tvLocalInicio.text = place.address
                AUTOCOMPLETE_REQUEST_CODE_TERMINO -> binding.tvLocalTermino.text = place.address
            }
        }
    }

    private fun checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_CODE
            )
        } else {
            getCurrentLocation()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location != null) {
                val geocoder = Geocoder(this, Locale.getDefault())
                val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                val address = addresses?.get(0)?.getAddressLine(0)
                binding.tvLocalInicio.text = address ?: "Local não encontrado"
            } else {
                binding.tvLocalInicio.text = "Não foi possível obter a localização"
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_CODE &&
            grantResults.isNotEmpty() &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            getCurrentLocation()
        } else {
            Toast.makeText(this, "Permissão de localização negada", Toast.LENGTH_SHORT).show()
        }
    }
}
