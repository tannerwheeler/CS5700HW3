class NoteAddedBehavior(
    data: MutableList<String>
) : SimulatorActionBehavior(data) {
    override var dataLength: Int = 4

    init {
        require(data.size == dataLength) {
            "NoteAddedBehavior data parameter must be of size 4"
        }
    }

    override fun performAction() {
        val shipment = findShipment()
        require(shipment != null) { "Could not find Shipment during NoteAddedBehavior" }
        shipment.addNote(data[3])
    }
}