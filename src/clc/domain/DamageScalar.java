package clc.domain;

import java.util.function.Predicate;

/**
 * @author Patrick Plieschnegger
 */
public class DamageScalar implements Predicate<Targetable> {
    private final Predicate<Targetable> _targetPredicate;
    private final double _scalar;

    public DamageScalar(Predicate<Targetable> targetPredicate, double scalar) {
        _targetPredicate = targetPredicate;
        _scalar = scalar;
    }

    @Override
    public boolean test(Targetable unit) {
        return _targetPredicate.test(unit);
    }

    public double getForType(Targetable target) {
        return test(target) ? _scalar : 1.0;
    }
}
