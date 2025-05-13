package com.example.bsef22pmt


data class UserResponse(
    val users: List<User>,
    val total: Int,
    val skip: Int,
    val limit: Int
)