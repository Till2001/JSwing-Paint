package basiX.adt;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * 
 * <p>
 * Materialien zu den zentralen Abiturpruefungen im Fach Informatik ab 2012 in
 * Nordrhein-Westfalen.
 * </p>
 * <p>
 * Klasse List
 * </p>
 * <p>
 * Objekte der Klasse List verwalten beliebig viele, linear angeordnete Objekte.
 * Auf hoechstens ein Listenobjekt, aktuelles Objekt genannt, kann jeweils
 * zugegriffen werden. Wenn eine Liste leer ist, vollstaendig durchlaufen wurde
 * oder das aktuelle Objekt am Ende der Liste geloescht wurde, gibt es kein
 * aktuelles Objekt. Das erste oder das letzte Objekt einer Liste koennen durch
 * einen Auftrag zum aktuellen Objekt gemacht werden. Ausserdem kann das dem
 * aktuellen Objekt folgende Listenobjekt zum neuen aktuellen Objekt werden. Das
 * aktuelle Objekt kann gelesen, veraendert oder geloescht werden. Ausserdem
 * kann vor dem aktuellen Objekt ein Listenobjekt eingefuegt werden.
 * </p>
 * 
 * <p>
 * NW-Arbeitsgruppe: Materialentwicklung zum Zentralabitur im Fach Informatik
 * </p>
 * 
 * 
 * 
 * <ul>
 * <li>public List()</li>
 * <li>public boolean isEmpty()</li>
 * <li>public boolean hasAccess()</li>
 * <li>public void next()</li>
 * <li>public void toFirst()</li>
 * <li>public void toLast()</li>
 * <li>public Object getObject()</li>
 * <li>public void setObject (Object pObject)</li>
 * <li>public void insert(Object pObject)</li>
 * <li>public void remove()</li>
 * <li>public void concat(List l)</li>
 * </ul>
 * <b> Nicht Bestandteil des Zentralabiturs 2012</b> - dient der praktischen
 * Anwendung<br>
 * <ul>
 * <li>public load(String fileName)</li>
 * <li>public save(String fileName)</li>
 * </ul>
 * 
 * @author Georg Dick
 */
public class List implements Serializable {
	/**
	 * Attribut dient zur Serialisierung.
	 */

	/**
	 * aktuelles Element.
	 */
	private ListElement voraktuell;

	/**
	 * Dummy vor erstem Element der Liste
	 */
	private ListElement vorfirst;

	/**
	 * Eine leere Liste wird erzeugt.
	 */
	public List() {
		this.vorfirst = new ListElement();
		this.vorfirst.setNext(null);
		this.voraktuell = this.vorfirst;
	}

	/**
	 * Die Anfrage liefert den Wert true, wenn die Liste keine Objekte enthaelt,
	 * sonst liefert sie den Wert false.
	 * 
	 * @return true, wenn die Liste leer ist, sonst false
	 */
	public boolean isEmpty() {
		return this.vorfirst.getNext() == null;
	}

	/**
	 * Die Anfrage liefert den Wert true, wenn es ein aktuelles Objekt gibt,
	 * sonst liefert sie den Wert false.
	 * 
	 * @return true, falls Zugriff moeglich, sonst false
	 */
	public boolean hasAccess() {
		return this.voraktuell.getNext() != null;
	}

	/**
	 * Falls die Liste nicht leer ist, es ein aktuelles Objekt gibt und dieses
	 * nicht das letzte Objekt der Liste ist, wird das dem aktuellen Objekt in
	 * der Liste folgende Objekt zum aktuellen Objekt, andernfalls gibt es nach
	 * Ausfuehrung des Auftrags kein aktuelles Objekt, d.h. hasAccess() liefert
	 * den Wert false.
	 */
	public void next() {
		if (this.hasAccess()) {
			this.voraktuell = this.voraktuell.getNext();
		}
	}

	/**
	 * Falls die Liste nicht leer ist, wird das erste Objekt der Liste aktuelles
	 * Objekt. Ist die Liste leer, geschieht nichts.
	 */
	public void toFirst() {
		this.voraktuell = this.vorfirst;
	}

	/**
	 * Falls die Liste nicht leer ist, wird das letzte Objekt der Liste
	 * aktuelles Objekt. Ist die Liste leer, geschieht nichts.
	 */
	public void toLast() {
		if (!this.hasAccess()) {
			this.voraktuell = this.vorfirst;
		}
		while (this.voraktuell.getNext() != null
				&& this.voraktuell.getNext().getNext() != null) {
			this.voraktuell = this.voraktuell.getNext();
		}
	}

	/**
	 * Falls es ein aktuelles Objekt gibt (hasAccess() == true), wird das
	 * aktuelle Objekt zurueckgegeben, andernfalls (hasAccess()== false) gibt
	 * die Anfrage den Wert null zurueck.
	 * 
	 * @return Inhaltsobjekt
	 */
	public Object getObject() {
		if (this.hasAccess()) {
			return this.voraktuell.getNext().getItem();
		} else {
			return null;
		}
	}

	/**
	 * Falls es ein aktuelles Objekt gibt (hasAccess() == true) und pObject
	 * ungleich null ist, wird das aktuelle Objekt durch pObject ersetzt. Sonst
	 * bleibt die Liste unveraendert.
	 * 
	 * @param pObject
	 *            Inhaltsobjekt
	 */
	public void setObject(Object pObject) {
		if (pObject != null && this.hasAccess())
			this.voraktuell.getNext().setItem(pObject);
	}

	/**
	 * Ein neues Objekt pObject wird am Ende der Liste eingefuegt. Das aktuelle
	 * Objekt bleibt unveraendert. Wenn die Liste leer ist, wird das Objekt
	 * pObject in die Liste eingefuegt und es gibt weiterhin kein aktuelles
	 * Objekt (hasAccess() == false). Falls pObject gleich null ist, bleibt die
	 * Liste unveraendert.
	 * 
	 * @param pObject
	 *            Inhaltsobject
	 */
	public void append(Object pObject) {
		if (pObject == null) {
			return;
		}
		if (this.isEmpty()) {
			vorfirst.setNext(new ListElement(pObject));
			voraktuell = vorfirst.getNext();// damit kein Access, warum auch
											// immer
			return;
		}
		ListElement l = vorfirst;
		while (l.getNext() != null) {
			l = l.getNext();
		}
		l.setNext(new ListElement(pObject));
	}

	/**
	 * Falls es ein aktuelles Objekt gibt (hasAccess() == true), wird ein neues
	 * Objekt vor dem aktuellen Objekt in die Liste eingefuegt. Das aktuelle
	 * Objekt bleibt unveraendert. Wenn die Liste leer ist, wird pObject in die
	 * Liste eingefuegt und es gibt weiterhin kein aktuelles Objekt (hasAccess()
	 * == false). Falls es kein aktuelles Objekt gibt (hasAccess() == false) und
	 * die Liste nicht leer ist oder pObject gleich null ist, bleibt die Liste
	 * unveraendert.
	 * 
	 * @param pObject
	 *            Inhaltsobjekt
	 */
	public void insert(Object pObject) {
		if (pObject == null) {
			return;
		}
		if (this.isEmpty()) {
			this.append(pObject);
			return;
		}
		if (!this.hasAccess()) {
			return;
		}
		ListElement neu = new ListElement(pObject);
		neu.setNext(voraktuell.getNext());
		voraktuell.setNext(neu);
		voraktuell = neu;
	}

	/**
	 * Die Liste pList wird an die Liste angehaengt. Anschliessend wird pList
	 * eine leere Liste. Das aktuelle Objekt bleibt unveraendert. Falls pList
	 * null oder eine leere Liste ist, bleibt die Liste unveraendert.
	 * 
	 * @param pList
	 *            Liste
	 */
	public void concat(List pList) {
		if (pList == null || pList.isEmpty()) {
			return;
		}
		for (pList.toFirst(); pList.hasAccess(); pList.next()) {
			this.append(pList.getObject());
		}
		pList.toFirst();
		while (!pList.isEmpty()) {
			pList.remove();
		}
	}

	/**
	 * Falls es ein aktuelles Objekt gibt (hasAccess() == true), wird das
	 * aktuelle Objekt geloescht und das Objekt hinter dem geloeschten Objekt
	 * wird zum aktuellen Objekt. Wird das Objekt, das am Ende der Liste steht,
	 * geloescht, gibt es kein aktuelles Objekt mehr (hasAccess() == false).
	 * Wenn die Liste leer ist oder es kein aktuelles Objekt gibt (hasAccess()
	 * == false), bleibt die Liste unveraendert.
	 */
	public void remove() {
		if (this.hasAccess()) {
			voraktuell.setNext(voraktuell.getNext().getNext());
		}
	}

	/**
	 * Laedt die gesamte Liste als Objekt aus einer Datei - <b>Nicht Bestandteil
	 * des Zentralbiturs 2012!!!</b><br>
	 * Neues aktuelles Element wird das erste Listenelement(Setzung!).
	 * 
	 * @param fileName
	 *            [Laufwerk][Pfad]Dateiname,
	 */
	public void load(String fileName) {
		try {
			// Es wird elementweise gespeichert, da sonst Stackoverflow bei
			// grossen Listen
			ObjectInputStream stream = new ObjectInputStream(
					new FileInputStream(fileName));
			ListElement dummyVorFirst = new ListElement();
			ListElement hilf = dummyVorFirst;
			int iAnz = ((Integer) stream.readObject()).intValue();
			try {
				hilf.setItem(stream.readObject());
				for (int i = 2; i <= iAnz; i++) {
					ListElement h = new ListElement();
					hilf.setNext(h);
					hilf = h;
					hilf.setItem(stream.readObject());
					// System.out.println(hilf.getItem()==null?"null":hilf.getItem().getClass().toString());

				}
			} catch (Exception e) {

			}
			vorfirst = dummyVorFirst;
			this.toFirst(); // Als neues aktuelles Element wird das erste
							// Listenelement gesetzt!
			stream.close();

		} catch (Exception e) {
			System.out.println("Fehler " + e.toString());
		}

	}

	/**
	 * Speichert die gesamte Liste in einer Datei - <b>Nicht Bestandteil des
	 * Zentralbiturs 2012!!!</b><br>
	 * Das aktuelle Element bleibt erhalten!
	 * 
	 * @param fileName
	 *            [Laufwerk][Pfad]Dateiname.
	 */
	public void save(String fileName) {
		try {
			ObjectOutputStream stream = new ObjectOutputStream(
					new FileOutputStream(fileName));
			ListElement hilf;
			int anzahl = 0;
			for (hilf = this.vorfirst; hilf != null; hilf = hilf.getNext()) {
				anzahl++;
			}
			stream.writeObject(new Integer(anzahl));
			for (hilf = this.vorfirst; hilf != null; hilf = hilf.getNext()) {
				stream.writeObject(hilf.getItem());
			}
			stream.close();
		} catch (Exception e) { // Fehler beim Speichern der Datei.

		}
	}

}
