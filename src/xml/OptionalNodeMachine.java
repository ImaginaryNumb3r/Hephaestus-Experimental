package xml;

import graphs.abstract_machine.AbstractMachineBuilder;
import graphs.abstract_machine.MachineExecutor;

/**
 * Creator: Patrick
 * Created: 18.03.2019
 * Purpose:
 */
public class OptionalNodeMachine extends AbstractToken {
    private final TokenBuilder _optional;
    private final TokenBuilder _mandatory;
    private final MachineExecutor<Character, TokenBuilder> _executor;

    public OptionalNodeMachine(TokenBuilder optional, TokenBuilder mandatory) {
        _optional = optional;
        _mandatory = mandatory;
        _executor = getExecutor();
    }

    private static MachineExecutor<Character, TokenBuilder> getExecutor() {
        var builder = new AbstractMachineBuilder<State, Character, TokenBuilder>(State.BOTH);
        // builder.addTransition(State.BOTH, );

        return builder.construct();
    }

    @Override
    protected void partialReset() {

    }

    @Override
    public Status accept(char character) {
        assertParsing();

        // _executor.process()

        return null;
    }

    @Override
    public String toString() {
        return null;
    }

    private enum State {
        BOTH, OPTIONAL, MANDATORY, INVALID, DONE
    }

}
