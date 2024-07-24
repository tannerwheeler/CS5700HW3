import java.util.*

class ShipmentDeliveryTimeChecker(
    val shipment: Shipment,
    val shipmentType: String,
    val originalTime: Long?,
    val newTime: Long
) {
    fun checkTimes() : SimulatorActionBehavior? {
        require(originalTime != null)
        val timeDifference = Date(originalTime).compareTo(Date(newTime))

        when(this.shipmentType) {
            "ExpressShipment" -> {
                if(timeDifference < 0) {
                    return NoteAddedBehavior(mutableListOf("noteadded",this.shipment.id,"0","This express shipment was updated to" +
                            " include a delivery date 3 or more days after it was created."))
                }
            }
            "OvernightShipment" -> {
                if(timeDifference < 0) {
                    return NoteAddedBehavior(mutableListOf("noteadded",this.shipment.id,"0","This overnight shipment was updated to" +
                            " include a delivery date later than 24 hours after it was created."))
                }
            }
            "BulkShipment" -> {
                if(timeDifference > 0) {
                    return NoteAddedBehavior(mutableListOf("noteadded",this.shipment.id,"0","This bulk shipment will arrive before the estimated 3 day minimum."))
                }
            }
            else -> return null
        }
        return null
    }
}


//An express shipment cannot have an expected delivery date of more than 3 days after the shipment was created
//An overnight shipment must have an expected delivery date of the day after it was created.
//A BulkShipment should not have an expected delivery date sooner than 3 days after it was created.
//StandardShipment has no special conditions

//In the event that an update occurs that validates one of these rules then you should still apply the update
// but change the state of the shipment to indicate that something abnormal has happened. If a user is tracking one of
// these shipments then the UI should indicate to the user that the shipment had something unexpected happen and indicate
// what happened to the user (for example you might display some text saying: "An overnight shipment was updated to
// include a delivery date later than 24 hours after it was created.") They should also behave appropriately with the
// existing update types. (For example, if an overnight shipment is delayed, then it is expected that the delivery
// date will be after 24 hours so there is no reason to display a message to the user about that. Simply changing
// the status to delayed is fine)
