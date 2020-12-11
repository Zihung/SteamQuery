package com.bignerdranch.android.cua.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SteamApi {
    @GET(
        "?appid=457140"
    + "&maxlength=300&format=json"
    )
    fun fetchNews(): Call<SteamResponse>

    @GET("ISteamNews/GetNewsForApp/v0002")
    fun searchGames(@Query("appid") query: String): Call<SteamResponse>

    @GET("ISteamApps/GetAppList/v0002")
    fun findGame(@Query("appid") query: String): Call<SteamResponse>
}