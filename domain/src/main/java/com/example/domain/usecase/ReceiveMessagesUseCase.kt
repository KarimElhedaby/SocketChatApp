package com.example.domain.usecase

import com.example.domain.repo.WebSocketRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


open class ReceiveMessagesUseCase @Inject constructor(
    private val webSocketRepository: WebSocketRepository
) {

    operator fun invoke(): Flow<String> =  webSocketRepository.receiveMessages()
}
