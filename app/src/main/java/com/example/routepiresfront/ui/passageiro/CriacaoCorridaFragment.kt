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
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.routepiresfront.R
import com.example.routepiresfront.data.model.CorridaPassageiroRequest
import com.example.routepiresfront.data.model.GeoPoint
import com.example.routepiresfront.data.model.LocalizacaoRequest
import com.example.routepiresfront.data.repository.CorridaPassageiroRepository
import com.example.routepiresfront.databinding.FragmentCriacaoCorridaBinding
import com.example.routepiresfront.viewmodel.CorridaPassageiroViewModelFactory
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import java.util.Locale

class CriacaoCorridaFragment : Fragment() {

    private var _binding: FragmentCriacaoCorridaBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: CorridaPassageiroViewModel
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val LOCATION_PERMISSION_CODE = 1001

    // Variáveis para armazenar coordenadas
    private var latLngInicio: LatLng? = null
    private var latLngDestino: LatLng? = null

    // Controle para saber qual campo está sendo editado (Origem ou Destino)
    private var isEditandoOrigem = true

    // --- CORREÇÃO: Novo jeito de pegar o resultado do Google Places ---
    private val autocompleteLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == AppCompatActivity.RESULT_OK && result.data != null) {
            val place = Autocomplete.getPlaceFromIntent(result.data!!)
            val endereco = place.address ?: "Endereço desconhecido"
            val latLng = place.latLng

            if (isEditandoOrigem) {
                binding.tvLocalInicio.text = endereco
                latLngInicio = latLng
            } else {
                binding.tvLocalTermino.text = endereco
                latLngDestino = latLng
            }
        } else if (result.resultCode == AppCompatActivity.RESULT_CANCELED) {
            // O usuário cancelou, não faz nada
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCriacaoCorridaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializa ViewModel
        val repository = CorridaPassageiroRepository()
        val factory = CorridaPassageiroViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(CorridaPassageiroViewModel::class.java)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        checkLocationPermission()

        setupUI()
        setupObservers()
    }

    private fun setupUI() {
        binding.chipGroupTipoCorrida.isSingleSelection = true
        binding.chipGroupPagamento.isSingleSelection = true
        setupChipSelection(binding.chipGroupTipoCorrida)
        setupChipSelection(binding.chipGroupPagamento)

        // Botão Buscar
        binding.btnBuscar.setOnClickListener {
            enviarSolicitacaoCorrida()
        }

        binding.tvCancelar.setOnClickListener {
            limparCampos()
            parentFragmentManager.popBackStack()
        }
        binding.tvLimpar.setOnClickListener { limparCampos() }

        // Cliques nos campos de endereço
        binding.tvLocalInicio.setOnClickListener {
            isEditandoOrigem = true
            abrirAutocomplete()
        }

        binding.tvLocalTermino.setOnClickListener {
            isEditandoOrigem = false
            abrirAutocomplete()
        }
    }

    private fun abrirAutocomplete() {
        // Define quais campos queremos que o Google retorne
        val fields = listOf(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG)

        // Cria a Intent do Google Places
        val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
            .build(requireContext())

        // Lança usando o launcher moderno
        autocompleteLauncher.launch(intent)
    }

    private fun setupObservers() {
        viewModel.corridaState.observe(viewLifecycleOwner) { result ->
            result.onSuccess { response ->
                Toast.makeText(requireContext(), "Corrida solicitada!", Toast.LENGTH_SHORT).show()
                val bundle = Bundle().apply {
                    putString("corrida_id", response.id)
                }
                findNavController().navigate(
                    R.id.action_criacaoCorridaFragment_to_buscaMotoristaFragment,
                    bundle
                )
            }.onFailure { exception ->
                binding.btnBuscar.isEnabled = true
                binding.btnBuscar.text = "Buscar Corrida"

                // Isso vai imprimir o erro exato no Logcat e na tela
                exception.printStackTrace()
                Toast.makeText(requireContext(), "Falha: ${exception.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun enviarSolicitacaoCorrida() {
        // 1. Verificar se as coordenadas foram capturadas
        if (latLngInicio == null) {
            Toast.makeText(requireContext(), "Erro: Local de Início vazio", Toast.LENGTH_SHORT).show()
            return
        }
        if (latLngDestino == null) {
            Toast.makeText(requireContext(), "Erro: Destino vazio", Toast.LENGTH_SHORT).show()
            return
        }

        // 2. Preparar os dados
        val origemRequest = LocalizacaoRequest(
            localizacao = GeoPoint(latLngInicio!!.latitude, latLngInicio!!.longitude),
            timestamp = System.currentTimeMillis()
        )

        val destinoRequest = LocalizacaoRequest(
            localizacao = GeoPoint(latLngDestino!!.latitude, latLngDestino!!.longitude),
            timestamp = System.currentTimeMillis()
        )

        // --- ATENÇÃO AQUI ---
        // Se o backend tiver validação de chave estrangeira, "passageiro_123" vai falhar.
        // Tente usar um ID que você sabe que existe no banco, ou crie um usuário antes via Postman/Swagger.
        val passageiroIdParaTeste = "1" // Tente usar 1 ou um UUID real se seu banco usar UUID

        val request = CorridaPassageiroRequest(
            passageiroId = passageiroIdParaTeste,
            origem = origemRequest,
            destino = destinoRequest,
            dataHoraSolicitacao = System.currentTimeMillis(),
            status = "PENDENTE"
        )

        // 3. Feedback visual
        binding.btnBuscar.text = "Enviando para API..."
        binding.btnBuscar.isEnabled = false

        // 4. Chamar ViewModel
        viewModel.criarCorrida(request)
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
                    resetChipColors(chipGroup) // Garante reset visual
                }
            }
        }
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

    private fun limparCampos() {
        latLngInicio = null
        latLngDestino = null
        binding.chipGroupTipoCorrida.clearCheck()
        binding.chipGroupPagamento.clearCheck()
        resetChipColors(binding.chipGroupTipoCorrida)
        resetChipColors(binding.chipGroupPagamento)
        binding.tvLocalInicio.text = "Local de início"
        binding.tvLocalTermino.text = "Destino"

        Handler(Looper.getMainLooper()).postDelayed({
            getCurrentLocation()
        }, 500)
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
                latLngInicio = LatLng(location.latitude, location.longitude) // Salva coord atual
                val geocoder = Geocoder(requireContext(), Locale.getDefault())
                val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                binding.tvLocalInicio.text = addresses?.get(0)?.getAddressLine(0) ?: "Local atual"
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == LOCATION_PERMISSION_CODE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}