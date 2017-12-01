package it.gopher.mayo.kotlin

/**
 * An interface that represents a cancelable action.
 * @author Alex Nguyen
 */
public interface Cancellable {
    /**
     * Indicates whether the action is already cancelled.
     */
    var isCancelled: Boolean

    /**
     * Notifies the object to cancel it's current process.
     */
    fun cancel()
}

/**
 * A wrapper class and implementation of Cancellable.
 * @author Chamu Rajasekera
 */
class CancellableWrapper : Cancellable {
    private var innerCancellable = SimpleCancellable()

    /**
     * Indicates whether the action is already cancelled.
     */
    override var isCancelled: Boolean = innerCancellable.isCancelled

    /**
     * Notifies the object to cancel it's current process.
     */
    override fun cancel() {
        innerCancellable.cancel()
    }
}

/**
 * A simple implementation of a basic Cancellable object.
 * @author Chamu Rajasekera
 */
class SimpleCancellable : Cancellable {
    /**
     * Indicates whether the action is already cancelled.
     */
    override var isCancelled: Boolean = false

    /**
     * Notifies the object to cancel it's current process.
     */
    override fun cancel() {
        isCancelled = true;
    }
}