package clc.domain;

import clc.util.KindOf;

import java.util.Set;

/**
 * @author Patrick Plieschnegger
 */
public interface Targetable {

    Set<KindOf> getKindOf();

    int getHealth();

    Armor getArmor();

    default double resolveAttack(Warhead warhead) {
        return getArmor().resolveAttack(warhead);
    }
}
