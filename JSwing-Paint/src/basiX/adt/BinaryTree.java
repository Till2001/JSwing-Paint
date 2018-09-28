/**
 * <p>Materialien zu den zentralen
 * Abiturpruefungen im Fach Informatik ab 2012 in 
 * Nordrhein-Westfalen.</p>
 * <p>Klasse BinaryTree</p>
 * <p>Mithilfe der Klasse BinaryTree k&ouml;nnen beliebig viele Inhaltsobjekte in einem Bin&auml;rbaum verwaltet werden.
 * Ein Objekt der Klasse stellt entweder einen leeren Baum dar oder verwaltet ein Inhaltsobjekt sowie einen 
 * linken und einen rechten Teilbaum, die ebenfalls Objekte der Klasse BinaryTree sind.


 * 
 * @version 2012-12-22
 */
 
package basiX.adt;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
 
public class BinaryTree implements java.io.Serializable
{
    private Object lContent;
    private BinaryTree lLeftTree, lRightTree;

    /**
     *Nach dem Aufruf des Konstruktors existiert ein leerer Bin&auml;rbaum.
     */
    public BinaryTree() {
        lContent = null;
        lLeftTree = null;
        lRightTree = null;
    }   
    
    /**
     *Wenn der Parameter pContent ungleich null ist, existiert nach dem Aufruf des Konstruktors der Bin&auml;rbaum und hat pContent als Inhaltsobjekt
     *und zwei leere Teilb&auml;ume. Falls der Parameter null ist, wird ein leerer Bin&auml;rbaum erzeugt.
     *@param pContent  Inhaltsobjekt
     */
    public BinaryTree(Object pContent) {
        if (pContent!=null) {
          lContent = pContent;
          lLeftTree = new BinaryTree();
          lRightTree = new BinaryTree();
        }
        else {
          lContent = null;
          lLeftTree = null;
          lRightTree = null;
        }   
    }        
    
    /**
     *Wenn der Parameter pContent ungleich null ist, wird ein Bin&auml;rbaum mit pContent als Objekt 
     *und den beiden Teilb&auml;ume pLeftTree und pRightTree erzeugt. Sind  pLeftTree oder pRightTree gleich null, wird der entsprechende Teilbaum 
     *als leerer Bin&auml;rbaum eingef&uuml;gt. Wenn der Parameter pContent gleich null ist, wird ein leerer Bin&auml;rbaum erzeugt.
     *@param pContent  Inhaltsobjekt 
     *@param pLinkerBaum linker Bin&auml;rbaum
     *@param pRechterBaum rechter Bin&auml;rbaum
     */
    public BinaryTree(Object pContent, BinaryTree pLinkerBaum, BinaryTree pRechterBaum){
      if (pContent!=null) {
        lContent = pContent; 
        if (pLinkerBaum!=null)
          lLeftTree = pLinkerBaum;
        else
          lLeftTree = new BinaryTree();
        if (pRechterBaum!=null)
          lRightTree = pRechterBaum;
        else
          lRightTree = new BinaryTree();
       }    
       else {    // da der Inhalt null ist, wird ein leerer Baum erzeugt
         lContent = null;
         lLeftTree = null;
         lRightTree = null;
       } 
     
    }    
    
    /**
     *Diese Anfrage liefert den Wahrheitswert true, wenn der Bin&auml;rbaum leer ist, sonst liefert sie den Wert false.
     *@return true, wenn der Bin&auml;rbaum leer ist, sonst false
     */
    public boolean isEmpty() {
        return (lContent==null);
    } 
    
    /**
     *Wenn der Bin&auml;rbaum leer ist, wird der Parameter pObject als Inhaltsobjekt sowie ein leerer linker und rechter Teilbaum eingef&uuml;gt.
     *Ist der Bin&auml;rbaum nicht leer, wird das Inhaltsobjekt durch pObject ersetzt. Die Teilb&auml;ume werden nicht ge&auml;ndert. 
     *Wenn pObject null ist, bleibt der Bin&auml;rbaum unver&auml;ndert.
     *@param pObject neues Inhaltsobjekt
     */
    public void  setObject(Object pObject) {   
       if (pObject!=null) {
          if (this.isEmpty()) {
            lLeftTree = new BinaryTree();
            lRightTree = new BinaryTree();
          }
          lContent = pObject;
       }
    }
    
    /**
     *Diese Anfrage liefert das Inhaltsobjekt des Bin&auml;rbaums. Wenn der Bin&auml;rbaum leer ist, wird null zur&uuml;ckgegeben.
     *@return Inhaltsobjekt (bzw. null, wenn der Baum leer ist)
     */
    public Object getObject() {
         return lContent;
    }
    
    /**
     *Wenn der Bin&auml;rbaum leer ist, wird pTree nicht angeh&auml;ngt. 
     *Andernfalls erh&auml;lt der Bin&auml;rbaum den &uuml;bergebenen Baum als linken Teilbaum. Falls der Parameter null ist, &auml;ndert sich nichts.
     *@param pTree neuer linker Teilbaum
     */
    public void setLeftTree(BinaryTree pTree) {
        if (!this.isEmpty() && pTree!=null)
            lLeftTree = pTree;
    }
    
    /**
     *Wenn der Bin&auml;rbaum leer ist, wird pTree nicht angeh&auml;ngt. 
     *Andernfalls erh&auml;lt der Bin&auml;rbaum den &uuml;bergebenen Baum als rechten Teilbaum. Falls der Parameter null ist, &auml;ndert sich nichts.
     *@param pTree neuer rechter Teilbaum
     */
    public void setRightTree(BinaryTree pTree) {
        if (! this.isEmpty() && pTree!=null)
            lRightTree = pTree;
    }    
    
    /**
     *Diese Anfrage liefert den linken Teilbaum des Bin&auml;rbaumes. 
     *Der Bin&auml;rbaum &auml;ndert sich nicht. Wenn der Bin&auml;rbaum leer ist, wird null zur&uuml;ckgegeben.
     *@return linker Teilbaum
     */
    public BinaryTree getLeftTree() {
        if (! this.isEmpty())
          return lLeftTree;
        else
          return null;
    }
    
    /**
     *Diese Anfrage liefert den rechten Teilbaum des Bin&auml;rbaumes. 
     *Der Bin&auml;rbaum &auml;ndert sich nicht. Wenn der Bin&auml;rbaum leer ist, wird null zur&uuml;ckgegeben.
     *@return rechter Teilbaum
     */
    public BinaryTree getRightTree() {
        if (! this.isEmpty())
          return lRightTree;
         else
          return null;   
    }  
    
    
    /**
	 * Laedt den Baum als Objekt aus einer Datei - <b>Nicht Bestandteil
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
			BinaryTree bt = (BinaryTree)stream.readObject();
			stream.close();
			 lContent = bt.lContent;
		     lLeftTree = bt.lLeftTree;
		     lRightTree = bt.lRightTree;

		} catch (Exception e) {
			System.out.println("Fehler " + e.toString());
		}

	}

	/**
	 * Speichert den gesamten Baum in einer Datei - <b>Nicht Bestandteil des
	 * Zentralbiturs 2012!!!</b><br>
	 * 
	 * @param fileName
	 *            [Laufwerk][Pfad]Dateiname.
	 */
	public void save(String fileName) {
		try {
			ObjectOutputStream stream = new ObjectOutputStream(
					new FileOutputStream(fileName));
			stream.writeObject(this);
			stream.close();
		} catch (Exception e) { // Fehler beim Speichern der Datei.
			System.out.println("Fehler " + e.toString());
		}
	}

}
