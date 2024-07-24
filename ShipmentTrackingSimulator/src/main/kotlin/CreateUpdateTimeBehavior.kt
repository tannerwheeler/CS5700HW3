class CreateUpdateTimeBehavior(
    data: MutableList<String>
) : SimulatorActionBehavior(data) {
    override var dataLength: Int = 4

    init {
        require(data.size == dataLength) {
            "CreateUpdateTimeBehavior data parameter must be of size 4"
        }
    }

    override fun performAction() {
        val shipment = findShipment()
        require(shipment != null) { "Shipment not found in CreateUpdateTimeBehavior" }
        shipment.expectedDeliveryDateTimestamp = data[3].toLong()
    }
}