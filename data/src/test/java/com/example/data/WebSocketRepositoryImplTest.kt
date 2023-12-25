import com.example.data.api.client.WebSocketClient
import com.example.data.api.repoImpl.WebSocketRepositoryImpl
import com.example.data.db.WebSocketMessageDao
import com.example.domain.model.WebSocketMessageEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.mockk
import junit.framework.Assert
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class WebSocketRepositoryImplTest {

    @Mock
    private lateinit var webSocketClient: WebSocketClient

    @Mock
    private lateinit var webSocketMessageDao: WebSocketMessageDao

    //InjectMockKs is used to inject mocks into the class under test
    @InjectMockKs
    private lateinit var webSocketRepository: WebSocketRepositoryImpl

    //TestCoroutineDispatcher is used to run coroutines in tests
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        webSocketClient = mockk()
        webSocketMessageDao = mockk()
        webSocketRepository = WebSocketRepositoryImpl(webSocketClient, webSocketMessageDao)
    }

    @Test
    fun `sendMessage should call WebSocketClient's send`() {
        testDispatcher.runBlockingTest {
            //coEvery is used to mock suspend functions
            coEvery { webSocketClient.send(message = "message") } returns Unit
            webSocketRepository.sendMessage("message")

            coVerify { webSocketClient.send(message = "message")  }
    }}

    @Test
    fun `receiveMessages should return Flow from WebSocketClient`() =
        testDispatcher.runBlockingTest {
            // Arrange
            val testFlow: Flow<String> = flow { emit("TestFlow") }
            //coEvery is used to mock suspend functions
            coEvery { webSocketClient.receiveMessages() } returns testFlow

            // Act
            val resultFlow = webSocketRepository.receiveMessages()

            // Assert
            resultFlow.collect { result ->
                assert(result == "TestFlow")
            }
        }


    @Test
    fun `getCachedMessages should return Flow from WebSocketMessageDao`() {
        // Arrange
        val mockFlow = flowOf(emptyList<WebSocketMessageEntity>())
        coEvery { webSocketMessageDao.getAllMessages() } returns mockFlow

        // Act
        val result = webSocketRepository.getCachedMessages()

        // Assert
        assert(result == mockFlow)

    }

    @Test
    fun `cacheMessage should call WebSocketMessageDao's insertMessage`() =
    testDispatcher.runBlockingTest {
        // Arrange
        val mockEntity = WebSocketMessageEntity(content = "some content" , type = 1)
        coEvery { webSocketMessageDao.insertMessage(mockEntity) } returns Unit

        // Act
        webSocketRepository.cacheMessage(mockEntity)

        //coVerify is used to verify suspend functions
        // Assert
        coVerify { webSocketMessageDao.insertMessage(mockEntity) }
    }
}