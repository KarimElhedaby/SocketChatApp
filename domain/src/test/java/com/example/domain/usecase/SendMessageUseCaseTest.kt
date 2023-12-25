package com.example.domain.usecase

import com.example.domain.repo.WebSocketRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class SendMessageUseCaseTest {

    @Mock
    private lateinit var webSocketRepository: WebSocketRepository

    @InjectMocks
    private lateinit var sendMessageUseCase: SendMessageUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `invoke should call repository method`() = runBlocking {
        // Given
        val message = "Test message"

        // When
        sendMessageUseCase(message)

        // Then
        verify(webSocketRepository).sendMessage(message)
    }
}
