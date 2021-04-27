package com.example.mtaaprojekt

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.mtaaprojekt.databinding.ActivityAddPostBinding
import com.example.mtaaprojekt.databinding.ActivityEditPostBinding
import org.json.JSONObject

class EditpostActivity : AppCompatActivity()  {

    private lateinit var binding: ActivityEditPostBinding
    var postTitle: String = ""
    var postText: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {

        val userId = intent.extras!!.getString("userId")
        val postId = intent.extras!!.getString("postId")
        val categoryId = intent.extras!!.getString("categoryId")

        super.onCreate(savedInstanceState)

        binding = ActivityEditPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.topBarBack.setOnClickListener{
            val goToActivity = Intent(this, PostActivity::class.java)
            goToActivity.putExtra("userId", userId)
            goToActivity.putExtra("postId", postId)
            goToActivity.putExtra("categoryId", categoryId)
            startActivity(goToActivity)
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
        val url = "http://192.168.100.16:8080/post?id=$postId"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->

                postTitle = response.getString("title")
                postText = response.getString("description")
                binding.editPostTitle.setText(response.getString("title"))
                binding.editPostText.setText(response.getString("description"))
            },
            Response.ErrorListener { error ->
                Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show()
            }
        )

        queue.add(jsonObjectRequest)

        binding.editAddButton.setOnClickListener {

            val queue = Volley.newRequestQueue(this)
            val url = "http://192.168.100.16:8080/post?id=$postId"
            val payload = JSONObject()
            val categoryIdPayload = JSONObject()
            val userIdPayload = JSONObject()

            categoryIdPayload.put("id", categoryId)
            userIdPayload.put("id", userId)

            if (binding.editPostTitle.text.toString().isEmpty()){
                payload.put("title", postTitle)
            } else{
                payload.put("title", binding.editPostTitle.text.toString())
            }

            if (binding.editPostText.text.toString().isEmpty()){
                payload.put("description", postText)
            } else {
                payload.put("description", binding.editPostText.text.toString())
            }

            payload.put("categoryId", categoryIdPayload)
            payload.put("userId", userIdPayload)

            Log.d("tag", payload.toString())

            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.PUT, url, payload,
                Response.Listener { response ->

                    val goToActivity = Intent(this, PostActivity::class.java)
                    goToActivity.putExtra("userId", userId)
                    goToActivity.putExtra("postId", postId)
                    goToActivity.putExtra("categoryId", categoryId)
                    startActivity(goToActivity)
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