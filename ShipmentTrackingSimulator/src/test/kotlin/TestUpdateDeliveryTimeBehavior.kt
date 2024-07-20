import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class TestUpdateDeliveryTimeBehavior {
    private val s_endStatus = CreateBehavior(mutableListOf("created", "s1000s", "10002928849"))
    private val s2_endStatus = CreateBehavior(mutableListOf("created", "tandllesl", "10002928849"))

    @Test
    fun testBasicUpdateDeliveryTimeBehavior() {
        s_endStatus.performAction()
        s2_endStatus.performAction()

        val shipment = TrackingSimulator.findShipment("s1000s")
        val shipped = UpdateDeliveryTimeBehavior(mutableListOf("shipped", "s1000s", "10002928849", "1000093283883"))
        assertEquals(0, shipment?.updateHistory?.size)

        shipped.performAction()
        assertEquals("1000093283883".toLong(), shipment?.expectedDeliveryDateTimestamp)
        assertEquals("s1000s", shipment?.id)
        assertEquals("shipped", shipment?.status)
        assertEquals(null, shipment?.currentLocation)
        assertEquals(1, shipment?.updateHistory?.size)
    }

    @Test
    fun testWeirdIDUpdateDeliveryTimeBehavior() {
        s2_endStatus.performAction()

        val shipped = UpdateDeliveryTimeBehavior(mutableListOf("shipped", "tandllesl", "10002928849", "1000093283883"))
        shipped.performAction()
        val shipment = TrackingSimulator.findShipment("tandllesl")
        assertEquals("1000093283883".toLong(), shipment?.expectedDeliveryDateTimestamp)
        assertEquals("tandllesl", shipment?.id)
        assertEquals("shipped", shipment?.status)
        assertEquals(null, shipment?.currentLocation)
        assertEquals(1, shipment?.updateHistory?.size)
    }

    @Test
    fun testTooFewParametersIDUpdateDeliveryTimeBehavior() {
        val block : () -> Unit = { UpdateDeliveryTimeBehavior(mutableListOf("shipped", "tandllesl", "10002928849")) }
        assertFailsWith<IllegalArgumentException> { block() }
    }

    @Test
    fun testTooManyParametersIDUpdateDeliveryTimeBehavior() {
        val block : () -> Unit = { UpdateDeliveryTimeBehavior(mutableListOf("shipped", "tandllesl", "10002928849", "1000093283883", "Los Angeles, CA")) }
        assertFailsWith<IllegalArgumentException> { block() }
    }

    @Test
    fun testUpdateDeliveryTimeBehaviorBadID() {
        val badBehavior = UpdateDeliveryTimeBehavior(mutableListOf("shipped", "s10jjhbhn", "10002928849", "10000255623"))
        val block : () -> Unit = { badBehavior.performAction() }
        assertFailsWith<IllegalArgumentException> { block() }
    }
}