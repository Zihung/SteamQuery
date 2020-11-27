package com.bignerdranch.android.cua.api

import retrofit2.Call
import retrofit2.http.GET

interface SteamApi {
    @GET("/")
    fun fetchContents(): Call<String>
}