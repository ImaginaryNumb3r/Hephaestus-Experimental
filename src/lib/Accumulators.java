package lib;

/**
 * @author Patrick Plieschnegger
 */
public class Accumulators {

    public static <Acc, Buff> Buff noOp(Acc input, Buff buffer) {
        return buffer;
    }

}
