package com.bignerdranch.android.cua

import androidx.lifecycle.ViewModel
import com.bignerdranch.android.cua.api.Game

class GamesListViewModel : ViewModel() {
    val games = mutableListOf<Game>()
    init {
        for (i in 0 until 100) {
            val game = Game()
            game.name = "Crime #$i"
            game.appid = (i % 2 == 0).toString()
            games += game
        }
    }
}