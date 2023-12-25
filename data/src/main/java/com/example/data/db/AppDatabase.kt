package com.example.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.domain.model.WebSocketMessageEntity

// AppDatabase is a Room database that contains the webSocketMessageDao
@Database(entities = [WebSocketMessageEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun webSocketMessageDao(): WebSocketMessageDao
}
