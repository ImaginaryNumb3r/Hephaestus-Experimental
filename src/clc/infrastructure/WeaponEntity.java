package clc.infrastructure;

import clc.domain.Warhead;
import lib.io.Range;

/**
 * @author Patrick Plieschnegger
 */
public interface WeaponEntity {
    Range<Double> preAttackDelay();
    Range<Double> clipSize();
    Range<Double> clipReloadTime();
    Range<Double> firingDuration();
    double range();
    Warhead warhead();

}
