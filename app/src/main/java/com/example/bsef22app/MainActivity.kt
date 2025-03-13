package com.example.bsef22app

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bsef22app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//        setContentView(R.layout.activity_main)

        Log.d("haris","activity onCreate")
        val binding:ActivityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main)
//        val binding:ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
//        binding.button.setOnClickListener {
//            Log.d("haris","button pressed")
//            binding.name = "name changed"
//        }
//        val navController = findNavController(R.id.nav_host)
//        navController.navigate(R.id.action_blankFragment_to_secondFragment)

//        val button = findViewById<Button>(R.id.button)
        binding.name = "Bsef22 button"

        binding.button.setOnClickListener {
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
            val navController = navHostFragment.navController
            navController.navigate(R.id.action_blankFragment_to_secondFragment)
        }




    }



    override fun onRestart() {
        super.onRestart()
        Log.d("haris","activity onRestart")
    }

    override fun onStart() {
        super.onStart()

        Log.d("haris","activity onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("haris","activity onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("haris","activity onPause")
    }


    override fun onStop() {
        super.onStop()
        Log.d("haris","activity onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("haris","activity onDestroy")
    }


}