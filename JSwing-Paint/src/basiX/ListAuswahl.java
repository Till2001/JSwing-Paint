package basiX;

import java.awt.*;
import java.io.*;
import java.util.*;

import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import basiX.vw.*;

/**
 * ListAuswahl-Objekte sind Oberflaechenelemente, die es dem Benutzer ermöglichen
 * einen Eintrag einer Liste mit der Maus oder über die Tastatur auszuwählen. Zur Ereignisbearbeitung:
 * Ob eine Auswahl stattgefunden hat, kann über die Anfrage wurdeGewaehlt ermittelt werden.
 * Alternativ wird jede Auswahl einem angemeldeten ListAuswahlLauscher signalisiert.
 * siehe: Auftrag setzeListAuswahlLauscher(ListAuswahlLauscher l) siehe: Interface ListAuswahlLauscher
 */
public class ListAuswahl extends Komponente implements  Serializable {
    /**
	 * 
	 */
	
	
    private boolean wurdeGewaehlt = false;
    private DefaultListModel inhalte; 
	
	private transient Vector listAuswahlLauscher;
	private JList meineEingebetteteKomponente;

    /**
     * erzeugt auf einem Fenster eine Auswahlliste mit linker oberer Ecke (x,y) vorgegebener Breite b
     * und Hoehe h, das Fenster muss vorher erzeugt sein
     */
    public ListAuswahl(double x, double y, double b, double h) {
        this(x, y, b, h, DasFenster.hauptLeinwand());
    }

    /**
     * erzeugt auf einem Fensterobjekt eine Auswahlliste mit linker oberer Ecke (10,10) der vorgegebenen Breite 100
     * und Hoehe 100, ein Fenster muss vorher erzeugt sein
     * 
     */
    public ListAuswahl() {
        this(10, 10, 100, 100, DasFenster.hauptLeinwand());
    }

    /**
     * erzeugt auf einem Fenster bi eine Auswahlliste mit linker oberer Ecke (x,y) vorgegebener Breite b
     * und Hoehe h, ein Fenster muss vorher erzeugt sein
     * 
     */
    public ListAuswahl(double x, double y, double b, double h, Fenster f) {
        this(x, y, b, h, f.leinwand());
    }

    /**
     * erzeugt auf einem Containerobjekt le eine Auswahlliste mit linker oberer Ecke (x,y) vorgegebener Breite b
     * und Hoehe h, der Container muss vorher erzeugt sein
     * 
     */
    public ListAuswahl(double x, double y, double b, double h, Komponente le) {
    	super(new JScrollPane(new JList()),x,y,b,h,le);
    	this.meineKomponente.validate();
        try {
            meineEingebetteteKomponente = (JList)((JScrollPane)meineKomponente).getViewport().getComponent(0);       

        	inhalte = new DefaultListModel();
        	((JList)meineEingebetteteKomponente).setModel(inhalte);
        	((JList)meineEingebetteteKomponente).addListSelectionListener(new ListSelectionListener(){
        			public void valueChanged(ListSelectionEvent e) {
        				 //Choice choice = (Choice)itemevent.getItemSelectable();
        			        wurdeGewaehlt = true;
        			        ListAuswahl.this.fireAuswahl(ListAuswahl.this);
        				
        			}}
        	);
            
        } catch (Exception e) {
            System.out.println(" ListAuswahl konnte nicht erstellt werden, da zuvor kein");
            System.out.println(" Fensterobjekt o.a. erstellt wurde. ");
            System.out.println(" Bitte erst dieses erzeugen");
        }
    }
//    private TastenLauscherVerwalter tlv;
//    /** Ereignisbearbeitung registriert einen TastenLauscher */
//	public synchronized void setzeTastenLauscher(TastenLauscher l) {
//		if (tlv == null){
//			tlv= new TastenLauscherVerwalter(((JList)meineKomponente));
//		}
//		tlv.setzeTastenLauscher(l);
//	}
//	/** Ereignisbearbeitung entfernt einen TastenLauscher */
//	public synchronized void entferneTastenLauscher(TastenLauscher l) {
//		if (tlv != null){
//			tlv.entferneTastenLauscher(l);
//		}
//	}
   

    /** wählt das i-te Element */
    public void waehle(int i) {
        try {
        	((JList)meineEingebetteteKomponente).setSelectedIndex(i);
        } catch (Exception e) { }
    }

    /** wählt das Element mit Inhaltstext s */
    public void waehle(String s) {
        try {
        	((JList)meineEingebetteteKomponente).setSelectedValue(s, true);
        } catch (Exception e) { }
    }
    /** wählt das Element mit Inhaltstext s
      */
    public void waehle(char s) {
        try {
        	((JList)meineEingebetteteKomponente).setSelectedValue(s, true);
        } catch (Exception e) { }
    }
    /** fuegt ein Listenelement an die Auswahlliste an */
    public synchronized void fuegeAn(String s) {
    	inhalte.addElement(s);
        this.meineKomponente.validate();
    }
    /** fuegt ein Listenelement an die Auswahlliste an
     */
    public synchronized void  fuegeAn(char s) {
    	inhalte.addElement(s+"");
    	this.meineKomponente.validate();
    }
     
    /** fügt den Text ab einer bestimmten Position ein
   */
    public void fuegeEinBei(char s, int i) {
    	inhalte.add(i, s+"");
    	this.meineKomponente.validate();
    }
    /** fügt den Text ab einer bestimmten Position ein   */
    public void fuegeEinBei(String s, int i) {
    	inhalte.add(i, s);
    	this.meineKomponente.validate();
    }
    /** entfernt das i-te Element (i>=0) */
    public void entferne(int i) {
        try {
        	inhalte.removeElementAt(i);
            this.meineKomponente.validate();
        } catch (Exception e) { }
    }

    /** entfernt das erste gleichlautende Element */
    public void entferne(String s) {
        try {
        	inhalte.removeElement(s);
            this.meineKomponente.validate();
        } catch (Exception e) { }
    }

    /** leert die ListBox */
    public void entferneAlles() {
    	inhalte.removeAllElements();
        this.meineKomponente.validate();
    }

    /** registriert einen Auswahllauscher */
    public synchronized void setzeListAuswahlLauscher(ListAuswahlLauscher l) {
        Vector v = listAuswahlLauscher == null ? new Vector(2) : (Vector)listAuswahlLauscher.clone();
        if (!v.contains(l)) {
            v.addElement(l);
            listAuswahlLauscher = v;
        }
    }

    /** entfernt einen ListAuswahllauscher */
    public synchronized void entferneListAuswahlLauscher(ListAuswahlLauscher l) {
        if (listAuswahlLauscher != null && listAuswahlLauscher.contains(l)) {
            Vector v = (Vector)listAuswahlLauscher.clone();
            v.removeElement(l);
            listAuswahlLauscher = v;
        }
    }

    /** gibt an, ob ein Listenelement der ListAuswahl gewählt wurde */
    public boolean wurdeGewaehlt() {
        boolean merke = wurdeGewaehlt;
        wurdeGewaehlt = false;
        return merke;
    }

    /** liefert den Index des aktuell gewählten Listenelements */
    public int index() {
        return ((JList)meineEingebetteteKomponente).getSelectedIndex();
    }

    /** liefert das aktuell gewählte Listenelement */
    public String gewaehlterText() {
    	return (String)inhalte.get(((JList)meineEingebetteteKomponente).getSelectedIndex());
    }

    /* liefert die Anzahl der Elemente der ListAuswahl */

    public int anzahl() {
    	return inhalte.size();
    }
    /** 
     * 
     * @return liefert alle Einträge als Feld von Strings
     */
    public String[] inhalte(){
    	String[] in = new String[inhalte.getSize()];
    	for ( int i =0; i<in.length;i++){
    		in[i]=(String)inhalte.getElementAt(i);
    	}
    	return in;
    }
    
    /**
     * Die Inhalte eines String-Feldes werden zu Auswahlen der ListAuswahl
     * @param inhalte
     */
    public void setzeInhalte(String[] in){
    	this.inhalte.clear();
    	inhalte = new DefaultListModel();
    	for (int i=0;i<in.length;i++){
    		this.inhalte.addElement(in[i]);
    	}    
    	((JList)meineEingebetteteKomponente).setModel(inhalte);    	
    }

 

    /** */
    protected void fireAuswahl(ListAuswahl e) {
        if (listAuswahlLauscher != null) {
            Vector listeners = listAuswahlLauscher;
            int count = listeners.size();
            for (int i = 0; i < count; i++)
                ((ListAuswahlLauscher)listeners.elementAt(i)).bearbeiteAuswahl(e);
        }
    }

    /** ändert die Hintergrundfarbe der Liste zu farbe */
	public void setzeHintergrundFarbe(Color farbe) {
		if (meineEingebetteteKomponente instanceof JComponent) {
			((JComponent) meineEingebetteteKomponente).setOpaque(true);
		}
		meineEingebetteteKomponente.setBackground(farbe);

	}

	/** setzt die Schriftgröße */
	public void setzeSchriftGroesse(int g) {
		try {
			String name = meineEingebetteteKomponente.getFont().getName();
			int stil = meineEingebetteteKomponente.getFont().getStyle();
			meineEingebetteteKomponente.setFont(new Font(name, stil, g));
			// meineKomponente.getToolkit().sync();
		} catch (Exception e) {
			System.out.println("falsche Schriftgroesse");
		}
	}

	/** ändert die Schriftfarbe des Elementes */
	public void setzeSchriftFarbe(Color farbe) {
		meineEingebetteteKomponente.setForeground(farbe);
	}

	/**
	 * setzt den Schriftstil, vorgegebene Stile sind in Basis: Schrift.FETT oder
	 * Schrift.KURSIV oder Schrift.KURSIVFETT oder Schrift.STANDARDSTIL
	 */
	public void setzeSchriftStil(int stil) {
		try {
			String name = meineEingebetteteKomponente.getFont().getName();
			int groesse = meineEingebetteteKomponente.getFont().getSize();
			meineEingebetteteKomponente.setFont(new Font(name, stil, groesse));
		} catch (Exception e) {
			System.out.println("falscher Schriftstil");
		}
	}

	/**
	 * setzt die Schriftart, vorgegebene Arten sind in basis: Schrift.HELVETICA
	 * oder Schrift.TIMESROMAN oder Schrift.STANDARDSCHRIFTART
	 */
	public void setzeSchriftArt(String a) {
		try {
			int stil = meineEingebetteteKomponente.getFont().getStyle();
			int groesse = meineEingebetteteKomponente.getFont().getSize();
			meineEingebetteteKomponente.setFont(new Font(a, stil, groesse));
		} catch (Exception e) {
			System.out.println("falsche Schriftart");
		}
	}

	

	/**
	 * Schaltet die Funktionstüchtigkeit des Steuerelementes an (Parameterwert
	 * true) oder ab (false)
	 */
	public void setzeBenutzbar(boolean bb) {
		meineEingebetteteKomponente.setEnabled(bb);
	}

	/** setzt den Fokus auf diese Komponente */
	public void setzeFokus() {
		meineEingebetteteKomponente.requestFocus();
	}

	

/**
 * liefert die JList-Komponente
 * wohingegen getSwingComponent die umgebende JScrollPane liefert
 */
	public Container getEmbeddedSwingComponent() {
		return meineEingebetteteKomponente;
	}
	


	
	
}
