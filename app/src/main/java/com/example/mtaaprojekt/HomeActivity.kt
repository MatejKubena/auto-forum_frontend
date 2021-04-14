package com.example.mtaaprojekt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mtaaprojekt.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)


        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}