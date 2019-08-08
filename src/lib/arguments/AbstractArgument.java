package lib.arguments;

import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class implemtns the bulk of the Argument
 */
public class AbstractArgument<T> implements Argument<T> {
    private final Set<String> _names = new HashSet<>();
    private String _description;
    private T _value;

    @Override
    public Set<String> names() {
        return _names;
    }

    @Override
    public void setValue(@NotNull T value) {
        _value = value;
    }

    @Override
    public T getValue() {
        return _value;
    }

    @Override
    public String getDescription() {
        return _description;
    }

    @Override
    public void setDescription(String description) {
        _description = description;
    }

    protected void consume(List<String> tokens) {
        /*
         * Listconsumer: peek value, next value, revert changes, persist changes.
         */
    }
}
