class ActionFactory(
    private val data: MutableList<String>
) {
    fun callAction() {
        when(data[0]) {
            "created" -> CreateBehavior(data).performAction()
            "shipped" -> UpdateDeliveryTimeBehavior(data).performAction()
            "location" -> LocationBehavior(data).performAction()
            "delayed" -> UpdateDeliveryTimeBehavior(data).performAction()
            "noteadded" -> NoteAddedBehavior(data).performAction()
            "lost" -> GiveBadEndStatusBehavior(data).performAction()
            "canceled" -> GiveBadEndStatusBehavior(data).performAction()
            "delivered" -> DeliveredEndBehavior(data).performAction()
            else -> require(true == false) {"Illegal Input call data for Action Factory"}
        }
    }
}