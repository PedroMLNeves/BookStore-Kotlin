package com.example.android.myapplicationbook.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.myapplicationbook.ApiInterface
import com.example.android.myapplicationbook.Model.ResponseItems
import com.example.android.myapplicationbook.RetrofitClient
import kotlinx.coroutines.runBlocking
import retrofit2.Retrofit

class MainViewModel : ViewModel() {

    private val _currentBook: MutableLiveData<List<ResponseItems>> = MutableLiveData()
    val currentBook: LiveData<List<ResponseItems>> = _currentBook

    private val _favoriteItems: MutableLiveData<List<ResponseItems>> = MutableLiveData()
    val favoriteItems: LiveData<List<ResponseItems>> = _favoriteItems

    private val _allItems: MutableLiveData<List<ResponseItems>> = MutableLiveData()
    val allItems: LiveData<List<ResponseItems>> = _allItems

    var lastResult: Int = 0

    val retrofit: Retrofit = RetrofitClient.RetrofitClient.getInstance()
    val apiInterface: ApiInterface = retrofit.create(ApiInterface::class.java)

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

    fun getBookList(apiInterface: ApiInterface) = runBlocking {
        try {

            val response = apiInterface.getAllBooks("ios",20,lastResult)
            lastResult +=20
            if (response.isSuccessful()) {
                response.body()?.let { setResponseBook(it.items) }
            } else {
                Log.e("Error",response.errorBody().toString())
            }
        }catch (Ex:Exception){
            Log.e("Error",Ex.localizedMessage)
        }
    }
}