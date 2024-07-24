class CreateTypeFactory(
    var type: String
) {
    fun createType() : String {
        when(type) {
            "express" -> return "ExpressShipment"
            "overnight" -> return "OvernightShipment"
            "bulk" -> return "BulkShipment"
            else -> return "StandardShipment"
        }
    }
}