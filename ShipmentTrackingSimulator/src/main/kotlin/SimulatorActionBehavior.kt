abstract class SimulatorActionBehavior(
    var data: MutableList<String>
) {
    abstract var dataLength: Int
    abstract fun performAction()
    protected fun findShipment() : Shipment? {
        return TrackingSimulator.findShipment(data[1])
    }
}