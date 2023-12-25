package com.example.di

import com.example.data.api.client.WebSocketClient
import com.example.data.api.repoImpl.WebSocketRepositoryImpl
import com.example.data.db.WebSocketMessageDao
import com.example.domain.repo.WebSocketRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


//AppModule is a Dagger module that lives in the app component and provides dependencies that will exist for the lifetime of the app.
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideWebSocketRepository(webSocketClient: WebSocketClient, webSocketMessageDao: WebSocketMessageDao): WebSocketRepository {
        return WebSocketRepositoryImpl(webSocketClient , webSocketMessageDao)
    }

    @Singleton
    @Provides
    fun provideWebSocketClient(): WebSocketClient {
        return WebSocketClient()
    }

}
