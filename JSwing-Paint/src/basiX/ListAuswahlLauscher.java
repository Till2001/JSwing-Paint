 package basiX;

import java.util.*;
/**
 * ListAuswahlLauscher 
 * Anmeldung/Abmeldung über setzeListAuswahlLauscher/entferneListAuswahlLauscher
 * bei ListAuswahl-Objekten
 */
public interface ListAuswahlLauscher extends EventListener {
    /**
     * Antwort auf Auswahl-Ereignis für ListAuswahlLauscher. Das Auswahl-Ereignis wird ausgelöst, wenn in der Liste durch
     * Mausklick oder Return ein Listenelement ausgewählt wurde.
     */
    public void bearbeiteAuswahl(ListAuswahl a);
}
