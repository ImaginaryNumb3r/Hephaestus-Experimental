package clc.domain;

import clc.util.KindOf;

import java.util.HashSet;
import java.util.Set;

/**
 * A GameObject which represents a unit of the game.
 * Could be:
 *  - Single Tank
 *  - Infantry Squad
 */
public class GameUnit implements Targetable {
    private final int _health;
    private final int _speed;
    private final Set<KindOf> _kindOf;
    private final Armor _armor;
    // TODO: Something like a debuff?

    public GameUnit(int health, int speed, Set<KindOf> kindOf, Armor armor) {
        _health = health;
        _speed = speed;
        _kindOf = new HashSet<>(kindOf);
        _armor = armor;
    }

    public int getSpeed() {
        return _speed;
    }

    @Override
    public Set<KindOf> getKindOf() {
        return _kindOf;
    }

    @Override
    public int getHealth() {
        return _health;
    }

    @Override
    public Armor getArmor() {
        return _armor;
    }
}
