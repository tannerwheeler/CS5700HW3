abstract class ShipmentSubject {
    protected val observers = mutableListOf<ShipmentObserver>()

    fun subscribe(observer: ShipmentObserver) {
        observers.add(observer)
        this.notifyObservers(null, null)
    }

    fun unsubscribe(observer: ShipmentObserver) {
        observers.remove(observer)
    }

    abstract fun notifyObservers(note: String?, history: String?)
}