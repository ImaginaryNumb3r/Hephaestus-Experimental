package lib;

import graphs.node.GenericNode;

/**
 * Creator: Patrick
 * Created: 05.04.2019
 * Purpose:
 */
public class Branch<S, T extends Iterable<S>> extends GenericNode<T> {

    public Branch(T value ) {
        super(value);
    }
}
