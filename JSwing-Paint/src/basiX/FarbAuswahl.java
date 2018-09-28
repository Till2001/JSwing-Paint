package basiX;

import java.awt.Color;

import javax.swing.JColorChooser;

import basiX.vw.DasFenster;

public class FarbAuswahl extends Komponente {

	/**
	 * erzeugt ein Farbauswahlfenster mit Vorwahlfarbe c, mit linker oberer Ecke
	 * (x,y) vorgegebener Breite b und Hoehe h, das Fenster muss vorher erzeugt
	 * sein
	 */
	public FarbAuswahl(Color c, double x, double y, double b, double h) {
		this(c, x, y, b, h, DasFenster.hauptLeinwand());
	}

	/**
	 * erzeugt ein Farbauswahlfenster mit Vorwahlfarbe c, mit linker oberer Ecke
	 * (10,10) der vorgegebenen Breite 100 und Hoehe 20, ein Fenster muss vorher
	 * erzeugt sein
	 * 
	 */
	public FarbAuswahl(Color c) {
		this(c, 10, 10, 100, 20, DasFenster.hauptLeinwand());
	}

	/**
	 * erzeugt ein Farbauswahlfenster mit Vorwahlfarbe c, mit linker oberer Ecke
	 * (x,y) vorgegebener Breite b und Hoehe h, das Fenster muss vorher erzeugt
	 * sein
	 * 
	 */
	public FarbAuswahl(Color c, double x, double y, double b, double h,
			Fenster f) {
		this(c, x, y, b, h, f.leinwand());
	}

	/**
	 * erzeugt auf einem Containerobjekt le ein Farbauswahlfenster mit
	 * Vorwahlfarbe c, mit linker oberer Ecke (x,y) vorgegebener Breite b und
	 * Hoehe h, der Container muss vorher erzeugt sein
	 * 
	 */
	public FarbAuswahl(Color c, double x, double y, double b, double h,
			Komponente le) {
		super(new JColorChooser(c), x, y, b, h, le);

	}

	/**
	 * erzeugt auf einer Komponente ein BeschriftungsFeld mit Aufschrift s, mit
	 * linker oberer Ecke (0,0) 0 Breite 0 und Hoehe 0, die Komponente muss
	 * vorher erzeugt sein
	 */
	public FarbAuswahl(Komponente k) {
		this(Color.black, 0, 0, 0, 0, k);
	}

	/** verändert die FarbAuswahl auf die Farbe c */
	public void setzeFarbe(Color c) {

	}

	/** liefert die gewählte Farbe */
	public Color getFarbe() {
		return null;
	}

}
