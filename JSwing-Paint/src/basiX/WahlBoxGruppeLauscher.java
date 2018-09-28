/**
 * WahlBoxGruppeLauscher 
 * Anmeldung/Abmeldung über setzeWahlBoxGruppeLauscher/entferneWahlBoxGruppeLauscher
 * bei WahlBoxGruppe-Objekten
 */package basiX;

import java.util.*;
/**
 * WahlBoxGruppeLauscher 
 * Anmeldung/Abmeldung über setzeWahlBoxGruppeLauscher/entferneWahlBoxGruppeLauscher
 * bei WahlBoxGruppe-Objekten
 */
public interface WahlBoxGruppeLauscher extends EventListener {
    /**
     * Antwort auf WahlBoxAuswahl-Ereignis für WahlBoxGruppeLauscher.
     * Das WahlBoxAuswahl-Ereignis wird ausgelöst, wenn der Zustand einer
     * der zur Gruppe k gehörenden WahlBoxen geändert wurde.
     */
    public void bearbeiteWahlBoxAuswahl(Object k);
//    /**
//     * liefert die neuGewaehlteBox
//     * @param k
//     * @param gewaehlt
//     */
//    public void bearbeiteNeuwahl(Object k, WahlBox gewaehlt);
}
