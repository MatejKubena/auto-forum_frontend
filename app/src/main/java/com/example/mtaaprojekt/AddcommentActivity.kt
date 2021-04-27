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
import com.example.mtaaprojekt.databinding.ActivityAddCommentBinding
import com.example.mtaaprojekt.databinding.ActivityAddPostBinding
import org.json.JSONObject

class AddcommentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddCommentBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        val userId = intent.extras!!.getString("userId")
        val postId = intent.extras!!.getString("postId")
        val categoryId = intent.extras!!.getString("categoryId")

        super.onCreate(savedInstanceState)

        binding = ActivityAddCommentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.topBarBack.setOnClickListener{
            val goToHome = Intent(this, PostActivity::class.java)
            goToHome.putExtra("userId", userId)
            goToHome.putExtra("postId", postId)
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

            if (binding.editPostTitle.text.toString().isEmpty()){
                Toast.makeText(this, "pass", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val queue = Volley.newRequestQueue(this)
            val url = "http://192.168.100.16:8080/comment"
            val payload = JSONObject()
            val postIdPayload = JSONObject()
            val userIdPayload = JSONObject()

            postIdPayload.put("id", postId)
            userIdPayload.put("id", userId)
            payload.put("description", binding.editPostTitle.text.toString())
            payload.put("postId", postIdPayload)
            payload.put("userId", userIdPayload)

            Log.d("tag", payload.toString())

            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.POST, url, payload,
                Response.Listener { response ->

                    val goToHome = Intent(this, PostActivity::class.java)
                    goToHome.putExtra("userId", userId)
                    goToHome.putExtra("postId", postId)
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