package com.example.android.myapplicationbook.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.myapplicationbook.Model.ResponseItems

class MainViewModel : ViewModel() {

    private val _currentBook: MutableLiveData<List<ResponseItems>> = MutableLiveData()
    val currentBook: LiveData<List<ResponseItems>> = _currentBook

    private val _favoriteItems: MutableLiveData<List<ResponseItems>> = MutableLiveData()
    val favoriteItems: LiveData<List<ResponseItems>> = _favoriteItems

    private val _allItems: MutableLiveData<List<ResponseItems>> = MutableLiveData()
    val allItems: LiveData<List<ResponseItems>> = _allItems

    //private var favoriteItems = mutableListOf<ResponseItems>()

    fun setResponseBook(responseItems: List<ResponseItems>){
        if(this._currentBook.value != null){
            if(this._currentBook.value!!.isEmpty()){
                this._currentBook.value = responseItems
            } else {
                this._currentBook.value = this._currentBook.value!!.plus(responseItems)
            }
        } else {
            this._currentBook.value = responseItems
        }


    }

    fun setFavoriteItems(favorites: List<ResponseItems>){
        this._favoriteItems.value = favorites
    }

    fun saveAllItems(){
        this._allItems.value = _currentBook.value
    }

}