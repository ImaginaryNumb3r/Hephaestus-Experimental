package parsing.model;

/**
 * @author Patrick Plieschnegger
 */
public interface CopyNode<T extends ParseNode> extends ParseNode {

    T deepCopy();

    void setData(T other);

    void reset();

}
