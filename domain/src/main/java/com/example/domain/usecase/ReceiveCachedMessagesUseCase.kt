package com.example.domain.usecase

import com.example.domain.model.WebSocketMessageEntity
import com.example.domain.repo.WebSocketRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

open class ReceiveCachedMessagesUseCase @Inject constructor(
    private val webSocketRepository: WebSocketRepository
) {

     operator fun invoke(): Flow<List<WebSocketMessageEntity>> {
        return webSocketRepository.getCachedMessages()
    }
}
