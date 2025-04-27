package com.ngio.easyhire.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ngio.easyhire.Adapters.MessagesAdapter
import com.ngio.easyhire.Models.MessagesModel
import com.ngio.easyhire.R

class FragmentOne : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MessagesAdapter
    private val messagesList = mutableListOf<MessagesModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.option_messages_fragment, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.isNestedScrollingEnabled = false

        adapter = MessagesAdapter(messagesList)
        recyclerView.adapter = adapter
        loadMessages()
        return view
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun loadMessages() {
        messagesList.add(MessagesModel("Usuario1", "Hola, ¿cómo estás?", "10:00 AM"))
        messagesList.add(MessagesModel("Usuario2", "¿Vas a la reunión?", "10:05 AM"))
        messagesList.add(MessagesModel("Usuario3", "Sí, ahí estaré.", "10:10 AM"))
        adapter.notifyDataSetChanged()
    }
}
