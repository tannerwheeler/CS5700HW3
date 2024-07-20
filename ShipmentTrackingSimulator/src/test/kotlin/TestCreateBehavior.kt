import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith

class TestCreateBehavior {
    @Test
    fun testBasicCreateBehavior() {
        val create = CreateBehavior(mutableListOf<String>("created", "s10002", "10002928849"))
        create.performAction()
        val shipment = TrackingSimulator.findShipment("s10002")
        assert(shipment != null)
        assert(shipment?.id == "s10002")
        assert(shipment?.status == "created")
        assert(shipment?.currentLocation == null)
    }

    @Test
    fun testWeirdIDCreateBehavior() {
        val create = CreateBehavior(mutableListOf<String>("created", "tandlleslldd", "10002928849"))
        create.performAction()
        val shipment = TrackingSimulator.findShipment("tandlleslldd")
        assert(shipment != null)
        assert(shipment?.id == "tandlleslldd")
    }

    @Test
    fun testTooFewParametersIDCreateBehavior() {
        val block : () -> Unit = { CreateBehavior(mutableListOf<String>("created", "tandlleslldd")) }
        assertFailsWith<IllegalArgumentException> { block() }
    }

    @Test
    fun testTooManyParametersIDCreateBehavior() {
        val block : () -> Unit = { CreateBehavior(mutableListOf<String>("created", "tandlleslldd", "10002928849", "Los Angeles, CA")) }
        assertFailsWith<IllegalArgumentException> { block() }
    }
}