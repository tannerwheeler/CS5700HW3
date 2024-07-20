class UpdateDeliveryTimeBehavior(
    data: MutableList<String>
) : SimulatorActionBehavior(data) {
    override var dataLength: Int = 4

    init {
        require(data.size == dataLength) {
            "UpdateDeliveryTimeBehavior data parameter must be of size 4"
        }
    }

    override fun performAction() {
        val shipment = findShipment()
        require(shipment != null) { "Shipment not found in UpdateDeliveryTimeBehavior" }

        val update = ShippingUpdate(shipment.status, data[0], data[2].toLong())
        shipment.expectedDeliveryDateTimestamp = data[3].toLong()
        shipment.addUpdate(update)
    }
}