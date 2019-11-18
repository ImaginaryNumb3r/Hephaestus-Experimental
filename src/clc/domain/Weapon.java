package clc.domain;

import clc.infrastructure.Mapper;
import lib.io.Range;
import org.jetbrains.annotations.NotNull;
import parsing.xml.AttributeToken;
import parsing.xml.XMLTag;

import java.util.function.Function;

import static java.lang.Double.parseDouble;
import static lib.io.Range.range;

/**
 * @author Patrick Plieschnegger
 * TODO: What if
 */
public class Weapon {
    private final Range<Double> _preAttackDelay;
    private final Range<Double> _clipReloadTimeMax;
    private final Range<Double> _firingDurationMax;
    private final double _clipSize;
    private final double _range;
    private final Warhead _warhead;

    public Weapon(Range<Double> preAttackDelay, Range<Double> clipReloadTimeMax, Range<Double> firingDurationMax, double clipSize, double range, Warhead warhead) {
        _preAttackDelay = preAttackDelay;
        _clipReloadTimeMax = clipReloadTimeMax;
        _firingDurationMax = firingDurationMax;
        _clipSize = clipSize;
        _range = range;
        _warhead = warhead;
    }

    public Weapon(double preAttackDelayMin, double preAttackDelayMax, double clipSize,
                  double clipReloadTimeMin, double clipReloadTimeMax,
                  double firingDurationMin, double firingDurationMax,
                  double range, @NotNull Warhead warhead
    ) {
        this(range(preAttackDelayMin, preAttackDelayMax),
             range(clipReloadTimeMin, clipReloadTimeMax),
             range(firingDurationMin, firingDurationMax),
             clipSize, range, warhead
        );
    }

    public double resolveDamage(Targetable target) {
        return _warhead.resolveDamage(target);
    }

    public Weapon parse(XMLTag weaponTemplate) {
        Function<String, Range<Double>> toRange = tagName -> weaponTemplate
            .getTag(tagName)
            .map(Mapper::parseRange)
            .orElse(range(0.0, 0.0));

        Function<String, Double> toDouble = attributeName -> parseDouble(weaponTemplate
            .getAttribute(attributeName)
            .getValue()
        );

        return new Weapon(
            toRange.apply("PreAttackDelay"),
            toRange.apply("FiringDuration"),
            toRange.apply("ClipReloadTime"),
            toDouble.apply("ClipSize"),
            toDouble.apply("range"),
            Warhead.ofWeapon(weaponTemplate)
        );
    }

}
