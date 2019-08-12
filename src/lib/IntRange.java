package lib;

import java.util.*;
import java.util.function.IntConsumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Creator: Patrick
 * Created: 18.08.2017
 * Purpose:
 */
public final class IntRange {
    public final int start;
    public final int end;
    private final boolean _forward;

    /**
     * Creates a range of ints from a given start and end.
     * The lower given variable will serve as the start and the other will be the end of the range.
     */
    private IntRange(int start, int end) {
        _forward = start < end;
        this.start = start;
        this.end = end;
    }

    /**
     * Creates a range of ints from a given start and end.
     * The lower given variable will serve as the start and the other will be the end of the range.
     */
    public static IntRange range(int startInclusive, int endInclusive) {
        return new IntRange(startInclusive, endInclusive);
    }

    public void forEach(IntConsumer consumer) {
        int start = _forward ? this.start : this.end;
        int end = _forward ? this.end : this.start;
        int pos = _forward ? start : end;

        while (_forward ? pos != end : pos != start) {
            consumer.accept(pos);
            pos = _forward ? ++pos : --end;
        }
    }

    /**
     * Creates a range of ints from a given start and end.
     * The lower given variable will serve as the start and the other will be the end of the range.
     * Performs a size comparison and takes the lower argument as start.
     *
     * @param int1 that does not need to be the lower value
     * @param int2 that does not need to be the greater value
     */
    public static IntRange forward(int int1, int int2) {
        if (int1 > int2){
            // Swap
            int temp = int1;
            int1 = int2;
            int2 = temp;
        }

        return new IntRange(int1, int2);
    }

    /**
     * Creates a range of ints from a given start and end.
     * The lower given variable will serve as the start and the other will be the end of the range.
     * Performs a size comparison and takes the lower argument as start.
     *
     * @param int1 that does not need to be the greater value
     * @param int2 that does not need to be the lower value
     */
    public static IntRange backwards(int int1, int int2) {
        if (int1 < int2){
            // Swap
            int temp = int1;
            int1 = int2;
            int2 = temp;
        }

        return new IntRange(int1, int2);
    }

    public int[] toArray(){
        int[] array = new int[size()];

        int start = _forward ? 0 : size();
        int end   = _forward ? size() : 0;

        for (int i = start; i != end; i = _forward ? ++i : --i){
            array[i] = i + this.start;
        }

        return array;
    }

    public int random() {
        int exclusiveEnd = end + 1;
        return new Random().ints(start, exclusiveEnd).findFirst().getAsInt();
    }

    /**
     * @return the size of amount of ints in the range. Is greater than zero.
     */
    public int size(){
        // +1 because there is always at least one element
        // e.g. the range 10 to 10 is not zero
        return Math.abs(end - start) + 1;
    }

    public IntStream stream(){
        return _forward
            ? IntStream.range(start, end)
            : IntStream.range(end, start);
    }

    /**
     * @return an iterator providing all values from the range exactly once, but at random.
     */
    public ListIterator<Integer> randomIterator(){
        List<Integer> integers = stream()
            .boxed()
            .collect(Collectors.toList());
        Collections.shuffle(integers);

        return integers.listIterator();
    }

    public boolean contains(int value) {
        return _forward
            ? value >= start && value <= end
            : value <= start && value >= end;
    }

    @Override
    public String toString() {
        return start + ".." + end;
    }
}
