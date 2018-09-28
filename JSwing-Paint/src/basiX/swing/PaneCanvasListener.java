
package basiX.swing;

import java.util.*;
/**
 * CanvasLauscher 
 * Anmeldung/Abmeldung über setzeCanvasLauscher/entferneCanvasLauscher
 * bei Canvas-Objekten
 */
public interface PaneCanvasListener extends EventListener {
    /**
     * Antwort auf canvasGezeichnet-Ereignis für CanvasLauscher.
     * Das canvasGezeichnet-Ereignis wird ausgelöst, wenn der Canvas durch
     * Größenänderung, in den Vorder- oder Hintergrund-Stellen oder bei Änderung der Sichtbarkeit neu dargestellt werden musste
     */
    public void paneCanvasRepainted(PaneCanvas l);
}
