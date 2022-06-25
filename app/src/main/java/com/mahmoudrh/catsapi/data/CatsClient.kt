package com.mahmoudrh.catsapi.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CatsClient {
    private const val BASE_URL = "https://api.thecatapi.com/v1/images/"

    private fun retrofit():Retrofit{
        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(BASE_URL).build()
    }

    val catsService:CatsAPI by lazy {
        retrofit().create(CatsAPI::class.java)
    }
}