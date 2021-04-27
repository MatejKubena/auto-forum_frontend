package com.example.mtaaprojekt

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.mtaaprojekt.databinding.ActivityForeignProfileBinding

class ForeignprofileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForeignProfileBinding
    lateinit var imageBitmap: Bitmap

    var userId: String? = ""
    var usernameString: String = ""
    var emailString: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {

        val userId = intent.extras!!.getString("userId")
        val postId = intent.extras!!.getString("postId")
        val categoryId = intent.extras!!.getString("categoryId")
        val userPostId = intent.extras!!.getString("userPostId")

        super.onCreate(savedInstanceState)

        binding = ActivityForeignProfileBinding.inflate(layoutInflater)
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
        val url1 = "http://192.168.100.16:8080/user?id=$userPostId"

        val jsonObjectRequest1 = JsonObjectRequest(
            Request.Method.GET, url1, null,
            Response.Listener { response ->

                val jsonArray = response.getJSONArray("posts")
                val jsonArray1 = response.getJSONArray("comments")
                val imageBytes = Base64.decode(response.getString("picture"), Base64.DEFAULT)

                imageBitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                binding.imageView.setImageBitmap(imageBitmap)

                binding.hereusername.text = response.getString("username")
                usernameString = response.getString("username")
                binding.hereemail.text = response.getString("email")
                emailString = response.getString("email")
                binding.hereregistered.text = response.getString("createdAt").substring(0, 10)
                binding.herecomments.text = jsonArray1.length().toString()
                binding.herethreads.text = jsonArray.length().toString()
            },
            Response.ErrorListener { error ->
                Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(jsonObjectRequest1)
    }
}