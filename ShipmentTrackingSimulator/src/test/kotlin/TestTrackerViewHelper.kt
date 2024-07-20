import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class TestTrackerViewHelper {
    @Test
    fun testCreateTrackViewHelper() {
        val trackerViewHelper = TrackerViewHelper("testHelper")
        assertEquals(null, trackerViewHelper.shipment)
        assertEquals("testHelper", trackerViewHelper.shipmentId)
        assertEquals(0, trackerViewHelper.shipmentNotes.size)
        assertEquals(0, trackerViewHelper.shipmentUpdateHistory.size)
        assertEquals(null, trackerViewHelper.shipmentStatus)
        assertEquals(null, trackerViewHelper.shipmentLocation)
    }

    @Test
    fun testTrackShipment() {
        val trackerViewHelper = TrackerViewHelper("helperID")
        val shipment = Shipment("observer", "helperID", null, null)
        TrackingSimulator.addShipment(shipment)
        trackerViewHelper.trackShipment("helperID")
        assertEquals(shipment, trackerViewHelper.shipment)
        assertEquals("helperID", trackerViewHelper.shipmentId)
        assertEquals(0, trackerViewHelper.shipmentNotes.size)
        assertEquals(0, trackerViewHelper.shipmentUpdateHistory.size)
        assertEquals("observer", trackerViewHelper.shipmentStatus)
        assertEquals(null, trackerViewHelper.shipmentLocation)
        shipment.notifyObservers(null, null)
        assertEquals("observer", trackerViewHelper.shipmentStatus)

        shipment.addNote("I am a note")
        assertEquals(1, trackerViewHelper.shipmentNotes.size)

        shipment.addUpdate(ShippingUpdate("observer", "newObserver", "10002222022".toLong()))
        assertEquals(1, trackerViewHelper.shipmentUpdateHistory.size)
        assertEquals("newObserver", trackerViewHelper.shipmentStatus)

        shipment.expectedDeliveryDateTimestamp = "1022255332255".toLong()
        shipment.currentLocation = "Los Angeles CA"
        shipment.notifyObservers(null, null)
        assertEquals("Los Angeles CA", trackerViewHelper.shipmentLocation)
        assertEquals("Fri May 24 09:48:52 MDT 2002", trackerViewHelper.expectedShipmentDeliveryDate)
    }

    @Test
    fun testStopTracking() {
        val trackerViewHelper = TrackerViewHelper("helperID2")
        val shipment = Shipment("observer", "helperID2", null, null)
        TrackingSimulator.addShipment(shipment)
        trackerViewHelper.trackShipment("helperID2")
        assertEquals(shipment, trackerViewHelper.shipment)
        assertEquals("helperID2", trackerViewHelper.shipmentId)
        assertEquals(0, trackerViewHelper.shipmentNotes.size)
        assertEquals(0, trackerViewHelper.shipmentUpdateHistory.size)
        assertEquals("observer", trackerViewHelper.shipmentStatus)
        assertEquals(null, trackerViewHelper.shipmentLocation)
        shipment.notifyObservers(null, null)
        assertEquals("observer", trackerViewHelper.shipmentStatus)

        trackerViewHelper.stopTracking()
        shipment.addNote("I am a note")
        assertEquals(0, trackerViewHelper.shipmentNotes.size)

        shipment.addUpdate(ShippingUpdate("observer", "newObserver", "10002222022".toLong()))
        assertEquals(0, trackerViewHelper.shipmentUpdateHistory.size)
        assertEquals("observer", trackerViewHelper.shipmentStatus)

        shipment.expectedDeliveryDateTimestamp = "1022255332255".toLong()
        shipment.currentLocation = "Los Angeles CA"
        shipment.notifyObservers(null, null)
        assertEquals(null, trackerViewHelper.shipmentLocation)
        assertEquals(null, trackerViewHelper.expectedShipmentDeliveryDate)
    }
}