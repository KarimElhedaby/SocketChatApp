package com.example.domain.usecase

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
class ReceiveMessagesUseCaseTest {

    @Mock
    private lateinit var webSocketRepository: WebSocketRepository

    @InjectMocks
    private lateinit var receiveMessagesUseCase: ReceiveMessagesUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `invoke should return flow from repository`() = runBlocking {
        // Given
        val expectedMessage = "Test message"

        // Mock the repository to return the expected flow of messages
        `when`(webSocketRepository.receiveMessages()).thenReturn(flowOf(expectedMessage))

        // When
        val flowResult = receiveMessagesUseCase()

        // Then
        // Verify that the flow from the use case contains the expected message
        flowResult.collect { message ->
            assertEquals(expectedMessage, message)
        }
    }
}
