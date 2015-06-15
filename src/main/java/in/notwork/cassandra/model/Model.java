package in.notwork.cassandra.model;

/**
 * @author rishabh.
 */
public abstract class Model {

    /**
     * Abstract method to ensure (forcefully) the subclass implements them.
     */
    public abstract boolean equals(Object o);

    /**
     * Abstract method to ensure (forcefully) the subclass implements them.
     */
    public abstract int hashCode();

    /**
     * Final method to prevent anyone from writing a custom toString() method.
     * The need for using the toString() method of a {@link Model} should not arise.
     * Make use custom or third-party converters to convert the {@link Model} into a {@link String} object.
     */
    @Override
    public final String toString() {
        return super.toString();
    }
}
