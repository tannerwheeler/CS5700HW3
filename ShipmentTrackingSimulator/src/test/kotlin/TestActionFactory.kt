import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class TestActionFactory {
    @Test
    fun testActionFactoryCreate() {
        val t1 = mutableListOf("created","af","1652712855468")
        val t2 = mutableListOf("shipped","af","1652712855468","1652713940874")
        val t3 = mutableListOf("location","af","1652712855468","Los Angeles CA")
        val t4 = mutableListOf("delayed","af","1652712855468","1652718051403")
        val t5 = mutableListOf("noteadded","af","1652712855468","package was damaged slightly during shipping")
        val t6 = mutableListOf("lost","af","1652712855468")
        val t7 = mutableListOf("canceled","af","1652712855468")
        val t8 = mutableListOf("delivered","af","1652712855468")

        ActionFactory(t1).callAction()
        val shipment = TrackingSimulator.findShipment("af")
        assertNotEquals(null, shipment)
        assertEquals("af", shipment?.id)
        assertEquals("created", shipment?.status)
        assertEquals(null, shipment?.currentLocation)
        assertEquals(0, shipment?.updateHistory?.size)
        assertEquals(0, shipment?.notes?.size)

        ActionFactory(t2).callAction()
        assertEquals("af", shipment?.id)
        assertEquals("shipped", shipment?.status)
        assertEquals(null, shipment?.currentLocation)
        assertEquals(1, shipment?.updateHistory?.size)
        assertEquals(0, shipment?.notes?.size)

        ActionFactory(t3).callAction()
        assertEquals("af", shipment?.id)
        assertEquals("shipped", shipment?.status)
        assertEquals("Los Angeles CA", shipment?.currentLocation)
        assertEquals(1, shipment?.updateHistory?.size)
        assertEquals(0, shipment?.notes?.size)

        ActionFactory(t4).callAction()
        assertEquals("af", shipment?.id)
        assertEquals("delayed", shipment?.status)
        assertEquals("Los Angeles CA", shipment?.currentLocation)
        assertEquals(2, shipment?.updateHistory?.size)
        assertEquals(0, shipment?.notes?.size)

        ActionFactory(t5).callAction()
        assertEquals("af", shipment?.id)
        assertEquals("delayed", shipment?.status)
        assertEquals("Los Angeles CA", shipment?.currentLocation)
        assertEquals(2, shipment?.updateHistory?.size)
        assertEquals(1, shipment?.notes?.size)

        ActionFactory(t6).callAction()
        assertEquals("af", shipment?.id)
        assertEquals("lost", shipment?.status)
        assertEquals("Los Angeles CA", shipment?.currentLocation)
        assertEquals(3, shipment?.updateHistory?.size)
        assertEquals(1, shipment?.notes?.size)

        ActionFactory(t7).callAction()
        assertEquals("af", shipment?.id)
        assertEquals("canceled", shipment?.status)
        assertEquals("Los Angeles CA", shipment?.currentLocation)
        assertEquals(4, shipment?.updateHistory?.size)
        assertEquals(1, shipment?.notes?.size)

        ActionFactory(t8).callAction()
        assertEquals("af", shipment?.id)
        assertEquals("delivered", shipment?.status)
        assertEquals("Los Angeles CA", shipment?.currentLocation)
        assertEquals(5, shipment?.updateHistory?.size)
        assertEquals(1, shipment?.notes?.size)
    }

    @Test
    fun testActionFactoryBadCall() {
        val block : () -> Unit = { ActionFactory(mutableListOf("badCall", "tandllesll", "10002928849", "Los Angeles, CA")).callAction() }
        assertFailsWith<IllegalArgumentException> { block() }
    }
}