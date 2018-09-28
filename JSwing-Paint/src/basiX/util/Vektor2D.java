package basiX.util;

import basiX.Hilfe;

/**
 * Die Klasse repräsentiert einen Vektor, der Bewegungen im
 * Basis-Koordinatensystem kartesisch oder polar beschreibt. Richtungen sind
 * Winkel: 0 Grad: nach rechts, 90 Grad: nach oben usw. dx und dy sind
 * Translationen längs der Achsen: dx positiv nach rechts dy positiv <b> nach
 * unten </b> Ändert oder setzte man einen Wert, so werden die jeweils anderen
 * angepasst. Setzt man beispielsweise dx und dy, so werden richtung und
 * schrittlänge angepasst.
 * 
 * 
 * @author Georg
 * 
 */
public class Vektor2D {

	private double dx = 0;
	private double dy = 0;
	private double richtung = 0;
	private double laenge = 0;

	public Vektor2D() {
	}

	/**
	 * Erzeugt einen Vektor mit gegebener Richtung und Länge
	 * 
	 * @param richtung
	 * @param schrittlaenge
	 */
	public Vektor2D(double richtung, double schrittlaenge) {
		this.laenge = schrittlaenge;
		this.richtung = richtung;
		this.richtungUndLaengeIndxunddy();
	}

	/**
	 * erzeugt den Verschiebungsvektor von (x0,y0) auf (x1,y1)
	 * 
	 * @param x0
	 * @param y0
	 * @param x1
	 * @param y1
	 */
	public Vektor2D(double x0, double y0, double x1, double y1) {
		this.dx = x1 - x0;
		this.dy = y1 - y0;
		this.dxUndDyinRichtungUndLaenge();
	}

	/**
	 * 
	 * @return liefert den Versatz in x-Richtung bei einer Bewegung
	 */
	public double getDx() {
		return dx;
	}

	/**
	 * 
	 * setzt den Versatz in x-Richtung bei einer Bewegung, passt ferner Richtung
	 * und Schrittlänge an diesen Versatz an
	 * 
	 * @param dx
	 */
	public void setzeDx(double dx) {
		this.dx = dx;
		dxUndDyinRichtungUndLaenge();
	}

	/**
	 * liefert eine Kopie des Verktors *
	 * 
	 * @return
	 */

	public Vektor2D kopie() {
		return new Vektor2D(this.richtung, this.laenge);
	}

	/**
	 * Setzt Versatz in x und y -Richtung bei einer Bewegung passt Richtung und
	 * Schrittlänge an
	 * 
	 * @param dxdy
	 */
	public void setzeDxUndDy(double[] dxdy) {
		this.dx = dxdy[0];
		this.dy = dxdy[1];
		dxUndDyinRichtungUndLaenge();
	}

	public void setzeDxUndDy(double x2, double y2) {
		this.dx = x2;
		this.dy = y2;
		dxUndDyinRichtungUndLaenge();

	}

	/**
	 * 
	 * @return liefert Versatz in y-Richtung: positiv: nach unten, negativ nach
	 *         oben
	 */
	public double getDy() {
		return dy;
	}

	/**
	 * 
	 * @param dy
	 *            Setzt Versatz in y -Richtung bei einer Bewegung passt Richtung
	 *            und Schrittlänge an, dy positiv: nach unten, negativ nach oben
	 */
	public void setzeDy(double dy) {
		this.dy = dy;
		dxUndDyinRichtungUndLaenge();
	}

	/**
	 * setzt Schrittlänge für eine Bewegung, passt dx un dy an
	 * 
	 * @param l
	 */
	public void setzeLaenge(double l) {
		laenge = l;
		this.richtungUndLaengeIndxunddy();
	}

	/**
	 * 
	 * @return liefert Schrittlänge für eine Bewegung
	 */
	public double getLaenge() {
		return laenge;
	}

	/**
	 * setzt Richtung (in Grad) für eine Bewegung. Passt dx und dy an.
	 * 
	 * @param r
	 */
	public void setzeRichtung(double r) {
		richtung = r;
		this.richtungUndLaengeIndxunddy();
	}

	/**
	 * 
	 * @return liefert richtung einer Bewegung in Grad
	 */
	public double getRichtung() {
		return richtung;
	}

	/**
	 * Addiert einen Vektor.
	 */
	public void addiere(Vektor2D summand) {
		dx += summand.dx;
		dy += summand.dy;
		this.dxUndDyinRichtungUndLaenge();
	}

	

	private void richtungUndLaengeIndxunddy() {
		double w = richtung * Math.PI / 180;
		dx = laenge * Math.cos(w);
		dy = -laenge * Math.sin(w);
	}

	private void dxUndDyinRichtungUndLaenge() {
		laenge = Hilfe.wurzel(dx * dx + dy * dy);
		if (dx > 0 && dy > 0) {
			richtung = 360 - Hilfe.arctangens(dy / dx);
		} else if (dx > 0 && dy < 0) {
			richtung = Hilfe.arctangens(-dy / dx);
		} else if (dx < 0) {
			richtung = 180 - Hilfe.arctangens(dy / dx);
		} else if ((dx == 0) & (dy < 0)) {
			richtung = 90;
		} else if ((dx == 0) & (dy > 0)) {
			richtung = 270;
		}
	}

	/**
	 * Liefert den Vektor, der durch Reflektion an ve entsteht. Ist ve der
	 * Nullvektor, so wird null zurückgegeben.
	 * 
	 * @param ve
	 * @return
	 */
	public Vektor2D reflektionAn(Vektor2D ve) {
		double u = ve.getDx();
		double v = ve.getDy();
		double a = this.getDx();
		double b = this.getDy();
		double hn = u * u + v * v;
		if (hn != 0) {
			double z1 = b * u - a * v;
			double z2 = a * u + b * v;
			return new Vektor2D(0, 0, (z1 * v + z2 * u) / hn,
					(-z1 * u + z2 * v) / hn);
		}
		return null;
	}

	/**
	 * Multipliziert die Länge des Vektors mit dem Betrag von s. Bei negativem s
	 * wird die Richtung um 180° geändert.
	 * 
	 * @param s
	 */
	public void mul(double s) {
		this.dx *= s;
		this.dy *= s;
		this.dxUndDyinRichtungUndLaenge();
	}
}