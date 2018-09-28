package basiX;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import basiX.vw.*;


/**
 * TextBereiche sind Oberflaechenelemente, die es dem Benutzer ermöglichen
 * mehrzeilige Texte einzugeben. Darüberhinaus können sie auch zur
 * Anzeige von Texten genutzt werden. Mit Hilfe der Maus können Textstellen
 * markiert und gelöscht werden. Einstellungen für die Schrift betreffen jeweils
 * den gesamten Text eines TextBereiches.  
 * Zur Ereignisbearbeitung:
 * Ob der Text des Bereiches geändert wurde, kann über die Anfrage textWurdeGeaendert ermittelt werden.
 * Alternativ wird jede Textänderung einem angemeldeten TextBereichLauscher signalisiert.
 * siehe: Auftrag setzeTextBereichLauscher(TextBereichLauscher l) siehe: Interface TextBereichLauscher
 */
public class TextBereichA extends Komponente implements  Serializable{
    /**
	 * 
	 */
	protected Container kenntContainer;
    private boolean wurdeGeaendert = false;
    JTextArea mk;
	

	/**
	 * 
	
	 */
	private transient Vector textBereichLauscher;

    /**
     * erzeugt auf einem Fenster einen Textbereich mit linker oberer Ecke
     * (x,y) vorgegebener Breite und Hoehe, ein Fenster muss vorher erzeugt sein
     */
    public TextBereichA(double x, double y, double breite, double hoehe) {
        this(x, y, breite, hoehe, DasFenster.hauptLeinwand());
    }

    /**
     * erzeugt auf einem Fensterobjekt einen Textbereich mit linker oberer Ecke (10,10 der Breite 100 und der Höhe 100)
     * ein Fenster muss vorher erzeugt sein
     * 
     */
    public TextBereichA() {
        this(10, 10, 100, 100, DasFenster.hauptLeinwand());
    }

    /**
     * erzeugt auf dem  Fenster bi einen Textbereich mit linker oberer Ecke (x,y) vorgegebener Breite
     * und Hoehe, ein Fenster muss vorher erzeugt sein
     * 
     */
//    public TextBereich(double x, double y, double b, double h, Fenster f) {
//        this(x, y, b, h, f.leinwand());
//    }

    /**
     * erzeugt auf einem Containerobjekt le einen Textbereich mit linker oberer Ecke (x,y) vorgegebener Breite
     * und Hoehe, der Container muss vorher erzeugt sein
     * 
     */
    public TextBereichA(double x, double y, double b, double h, Komponente le) {
    	//super(new JScrollPane(new JTextArea()),x,y,b,h,le);
    	super(new JTextArea(),x,y,b,h,le instanceof Fenster ? ((Fenster)le).leinwand():le);
        try {
           //mk = (JTextArea)((JScrollPane)meineKomponente).getComponent(0);
        	mk = (JTextArea)meineKomponente;
        	mk.addCaretListener(new CaretListener(){
            	public void caretUpdate(CaretEvent e) {
           		 wurdeGeaendert = true;
           	        TextBereichA.this.fireTextBereich();
           		
           	}
            });
			
        } catch (Exception e) {
            System.out.println(" TextBereich konnte nicht erstellt werden, da zuvor kein");
            System.out.println(" Fensterobjekt o.a. erstellt wurde. ");
            System.out.println(" Bitte erst dieses erzeugen");
        }
    }
//    private TastenLauscherVerwalter tlv;
//    /** Ereignisbearbeitung registriert einen TastenLauscher */
//	public synchronized void setzeTastenLauscher(TastenLauscher l) {
//		if (tlv == null){
//			tlv= new TastenLauscherVerwalter(((JTextArea)meineKomponente));
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

    /** Schaltet die Editierbarkeit an (Parameterwert true) oder ab (false) */
    public void setzeEditierbar(boolean ed) {
    	mk.setEditable(ed);
    }

    /** legt die Anzahl der Zeichenspalten fest public void setzeSpalten(int i) { setColumns(i); } */

    /** legt die Anzahl der Textzeilen fest public void setzeZeilen(int i) { setRows(i); } */

    /** ersetzt den Inhalt durch den Text des Parameters */
    public void setzeText(String s) {
    	mk.setText(s);
    }
    /**  */
    public void setzeText(char s) {
    	mk.setText(s+"");
    }
    /** fügt Text an den bestehenden Text an
    @deprecated */
    public void fuegeTextAn(String s) {
    	mk.setText(((JTextArea)((JScrollPane)meineKomponente).getComponent(0)).getText() + s);
    }

    /** fügt Text an den bestehenden Text an, auf Wunsch in einer neuen Zeile
    @deprecated */
    public void fuegeTextAn(String s, boolean neueZeile) {
        if (neueZeile) {
            s = "\n" + s;
        }
        mk.setText(mk.getText() + s);
    }

    /** fügt Text an den bestehenden in einer neuen Zeile an */
    public void setzeInNeueZeile(String s) {
    	mk.setText((mk).getText() +"\n" + s);
    	mk.setCaretPosition(mk.getText().length());
    }
    /**  */
    public void setzeInNeueZeile(char s) {
        this.setzeInNeueZeile(s+"");
    }
    /** hängt Text an den bestehenden in der letzten Zeile an */
    public void fuegeAn(String s) {
    	mk.setText(mk.getText() + s);
    	mk.setCaretPosition(mk.getText().length());
    }
    /** hängt Text an den bestehenden in der letzten Zeile an
     */
    public void fuegeAn(char s) {
        this.fuegeAn(s+"");
    }
    /** fügt den Text ab einer bestimmten Position ein
     */
    public void fuegeEinBei(char s, int i) {
        this.fuegeEinBei(s+"", i);
    }
    /** fügt den Text ab einer bestimmten Position ein */
    public void fuegeEinBei(String s, int i) {
    mk.insert(s, i);
    	mk.setCaretPosition(i+s.length());
    }
    /** markiert den gesamten Text */
    public void markiereAlles() {
    	mk.selectAll();
    }

    /** markiert den Text von Position i bis j */
    public void markiere(int i, int j) {
    	mk.requestFocus();
    	mk.select(i, j);
    }

    /** ersetzt den Text zwischen Position i und j mit s */
    public void ersetze(String s, int i, int j) {
    	mk.replaceRange(s, i, j);
    }

    /** registriert einen TextBereichLauscher */
    public synchronized void setzeTextBereichLauscher(TextBereichLauscher l) {
        Vector v = textBereichLauscher == null ? new Vector(2) : (Vector)textBereichLauscher.clone();
        if (!v.contains(l)) {
            v.addElement(l);
            textBereichLauscher = v;
        }
    }

    /** entfernt einen Textbereichlauscher */
    public synchronized void entferneTextBereichLauscher(TextBereichLauscher l) {
        if (textBereichLauscher != null && textBereichLauscher.contains(l)) {
            Vector v = (Vector)textBereichLauscher.clone();
            v.removeElement(l);
            textBereichLauscher = v;
        }
    }

    /** liefert genau dann wahr, wenn der Text seit dem letzten Aufruf dieser Methode geändert wurde. */
    public boolean textWurdeGeaendert() {
        boolean merke = wurdeGeaendert;
        wurdeGeaendert = false;
        return merke;
    }

    /** liefert den Text */
    public String text() {
        return mk.getText();
    }

    /** liefert den markierten Inhalt */
    public String markierterText() {
        return mk.getSelectedText();
    }

   
	/** Öffnet Dateidialog zum Öffnen einer Textdatei. Diese wird eingelesen und
		 *  im Textbereich angezeigt
		 */ 
	public boolean ladeText(){
		 StringBuffer inhalt;
		 try{	
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showOpenDialog( null );		
			File f = fc.getSelectedFile();
			FileReader reader = new FileReader(f);
			int c;
			c = reader.read();
			inhalt = new StringBuffer(1000);
			
			while (c != -1){
			 inhalt.append((char) c);
			 c = reader.read();
			}
			this.setzeText(inhalt.toString());
			reader.close();
		 }	catch (Exception exc){
						return false;
		 }
		 return true;	
	}
	/** Lädt die vorgegebene Textdateiin den Textbereich 
	 */ 
		public boolean ladeText(String dateiname){
			StringBuffer inhalt;
			 try{	
				File f = new File(dateiname );
				FileReader reader = new FileReader(f);
				int c;
				c = reader.read();
				inhalt = new StringBuffer(1000);
				while (c != -1){
				 inhalt.append((char) c);
				 c = reader.read();
				}
				this.setzeText(inhalt.toString());
				reader.close();
			 }	catch (Exception exc){
							return false;
			 }
			 return true;	
		}
	
	/** speichert den Text des Bereichs in eine Datei */
	public boolean speichereText(){
			 try {	
				JFileChooser fc = new JFileChooser();
				int returnVal = fc.showSaveDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					//System.out.println(file.getPath()+" "+file.getName());
					return this.speichereText(file.getPath());
				} else {
					return false;	
				}
			 } catch (Exception e){
				return false;
			 }
	}
	/** speichert den Text des Bereichs in eine Datei */
		public boolean speichereText(String dateiname){
			FileWriter fw = null;

				 try
				 {
					 fw = new FileWriter( dateiname );  
					 fw.write(this.text());
				 }
				 catch ( IOException e ) {
					return false;
				 }
				 finally {
					 try {
						 if ( fw != null ) fw.close();
					 } catch ( IOException e ) {
						return false;
					}
				 }
				 return true;
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
    protected void fireTextBereich() {
        if (textBereichLauscher != null) {
            Vector listeners = textBereichLauscher;
            int count = listeners.size();
            for (int i = 0; i < count; i++)
                ((TextBereichLauscher)listeners.elementAt(i)).bearbeiteTextBereichVeraenderung(this);
        }
    }

  
	
	
}
