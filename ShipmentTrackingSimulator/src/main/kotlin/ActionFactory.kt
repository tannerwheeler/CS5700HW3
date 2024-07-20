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
            "lost" -> GiveEndStatusBehavior(data).performAction()
            "canceled" -> GiveEndStatusBehavior(data).performAction()
            "delivered" -> GiveEndStatusBehavior(data).performAction()
            else -> require(true == false) {"Illegal Input call data for Action Factory"}
        }
    }
}