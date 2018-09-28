/**
 * <p>Materialien zu den zentralen
 * Abiturpruefungen im Fach Informatik ab 2012 in 
 * Nordrhein-Westfalen.</p>
 * <p>Klasse Item</p>
 * <p>Die Klasse Item ist abstrakte Oberklasse aller Klassen, deren Objekte in einen Suchbaum (BinarySearchTree) eingef&uuml;gt werden sollen. 
 * Die Ordnungsrelation wird in den Unterklassen von Item durch &Uuml;berschreiben der drei abstrakten Methoden isEqual, isGreater und isLess festgelegt. 

</p>
 * 
 * <p>NW-Arbeitsgruppe: Materialentwicklung zum Zentralabitur 
 * im Fach Informatik</p>
 * 
 * @version 2010-10-20
 */
 
package basiX.adt;
 
public abstract class Item {

  /**
     *Wenn festgestellt wird, dass das Objekt, von dem die Methode aufgerufen wird, bzgl. der gew&uuml;nschten Ordnungsrelation gr&ouml;sser als das Objekt pItem ist,
     *wird true geliefert. 
     *Sonst wird false geliefert.
     *@param pItem es wird &uuml;berpr&uuml;ft, ob das aufrufende Objekt gr&ouml;sser als dieser Parameter pItem ist
     */
  public abstract boolean isGreater (Item pItem);
  
  /**
     *Wenn festgestellt wird, dass das Objekt, von dem die Methode aufgerufen wird, bzgl. der gew&uuml;nschten Ordnungsrelation kleiner als das Objekt pItem ist,
     *wird true geliefert. 
     *Sonst wird false geliefert.
     *@param pItem es wird &uuml;berpr&uuml;ft, ob das aufrufende Objekt kleiner als dieser Parameter pItem ist
     */
  public abstract boolean isLess (Item pItem);
  
  /**
     *Wenn festgestellt wird, dass das Objekt, von dem die Methode aufgerufen wird, bzgl. der gew&uuml;nschten Ordnungsrelation gleich dem Objekt pItem ist,
     *wird true geliefert. 
     *Sonst wird false geliefert.
     *@param pItem es wird &uuml;berpr&uuml;ft, ob das aufrufende Objekt gleich dem Parameter pItem ist
     */
  public abstract boolean isEqual (Item pItem);
  
}
