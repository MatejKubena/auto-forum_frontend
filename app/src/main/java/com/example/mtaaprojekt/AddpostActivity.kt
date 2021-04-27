package com.example.mtaaprojekt

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.mtaaprojekt.databinding.ActivityAddPostBinding
import com.example.mtaaprojekt.databinding.ActivityPostBinding
import org.json.JSONObject

class AddpostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddPostBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        val userId = intent.extras!!.getString("userId")
        val categoryId = intent.extras!!.getString("categoryId")

        super.onCreate(savedInstanceState)

        binding = ActivityAddPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.topBarBack.setOnClickListener{
            val goToHome = Intent(this, CategoryActivity::class.java)
            goToHome.putExtra("userId", userId)
            goToHome.putExtra("categoryId", categoryId)
            startActivity(goToHome)
        }

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

        binding.editAddButton.setOnClickListener {

            if (binding.editPostText.text.toString().isEmpty()){
                Toast.makeText(this, "user", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (binding.editPostTitle.text.isEmpty()){
                Toast.makeText(this, "pass", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val queue = Volley.newRequestQueue(this)
            val url = "http://192.168.100.16:8080/post"
            val payload = JSONObject()
            val categoryIdPayload = JSONObject()
            val userIdPayload = JSONObject()

            categoryIdPayload.put("id", categoryId)
            userIdPayload.put("id", userId)
            payload.put("title", binding.editPostTitle.text.toString())
            payload.put("description", binding.editPostText.text.toString())
            payload.put("categoryId", categoryIdPayload)
            payload.put("userId", userIdPayload)

            Log.d("tag", payload.toString())

            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.POST, url, payload,
                Response.Listener { response ->

                    val goToHome = Intent(this, CategoryActivity::class.java)
                    goToHome.putExtra("userId", userId)
                    goToHome.putExtra("categoryId", categoryId)
                    startActivity(goToHome)
                },
                Response.ErrorListener { error ->
                    Log.d("Error", error.toString())
                    Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show()
                }
            )
            queue.add(jsonObjectRequest)
        }



    }
}