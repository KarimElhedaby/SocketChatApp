package com.example.data.api.repoImpl

import com.example.data.api.client.WebSocketClient
import com.example.data.db.WebSocketMessageDao
import com.example.domain.model.WebSocketMessageEntity
import com.example.domain.repo.WebSocketRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

//WebSocketRepositoryImpl is a repository implementation for the WebSocketRepository interface in domain\src\main\java\com\example\domain\repo\WebSocketRepository.kt
class WebSocketRepositoryImpl @Inject constructor(private val webSocketClient: WebSocketClient
, private val webSocketMessageDao: WebSocketMessageDao
) : WebSocketRepository {

    //closeWebSocketConnection() is a suspend function that returns nothing to close the WebSocket connection in the WebSocketClient class in data\src\main\java\com\example\data\api\client\WebSocketClient.kt
    override suspend fun closeWebSocketConnection() {
        webSocketClient.closeWebSocketConnection()
    }

//sendMessage() is a function that takes a String object as a parameter and returns nothing to send a message to the WebSocketClient class in data\src\main\java\com\example\data\api\client\WebSocketClient.kt
    override fun sendMessage(message: String) {
        webSocketClient.send(message)
    }

//receiveMessages() is a function that returns a Flow of String objects from the WebSocketClient class in data\src\main\java\com\example\data\api\client\WebSocketClient.kt
    override fun receiveMessages(): Flow<String> {
        return webSocketClient.receiveMessages()
    }

//getCachedMessages() is a function that returns a Flow of a list of WebSocketMessageEntity objects from the database
    override fun getCachedMessages():Flow< List<WebSocketMessageEntity>> {
        return webSocketMessageDao.getAllMessages()

    }

    //cacheMessage() is a suspend function that takes a WebSocketMessageEntity object as a parameter and returns nothing to save the message to the database
   override suspend fun cacheMessage(webSocketMessageEntity: WebSocketMessageEntity) {
        webSocketMessageDao.insertMessage(webSocketMessageEntity)
    }
}