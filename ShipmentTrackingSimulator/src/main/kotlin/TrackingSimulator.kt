import kotlinx.coroutines.delay
import java.io.File

object TrackingSimulator {
    private var shipments = mutableListOf<Shipment>()

    fun findShipment(id: String?) : Shipment? {
        shipments.forEach {
            if(it.id == id) {
                return it
            }
        }
        return null
    }

    fun addShipment(shipment: Shipment) {
        shipments.add(shipment)
    }

    fun readFile() : MutableList<String> {
        val listOfLines = mutableListOf<String>()

        File("./src/main/resources/test.txt").reader().useLines { lines ->
            lines.forEach {
                listOfLines.add(it)
            }
            return listOfLines
        }
    }

    suspend fun runSimulation() {
        val listOfLines = readFile().toMutableList()

        listOfLines.forEach {
            val shipmentAction = it.split(",")
            ActionFactory(shipmentAction.toMutableList()).callAction()
            delay(1000)
        }
    }
}