package com.example.mtaaprojekt

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.mtaaprojekt.databinding.ActivityMypostBinding
import com.example.mtaaprojekt.databinding.ActivityPostBinding
import kotlinx.android.synthetic.main.activity_category.*
import org.json.JSONObject

class PostActivity : AppCompatActivity(), CategoryAdapter.OnItemClickListener {

    private lateinit var binding: ActivityPostBinding

    val exampleList = ArrayList<CategoryItem>()
    var adapter = CategoryAdapter(exampleList, this)
    var userId: String? = ""
    var postId: String? = ""
    var categoryId: String? = ""
    var userPostId: String? = ""
    lateinit var imageBitmap: Bitmap

    var postUsername: ArrayList<String> = ArrayList()
    var postText: ArrayList<String> = ArrayList()
    var userIds: ArrayList<String> = ArrayList()
    var commentIds: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {

        userId = intent.extras!!.getString("userId")
        postId = intent.extras!!.getString("postId")
        categoryId = intent.extras!!.getString("categoryId")

        super.onCreate(savedInstanceState)

        binding = ActivityPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)

        binding.topBarBack.setOnClickListener {
            val goToHome = Intent(this, CategoryActivity::class.java)
            goToHome.putExtra("userId", userId)
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

        binding.commentAdd.setOnClickListener {
            val goToMore = Intent(this, AddcommentActivity::class.java)
            goToMore.putExtra("userId", userId)
            goToMore.putExtra("postId", postId)
            startActivity(goToMore)
        }

        binding.topBarDelete.setOnClickListener{
            val queue = Volley.newRequestQueue(this)
            val url = "http://192.168.100.16:8080/post?id=$postId"

            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.DELETE, url, null,
                Response.Listener { response ->

                    val goToMore = Intent(this, CategoryActivity::class.java)
                    goToMore.putExtra("userId", userId)
                    goToMore.putExtra("categoryId", categoryId)
                    startActivity(goToMore)
                },
                Response.ErrorListener { error ->
                    Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show()
                }
            )

            queue.add(jsonObjectRequest)
        }

        binding.topBarEdit.setOnClickListener{
            val goToMore = Intent(this, EditpostActivity::class.java)
            goToMore.putExtra("userId", userId)
            goToMore.putExtra("postId", postId)
            goToMore.putExtra("categoryId", categoryId)
            startActivity(goToMore)
        }

        val queue = Volley.newRequestQueue(this)
        val url = "http://192.168.100.16:8080/post?id=$postId"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->

                val jsonArray = response.getJSONObject("userId")

                binding.postTitle.text = response.getString("title")
                binding.postDate.text = response.getString("createdAt").substring(0, 10)
                binding.postText.text = response.getString("description")
                binding.postUser.text = jsonArray.getString("username")
                userPostId = jsonArray.getString("id")

                if (userId == jsonArray.getString("id")){
                    binding.topBarEdit.visibility = View.VISIBLE
                    binding.topBarDelete.visibility = View.VISIBLE
                } else {
                    binding.topBarEdit.visibility = View.INVISIBLE
                    binding.topBarDelete.visibility = View.INVISIBLE
                }

                val url2 = "http://192.168.100.16:8080/user?id=$userPostId"

                val jsonObjectRequest2 = JsonObjectRequest(
                    Request.Method.GET, url2, null,
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

                queue.add(jsonObjectRequest2)
            },
            Response.ErrorListener { error ->
                Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show()
            }
        )

        queue.add(jsonObjectRequest)

        Log.d("userPostId", userPostId.toString())



        val url1 = "http://192.168.100.16:8080/comments?id=$postId"

        val jsonObjectRequest1 = JsonArrayRequest(
            Request.Method.GET, url1, null,
            Response.Listener { response ->

//                val jsonArray = response.getJSONObject("userId")
//
//                binding.postTitle.text = response.getString("title")
//                binding.postDate.text = response.getString("createdAt").substring(0, 10)
//                binding.postText.text = response.getString("description")
//                binding.postUser.text = jsonArray.getString("username")

                binding.commentNumberText.text = response.length().toString()

                for (i in 0 until response.length()) {
                    val userDetail = response.getJSONObject(i)
                    val jsonArray = userDetail.getJSONObject("userId")
                    postUsername.add(jsonArray.getString("username"))
                    postText.add(userDetail.getString("description"))
                    userIds.add(jsonArray.getString("id"))
                    commentIds.add(userDetail.getString("id"))

                    val item = CategoryItem(userDetail.getString("description"), jsonArray.getString("username"))
                    exampleList.add(i, item)
                    adapter.notifyItemInserted(i)
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show()
            }



        )
        queue.add(jsonObjectRequest1)
    }

    override fun onItemClick(position: Int) {
    }

}