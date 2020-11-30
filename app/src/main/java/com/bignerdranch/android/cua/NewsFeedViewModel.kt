package com.bignerdranch.android.cua

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.bignerdranch.android.cua.api.NewsItem

class NewsFeedViewModel : ViewModel() {
    val galleryItemLiveData: LiveData<List<NewsItem>>
    private val steamQuery = SteamQuery()
    private val mutableSearchTerm = MutableLiveData<String>()

    init {
        mutableSearchTerm.value = "435150"
        galleryItemLiveData = Transformations.switchMap(mutableSearchTerm) {
                searchTerm -> steamQuery.searchGames(searchTerm)
        }
    }

    fun fetchGames(query: String = "") {
        mutableSearchTerm.value = query
    }
}