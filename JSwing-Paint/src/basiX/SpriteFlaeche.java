package basiX;

import java.awt.*; //import java.io.*;
//import java.util.*;
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseListener;
//import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage; //import java.awt.print.PageFormat;
//import java.awt.print.Printable;
//import java.awt.print.PrinterException; //import java.awt.print.PrinterGraphics;
//import java.awt.print.PrinterJob;

//import javax.print.*;
//import javax.print.attribute.*;
//import javax.print.attribute.standard.*;

//import javax.imageio.*; 
//import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
//import javax.imageio.stream.ImageOutputStream;

//import javax.swing.ImageIcon;
//import javax.swing.JComponent;
//import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.event.MouseInputAdapter; //import javax.swing.JPanel;
//import javax.swing.filechooser.FileFilter;

//import java.io.File;

import basiX.swing.BufferedCanvas;
import basiX.vw.*;

/**
 * erstellt Dezember 2009 Eine SpriteFlaeche beschreibt einen rechteckigen
 * Bereich. Die SpriteFlaeche ist mit einem Koordinatensystem versehen, dessen
 * Ursprung sich in der oberen linken Ecke befindet und dessen Achsen horizontal
 * nach rechts und vertikal nach unten gerichtet sind. Die Einheit ist ein
 * Pixel. Um eine SpriteFlaeche verwenden zu können, muss eine Komponente
 * vorhanden sein, auf die die SpriteFlaeche aufgebracht werden kann. Dies
 * geschieht bei der Konstruktion gegebenenfalls mit Hilfe eines Parameters, der
 * eine Kenntbeziehung zu der Komponente herstellt. Zur Ereignisbearbeitung: Ob
 * die SpriteFlaeche durch Überdecken mit einem Fenster, Größenveränderung oder
 * ähnliches vom System neu gezeichnet wurde, kann über die Anfrage
 * wurdeNeuGezeichnet ermittelt werden.
 */

public class SpriteFlaeche extends Komponente implements StiftFlaeche {

//	protected boolean nochnichtsichtbargewesen = true;
	protected boolean leinwandneugezeichnet;

    private Color zhintergrundfarbe = Farbe.WEISS;

	// protected Graphics grafikKontext=null;
	protected Toolkit toolkit;

	// private boolean transparent = false;
	// private Graphics gk = null;
	// private boolean neuGezeichnet = true;

	/**
	 * erzeugt eine SpriteFlaeche mit Position (0,0) (linke obere Ecke) und Null
	 * Pixel Breite und Höhe auf einem vorhandenen Fensterobjekt
	 */
	public SpriteFlaeche() {
		super(new BufferedCanvas(), 0, 0, 0, 0);
		// this.setBackground(zhintergrundfarbe);
		try {
			this.fakeMouseListenerErzeugen();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * erzeugt eine SpriteFlaeche mit Position (0,0) (linke obere Ecke) und Null
	 * Pixel Breite und Höhe auf einer Komponente c. c kann selbst eine
	 * SpriteFlaeche sein.
	 */
	public SpriteFlaeche(Komponente c) {
		this(0, 0, 0, 0, c);
	}

	/**
	 * erzeugt eine SpriteFlaeche mit Position (x,y) (linke obere Ecke) und den
	 * angegebenen Dimensionen auf einem Container c.c kann selbst eine
	 * SpriteFlaeche sein.
	 */
	public SpriteFlaeche(double x, double y, double b, double h, Komponente c) {
		super(new BufferedCanvas(), x, y, b, h, c);
		// this.setLayout(null);
//		((BufferedCanvas) meineKomponente).setOpaque(true);
		if (c != null) {
			if (c.meineKomponente instanceof JFrame) {
				((JFrame) c.meineKomponente).setContentPane(meineKomponente);
			} else {
				try {
					c.meineKomponente.add(meineKomponente, BorderLayout.CENTER);
				} catch (Exception exc1) {
					c.meineKomponente.add(meineKomponente);
				}
			}
		}
		try {
			this.fakeMouseListenerErzeugen();
			this.setzeHintergrundFarbe(zhintergrundfarbe);
			meineKomponente.repaint();
			// this.setBackground(zhintergrundfarbe);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * wenn kein Mouselistener vorhanden, werden die Mausevents an parent
	 * geleitet, damit ist dann die Herkunft nicht mehr identifizierbar. Siehe
	 * Bug ID 4413412
	 */
	protected void fakeMouseListenerErzeugen() {

		meineKomponente.addMouseListener(new MouseInputAdapter() {
		});
		meineKomponente.addMouseMotionListener(new MouseInputAdapter() {
		});

	}

	/**
	 * erzeugt eine SpriteFlaeche mit Position (x,y) (linke obere Ecke) und den
	 * angegebenen Dimensionen auf der standardmäßig vorhandenene Leinwand eines
	 * bereits erzeugten Fensters
	 */
	public SpriteFlaeche(double x, double y, double b, double h) {
		this(x, y, b, h, DasFenster.hauptLeinwand());

	}

	/**
	 * erzeugt eine SpriteFlaeche mit Position (x,y) (linke obere Ecke) und den
	 * angegebenen Dimensionen auf der standardmäßig vorhandenene Leinwand des
	 * Fensters
	 * 
	 * 
	 */
	public SpriteFlaeche(double x, double y, double b, double h, Fenster f) {
		this(x, y, b, h, f == null ? null : f.leinwand());
	}

	/** setzt die Hintergrundfarbe für die SpriteFlaeche */
	public void setzeHintergrundFarbe(Color c) {
		zhintergrundfarbe = c;
		((BufferedCanvas) meineKomponente).setBackground(c);
	}

	/** leert die SpriteFlaeche. */
	public void loescheAlles() {
		BufferedImage img = ((BufferedCanvas) meineKomponente).getBufferedImage();
		try {
			Graphics gr = meineKomponente.getGraphics();
			gr.setColor(zhintergrundfarbe);
			gr.fillRect(0, 0, meineKomponente.getSize().width, meineKomponente
					.getSize().height);
			Graphics gh = img.createGraphics();
			gh.setColor(zhintergrundfarbe);
			gh.fillRect(0, 0, img.getWidth(), img.getHeight());
			meineKomponente.repaint();
		} catch (Exception e) {
		}

	}

	/**
	 * liefert genau dann wahr, wenn die SpriteFlaeche seit dem letzten Aufruf
	 * dieser Methode neu gezeichnet wurde, etwa durch Größenänderung oder
	 * Verschieben.
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
//	 */
//	public Graphics grafikKontext() {
//		return meineKomponente.getGraphics();
//	}

	/**
	 * leert die SpriteFlaeche.
	 */
	public void löscheAlles() {
		this.loescheAlles();
	}

	public void ladeBild(String pfad, boolean transparent) {
		this.setzeTransparenz(transparent);
		this.ladeBild(pfad);
	}

	public void ladeBild(String pfad) {
		((BufferedCanvas) meineKomponente).setImageIcon(pfad);
	}

	/**
	 * Legt fest, inwieweit die gesamte Spritefläche transparent dargestellt
	 * werden soll
	 * 
	 * @param a
	 *            Grad: zwischen 0 und 1 (jeweils einschließlich)
	 */
	public void setzeTransparenzGrad(double a) {
		((BufferedCanvas) this.meineKomponente).setAlpha((float) a);
	}

	/**
	 * Legt fest, ob die SpriteFlaeche eine transparente Farbe enthalten kann
	 * Dies hat nichts mit der Setzung des Transparenzgrades zu tun.
	 * 
	 * @param t
	 */
	public void setzeTransparenz(boolean t) {
		// this.transparent = t;
		((BufferedCanvas) this.meineKomponente).setOpaque(!t);
	}

	/**  */
	protected void sync() {
		((BufferedCanvas) this.meineKomponente).sync();

	}

	/** Bearbeitung des SpriteFlaeche Neu Gezeichnet Ereignisses */
	public void bearbeiteLeinwandNeuGezeichnet() {
		// if (this == DasFenster.hauptLeinwand())System.out.println("Lw
		// neugez");
	}

	public BufferedImage hintergrundBild() {
		return ((BufferedCanvas) meineKomponente).getBufferedImage();
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
/**
 * liefert ein Graphics-Objekt des zur Komponente gehörenden BufferedImage
 */
	public Graphics getBufferedImageGraphics() {
		return ((BufferedCanvas) this.getSwingComponent()).getBufferedImageGraphics();
				
	}

	public void aktualisiereDarstellung() {
		// TODO Auto-generated method stub

	}

}
