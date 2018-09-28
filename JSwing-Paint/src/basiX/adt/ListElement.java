package basiX.adt;
import java.io.Serializable;

/**
 * Datei ListElement.java (Klasse Listelement mit generalisiertem Datentyp Object).<br>
 * <br>
 * Die Klasse ListElement ist die <b>Basisklasse fuer doppelt verkette Listen</b>.<br>
 * <br>
 * Ein ListElement enthaelt eine Date, einen Verweis auf den Vorgaenger und einen Verweis
 * auf den Nachfolger. <br>
 *
 * Die Klasse ist wegen der Speicherbarkeit der Oberklasse serialisiert.<br>
 * <b>Die Klasse ist nicht Bestandteil des Zentralabiturs 2009, aber notwendig!!!</b>
 
 */
public class ListElement implements Serializable
{
	/**
	 * Attribut dient zur Serialisierung.
	 */
	
	private static final long serialVersionUID = -3041697271163657863L;

	/**
	 * Attribut Date des Listenelements.
	 */
	private Object item;

	/**
	 * Attribut Nachfolger des Listenelements.
	 */
	private ListElement next;

	
	/**
	 * Der Konstruktor erzeugt ein Listenelement mit Date, Vorgaenger und Nachfolger.
	 *
	 * @param item Date des Listenelements.
	 * @param previous Vorgaenger des Listenelements
	 * @param next Nachfolger des Listenelemente.
	 */
	public ListElement(Object item)
	{	this.item = item;
		this.next = null;
	}

	public ListElement() {
		this(null);
	}

	/**
	 * Gibt die Date des Elements zurueck.
	 * @return  Date des Listenelements.
	 */
	public Object getItem()	{	
		return this.item;
	}

	/**
	 * Gibt den Nachfolger des Listenelements zurueck.
	 * @return    Nachfolger des Listenelements.
	 */
	public ListElement getNext()
	{	return this.next;
	}

	
	/**
	 * Setzt die Date des Listenelements.
	 * @param item    neue Date des Listenelements.
	 */
	public void setItem(Object item){	
		this.item = item;
	}

	/**
	 * Setzt den Nachfolger des Listenelements.
	 * @param next    neuer Nachfolger des Listenelements.
	 */
	public void setNext(ListElement next){	
		this.next = next;
	}

	

}
