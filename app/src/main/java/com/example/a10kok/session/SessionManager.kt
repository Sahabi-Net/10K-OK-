package com.example.a10kok.session

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {

    private val pref: SharedPreferences =
        context.getSharedPreferences("LOGIN", Context.MODE_PRIVATE)

    fun saveLogin(
        id: String,
        nama: String,
        email: String,
        role: String
    ) {
        pref.edit().apply {
            putString("id_user", id)
            putString("nama", nama)
            putString("email", email)
            putString("role", role)
            apply()
        }
    }

    fun getIdUser(): String {
        return pref.getString("id_user", "") ?: ""
    }

    fun getNama(): String {
        return pref.getString("nama", "") ?: ""
    }

    fun getEmail(): String {
        return pref.getString("email", "") ?: ""
    }

    fun getRole(): String {
        return pref.getString("role", "") ?: ""
    }

    fun isLogin(): Boolean {
        return pref.getString("id_user", null) != null
    }

    fun logout() {
        pref.edit().clear().apply()
    }
    fun isCustomer(): Boolean {
        return getRole() == "customer"
    }
    fun getNamaUser(): String {
        return getNama().ifEmpty { "User" }
    }
}
