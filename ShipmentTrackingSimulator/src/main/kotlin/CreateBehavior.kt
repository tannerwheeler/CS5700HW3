import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

class CreateBehavior(
    data: MutableList<String>
) : SimulatorActionBehavior(data) {
    override var dataLength: Int = 4

    init {
        require(data.size == dataLength) {
            "CreateBehavior data parameter must be of size 3"
        }
    }

    override fun performAction() {
        require(TrackingSimulator.findShipment(data[1]) == null) { "This shipment already exists" }
        val newShipment = Shipment(data[0],
            data[1],
            CreateTypeFactory(data[2]).createType(),
            data[3].toLong(),
            null
        )

        TrackingSimulator.addShipment(
            newShipment
        )

        when(newShipment.type) {
            "express" -> CreateUpdateTimeBehavior(mutableListOf(data[0],data[1],data[2],(data[2].toLong() + 172800000).toString())).performAction()
            "overnight" -> CreateUpdateTimeBehavior(mutableListOf(data[0],data[1],data[2],(data[2].toLong() +86400000).toString())).performAction()
            "bulk" -> CreateUpdateTimeBehavior(mutableListOf(data[0],data[1],data[2],(data[2].toLong() +345600000).toString())).performAction()
            else -> CreateUpdateTimeBehavior(mutableListOf(data[0],data[1],data[2],(data[2].toLong() + 259200000).toString())).performAction()
        }
    }
}