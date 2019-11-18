package clc.util;

import static clc.util.MainFaction.*;

/**
 * @author Patrick Plieschnegger
 */
public enum SubFaction {
    RRF(GDI), SteelTalons(GDI), ZOCOM(GDI),
    Renegades(Nod), MarkedOfKane(Nod), BlackHand(Nod),
    Messenger_8(Scrin), Reaper_17(Scrin), Traveler_59(Scrin);

    private final MainFaction _primary;

    SubFaction(MainFaction primary) { _primary = primary; }

    public boolean isGDI() { return _primary == GDI; }
    public boolean isNod() { return _primary == Nod; }
    public boolean isScrin() { return _primary == Scrin; }

}
