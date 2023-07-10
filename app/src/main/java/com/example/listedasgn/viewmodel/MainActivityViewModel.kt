package com.example.listedasgn.viewmodel

import androidx.lifecycle.LiveData
import com.example.listedasgn.Result
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.listedasgn.repository.DataRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class MainActivityViewModel(private val repository: DataRepository) : ViewModel() {

    private val _result = MutableLiveData<Result>()
    val result:LiveData<Result> get() = _result
    private val error = MutableLiveData<String>()

    fun getResultData(token: String) {
        val response = repository.getData()
        response.enqueue(object : Callback<Result> {
            override fun onResponse(call: Call<Result>, response: Response<Result>) {
                _result.postValue(response.body())
            }

            override fun onFailure(call: Call<Result>, t: Throwable) {
                error.postValue(t.message)
            }
        })
    }
}
