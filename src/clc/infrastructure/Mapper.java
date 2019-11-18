package clc.infrastructure;

import lib.io.Range;
import parsing.xml.XMLTag;

import java.util.Optional;
import java.util.function.BiFunction;

import static java.lang.Double.parseDouble;

/**
 * @author Patrick Plieschnegger
 * TODO: Make a free builder?
 *
 * What I want: Specific a data carrier with an interfact and get the implementation for free.
 */
public class Mapper<T> {

    public static Range<Double> parseRange(XMLTag childTag) {
        BiFunction<XMLTag, String, Double> converter = (tag, name) -> {
            String value = tag.getAttribute(name)
                .getValue();

            return parseTime(value);
        };

        double min = converter.apply(childTag, "MinSeconds");
        double max = converter.apply(childTag, "MaxSeconds");

        return Range.of(min, max);
    }

    public static double parseTime(String timeStr) {
        return parseDouble(timeStr.substring(0, timeStr.length() - 2));
    }
}
