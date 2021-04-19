package com.example.mtaaprojekt

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mtaaprojekt.databinding.ActivityPostBinding

class PostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPostBinding

    override fun onCreate(savedInstanceState: Bundle?) {

//        val userId = intent.extras!!.getString("userId")

        super.onCreate(savedInstanceState)

        binding = ActivityPostBinding.inflate(layoutInflater)
        setContentView(binding.root)




    }
}