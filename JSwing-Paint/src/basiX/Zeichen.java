package basiX;

// Zeichen.java
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 *Konstante f&uuml;r Sonderzeichen wie Funktionstasten und Cursortasten.
 * Beispiel für die Enter-Taste: Zeichen.ENTER. Die Verwendung ist für
 * Tasaturobjekte gedacht. Diese liefern Zeichenkonstante gemäß der
 * vorliegenden Codierung.
 *@author Georg Dick
 *@version 22.7.98
 */
public class Zeichen {
    public final static char PFEILUNTEN = KeyEvent.VK_DOWN;
    public final static char ENDE = (char)Event.END;
    public final static char ESC = (char)27;
    public final static char ENTER = (char)10;
    public final static char F1 = (char)Event.F1;
    public final static char F2 = (char)Event.F2;
    public final static char F3 = (char)Event.F3;
    public final static char F4 = (char)Event.F4;
    public final static char F5 = (char)Event.F5;
    public final static char F6 = (char)Event.F6;
    public final static char F7 = (char)Event.F7;
    public final static char F8 = (char)Event.F8;
    public final static char F9 = (char)Event.F9;
    public final static char F10 = (char)Event.F10;
    public final static char F11 = (char)Event.F11;
    public final static char F12 = (char)Event.F12;
    public final static char POS1 = (char)Event.HOME;
    public final static char PFEILLINKS = KeyEvent.VK_LEFT;
    public final static char BILDRUNTER = (char)Event.PGDN;
    public final static char BILDRAUF = (char)Event.PGUP;
    public final static char UNDFINIERT = 999;

    /** entspricht BILDRUNTER */
    public final static char BILDUNTEN = (char)Event.PGDN;

    /** entspricht BILDRAUF */
    public final static char BILDAUF = (char)Event.PGUP;
    public final static char PFEILRECHTS = KeyEvent.VK_RIGHT;
    public final static char PFEILOBEN = KeyEvent.VK_UP;
}
