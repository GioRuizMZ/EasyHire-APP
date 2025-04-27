package com.ngio.easyhire.Activities

import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ngio.easyhire.R
import com.ngio.easyhire.Adapters.GridAdapter
import com.ngio.easyhire.Models.home_carrousel_model

class MoreElementsActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: GridAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.enter_element)
        window?.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)

        // Encabezado
        val bundle = intent.extras
        val element = bundle?.getString("element")
        val textElement: TextView = findViewById(R.id.txt_element)
        textElement.text = element
        val back: ImageButton = findViewById(R.id.btn_back)
        back.setOnClickListener { this.finish() }

        //carrousel cuadriculado
        recyclerView = findViewById(R.id.recyclerViewGrid)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        adapter = GridAdapter(getData())
        recyclerView.adapter = adapter
    }

    private fun getData(): List<home_carrousel_model> {
        return listOf(
            home_carrousel_model(
                title = "Title 1",
                description = "Description 1",
                imageUrl = "https://via.placeholder.com/150"
            ),
            home_carrousel_model(
                title = "Title 2",
                description = "Description 2",
                imageUrl = "https://via.placeholder.com/150"
            ),
            home_carrousel_model(
                title = "Title 3",
                description = "Description 3",
                imageUrl = "https://via.placeholder.com/150"
            ),
            home_carrousel_model(
                title = "Title 4",
                description = "Description 4",
                imageUrl = "https://via.placeholder.com/150"
            )
        )
    }
}
