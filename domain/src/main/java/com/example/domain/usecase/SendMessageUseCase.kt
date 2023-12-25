package com.example.domain.usecase

import com.example.domain.repo.WebSocketRepository
import javax.inject.Inject


open class SendMessageUseCase @Inject constructor(
    private val webSocketRepository: WebSocketRepository
) {

     operator fun invoke(message: String) {
        return webSocketRepository.sendMessage(message)
    }
}
