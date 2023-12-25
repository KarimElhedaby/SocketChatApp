package com.example.domain.usecase

import com.example.domain.model.WebSocketMessageEntity
import com.example.domain.repo.WebSocketRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class ReceiveCachedMessagesUseCaseTest {

    @Mock
    private lateinit var webSocketRepository: WebSocketRepository

    @InjectMocks
    private lateinit var receiveCachedMessagesUseCase: ReceiveCachedMessagesUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `invoke should return flow from repository`() = runBlocking {
        // Given
        val expectedMessages = listOf(
            WebSocketMessageEntity(content = "Message 1", type = 1),
            WebSocketMessageEntity(content = "Message 2", type = 2)
        )

        // Mock the repository to return the expected flow of messages
        `when`(webSocketRepository.getCachedMessages()).thenReturn(flowOf(expectedMessages))

        // When
        val flowResult = receiveCachedMessagesUseCase()

        // Then
        // Verify that the flow from the use case contains the expected messages
        flowResult.collect { messages ->
            assertEquals(expectedMessages, messages)
        }
    }
}
