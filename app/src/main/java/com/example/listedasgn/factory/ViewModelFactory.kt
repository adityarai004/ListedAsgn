package com.example.listedasgn.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.listedasgn.repository.DataRepository
import com.example.listedasgn.viewmodel.MainActivityViewModel

class ViewModelFactory constructor(private val repository: DataRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
            MainActivityViewModel(this.repository) as T
        }
        else{
            throw IllegalArgumentException("View Model Not Found")
        }
    }
}