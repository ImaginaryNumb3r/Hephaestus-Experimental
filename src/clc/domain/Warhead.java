package clc.domain;

import clc.infrastructure.VisionMapper;
import clc.util.DamageType;
import org.jetbrains.annotations.NotNull;
import parsing.xml.Attributes;
import parsing.xml.XMLTag;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static java.lang.Double.parseDouble;

/**
 * @author Patrick Plieschnegger
 */
public class Warhead {
    public final double _damage;
    public final double _damageRadius;
    public final double _damageTaperOff;
    public final DamageType _damageType;
    public final List<DamageScalar> _scalars;

    public Warhead(double damage, double damageRadius, double damageTaperOff,
                   @NotNull DamageType damageType, @NotNull Collection<DamageScalar> scalars
    ) {
        _damage = damage;
        _damageRadius = damageRadius;
        _damageTaperOff = damageTaperOff;
        _damageType = damageType;
        _scalars = new ArrayList<>(scalars);
    }

    public double resolveDamage(Targetable target) {
        double damage = _damage;
        damage *= target.resolveAttack(this);

        for (DamageScalar scalar : _scalars) {
            damage *= scalar.getForType(target);
        }

        return damage;
    }

    public static Warhead ofWeapon(XMLTag weaponTemplate) {
        var damageNugget = weaponTemplate.getTag("Nuggets")
            .flatMap(tag -> tag.getTag("DamageNugget"));

        // TODO: Make fallback to separate weapon template.
        VisionMapper mapper = VisionMapper.instance();
        XMLTag warheadNugget = damageNugget.orElse(
            null // TODO: Here
        );

        double damage = Attributes.getDouble(warheadNugget, "damage");
        double radius = 0;
        double damageTaperOff = 0; /*
        warheadNugget
            .fetchAttribute("Radius")
            .orElse(

            ); */

        return new Warhead(
            damage,
            radius,
            damageTaperOff,
            DamageType.valueOf(warheadNugget.getAttribute("DamageType")),
            Collections.emptyList()
        );
    }
}
