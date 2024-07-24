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

        val note = ShipmentDeliveryTimeChecker(shipment, shipment.type, shipment.expectedDeliveryDateTimestamp, data[3].toLong()).checkTimes()

        if (data[3].toLong() > shipment.expectedDeliveryDateTimestamp!!
            && shipment.type != "StandardShipment"
            && data[0] != "delayed")
        {
            UpdateDeliveryTimeBehavior(mutableListOf("delayed",data[1],data[2],data[3])).performAction()
            return
        }

        shipment.expectedDeliveryDateTimestamp = data[3].toLong()

        val update = ShippingUpdate(shipment.status, data[0], data[2].toLong())
        shipment.addUpdate(update)

        note?.performAction()
    }
}