class LocationBehavior(
    data: MutableList<String>
) : SimulatorActionBehavior(data) {
    override var dataLength: Int = 4

    init {
        require(data.size == dataLength) {
            "LocationBehavior data parameter must be of size 4"
        }
    }

    override fun performAction() {
        val shipment = findShipment()
        require(shipment != null) { "Shipment not found during Location Behavior" }

        shipment.currentLocation = data[3]
        shipment.notifyObservers(null, null)
    }
}