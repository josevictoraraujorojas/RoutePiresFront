package com.example.routepiresfront

import android.app.Application
import com.google.android.libraries.places.api.Places

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()

        // Inicializa o Google Places uma única vez
        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, BuildConfig.MAPS_API_KEY)
        }
    }
}
