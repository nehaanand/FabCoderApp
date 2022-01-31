package com.neha.fabcoderapp.views.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.neha.fabcoderapp.data.repository.MainRepository
import com.neha.fabcoderapp.data.model.Drink
import com.neha.fabcoderapp.data.model.DrinksList
import com.neha.fabcoderapp.utilities.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewModelCocktails constructor(private val repository: MainRepository)  : ViewModel() {

    val drinksList = MutableLiveData<Resource<List<Drink>>>()
    val errorMessage = MutableLiveData<String>()

    fun getDrinksByFirstChar(firstChar:String) {

        val response = repository.getDrinksByFirstChar(firstChar)
        response.enqueue(object : Callback<DrinksList> {
            override fun onResponse(call: Call<DrinksList>, response: Response<DrinksList>) {
                drinksList.postValue(Resource.success(response.body()?.drinks))
            }
            override fun onFailure(call: Call<DrinksList>, t: Throwable) {
                drinksList.postValue(Resource.error(t.message.toString(),null))
            }
        })
    }

    fun searchDrinkByName(drinkName:String) {

        val response = repository.searchDrinkByName(drinkName)
        response.enqueue(object : Callback<DrinksList> {
            override fun onResponse(call: Call<DrinksList>, response: Response<DrinksList>) {
                drinksList.postValue(Resource.success(response.body()?.drinks))
            }
            override fun onFailure(call: Call<DrinksList>, t: Throwable) {
                drinksList.postValue(Resource.error(t.message.toString(),null))
            }
        })
    }
}