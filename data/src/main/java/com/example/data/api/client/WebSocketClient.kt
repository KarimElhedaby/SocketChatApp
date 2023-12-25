package com.example.data.api.client

import android.util.Log
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.runBlocking
import okhttp3.*
import javax.inject.Inject

//WebSocketClient is a class that handles the WebSocket connection
open class WebSocketClient @Inject constructor() {

    private var webSocket: WebSocket? = null

    /* socketsbay.com is a website that provides a WebSocket server for testing

      NOTE : BestPractice for that is to store the URL in a config file but for simplicity,
     I will store it here and to make sure it will be pushed to the remote repository */

    private var  websocketUrl = "wss://socketsbay.com/wss/v2/1/demo/"

    var isServerConnected = false
    //messageChannel is a Channel of String objects that will be used to send messages from the WebSocket server to the app
    private val messageChannel = Channel<String>()

    init {
    connect()
    }

    // connect() is a function that creates a WebSocket connection
    private fun connect() {
        val request = Request.Builder().url(websocketUrl).build()
        webSocket = OkHttpClient().newWebSocket(request, object : WebSocketListener() {
            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                Log.e("TAGSocket", "onFailure: " )
                isServerConnected = false
            }

            override fun onOpen(webSocket: WebSocket, response: Response) {
                isServerConnected = true
                Log.e("TAGSocket", "onOpen: " )
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                Log.e("TAGSocket", "onFailure: " )
                isServerConnected = false
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                Log.e("TAGSocket", "onMessage: $text")
                runBlocking {
                    messageChannel.send(text)
                }
            }
        })
    }

    //send() is a function that takes a String object as a parameter and returns nothing to send a message to the WebSocket server
    fun send(message: String) {
         webSocket?.send(message)
    }

  /* receiveMessages() is a function that returns a Flow of String objects from the messageChannel
    CallbackFlow to convert WebSocket messages into a Flow.*/
    fun receiveMessages(): Flow<String> {
    return messageChannel.receiveAsFlow()
    }

   //closeWebSocketConnection() is a suspend function that returns nothing to close the WebSocket connection
     fun closeWebSocketConnection() {
        webSocket?.cancel()
        webSocket?.close(1000, "Closed by client")
        webSocket = null
    }
}
