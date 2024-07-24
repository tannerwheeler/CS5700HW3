class DeliveredEndBehavior(
    data: MutableList<String>
) : SimulatorActionBehavior(data) {
    override var dataLength: Int = 3

    init {
        require(data.size == dataLength) {
            "DeliveredEndBehavior data parameter must be of size 3"
        }
    }

    override fun performAction() {
        val shipment = findShipment()
        require(shipment != null) { "Shipment not found during DeliveredEndBehavior" }

        val update = ShippingUpdate(shipment.status, data[0], data[2].toLong())
        shipment.addUpdate(update)
    }
}