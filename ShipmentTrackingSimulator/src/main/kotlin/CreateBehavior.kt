class CreateBehavior(
    data: MutableList<String>
) : SimulatorActionBehavior(data) {
    override var dataLength: Int = 4

    init {
        require(data.size == dataLength) {
            "CreateBehavior data parameter must be of size 3"
        }
    }

    override fun performAction() {
        require(TrackingSimulator.findShipment(data[1]) == null) { "This shipment already exists" }
        TrackingSimulator.addShipment(
            Shipment(data[0],
                data[1],
                CreateTypeFactory(data[2]).createType(),
                null,
                null
            )
        )
    }
}