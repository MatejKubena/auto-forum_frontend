package com.example.mtaaprojekt

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mtaaprojekt.databinding.ActivityCategoryBinding
import com.example.mtaaprojekt.databinding.ActivityMypostBinding
import kotlinx.android.synthetic.main.activity_category.*

class MypostActivity : AppCompatActivity(){

    private lateinit var binding: ActivityMypostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMypostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val exampleList = generateDummyList(500)

//        recycler_view.adapter = CategoryAdapter(exampleList)
//        recycler_view.layoutManager = LinearLayoutManager(this)
//        recycler_view.setHasFixedSize(true)

        binding.topBarBack.setOnClickListener {
            finish()
        }

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