package com.example.mtaaprojekt

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.mtaaprojekt.databinding.ActivityPostBinding
import org.json.JSONObject

class PostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPostBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        val userId = intent.extras!!.getString("userId")
        val postId = intent.extras!!.getString("postId")

        super.onCreate(savedInstanceState)

        binding = ActivityPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.topBarBack.setOnClickListener {
            finish()
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

        val queue = Volley.newRequestQueue(this)
        val url = "http://192.168.1.102:8080/post?id=$postId"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->

                val jsonArray = response.getJSONObject("userId")

                binding.postTitle.text = response.getString("title")
                binding.postDate.text = response.getString("createdAt").substring(0, 10)
                binding.postText.text = response.getString("description")
                binding.postUser.text = jsonArray.getString("username")
            },
            Response.ErrorListener { error ->
                Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show()
            }
        )

        queue.add(jsonObjectRequest)




    }
}