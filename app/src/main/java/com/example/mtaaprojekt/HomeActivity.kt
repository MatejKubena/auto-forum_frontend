package com.example.mtaaprojekt

import android.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
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
            goToProfile.putExtra("userId", userId)
            startActivity(goToProfile)
        }

        binding.navButtHome.setOnClickListener{
            val goToHome = Intent(this, HomeActivity::class.java)
            goToHome.putExtra("userId", userId)
            startActivity(goToHome)
        }

        binding.navButtMyposts.setOnClickListener{
            val goToMypost = Intent(this, MypostActivity::class.java)
            goToMypost.putExtra("userId", userId)
            startActivity(goToMypost)
        }

        binding.navButtMore.setOnClickListener {
            val goToMore = Intent(this, MoreActivity::class.java)
            goToMore.putExtra("userId", userId)
            startActivity(goToMore)
        }

        val queue = Volley.newRequestQueue(this)
        val url = "http://192.168.100.16:8080/home"

        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->

                binding.rules.text = response.getJSONObject(0).getString("name")
                binding.discussions.text = response.getJSONObject(1).getString("name")
                binding.diagnostics.text = userId.toString()

            },
            Response.ErrorListener { error ->
            }
        )

        queue.add(jsonArrayRequest)

        binding.rules.setOnClickListener {
            goTOSomewhere("1")
        }

        binding.rules.setOnClickListener {
            goTOSomewhere("2")
        }

        binding.rules.setOnClickListener {
            goTOSomewhere("3")
        }

        binding.rules.setOnClickListener {
            goTOSomewhere("4")
        }

        binding.rules.setOnClickListener {
            goTOSomewhere("5")
        }

        binding.rules.setOnClickListener {
            goTOSomewhere("6")
        }


    }

    fun goTOSomewhere(categoryId: String) {
        val goToCategory = Intent(this, CategoryActivity::class.java)
        goToCategory.putExtra("categoryId", categoryId)
        startActivity(goToCategory)
    }

}