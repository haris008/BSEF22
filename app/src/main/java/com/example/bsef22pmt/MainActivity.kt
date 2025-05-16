package com.example.bsef22pmt

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val imageView = findViewById<ImageView>(R.id.imgView)

        Glide.with(this)
        .load("https://media.istockphoto.com/id/2113989619/photo/team-of-scientists-working-on-a-research-in-laboratory.jpg?s=1024x1024&w=is&k=20&c=r99mG06NPokMsWFj1dPHMqpHzhHU69OM-u4YIqxKRUA=")
        .into(imageView)



        val apiService = RetrofitClient.apiService
        apiService.getUsers().enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                Log.d("haris","api success")
                val users = response.body()?.users
                // use users object with recyclerview or anything
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.d("haris","api failure")
            }
        })


    }
}