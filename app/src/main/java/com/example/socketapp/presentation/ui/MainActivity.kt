package com.example.socketapp.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.socketapp.databinding.ActivityMainBinding
import com.example.domain.model.WebSocketMessageEntity
import com.example.socketapp.presentation.vm.MainViewModel
import com.example.socketapp.presentation.ui.adapter.MessageAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding
    private val messageAdapter = MessageAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.receiveMessages()
        viewModel.receiveCachedMessages()

      setUpController()
       setUpAdapter()
      observeViewModel()

    }

    private fun setUpController(){
        binding.sendMsgLayout.apply {
            imageViewSend.setOnClickListener {
            val message = editTextMessage.text.toString()
            if (message.isNotBlank()) {
                viewModel.sendMessage(message)
                val sentWebSocketMessageEntity = WebSocketMessageEntity(
                    content = message, type = MessageAdapter.VIEW_TYPE_ME)
                // Add the message to the adapter
                messageAdapter.addNewMessage(sentWebSocketMessageEntity)
                editTextMessage.text.clear()
            }
        }}
    }

    private fun setUpAdapter(){
        binding.messageRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
             adapter = messageAdapter
        }
    }


    private fun observeViewModel() {
        viewModel.incomingMessages.observe(this) { incomingMessage ->
            // Create a Message object with type VIEW_TYPE_SERVER
            val entity = WebSocketMessageEntity(content = incomingMessage,type = MessageAdapter.VIEW_TYPE_SERVER)
            // Add the message to the adapter
            messageAdapter.addNewMessage(entity)
            binding.messageRecyclerView.scrollToPosition(messageAdapter.itemCount - 1)
        }

        viewModel.errorMessage.observe(this) { errorMessage ->
            // Handle error messages, e.g., display in a Toast
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
        }

        viewModel.sentMessageStatus.observe(this) { sentStatus ->
            if (!sentStatus) {
                Toast.makeText(this, "Failed to send message", Toast.LENGTH_SHORT).show()
            }
        }

        // Observe cached messages from the database
        viewModel.cachedMessages.observe(this) { cachedMsgs ->
            messageAdapter.setMessages(cachedMsgs)
            binding.messageRecyclerView.scrollToPosition(messageAdapter.itemCount - 1)
        }
    }
}