abstract class ShipmentObserver {
    abstract fun notify(note: String?,
                        history: String?,
                        expectedShipmentDeliveryDate: Long?,
                        status: String?,
                        location: String?)
}