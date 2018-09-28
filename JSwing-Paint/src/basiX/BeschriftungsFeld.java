package basiX;
 

import java.io.*;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.event.MouseInputAdapter;

import basiX.vw.*;
/** BeschriftungsFelder sind Oberflaechenelemente für Beschriftungen */

public class BeschriftungsFeld extends Komponente implements  Serializable {
	

	
    /**
	 * 
	 */
	private static final long serialVersionUID = -3705985801596955978L;

	/**
     * erzeugt auf einem Fenster ein BeschriftungsFeld  mit Aufschrift s,
     * mit linker oberer Ecke (x,y) vorgegebener Breite b und Hoehe h, das Fenster muss vorher erzeugt sein
     */
    public BeschriftungsFeld(String s, double x, double y, double b, double h) {
        this(s, x, y, b, h, DasFenster.hauptLeinwand());
    }
    /**
     * erzeugt auf einem Fensterobjekt eine BeschriftungsFeld  mit Aufschrift s,
     * mit linker oberer Ecke (10,10) der vorgegebenen Breite 100 und Hoehe 20, ein Fenster muss vorher erzeugt sein
     * 
     */
    public BeschriftungsFeld(String s) {
        this(s, 10, 10, 100, 20, DasFenster.hauptLeinwand());
    }
    /**
     * erzeugt auf einem Fensterobjekt eine BeschriftungsFeld  mit leerer Aufschrift,
     * mit linker oberer Ecke (10,10) der vorgegebenen Breite 100 und Hoehe 20, ein Fenster muss vorher erzeugt sein
     * 
     */
    public BeschriftungsFeld() {
        this("", 10, 10, 100, 20, DasFenster.hauptLeinwand());
    }
    /**
     * erzeugt auf einem Fenster bi eine BeschriftungsFeld  mit Aufschrift s,
     * mit linker oberer Ecke (x,y) vorgegebener Breite b und Hoehe h, der Fenster muss vorher erzeugt sein
     * 
     */
    public BeschriftungsFeld(String s, double x, double y, double b, double h, Fenster f) {
        this(s, x, y, b, h, f.leinwand());
    }
    /**
     * erzeugt auf einem Containerobjekt le eine BeschriftungsFeld  mit Aufschrift s,
     * mit linker oberer Ecke (x,y) vorgegebener Breite b und Hoehe h, der Container muss vorher erzeugt sein
     * 
     */
    public BeschriftungsFeld(String s, double x, double y, double b, double h, Komponente le) {
    	super(new JLabel(s), x, y, b, h, le);
    	fakeMouseListenerErzeugen();
        
    }
    protected void fakeMouseListenerErzeugen() {
		// wenn kein Mouselistener vorhanden, werden die Mausevents an parent
		// geleitet,
		// damit ist dann die Herkunft nicht mehr identifizierbar// Siehe Bug ID
		// 4413412
		meineKomponente.addMouseListener(new MouseInputAdapter() {
		});
		meineKomponente.addMouseMotionListener(new MouseInputAdapter() {
		});

	}
    /**
     * erzeugt auf einer Komponente ein BeschriftungsFeld  mit Aufschrift s,
     * mit linker oberer Ecke (0,0) 0 Breite 0 und Hoehe 0, die Komponente muss vorher erzeugt sein
     */
    public BeschriftungsFeld(Komponente k) {
        this("", 0, 0, 0, 0, k);
    }
       

    /** verändert die Aufschrift des BeschriftungsFelds */
    public void setzeText(String s) {
        ((JLabel)meineKomponente).setText(s);
    }
   
    /** liefert die Aufschrift */
    public String text() {
        return ((JLabel)meineKomponente).getText();
    }
   
	public void ladeBild(String pfad) {
		try {
			((JLabel)meineKomponente).setIcon(new ImageIcon(getClass().getResource(pfad)));
		} catch (Exception e) {
			//info("Laden des Bildes fehlgeschlagen");
		}
	}
	
	
	
	
 


}
