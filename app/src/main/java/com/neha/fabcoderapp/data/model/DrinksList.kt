package com.neha.fabcoderapp.data.model


import com.google.gson.annotations.SerializedName

data class DrinksList(
    @SerializedName("drinks")
    val drinks: List<Drink>
)