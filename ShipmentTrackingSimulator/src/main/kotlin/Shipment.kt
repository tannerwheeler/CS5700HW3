import java.util.*

class Shipment(
    var status: String,
    var id: String,
    var expectedDeliveryDateTimestamp: Long?,
    var currentLocation: String?
): ShipmentSubject() {
    var notes = mutableListOf<String>()
    var updateHistory = mutableListOf<ShippingUpdate>()

    fun addNote(note: String) {
        this.notes.add(note)
        this.notifyObservers(note,null)
    }

    fun addUpdate(update: ShippingUpdate) {
        updateHistory.add(update)
        this.status = update.newStatus
        this.notifyObservers(null,
            "Shipment went from ${update.previousStatus} " +
                    "to ${this.status} " +
                    "on ${Date(update.timestamp).toString()}")
    }

    override fun notifyObservers(note: String?, history: String?) {
        observers.forEach {
            it.notify(note,
                history,
                this.expectedDeliveryDateTimestamp,
                this.status,
                this.currentLocation)
        }
    }
}