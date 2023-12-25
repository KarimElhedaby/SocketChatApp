package com.example.domain.repo

import com.example.domain.model.WebSocketMessageEntity
import kotlinx.coroutines.flow.Flow

// This is an interface for the WebSocketRepositoryImpl class in data\src\main\java\com\example\data\api\repoImpl\WebSocketRepositoryImpl.kt
interface WebSocketRepository {
    suspend fun closeWebSocketConnection()
    fun sendMessage(message: String)
    fun receiveMessages(): Flow<String>
      fun getCachedMessages(): Flow<List<WebSocketMessageEntity>>
    suspend fun cacheMessage(webSocketMessageEntity: WebSocketMessageEntity)

}
