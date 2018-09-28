package basiX;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import basiX.vw.DasFenster;

/**
 * Eine Grundfläche beschreibt einen rechteckigen Bereich. Um eine Grundfläche verwenden zu
 * können, müssen ein Fenster oder ein Container-Objekt vorhanden sein, auf das
 * die Grundfläche aufgebracht werden kann. Dies geschieht bei der Konstruktion
 * gegebenenfalls mit Hilfe eines Parameters, der eine Kenntbeziehung zu dem
 * Fenster oder Containerobjekt herstellt. 
 * Grundflächen können selbst als Container für Komponenten
 * genommen werden. So kann man beispielsweise die Fensterfläche in unabhängige Bereiche
 * aufteilen. 
 */
public class SplitFlaeche extends Komponente {

		public final static int HORIZONTAL = JSplitPane.HORIZONTAL_SPLIT;
		public final static int VERTIKAL = JSplitPane.VERTICAL_SPLIT;
		 /**
	     * erzeugt auf einem Fenster ein SplitFlaeche ,
	     * mit linker oberer Ecke (x,y) vorgegebener Breite b und Hoehe h, das Fenster muss vorher erzeugt sein
	     */
	    public SplitFlaeche(double x, double y, double b, double h) {
	        this(x, y, b, h, DasFenster.hauptLeinwand());
	    }
	    /**
	     * erzeugt auf einem Fensterobjekt eine SplitFlaeche ,
	     * mit linker oberer Ecke (10,10) der vorgegebenen Breite 100 und Hoehe 20, ein Fenster muss vorher erzeugt sein
	     * 
	     */
	    public SplitFlaeche() {
	        this(10, 10, 100, 20, DasFenster.hauptLeinwand());
	    }
	    
	    /**
	     * erzeugt auf einem Fenster f eine SplitFlaeche ,
	     * mit linker oberer Ecke (x,y) vorgegebener Breite b und Hoehe h, das Fenster muss vorher erzeugt sein
	     * 
	     */
	    public SplitFlaeche( double x, double y, double b, double h, Fenster f) {
	        this(x, y, b, h, f.leinwand());
	    }
	    
	    /**
	     * erzeugt auf einem Containerobjekt le eine SplitFlaeche ,
	     * mit linker oberer Ecke (x,y) vorgegebener Breite b und Hoehe h, der Container muss vorher erzeugt sein
	     * 
	     */
	    public SplitFlaeche(double x, double y, double b, double h, Komponente le) {
	    	super(new JSplitPane(), x, y, b, h, le);
	    }
	    
	    /**
	     * erzeugt auf einer Komponente eine SplitFlaeche,
	     * mit linker oberer Ecke (0,0) 0 Breite 0 und Hoehe 0, die Komponente muss vorher erzeugt sein
	     */
	    public SplitFlaeche(Komponente k) {
	        this( 0, 0, 0, 0, k);
	    }
	       

	       /**
	        * lädt ein Bild in die SplitFlaeche
	        * @param pfad
	        */
		public void ladeBild(String pfad) {
			try {
				((JLabel)meineKomponente).setIcon(new ImageIcon(getClass().getResource(pfad)));
			} catch (Exception e) {
				//info("Laden des Bildes fehlgeschlagen");
			}
		}
		public void setzeLinkeKomponente(Komponente links){
			((JSplitPane)this.getSwingComponent()).setLeftComponent(links.getSwingComponent());
		}
		public void setzeRechteKomponente(Komponente rechts){
			((JSplitPane)this.getSwingComponent()).setRightComponent(rechts.getSwingComponent());
		}
		public void setzeObereKomponente(Komponente oben){
			((JSplitPane)this.getSwingComponent()).setTopComponent(oben.getSwingComponent());
		}
		public void setzeUntereKomponente(Komponente unten){
			((JSplitPane)this.getSwingComponent()).setBottomComponent(unten.getSwingComponent());
		}
		public void setzeTeilungsStrich(int t){
			((JSplitPane)this.getSwingComponent()).setDividerLocation(t);
		}
		public void setzeOrientierung(int orientierung){			
			((JSplitPane)this.getSwingComponent()).setOrientation(orientierung);
		}

		
		
	}