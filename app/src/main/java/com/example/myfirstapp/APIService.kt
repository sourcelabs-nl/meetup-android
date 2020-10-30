package com.example.myfirstapp

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface APIService {

    @GET("/api/character/{Id}")
    suspend fun getCharacter(@Path("Id") characterId: String): Response<Character>
}

data class Character (val image: String)