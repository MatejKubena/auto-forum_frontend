package com.example.mtaaprojekt

import android.R
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
import org.json.JSONObject
import java.io.ByteArrayOutputStream


class ProfileActivity : AppCompatActivity() {


    private lateinit var binding: ActivityProfileBinding
    private var requestQueue: RequestQueue? = null
    lateinit var imageBitmap: Bitmap

    private val pickImage = 100
    private var imageUri: Uri? = null

    var userId: String? = ""
    var usernameString: String = ""
    var emailString: String = ""
    var passwordString: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {

        userId = intent.extras!!.getString("userId")

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

        binding.changepassandname.setOnClickListener{

            val queue = Volley.newRequestQueue(this)
            val url = "http://192.168.100.16:8080/user?id=$userId"
            val payload = JSONObject()

            if (binding.editTextUsername.text.toString().isEmpty()){
                payload.put("username", usernameString)
            } else{
                payload.put("username", binding.editTextUsername.text.toString())
            }

            if (binding.editTextEmail.text.toString().isEmpty()){
                payload.put("email", emailString)
            } else {
                payload.put("email", binding.editTextEmail.text.toString())
            }

            if (binding.editTextPassword.text.toString().isEmpty()){
                payload.put("password", passwordString)
            } else {
                payload.put("password", binding.editTextPassword.text.toString())
            }

            Log.d("tag", payload.toString())

            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.PUT, url, payload,
                Response.Listener { response ->

                    Toast.makeText(this, "Information changed", Toast.LENGTH_SHORT).show()
                    val goToProfile = Intent(this, ProfileActivity::class.java)
                    goToProfile.putExtra("userId", userId)
                    startActivity(goToProfile)
                },
                Response.ErrorListener { error ->
                    Log.d("Error", error.toString())
                    Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show()
                }
            )
            queue.add(jsonObjectRequest)

        }

        val queue = Volley.newRequestQueue(this)
        val url = "http://192.168.100.16:8080/user?id=$userId"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                println(response.getString("picture"))
                val imageBytes = Base64.decode(response.getString("picture"), Base64.DEFAULT)
                imageBitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                println(imageBitmap)
                binding.imageView.setImageBitmap(imageBitmap)
            },
            Response.ErrorListener { error ->
                Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show()
            }
        )

        queue.add(jsonObjectRequest)


        val url1 = "http://192.168.100.16:8080/user?id=$userId"

        val jsonObjectRequest1 = JsonObjectRequest(
            Request.Method.GET, url1, null,
            Response.Listener { response ->

                val jsonArray = response.getJSONArray("posts")
                val jsonArray1 = response.getJSONArray("comments")

                binding.hereusername.text = response.getString("username")
                usernameString = response.getString("username")
                binding.hereemail.text = response.getString("email")
                emailString = response.getString("email")
                binding.hereregistered.text = response.getString("createdAt").substring(0, 10)
                binding.herecomments.text = jsonArray1.length().toString()
                binding.herethreads.text = jsonArray.length().toString()

                binding.editTextUsername.hint = usernameString
                binding.editTextEmail.hint = emailString
                binding.editTextPassword.hint = "Password..."

            },
            Response.ErrorListener { error ->
                Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show()
            }
        )

        queue.add(jsonObjectRequest1)

        val url2 = "http://192.168.100.16:8080/getPass?id=$userId"

        val jsonObjectRequest2 = JsonObjectRequest(
            Request.Method.GET, url2, null,
            Response.Listener { response ->

                passwordString = response.getString("password")
            },
            Response.ErrorListener { error ->
                Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show()
            }
        )

        queue.add(jsonObjectRequest2)
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode == RESULT_OK && requestCode == pickImage) {
//            imageUri = data?.data
//            binding.imageView.setImageURI(imageUri)
//            Log.d("image", imageUri.toString())
//        }
//    }

    fun BitMapToString(bitmap: Bitmap): String {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val b = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 100){
            binding.imageView.setImageURI(data?.data)

            val drawable: BitmapDrawable = binding.imageView.drawable as BitmapDrawable
            val bitmap: Bitmap = drawable.bitmap

            val queue = Volley.newRequestQueue(this)
            val url = "http://192.168.100.16:8080/changePicture"
            val payload = JSONObject()

            payload.put("id", userId)
            payload.put("picture", BitMapToString(bitmap))

            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.PUT, url, payload,
                Response.Listener { response ->

                },
                Response.ErrorListener { error ->
                    Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show()
                }
            )

            queue.add(jsonObjectRequest)
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