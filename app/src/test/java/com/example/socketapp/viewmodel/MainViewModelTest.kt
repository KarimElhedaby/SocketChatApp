import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.domain.usecase.*
import com.example.socketapp.presentation.vm.MainViewModel
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var closeWebSocketConnection: CloseWebSocketConnection

    @MockK
    lateinit var sendMessageUseCase: SendMessageUseCase

    @MockK
    lateinit var receiveMessagesUseCase: ReceiveMessagesUseCase

    @MockK
    lateinit var cacheMessagesUseCase: CacheMessagesUseCase

    @MockK
    lateinit var receiveCachedMessagesUseCase: ReceiveCachedMessagesUseCase

    private lateinit var viewModel: MainViewModel
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher) // Set the main dispatcher for tests
        MockKAnnotations.init(this)
        viewModel = MainViewModel(
            closeWebSocketConnection,
            sendMessageUseCase,
            receiveMessagesUseCase,
            cacheMessagesUseCase,
            receiveCachedMessagesUseCase
        )
    }

    @Test
    fun `sendMessage should update sentMessageStatus on success`() {
        // Arrange
        /*coEvery is a mockk function that returns a flow
        just Runs is a mockk function that does nothing */
        coEvery { sendMessageUseCase(any()) } just Runs
        coEvery { cacheMessagesUseCase(any()) } just Runs

        // Act
        viewModel.sendMessage("Hello")

        // Assert
        assert(viewModel.sentMessageStatus.value == true)
    }

    @Test
    fun `sendMessage should update errorMessage on failure`() = testDispatcher.runBlockingTest {
        // Arrange
        val errorMessage = "Error sending message"
        coEvery { sendMessageUseCase(any()) } coAnswers { throw Exception(errorMessage) }

        // Act
        viewModel.sendMessage("Hello")

        // Assert
        assert(viewModel.sentMessageStatus.value == false)
        assert(viewModel.errorMessage.value == "Error sending message: $errorMessage")
    }

    @Test
    fun `receiveMessages should update incomingMessages on success`() = testDispatcher.runBlockingTest {
        // Arrange
        val testMessage = "Server Message"
        coEvery { receiveMessagesUseCase() } returns flow { emit(testMessage) }
        coEvery { cacheMessagesUseCase(any()) } just Runs

        // Act
        viewModel.receiveMessages()

        // Assert
        assert(viewModel.incomingMessages.value == testMessage)
    }

}
