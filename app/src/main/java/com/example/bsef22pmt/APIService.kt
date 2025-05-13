package com.example.bsef22pmt

import retrofit2.Call
import retrofit2.http.GET


//https://dummyjson.com/users
interface ApiService {
    @GET("users")
    fun getUsers(): Call<UserResponse>
}