package basiX;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import basiX.vw.*;

/**
 * ListBox-Objekte sind Oberflaechenelemente, die es dem Benutzer ermöglichen
 * einen Eintrag einer Liste mit der Maus oder über die Tastatur auszuwählen. Zur Ereignisbearbeitung:
 * Ob eine Auswahl stattgefunden hat, kann über die Anfrage wurdeGewaehlt ermittelt werden.
 * Alternativ wird jede Auswahl einem angemeldeten ListBoxLauscher signalisiert.
 * siehe: Auftrag setzeListBoxLauscher(ListBoxLauscher l) siehe: Interface ListBoxLauscher
 */
public class ListBox extends Komponente implements  Serializable {
    /**
	 * 
	 */
	
    private boolean wurdeGewaehlt = false;
    private DefaultComboBoxModel  model;
	

	/**
	 
	 */
	private transient Vector listBoxLauscher;

    /**
     * erzeugt auf einem Fenster eine Auswahlliste mit linker oberer Ecke (x,y) vorgegebener Breite b
     * und Hoehe h, ein Fenster muss vorher erzeugt sein
     */
    public ListBox(double x, double y, double b, double h) {
        this(x, y, b, h, DasFenster.hauptLeinwand());
    }

    /**
     * erzeugt auf einem Fensterobjekt eine Auswahlliste mit linker oberer Ecke (10,10) der vorgegebenen Breite 100
     * und Hoehe 100, ein Fenster muss vorher erzeugt sein
     * 
     */
    public ListBox() {
        this(10, 10, 100, 100, DasFenster.hauptLeinwand());
    }

    /**
     * erzeugt auf einem Fenster f eine Auswahlliste mit linker oberer Ecke (x,y) vorgegebener Breite b
     * und Hoehe h, ein Fenster muss vorher erzeugt sein
     *  */ 
    public ListBox(double x,double y,double b,double h, Fenster f){ 
    	this(x,y,b,h,f.leinwand()); 
   }
     /** erzeugt auf einem Containerobjekt le eine Auswahlliste mit linker oberer Ecke (x,y) vorgegebener Breite b
     * und Hoehe h, der Container muss vorher erzeugt sein
     *  
     */
    public ListBox(double x, double y, double b, double h, Komponente le) {
    	super(new JComboBox(),x,y,b,h,le);
        try {
        	model = new DefaultComboBoxModel();
        	((JComboBox)meineKomponente).setModel(model);
            
            ((JComboBox)meineKomponente).addItemListener(new ItemListener(){
            	 public void itemStateChanged(ItemEvent itemevent) {
                     //wurdeGewaehlt = true;
                     //ListBox.this.fireAuswahl(ListBox.this);
                 }
            });
            ((JComboBox)meineKomponente).addActionListener(new ActionListener(){
            	public void actionPerformed(ActionEvent e) {
                    wurdeGewaehlt = true;
                    ListBox.this.fireAuswahl(ListBox.this);
                }
            });
            
            
        } catch (Exception e) {
            System.out.println(" ListBox konnte nicht erstellt werden, da zuvor kein");
            System.out.println(" Fensterobjekt o.a. erstellt wurde. ");
            System.out.println(" Bitte erst dieses erzeugen");
        }
    }
//    private TastenLauscherVerwalter tlv;
//    /** Ereignisbearbeitung registriert einen TastenLauscher */
//	public synchronized void setzeTastenLauscher(TastenLauscher l) {
//		if (tlv == null){
//			tlv= new TastenLauscherVerwalter(((JComboBox)meineKomponente));
//		}
//		tlv.setzeTastenLauscher(l);
//	}
//	/** Ereignisbearbeitung entfernt einen TastenLauscher */
//	public synchronized void entferneTastenLauscher(TastenLauscher l) {
//		if (tlv != null){
//			tlv.entferneTastenLauscher(l);
//		}
//	}
  

    /** wählt das i-te Element (i>=0) */
    public void waehle(int i) {
        try {
        	((JComboBox)meineKomponente).setSelectedIndex(i);
        } catch (Exception e) { }
    }

    /** wählt das i-te Element ab (i>=0) */
    public void waehleAb(int i) {
        try {
            //
        } catch (Exception e) { }
    }

    /** fuegt ein Listenelement unten an die Liste an */
    public void fuegeAn(String s) {
        
    	((JComboBox)meineKomponente).addItem(s);
    }
    /** fuegt ein Listenelement an die Liste an
     * 
     * @param s Listenelement
     */
    public void fuegeAn(char s) {
    	((JComboBox)meineKomponente).addItem(s+"");
    }
    /** fuegt ein Listenelement an i-ter Position (i>=0) in die Liste ein */
    public void fuegeEin(String s, int i) {
        try {
        	((JComboBox)meineKomponente).insertItemAt(s, i);
        } catch (Exception e) { }
    }
    /** fuegt ein Listenelement an i-ter Position (i>=0) in die Liste ein
     */
    public void fuegeEin(char s, int i) {
        try {
        	((JComboBox)meineKomponente).insertItemAt(s+"", i);
        } catch (Exception e) { }
    }
    /** entfernt das i-te Element (i>=0) */
    public void entferne(int i) {
        try {
        	((JComboBox)meineKomponente).removeItemAt(i);
        } catch (Exception e) { }
    }

    /** entfernt das erste gleichlautende Element */
    public void entferne(String s) {
        try {
        	((JComboBox)meineKomponente).removeItem(s);
        } catch (Exception e) { }
    }
     /** entfernt das erste gleichlautende Element
      */
    public void entferne(char s) {
        try {
        	((JComboBox)meineKomponente).removeItem(s+"");
        } catch (Exception e) { }
    }
    /** leert die ListBox */
    public void entferneAlles() {
    	((JComboBox)meineKomponente).removeAllItems();
    }

    /** registriert einen ListBoxLauscher */
    public synchronized void setzeListBoxLauscher(ListBoxLauscher l) {
        Vector v = listBoxLauscher == null ? new Vector(2) : (Vector)listBoxLauscher.clone();
        if (!v.contains(l)) {
            v.addElement(l);
            listBoxLauscher = v;
        }
    }

    /** entfernt einen ListBoxLauscher */
    public synchronized void entferneListBoxLauscher(ListBoxLauscher l) {
        if (listBoxLauscher != null && listBoxLauscher.contains(l)) {
            Vector v = (Vector)listBoxLauscher.clone();
            v.removeElement(l);
            listBoxLauscher = v;
        }
    }

    /** gibt an, ob ein Listenelement der ListBox gewählt wurde */
    public boolean wurdeGewaehlt() {
        boolean merke = wurdeGewaehlt;
        wurdeGewaehlt = false;
        return merke;
    }

    /** liefert den Index des aktuell gewählten Listenelements */
    public int index() {
        return ((JComboBox)meineKomponente).getSelectedIndex();
    }

    /** liefert das aktuell gewählte Listenelement */
    public String gewaehlterText() {
        return (String)((JComboBox)meineKomponente).getSelectedItem();
    }

    /* liefert die Anzahl der Elemente der ListBox */

    public int anzahl() {
        return ((JComboBox)meineKomponente).getItemCount();
    }

//    /** liefert die horizontale Koordinate der linken oberen Ecke */
//    public double hPosition() {
//        return ((JComboBox)meineKomponente).getLocation().x;
//    }

   


    /**  */
    protected void fireAuswahl(ListBox e) {
        if (listBoxLauscher != null) {
            Vector listeners = listBoxLauscher;
            int count = listeners.size();
            for (int i = 0; i < count; i++)
                ((ListBoxLauscher)listeners.elementAt(i)).bearbeiteListBoxAuswahl(e);
        }
    }

    /** 
     * 
     * @return liefert alle Einträge als Feld von Strings
     */
    public String[] inhalte(){
    	String[] in = new String[model.getSize()];
    	for ( int i =0; i<in.length;i++){
    		in[i]=(String)model.getElementAt(i);
    	}
    	return in;
    }
    
    /**
     * Die Inhalte eines String-Feldes werden zu Auswahlen der ListBox
     * @param inhalte
     */
    public void setzeInhalte(String[] inhalte){
//    	System.out.println("ListBox setzeIn");
    	model.removeAllElements();
    	for ( int i =0; i<inhalte.length;i++){
    		model.addElement(inhalte[i]);
    	}
    }
    
}
