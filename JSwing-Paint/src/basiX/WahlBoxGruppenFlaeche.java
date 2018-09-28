package basiX;

import java.awt.Container;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

import basiX.swing.BufferedCanvas;
import basiX.vw.DasFenster;

public class WahlBoxGruppenFlaeche extends Komponente {
	private WahlBoxGruppe wbg;
	private transient Vector<WahlBoxGruppeLauscher> wahlBoxGruppeLauscher;
	 /**
     * erzeugt auf einem Fenster ein WahlBoxGruppenFlaeche ,
     * mit linker oberer Ecke (x,y) vorgegebener Breite b und Hoehe h, das Fenster muss vorher erzeugt sein
     */
    public WahlBoxGruppenFlaeche(double x, double y, double b, double h) {
        this(x, y, b, h, DasFenster.hauptLeinwand());
    }
    /**
     * erzeugt auf einem Fensterobjekt eine WahlBoxGruppenFlaeche ,
     * mit linker oberer Ecke (10,10) der vorgegebenen Breite 100 und Hoehe 20, ein Fenster muss vorher erzeugt sein
     * 
     */
    public WahlBoxGruppenFlaeche() {
        this(10, 10, 100, 20, DasFenster.hauptLeinwand());
    }
    /**
     * erzeugt auf einem Fenster f eine WahlBoxGruppenFlaeche ,
     * mit linker oberer Ecke (x,y) vorgegebener Breite b und Hoehe h, das Fenster muss vorher erzeugt sein
     * 
     */
    public WahlBoxGruppenFlaeche( double x, double y, double b, double h, Fenster f) {
        this(x, y, b, h, f.leinwand());
    }
    /**
     * erzeugt auf einem Containerobjekt le eine WahlBoxGruppenFlaeche ,
     * mit linker oberer Ecke (x,y) vorgegebener Breite b und Hoehe h, der Container muss vorher erzeugt sein
     * 
     */
    public WahlBoxGruppenFlaeche(double x, double y, double b, double h, Komponente le) {
    	super(new JPanel(null), x, y, b, h, le);
    	wbg = new WahlBoxGruppe();	
    	wbg.setzeWahlBoxGruppeLauscher(new WahlBoxGruppeLauscher(){

			
			public void bearbeiteWahlBoxAuswahl(Object k) {
				WahlBoxGruppenFlaeche.this.fireWahlBoxGruppe(WahlBoxGruppenFlaeche.this);
				
			}
    		
    		
    	} );
        
    }
    /**
     * erzeugt auf einer Komponente eine WahlBoxGruppenFlaeche ,
     * mit linker oberer Ecke (0,0) 0 Breite 0 und Hoehe 0, die Komponente muss vorher erzeugt sein
     */
    public WahlBoxGruppenFlaeche(Komponente k) {
        this( 0, 0, 0, 0, k);
    }
    
    /**
     * Die WahlBox w wird durch die Gruppe logisch verwaltet
     * @param w
     */
    public void registriereWahlBox(WahlBox w){    	
    	this.wbg.fuegeEin(w);
//    	System.out.println(w.holeID());
    }
    
    /**
     * Die WahlBox wird nicht mehr durch die Gruppe verwaltet
     * @param w
     */
    public void hebeRegistrierungAuf(WahlBox w){
    	this.wbg.entferne(w);
    }
    /**
     * 
     * @return liefert die WahlBoxGruppe der Flaeche
     */
	public WahlBoxGruppe liefereWahlBoxGruppe() {
		return wbg;
	}
	
	/**
	 * bestimmt die ausgewählte WahlBox der Gruppe, alle anderen WahlBoxen der
	 * Gruppe werden auf "nicht_ausgewählt" gesetzt
	 */
	public void waehleBox(WahlBox box) {
		wbg.waehleBox(box);
	}
	/** registriert einen WahlBoxgruppelauscher */
	public synchronized void setzeWahlBoxGruppeLauscher(WahlBoxGruppeLauscher l) {
		Vector<WahlBoxGruppeLauscher> v = wahlBoxGruppeLauscher == null ? new Vector<WahlBoxGruppeLauscher>(
				2)
				: (Vector<WahlBoxGruppeLauscher>) wahlBoxGruppeLauscher.clone();
		if (!v.contains(l)) {
			v.addElement(l);
			wahlBoxGruppeLauscher = v;
		}
	}

	/** entfernt einen WahlBoxgruppelauscher */
	public synchronized void entferneWahlBoxGruppeLauscher(WahlBoxLauscher l) {
		if (wahlBoxGruppeLauscher != null && wahlBoxGruppeLauscher.contains(l)) {
			Vector<WahlBoxGruppeLauscher> v = (Vector<WahlBoxGruppeLauscher>) wahlBoxGruppeLauscher
					.clone();
			v.removeElement(l);
			wahlBoxGruppeLauscher = v;
		}
	}
	
	/**
	 */
	protected void fireWahlBoxGruppe(WahlBoxGruppenFlaeche e) {
		if (wahlBoxGruppeLauscher != null) {
			Vector<WahlBoxGruppeLauscher> listeners = wahlBoxGruppeLauscher;
			int count = listeners.size();
			for (int i = 0; i < count; i++)
				(listeners.elementAt(i)).bearbeiteWahlBoxAuswahl(e);
		}
	}
	/**
	 * liefert genau dann den Wert true, wenn seit dem letzten Aufruf der
	 * Anfrage eine andere WahlBox der Gruppe ausgewählt wurde
	 */
	public boolean wurdeGeaendert() {
		return wbg.wurdeGeaendert();
	}

	/** liefert die WahlBox der Gruppe, die ausgewählt ist */
	public WahlBox ausgewaehlteBox() {
		return wbg.ausgewaehlteBox();
	}

	
	public void gibFrei() {
		wbg.gibFrei();
		super.gibFrei();
	}

	/** bettet eine Komponente ein
	 * 
	 */
	public void betteEin(Komponente e) {
		if (e == null || e.elternKomponente==this) {
			return;
		}
		// alte Elternkomponente merken
		Komponente ealt = e.elternKomponente();		
		if (e instanceof WahlBox) {
			// erst einmal sehen, ob sie vorher in einer anderen
			// WahlBoxGruppe war
			if (((WahlBox) e).kenntWahlBoxGruppe != null) {
				((WahlBox) e).entferneWahlBoxGruppe();
			}
			// WahlBox in WahlBoxGruppe der WahlBoxGruppenFlaeche einbinden
			this.registriereWahlBox((WahlBox) e);
		}
		
		meineKomponente.add(e.getSwingComponent());
		e.setzeElternKomponente(this);
		ealt.getSwingComponent().repaint();
		this.getSwingComponent().repaint();
	}

	
	
    
    
   
	

}
