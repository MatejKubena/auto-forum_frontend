package com.example.mtaaprojekt

import android.R
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.mtaaprojekt.databinding.ActivityProfileBinding
import org.json.JSONArray
import org.json.JSONObject


class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private var requestQueue: RequestQueue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val textView = findViewById<TextView>(R.id.text)
// ...


//        val url = "http://192.168.100.16:8080/user?id=1"
//
//        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
//            Response.Listener { response ->
//                binding.textView3.text = "Response: %s".format(response.toString())
//            },
//            Response.ErrorListener { error ->
//            }
//        )
//
//// Access the RequestQueue through your singleton class.
//        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)


        val queue = Volley.newRequestQueue(this)
        val url = "http://192.168.100.16:8080/user?id=1"

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener { response ->

                binding.textView3.text = "Response is: ${response.getString("password")}"
                binding.textView4.text = "Response is: ${response.getString("email")}"
            },
            Response.ErrorListener { error ->
                binding.textView3.text = error.toString()
                binding.textView3.text = "ooooo"
            }
        )

        queue.add(jsonObjectRequest)

//// Instantiate the RequestQueue.
//        val queue = Volley.newRequestQueue(this)
//        val url = "http://192.168.100.16:8080/user?id=1"
//
//// Request a string response from the provided URL.
//        val stringRequest = StringRequest(Request.Method.GET, url,
//                Response.Listener<String> { response ->
//                    // Display the first 500 characters of the response string.
//                    binding.textView3.text = "Response is: ${response}"
//
//                },
//                Response.ErrorListener { error ->
//                    binding.textView3.text = error.toString()
//                }
//        )
//
//// Add the request to the RequestQueue.
//        queue.add(stringRequest)


// Access the RequestQueue through your singleton class.
//        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)

//
//        requestQueue = Volley.newRequestQueue(this)
//        button.setOnClickListener {
//            jsonParse()
//        }

        // Instantiate the RequestQueue.
//        val queue = Volley.newRequestQueue(this)
//        val url = "http://localhost:8080/user?id=1"
//
//        // Request a string response from the provided URL.
//        val stringRequest = StringRequest(Request.Method.GET, url,
//            Response.Listener<String> { response ->
//                // Display the first 500 characters of the response string.
//                binding.textView3.text= "Response is: ${response.substring(0, 10)}"
//            },
//            Response.ErrorListener { binding.textView3.text = "That didn't work!" })
//
//        // Add the request to the RequestQueue.
//        queue.add(stringRequest)

//        val requestQueue = Volley.newRequestQueue(applicationContext)
//        try {
//            val url = "http://localhost:8080/user?id=1"
//            val jsonObject = JSONObject()
//            val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null, Response.Listener { response ->
//                binding.textView3.text= "Response is: $response"
//                Toast.makeText(applicationContext, "I am OK !$response", Toast.LENGTH_LONG).show()
//            }, Response.ErrorListener { Toast.makeText(applicationContext, "Error", Toast.LENGTH_LONG).show() })
//            requestQueue.add(jsonObjectRequest)
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }


//        getUsers()
//    }
//
//        // function for network call
//    fun getUsers() {
//        // Instantiate the RequestQueue.
//        val queue = Volley.newRequestQueue(this)
//        val url: String = "http://localhost:8080/user?id=1"
//
//        // Request a string response from the provided URL.
//        val stringReq = StringRequest(Request.Method.GET, url,
//            Response.Listener<String> { response ->
//
//                var strResp = response.toString()
//                val jsonObj: JSONObject = JSONObject(strResp)
//                val jsonArray: JSONArray = jsonObj.getJSONArray("items")
//                var str_user: String = ""
//                for (i in 0 until jsonArray.length()) {
//                    var jsonInner: JSONObject = jsonArray.getJSONObject(i)
//                    str_user = str_user + "\n" + jsonInner.get("login")
//                }
//                textView!!.text = "response : $str_user "
//            },
//            Response.ErrorListener { textView!!.text = "That didn't work!" })
//        queue.add(stringReq)
//    }

//        binding.textView3.text = "jhjjjj"
//
//        val url = "http://localhost:8080/user?id=1"
//        val request = JsonObjectRequest(Request.Method.GET, url, null, Response.Listener {
//                response ->try {
////            val jsonArray = response.getJSONArray("user")
////
////            val user = jsonArray.getJSONObject(0)
//
//            val jsonArray = response.toString()
//            val jsonObject = JSONArray(jsonArray)
//            val user = JSONObject(jsonObject[0].toString())
//
//            binding.textView3.text = "1"
//            binding.textView3.text = user.getString("username")
//            binding.textView4.text = "2"
//            binding.textView4.text = user.getString("email")
//            binding.textView5.text = "3"
//            binding.textView5.text = user.getString("password")
//
////            for (i in 0 until jsonArray.length()) {
////                val employee = jsonArray.getJSONObject(i)
////                val firstName = employee.getString("firstname")
////                val age = employee.getInt("age")
////                val mail = employee.getString("mail")
////            }
//        } catch (e: JSONException) {
//            binding.textView3.text = "kek"
//            e.printStackTrace()
//        }
//        }, Response.ErrorListener { error -> error.printStackTrace() })
//        requestQueue?.add(request)

//        val stringRequest = StringRequest(Request.Method.GET, url,
//            { response ->
//                val jsonArray = response.toString()
//                val jsonObject = JSONArray(jsonArray)
//                val user = JSONObject(jsonObject[0].toString())
//
//                binding.textView3.text = user.getString("username")
//                binding.textView4.text = user.getString("email")
//                binding.textView5.text = user.getString("password")
//
//            }
//
//            {Toast.makeText(this, "Error occured", Toast.LENGTH_SHORT).show() })

//        val url = URL("http://localhost:8080/user?id=1")
//
//        with(url.openConnection() as HttpURLConnection) {
//            requestMethod = "GET"  // optional default is GET
//
//            println("\nSent 'GET' request to URL : $url; Response Code : $responseCode")
//
//            inputStream.bufferedReader().use {
//                it.lines().forEach { line ->
//                    println(line)
//                }
//            }
//        }
    }

    // Get Request For JSONObject
//    fun getData() {
//        val requestQueue = Volley.newRequestQueue(applicationContext)
//        try {
//            val url = "http://localhost:8080/user?id=1"
//            val `object` = JSONObject()
//            val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null, Response.Listener { response ->
//                resultTextView.setText("Resposne : $response")
//                Toast.makeText(applicationContext, "I am OK !$response", Toast.LENGTH_LONG).show()
//            }, Response.ErrorListener { Toast.makeText(applicationContext, "Error", Toast.LENGTH_LONG).show() })
//            requestQueue.add(jsonObjectRequest)
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }
}