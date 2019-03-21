package parsing.model;

/**
 * Creator: Patrick
 * Created: 21.03.2019
 * This Node only checks the current status of the node.
 * It does not have state, which is why it can overwrite toString and deepCopy.
 */
@FunctionalInterface
public interface ConstraintNode extends ParseNode {

    @Override
    int parse(String chars, int index);

    @Override
    default String asString() {
        return "";
    }

    @Override
    default ParseNode deepCopy() {
        return this;
    }
}
