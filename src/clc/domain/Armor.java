package clc.domain;

import clc.util.DamageType;

import java.util.EnumMap;
import java.util.Map;

/**
 * @author Patrick Plieschnegger
 */
public class Armor {
    private final Map<DamageType, Double> _armorMap;

    public Armor(double cannon, double rocket, double grenade, double gun, double sniper) {
        _armorMap = new EnumMap<>(DamageType.class);
        _armorMap.put(DamageType.CANNON, cannon);
        _armorMap.put(DamageType.ROCKET, rocket);
        _armorMap.put(DamageType.GRENADE, grenade);
        _armorMap.put(DamageType.GUN, gun);
        _armorMap.put(DamageType.SNIPER, sniper);
    }

    public double getCannon() {
        return _armorMap.get(DamageType.CANNON);
    }

    public double getRocket() {
        return _armorMap.get(DamageType.ROCKET);
    }

    public double getGrenade() {
        return _armorMap.get(DamageType.GRENADE);
    }

    public double getGun() {
        return _armorMap.get(DamageType.GUN);
    }

    public double getSniper() {
        return _armorMap.get(DamageType.SNIPER);
    }

    public double resolveAttack(Warhead warhead) {
        double damage = warhead._damage;
        double scalar = _armorMap.get(warhead._damageType);

        return damage * scalar;
    }
}
