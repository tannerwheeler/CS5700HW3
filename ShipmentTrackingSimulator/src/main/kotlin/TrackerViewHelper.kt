import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import java.util.*

class TrackerViewHelper(
    id: String
) : ShipmentObserver() {
    var shipment: Shipment? = null
        private set
    var shipmentId by mutableStateOf("")
        private set
    var shipmentNotes by mutableStateOf(mutableListOf<String>())
        private set
    var shipmentUpdateHistory by mutableStateOf(mutableListOf<String>())
        private set
    var expectedShipmentDeliveryDate by mutableStateOf<String?>(null)
        private set
    var shipmentStatus by mutableStateOf<String?>(null)
        private set
    var shipmentLocation by mutableStateOf<String?>(null)
        private set

    init {
        this.shipmentId = id
        trackShipment(this.shipmentId)
    }

    override fun notify(note: String?,
                        history: String?,
                        expectedShipmentDeliveryDate: Long?,
                        status: String?,
                        location: String?) {
        if (note != null) {
            this.shipmentNotes.add(note)
        }
        if (history != null) {
            this.shipmentUpdateHistory.add(history)
        }
        if (expectedShipmentDeliveryDate != null) {
            this.expectedShipmentDeliveryDate = Date(expectedShipmentDeliveryDate).toString()
        }
        if (status != null) {
            this.shipmentStatus = status
        }
        if (location != null) {
            this.shipmentLocation = location
        }
    }

    fun trackShipment(id: String) {
        this.shipment = TrackingSimulator.findShipment(this.shipmentId)
        shipment?.subscribe(this)
    }

    fun stopTracking() {
        shipment?.unsubscribe(this)
    }
}