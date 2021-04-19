package com.example.mtaaprojekt

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.mtaaprojekt.databinding.ActivityLoginBinding
import com.example.mtaaprojekt.databinding.ActivityMoreBinding
import org.json.JSONObject

class MoreActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.logOutButton.setOnClickListener {

            val queue = Volley.newRequestQueue(this)
            val url = "http://192.168.100.16:8080/login"
            val payload = JSONObject()


            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.GET, url, payload,
                Response.Listener { response ->

                    val userId = Integer.parseInt(response.getString("id"))

                    Toast.makeText(this, userId, Toast.LENGTH_SHORT).show()

                    val goToHome = Intent(this, HomeActivity::class.java)
                    goToHome.putExtra("userId", userId)
                    startActivity(goToHome)
                },
                Response.ErrorListener { error ->
                    Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show()
                }
            )

            queue.add(jsonObjectRequest)


        }
    }
}