package com.mahmoudrh.catsapi.data

import com.mahmoudrh.catsapi.data.model.CatItem
import retrofit2.Response
import retrofit2.http.GET

interface CatsAPI {

//    @GET("search?limit=2&page=1") This can be used to get 2 cats just by one call, but i use the second one twice to get 2 cats.

    @GET("search")
    suspend fun searchForACat():Response<List<CatItem>>
}