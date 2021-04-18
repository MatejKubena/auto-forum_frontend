package com.example.mtaaprojekt

import android.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mtaaprojekt.databinding.ActivityHomeBinding
import com.example.mtaaprojekt.databinding.ActivityLoginBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding


    override fun onCreate(savedInstanceState: Bundle?) {

        val userId = intent.extras!!.getString("userId")

        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)



        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.navButtProfile.setOnClickListener {

            val goToProfile = Intent(this, ProfileActivity::class.java)
            startActivity(goToProfile)
        }
    }

}