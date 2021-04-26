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
    var userId: String? = ""


    override fun onCreate(savedInstanceState: Bundle?) {

        userId = intent.extras!!.getString("userId")

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
        val url = "http://192.168.1.102:8080/home"

        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->

                binding.catItem1.text = response.getJSONObject(0).getString("name")
                binding.catItem2.text = response.getJSONObject(1).getString("name")
                binding.catItem3.text = response.getJSONObject(2).getString("name")
                binding.catItem4.text = response.getJSONObject(3).getString("name")
                binding.catItem5.text = response.getJSONObject(4).getString("name")
                binding.catItem6.text = response.getJSONObject(5).getString("name")
                binding.catItem7.text = response.getJSONObject(6).getString("name")

            },
            Response.ErrorListener { error ->
            }
        )

        queue.add(jsonArrayRequest)

        binding.catItem1.setOnClickListener {
            goTOSomewhere("1")
        }

        binding.catItem2.setOnClickListener {
            goTOSomewhere("2")
        }

        binding.catItem3.setOnClickListener {
            goTOSomewhere("3")
        }

        binding.catItem4.setOnClickListener {
            goTOSomewhere("4")
        }

        binding.catItem5.setOnClickListener {
            goTOSomewhere("5")
        }

        binding.catItem6.setOnClickListener {
            goTOSomewhere("6")
        }
    }

    fun goTOSomewhere(categoryId: String) {
        val goToCategory = Intent(this, CategoryActivity::class.java)
        goToCategory.putExtra("userId", userId)
        goToCategory.putExtra("categoryId", categoryId)
        startActivity(goToCategory)
    }

}