package com.bignerdranch.android.cua

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bignerdranch.android.cua.api.Game

class GamesListViewModel(private val app: Application) :
    AndroidViewModel(app) {
    val games = mutableListOf<Game>()

    val gameItemLiveData: MutableMap<String, *>?
    init {
        gameItemLiveData = GamesListPreferences.getStoredQuery(app)

        if (gameItemLiveData != null) {
            gameItemLiveData.forEach{
                if(it.value != ""){
                    games.add(Game(it.key, it.value as String))
                }

            }
        }

    }

    fun followGame(name: String= "", appid: String= ""){
        GamesListPreferences.setStoredQuery(app, name, appid)
    }

    fun removeGame(name: String=""){
        GamesListPreferences.removeStoredQuery(app, name)
    }
}