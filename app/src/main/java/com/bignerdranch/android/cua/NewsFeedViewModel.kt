package com.bignerdranch.android.cua

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bignerdranch.android.cua.api.NewsItem

class NewsFeedViewModel : ViewModel() {
    val galleryItemLiveData: LiveData<List<NewsItem>>
    init {
        galleryItemLiveData = SteamQuery().fetchContents()
    }
}