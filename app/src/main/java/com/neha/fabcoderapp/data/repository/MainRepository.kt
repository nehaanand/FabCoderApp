package com.neha.fabcoderapp.data.repository

import com.neha.fabcoderapp.data.api.ApiHelper


class MainRepository(private val apiHelper: ApiHelper) {

    fun getDrinksByFirstChar(firstChar:String) = apiHelper.getDrinksByFirstChar(firstChar)
    fun searchDrinkByName(drinkName:String) = apiHelper.searchDrinkByName(drinkName)
}