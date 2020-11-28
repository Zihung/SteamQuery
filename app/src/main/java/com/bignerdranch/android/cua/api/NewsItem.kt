package com.bignerdranch.android.cua.api

data class NewsItem(
    var title: String = "",
    var appid: String = "",
    var url: String = "",
    val date: String = "",
    val contents: String= ""

)