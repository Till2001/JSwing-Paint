/**
 * MausLauscher 
 * Anmeldung/Abmeldung über setzeMausLauscher/entferneMausLauscher
 * bei Fenster-Objekten und
 * bei Leinwand-Objekten
 *//*
 * Created on 01.10.2005
 
 */
package basiX;

import java.util.EventListener;
/**
 * MausLauscher 
 * Anmeldung/Abmeldung über setzeMausLauscher/entferneMausLauscher
 * bei Fenster-Objekten und
 * bei Leinwand-Objekten
 */
public interface MausLauscherStandard extends EventListener  {
	/**
	 * Antwort auf Maus-Ereignisse
	 */
	/**
	 * Bearbeitung des Maus-Ereignisses: linke Maustaste wurde gedrückt. Die
	 * Parameter enthalten die aktuelle Mausposition
	 */
	public void bearbeiteMausDruck(Object o, int x, int y);

	/**
	 * Bearbeitung des Maus-Ereignisses: rechte Maustaste wurde gedrückt. Die
	 * Parameter enthalten die aktuelle Mausposition
	 */
	public void bearbeiteMausDruckRechts(Object o, int x, int y);

	/**
	 * Bearbeitung des Maus-Ereignisses: linke Maustaste wurde los gelassen. Die
	 * Parameter enthalten die aktuelle Mausposition
	 */
	public void bearbeiteMausLos(Object o, int x, int y);

	/**
	 * Bearbeitung des Maus-Ereignisses: rechte Maustaste wurde los gelassen.
	 * Die Parameter enthalten die aktuelle Mausposition
	 */
	public void bearbeiteMausLosRechts(Object o, int x, int y);

	/**
	 * Bearbeitung des Maus-Ereignisses: Maus wurde bewegt. Die Parameter
	 * enthalten die aktuelle Mausposition
	 */
	public void bearbeiteMausBewegt(Object o, int x, int y);

	/**
	 * Bearbeitung des Maus-Ereignisses: Maus wurde bei gedrückter linker
	 * Maustaste bewegt. Die Parameter enthalten die aktuelle Mausposition
	 */
	public void bearbeiteMausGezogen(Object o, int x, int y);

	
}