import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotEquals

class TestGiveEndStatusBehavior {
    private val s_endStatus = CreateBehavior(mutableListOf("created", "s1000g", "10002928849"))
    private val s2_endStatus = CreateBehavior(mutableListOf("created", "tandllesll", "10002928849"))

    @Test
    fun testBasicGiveEndStatusBehavior() {
        s_endStatus.performAction()
        s2_endStatus.performAction()

        val shipment = TrackingSimulator.findShipment("s1000g")
        val lost = GiveEndStatusBehavior(mutableListOf("lost", "s1000g", "10002928849"))
        val canceled = GiveEndStatusBehavior(mutableListOf("canceled", "s1000g", "10002928849"))
        val delivered = GiveEndStatusBehavior(mutableListOf("delivered", "s1000g", "10002928849"))

        lost.performAction()
        assertNotEquals(null, shipment)
        assertEquals("s1000g", shipment?.id)
        assertEquals("lost", shipment?.status)
        assertEquals(null, shipment?.currentLocation)
        assertEquals(1, shipment?.updateHistory?.size)

        canceled.performAction()
        assertNotEquals(null, shipment)
        assertEquals("s1000g", shipment?.id)
        assertEquals("canceled", shipment?.status)
        assertEquals(null, shipment?.currentLocation)
        assertEquals(2, shipment?.updateHistory?.size)


        delivered.performAction()
        assertNotEquals(null, shipment)
        assertEquals("s1000g", shipment?.id)
        assertEquals("delivered", shipment?.status)
        assertEquals(null, shipment?.currentLocation)
        assertEquals(3, shipment?.updateHistory?.size)
    }

    @Test
    fun testWeirdIDGiveEndStatusBehavior() {
        s2_endStatus.performAction()

        val lost = GiveEndStatusBehavior(mutableListOf("lost", "tandllesll", "10002928849"))
        lost.performAction()
        val shipment = TrackingSimulator.findShipment("tandllesll")
        assertNotEquals(null, shipment)
        assertEquals("tandllesll", shipment?.id)
        assertEquals("lost", shipment?.status)
        assertEquals(null, shipment?.currentLocation)
        assertEquals(1, shipment?.updateHistory?.size)
    }

    @Test
    fun testTooFewParametersIDGiveEndStatusBehavior() {
        val block : () -> Unit = { GiveEndStatusBehavior(mutableListOf("lost", "tandllesll")) }
        assertFailsWith<IllegalArgumentException> { block() }
    }

    @Test
    fun testTooManyParametersIDGiveEndStatusBehavior() {
        val block : () -> Unit = { GiveEndStatusBehavior(mutableListOf("lost", "tandllesll", "10002928849", "Los Angeles, CA")) }
        assertFailsWith<IllegalArgumentException> { block() }
    }

    @Test
    fun testGiveEndStatusBehaviorBadID() {
        val badBehavior = GiveEndStatusBehavior(mutableListOf("lost", "s10jjhbhn", "10002928849"))
        val block : () -> Unit = { badBehavior.performAction() }
        assertFailsWith<IllegalArgumentException> { block() }
    }
}