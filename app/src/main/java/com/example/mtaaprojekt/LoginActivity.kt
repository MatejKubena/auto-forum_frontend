package com.example.mtaaprojekt

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mtaaprojekt.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {

//            val mail = binding.editTextTextEmailAddress.text
//            val password = binding.editTextTextPassword.text
//
//            val url = "http://localhost:8080/login"

            val goToProfile = Intent(this, ProfileActivity::class.java)
            startActivity(goToProfile)
        }

        binding.button8.setOnClickListener {

//            val mail = binding.editTextTextEmailAddress.text
//            val password = binding.editTextTextPassword.text
//
//            val url = "http://localhost:8080/login"

            val goToRegister = Intent(this, RegisterActivity::class.java)
            startActivity(goToRegister)
        }


    }
}