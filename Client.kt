interface Client {
    fun readData(): Event
    fun sendData(dest: Address, payload: Payload): Result
}
