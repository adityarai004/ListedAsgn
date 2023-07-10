package com.example.listedasgn.repository

import com.example.listedasgn.network.RetroService
import retrofit2.Retrofit

class DataRepository(private val retroService: RetroService) {
    fun getData() = retroService.getResultData("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MjU5MjcsImlhdCI6MTY3NDU1MDQ1MH0.dCkW0ox8tbjJA2GgUx2UEwNlbTZ7Rr38PVFJevYcXFI")
}