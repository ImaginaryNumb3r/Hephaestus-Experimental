package parsing.model;

/**
 * @author Patrick Plieschnegger
 */
public interface CopyNode<T extends ParseNode> extends ParseNode {

    /**
     * Implementing this interface statically guarantees that its deep copy is its own dynamic type (and not just ParseNode)
     * @return Value-equal instance to the source instance.
     */
    T deepCopy();

    void setData(T other);

    void reset();

}
