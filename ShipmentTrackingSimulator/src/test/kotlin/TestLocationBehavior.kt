import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotEquals

class TestLocationBehavior {
    private val s_location = CreateBehavior(mutableListOf("created", "s10001", "10002928849"))
    private val s2_location = CreateBehavior(mutableListOf("created", "tandlleslldd", "10002928849"))

    @Test
    fun testBasicLocationBehavior() {
        s_location.performAction()

        val shipment = TrackingSimulator.findShipment("s10001")
        val location = LocationBehavior(mutableListOf("location", "s10001", "10002928849", "Los Angeles, CA"))

        assertEquals(null, shipment?.currentLocation)

        location.performAction()
        assertEquals("s10001", shipment?.id)
        assertEquals("created", shipment?.status)
        assertEquals("Los Angeles, CA", shipment?.currentLocation)
        assertEquals(0, shipment?.updateHistory?.size)

        val shipped = UpdateDeliveryTimeBehavior(mutableListOf("shipped", "s10001", "10002928849", "10002928850"))
        shipped.performAction()
        assertNotEquals(null, shipment)
        assertEquals("s10001", shipment?.id)
        assertEquals("shipped", shipment?.status)
        assertEquals("Los Angeles, CA", shipment?.currentLocation)
        assertEquals(1, shipment?.updateHistory?.size)
    }

    @Test
    fun testWeirdIDLocationBehavior() {
        s2_location.performAction()

        val location = LocationBehavior(mutableListOf("location", "tandlleslldd", "10002928849", "Los Angeles, CA"))
        location.performAction()
        val shipment = TrackingSimulator.findShipment("tandlleslldd")
        assertNotEquals(null, shipment)
        assertEquals("tandlleslldd", shipment?.id)
        assertEquals("created", shipment?.status)
        assertEquals("Los Angeles, CA", shipment?.currentLocation)
        assertEquals(0, shipment?.updateHistory?.size)
    }

    @Test
    fun testTooFewParametersIDLocationBehavior() {
        val block : () -> Unit = { LocationBehavior(mutableListOf("location", "tandlleslldd", "Los Angeles, CA")) }
        assertFailsWith<IllegalArgumentException> { block() }
    }

    @Test
    fun testTooManyParametersIDLocationBehavior() {
        val block : () -> Unit = { LocationBehavior(mutableListOf("location", "tandlleslldd", "10002928849", "Los Angeles, CA", "1002255632556")) }
        assertFailsWith<IllegalArgumentException> { block() }
    }

    @Test
    fun testLocationBehaviorBadID() {
        val block : () -> Unit = { LocationBehavior(mutableListOf("location", "s10006", "10002928849", "Los Angeles, CA")).performAction() }
        assertFailsWith<IllegalArgumentException> { block() }
    }
}