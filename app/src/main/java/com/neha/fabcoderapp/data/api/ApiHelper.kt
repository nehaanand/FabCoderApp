package com.neha.fabcoderapp.data.api

class ApiHelper(private val apiService: ApiService) {

    fun getDrinksByFirstChar(firstChar:String) = apiService.getDrinksByFirstChar(firstChar);
    fun searchDrinkByName(drinkName:String) = apiService.searchDrinkByName(drinkName);
}