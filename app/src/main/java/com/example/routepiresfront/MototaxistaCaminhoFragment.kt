package com.example.routepiresfront

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.routepiresfront.databinding.FragmentMototaxistaCaminhoBinding
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class MototaxistaCaminhoFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentMototaxistaCaminhoBinding? = null
    private val binding get() = _binding!!

    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var userMarker: Marker? = null
    private lateinit var locationCallback: LocationCallback
    private var currentPolyline: Polyline? = null

    // Pontos fixos
    private val pontoMoto = LatLng(-17.3060, -48.2850)
    private val pontoFinal = LatLng(-17.3020, -48.2790)

    // Permissão moderna
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) startLocationUpdates()
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMototaxistaCaminhoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled = true

        // Mototaxista (laranja)
        mMap.addMarker(
            MarkerOptions()
                .position(pontoMoto)
                .title("Mototaxista")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
        )

        // Ponto final (vermelho)
        mMap.addMarker(
            MarkerOptions()
                .position(pontoFinal)
                .title("Ponto Final")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
        )

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pontoMoto, 14f))
        checkLocationPermission()
    }

    private fun checkLocationPermission() {
        val context = requireContext()
        when {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> startLocationUpdates()

            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) ->
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)

            else -> requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun startLocationUpdates() {
        try {
            val locationRequest = LocationRequest.Builder(
                Priority.PRIORITY_HIGH_ACCURACY,
                4000L // Atualiza a cada 4 segundos
            )
                .setMinUpdateIntervalMillis(2000L)
                .build()

            locationCallback = object : LocationCallback() {
                override fun onLocationResult(result: LocationResult) {
                    val location = result.lastLocation ?: return
                    val currentLatLng = LatLng(location.latitude, location.longitude)

                    if (userMarker == null) {
                        userMarker = mMap.addMarker(
                            MarkerOptions()
                                .position(currentLatLng)
                                .title("Seu dispositivo")
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                        )
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f))
                    } else {
                        userMarker?.position = currentLatLng
                    }

                    // Atualiza a rota do mototaxista até o dispositivo
                    lifecycleScope.launch {
                        val rota = getRoutePolyline(pontoMoto, currentLatLng)
                        rota?.let {
                            currentPolyline?.remove()
                            currentPolyline = mMap.addPolyline(it)
                        }
                    }
                }
            }

            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                fusedLocationClient.requestLocationUpdates(
                    locationRequest,
                    locationCallback,
                    requireActivity().mainLooper
                )
            }
        } catch (e: SecurityException) {
            Log.e("LocationError", "Erro de permissão: ${e.message}")
        }
    }

    private suspend fun getRoutePolyline(origin: LatLng, dest: LatLng): PolylineOptions? {
        return withContext(Dispatchers.IO) {
            try {
                val apiKey = BuildConfig.MAPS_API_KEY
                val urlStr = "https://maps.googleapis.com/maps/api/directions/json?" +
                        "origin=${origin.latitude},${origin.longitude}" +
                        "&destination=${dest.latitude},${dest.longitude}" +
                        "&mode=driving&key=$apiKey"

                val conn = URL(urlStr).openConnection() as HttpsURLConnection
                conn.requestMethod = "GET"
                val data = conn.inputStream.bufferedReader().use { it.readText() }
                conn.disconnect()

                val json = JSONObject(data)
                val routes = json.getJSONArray("routes")
                if (routes.length() == 0) return@withContext null

                val encodedPoints = routes.getJSONObject(0)
                    .getJSONObject("overview_polyline")
                    .getString("points")

                if (encodedPoints.isNullOrEmpty()) return@withContext null

                val points = decodePolyline(encodedPoints)

                PolylineOptions()
                    .addAll(points)
                    .color(Color.BLUE)
                    .width(10f)
            } catch (e: Exception) {
                Log.e("RouteError", "Erro ao buscar rota: ${e.message}")
                null
            }
        }
    }

    private fun decodePolyline(encoded: String): List<LatLng> {
        if (encoded.isEmpty()) return emptyList()

        val poly = ArrayList<LatLng>()
        var index = 0
        var lat = 0
        var lng = 0

        while (index < encoded.length) {
            var b: Int
            var shift = 0
            var result = 0
            do {
                b = encoded[index++].code - 63
                result = result or ((b and 0x1f) shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlat = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lat += dlat

            shift = 0
            result = 0
            do {
                b = encoded[index++].code - 63
                result = result or ((b and 0x1f) shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlng = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lng += dlng

            poly.add(LatLng(lat / 1E5, lng / 1E5))
        }
        return poly
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (::fusedLocationClient.isInitialized && ::locationCallback.isInitialized) {
            fusedLocationClient.removeLocationUpdates(locationCallback)
        }
        _binding = null
    }
}
