package xml.simple;

/**
 * @author Patrick Plieschnegger
 */
public interface CopyNode<T extends ParseNode> extends ParseNode {

    T deepCopy();

}
