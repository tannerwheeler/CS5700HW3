import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class TestTrackingSimulator {
    @Test
    fun testFindShipment() {
        CreateBehavior(mutableListOf("created", "ts1", "1234")).performAction()
        val shipment = TrackingSimulator.findShipment("ts1")
        assertNotEquals(null, shipment)
        assertEquals("ts1", shipment?.id)
    }

    @Test
    fun testAddShipment() {
        val sad = Shipment("testing", "1234", null, null)
        TrackingSimulator.addShipment(sad)
        val shipment = TrackingSimulator.findShipment("ts1")
        assertNotEquals(null, shipment)
        assertEquals("ts1", shipment?.id)
    }

    @Test
    fun testReadFile() {
        val lines = TrackingSimulator.readFile()
        assertEquals(80, lines.size)
    }
}