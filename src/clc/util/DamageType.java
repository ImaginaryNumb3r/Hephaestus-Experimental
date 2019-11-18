package clc.util;

import parsing.xml.AttributeToken;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author Patrick Plieschnegger
 */
public enum DamageType {
    CANNON, ROCKET, GUN, SNIPER, GRENADE;

    public static DamageType valueOf(AttributeToken damageType) {
        return valueOf(damageType.getValue());
    }
}
