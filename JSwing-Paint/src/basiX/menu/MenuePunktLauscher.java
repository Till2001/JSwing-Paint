package basiX.menu;

import java.util.*;
/**
 * MenuePunktLauscher 
 * Anmeldung/Abmeldung über setzeMenuePunktLauscher/entferneMenuePunktLauscher
 * bei MenuePunkt-Objekten
 */
public interface MenuePunktLauscher extends EventListener {
    /**
     * Antwort auf MenuepunktAuswahl-Ereignis für MenuePunktLauscher.
     * Das MenuepunktAuswahl-Ereignis wird ausgelöst, wenn der Menüpunkt mp gewählt wurde
     */
    public void bearbeiteMenuepunktAuswahl(Menuepunkt mp);
}
