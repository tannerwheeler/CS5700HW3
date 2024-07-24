class GiveBadEndStatusBehavior(
    data: MutableList<String>
) : SimulatorActionBehavior(data) {
    override var dataLength: Int = 3

    init {
        require(data.size == dataLength) {
            "GiveEndStatusBehavior data parameter must be of size 3"
        }
    }

    override fun performAction() {
        val shipment = findShipment()
        require(shipment != null) { "Shipment not found during GiveEndStatusBehavior" }
        val note = ShipmentDeliveryTimeChecker(shipment, shipment.type, shipment.expectedDeliveryDateTimestamp,
            shipment.expectedDeliveryDateTimestamp!!.plus(604800000)).checkTimes()

        if (data[0] == "lost") {
            val update = ShippingUpdate(shipment.status, data[0], shipment.expectedDeliveryDateTimestamp!!.plus(604800000))
            shipment.addUpdate(update)
            note?.performAction()
        }
        else {
            val update = ShippingUpdate(shipment.status, data[0], 9223372036854775807)
            shipment.addUpdate(update)
        }
    }
}