import kotlinx.coroutines.*
import java.time.Duration

class DataDistributor(private val client: Client) : Handler {
    override val timeout: Duration = Duration.ofSeconds(1)

    override fun performOperation() = runBlocking {
        val event = client.readData()
        event.recipients.forEach { address ->
            launch {
                var result: Result
                do {
                    result = client.sendData(address, event.payload)
                    if (result == Result.REJECTED) {
                        delay(timeout.toMillis())
                    }
                } while (result != Result.ACCEPTED)
            }
        }
    }
}
