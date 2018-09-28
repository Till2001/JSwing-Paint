
package basiX;

import java.util.*;
/**
 * KnopfLauscher 
 * Anmeldung/Abmeldung über setzeKnopfLauscher/entferneKnopfLauscher
 * bei Knopf-Objekten
 */
public interface KnopfLauscher extends EventListener {
    /**
     * Antwort auf KnopfDruck-Ereignis für KnopfLauscher.
     * Das KnopfDruck-Ereignis wird durch einen Mausklick auf einen Knopf ausgelöst
     */
    public void bearbeiteKnopfDruck(Knopf k);
}
