package basiX;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;

import basiX.vw.DasFenster;
/**
 * Eine Grundfläche beschreibt einen rechteckigen Bereich. Um eine Grundfläche verwenden zu
 * können, müssen ein Fenster oder ein Container-Objekt vorhanden sein, auf das
 * die Grundfläche aufgebracht werden kann. Dies geschieht bei der Konstruktion
 * gegebenenfalls mit Hilfe eines Parameters, der eine Kenntbeziehung zu dem
 * Fenster oder Containerobjekt herstellt. 
 * Grundflächen können selbst als Container für Komponenten
 * genommen werden. So kann man beispielsweise die Fensterfläche in Bereiche
 * aufteilen.
 */

public class GrundFlaeche extends Komponente {
	 /**
     * erzeugt auf einem Fenster ein Grundflaeche ,
     * mit linker oberer Ecke (x,y) vorgegebener Breite b und Hoehe h, das Fenster muss vorher erzeugt sein
     */
    public GrundFlaeche(double x, double y, double b, double h) {
        this(x, y, b, h, DasFenster.hauptLeinwand());
    }
    /**
     * erzeugt auf einem Fensterobjekt eine Grundflaeche ,
     * mit linker oberer Ecke (10,10) der vorgegebenen Breite 100 und Hoehe 20, ein Fenster muss vorher erzeugt sein
     * 
     */
    public GrundFlaeche() {
        this(10, 10, 100, 20, DasFenster.hauptLeinwand());
    }
//    /**
//     * erzeugt auf einem Fenster f eine Grundflaeche ,
//     * mit linker oberer Ecke (x,y) vorgegebener Breite b und Hoehe h, das Fenster muss vorher erzeugt sein
//     * 
//     */
//    public GrundFlaeche( double x, double y, double b, double h, Fenster f) {
//        this(x, y, b, h, f.leinwand());
//    }
    /**
     * erzeugt auf einem Containerobjekt le eine Grundflaeche ,
     * mit linker oberer Ecke (x,y) vorgegebener Breite b und Hoehe h, der Container muss vorher erzeugt sein
     * 
     */
    public GrundFlaeche(double x, double y, double b, double h, Komponente le) {
    	super(new JPanel(null), x, y, b, h, le instanceof Fenster? ((Fenster)le).leinwand():le );
    	this.fakeMouseListenerErzeugen();        
    }
    /**
     * erzeugt auf einer Komponente eine Grundflaeche ,
     * mit linker oberer Ecke (0,0) 0 Breite 0 und Hoehe 0, die Komponente muss vorher erzeugt sein
     */
    public GrundFlaeche(Komponente k) {
        this( 0, 0, 0, 0, k);
    }
       

    protected void fakeMouseListenerErzeugen() {
		// wenn kein Mouselistener vorhanden, werden die Mausevents an parent
		// geleitet,
		// damit ist dann die Herkunft nicht mehr identifizierbar
    	// Siehe Bug ID
		// 4413412
		meineKomponente.addMouseListener(new MouseInputAdapter() {
		});
		meineKomponente.addMouseMotionListener(new MouseInputAdapter() {
		});

	}
	
	
	
	
}
