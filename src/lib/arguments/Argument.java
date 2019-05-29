package lib.arguments;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Creator: Patrick
 * Created: 17.05.2019
 * Purpose:
 */
public class Argument { // TODO: Polymorphism. Split into different argument types via inheritance: Array, Value, None, Optional (value inherits optional).
    private final String _name;
    // private List<String> _value;
    private String _value;
    private final ArgumentOption _option;

    protected Argument(String name, String value, ArgumentOption option) {
        _value = value;
        _option = option;
        _name = name;
    }

    protected void setValue(String value) {
        if (_option == ArgumentOption.NONE)

        _value = value;
    }

    public String getName() {
        return _name;
    }

    public ArgumentOption getOption() {
        return _option;
    }

    public String getValue() {
        return _value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Argument argument = (Argument) o;
        return Objects.equals(_name, argument._name) &&
                _option == argument._option;
    }

    @Override
    public int hashCode() {
        return Objects.hash(_name, _option);
    }
}
