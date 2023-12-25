package com.example.domain.usecase

import com.example.domain.model.WebSocketMessageEntity
import com.example.domain.repo.WebSocketRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class CacheMessagesUseCaseTest {

    @Mock
    private lateinit var webSocketRepository: WebSocketRepository

    @InjectMocks
    private lateinit var cacheMessagesUseCase: CacheMessagesUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `invoke cacheMessage should call repository method`() = runBlocking {
        // Given
        val webSocketMessageEntity = WebSocketMessageEntity(content = "Test message", type =  1)

        // When
        cacheMessagesUseCase(webSocketMessageEntity)

        // Then
        // Verify that the repository's cacheMessage method was called with the provided entity
        verify(webSocketRepository).cacheMessage(webSocketMessageEntity)
    }

}
