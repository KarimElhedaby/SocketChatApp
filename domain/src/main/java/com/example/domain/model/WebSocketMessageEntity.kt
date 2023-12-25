package com.example.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

// This is an entity for the WebSocketMessageDao class in data\src\main\java\com\example\data\db\WebSocketMessageDao.kt
@Entity(tableName = "messages")
data class WebSocketMessageEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0 ,
    val content: String,
    val type: Int
)
