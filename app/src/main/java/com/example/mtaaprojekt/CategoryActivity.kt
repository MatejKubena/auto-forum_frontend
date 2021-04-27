package com.example.mtaaprojekt

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.mtaaprojekt.databinding.ActivityCategoryBinding
import kotlinx.android.synthetic.main.activity_category.*

class CategoryActivity : AppCompatActivity(), CategoryAdapter.OnItemClickListener {

    private lateinit var binding: ActivityCategoryBinding
    val exampleList = ArrayList<CategoryItem>()
    var adapter = CategoryAdapter(exampleList, this)
    var userId: String? = ""
    var categoryId: String? = ""

    var postTitle: ArrayList<String> = ArrayList()
    var postText: ArrayList<String> = ArrayList()
    var postId: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {

        categoryId = intent.extras!!.getString("categoryId")
        userId = intent.extras!!.getString("userId")

        Log.d("userId", userId.toString())
        super.onCreate(savedInstanceState)

        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)

        binding.topBarBack.setOnClickListener {
            val goToHome = Intent(this, HomeActivity::class.java)
            goToHome.putExtra("userId", userId)
            startActivity(goToHome)
        }

        binding.topBarAddPost.setOnClickListener {
            val goToActivity = Intent(this, AddpostActivity::class.java)
            goToActivity.putExtra("userId", userId)
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
        val url = "http://192.168.100.16:8080/posts/category?id=$categoryId"

        val jsonArrayRequest = JsonArrayRequest(Request.Method.GET, url, null,
            Response.Listener { response ->

                for (i in 0 until response.length()) {
                    val userDetail = response.getJSONObject(i)
                    postTitle.add(userDetail.getString("title"))
                    postText.add(userDetail.getString("description"))
                    postId.add(userDetail.getString("id"))

                    val item = CategoryItem(userDetail.getString("title"), userDetail.getString("description"))
                    exampleList.add(i, item)
                    adapter.notifyItemInserted(i)
                }
            },
            Response.ErrorListener { error ->
            }
        )

        queue.add(jsonArrayRequest)

        val url1 = "http://192.168.100.16:8080/category?id=$categoryId"

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url1, null,
            Response.Listener { response ->

                binding.topBarTitle.text = response.getString("name")
            },
            Response.ErrorListener { error ->
            }
        )
        queue.add(jsonObjectRequest)
    }

    override fun onItemClick(position: Int) {
        val goToPost = Intent(this, PostActivity::class.java)
        goToPost.putExtra("userId", userId)
        goToPost.putExtra("postId", postId[position])
        goToPost.putExtra("categoryId", categoryId)
        startActivity(goToPost)
    }
}