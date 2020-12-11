package com.bignerdranch.android.cua

import android.content.Context
import android.preference.PreferenceManager


object GamesListPreferences {
    fun getStoredQuery(context: Context): MutableMap<String, *>? {
        val prefs =
            PreferenceManager.getDefaultSharedPreferences(context)
        return prefs.all
//        return prefs.getString(PREF_SEARCH_QUERY, "")!!
    }


    fun setStoredQuery(context: Context, name: String, appid: String) {
        PreferenceManager.getDefaultSharedPreferences(context)
            .edit()
            .putString(name, appid)
            .apply()
    }

    fun removeStoredQuery(context: Context, name: String) {
        PreferenceManager.getDefaultSharedPreferences(context)
            .edit()
            .remove(name)
            .apply()

    }


}