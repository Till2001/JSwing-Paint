package basiX;

import java.awt.Color;
import java.awt.Container;
import java.awt.ScrollPane;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

import basiX.swing.JFrameMitKenntFenster;
import basiX.vw.DasFenster;

public class ScrollFlaeche extends Komponente {

    /**
     * erzeugt auf einem Fenster ein BeschriftungsFeld  mit Aufschrift s,
     * mit linker oberer Ecke (x,y) vorgegebener Breite b und Hoehe h, das Fenster muss vorher erzeugt sein
     */
    public ScrollFlaeche( double x, double y, double b, double h) {
        this( x, y, b, h, DasFenster.hauptLeinwand());
    }
    /**
     * erzeugt auf einem Fensterobjekt eine BeschriftungsFeld  mit Aufschrift s,
     * mit linker oberer Ecke (10,10) der vorgegebenen Breite 100 und Hoehe 20, ein Fenster muss vorher erzeugt sein
     * 
     */
    public ScrollFlaeche() {
        this(10, 10, 100, 20, DasFenster.hauptLeinwand());
    }
    /**
     * erzeugt auf einem Fenster bi eine BeschriftungsFeld  mit Aufschrift s,
     * mit linker oberer Ecke (x,y) vorgegebener Breite b und Hoehe h, der Fenster muss vorher erzeugt sein
     * 
     */
//    public ScrollFlaeche( double x, double y, double b, double h, Fenster f) {
//        this(x, y, b, h, f.leinwand());
//    }
    /**
     * erzeugt auf einem Containerobjekt le eine BeschriftungsFeld  mit Aufschrift s,
     * mit linker oberer Ecke (x,y) vorgegebener Breite b und Hoehe h, der Container muss vorher erzeugt sein
     * 
     */
    public ScrollFlaeche( double x, double y, double b, double h, Komponente le) {
    	super(new JScrollPane(), x, y, b, h, le instanceof Fenster ? ((Fenster)le).leinwand():le);
    	((JScrollPane)meineKomponente).setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    	((JScrollPane)meineKomponente).setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
    }
    /**
     * erzeugt auf einem Containerobjekt le eine BeschriftungsFeld  mit Aufschrift s,
     * mit linker oberer Ecke (x,y) vorgegebener Breite b und Hoehe h, der Container muss vorher erzeugt sein
     * 
     */
    public ScrollFlaeche( Komponente k,double x, double y, double b, double h, Komponente le) {
    	super(new JScrollPane(k.meineKomponente), x, y, b, h, le instanceof Fenster ? ((Fenster)le).leinwand():le);
    	((JScrollPane)meineKomponente).setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    	((JScrollPane)meineKomponente).setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
    }
    /**
     * erzeugt auf einer Komponente ein BeschriftungsFeld  mit Aufschrift s,
     * mit linker oberer Ecke (0,0) 0 Breite 0 und Hoehe 0, die Komponente muss vorher erzeugt sein
     */
    public ScrollFlaeche(Komponente k) {
        this( 0, 0, 0, 0, k);
    }
       
    public void schalteLayoutAus(){
    	((JScrollPane)this.getSwingComponent()).getViewport().setLayout(null);
    }
    
	@Override
	public Color hintergrundFarbe() {
		return ((JScrollPane)this.getSwingComponent()).getViewport().getBackground();
	}
	
	@Override
	public void setzeHintergrundFarbe(Color farbe) {
		((JScrollPane)this.getSwingComponent()).getViewport().setBackground(farbe);
	}
	
    @Override
	public void betteEin(Komponente e) {
		if (e == null || e.elternKomponente==this) {
			return;
		}
		// alte Elternkomponente merken
		Komponente ealt = e.elternKomponente();
		JViewport jw = ((JScrollPane) meineKomponente).getViewport();
		jw.add(e.getSwingComponent());
		((JScrollPane) meineKomponente).setViewport(jw);
		meineKomponente.validate();
		meineKomponente.repaint();		
		e.setzeElternKomponente(this);
		ealt.getSwingComponent().repaint();
	}

	
	
	
	
	
}
