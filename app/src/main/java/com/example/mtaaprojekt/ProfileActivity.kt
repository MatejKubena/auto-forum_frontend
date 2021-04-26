package com.example.mtaaprojekt

import android.content.CursorLoader
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.mtaaprojekt.databinding.ActivityProfileBinding
import java.io.ByteArrayOutputStream


abstract class ProfileActivity : AppCompatActivity() {


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
        val url = "http://192.168.1.102:8080/user?id=$userId"

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


        binding.savechanges.setOnClickListener {
            //TO-DO nacitanie  (get) obrazku zo servera //

            //nacitanie obrazky z image
            //pripadne priradenie mena
            //poslanie na server

            

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            binding.imageView.setImageURI(imageUri)
            Log.d("image", imageUri.toString())
            getPath(imageUri)
        }
    }


    private fun getPath(contentUri: Uri?): String? {
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val loader = CursorLoader(applicationContext, contentUri, proj, null, null, null)
        val cursor: Cursor = loader.loadInBackground()
        val column_index: Int = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        val result: String = cursor.getString(column_index)
        cursor.close()
        premena = result
        Log.d("", premena)
        return result
    }
        var premena:String = ""

    fun getFileDataFromDrawable(bitmap: Bitmap): ByteArray? {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream)
        return byteArrayOutputStream.toByteArray()
    }
    abstract var bitmapa:Bitmap

}