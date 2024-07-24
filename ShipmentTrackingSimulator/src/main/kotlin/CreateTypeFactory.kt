class CreateTypeFactory(
    var type: String
) {
    fun createType() : String {
        when(type) {
            "ExpressShipment" -> return "ExpressShipment"
            "OvernightShipment" -> return "OvernightShipment"
            "BulkShipment" -> return "BulkShipment"
            else -> return "StandardShipment"
        }
    }
}