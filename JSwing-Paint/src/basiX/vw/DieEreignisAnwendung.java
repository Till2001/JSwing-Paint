package basiX.vw;

import basiX.EreignisAnwendung;



/**
 * @author  Georg Dick
 * @version 1.0
 */
public class DieEreignisAnwendung {
    private static EreignisAnwendung hauptEreignisAnwendung;

    public static void setzeEreignisAnwendung(EreignisAnwendung t) {
        hauptEreignisAnwendung = t;
    }

    public static EreignisAnwendung hauptEreignisAnwendung() {
        return hauptEreignisAnwendung;
    }
}
