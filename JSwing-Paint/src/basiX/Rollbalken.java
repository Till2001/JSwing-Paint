package basiX;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

import javax.swing.JScrollBar;

import basiX.vw.*;

/**
 * Rollbalken sind Oberflaechenelemente, die es dem Benutzer ermöglichen einen
 * Wert in einem bestimmten Bereich mit Hilfe der Maus einzustellen. Zur
 * Ereignisbearbeitung: Ob eine Werteinstellung stattgefunden hat, kann über die
 * Anfrage wurdeBewegt ermittelt werden. Alternativ wird jede neue
 * Werteinstellung einem angemeldeten RollbalkenLauscher signalisiert. siehe:
 * Auftrag setzeRollbalkenLauscher(RollbalkenLauscher l) siehe: Interface
 * RollbalkenLauscher
 */
public class Rollbalken extends Komponente implements Serializable {
	/**
	 * 
	 */

	private boolean wurdeBewegt = false;

	private transient Vector rollbalkenLauscher;

	/**
	 * erzeugt auf einem Fenster einen Rollbalken u.z. horizontal mit linker
	 * oberer Ecke ((0,0) Breite 10 und Hoehe 10, ein Fenster muss vorher
	 * erzeugt sein voreingestellt sind: minimaler Wert 0, maximaler Wert 100,
	 * aktueller Wert 50, Inkrement 1
	 */
	public Rollbalken() {
		this(true, 0, 0, 10, 10, DasFenster.hauptLeinwand());
	}

	/**
	 * erzeugt auf einem Fenster einen Rollbalken u.z. horizontal, wenn erster
	 * Parameter true ist, sonst vertikal mit linker oberer Ecke (x,y)
	 * vorgegebener Breite und Hoehe, ein Fenster muss vorher erzeugt sein
	 * voreingestellt sind: minimaler Wert 0, maximaler Wert 100, aktueller Wert
	 * 50, Inkrement 1
	 */
	public Rollbalken(boolean horizontal, double x, double y, double breite, double hoehe) {
		this(horizontal, x, y, breite, hoehe, DasFenster.hauptLeinwand());
	}

	/**
	 * erzeugt auf dem Fenster f einen Rollbalken u.z. horizontal, wenn erster
	 * Parameter true ist, sonst vertikal mit linker oberer Ecke (x,y)
	 * vorgegebener Breite und Hoehe, ein Fenster muss vorher erzeugt sein
	 * voreingestellt sind: minimaler Wert 0, maximaler Wert 100, aktueller Wert
	 * 50, Inkrement 1
	 * 
	 */
	public Rollbalken(boolean horizontal, double x, double y, double breite, double hoehe,
			Fenster f) {
		this(horizontal, x, y, breite, hoehe, f.leinwand());
	}

	/**
	 * erzeugt auf einem Containerobjekt le einen Rollbalken u.z. horizontal,
	 * wenn erster Parameter true ist, sonst vertikal mit linker oberer Ecke
	 * (x,y) vorgegebener Breite und Hoehe, ein Fenster muss vorher erzeugt sein
	 * voreingestellt sind: minimaler Wert 0, maximaler Wert 100, aktueller Wert
	 * 50, Inkrement 1
	 * 
	 */
	public Rollbalken(boolean horizontal, double x, double y, double breite, double hoehe,
			Komponente le) {
		super(new JScrollBar(horizontal ? Scrollbar.HORIZONTAL
				: Scrollbar.VERTICAL), x, y, breite, hoehe, le);
		try {

			((JScrollBar) meineKomponente)
					.addAdjustmentListener(new AdjustmentListener() {

						public void adjustmentValueChanged(
								AdjustmentEvent adjustmentevent) {
							wurdeBewegt = true;
							Rollbalken.this.fireRollbalken(Rollbalken.this);
						}
					}

					);

			((JScrollBar) meineKomponente).setValues(50, 1, 0, 100);
		} catch (Exception e) {
			System.out
					.println(" Rollbalken konnte nicht erstellt werden, da zuvor kein");
			System.out.println(" Fensterobjekt o.a. erstellt wurde. ");
			System.out.println(" Bitte erst dieses erzeugen");
		}
	}

//	private TastenLauscherVerwalter tlv;
//
//	/** Ereignisbearbeitung registriert einen TastenLauscher */
//	public synchronized void setzeTastenLauscher(TastenLauscher l) {
//		if (tlv == null) {
//			tlv = new TastenLauscherVerwalter(((JScrollBar) meineKomponente));
//		}
//		tlv.setzeTastenLauscher(l);
//	}
//
//	/** Ereignisbearbeitung entfernt einen TastenLauscher */
//	public synchronized void entferneTastenLauscher(TastenLauscher l) {
//		if (tlv != null) {
//			tlv.entferneTastenLauscher(l);
//		}
//	}

	/** bestimmt den Wertebereich des Rollbalkens und den angezeigten Wert */
	public void setzeWerte(int min, int max, int aktuell) {
		((JScrollBar) meineKomponente).setMinimum(min);
		((JScrollBar) meineKomponente).setMaximum(max+1);
		((JScrollBar) meineKomponente).setValue(aktuell);
	}

	/**
	 * Setzt die Ausrichtung des Rollbalkens auf horizontal, wenn der Parameter
	 * true ist, sonst auf vertikal
	 * 
	 * @param horizontal
	 */
	public void setzeAusrichtungHorizontal(boolean horizontal) {
		((JScrollBar) meineKomponente).setOrientation(horizontal ? JScrollBar.HORIZONTAL : JScrollBar.VERTICAL);
	}
	/**
	 * 
	 * @return liefert den Wert true, wenn der Rollbalken horizontal ausgerichtet ist
	 */
	public boolean istHorizontal(){
		return ((JScrollBar) meineKomponente).getOrientation()==JScrollBar.HORIZONTAL;
	}

	/** legt den angezeigten Wert fest */
	public void setzeWert(int wert) {
		((JScrollBar) meineKomponente).setValue(wert);
	}

	/**
	 * bestimmt die durch einen Mausklick auf die Pfeilknöpfe an den Enden des
	 * Rollbalkens erzielte Wertaenderung
	 */
	public void setzeWertAenderung(int delta) {
		((JScrollBar) meineKomponente).setUnitIncrement(delta);
	}

	/** registriert einen Rollbalkenlauscher */
	public synchronized void setzeRollbalkenLauscher(RollbalkenLauscher l) {
		Vector v = rollbalkenLauscher == null ? new Vector(2)
				: (Vector) rollbalkenLauscher.clone();
		if (!v.contains(l)) {
			v.addElement(l);
			rollbalkenLauscher = v;
		}
	}

	/** entfernt einen Rollbalkenlauscher */
	public synchronized void entferneRollbalkenLauscher(RollbalkenLauscher l) {
		if (rollbalkenLauscher != null && rollbalkenLauscher.contains(l)) {
			Vector v = (Vector) rollbalkenLauscher.clone();
			v.removeElement(l);
			rollbalkenLauscher = v;
		}
	}

	/**
	 * liefert den Wert true genau dann, wenn die Rollbalkeneinstellung seit der
	 * letzten Anfrage verändert wurde
	 */
	public boolean wurdeBewegt() {
		boolean merke = wurdeBewegt;
		wurdeBewegt = false;
		return merke;
	}

	/* liefert den aktuellen Wert des Rollbalkens */

	public int wert() {
		return ((JScrollBar) meineKomponente).getValue();
	}

	/**
	 * 
	 * @return liefert Minimum
	 */
	public int min() {
		return ((JScrollBar) meineKomponente).getMinimum();
	}

	/**
	 * 
	 * @return liefert Maximum
	 */
	public int max() {
		return ((JScrollBar) meineKomponente).getMaximum();
	}

	/*
	 * void writeObject(ObjectOutputStream oos) throws IOException {
	 * oos.defaultWriteObject(); }
	 * 
	 * void readObject(ObjectInputStream ois) throws ClassNotFoundException,
	 * IOException { ois.defaultReadObject(); }
	 */

	/**  */
	protected void fireRollbalken(Rollbalken e) {
		if (rollbalkenLauscher != null) {
			Vector listeners = rollbalkenLauscher;
			int count = listeners.size();
			for (int i = 0; i < count; i++)
				((RollbalkenLauscher) listeners.elementAt(i))
						.bearbeiteRollbalkenBewegung(e);
		}
	}

}
