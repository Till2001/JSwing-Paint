package basiX.adt;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


/**
 * <p>Materialien zu den zentralen
 * Abiturpruefungen im Fach Informatik ab 2012 in 
 * Nordrhein-Westfalen.</p>
 * <p>Klasse BinarySearchTree</p>
 * <p>In einem Objekt der Klasse BinarySearchTree werden beliebig viele Objekte in einem Bin&auml;rbaum (bin&auml;rer Suchbaum) 
 * entsprechend einer Ordnungsrelation verwaltet. Ein Objekt der Klasse stellt entweder einen leeren Baum dar oder 
 * verwaltet ein Inhaltsobjekt sowie einen linken und einen rechten Teilbaum, die ebenfalls Objekte der Klasse BinarySearchTree sind. 
 * Dabei gilt:
 * Die Inhaltsobjekte sind Objekte einer Unterklasse von Item, in der durch &uuml;berschreiben der 
 * drei Vergleichsmethoden isLess, isEqual, isGreater (s. Item) eine eindeutige Ordnungsrelation festgelegt sein muss.
 * Alle Objekte im linken Teilbaum sind kleiner als das Inhaltsobjekt des Bin&auml;rbaumes. 
 * Alle Objekte im rechten Teilbaum sind gr&ouml;sser als das Inhaltsobjekt des Bin&auml;rbaumes. 
 * Diese Bedingung gilt auch in beiden Teilb&auml;umen. 

</p>
 * 
 * <p>NW-Arbeitsgruppe: Materialentwicklung zum Zentralabitur 
 * im Fach Informatik</p>
 * 
 * @version 2010-10-20
 * &Uuml;berarbeitet Georg Dick 2013-1-9 und BeA 2013-2-13
 */



public class BinarySearchTree implements Serializable{

	private InnerBinaryTree bintree;

	/**
	 * Der Konstruktor erzeugt einen leeren Suchbaum.
	 */
	public BinarySearchTree() {
		bintree = new InnerBinaryTree();
	}

	/**
	 * Diese Anfrage liefert den Wahrheitswert true, wenn der Suchbaum leer ist,
	 * sonst liefert sie den Wert false.
	 * 
	 * @return true, wenn der bin&auml;re Suchbaum leer ist, sonst false
	 */
	public boolean isEmpty() {
		return bintree.isEmpty();
	}

	/**
	 * Falls ein bez&uuml;glich der verwendeten Vergleichsmethode isEqual mit
	 * pItem &uuml;bereinstimmendes Objekt im geordneten Baum enthalten ist,
	 * passiert nichts. Andernfalls wird das Objekt pItem entsprechend der
	 * vorgegebenen Ordnungsrelation in den Baum eingeordnet. Falls der
	 * Parameter null ist, &auml;ndert sich nichts.
	 * 
	 * @param pItem
	 *            einzuf&uuml;gendes Objekt
	 */
	public void insert(Item pItem) {
		if (pItem != null) {
			if (bintree.isEmpty()) { // wenn der Suchbaum leer ist, dann wird
										// der Suchbaum mit dem Inhalt pItem
										// gef&uuml;llt
				bintree.setItem(pItem);				
			} else {
				Item lItem = bintree.getItem();
				if (pItem.isLess(lItem)) { // links Einf&uuml;gen
					this.getLeftTree().insert(pItem);
				} else if (pItem.isGreater(lItem)) { 
					// rechts Einf&uuml;gen;	
					// bei Gleichheit wird nicht noch einmal eingef&uuml;gt
					this.getRightTree().insert(pItem);
				}
			}
		}
	}

	/**
	 * Falls ein bez&uuml;glich der verwendeten Vergleichsmethode isEqual mit
	 * pItem &uuml;bereinstimmendes Objekt im bin&auml;ren Suchbaum enthalten
	 * ist, liefert die Anfrage dieses, ansonsten wird null zur&uuml;ckgegeben.
	 * Falls der Parameter null ist, wird null zur&uuml;ckgegeben.
	 * 
	 * @param pItem
	 *            zu suchendes Objekt
	 * @return das gefundene Objekt, bei erfolgloser Suche null
	 */
	public Item search(Item pItem) {
		if (bintree.isEmpty() || pItem == null) // Suche war erfolglos oder es
												// gab nichts zum Suchen
			return null;
		else {
			Item lItem = (Item) bintree.getItem();
			if (pItem.isLess(lItem)) // links suchen
				return this.getLeftTree().search(pItem);
			else if (pItem.isGreater(lItem)) // rechts suchen
				return this.getRightTree().search(pItem);
			else
				return lItem; // gefunden
		}
	}

	

	/**
	 * Falls ein bez&uuml;glich der verwendeten Vergleichsmethode isEqual mit
	 * pItem &uuml;bereinstimmendes Objekt im bin&auml;ren Suchbaum enthalten
	 * ist, wird dieses entfernt. Falls der Parameter null ist, &auml;ndert sich
	 * nichts.
	 * 
	 * @param pItem
	 *            zu entfernendes Objekt
	 */
	public void remove(Item pItem) {		
		if (this.isEmpty() || pItem == null) {
			return;
		}
		
        Item lWurzelInhalt;
        InnerBinaryTree lKnoten, lGroessterLinkerKnoten;
        InnerBinaryTree gefunden;
		lWurzelInhalt = (Item) bintree.getItem();
		if (lWurzelInhalt.isEqual(pItem)) {
			if (bintree.getRight().isEmpty()
					&& bintree.getLeft().isEmpty()) {
				// Blatt loeschen
				bintree.setItem(null);
				bintree.setLeft(null);
				bintree.setRight(null);
				return;
			}
			// kein Blatt loeschen
			if (bintree.getRight().isEmpty()) {
				// rechter Teilbaum leer
				lKnoten = bintree.getLeft();
				bintree.setItem(lKnoten.getItem());
				bintree.setLeft(lKnoten.getLeft());
				bintree.setRight(lKnoten.getRight());
				return;
			}
			if (bintree.getLeft().isEmpty()) {
				lKnoten = bintree.getRight();
				bintree.setItem(lKnoten.getItem());
				bintree.setLeft(lKnoten.getLeft());
				bintree.setRight(lKnoten.getRight());
				return;
			}
			// beide Teilb&auml;ume links und rechts sind nicht
			// leer
			lGroessterLinkerKnoten = bintree.getLeft();
			while (!lGroessterLinkerKnoten.getRight().isEmpty()) {
				lGroessterLinkerKnoten = lGroessterLinkerKnoten.getRight();
			}
			bintree.setItem(lGroessterLinkerKnoten.getItem());
			// BinarySearchTree lLinkerBaum=this.getLeftTree();
			this.getLeftTree()
					.remove((Item) lGroessterLinkerKnoten.getItem());
			return;

		} else {
			if (lWurzelInhalt.isLess(pItem)) { // rekursiv l&ouml;schen im
												// rechten Teilbaum
				BinarySearchTree lRechterBaum = this.getRightTree();
				lRechterBaum.remove(pItem);
			} else { // rekursiv l&ouml;schen im linken Teilbaum
				BinarySearchTree lLinkerBaum = this.getLeftTree();
				lLinkerBaum.remove(pItem);
			}
		}

	}

	/**
	 * Diese Anfrage liefert das Inhaltsobjekt des Suchbaumes. Wenn der Suchbaum
	 * leer ist, wird null zur&uuml;ckgegeben.
	 * 
	 * @return das Inhaltsobjekt bzw. null, wenn der Suchbaum leer ist.
	 */
	public Item getItem() {
		if (this.isEmpty())
			return null;
		else
			return bintree.getItem();
	}

	/**
	 * Diese Anfrage liefert den linken Teilbaum des bin&auml;ren Suchbaumes.
	 * Der bin&auml;re Suchbaum &auml;ndert sich nicht. Wenn er leer ist, wird
	 * null zur&uuml;ckgegeben.
	 * 
	 * @return den linken Teilbaum bzw. null, wenn der Suchbaum leer ist.
	 */
	public BinarySearchTree getLeftTree() {
		if (this.isEmpty()) {
			return null;
		} else {
			BinarySearchTree lTree = new BinarySearchTree(); // der linke
																// Teilbaum muss
																// ein Baum der
																// Klasse
																// BinarySearchTree
																// sein
			lTree.bintree = bintree.getLeft();
			return lTree;
		}
	}

	/**
	 * Diese Anfrage liefert den rechten Teilbaum des bin&auml;ren Suchbaumes.
	 * Der bin&auml;re Suchbaum &auml;ndert sich nicht. Wenn er leer ist, wird
	 * null zur&uuml;ckgegeben.
	 * 
	 * @return den rechten Teilbaum bzw. null, wenn der Suchbaum leer ist.
	 */
	public BinarySearchTree getRightTree() {
		if (this.isEmpty()){
			return null;
		} else {
			BinarySearchTree lTree = new BinarySearchTree(); // der rechte
																// Teilbaum muss
																// ein Baum der
																// Klasse
																// BinarySearchTree
																// sein
			lTree.bintree = bintree.getRight();
			return lTree;
		}
	}
	
	public void load(String pPfad){
		try {
			bintree.load(pPfad);
		} catch (RuntimeException e) {
			// TODO Automatisch erstellter Catch-Block
			e.printStackTrace();
		}
	}
	public void load(){
		try {
			bintree.load("Sicherung");
		} catch (RuntimeException e) {
			// TODO Automatisch erstellter Catch-Block
			e.printStackTrace();
		}
	}
	public void save(String pPfad){
		try {
			bintree.save(pPfad);
		} catch (RuntimeException e) {
			// TODO Automatisch erstellter Catch-Block
			e.printStackTrace();
		}
	}
	
	public void save(){
		try {
			bintree.save("Sicherung");
		} catch (RuntimeException e) {
			// TODO Automatisch erstellter Catch-Block
			e.printStackTrace();
		}
	}
	
	class InnerBinaryTree implements java.io.Serializable{
	    private Item item;
	    private InnerBinaryTree left, right;
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
				InnerBinaryTree ibt = (InnerBinaryTree)stream.readObject();
				stream.close();
				 item = ibt.item;
			     left = ibt.left;
			     right = ibt.right;

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
		
	    public InnerBinaryTree() {
	        item = null;
	        left = null;
	        right = null;
	    }   	    
	   
	    public InnerBinaryTree(Item pContent) {
	        if (pContent!=null) {
	          item = pContent;
	          left = new InnerBinaryTree();
	          right = new InnerBinaryTree();
	        }
	        else {
	          item = null;
	          left = null;
	          right = null;
	        }   
	    }        
	    
	      
	    public boolean isEmpty() {
	        return (item==null);
	    } 	    
	    
	    public void  setItem(Item pItem) {         
	          if (this.isEmpty() && pItem!=null) {
	            left = new InnerBinaryTree();
	            right = new InnerBinaryTree();
	          }
	          item = pItem;
	       }


		
		public Item getItem() {			
			return item;
		}

		public InnerBinaryTree getLeft() {
			return left;
		}

		public InnerBinaryTree getRight() {
			return right;
		}


		public void setLeft(InnerBinaryTree left) {
			this.left = left;			
		}

		
		public void setRight(InnerBinaryTree right) {
			this.right = right;			
		}
	   
	}
	
}
