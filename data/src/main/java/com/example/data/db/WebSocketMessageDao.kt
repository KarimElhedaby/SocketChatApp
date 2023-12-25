package com.example.data.db


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.domain.model.WebSocketMessageEntity
import kotlinx.coroutines.flow.Flow

//WebSocketMessageDao is a Data Access Object (DAO) for the WebSocketMessageEntity class in domain\src\main\java\com\example\domain\model\WebSocketMessageEntity.kt
@Dao
interface WebSocketMessageDao {
    @Query("SELECT * FROM messages")
    fun getAllMessages(): Flow<List<WebSocketMessageEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(webSocketMessageEntity: WebSocketMessageEntity)
}
