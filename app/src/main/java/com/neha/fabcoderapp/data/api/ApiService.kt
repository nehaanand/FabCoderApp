package com.neha.fabcoderapp.data.api

import com.neha.fabcoderapp.data.model.Drink
import com.neha.fabcoderapp.data.model.DrinksList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.*

interface ApiService {
    @GET("search.php?")
    fun getDrinksByFirstChar(@Query("f") firstchar: String  ): Call<DrinksList>

    @GET("search.php?")
    fun searchDrinkByName(@Query("s") drinkName: String):Call<DrinksList>

}