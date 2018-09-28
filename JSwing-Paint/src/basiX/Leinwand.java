package basiX;

import java.awt.*;
import java.io.*;
import java.util.*;
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseListener;
//import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
//import java.awt.print.PageFormat;
//import java.awt.print.Printable;
//import java.awt.print.PrinterException; //import java.awt.print.PrinterGraphics;
////import java.awt.print.PrinterJob;
//
//import javax.print.*;
//import javax.print.attribute.*;
//import javax.print.attribute.standard.*;

import javax.imageio.*; //import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
//import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
//import javax.imageio.stream.ImageOutputStream; 
//import javax.imageio.stream.ImageOutputStream;

//import javax.swing.ImageIcon;
//import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
//import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;
//import javax.swing.filechooser.FileFilter;

//import java.io.File;

import basiX.swing.BufferedCanvas;
import basiX.swing.BufferedCanvasListener;
import basiX.swing.Picture;
import basiX.vw.*;

/** überarbeitet Mai 2011 
 * Eine Leinwand beschreibt einen rechteckigen Bereich. Auf ihm kann mit Stiften
 * gezeichnet werden. Zu diesem Zweck ist die Leinwand mit einem
 * Koordinatensystem versehen, dessen Ursprung sich in der oberen linken Ecke
 * befindet und dessen Achsen horizontal nach rechts und vertikal nach unten
 * gerichtet sind. Die Einheit ist ein Pixel. Um eine Leinwand verwenden zu
 * können, müssen ein Fenster oder ein Container-Objekt vorhanden sein, auf das
 * die Leinwand aufgebracht werden kann. Dies geschieht bei der Konstruktion
 * gegebenenfalls mit Hilfe eines Parameters, der eine Kenntbeziehung zu dem
 * Bildschim oder Containerobjekt herstellt. Der Zeichenbereich eines
 * Fensterobjekts ist von vornherein von einer Leinwand vollständig bedeckt.
 * Leinwandobjkekte können selbst als Container für andere Leinwandobejkte
 * genommen werden. So kann man eine Zeichenfläche in unabhängige Bereiche
 * aufteilen. Zur Ereignisbearbeitung: Ob die Leinwand durch Überdecken mit
 * einem Fenster, Größenveränderung oder ähnliches vom System neu gezeichnet
 * wurde, kann über die Anfrage wurdeNeuGezeichnet ermittelt werden. Alternativ
 * wird jede Auswahl einem angemeldeten LeinwandLauscher signalisiert. siehe:
 * Auftrag setzeLeinwandLauscher(LeinwandLauscher l) siehe: Interface
 * LeinwandLauscher Ferner stehen leere Methoden zur Ereignisbearbeitung von
 * Mausereignissen zur Verfügung, so dass spezialisierte Leinwandklassen
 * gebildet werden können.
 */

public class Leinwand extends Komponente implements Serializable,
		StiftFlaeche, LeinwandIF {

	protected boolean nochnichtsichtbargewesen = true;
	protected boolean leinwandneugezeichnet;

	private Color zhintergrundfarbe = Farbe.WEISS;

	// protected Graphics grafikKontext=null;
	protected Toolkit toolkit;

	private transient Vector leinwandListeners;
	// private TastenLauscherVerwalter tlv;

//	private boolean transparent = true;
//	private Graphics gk = null;
//	private boolean neuGezeichnet = true;

	/**
	 * Vorläufig, kommt wieder weg
	 */
	public Leinwand(JFrame f, boolean alsCP) {
		super(new BufferedCanvas(), f);
		// this.setBackground(zhintergrundfarbe);
		try {
			// this.mouseListenerErzeugen();
			this.fakeMouseListenerErzeugen();
			this.canvasListenerErzeugen();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
/**
 *  wenn kein Mouselistener vorhanden, werden die Mausevents an parent geleitet,
 *  damit ist dann die Herkunft nicht mehr identifizierbar.
 *  Siehe Bug ID 4413412
 */
	protected void fakeMouseListenerErzeugen() {
		
		meineKomponente.addMouseListener(new MouseInputAdapter() {
		});
		meineKomponente.addMouseMotionListener(new MouseInputAdapter() {
		});

	}

	private void canvasListenerErzeugen() {
		if (meineKomponente != null) {
			((BufferedCanvas) meineKomponente).addBufferedCanvasListener(new BufferedCanvasListener() {
				
				public void bufferedCanvasRepainted(BufferedCanvas l) {
					fireLeinwandGezeichnet(Leinwand.this);
				}
			});
		}

	}

	/**
	 * erzeugt eine Leinwand mit Position (0,0) (linke obere Ecke) und Null
	 * Pixel Breite und Höhe auf einem vorhandenen Fensterobjekt
	 */
	public Leinwand() {
		super(new BufferedCanvas(), 0, 0, 0, 0);
		// this.setBackground(zhintergrundfarbe);
		try {
			// this.mouseListenerErzeugen();
			this.fakeMouseListenerErzeugen();
			this.canvasListenerErzeugen();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * erzeugt eine Leinwand mit Position (0,0) (linke obere Ecke) und Null
	 * Pixel Breite und Höhe auf einer Komponente c. c kann selbst eine Leinwand
	 * sein.
	 */
	public Leinwand(Komponente c) {
		this(0, 0, 0, 0, c);
	}

	/**
	 * erzeugt eine Leinwand mit Position (x,y) (linke obere Ecke) und den
	 * angegebenen Dimensionen auf einem Container c.c kann selbst eine Leinwand
	 * sein.
	 */
	public Leinwand(double x, double y, double b, double h, Komponente c) {
		super(new BufferedCanvas(), x, y, b, h, c);
		
		try {
			// this.mouseListenerErzeugen();
			this.fakeMouseListenerErzeugen();
			this.canvasListenerErzeugen();
			this.setzeHintergrundFarbe(zhintergrundfarbe);
			meineKomponente.repaint();
			// this.setBackground(zhintergrundfarbe);

		} catch (Exception e) {
			System.out.println(" Leinwand konnte nicht erstellt werden.");
		}
	}

	/**
	 * erzeugt eine Leinwand mit Position (x,y) (linke obere Ecke) und den
	 * angegebenen Dimensionen auf der standardmäßig vorhandenene Leinwand eines
	 * bereits erzeugten Fensters
	 */
	public Leinwand(double x, double y, double b, double h) {
		this(x, y, b, h, DasFenster.hauptLeinwand());
	}

	/**
	 * erzeugt eine Leinwand mit Position (x,y) (linke obere Ecke) und den
	 * angegebenen Dimensionen auf der standardmäßig vorhandenene Leinwand des
	 * Fensters
	 * 
	 * 
	 */
	public Leinwand(double x, double y, double b, double h, Fenster f) {
		this(x, y, b, h, f == null ? null : f.leinwand());
	}

	/** setzt die Hintergrundfarbe für die Leinwand */
	public void setzeHintergrundFarbe(Color c) {
		zhintergrundfarbe = c;
		((BufferedCanvas) meineKomponente).setBackground(c);	
		
	}

	/** leert die Leinwand. */
	public void loescheAlles() {
		this.setzeHintergrundFarbe(zhintergrundfarbe);
//		BufferedImage img = ((BufferedCanvas) meineKomponente).getBufferedImage();
//		try {
////			Graphics gr = meineKomponente.getGraphics();
////			gr.setColor(zhintergrundfarbe);
////			gr.fillRect(0, 0, meineKomponente.getSize().width, meineKomponente
////					.getSize().height);
//			Graphics gh = img.createGraphics();
//			gh.setColor(zhintergrundfarbe);
//			gh.fillRect(0, 0, img.getWidth(), img.getHeight());
//			meineKomponente.repaint();
//			((BufferedCanvas) meineKomponente).sync();
//		} catch (Exception e) {
//		}

	}

	/** registriert einen LeinwandLauscher */
	public synchronized void setzeLeinwandLauscher(LeinwandLauscher l) {
		Vector v = leinwandListeners == null ? new Vector(2)
				: (Vector) leinwandListeners.clone();
		if (!v.contains(l)) {
			v.addElement(l);
			leinwandListeners = v;
		}
	}

	/** entfernt einen LeinwandLauscher */
	public synchronized void entferneLeinwandLauscher(LeinwandLauscher l) {
		if (leinwandListeners != null && leinwandListeners.contains(l)) {
			Vector v = (Vector) leinwandListeners.clone();
			v.removeElement(l);
			leinwandListeners = v;
		}
	}

	/**
	 * liefert genau dann wahr, wenn die Leinwand seit dem letzten Aufruf dieser
	 * Methode neu gezeichnet wurde, etwa durch Größenänderung oder Verschieben.
	 */
	public boolean wurdeNeuGezeichnet() {
		boolean merke = this.leinwandneugezeichnet;
		this.leinwandneugezeichnet = false;
		return merke;
	}

	

	/** liefert die Farbe des Hintergrundes */
	public Color hintergrundFarbe() {
		return zhintergrundfarbe;
	}

//	/**
//	 * stellt einen Grafikkontext f&uuml;r Malwerkzeuge, wie den Stift, zur
//	 * Verf&uuml;gung. Um Stifte threadsicherer zu machen, bekommt jeder seinen
//	 * eigenen Grafikkontext
//	 * 
//	 * 
//	 */
//	public Graphics grafikKontext() {
//		return meineKomponente.getGraphics();
//	}

	/**
	 * leert die Leinwand.
	 * 
	 * 
	 */
	public void löscheAlles() {
		this.loescheAlles();
	}

	public boolean ladeBild(String pfad, boolean transparent) {
		this.setzeTransparenz(transparent);
		return this.ladeBild(pfad);
	}

	public boolean ladeBild(String pfad) {
		return ((BufferedCanvas) meineKomponente).setImageIcon(pfad);
//		pic = new Picture(pfad);
//		((BufferedCanvas) meineKomponente).setBufferedImage(pic.getRecentImage());
	}

	
	
	public void setzeTransparenz(boolean t) {
//		this.transparent = t;
		((BufferedCanvas) this.meineKomponente).setOpaque(!t);
		this.sync();
	}

	/**
	 * für Debuggingzwecke
	 */
	private void info(String s) {
		if (Einstellungen.DEBUGMODUS)
			System.out.println(s);
	}

	/**  */
	void writeObject(ObjectOutputStream oos) throws IOException {
		oos.defaultWriteObject();
	}

	/**  */
	void readObject(ObjectInputStream ois) throws ClassNotFoundException,
			IOException {
		ois.defaultReadObject();
	}

	/**  */
	protected void fireLeinwandGezeichnet(Leinwand e) {
		if (leinwandListeners != null) {
			Vector listeners = leinwandListeners;
			int count = listeners.size();
			for (int i = 0; i < count; i++) {
				((LeinwandLauscher) listeners.elementAt(i))
						.leinwandWurdeGezeichnet(e);
			}
		}
	}

	/**  */
	protected void sync() {
		((BufferedCanvas) this.meineKomponente).sync(); // bei Bedarf wieder
		// aktivieren

	}

	/** Bearbeitung des Leinwand Neu Gezeichnet Ereignisses */
	public void bearbeiteLeinwandNeuGezeichnet() {
		// if (this == DasFenster.hauptLeinwand())System.out.println("Lw
		// neugez");
	}

	public BufferedImage hintergrundBild() {
		return ((BufferedCanvas) meineKomponente).getBufferedImage();
	}
	
	public Picture leinwandZuBild(){
		return new Picture(((BufferedCanvas) meineKomponente).getBufferedImage());
	}

	

	/**
	 * Speichert den Leinwandinhalt ohne die eventuell auf der Leinwand befindlichen Komponenten als Bild
	 * 
	 * @param dateiname
	 *            Pfad in der Form "LW:/Ordner/..../Dateiname.typ" oder
	 *            "/Ordner/..../Dateiname.typ" oder "/Dateiname.typ" Typ : gif,
	 *            jpg, png oder bmp. Fehlt der Typ, so wird jpg als Typ angenommen
	 */
	public boolean speichereUnter(String dateiname) {
		return leinwandZuBild().saveRecent(dateiname);
	}

	

	

	/**
	 * Öffnet Dateidialog zum Öffnen einer png-, jpg-, gif oder bmp-Bilddatei. Diese wird
	 * eingelesen und und auf der Zeichenfläche angezeigt
	 */
	public boolean ladeBild() {
		try {
			JFileChooser fc = new JFileChooser();
			fc.setFileFilter(Picture.filefilter);
			int returnVal = fc.showOpenDialog(null);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				((BufferedCanvas) meineKomponente).setBufferedImage(ImageIO.read(fc
						.getSelectedFile()));
			} else {
				return false;
			}

		} catch (Exception exc) {
			return false;
		}
		return true;

	}

	

	/**
	 * Speichert Zeichenfläche über einen Dialog. Als Dateiformat kommen
	 * gif, jpg, bmp und png in Frage
	 * 
	 * @return false bei Abbruch oder sonstigem Fehler
	 */

	public boolean speichere() {
		return this.leinwandZuBild().saveByDialog();	
	}
	
	

	// private static double dbldgt(double d) {
	// return Math.round(d * 10.) / 10.; // show one digit after point
	// }

	public void drucke() {
        this.leinwandZuBild().print();
    }

	public Color farbeVon(int x, int y) {
		try {
			Robot r;
			r = new Robot();
			return r.getPixelColor(x
					+ this.getSwingComponent().getLocationOnScreen().x, y
					+ this.getSwingComponent().getLocationOnScreen().y);
		} catch (AWTException e) {
			e.printStackTrace();
		}
		return null;
	}

	
	public Graphics getBufferedImageGraphics() {
		return ((BufferedCanvas) this.getSwingComponent()).getBufferedImageGraphics();
				
	}

	
	public void aktualisiereDarstellung() {

	}

	public void setzeFarbeBei(int x,int y,Color c) {
		((BufferedCanvas) meineKomponente).getBufferedImage().setRGB(x,y,c.getRGB());
		}
	
	

}
