package com.bignerdranch.android.cua.api

import android.net.Uri

data class NewsItem(
    var title: String = "",
    var appid: String = "",
    var url: String = "",
    val date: String = "",
    val contents: String= ""
) {
    val newsPageUri: Uri
    get() {
        return Uri.parse(url)
    }
}