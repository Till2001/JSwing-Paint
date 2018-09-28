package basiX;

import java.awt.*; //import java.awt.image.BufferedImage;
//
//import javax.swing.JComponent;
//import javax.swing.JPanel;
//import basis.*;
import basiX.swing.PenPane;
import basiX.util.Vektor2D;
import basiX.vw.DasFenster;

/**
 * @author Georg Dick
 * @version 16.11.2013 by BeA
 */
public class Stift {
	/** Stiftmodi als Klassenkonstante */
	public static final int NORMALMODUS = 0;
	public static final int RADIERMODUS = 1;
	public static final int WECHSELMODUS = 2;
	public static final int GESTRICHELT = 1;
	public static final int DURCHGEZOGEN = 0;

	private String aktuellerFont = Schrift.STANDARDSCHRIFTART;
	private int schriftStil = Schrift.STANDARDSTIL;
	private int schriftGroesse = Schrift.STANDARDGROESSE;
	private Font schriftArt = Schrift.STANDARDSCHRIFT;
	private Color farbe = Farbe.SCHWARZ;
	private int linienBreite = 1;
	private int linienTyp = DURCHGEZOGEN;
	private double alphaWert = 1.0;

	private int muster = Muster.DURCHSICHTIG;
	private float[] dash;

	/**
	 * Ein Stift muss seinen Arbeitsbereich kennen. Aus ihm bezieht er u.a. den
	 * Grafikkontext.
	 */
	protected Container kenntSwingkomponente;
	protected BasisStiftFlaeche stiftflaeche = null;
	protected PenPane penpane = null;

	/**
	 * Stiftposition
	 */
	protected double stiftx = 0;
	protected double stifty = 0;
	/** Stiftzustand */
	protected boolean stiftOben = true;
	/** Stiftrichtung */
	protected double stiftRichtung = 0;
	/** StandardModus ist NORMALMODUS */
	protected int schreibModus = NORMALMODUS;
	protected Graphics2D gr;

	// protected Graphics grk;

	private boolean leinwandneu = true;// inzwischen unnötig ?

	private Komponente malflaeche = null;
	private boolean fontveraendert = false;

	private StiftZustand merkeZustand;
	// Aufruf von Toolkit sync nach jeder Zeichenoperation
	// (verlangsamt, ist möglicherweise unnötig)
	private boolean toolkitsyncAn = false;
	// die beiden folgenden Variabalen dienene dazu
	// das Toolkit sync nur in vorbetsimmbaren Zeiten auszuführen
	private static boolean syncanforderungvorhanden = true;
	private static long letztesSync = 0;
	private static int dsync = 500;
	private static boolean syncthreadstarted = false;

	public void setSynctimeInMS(int ms) {
		dsync = ms;
	}

	public int getSynctimeInMS() {
		return dsync;
	}

	public Komponente getMalflaeche() {
		return malflaeche;
	}

	/**
	 * erzeugt einen Stift. Die Position ist (0,0), der Normalmodus ist
	 * eingestellt, die Farbe ist Farbe.SCHWARZ, die verwendeten
	 * Schriftattribute sind auf Standardwerte eingestellt (s. Klasse Schrift).
	 * Der Stift ist angehoben.
	 */
	public Stift() {
		this(DasFenster.hauptLeinwand());
	}

	/**
	 * erzeugt einen Stift. Die Position ist (0,0), der Normalmodus ist
	 * eingestellt, die Farbe ist Farbe.SCHWARZ, die verwendeten
	 * Schriftattribute sind auf Standardwerte eingestellt (s. Klasse Schrift).
	 * Der Stift ist angehoben.
	 */

	public Stift(BasisStiftFlaeche sf) {
		// if (sf instanceof Fenster){
		// stiftflaeche=((Fenster)sf).leinwand();
		// } else
		// if (sf instanceof Komponente){
		// malflaeche = (Komponente)sf;
		// kenntSwingkomponente = ((Komponente)sf).getSwingComponent();
		//
		// } else
		// stiftflaeche = sf;
		// this.setzeStandard();
		this.maleAuf(sf);
		this.merkeZustand();
	}

	public Stift(PenPane pp) {
		// if (sf instanceof Fenster){
		// stiftflaeche=((Fenster)sf).leinwand();
		// } else
		// if (sf instanceof Komponente){
		// malflaeche = (Komponente)sf;
		// kenntSwingkomponente = ((Komponente)sf).getSwingComponent();
		//
		// } else
		// stiftflaeche = sf;
		// this.setzeStandard();
		this.maleAuf(pp);
		this.merkeZustand();
	}

	/**
	 * Der Stift wird angewiesen auf der Unterlage zu malen. Der Stift wird in
	 * seinen Standardzustand versetzt.
	 * 
	 * @param sf
	 *            Malflaeche für den Stift
	 * 
	 */
	public void maleAuf(BasisStiftFlaeche sf) {
		if (sf instanceof Fenster) {
			sf = ((Fenster) sf).leinwand();
		} // kein else!
		if (sf instanceof Komponente) {
			malflaeche = (Komponente) sf;
			kenntSwingkomponente = ((Komponente) sf).getSwingComponent();
		}
		stiftflaeche = sf;
		penpane = null;
		this.setzeStandard();
	}

	/**
	 * Der Stift wird angewiesen auf der Unterlage zu malen. Der Stift wird in
	 * seinen Standardzustand versetzt.
	 * 
	 * @param sf
	 *            Malflaeche für den Stift
	 * 
	 */
	public void maleAuf(PenPane pp) {
		penpane = pp;
		stiftflaeche = null;
		this.setzeStandard();
	}

	/**  */
	protected void sync() {
		if (kenntSwingkomponente != null) {
			kenntSwingkomponente.repaint();
			// this.syncanforderungvorhanden=true;
			// if (!syncthreadstarted){
			// this.startSyncThread();
			// }
			if (toolkitsyncAn) {
				Toolkit.getDefaultToolkit().sync();
			}

		} else {
			if (stiftflaeche instanceof StiftFlaeche) {
				((StiftFlaeche) this.stiftflaeche).aktualisiereDarstellung();
			}

			// System.out.println("aktu");
		}
	}

	/**
	 * ruft sync verzögert auf. Experimentell
	 */
	protected static void startSyncThread() {
		syncthreadstarted = true;
		new Thread() {
			public void run() {
				while (true) {
					if (Stift.this.syncanforderungvorhanden
							&& System.currentTimeMillis() - letztesSync > Stift.this.dsync) {
						Toolkit.getDefaultToolkit().sync();
						Stift.this.syncanforderungvorhanden = false;
						letztesSync = System.currentTimeMillis();
						System.out.println("sync");
					}
				}
			}
		}.start();
	}

	/**
	 * versetzt den Stift zur die angegebene Position. Ist der Stift abgesenkt
	 * wird gezeichnet. Modus und Farbe werden berücksichtigt. Der Stift ändert
	 * seine Position auf den Endpunkt der Bewegung. Seine Ausrichtung wird
	 * nicht verändert
	 */
	public void bewegeBis(double px, double py) {
		// Vektor2D v = new Vektor2D();
		// v.setzeDxUndDy(px - stiftx, py - stifty);
		// this.stiftRichtung = v.getRichtung();
		if (!stiftOben) {
			this.linie(stiftx, stifty, px, py);
			this.sync();
		}
		stiftx = px;
		stifty = py;
	}

	/**
	 * bewegt den Stift auf die angegebene Position. Ist der Stift abgesenkt
	 * wird gezeichnet. Modus und Farbe werden berücksichtigt. Der Stift ändert
	 * seine Position auf den Endpunkt der Bewegung. Seine Ausrichtung wird der
	 * Bewegung angepasst
	 */
	public void bewegeAuf(double px, double py) {
		Vektor2D v = new Vektor2D();
		v.setzeDxUndDy(px - stiftx, py - stifty);
		this.stiftRichtung = v.getRichtung();
		if (!stiftOben) {
			this.linie(stiftx, stifty, px, py);
			this.sync();
		}
		stiftx = px;
		stifty = py;
	}

	/**  */
	public Graphics2D grafikKontext() {
		if (stiftflaeche != null) {
			return (Graphics2D) this.stiftflaeche.getBufferedImageGraphics();
		}
		if (penpane != null) {
			return (Graphics2D) this.penpane.getBufferedImageGraphics();
		}
		return null;
		// if (leinwandneu || (grk == null)) {
		// leinwandneu = false;
		// grk = ((Stiftflaeche)this.malflaeche).holeGraphics();
		// }
		// grk = this.kenntLeinwand.getGraphics();
	}

	/**
	 * bewegt den Stift um die angegebene Entfernung in Pixeln. Ist der Stift
	 * abgesenkt wird gezeichnet. Ausrichtung, Modus und Farbe werden
	 * berücksichtigt. Der Stift ändert seine Position auf den Endpunkt der
	 * Bewegung.
	 */
	public void bewegeUm(double pl) {
		double w;
		double x, y;
		w = stiftRichtung * Math.PI / 180;
		x = stiftx + pl * Math.cos(w);
		y = stifty - pl * Math.sin(w);
		if (!stiftOben) {
			linie(stiftx, stifty, x, y);
			this.sync();
		}
		stiftx = x;
		stifty = y;

	}

	/**
	 * bewegt den Stift in Richtung des Vektors um die Entfernung des Vektors in
	 * Pixeln. Ist der Stift abgesenkt wird gezeichnet. Ausrichtung, Modus und
	 * Farbe werden berücksichtigt. Der Stift ändert seine Position auf den
	 * Endpunkt der Bewegung. Die Ausrichtung entspricht der des Vektors.
	 */
	public void bewegeUm(Vektor2D v) {
		stiftRichtung = v.getRichtung();
		this.bewegeUm(v.getLaenge());
	}

	/**
	 * ändert die Ausrichtung des Stiftes. Der Stift wird auf den gegebenen
	 * Winkel ausgerichtet.
	 */
	public void dreheBis(double w) {
		stiftRichtung = w;
		while (stiftRichtung < 0) {
			stiftRichtung = stiftRichtung + 360;
		}
		while (stiftRichtung >= 360) {
			stiftRichtung = stiftRichtung - 360;
		}
	}

	/**
	 * legt fest ob und in welchem Grad die Stiftfarbe transparent gezeichnet
	 * wird
	 * 
	 * @param alpha
	 *            Grad zwischen 0.0 und 1.0
	 */
	public void setzeTransparenzGrad(double alpha) {
		this.alphaWert = alpha;
	}

	/**
	 * 
	 * @return liefert den Grad der Transparenz mit der der Stift zeichnet
	 */
	public double transparenzGrad() {
		return alphaWert;
	}

	/**
	 * ändert die Ausrichtung des Stiftes. Der Stift wird auf den gegebenen
	 * Punkt(x,y) ausgerichtet.
	 */
	public void dreheInRichtung(double x, double y) {
		Vektor2D v = new Vektor2D(stiftx, stifty, x, y);
		stiftRichtung = v.getRichtung();
	}

	/**
	 * ändert die Ausrichtungdes Stiftes um den angegebene Winkel. Positive
	 * Werte führen zu Änderungen gegen den Uhrzeigersinn, negative zu
	 * Änderuungen im Uhrzeigersinn
	 */
	public void dreheUm(double w) {
		stiftRichtung = stiftRichtung + w;
		while (stiftRichtung < 0) {
			stiftRichtung = stiftRichtung + 360;
		}
		while (stiftRichtung >= 360) {
			stiftRichtung = stiftRichtung - 360;
		}
	}

	/** hebt den Stift an */
	public void hoch() {
		stiftOben = true;
	}

	/** senkt den Stift ab */
	public void runter() {
		stiftOben = false;
	}

	/** setzt den Stift auf den Normalmodus */
	public void normal() {
		schreibModus = NORMALMODUS;
	}

	/** setzt den Stift auf den Radiermodus */
	public void radiere() {
		schreibModus = RADIERMODUS;
	}

	/** setzt den Stift auf den Wechslemodus */
	public void wechsle() {
		schreibModus = WECHSELMODUS;
	}

	/**
	 * Bestimmt die Stiftfarbe. In der Klasse Farbe werden Konstante und eine
	 * Funktion zur Farbwahl angeboten
	 */
	public void setzeFarbe(Color farbe) {
		this.farbe = farbe;
	}

	/** Festlegung von Lininebreiten. */
	public void setzeLinienBreite(int pb) {
		linienBreite = pb;
	}

	/**
	 * Festlegung von Lininetypen.
	 * 
	 * @param lt
	 *            Gestrichelt mit 10 zu 2 Pixeln oder Durchgezogen
	 */
	public void setzeLinienTyp(int lt) {
		dash = new float[2];
		dash[0] = 10;
		dash[1] = 2;
		linienTyp = lt;
	}

	/**
	 * bestimmt ein Strichmuster
	 * 
	 * @param solid
	 *            Anzahl gezeichneter Pixel
	 * @param durchsichtig
	 *            Anzahl nicht gezeichneter Pixel
	 */
	public void setzeLinienTyp(float solid, float durchsichtig) {
		dash = new float[2];
		dash[0] = solid;
		dash[1] = durchsichtig;
		linienTyp = GESTRICHELT;
	}

	/**
	 * bestimmt das Füllmuster für Kreis und Rechteck. Für die Muster stehen in
	 * der Klasse Muster Konstanten zur Verfügung
	 */
	public void setzeFuellMuster(int muster) {
		this.muster = muster;
	}

	/** setzt den Stiftzustand auf Standardwerte zurück */
	public void setzeStandard() {
		stiftx = 0;
		stifty = 0; // Stiftposition
		stiftOben = true; // Stiftzustand
		stiftRichtung = 0; // Stiftrichtung
		schreibModus = NORMALMODUS; // Normalmodus
		aktuellerFont = Schrift.STANDARDSCHRIFTART;
		schriftStil = Schrift.STANDARDSTIL;
		schriftGroesse = Schrift.STANDARDGROESSE;
		schriftArt = Schrift.STANDARDSCHRIFT;
		farbe = Farbe.SCHWARZ;
		linienBreite = 1;
		muster = Muster.DURCHSICHTIG;
		linienTyp = 0;
	}

	/**
	 * bestimmt Schriftart, Schriftstil und Schriftgröße. Die Klasse Schrift
	 * stellt passende Konstanten zur Verfügung
	 */
	public void setzeSchrift(String art, int stil, int groesse) {
		aktuellerFont = art;
		schriftGroesse = groesse;
		schriftStil = stil;
	}

	/**
	 * bestimmt die Schriftart. Die möglichen Schriftarten sind in der Klasse
	 * Schrift als Konstanten festgelegt
	 */
	public void setzeSchriftArt(String schriftart) {
		aktuellerFont = schriftart;
		schriftArt = new Font(aktuellerFont, schriftStil, schriftGroesse);
		fontveraendert = true;
	}

	/**
	 * bestimmt die Schriftart.
	 */
	public void setzeSchriftArt(Font fschriftart) {
		aktuellerFont = fschriftart.getFontName();
		schriftArt = fschriftart;
		fontveraendert = true;
	}

	/** legt die Schriftgröße in Pixeln fest */
	public void setzeSchriftGroesse(int schriftgroesse) {
		schriftGroesse = schriftgroesse;
		schriftArt = new Font(aktuellerFont, schriftStil, schriftGroesse);
		// zschriftart = zschriftart.deriveFont((float)schriftgroesse);
		fontveraendert = true;
	}

	/**
	 * bestimmt den Schriftstil. Die möglichen Stile sind in der Klasse Schrift
	 * als Konstanten festgelegt
	 */
	public void setzeSchriftStil(int schriftstil) {
		schriftStil = schriftstil;
		schriftArt = null;
		schriftArt = new Font(aktuellerFont, schriftStil, schriftGroesse);
		fontveraendert = true;
	}

	/**
	 * schreibt den Text t an die aktuelle Stiftposition in horizontaler
	 * Richtung. Anschließend steht der Stift hinter dem Text.
	 */
	public void schreibe(char t) {
		this.schreibe(t + "");
	}

	/**
	 * schreibt den Text t an die aktuelle Stiftposition in horizontaler
	 * Richtung. Anschließend steht der Stift hinter dem Text.
	 */
	public void schreibeText(String t) {
		this.schreibe(t);
	}

	/**
	 * schreibt den Text t an die aktuelle Stiftposition in horizontaler
	 * Richtung. Anschließend steht der Stift hinter dem Text.
	 * 
	 */
	public void schreibeText(char t) {
		this.schreibe(t);
	}

	/**
	 * schreibt die Zahl z an die aktuelle Stiftpositionin horizontaler
	 * Richtung. Anschließend steht der Stift hinter der Zahl
	 */
	public void schreibeZahl(double z) {
		this.schreibe(z + "");
	}

	/**
	 * schreibt die Zahl z an die aktuelle Stiftposition in horizontaler
	 * Richtung. Anschließend steht der Stift hinter der Zahl
	 */
	public void schreibeZahl(int z) {
		this.schreibe(z + "");
	}

	/**
	 * zeichnet einen Kreis. Die Stiftposition bestimmt den Mittelpunkt. Der
	 * Radius wird über den Parameter festgelegt, Stiftposition und -richtung
	 * werden nicht beeinflusst. Es wird auch gezeichnet, wenn der Stift
	 * angehoben ist. Gesetzte Füllmuster werden berücksichtigt
	 */
	public void zeichneKreis(double pradius) {
		int x1, y1, d;
		x1 = (int) Math.round(stiftx - pradius);
		y1 = (int) Math.round(stifty - pradius);
		d = (int) Math.round(2 * pradius);
		gr = this.grafikKontext();
		if (gr != null) {
			setGZustand(gr);
			if (muster == Muster.DURCHSICHTIG) {
				gr.drawOval(x1, y1, d, d);
			} else {
				if (muster == Muster.GEFUELLT) {
					gr.fillOval(x1, y1, d, d);
				}
			}
		}
		this.sync();
		gr = null;
	}
	/**
	 * zeichnet ein Oval. Die Stiftposition bestimmt den Mittelpunkt. Die
	 * Radien werden über die Parameter festgelegt, Stiftposition und -richtung
	 * werden nicht beeinflusst. Es wird auch gezeichnet, wenn der Stift
	 * angehoben ist. Gesetzte Füllmuster werden berücksichtigt
	 */
	public void zeichneOval(double pradius1, double pradius2 ) {
		int x1, y1, r1,r2;
		x1 = (int) Math.round(stiftx - pradius1);
		y1 = (int) Math.round(stifty - pradius1);
		r1 = (int) Math.round(pradius1);
		r2 = (int) Math.round(pradius2);
		gr = this.grafikKontext();
		if (gr != null) {
			setGZustand(gr);
			if (muster == Muster.DURCHSICHTIG) {
				gr.drawOval(x1, y1, r1, r2);
			} else {
				if (muster == Muster.GEFUELLT) {
					gr.fillOval(x1, y1, r1, r2);
				}
			}
		}
		this.sync();
		gr = null;
	}
	/**
	 * zeichnet ein Rechteck. Der Stift beginnt in Bewegungsrichtung mit der
	 * Seitenlänge Breite (erster Parameter) und dreht dann jeweils nach rechts
	 * Breite und Höhe werden über die Parameter bestimmt. Stiftposition und
	 * -richtung werden nicht beeinflusst. Es wird auch gezeichnet, wenn der
	 * Stift angehoben ist. Gesetzte Füllmuster werden berücksichtigt.
	 */
	public void zeichneRechteck(double pbreite, double phoehe) {
		if (stiftRichtung == 0) {
			int x1, y1, x2, y2;
			x1 = (int) Math.round(stiftx);
			x2 = (int) Math.round(pbreite) - 1;
			y1 = (int) Math.round(stifty);
			y2 = (int) Math.round(phoehe) - 1;
			gr = this.grafikKontext();
			if (gr != null) {
				setGZustand(gr);
				if (muster == Muster.DURCHSICHTIG) {
					gr.drawRect(x1, y1, x2, y2);
				} else {
					if (muster == Muster.GEFUELLT) {
						gr.fillRect(x1, y1, x2 + 1, y2 + 1);
						/* fillrect zeichnet kleinere Rechtecke als drawrect ! */
					}
				}
			}
			this.sync();
			gr = null;
			return;
		}
		double[] x = new double[4];
		double[] y = new double[4];
		x[0] = stiftx;
		y[0] = stifty;
		Vektor2D v = new Vektor2D(stiftRichtung, pbreite);
		x[1] = stiftx + v.getDx();
		y[1] = stifty + v.getDy();
		v = new Vektor2D(stiftRichtung - 90, phoehe);
		x[2] = x[1] + v.getDx();
		y[2] = y[1] + v.getDy();
		v = new Vektor2D(stiftRichtung - 180, pbreite);
		x[3] = x[2] + v.getDx();
		y[3] = y[2] + v.getDy();
		this.polygon(x, y);

	}
	/**
	 * zeichnet ein Oval mit Mittelpunkt (x,y) und den vorgegebenen Radien.
	 * Stiftposition ist anschließend der Mittelpunkt des Ovals. Die
	 * Stiftrichtung wird nicht beeinflusst. Die Bewegung des Stiftes zum
	 * Mittelpunkt wird nicht gezeichnet. Es wird auch gezeichnet, wenn der
	 * Stift angehoben ist. Gesetzte Füllmuster werden berücksichtigt
	 */
	public void oval(double x, double y, double radius1, double radius2) {
		int x1, y1, r1,r2;
		x1 = (int) Math.round(x - radius1);
		y1 = (int) Math.round(y - radius2);
		r1 = (int) Math.round(radius1);
		r2 = (int) Math.round(radius2);
		gr = this.grafikKontext();
		if (gr != null) {
			setGZustand(gr);
			if (muster == Muster.DURCHSICHTIG) {
				gr.drawOval(x1, y1, r1, r2);
			} else {
				if (muster == Muster.GEFUELLT) {
					gr.fillOval(x1, y1, r1, r2);
				}
			}
		}
		this.sync();
		gr = null;
		stiftx = x;
		stifty = y;
	}
	/**
	 * zeichnet einen Kreis mit Mittelpunkt (x,y) und vorgegebenem Radius.
	 * Stiftposition ist anschließend der Mittelpunkt des Kreises. Die
	 * Stiftrichtung wird nicht beeinflusst. Die Bewegung des Stiftes zum
	 * Mittelpunkt wird nicht gezeichnet. Es wird auch gezeichnet, wenn der
	 * Stift angehoben ist. Gesetzte Füllmuster werden berücksichtigt
	 */
	public void kreis(double x, double y, double radius) {
		int x1, y1, d;
		x1 = (int) Math.round(x - radius);
		y1 = (int) Math.round(y - radius);
		d = (int) Math.round(2 * radius);
		gr = this.grafikKontext();
		if (gr != null) {
			setGZustand(gr);
			if (muster == Muster.DURCHSICHTIG) {
				gr.drawOval(x1, y1, d, d);
			} else {
				if (muster == Muster.GEFUELLT) {
					gr.fillOval(x1, y1, d, d);
				}
			}
		}
		this.sync();
		gr = null;
		stiftx = x;
		stifty = y;
	}

	/**
	 * zeichnet ein achsenparalleles Rechteck mit der linken oberen Ecke (x,y),
	 * vorgegebener Breite und Höhe. Stiftposition und -richtung werden nicht
	 * beeinflusst. Es wird auch gezeichnet, wenn der Stift angehoben ist
	 * Gesetzte Füllmuster werden berücksichtigt
	 */
	public void rechteck(double x, double y, double breite, double hoehe) {
		int x1, y1, x2, y2;
		x1 = (int) Math.round(x);
		x2 = (int) Math.round(breite) - 1;
		y1 = (int) Math.round(y);
		y2 = (int) Math.round(hoehe) - 1;
		gr = this.grafikKontext();
		if (gr != null) {
			setGZustand(gr);
			if (muster == Muster.DURCHSICHTIG) {
				gr.drawRect(x1, y1, x2, y2);
			} else {
				if (muster == Muster.GEFUELLT) {
					gr.fillRect(x1, y1, x2 + 1, y2 + 1);

					/* fillrect zeichnet kleinere Rechtecke als drawrect ! */
				}
			}
		}
		this.sync();
		gr = null;
	}

	/**
	 * zeichnet eine Strecke vom aktuellen Standort des Stiftes bis zum Punkt
	 * (x,y). Der Stift steht anschließend bei (x,y) und in seiner
	 * Bewegungsrichtung. Es wird auch gezeichnet, wenn der Stift angehoben ist.
	 * 
	 * 
	 */
	public void zeichneLinie(double x, double y) {
		Vektor2D v = new Vektor2D();
		v.setzeDxUndDy(x - stiftx, y - stifty);
		stiftRichtung = v.getRichtung();
		this.linie(stiftx, stifty, x, y);
	}

	/**
	 * zeichnet eine Strecke mit den Endpunkte (x1,y1) und (x2,y2). Es wird auch
	 * gezeichnet, wenn der Stift angehoben ist. StiftPosition und StiftRichtung
	 * werden nicht verändert.
	 * 
	 */
	public void linie(double x1, double y1, double x2, double y2) {
		int ix1, ix2, iy1, iy2;
		ix1 = (int) Math.round(x1);
		iy1 = (int) Math.round(y1);
		ix2 = (int) Math.round(x2);
		iy2 = (int) Math.round(y2);
		gr = this.grafikKontext();
		setGZustand(gr);
		if (gr != null) {
			gr.drawLine(ix1, iy1, ix2, iy2);
		}
		gr = null;
		this.sync();
		// Vektor2D v = new Vektor2D();
		// v.setzeDxUndDy(x2 - x1, y2 - y1);
		// stiftRichtung = v.getRichtung();
		// // System.out.println(zwinkel);
		// stiftx = x2;
		// stifty = y2;
	}

	/**
	 * liefert die Ausrichtung des Stiftes (nach rechts Null Grad, Winkelzunahme
	 * gegen den Uhrzeigersinn
	 */
	public double winkel() {
		return stiftRichtung;
	}

	/** liefert die Farbe des Stiftes */
	public Color farbe() {
		return farbe;
	}

	/** liefert den Wert wahr, wenn der Stift angehoben ist */
	public boolean istOben() {
		return stiftOben;
	}

	/** liefert die Breite des Textes s in Pixeln */
	public int textBreite(String s) {
		gr = this.grafikKontext();
		setGZustand(gr);
		if (gr != null) {
			try {
				return gr.getFontMetrics().stringWidth(s);
			} catch (Exception e) {
			}
		}
		return 0;
	}

	/** liefert die Höhe der eingestellten Schriftart in Pixeln */
	public int textHoehe() {
		gr = this.grafikKontext();
		setGZustand(gr);
		if (gr != null) {
			try {
				return gr.getFontMetrics().getHeight();
			} catch (Exception e) {
			}
		}
		return 0;
	}

	/** liefert die Füllmusterkonstante */
	public int fuellMuster() {
		return muster;
	}

	/** gibt alle Ressourcen frei. Der Stift ist danach nicht mehr verwendbar */
	public void gibFrei() {
		schriftArt = null;
		farbe = null;

	}

	/**
	 * schreibt den Text t an die aktuelle Stiftposition in horizontaler
	 * Richtung. Anschließend steht der Stift hinter dem Text. *
	 */
	public void schreibe(String ps) {
		// new Thread() {
		// public void run() {
		int zx = (int) Math.round(stiftx);
		int zy = (int) Math.round(stifty);
		int dx = 0;
		gr = Stift.this.grafikKontext();
		setGZustand(gr);
		if (gr != null) {
			gr.drawString(ps, zx, zy);
			try {
				dx = gr.getFontMetrics().stringWidth(ps);
			} catch (Exception e) {
			}
		}
		stiftx = stiftx + dx;
		stiftRichtung = 0;
		gr = null;
		Stift.this.sync();
		// }
		// }.start();

	}

	/**  */
	protected void setGZustand(Graphics2D g) {
		if ((g != null)) {
			if (schreibModus == NORMALMODUS) {
				g.setPaintMode();
				if (this.farbe.equals(Farbe.DURCHSICHTIG)) {
					g.setComposite(AlphaComposite.Src);
				} else {
					AlphaComposite ac1 = AlphaComposite.getInstance(
							AlphaComposite.SRC_OVER, (float) alphaWert);
					g.setComposite(ac1);
				}
				g.setColor(farbe);
			} else if (schreibModus == RADIERMODUS) {
				g.setPaintMode();
				g.setComposite(AlphaComposite.Src);
				// System.out.println(hgColor().equals(Farbe.DURCHSICHTIG));
				g.setColor(hgColor());

			} else {
				g.setColor(farbe);
				g.setXORMode(this.hgColor());
			}
			if (fontveraendert || (schriftArt != g.getFont())) {
				// Font f = new Font("Dialog", Font.PLAIN, 18);
				// g.setFont(f);
				g.setFont(schriftArt);
				fontveraendert = false;

			}
			Stroke stroke;
			if (linienTyp == DURCHGEZOGEN) {
				stroke = new BasicStroke(linienBreite);

			} else {

				stroke = new BasicStroke(linienBreite, BasicStroke.CAP_BUTT,
						BasicStroke.JOIN_MITER, 1, dash, 0);
			}
			((Graphics2D) g).setStroke(stroke);

		} else { /* System.out.println("Kein g"); */
		}
	}

	private Color hgColor() {

		if (this.stiftflaeche != null) {
			return this.stiftflaeche.hintergrundFarbe();
		}
		if (this.penpane != null) {
			return this.penpane.getFillColor();
		}
		return Farbe.DURCHSICHTIG;
	}

	/**
	 * zeichnet einen Kreisbogen. Der Mittelpunkt des Bogens ergibt sich aus der
	 * Stiftposition. Der Radius über radius. Der Bogen beginnt vom Mittelpunkt
	 * aus gesehen mit dem Winkel des Stiftes und überstreicht einen Winkel
	 * entsprechend der Größe drehwinkel. Je nach Vorzeichen diese Parameters
	 * erfolgt eine Linksdrehung (+) bzw. eine Rechtsdrehung(-). Der Stift steht
	 * anschließend in der Mitte und ist auf das Ende des Bogens ausgerichtet.
	 * Füllmuster werden bei der Zeichnung berücksichtigt. Es entsteht
	 * gegebenenfalls ein Kreissegment. Die Zeichnung erfolgt auch bei
	 * angehobeneme Stift.
	 * 
	 * @param radius
	 * @param drehwinkel
	 */
	public void zeichneKreisBogen(double radius, double drehwinkel) {
		// stiftRichtung += drehwinkel;
		this.kreisBogen(stiftx, stifty, radius, stiftRichtung, drehwinkel);
		stiftRichtung += drehwinkel;
	}

	/**
	 * zeichnet einen Kreisbogen. Der Mittelpunkt des Bogens ergibt sich über
	 * die Parameter mx und my. Der Radius über radius. Der Bogen beginnt vom
	 * Mittelpunkt aus gesehen mit dem Winkel startwinkel und überstreicht einen
	 * Winkel entsprechend der Größe drehwinkel. Je nach Vorzeichen diese
	 * Parameters erfolgt eine Linksdrehung (+) bzw. eine Rechtsdrehung(-)
	 * Füllmuster werden bei der Zeichnung berücksichtigt. Es entsteht
	 * gegebenenfalls ein Kreissegment. Die Zeichnung erfolgt auch bei
	 * angehobeneme Stift. Die Bewegung des Stiftes zum Mittelpunkt wird dagegen
	 * nicht gezeichnet.
	 * 
	 * @param mx
	 * @param my
	 * @param radius
	 * @param startwinkel
	 * @param drehwinkel
	 */
	public void kreisBogen(double mx, double my, double radius,
			double startwinkel, double drehwinkel) {

		int imx = (int) Math.round(mx);
		int imy = (int) Math.round(my);
		int iradius = (int) Math.round(radius);
		int istartwinkel = (int) Math.round(startwinkel);
		int idrehwinkel = (int) Math.round(drehwinkel);

		gr = Stift.this.grafikKontext();
		setGZustand(gr);
		if (gr != null) {
			setGZustand(gr);
			if (muster == Muster.DURCHSICHTIG) {
				gr.drawArc(imx - iradius, imy - iradius, 2 * iradius,
						2 * iradius, istartwinkel, idrehwinkel);
			} else {
				if (muster == Muster.GEFUELLT) {
					gr.fillArc(imx - iradius, imy - iradius, 2 * iradius,
							2 * iradius, istartwinkel, idrehwinkel);
				}
			}
		}
		this.sync();
		gr = null;
	}

	/**
	 * Zeichnet einen geschlossenen Streckenzug. Der Anfangpunkt ist die
	 * aktuelle Stiftkoordinate. Im Felde xy werden Änderungen (Vektoren) der
	 * jeweils aktuellen Position übergeben. Befindet sich der Stift auf
	 * Position (u/v) dann wird (u+xy[i].getDx(),u+xy[i].getDy()) zur neuen
	 * Position. Das Feld xy muss mindestens zwei Vektoren enthalten. Die
	 * Stiftposition nach erfolgter Zeichnung ist der Anfangs(und Endpunkt). Der
	 * Stift richtet sich nach der ersten Strecke aus. Füllmuster werden
	 * berücksichtigt.
	 * 
	 * @param xy
	 *            Feld für Verschiebungsvektoren
	 * 
	 */
	public void zeichnePolygon(Vektor2D[] xy) {
		if (xy.length > 1) {
			double[] u = new double[xy.length + 1];
			double[] v = new double[xy.length + 1];
			u[0] = stiftx;
			u[1] = stifty;
			for (int i = 1; i < u.length; i++) {
				u[i] = u[i - 1] + xy[i - 1].getDx();
				v[i] = v[i - 1] + xy[i - 1].getDy();
			}
			this.polygon(u, v);
			stiftRichtung = xy[0].getRichtung();
		}
	}

	/**
	 * Zeichnet einen geschlossenen Streckenzug. Der Anfangpunkt ist die
	 * aktuelle Stiftkoordinate. In den Parametern werden Änderungen (Vektoren)
	 * der jeweils aktuellen Position übergeben. Befindet sich der Stift auf
	 * Position (u/v) dann wird (u+p1x,u+py1) zur neuen Position. Die
	 * Stiftposition nach erfolgter Zeichnung ist der Anfangs(und Endpunkt). Der
	 * Stift richtet sich nach der ersten Strecke aus. Füllmuster werden
	 * berücksichtigt.
	 * 
	 */

	public void zeichneDreieck(double px1, double py1, double px2, double py2) {
		double[] x = new double[2];
		double[] y = new double[2];
		x[0] = px1;
		y[0] = py1;
		x[1] = px2;
		y[1] = py2;
		this.zeichnePolygon(x, y);
	}

	/**
	 * Zeichnet einen geschlossenen Streckenzug. Der Anfangpunkt ist die
	 * aktuelle Stiftkoordinate. In den Parametern werden Änderungen (Vektoren)
	 * der jeweils aktuellen Position übergeben. Befindet sich der Stift auf
	 * Position (u/v) dann wird (u+p1x,u+py1) zur neuen Position. Die
	 * Stiftposition nach erfolgter Zeichnung ist der Anfangs(und Endpunkt). Der
	 * Stift richtet sich nach der ersten Strecke aus. Füllmuster werden
	 * berücksichtigt.
	 * 
	 */

	public void zeichneViereck(double px1, double py1, double px2, double py2,
			double px3, double py3) {
		double[] x = new double[3];
		double[] y = new double[3];
		x[0] = px1;
		y[0] = py1;
		x[1] = px2;
		y[1] = py2;
		x[2] = px3;
		y[2] = py3;

		this.zeichnePolygon(x, y);
	}

	/**
	 * Zeichnet einen geschlossenen Streckenzug. Der Anfangpunkt ist die
	 * aktuelle Stiftkoordinate. In den Parametern werden Änderungen (Vektoren)
	 * der jeweils aktuellen Position übergeben. Befindet sich der Stift auf
	 * Position (u/v) dann wird (u+p1x,u+py1) zur neuen Position. Die
	 * Stiftposition nach erfolgter Zeichnung ist der Anfangs(und Endpunkt). Der
	 * Stift richtet sich nach der ersten Strecke aus. Füllmuster werden
	 * berücksichtigt.
	 * 
	 */

	public void zeichneNikolaus(double px1, double py1, double px2, double py2,
			double px3, double py3, double px4, double py4, double px5,
			double py5, double px6, double py6, double px7, double py7, double px8, double py8) {
		double[] x = new double[8];
		double[] y = new double[8];
		x[0] = px1;
		y[0] = py1;
		x[1] = px2;
		y[1] = py2;
		x[2] = px3;
		y[2] = py3;
		x[3] = px4;
		y[3] = py4;
		x[4] = px5;
		y[4] = py5;
		x[5] = px6;
		y[5] = py6;
		x[6] = px7;
		y[6] = py7;
		x[7] = px8;
		y[7] = py8;
		
		this.zeichnePolygon(x, y);
	}
	
	/**
	 * Zeichnet einen geschlossenen Streckenzug. Der Anfangpunkt ist die
	 * aktuelle Stiftkoordinate. In den Parametern werden Änderungen (Vektoren)
	 * der jeweils aktuellen Position übergeben. Befindet sich der Stift auf
	 * Position (u/v) dann wird (u+p1x,u+py1) zur neuen Position. Die
	 * Stiftposition nach erfolgter Zeichnung ist der Anfangs(und Endpunkt). Der
	 * Stift richtet sich nach der ersten Strecke aus. Füllmuster werden
	 * berücksichtigt.
	 * 
	 */

	public void zeichneAchteck(double px1, double py1, double px2, double py2,
			double px3, double py3, double px4, double py4, double px5,
			double py5, double px6, double py6, double px7, double py7) {
		double[] x = new double[7];
		double[] y = new double[7];
		x[0] = px1;
		y[0] = py1;
		x[1] = px2;
		y[1] = py2;
		x[2] = px3;
		y[2] = py3;
		x[3] = px4;
		y[3] = py4;
		x[4] = px5;
		y[4] = py5;
		x[5] = px6;
		y[5] = py6;
		x[6] = px7;
		y[6] = py7;
		this.zeichnePolygon(x, y);
	}

	/**
	 * Zeichnet einen geschlossenen Streckenzug. Der Anfangpunkt ist die
	 * aktuelle Stiftkoordinate. In den Feldern x und y werden Änderungen
	 * (Vektoren) der jeweils aktuellen Position übergeben. Befindet sich der
	 * Stift auf Position (u/v) dann wird (u+x[i],u+y[i]) zur neuen Position.
	 * Die Feldlängen von x und y müssen übereinstimmen und mindestens zwei
	 * Vektoren müssen gegeben sein. Die Stiftposition nach erfolgter Zeichnung
	 * ist der Anfangs(und Endpunkt). Der Stift richtet sich nach der ersten
	 * Strecke aus. Füllmuster werden berücksichtigt.
	 * 
	 * @param x
	 *            Feld für x-Koordinaten der Verschiebungsvektoren
	 * @param y
	 *            Feld für y-Koordinaten der Verschiebungsvektoren
	 */
	public void zeichnePolygon(double[] x, double[] y) {
		if (x.length > 1 && x.length == y.length) {
			double[] u = new double[x.length + 1];
			double[] v = new double[x.length + 1];
			u[0] = stiftx;
			v[0] = stifty;
			for (int i = 1; i < u.length; i++) {
				u[i] = u[i - 1] + x[i - 1];
				v[i] = v[i - 1] + y[i - 1];
			}
			this.polygon(u, v);

		}
	}

	/**
	 * Zeichnet einen geschlossenen Streckenzug mit den den Eckpunkten, die in
	 * den Parametern übergeben werden. Füllmuster werden berücksichtigt. Die
	 * Bewegung zum Startpunkt des Dreiecks wird nicht gezeichnet. Stiftposition
	 * und -richtung werden nicht beeinflusst.
	 * 
	 */

	public void dreieck(double px1, double py1, double px2, double py2,
			double px3, double py3) {
		double[] x = new double[3];
		double[] y = new double[3];
		x[0] = px1;
		y[0] = py1;
		x[1] = px2;
		y[1] = py2;
		x[2] = px3;
		y[2] = py3;
		this.polygon(x, y);
	}

	/**
	 * Zeichnet einen geschlossenen Streckenzug mit den den Eckpunkten, die in
	 * den Parametern übergeben werden. Füllmuster werden berücksichtigt. Die
	 * Bewegung zum Startpunkt des Vierecks wird nicht gezeichnet. Stiftposition
	 * und -richtung werden nicht beeinflusst.
	 * 
	 */

	public void viereck(double px1, double py1, double px2, double py2,
			double px3, double py3, double px4, double py4) {
		double[] x = new double[4];
		double[] y = new double[4];
		x[0] = px1;
		y[0] = py1;
		x[1] = px2;
		y[1] = py2;
		x[2] = px3;
		y[2] = py3;
		x[3] = px4;
		y[3] = py4;

		this.polygon(x, y);
	}

	/**
	 * Zeichnet einen geschlossenen Streckenzug mit den den Eckpunkten, die in
	 * den Parametern übergeben werden. Füllmuster werden berücksichtigt. Die
	 * Bewegung zum Startpunkt des polygons wird nicht gezeichnet. Stiftposition
	 * und -richtung werden nicht beeinflusst.
	 * 
	 */

	public void nikolaus(double px1, double py1, double px2, double py2,
			double px3, double py3, double px4, double py4, double px5, double py5, double px6, double py6,
			double px7, double py7, double px8, double py8, double px9, double py9) {
		double[] x = new double[9];
		double[] y = new double[9];
		x[0] = px1;
		y[0] = py1;
		x[1] = px2;
		y[1] = py2;
		x[2] = px3;
		y[2] = py3;
		x[3] = px4;
		y[3] = py4;
		x[4] = px5;
		y[4] = py5;
		x[5] = px6;
		y[5] = py6;
		x[6] = px7;
		y[6] = py7;
		x[7] = px8;
		y[7] = py8;
		x[8] = px9;
		y[8] = py9;
		this.polygon(x, y);
	}
	
	/**
	 * Zeichnet einen geschlossenen Streckenzug mit den den Eckpunkten, die in
	 * den Parametern übergeben werden. Füllmuster werden berücksichtigt. Die
	 * Bewegung zum Startpunkt des Achtecks wird nicht gezeichnet. Stiftposition
	 * und -richtung werden nicht beeinflusst.
	 * 
	 */

	public void achteck(double px1, double py1, double px2, double py2,
			double px3, double py3, double px4, double py4, double px5, double py5, double px6, double py6,
			double px7, double py7, double px8, double py8) {
		double[] x = new double[8];
		double[] y = new double[8];
		x[0] = px1;
		y[0] = py1;
		x[1] = px2;
		y[1] = py2;
		x[2] = px3;
		y[2] = py3;
		x[3] = px4;
		y[3] = py4;
		x[4] = px5;
		y[4] = py5;
		x[5] = px6;
		y[5] = py6;
		x[6] = px7;
		y[6] = py7;
		x[7] = px8;
		y[7] = py8;
		this.polygon(x, y);
	}
	
	/**
	 * Zeichnet einen geschlossenen Streckenzug mit den den Eckpunkten, die in
	 * den Feldern x und y übergeben werden. Die Feldlängen müssen
	 * übereinstimmen und mindestens drei Punkte müssen gegeben sein. Füllmuster
	 * werden berücksichtigt Die Bewegung zum Startpunkt des Polygons wird nicht
	 * gezeichnet. Stiftposition und -richtung werden nicht beeinflusst
	 * 
	 * @param x
	 *            Feld für x-Koordinaten
	 * @param y
	 *            Feld für y-Koordinaten
	 */
	public void polygon(double[] x, double[] y) {
		// System.out.println("poly");
		if (x.length > 2 && x.length == y.length) {
			int[] xi;
			int[] yi;
			xi = new int[x.length];
			yi = new int[y.length];
			for (int i = 0; i < xi.length; i++) {
				xi[i] = (int) Math.round(x[i]);
				yi[i] = (int) Math.round(y[i]);
			}

			gr = Stift.this.grafikKontext();
			setGZustand(gr);
			int n = x.length;
			if (gr != null) {
				setGZustand(gr);
				if (muster == Muster.DURCHSICHTIG) {
					gr.drawPolygon(xi, yi, n);
				} else {
					if (muster == Muster.GEFUELLT) {
						gr.fillPolygon(xi, yi, n);
					}
				}
			}
			this.sync();
			gr = null;
		}
	}

	/**
	 * 
	 * @return aktuelle vertikale Koordinate der Stiftposition
	 */
	public double vPosition() {
		return stifty;
	}

	/**
	 * 
	 * @return aktuelle horizontale Koordinate der Stiftposition
	 */
	public double hPosition() {
		return stiftx;
	}

	/**
	 * Setzt einen Stiftzustand (Position, Linienbreite etc.
	 */
	public void setzeZustand(StiftZustand zustand) {
		aktuellerFont = zustand.getAktuellfont();
		schriftStil = zustand.getSchriftstil();
		schriftGroesse = zustand.getSchriftgroesse();
		schriftArt = zustand.getSchriftart();
		farbe = zustand.getFarbe();
		linienBreite = zustand.getLinienbreite();
		stiftx = zustand.getStiftx();
		muster = zustand.getZmuster();
		stifty = zustand.getStifty();
		stiftOben = zustand.isHoch();
		stiftRichtung = zustand.getWinkel();
		schreibModus = zustand.getSchreibmodus();
	}

	/**
	 * Liefert den aktuellen Stiftzustand (Position, Linienbreite etc.
	 */
	public StiftZustand holeZustand() {
		return new StiftZustand(aktuellerFont, schriftStil, schriftGroesse,
				schriftArt, farbe, linienBreite, stiftx, stifty, muster,
				stiftOben, stiftRichtung, schreibModus);
	}

	/**
	 * Speichert den aktuellen Stiftzustand (Position, Linienbreite etc.
	 */
	public void merkeZustand() {
		merkeZustand = this.holeZustand();
	}

	/**
	 * Setzt den gespeicherten Stiftzustand zurück. Es wird nicht gezeichnet.
	 */
	public void restauriereZustand() {
		this.setzeZustand(merkeZustand);
	}

	/**
	 * 
	 * @return liefert die eingestellte Linienbreite
	 */
	public int linienBreite() {
		return this.linienBreite;
	}

}
