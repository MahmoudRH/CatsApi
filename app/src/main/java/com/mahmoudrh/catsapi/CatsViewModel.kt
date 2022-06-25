package com.mahmoudrh.catsapi

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmoudrh.catsapi.data.CatsClient
import com.mahmoudrh.catsapi.data.model.CatItem
import kotlinx.coroutines.launch

class CatsViewModel : ViewModel() {

    var listOfResults = mutableStateListOf(
        CatItem(url = "https://cdn2.thecatapi.com/images/ade.jpg"),
        CatItem(url = "https://cdn2.thecatapi.com/images/abc.jpg")
    )
    var isRefreshing = mutableStateOf(false)

    fun updateCats() = viewModelScope.launch {
        isRefreshing.value = true
        CatsClient.catsService.searchForACat().body()
            ?.let { listOfResults[0] = it[0] }

        CatsClient.catsService.searchForACat().body()
            ?.let { listOfResults[1] = it[0] }
        isRefreshing.value = false
    }

}