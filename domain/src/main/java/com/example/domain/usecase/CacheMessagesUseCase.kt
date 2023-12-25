package com.example.domain.usecase

import com.example.domain.model.WebSocketMessageEntity
import com.example.domain.repo.WebSocketRepository
import javax.inject.Inject


open class CacheMessagesUseCase @Inject constructor(
    private val webSocketRepository: WebSocketRepository
) {

    suspend operator fun invoke(webSocketMessageEntity: WebSocketMessageEntity) {
        return webSocketRepository.cacheMessage(webSocketMessageEntity)
    }

}
