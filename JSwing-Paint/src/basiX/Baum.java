/**
 * 
 */
package basiX;

import javax.swing.JTree;

import basiX.vw.DasFenster;

/**
 * @author Georg
 *
 */
public class Baum extends Komponente {	

	/**
	 * @param x
	 * @param y
	 * @param breite
	 * @param hoehe
	 */
	public Baum(double x, double y, double breite, double hoehe) {
		this(x,y,breite,hoehe,DasFenster.hauptLeinwand());
	}

	

	/**
	 * @param x
	 * @param y
	 * @param b
	 * @param h
	 * @param f
	 */
	public Baum(double x, double y, double b, double h, Komponente f) {
		super(new JTree(), x, y, b, h, f instanceof Fenster ? ((Fenster)f).leinwand():f);
	}
/**
 * 
 */
	public Baum() {
		this(0,0,10,10);
	}

}
