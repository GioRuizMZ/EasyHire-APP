package com.ngio.easyhire.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ngio.easyhire.Models.MessagesModel
import com.ngio.easyhire.R

class MessagesAdapter(private val messages: List<MessagesModel>) :
    RecyclerView.Adapter<MessagesAdapter.MessageViewHolder>() {

    class MessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val user: TextView = view.findViewById(R.id.txtUser)
        val message: TextView = view.findViewById(R.id.txtMessage)
        val time: TextView = view.findViewById(R.id.txtTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.messages_item, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messages[position]
        holder.user.text = message.user
        holder.message.text = message.text
        holder.time.text = message.time
    }

    override fun getItemCount(): Int = messages.size
}
