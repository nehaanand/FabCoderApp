package com.neha.fabcoderapp.views.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.neha.fabcoderapp.data.repository.MainRepository
import com.neha.fabcoderapp.data.api.ApiHelper
import com.neha.fabcoderapp.views.viewmodel.ViewModelCocktails

class ViewModelFactory constructor(private val repository: ApiHelper): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ViewModelCocktails::class.java)) {
            ViewModelCocktails(MainRepository(repository)) as T
        }  else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}