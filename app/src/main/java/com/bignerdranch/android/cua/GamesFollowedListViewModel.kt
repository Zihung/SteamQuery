package com.bignerdranch.android.cua

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.bignerdranch.android.cua.api.Game

class GamesFollowedListViewModel : ViewModel() {

    val games = mutableListOf<Game>()
    val gameItemLiveData: LiveData<List<Game>>

    private val steamQuery = SteamQuery()
    private val mutableSearchTerm = MutableLiveData<String>()



    init {
        mutableSearchTerm.value = "435150"
        gameItemLiveData = Transformations.switchMap(mutableSearchTerm) {
                searchTerm -> steamQuery.findGame(searchTerm)
        }
    }

    fun findGames(query: String = "") {
        mutableSearchTerm.value = query
    }

}