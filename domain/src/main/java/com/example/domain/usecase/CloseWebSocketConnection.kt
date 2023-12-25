package com.example.domain.usecase

import com.example.domain.repo.WebSocketRepository
import javax.inject.Inject


open class CloseWebSocketConnection @Inject constructor(
    private val webSocketRepository: WebSocketRepository
) {

    suspend operator fun invoke() {
        return webSocketRepository.closeWebSocketConnection()
    }
}
