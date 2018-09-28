package basiX;

import java.awt.*;
import java.util.*;
import java.awt.event.*;

import javax.swing.JCheckBox;
import javax.swing.JRadioButton;

import basiX.vw.*;
/**
 * WahlBoxen sind Oberflaechenelemente, die es dem Benutzer ermöglichen
 * eine Auswahl zwischen zwei Alternativen einzustellen. Dazu klickt er mit der
 * Maus ein Auswahlkästchen an. Die WahlBox enthält zudem einen erläuternden einzeiligen Hinweistext. Zur Ereignisbearbeitung:
 * Ob der Zustand der WahlBox geändert wurde, kann über die Anfrage wurdeGeaendert ermittelt werden.
 * Alternativ wird jede Zustandsänderung einem angemeldeten WahlBoxLauscher signalisiert.
 * siehe: Auftrag setzeWahlBoxLauscher(WahlBoxLauscher l) siehe: Interface WahlBoxLauscher
 */
public class WahlBox extends Komponente  {
    /**
	 * 
	 */
	
	protected Container kenntContainer;
	protected WahlBoxGruppe kenntWahlBoxGruppe=null;
    public WahlBoxGruppe getWahlBoxGruppe() {
		return kenntWahlBoxGruppe;
	}
    
    /** setzt die WahlBox in eine neue Gruppe 
     * 
     * @param neu
     */
    public void setzeWahlBoxGruppe(WahlBoxGruppe neu) {    	
    	if (this.kenntWahlBoxGruppe!=neu){
    		this.kenntWahlBoxGruppe=neu;// Verhinderung von Endlosrekursion
    		neu.fuegeEin(this);    		
    	}		
	}
    
    /** entfernt die WahlBox aus ihrer Gruppe
     * 
     
     */
    public void entferneWahlBoxGruppe(){
    	if (this.kenntWahlBoxGruppe != null){
    		WahlBoxGruppe merke = this.kenntWahlBoxGruppe;// Verhinderung von Endlosrekursion
    		this.kenntWahlBoxGruppe=null;
    		merke.entferne(this);
    	}
    }
    
	private boolean wurdeGeaendert = false;
	
	
	private transient Vector WahlBoxLauscher;
	private int nummer = 0;
	/* liefert die Nummer als ID für WahlBoxGruppe*/
	    public int holeID(){
	        return nummer;
	    }
	/* setzt nummer  */
	    public void setzeID(int pNummer){
	        nummer = pNummer;
	    } 
    /**
     * erzeugt auf einem Fenster eine WahlBox mit Aufschrift s, mit linker oberer Ecke (x,y) vorgegebener Breite b
     * und Hoehe h, das Fenster muss vorher erzeugt sein die WahlBox zeigt den Zustand: nicht ausgewählt
     */
    public WahlBox(String s, double x, double y, double b, double h) {
        this(s, x, y, b, h, DasFenster.hauptLeinwand());
    }

    /**
     * erzeugt auf einem Fenster eine WahlBox mit leerer Aufschrift , mit linker oberer Ecke (10,10) vorgegebener Breite 100
     * und Hoehe 20, das Fenster muss vorher erzeugt sein die WahlBox zeigt den Zustand: nicht ausgewählt
     * 
     */
    public WahlBox() {
        this("", 10, 10, 100, 20, DasFenster.hauptLeinwand());
    }
    /**
     * erzeugt auf einem Fenster eine WahlBox mit Aufschrift s, mit linker oberer Ecke (10,10) vorgegebener Breite 100
     * und Hoehe 20, das Fenster muss vorher erzeugt sein die WahlBox zeigt den Zustand: nicht ausgewählt
     * 
     */
    public WahlBox(String s) {
        this(s, 10, 10, 100, 20, DasFenster.hauptLeinwand());
    }
    /**
     * erzeugt auf dem Fenster f eine WahlBox mit Aufschrift s, mit linker oberer Ecke (x,y) vorgegebener Breite b
     * und Hoehe h, das Fenster muss bi vorher erzeugt sein die WahlBox zeigt den Zustand: nicht ausgewählt
     * 
     */
    public WahlBox(String s, double x, double y, double b, double h, Fenster f) {
        this(s, x, y, b, h, f.leinwand());
    }

    /**
     * erzeugt auf einem Containerobjekt le eine WahlBox mit Aufschrift s, mit linker oberer Ecke (x,y) vorgegebener Breite b
     * und Hoehe h, das Containerobjekt muss vorher erzeugt sein die WahlBox zeigt den Zustand: nicht ausgewählt
     * 
     */
    public WahlBox(String s, double x, double y, double b, double h, Komponente le) {
        super(new JRadioButton(s),x,y,b,h,le);
        try {
            
            ((JRadioButton)meineKomponente).setSelected(false);
            ((JRadioButton)meineKomponente).addActionListener(new ActionListener(){
            	
            	public void actionPerformed(ActionEvent e) {
            		wurdeGeaendert = true;
                    WahlBox.this.fireWahlBox(WahlBox.this);
            		
            	}
            });
            ((JRadioButton)meineKomponente).addKeyListener(new KeyAdapter(){
            	
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        //System.out.println("Enter");
                        e.setKeyCode(KeyEvent.VK_SPACE);
                        ((JRadioButton)meineKomponente).setSelected(!((JRadioButton)meineKomponente).isSelected());
                        wurdeGeaendert = true;
                        WahlBox.this.fireWahlBox(WahlBox.this);
                    }
                }

            });
        } catch (Exception e) {
            System.out.println(" WahlBox konnte nicht erstellt werden");
        }
    }
//    private TastenLauscherVerwalter tlv;
//    /** Ereignisbearbeitung registriert einen TastenLauscher */
//	public synchronized void setzeTastenLauscher(TastenLauscher l) {
//		if (tlv == null){
//			tlv= new TastenLauscherVerwalter(((JRadioButton)meineKomponente));
//		}
//		tlv.setzeTastenLauscher(l);
//	}
//	/** Ereignisbearbeitung entfernt einen TastenLauscher */
//	public synchronized void entferneTastenLauscher(TastenLauscher l) {
//		if (tlv != null){
//			tlv.entferneTastenLauscher(l);
//		}
//	}
//   
//   

    /** setzt den Zustand der WahlBox */
    public void setzeZustand(boolean ein) {
    	((JRadioButton)meineKomponente).setSelected(ein);
    }

    /** Schaltet die Funktionstüchtigkeit des Steuerelemntes an (Parameterwert true) oder ab (false) */
    public void setzeBenutzbar(boolean bb) {
    	((JRadioButton)meineKomponente).setEnabled(bb);
    }

   

    /** legt die Beschriftung der WahlBox fest */
    public void setzeText(String s) {
    	((JRadioButton)meineKomponente).setText(s);
    }
    public String text(){
    	return ((JRadioButton)meineKomponente).getText();
    }

    /** registriert einen WahlBoxlauscher */
    public synchronized void setzeWahlBoxLauscher(WahlBoxLauscher l) {
        Vector v = WahlBoxLauscher == null ? new Vector(2) : (Vector)WahlBoxLauscher.clone();
        if (!v.contains(l)) {
            v.addElement(l);
            WahlBoxLauscher = v;
        }
    }

    /** entfernt einen WahlBoxlauscher */
    public synchronized void entferneWahlBoxLauscher(WahlBoxLauscher l) {
        if (WahlBoxLauscher != null && WahlBoxLauscher.contains(l)) {
            Vector v = (Vector)WahlBoxLauscher.clone();
            v.removeElement(l);
            WahlBoxLauscher = v;
        }
    }

    /** liefert genau dann den Wert true, wenn seit der letzten Anfrage der Zustand der WahlBox geändert wurde */
    public boolean wurdeGeaendert() {
        boolean merke = wurdeGeaendert;
        wurdeGeaendert = false;
        return merke;
    }

    /** liefert den Zustand der WahlBox */
    public boolean istGewaehlt() {
        return ((JRadioButton)meineKomponente).isSelected();
    }

    

 /*
  void writeObject(ObjectOutputStream oos) throws IOException {
    oos.defaultWriteObject();
  }

  void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
    ois.defaultReadObject();
  }
   */

    /**  */
    protected void fireWahlBox(WahlBox e) {
//    	System.out.print("fireWahlbox");
        if (WahlBoxLauscher != null) {
            Vector listeners = WahlBoxLauscher;
            int count = listeners.size();
            for (int i = 0; i < count; i++)
                ((WahlBoxLauscher)listeners.elementAt(i)).bearbeiteWahlBoxAenderung(e);
        }
    }

	public void merkeWahlBoxGruppe(WahlBoxGruppe wahlBoxGruppe) {
		// TODO Auto-generated method stub
		
	}

  

   
}
