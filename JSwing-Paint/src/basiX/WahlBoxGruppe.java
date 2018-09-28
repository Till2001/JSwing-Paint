package basiX;

import java.util.*;
import java.awt.*;
import java.io.*;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;

/**
 * WahlBoxGruppen dienen dazu, WahlBoxen zusammenzufassen. Unter allen
 * WahlBoxen, die zu einer Gruppe gehören, ist nur jeweils eine eingeschaltet:
 * Wird eine nicht eingschaltete WahlBox der Gruppe mit der Maus angeklickt, so
 * wird sie eingeschaltet und die bisher eingeschaltete ändert ihren Zustand.
 * Zur Ereignisbearbeitung: Ob der Zustand einer WahlBox in einer der
 * WahlBoxGruppe geändert wurde, kann über die Anfrage wurdeGeaendert ermittelt
 * werden. Alternativ wird jede Zustandsänderung einem angemeldeten
 * WahlBoxGruppeLauscher signalisiert. siehe: Auftrag
 * setzeWahlBoxGruppeLauscher(WahlBoxGruppeLauscher l) siehe: Interface
 * WahlBoxGruppeLauscher
 */
public class WahlBoxGruppe implements Serializable {

	/**
	 * 
	 */

	private transient Vector<WahlBoxGruppeLauscher> wahlBoxGruppeLauscher;
	private Vector<WahlBox> wahlbox;

	private ButtonGroup bg;

	private boolean wurdeGeaendert = false;
	private int lfdNr = 0;

	/** Erzeugt eine WahlBoxgruppe */
	public WahlBoxGruppe() {
		bg = new ButtonGroup();
		wahlbox = new Vector<WahlBox>();
	}

	class Wbl implements WahlBoxLauscher {
		
		public void bearbeiteWahlBoxAenderung(WahlBox k) {
			WahlBoxGruppe.this.wurdeGeaendert = true;
			WahlBoxGruppe.this.fireWahlBoxGruppe(WahlBoxGruppe.this);

		}
	}

	private Wbl wbl = new Wbl();

	/** Fügt eine WahlBox der Gruppe hinzu */
	public void fuegeEin(WahlBox box) {
		if (!wahlbox.contains(box)) {
			wahlbox.add(box);
			lfdNr++;
//			System.out.println(lfdNr);
			box.setzeID(lfdNr);
			bg.add((JRadioButton) box.meineKomponente);
			box.setzeWahlBoxGruppe(this);// Vorsicht vor Endlosrekursion			
			box.setzeWahlBoxLauscher(wbl);
			box.setzeZustand(true);
		}
	}

	/** Entfernt eine WahlBox aus der Gruppe */
	public void entferne(WahlBox box) {
		if (wahlbox.contains(box)) {
			box.setzeID(0);
			bg.remove((JRadioButton) box.meineKomponente);
			box.entferneWahlBoxGruppe();// Vorsicht vor Endlosrekursion
			wahlbox.remove(box);
			box.entferneWahlBoxLauscher(wbl);
			box.setzeZustand(false);
			if (!wahlbox.isEmpty()) {
				wahlbox.get(0).setzeZustand(true);
			}
		}
	}

	/**
	 * bestimmt die ausgewählte WahlBox der Gruppe, alle anderen WahlBoxen der
	 * Gruppe werden auf "nicht_ausgewählt" gesetzt
	 */
	public void waehleBox(WahlBox box) {
		try {
			box.setzeZustand(true);
		} catch (Exception exc) {
		}
	}

	/** registriert einen WahlBoxgruppelauscher */
	public synchronized void setzeWahlBoxGruppeLauscher(WahlBoxGruppeLauscher l) {
		Vector<WahlBoxGruppeLauscher> v = wahlBoxGruppeLauscher == null ? new Vector<WahlBoxGruppeLauscher>(
				2)
				: (Vector<WahlBoxGruppeLauscher>) wahlBoxGruppeLauscher.clone();
		if (!v.contains(l)) {
			v.addElement(l);
			wahlBoxGruppeLauscher = v;
		}
	}

	/** entfernt einen WahlBoxgruppelauscher */
	public synchronized void entferneWahlBoxGruppeLauscher(WahlBoxLauscher l) {
		if (wahlBoxGruppeLauscher != null && wahlBoxGruppeLauscher.contains(l)) {
			Vector<WahlBoxGruppeLauscher> v = (Vector<WahlBoxGruppeLauscher>) wahlBoxGruppeLauscher
					.clone();
			v.removeElement(l);
			wahlBoxGruppeLauscher = v;
		}
	}

	/**
	 * liefert genau dann den Wert true, wenn seit dem letzten Aufruf der
	 * Anfrage eine andere WahlBox der Gruppe ausgewählt wurde
	 */
	public boolean wurdeGeaendert() {
		boolean merke = wurdeGeaendert;
		wurdeGeaendert = false;
		return merke;
	}

	/** liefert die WahlBox der Gruppe, die ausgewählt ist */
	public WahlBox ausgewaehlteBox() {
		WahlBox box;
		Enumeration<WahlBox> wb = wahlbox.elements();
		while (wb.hasMoreElements()) {
			box = wb.nextElement();
			if (box.istGewaehlt()) {
				return box;
			}
		}
		return null;
	}

	/*
	 * void writeObject(ObjectOutputStream oos) throws IOException {
	 * oos.defaultWriteObject(); }
	 * 
	 * void readObject(ObjectInputStream ois) throws ClassNotFoundException,
	 * IOException { ois.defaultReadObject(); }
	 */
	/**
	 */
	protected void fireWahlBoxGruppe(WahlBoxGruppe e) {
		if (wahlBoxGruppeLauscher != null) {
			Vector<WahlBoxGruppeLauscher> listeners = wahlBoxGruppeLauscher;
			int count = listeners.size();
			for (int i = 0; i < count; i++)
				(listeners.elementAt(i)).bearbeiteWahlBoxAuswahl(e);
		}
	}

	public void gibFrei() {
		while (!wahlbox.isEmpty()) {
			this.entferne(wahlbox.get(0));
		}
	}

}