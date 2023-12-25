package com.example.socketapp.presentation.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.socketapp.R
import com.example.domain.model.WebSocketMessageEntity


//MessageAdapter is a RecyclerView.Adapter that is responsible for displaying the messages in the RecyclerView.`
class MessageAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_ME = 1
        const val VIEW_TYPE_SERVER = 2
    }

    private val webSocketMessageEntities = mutableListOf<WebSocketMessageEntity>()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_ME -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_message_me, parent, false)
                MyMessageViewHolder(view)
            }
            VIEW_TYPE_SERVER -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_message_server, parent, false)
                ServerMessageViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = webSocketMessageEntities[position]

        when (holder) {
            is MyMessageViewHolder -> holder.bind(message)
            is ServerMessageViewHolder -> holder.bind(message)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return webSocketMessageEntities[position].type
    }

    // ViewHolder for messages sent by the user
    class MyMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewMessageMe: TextView = itemView.findViewById(R.id.textViewMessageMe)

        fun bind(message: WebSocketMessageEntity) {
            textViewMessageMe.text = message.content
        }
    }

    // ViewHolder for messages received from the server
    class ServerMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewMessageServer: TextView = itemView.findViewById(R.id.textViewMessageServer)
        fun bind(webSocketMessageEntity: WebSocketMessageEntity) {
            textViewMessageServer.text = webSocketMessageEntity.content
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    fun setMessages(messageEntities: List<WebSocketMessageEntity>) {
        webSocketMessageEntities.clear()
        webSocketMessageEntities.addAll(messageEntities)
        notifyDataSetChanged()
    }

    fun addNewMessage(newWebSocketMessageEntity: WebSocketMessageEntity) {
        webSocketMessageEntities.add(newWebSocketMessageEntity)
        notifyItemChanged(webSocketMessageEntities.lastIndex)
    }

    override fun getItemCount(): Int = webSocketMessageEntities.size
}
