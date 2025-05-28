package com.example.viewmodel

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    // Create ViewModel using property delegate
    //What is a Property Delegate?
    //A property delegate in Kotlin allows you to delegate the implementation of property accessors (getter/setter) to another object.
    // Instead of storing the value directly, the property "delegates" the responsibility to handle getting and setting values to a delegate object.
    private val viewModel: CounterViewModel by viewModels()
//equivalent to
//    private val viewModel: CounterViewModel by lazy {
//    ViewModelProvider(this)[CounterViewModel::class.java]


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Find views
        val tvCounter = findViewById<TextView>(R.id.tvCounter)
        val tvMessage = findViewById<TextView>(R.id.tvMessage)
        val btnIncrement = findViewById<Button>(R.id.btnIncrement)
        val btnDecrement = findViewById<Button>(R.id.btnDecrement)
        val btnReset = findViewById<Button>(R.id.btnReset)



        // Observe LiveData
        viewModel.counter.observe(this) { count ->
            Log.d("haris","count is ${count}")
            tvCounter.text = count.toString()
        }

        viewModel.message.observe(this) { message ->
            tvMessage.text = message
        }

        // Set click listeners
        btnIncrement.setOnClickListener {
            viewModel.increment()
        }

        btnDecrement.setOnClickListener {
            viewModel.decrement()
        }

        btnReset.setOnClickListener {
            viewModel.reset()
        }
    }
}