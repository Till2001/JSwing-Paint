package basiX;

import java.util.*;
/**
 * WahlBoxLauscher 
 * Anmeldung/Abmeldung über setzeWahlBoxLauscher/entferneWahlBoxLauscher
 * bei WahlBox-Objekten
 */
public interface WahlBoxLauscher extends EventListener {
    /**
     * Antwort auf WahlBoxAenderung-Ereignis für WahlBoxLauscher.
     * Das WahlBoxAenderung-Ereignis wird ausgelöst, wenn der Zustand der WahlBox geändert wurde.
     */
    public void bearbeiteWahlBoxAenderung(WahlBox k);
}
