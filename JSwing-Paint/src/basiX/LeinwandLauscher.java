
package basiX;

import java.util.*;
/**
 * LeinwandLauscher 
 * Anmeldung/Abmeldung über setzeLeinwandLauscher/entferneLeinwandLauscher
 * bei Leinwand-Objekten
 */
public interface LeinwandLauscher extends EventListener {
    /**
     * Antwort auf leinwandGezeichnet-Ereignis für LeinwandLauscher.
     * Das leinwandGezeichnet-Ereignis wird ausgelöst, wenn die Leinwand durch
     * Größenänderung, in den Vorder- oder Hintergrund-Stellen oder bei Änderung der Sichtbarkeit neu dargestellt werden musste
     */
    public void leinwandWurdeGezeichnet(LeinwandIF l);
}
