package com.example.domain.usecase

import com.example.domain.repo.WebSocketRepository
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class CloseWebSocketUseCaseTest {

    @Mock
    private lateinit var webSocketRepository: WebSocketRepository

    @InjectMocks
    private lateinit var closeWebSocketConnection: CloseWebSocketConnection

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `invoke close should call repository method`() = runBlocking {

        // When
        closeWebSocketConnection()

        // Then
        // Verify that the repository's connect method was called
        verify(webSocketRepository).closeWebSocketConnection()
    }

    @Test
    fun `invoke close should return result from repository`() = runBlocking {
        // Given
        val expectedResult = Unit

        // Mock the repository to return the expected result
        `when`(webSocketRepository.closeWebSocketConnection()).thenReturn(expectedResult)

        // When
        val result = closeWebSocketConnection()

        // Then
        // Verify that the result from the use case is the same as the expected result from the repository
        assertEquals(expectedResult, result)
    }
}
