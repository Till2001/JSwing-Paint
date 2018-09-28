/**
 * 
 */
package basiX;

import java.util.EventListener;

/**
 * TastenLauscher 
 * Anmeldung/Abmeldung über setzeTastenLauscher/entferneTastenLauscher
 * bei allen Oberflächen-Objekten 
 * Der Lauscher liefert die Tastendrucke aller Objekte, 
 * die auf dem Fenster platziert sind.
 */

public interface TastenLauscher extends EventListener{
	public void bearbeiteTaste(Komponente sender, char t);

}
