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
}