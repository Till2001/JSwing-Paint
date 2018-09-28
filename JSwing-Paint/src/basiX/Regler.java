package basiX;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

import javax.swing.JScrollBar;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import basiX.vw.*;

/**
 * Gleiter sind Oberflaechenelemente, die es dem Benutzer ermöglichen einen
 * Wert in einem bestimmten Bereich mit Hilfe der Maus einzustellen. Zur
 * Ereignisbearbeitung: Ob eine Werteinstellung stattgefunden hat, kann über die
 * Anfrage wurdeBewegt ermittelt werden. Alternativ wird jede neue
 * Werteinstellung einem angemeldeten GleiterLauscher signalisiert. siehe:
 * Auftrag setzeGleiterLauscher(GleiterLauscher l) siehe: Interface
 * GleiterLauscher
 */
public class Regler extends Komponente implements Serializable {
	/**
	 * 
	 */

	private boolean wurdeBewegt = false;

	private transient Vector reglerLauscher;

	/**
	 * erzeugt auf einem Fenster einen Gleiter u.z. horizontal mit linker
	 * oberer Ecke ((0,0) Breite 10 und Hoehe 10, ein Fenster muss vorher
	 * erzeugt sein voreingestellt sind: minimaler Wert 0, maximaler Wert 100,
	 * aktueller Wert 50, Inkrement 1
	 */
	public Regler() {
		this(true, 0, 0, 10, 10, DasFenster.hauptLeinwand());
	}

	/**
	 * erzeugt auf einem Fenster einen Gleiter u.z. horizontal, wenn erster
	 * Parameter true ist, sonst vertikal mit linker oberer Ecke (x,y)
	 * vorgegebener Breite und Hoehe, ein Fenster muss vorher erzeugt sein
	 * voreingestellt sind: minimaler Wert 0, maximaler Wert 100, aktueller Wert
	 * 50, Inkrement 1
	 */
	public Regler(boolean horizontal, double x, double y, double breite, double hoehe) {
		this(horizontal, x, y, breite, hoehe, DasFenster.hauptLeinwand());
	}

	/**
	 * erzeugt auf dem Fenster f einen Gleiter u.z. horizontal, wenn erster
	 * Parameter true ist, sonst vertikal mit linker oberer Ecke (x,y)
	 * vorgegebener Breite und Hoehe, ein Fenster muss vorher erzeugt sein
	 * voreingestellt sind: minimaler Wert 0, maximaler Wert 100, aktueller Wert
	 * 50, Inkrement 1
	 * 
	 */
	public Regler(boolean horizontal, double x, double y, double breite, double hoehe,
			Fenster f) {
		this(horizontal, x, y, breite, hoehe, f.leinwand());
	}

	/**
	 * erzeugt auf einem Containerobjekt le einen Gleiter u.z. horizontal,
	 * wenn erster Parameter true ist, sonst vertikal mit linker oberer Ecke
	 * (x,y) vorgegebener Breite und Hoehe, ein Fenster muss vorher erzeugt sein
	 * voreingestellt sind: minimaler Wert 0, maximaler Wert 100, aktueller Wert
	 * 50, Inkrement 1
	 * 
	 */
	public Regler(boolean horizontal, double x, double y, double breite, double hoehe,
			Komponente le) {
		super(new JSlider(horizontal ? Scrollbar.HORIZONTAL
				: Scrollbar.VERTICAL), x, y, breite, hoehe, le);
		try {

			((JSlider) meineKomponente).addChangeListener(new ChangeListener() {

				
				public void stateChanged(ChangeEvent arg0) {
					wurdeBewegt = true;
					Regler.this.fireRegler(Regler.this);

				}
			}

			);

			((JSlider) meineKomponente).setMinimum(0);
			((JSlider) meineKomponente).setMaximum(100);
			((JSlider) meineKomponente).setValue(50);
		} catch (Exception e) {
			System.out
					.println(" Gleiter konnte nicht erstellt werden, da zuvor kein");
			System.out.println(" Fensterobjekt o.a. erstellt wurde. ");
			System.out.println(" Bitte erst dieses erzeugen");
		}
	}

//	private TastenLauscherVerwalter tlv;
//
//	/** Ereignisbearbeitung registriert einen TastenLauscher */
//	public synchronized void setzeTastenLauscher(TastenLauscher l) {
//		if (tlv == null) {
//			tlv = new TastenLauscherVerwalter(((JSlider) meineKomponente));
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

	/** bestimmt den Wertebereich des Gleiters und den angezeigten Wert */
	public void setzeWerte(int min, int max, int aktuell) {
		((JSlider) meineKomponente).setMinimum(min);
		((JSlider) meineKomponente).setMaximum(max);
		((JSlider) meineKomponente).setValue(aktuell);
	}

	/** legt den angezeigten Wert fest */
	public void setzeWert(int wert) {
		((JSlider) meineKomponente).setValue(wert);
	}

	/**
	 * Gibt den minimalen Abstand zwischen zwei wählbaren Werten an
	 * 
	 * @param wert
	 */
	public void setzeMinimalenWertAbstand(int wert) {
		((JSlider) meineKomponente).setMinorTickSpacing(wert);
	}

	/**
	 * Gibt Zwischenwertabstände an, die in der Skala hervorgehoben werden
	 * 
	 * @param wert
	 */
	public void setzeHauptWertAbstand(int wert) {
		((JSlider) meineKomponente).setMajorTickSpacing(wert);
	}

	/**
	 * sorgt für die Darstellung von Skalenstrichen
	 * 
	 * @param wert
	 */
	public void setzeSkalenStricheSichtbar(boolean wert) {
		((JSlider) meineKomponente).setPaintTicks(wert);
	}

	/**
	 * zeigt die Skalenbeschriftung (Zahlenwerte)
	 * 
	 * @param wert
	 */
	public void setzeSkalenBeschriftungSichtbar(boolean wert) {
		((JSlider) meineKomponente).setPaintLabels(wert);
	}

	/**
	 * sorgt dafür, dass eine Wertauswahl nur an Skalenstrichen möglich ist
	 * 
	 * @param wert
	 */
	public void rasteEin(boolean wert) {
		((JSlider) meineKomponente).setSnapToTicks(wert);
	}

	/** registriert einen Reglerlauscher */
	public synchronized void setzeReglerLauscher(ReglerLauscher l) {
		Vector v = reglerLauscher == null ? new Vector(2)
				: (Vector) reglerLauscher.clone();
		if (!v.contains(l)) {
			v.addElement(l);
			reglerLauscher = v;
		}
	}

	/** entfernt einen Reglerlauscher */
	public synchronized void entferneReglerLauscher(ReglerLauscher l) {
		if (reglerLauscher != null && reglerLauscher.contains(l)) {
			Vector v = (Vector) reglerLauscher.clone();
			v.removeElement(l);
			reglerLauscher = v;
		}
	}

	/**
	 * liefert den Wert true genau dann, wenn die Gleitereinstellung seit der
	 * letzten Anfrage verändert wurde
	 */
	public boolean wurdeBewegt() {
		boolean merke = wurdeBewegt;
		wurdeBewegt = false;
		return merke;
	}

	/* liefert den aktuellen Wert des Gleiters */

	public int wert() {
		return ((JSlider) meineKomponente).getValue();
	}

	/**
	 * 
	 * @return liefert Minimum
	 */
	public int min() {
		return ((JSlider) meineKomponente).getMinimum();
	}

	/**
	 * 
	 * @return liefert Maximum
	 */
	public int max() {
		return ((JSlider) meineKomponente).getMaximum();
	}

	/*
	 * void writeObject(ObjectOutputStream oos) throws IOException {
	 * oos.defaultWriteObject(); }
	 * 
	 * void readObject(ObjectInputStream ois) throws ClassNotFoundException,
	 * IOException { ois.defaultReadObject(); }
	 */

	/**  */
	protected void fireRegler(Regler regler) {
		if (reglerLauscher != null) {
			Vector listeners = reglerLauscher;
			int count = listeners.size();
			for (int i = 0; i < count; i++)
				((ReglerLauscher) listeners.elementAt(i))
						.bearbeiteReglerBewegung(regler);
		}
	}

	public int minimalerWertAbstand() {
		
		return ((JSlider) meineKomponente).getMinorTickSpacing();
	}

	public int hauptWertAbstand() {
		return ((JSlider) meineKomponente).getMajorTickSpacing();
	}

	public boolean rastetEin() {
		return ((JSlider) meineKomponente).getSnapToTicks();
	}

	public boolean skalenBeschriftungSichtbar() {
		return ((JSlider) meineKomponente).getPaintLabels();
	}

	public boolean skalenStricheSichtbar() {
		return ((JSlider) meineKomponente).getPaintTicks();
	}

}
