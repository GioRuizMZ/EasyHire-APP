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

class HomeCarouselAdapter(
    private val items: List<home_carrousel_model>,
    private var mode: Int = 0
) : RecyclerView.Adapter<HomeCarouselAdapter.CarouselViewHolder>() {

    inner class CarouselViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.titleTextView)
        val description: TextView = view.findViewById(R.id.descriptionTextView)
        val image: ImageView = view.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselViewHolder {
        if (mode == 1){
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.categories_item, parent, false)
            return CarouselViewHolder(view)
        }
        else{
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.carrousel_items, parent, false)
            return CarouselViewHolder(view)
        }

    }

    override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) {
        val item = items[position]
        holder.title.text = item.title
        holder.description.text = item.description

        Glide.with(holder.image.context)
            .load(item.imageUrl)
            .into(holder.image)
    }

    override fun getItemCount(): Int = items.size
}
