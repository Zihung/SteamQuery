package com.bignerdranch.android.cua.api

import retrofit2.Call
import retrofit2.http.GET

interface SteamApi {
    @GET(
        "?appid=457140"
    + "&maxlength=300&format=json"
    )
    fun fetchNews(): Call<SteamResponse>
}