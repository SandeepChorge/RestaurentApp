package com.madtitan94.codengineapp.utils

import android.content.Context
import android.content.SharedPreferences

object SharedPrefs {
    private val APP_SETTINGS = "APP_SETTINGS"


    // properties
    private val SOME_STRING_VALUE = "SOME_STRING_VALUE"
    private val IS_LOGGED_IN = "IS_LOGGED_IN"
    private val IS_ADMIN = "IS_ADMIN"
    private val USERNAME = "USERNAME"

    // other properties...


    // other properties...
    private fun SharedPreferencesManager() {}

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(APP_SETTINGS, Context.MODE_PRIVATE)
    }

    fun getUsreName(context: Context): String? {
        return getSharedPreferences(context).getString(USERNAME, "")
    }

    fun setUsername(context: Context, newValue: String?) {
        val editor = getSharedPreferences(context).edit()
        editor.putString(USERNAME, newValue)
        editor.commit()
    }

    public fun isLoggedIn(context: Context): Boolean {
        return getSharedPreferences(context).getBoolean(IS_LOGGED_IN, false)
    }

    public  fun setLoggedIn(context: Context, newValue: Boolean) {
        val editor = getSharedPreferences(context).edit()
        editor.putBoolean(IS_LOGGED_IN, newValue)
        editor.commit()
    }

    public fun isAdmin(context: Context): Boolean {
        return getSharedPreferences(context).getBoolean(IS_ADMIN, false)
    }

    public  fun setAdmin(context: Context, newValue: Boolean) {
        val editor = getSharedPreferences(context).edit()
        editor.putBoolean(IS_ADMIN, newValue)
        editor.commit()
    }

    public fun ClearPref(context: Context){
        val editor = getSharedPreferences(context).edit()
        editor.putBoolean(IS_ADMIN, false)
        editor.putBoolean(IS_LOGGED_IN, false)
        editor.putString(USERNAME,"")
        editor.commit()
    }


}