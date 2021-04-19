package com.example.mtaaprojekt

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mtaaprojekt.databinding.ActivityAddPostBinding
import com.example.mtaaprojekt.databinding.ActivityPostBinding

class AddpostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddPostBinding

    override fun onCreate(savedInstanceState: Bundle?) {

//        val userId = intent.extras!!.getString("userId")

        super.onCreate(savedInstanceState)

        binding = ActivityAddPostBinding.inflate(layoutInflater)
        setContentView(binding.root)




    }
}