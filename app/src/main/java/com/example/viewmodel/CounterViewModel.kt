package com.example.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CounterViewModel : ViewModel() {
    
    // Private MutableLiveData
    private val _counter = MutableLiveData<Int>()
    private val _message = MutableLiveData<String>()
    
    // Public LiveData (read-only)
    val counter: LiveData<Int> = _counter
    val message: LiveData<String> = _message
    
    init {
        // Initialize values
        _counter.value = 0
        _message.value = "Click buttons to change counter"
    }
    
    fun increment() {
        val currentValue = _counter.value ?: 0
        _counter.value = -1
        _counter.value = currentValue + 1
        updateMessage()
    }

    fun decrement() {
        val currentValue = _counter.value ?: 0
        _counter.value = currentValue - 1
        updateMessage()
    }

    fun reset() {
        _counter.value = 0
        _message.value = "Counter reset!"
    }

    private fun updateMessage() {
        val count = _counter.value ?: 0
        _message.value = when {
            count > 10 -> "Wow! You're on fire! 🔥"
            count > 5 -> "Great job! Keep going! 👍"
            count < 0 -> "Going negative? 📉"
            else -> "Keep clicking! 👆"
        }
    }
}