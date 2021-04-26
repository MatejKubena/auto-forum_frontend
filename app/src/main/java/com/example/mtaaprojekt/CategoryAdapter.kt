package com.example.mtaaprojekt



import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.catogory_list_item.view.*

class CategoryAdapter(private val categoryList: List<CategoryItem>, private val listener: OnItemClickListener) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.catogory_list_item, parent, false)

        return CategoryViewHolder(itemView)
    }

    override fun getItemCount() = categoryList.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val currentItem = categoryList[position]

        holder.textView1.text = currentItem.text1
        holder.textView2.text = currentItem.text2
    }

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val textView1: TextView = itemView.text_view_1
        val textView2: TextView = itemView.text_view_2

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }

    interface  OnItemClickListener {
        fun onItemClick(position: Int)
    }
}