package basiX;

import java.util.*;
/**
 * RollbalkenLauscher 
 * Anmeldung/Abmeldung über setzeRollbalkenLauscher/entferneRollbalkenLauscher
 * bei Rollbalken-Objekten
 */
public interface RollbalkenLauscher extends EventListener {
    /**
     * Antwort auf RollbalkenBewegung-Ereignis für Rollbalkenlauscher.
     * Das RollbalkenBewegung-Ereignis wird ausgelöst, wenn der eingestellte Rollbalkenwert mit Hilfe der Maus verändert wurde
     */
    public void bearbeiteRollbalkenBewegung(Rollbalken r);
}
