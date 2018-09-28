package basiX.vw;

import basiX.*;



//
//
// DasFenster
//
//
public class DasFenster {
    /**
     *        <b> hauptFenster</b> ist jeweils das letzte Leinwandobjekt, das von einer Anwendung erzeugt wird.
     * Darauf greifen Stift, Maus etc. zu, wenn sie mit den jeweiligen parameterfreien Konstruktoren erzeugt werden.
     *        @see Maus#Maus()
     *        @see Stift#Stift()
     */
   

    public static Leinwand hauptLeinwand() {
        if (hauptFenster == null) {
           return null;
        }
        return hauptFenster.leinwand();
    }

    /**
     * <b> hauptFenster</b> ist jeweils das letzte Fensterobjekt, das von einer Anwendung erzeugt wird.
     */
    private static Fenster hauptFenster;
    /** registriert das Fenster,das das eigentliche Anwendungsfenster ist. Nur wenn dieses geschlossen wird,
    wird die Anwendung beendet */
    private static Fenster anwendungsfenster = null;

    public static Fenster hauptFenster() {
        return hauptFenster;
    }
    public static void loescheAnwendungsfenster(){
        anwendungsfenster = null;
    }
    public static Fenster anwendungsfenster(){
      return anwendungsfenster;
    }
    public static void setzeHauptFenster(Fenster l) {
        hauptFenster = l;
        if (anwendungsfenster == null) {
           anwendungsfenster = l;
        }
    }
}
