package com.example.mtaaprojekt

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.mtaaprojekt.databinding.ActivityRegisterBinding
import org.json.JSONObject

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            if (binding.editTextTextUsername.text.toString().isEmpty()){
                Toast.makeText(this, "Empty Text", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (binding.editTextTextEmailAddress2.text.toString().isEmpty()){
                Toast.makeText(this, "Empty Text", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (binding.editTextTextPassword.text.toString().isEmpty()){
                Toast.makeText(this, "Empty Text", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val queue = Volley.newRequestQueue(this)
            val url = "http://192.168.100.16:8080/register"
            val payload = JSONObject()

            payload.put("username", binding.editTextTextUsername.text.toString())
            payload.put("email", binding.editTextTextEmailAddress2.text.toString())
            payload.put("password", binding.editTextTextPassword.text.toString())

            val jsonObjectRequest = JsonObjectRequest(Request.Method.POST, url, payload,
                Response.Listener<JSONObject?> { response ->
                    val goLogin = Intent(this, LoginActivity::class.java)
                    startActivity(goLogin)
                },
                Response.ErrorListener { error ->
                    print(error.toString())
                }
            )
            queue.add(jsonObjectRequest)
        }
    }
}