package basiX;

import java.util.*;
/**
 * TextBereichLauscher 
 * Anmeldung/Abmeldung über setzeTextBereichLauscher/entferneTextBereichLauscher
 * bei TextBereich-Objekten
 */
public interface TextBereichLauscher extends EventListener {
    /**
     * Antwort auf TextVeraenderung-Ereignis für TextBereichLauscher.
     * Das TextVeraenderung-Ereignis wird ausgelöst, wenn der Text im Fenster durch den Benutzer verändert wurde
     */
    public void bearbeiteTextBereichVeraenderung(Komponente t);
}
