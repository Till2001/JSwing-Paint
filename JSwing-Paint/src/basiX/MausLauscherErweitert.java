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
public interface MausLauscherErweitert extends EventListener  {
	/**
	 * Antwort auf Maus-Ereignisse
	 */
	
	/**
	 * Bearbeitung des Maus-Ereignisses: Klick (Druck und Loslassen) mit der
	 * linken Maustaste. Die Parameter enthalten die aktuelle Mausposition
	 */
	public void bearbeiteMausKlick(Object o, int x, int y);

	

	/**
	 * Bearbeitung des Maus-Ereignisses: Klick (Druck und Loslassen) mit der
	 * rechten Maustaste. Die Parameter enthalten die aktuelle Mausposition
	 */
	public void bearbeiteMausKlickRechts(Object o, int x, int y);

	/**
	 * Bearbeitung des Maus-Ereignisses: Doppelklick ( zweimaliges kurz
	 * aufeinander folgendes Drücken und Loslassen) der linken Maustaste. Die
	 * Parameter enthalten die aktuelle Mausposition
	 */
	public void bearbeiteDoppelKlick(Object o,int x, int y);
	  /** Bearbeitung des Maus-Ereignisses: Maus tritt in Zeichenbereich ein
    Die Parameter enthalten die aktuelle Mausposition */
    public void bearbeiteMausHinein(Object o,int x, int y) ;
    /** Bearbeitung des Maus-Ereignisses: Maus tritt aus Zeichenbereich aus
    Die Parameter enthalten die aktuelle Mausposition */
    public void bearbeiteMausHeraus(Object o,int x, int y) ;
}