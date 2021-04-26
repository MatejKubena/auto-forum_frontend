package com.example.mtaaprojekt

import android.R
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.mtaaprojekt.databinding.ActivityProfileBinding


class ProfileActivity : AppCompatActivity() {


    private lateinit var binding: ActivityProfileBinding
    private var requestQueue: RequestQueue? = null


    private val pickImage = 100
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        val userId = intent.extras!!.getString("userId")

        super.onCreate(savedInstanceState)

        binding = ActivityProfileBinding.inflate(layoutInflater)
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

        binding.changeprofilepicbut.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }

        val queue = Volley.newRequestQueue(this)
        val url = "http://192.168.100.16:8080/user?id=$userId"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->

                binding.hereusername.text = response.getString("username")

            },
            Response.ErrorListener { error ->
                Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show()
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            binding.imageView.setImageURI(imageUri)
            Log.d("image", imageUri.toString())
        }
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