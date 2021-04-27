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
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {

            if (binding.editTextTextUsername.text.isEmpty()){
                Toast.makeText(this, "user", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (binding.editTextTextPassword.text.isEmpty()){
                Toast.makeText(this, "pass", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val queue = Volley.newRequestQueue(this)
            val url = "http://192.168.100.16:8080/login"
            val payload = JSONObject()

            payload.put("username", binding.editTextTextUsername.text.toString())
            payload.put("password", binding.editTextTextPassword.text.toString())

            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.POST, url, payload,
                Response.Listener { response ->

                    val userId = response.getString("id")

                    Toast.makeText(this, userId.toString(), Toast.LENGTH_SHORT).show()

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

        binding.button8.setOnClickListener {

            val goToRegister = Intent(this, RegisterActivity::class.java)
            startActivity(goToRegister)
        }
    }

    override fun onBackPressed() {
        return
    }
}