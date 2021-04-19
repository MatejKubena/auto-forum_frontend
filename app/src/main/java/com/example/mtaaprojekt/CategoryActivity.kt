package com.example.mtaaprojekt

import android.content.Intent
import android.os.Bundle
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
//    val exampleList = generateDummyList(500)
    val exampleList = ArrayList<CategoryItem>()
//    val adapter = CategoryAdapter(exampleList, this)
    var adapter = CategoryAdapter(exampleList, this)

    var postTitle: ArrayList<String> = ArrayList()
    var postText: ArrayList<String> = ArrayList()
    var postId: ArrayList<Int> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {

//        val categoryId = intent.extras!!.getString("categoryId")

        super.onCreate(savedInstanceState)

        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.topBarBack.setOnClickListener {
            finish()
        }

        val queue = Volley.newRequestQueue(this)
        val url = "http://192.168.100.16:8080/posts/category?id=2"

        val jsonArrayRequest = JsonArrayRequest(Request.Method.GET, url, null,
            Response.Listener { response ->

//                val obj = response
//                val userArray = obj.getJSONArray("users")
//                for (i in 0 until userArray.length()) {
//                    val userDetail = userArray.getJSONObject(i)
//                    personName.add(userDetail.getString("name"))
//                    emailId.add(userDetail.getString("email"))
//                    val contact = userDetail.getJSONObject("contact")
//                    mobileNumbers.add(contact.getString("mobile"))
//                }

                for (i in 0 until response.length()) {
                    val userDetail = response.getJSONObject(i)
                    postTitle.add(userDetail.getString("title"))
                    postText.add(userDetail.getString("description"))
                    postId.add(userDetail.getInt("id"))

                    val item = CategoryItem(userDetail.getString("title"), userDetail.getString("description").substring(0, 20))
                    exampleList += item
                }

//                binding.textView3.text = "Response is: ${response.getString("password")}"
//                binding.textView4.text = "Response is: ${response.getString("email")}"
            },
            Response.ErrorListener { error ->
//                binding.textView3.text = error.toString()
//                binding.textView3.text = "ooooo"
            }
        )

        queue.add(jsonArrayRequest)

//        recycler_view.adapter = adapter
        adapter = CategoryAdapter(exampleList, this)
        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)


//        val queue = Volley.newRequestQueue(this)
//        val url = "http://192.168.100.16:8080/posts?id=$categoryId"
//
//        val jsonObjectRequest = JsonObjectRequest(
//            Request.Method.GET, url, null,
//            Response.Listener { response ->
//
//                binding.textView3.text = "Response is: ${response.getString("password")}"
//                binding.textView4.text = "Response is: ${response.getString("email")}"
//            },
//            Response.ErrorListener { error ->
//                binding.textView3.text = error.toString()
//                binding.textView3.text = "ooooo"
//            }
//        )
//
//        queue.add(jsonObjectRequest)

    }

    override fun onItemClick(position: Int) {
        Toast.makeText(this, "Item $position clicked", Toast.LENGTH_SHORT).show()
        val clickedItem = exampleList[position]
//        clickedItem.text1 = "Clicked"
        adapter.notifyItemChanged(position)

        val goToPost = Intent(this, PostActivity::class.java)
//        goToPost.putExtra("userId", userId)
        startActivity(goToPost)
    }

    private fun generateDummyList(size: Int): List<CategoryItem> {

        val list = ArrayList<CategoryItem>()

        for (i in 0 until size) {

            val item = CategoryItem("Item $i", "Line 2")
            list += item
        }
        return list
    }
}