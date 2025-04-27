package com.ngio.easyhire.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ngio.easyhire.Models.home_carrousel_model
import com.ngio.easyhire.R

class GridAdapter(private val itemList: List<home_carrousel_model>) :
    RecyclerView.Adapter<GridAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.titleTextView)
        val description: TextView = itemView.findViewById(R.id.descriptionTextView)
        val image: ImageView = itemView.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.carrousel_items, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.title.text = item.title
        holder.description.text = item.description

        Glide.with(holder.image.context)
            .load(item.imageUrl)
            .into(holder.image)
    }

    override fun getItemCount(): Int = itemList.size
}
