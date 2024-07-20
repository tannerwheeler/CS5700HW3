import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class TestNoteAddedBehavior {
    private val s_note = CreateBehavior(mutableListOf("created", "s1000t", "10002928849"))
    private val s2_note = CreateBehavior(mutableListOf("created", "tandlleslld", "10002928849"))

    @Test
    fun testBasicNoteAddedBehavior() {
        s_note.performAction()
        val shipment = TrackingSimulator.findShipment("s1000t")

        assertEquals(0, shipment?.notes?.size)

        val shipped = UpdateDeliveryTimeBehavior(mutableListOf("shipped", "s1000t", "10002928849", "10002928850"))
        shipped.performAction()
        val noteAdded = NoteAddedBehavior(mutableListOf("noteadded", "s1000t", "10002928849", "Shipped at 10002928850"))
        noteAdded.performAction()
        assertEquals("s1000t", shipment?.id)
        assertEquals("shipped", shipment?.status)
        assertEquals(null, shipment?.currentLocation)
        assertEquals(1, shipment?.updateHistory?.size)
        assertEquals(1, shipment?.notes?.size)

        val noteAdded2 = NoteAddedBehavior(mutableListOf("noteadded", "s1000t", "10002928849", "Shipped Los Angeles, CA"))
        noteAdded2.performAction()
        assertEquals(2, shipment?.notes?.size)
    }

    @Test
    fun testWeirdIDNoteAddedBehavior() {
        s2_note.performAction()
        val shipment = TrackingSimulator.findShipment("tandlleslld")

        val noteAdded = NoteAddedBehavior(mutableListOf("noteadded", "tandlleslld", "10002928849", "Shipped at 10002928850"))
        noteAdded.performAction()
        assertEquals("tandlleslld", shipment?.id)
        assertEquals("created", shipment?.status)
        assertEquals(1, shipment?.notes?.size)
    }

    @Test
    fun testTooFewParametersIDNoteAddedBehavior() {
        val block : () -> Unit = { NoteAddedBehavior(mutableListOf("noteadded", "tandlleslld", "Shipped to Los Angeles, CA")) }
        assertFailsWith<IllegalArgumentException> { block() }
    }

    @Test
    fun testTooManyParametersIDNoteAddedBehavior() {
        val block : () -> Unit = { NoteAddedBehavior(mutableListOf("noteadded", "tandlleslld", "10002928849", "Shipped to Los Angeles, CA", "1002255632556")) }
        assertFailsWith<IllegalArgumentException> { block() }
    }

    @Test
    fun testNoteAddedBehaviorBadID() {
        val block : () -> Unit = { NoteAddedBehavior(mutableListOf("noteadded", "alfdba;lkjkafb", "10002928849", "Shipped to Los Angeles, CA")).performAction() }
        assertFailsWith<IllegalArgumentException> { block() }
    }
}