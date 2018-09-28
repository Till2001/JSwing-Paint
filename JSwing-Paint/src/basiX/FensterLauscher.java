

package basiX;
import java.util.*;
/**
 * FensterLauscher 
 * Anmeldung/Abmeldung über setzeFensterLauscher/entferneFensterLauscher
 * bei Fenster-Objekten
 */
public interface FensterLauscher extends EventListener{
    public void bearbeiteFensterWurdeGeschlossen(Fenster b);
} 