package com.example.mtaaprojekt

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.mtaaprojekt.databinding.ActivityMypostBinding
import com.example.mtaaprojekt.databinding.ActivityPostBinding
import org.json.JSONObject

class PostActivity : AppCompatActivity(), CategoryAdapter.OnItemClickListener {

    private lateinit var binding: ActivityPostBinding

    val exampleList = ArrayList<CategoryItem>()
    var adapter = CategoryAdapter(exampleList, this)
    var userId: String? = ""
    var postId: String? = ""

    var postUsername: ArrayList<String> = ArrayList()
    var postText: ArrayList<String> = ArrayList()
    var userIds: ArrayList<String> = ArrayList()
    var commentIds: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {

        val userId = intent.extras!!.getString("userId")
        postId = intent.extras!!.getString("postId")

        super.onCreate(savedInstanceState)

        binding = ActivityPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.topBarBack.setOnClickListener {
            finish()
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
            },
            Response.ErrorListener { error ->
                Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show()
            }
        )

        queue.add(jsonObjectRequest)

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

                    val item = CategoryItem(jsonArray.getString("username"), userDetail.getString("description"))
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
        Toast.makeText(this, "Item $position clicked", Toast.LENGTH_SHORT).show()
//        val clickedItem = exampleList[position]
//        clickedItem.text1 = "Clicked"
//        adapter.notifyItemChanged(position)

//        val goToPost = Intent(this, PostActivity::class.java)
//        goToPost.putExtra("userId", userId)
//        goToPost.putExtra("commentUserId", userIds[position])
//        goToPost.putExtra("commentId", commentIds[position])
//        startActivity(goToPost)
    }

}