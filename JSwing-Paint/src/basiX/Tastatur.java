package basiX;

import java.awt.AWTEvent;
import java.awt.Toolkit;
import java.awt.event.*;
import java.util.*;

import basiX.vw.*;

/**
 * @author Georg Dick
 * @version 6.1.2010 
 * Eine Tastatur realisiert die Tastatureingabe des
 * verwendeten Computers. Sie speichert die eingegebenen
 * Tastaturzeichen in der Reihenfolge ihrer Eingabe in einem
 * Tastaturpuffer. Der Puffer enthält maximal 20 Zeichen.
 * Die Pufferlänge kann geändert werden.
 * Ist der Puffer voll, so wird bei einem Tastedruck, das "älteste" Zeichen 
 * des Puffers entfernt und das aktuelle hinten angehangen.
 * Ferner liefert ein Tastaturobjekt für jede Taste den aktuellen Zustand 
 * (gedrückt oder eben nicht gedrückt)
 * Für einige Tastatureingaben stehen Konstanten der
 * Klasse Zeichen zur Verfügung. Dies betrifft insbesonder
 * Sondertasten, wie die Cursor- oder Funktionstasten. Beispielcode: if
 * (tastatur.zeichen() == Zeichen.ENTER ) {...} untersucht, ob die
 * Entertaste (=Eingabetaste) gedrückt wurde.
 * Hinweis: Die Tastatur reagiert nur auf Tastenbenutzung, wenn 
 * das Programmfenster den Fokus hat.
 */
public class Tastatur {

	/**
	 * 
	 */
	private int maxPufferLaenge = 20;
	private Vector<Character> puffer = new Vector<Character>(maxPufferLaenge);
	private boolean[] gedrueckteTasten = new boolean[1024] ;
	private char aktuellesZeichen=Zeichen.UNDFINIERT;

	/**
	 * erzeugt ein Tastaturobjekt.
	 */
	public Tastatur() {
//		puffer = new Vector<Character>(20, 10);
		for (int i=0;i<1024;i++){
			gedrueckteTasten[i]=false;
		}
		Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {
			public void eventDispatched(AWTEvent event) {
				if (((KeyEvent) event).getID() == KeyEvent.KEY_PRESSED) {
					char t = zeichenVon((KeyEvent) event);
					if (t != KeyEvent.CHAR_UNDEFINED) {
						puffer.addElement(new Character(t));
						if (maxPufferLaenge < puffer.size()){
							puffer.remove(0);
						}
					}
					if (t != KeyEvent.CHAR_UNDEFINED && t<1024) {
						gedrueckteTasten[t]=true;
						
					}					
				}
				if (((KeyEvent) event).getID() == KeyEvent.KEY_RELEASED) {
					char t = zeichenVon((KeyEvent) event);
					if (t<1024){ gedrueckteTasten[t]=false;}
				}				
			}
			
		}, AWTEvent.KEY_EVENT_MASK);
	}

	protected void info(String s) {
		if (Einstellungen.DEBUGMODUS)
			System.out.println(s);
	}

	/**
	 * gibt die Ressouren der Tastatur frei. Danach kann die Tastatur nicht mehr
	 * verwendet werden.
	 */
	public void gibFrei() {
		// Tastenverwaltung.entferneTastenLauscher(tl);
		puffer.removeAllElements();
		puffer = null;
	}

	/**
	 * liefert das erste Zeichen aus dem Tastaturpuffer und entfernt dieses aus
	 * dem Puffer. Ist der Tastaturpuffer leer, so wird "Zeichen.UNDFINIERT"
	 * zurück gegeben.
	 */
	public char zeichen() {
		if (puffer.isEmpty()) {
			info("Puffer leer");
			return Zeichen.UNDFINIERT;
		} else {
			char merke = ((Character) puffer.firstElement()).charValue();
			aktuellesZeichen = merke;
			puffer.removeElementAt(0);
			return merke;
		}
	}
	
	/**
	 * liefert das letzte Zeichen, das aus dem Tastaturpuffer geholt wurde. 
	 */
	public char aktuellesZeichen() {
		return aktuellesZeichen;	
	}
	
	/**
	 * holt das erste Zeichen aus dem Tastaturpuffer und setzt dieses als
	 * aktuelles Zeichen. Ist der Tastaturpuffer leer, so wird "Zeichen.UNDFINIERT"
	 * zurück gegeben.
	 */
	public char holeZeichen() {
		if (puffer.isEmpty()) {
			info("Puffer leer");
			return Zeichen.UNDFINIERT;
		} else {
			char merke = ((Character) puffer.firstElement()).charValue();
			aktuellesZeichen = merke;
			puffer.removeElementAt(0);
			return merke;
		}
	}
	
	
	

	/**
	 * liefert den Wert wahr, wenn der Tastaturpuffer nicht leer ist, also
	 * mindestens eine Taste gedrückt wurde
	 */
	public boolean wurdeGedrueckt() {
		return !puffer.isEmpty();

		/*
		 * return aktuellesZeichen != KeyEvent.CHAR_UNDEFINED;
		 */
	}

	protected char zeichenVon(KeyEvent k) {
		char c = k.getKeyChar();
		switch (k.getKeyCode()) {
		case KeyEvent.CHAR_UNDEFINED:
			return Zeichen.UNDFINIERT;
		case 27:
			return Zeichen.ESC;

		case KeyEvent.VK_DOWN:
			return Zeichen.PFEILUNTEN;

		case KeyEvent.VK_END:
			return Zeichen.ENDE;

		case KeyEvent.VK_F1:
			return Zeichen.F1;

		case KeyEvent.VK_F2:
			return Zeichen.F2;

		case KeyEvent.VK_F3:
			return Zeichen.F3;

		case KeyEvent.VK_F4:
			return Zeichen.F4;

		case KeyEvent.VK_F5:
			return Zeichen.F5;

		case KeyEvent.VK_F6:
			return Zeichen.F6;

		case KeyEvent.VK_F7:
			return Zeichen.F7;

		case KeyEvent.VK_F8:
			return Zeichen.F8;

		case KeyEvent.VK_F9:
			return Zeichen.F9;

		case KeyEvent.VK_F10:
			return Zeichen.F10;

		case KeyEvent.VK_F11:
			return Zeichen.F11;

		case KeyEvent.VK_F12:
			return Zeichen.F12;

		case KeyEvent.VK_HOME:
			return Zeichen.POS1;

		case KeyEvent.VK_LEFT:
			return Zeichen.PFEILLINKS;

		case KeyEvent.VK_PAGE_DOWN:
			return Zeichen.BILDUNTEN;

		case KeyEvent.VK_PAGE_UP:
			return Zeichen.BILDAUF;

		case KeyEvent.VK_RIGHT:
			return Zeichen.PFEILRECHTS;

		case KeyEvent.VK_UP:
			return Zeichen.PFEILOBEN;
		default:
			return c;
		}
	}
	
	public boolean istGedrueckt(char t){		
		return gedrueckteTasten[t];
	}
	
	/**
	 * legt fest, wie viele Zeichen maximal in den
	 * Puffer geschrieben werden. Der Puffer enthält immer die Tastendrucke.
	 * Die Anzahl der Zeichen ist standardmäßig auf 20 begrenzt. 
	 * Wird der Wert beispielsweise auf 1
	 * gesetzt, so wird nur der letzte Tastendruck gespeichert. 
	 * Wird ein Wert unterhalb von 1 übergeben, so wird der Puffer
	 * auf die Länge 1 gesetzt.
	 */
	public void setzePufferLaenge(int l) {
		maxPufferLaenge = (l > 0) ? l : 1;
		// gegebenenfalls Puffer verkleinern
		int loeschzahl = puffer.size() - maxPufferLaenge;
		for (int i = loeschzahl - 1; i >= 0; i--) {
			puffer.remove(i);
		}		
	}

	/**
	 * 
	 * @return liefert die maximale Anzahl von Klick bzw. Bewegungsereignissen
	 *         der Maus, die zwischengespeichert werden
	 */
	public int getPufferLaenge() {
		return maxPufferLaenge;
	}

}