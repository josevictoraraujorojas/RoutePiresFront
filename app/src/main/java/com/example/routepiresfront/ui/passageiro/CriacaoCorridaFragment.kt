package com.example.routepiresfront.ui.passageiro

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.Color
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.routepiresfront.R
import com.example.routepiresfront.databinding.FragmentCriacaoCorridaBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import java.util.Locale

class CriacaoCorridaFragment : Fragment() {

    private var _binding: FragmentCriacaoCorridaBinding? = null
    private val binding get() = _binding!!
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val LOCATION_PERMISSION_CODE = 1001
    private val AUTOCOMPLETE_REQUEST_CODE_INICIO = 2001
    private val AUTOCOMPLETE_REQUEST_CODE_TERMINO = 2002

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCriacaoCorridaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        checkLocationPermission()

        // Configuração dos chips
        binding.chipGroupTipoCorrida.isSingleSelection = true
        binding.chipGroupPagamento.isSingleSelection = true
        setupChipSelection(binding.chipGroupTipoCorrida)
        setupChipSelection(binding.chipGroupPagamento)

        // Botão "Buscar corrida"
        binding.btnBuscar.setOnClickListener {
//            val inicio = binding.tvLocalInicio.text.toString()
//            val destino = binding.tvLocalTermino.text.toString()
//
//            if (inicio.contains("Local") || destino.contains("Destino")) {
//                Toast.makeText(requireContext(), "Selecione os locais antes de buscar", Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(requireContext(), "Buscando corrida de:\n$inicio\naté\n$destino", Toast.LENGTH_LONG).show()
//            }
            findNavController().navigate(
                R.id.action_criacaoCorridaFragment_to_buscaMotoristaFragment
            )

        }

        // Botão "Cancelar"
        binding.tvCancelar.setOnClickListener {
            limparCampos()
            parentFragmentManager.popBackStack()
        }

        binding.tvLimpar.setOnClickListener {
            limparCampos()
        }

        // Campos de busca
        binding.tvLocalInicio.setOnClickListener { abrirSeletorDeLocal(true) }
        binding.tvLocalTermino.setOnClickListener { abrirSeletorDeLocal(false) }
    }

    private fun setupChipSelection(chipGroup: ChipGroup) {
        for (i in 0 until chipGroup.childCount) {
            val chip = chipGroup.getChildAt(i) as Chip
            chip.setOnCheckedChangeListener { _, isChecked ->
                val context = requireContext()
                if (isChecked) {
                    chip.chipBackgroundColor = ColorStateList.valueOf(Color.WHITE)
                    chip.setTextColor(Color.BLACK)
                } else {
                    val colorRes = when (chip.id) {
                        R.id.btnCorrida -> R.color.yellow
                        R.id.btnFreteSimples -> R.color.red
                        R.id.btnFrete -> R.color.green
                        R.id.btnPix -> R.color.blue
                        R.id.btnDebito -> R.color.orange
                        R.id.btnCredito -> R.color.purple
                        R.id.btnDinheiro -> R.color.green
                        else -> R.color.green
                    }
                    chip.chipBackgroundColor = ColorStateList.valueOf(ContextCompat.getColor(context, colorRes))
                    chip.setTextColor(Color.WHITE)
                }
            }
        }
    }

    private fun limparCampos() {
        binding.chipGroupTipoCorrida.clearCheck()
        binding.chipGroupPagamento.clearCheck()
        resetChipColors(binding.chipGroupTipoCorrida)
        resetChipColors(binding.chipGroupPagamento)

        // Mantém o texto fixo, sem piscar
        binding.tvLocalInicio.text = "Local de início"
        binding.tvLocalTermino.text = "Destino"

        // Opcional: atualizar localização após 500ms (evita “pular” layout)
        Handler(Looper.getMainLooper()).postDelayed({
            getCurrentLocation()
        }, 500)
    }

    private fun resetChipColors(chipGroup: ChipGroup) {
        val context = requireContext()
        for (i in 0 until chipGroup.childCount) {
            val chip = chipGroup.getChildAt(i) as Chip
            val colorRes = when (chip.id) {
                R.id.btnCorrida -> R.color.yellow
                R.id.btnFreteSimples -> R.color.red
                R.id.btnFrete -> R.color.green
                R.id.btnPix -> R.color.blue
                R.id.btnDebito -> R.color.orange
                R.id.btnCredito -> R.color.purple
                R.id.btnDinheiro -> R.color.green
                else -> R.color.green
            }
            chip.chipBackgroundColor = ColorStateList.valueOf(ContextCompat.getColor(context, colorRes))
            chip.setTextColor(Color.WHITE)
        }
    }

    private fun abrirSeletorDeLocal(isInicio: Boolean) {
        val fields = listOf(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG)
        val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
            .build(requireContext())
        startActivityForResult(
            intent,
            if (isInicio) AUTOCOMPLETE_REQUEST_CODE_INICIO else AUTOCOMPLETE_REQUEST_CODE_TERMINO
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == AppCompatActivity.RESULT_OK && data != null) {
            val place = Autocomplete.getPlaceFromIntent(data)
            when (requestCode) {
                AUTOCOMPLETE_REQUEST_CODE_INICIO -> binding.tvLocalInicio.text = place.address
                AUTOCOMPLETE_REQUEST_CODE_TERMINO -> binding.tvLocalTermino.text = place.address
            }
        }
    }

    private fun checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_CODE)
        } else getCurrentLocation()
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (!isAdded) return@addOnSuccessListener

            if (location != null) {
                val geocoder = Geocoder(requireContext(), Locale.getDefault())
                val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                binding.tvLocalInicio.text = addresses?.get(0)?.getAddressLine(0) ?: "Local não encontrado"
            } else {
                binding.tvLocalInicio.text = "Não foi possível obter a localização"
            }
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == LOCATION_PERMISSION_CODE && grantResults.isNotEmpty()
            && grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) getCurrentLocation()
        else Toast.makeText(requireContext(), "Permissão de localização negada", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}