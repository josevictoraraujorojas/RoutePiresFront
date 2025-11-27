package com.example.routepiresfront.core

import android.content.Context
import android.content.SharedPreferences

/**
 * Armazena dados mínimos do usuário logado.
 */
object SessionManager {
    private const val PREFS_NAME = "route_pires_session"
    private const val KEY_USER_ID = "user_id"
    private const val KEY_USER_TYPE = "user_type"

    private fun prefs(context: Context): SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun salvarUsuario(context: Context, id: String?, tipo: String?) {
        prefs(context).edit()
            .putString(KEY_USER_ID, id)
            .putString(KEY_USER_TYPE, tipo)
            .apply()
    }

    fun obterUsuarioId(context: Context): String? =
        prefs(context).getString(KEY_USER_ID, null)

    fun obterTipo(context: Context): String? =
        prefs(context).getString(KEY_USER_TYPE, null)

    fun limpar(context: Context) {
        prefs(context).edit().clear().apply()
    }
}
