package basiX;

import java.awt.*;
import java.awt.event.AWTEventListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.lang.reflect.Method;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.LineBorder;

import basiX.swing.BufferedCanvas;
import basiX.swing.JFrameMitKenntFenster;
import basiX.swing.PaneCanvas;
import basiX.swing.TriplePaneCanvas;
import basiX.vw.*;

/**
 * Komponenten sind Oberflächenelemente.
 */
public  abstract class Komponente {
	private java.awt.Component p;

	protected Container kenntContainer;
	protected Container meineKomponente;
	protected transient Vector mausListeners = new Vector();
	protected Komponente elternKomponente;
	private transient Vector komponentenLauscher;
	private transient Vector mausListenersX = new Vector();
	private double x, y;

	/**
	 * ein Fenster darf von seinem Super-Konstruktor nicht auf einem Fenster
	 * platziert werden erst mal vorläüfig
	 * 
	 * @param fenster
	 */
	public Komponente(boolean fenster) {
		kenntContainer = null;
	}
	public Komponente(PaneCanvas jc, JFrame f) {
		meineKomponente = jc;
		elternKomponente = null;
		jc.addComponentListener(new ComponentListener() {

			public void componentHidden(ComponentEvent arg0) {
				Komponente.this.fireKomponentenEreignis();
			}

			public void componentMoved(ComponentEvent arg0) {
				Komponente.this.fireKomponentenEreignis();
			}

			public void componentResized(ComponentEvent arg0) {
				Komponente.this.fireKomponentenEreignis();
			}

			public void componentShown(ComponentEvent arg0) {
				Komponente.this.fireKomponentenEreignis();
			}
		});
		// this.mouseListenerErzeugen();
		this.mausListenerErzeugen();
		// mauslistenerErzeugenAlternativ();
		try {
			kenntContainer = f;
			((JFrame) kenntContainer).setContentPane((BufferedCanvas) meineKomponente);
			kenntContainer.repaint();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * vorläufiger Konstruktor für Leinwand direkt auf Fenster, wird wieder
	 * entfernt!!
	 */
	public Komponente(BufferedCanvas jc, JFrame f) {
		meineKomponente = jc;
		elternKomponente = null;
		jc.addComponentListener(new ComponentListener() {

			public void componentHidden(ComponentEvent arg0) {
				Komponente.this.fireKomponentenEreignis();
			}

			public void componentMoved(ComponentEvent arg0) {
				Komponente.this.fireKomponentenEreignis();
			}

			public void componentResized(ComponentEvent arg0) {
				Komponente.this.fireKomponentenEreignis();
			}

			public void componentShown(ComponentEvent arg0) {
				Komponente.this.fireKomponentenEreignis();
			}
		});
		// this.mouseListenerErzeugen();
		this.mausListenerErzeugen();
		// mauslistenerErzeugenAlternativ();
		try {
			kenntContainer = f;
			((JFrame) kenntContainer).setContentPane((BufferedCanvas) meineKomponente);
			kenntContainer.repaint();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * erzeugt auf einem Fenster eine Komponente mit linker oberer Ecke (x,y)
	 * vorgegebener Breite b und Hoehe h, das Fenster muss vorher erzeugt sein
	 */
	public Komponente(double x, double y, double breite, double hoehe) {
		this(null, x, y, breite, hoehe, DasFenster.hauptLeinwand());
	}

	/**
	 * erzeugt auf einem Fenster eine Komponente mit linker oberer Ecke (x,y)
	 * vorgegebener Breite b und Hoehe h, das Fenster muss vorher erzeugt sein
	 */
	public Komponente(JComponent k, double x, double y, double breite,
			double hoehe) {
		this(k, x, y, breite, hoehe, DasFenster.hauptLeinwand());
	}

	/**
	 * erzeugt auf einem Fensterobjekt eine Komponente, mit linker oberer Ecke
	 * (10,10) der vorgegebenen Breite 100 und Hoehe 20, ein Fenster muss vorher
	 * erzeugt sein
	 */
	public Komponente() {
		this(null, 10, 10, 100, 20, DasFenster.hauptLeinwand());
	}

	/**
	 * erzeugt auf einem Fenster f einen Knopf mit Aufschrift s, mit linker
	 * oberer Ecke (x,y) vorgegebener Breite b und Hoehe h, das Fenster muss
	 * vorher erzeugt sein
	 */
	public Komponente(double x, double y, double b, double h, Fenster f) {
		this(null, x, y, b, h, f.leinwand());
	}

	/**
	 * erzeugt auf einem Containerobjekt le eine Komponente mit Aufschrift s,
	 * mit linker oberer Ecke (x,y) vorgegebener Breite b und Hoehe h, der
	 * Container muss vorher erzeugt sein
	 */
	public Komponente(JComponent jc, double x, double y, double b, double h,
			Komponente le) {
		meineKomponente = jc;
		elternKomponente = le;
		// Sonderfall JFrame
		jc.addComponentListener(new ComponentListener() {

			public void componentHidden(ComponentEvent arg0) {
				Komponente.this.fireKomponentenEreignis();
			}

			public void componentMoved(ComponentEvent arg0) {
				Komponente.this.fireKomponentenEreignis();
			}

			public void componentResized(ComponentEvent arg0) {
				Komponente.this.fireKomponentenEreignis();
			}

			public void componentShown(ComponentEvent arg0) {
				Komponente.this.fireKomponentenEreignis();
			}
		});
		// this.mouseListenerErzeugen();
		this.mausListenerErzeugen();
		// mauslistenerErzeugenAlternativ();
		this.setzePosition(x, y);
		this.setzeGroesse(b, h);
		this.betteEinIn(le);
		meineKomponente.repaint();
		meineKomponente.validate();
//		Toolkit.getDefaultToolkit().sync();
	}
	/**
	 * erzeugt auf einem Containerobjekt le eine Komponente mit Aufschrift s,
	 * mit linker oberer Ecke (x,y) vorgegebener Breite b und Hoehe h, der
	 * Container muss vorher erzeugt sein
	 */
	public Komponente(JComponent jc, double x, double y, double b, double h,
			Komponente le,boolean sichtbar) {
		meineKomponente = jc;
		jc.setVisible(false);
		elternKomponente = le;
		// Sonderfall JFrame
		jc.addComponentListener(new ComponentListener() {

			public void componentHidden(ComponentEvent arg0) {
				Komponente.this.fireKomponentenEreignis();
			}

			public void componentMoved(ComponentEvent arg0) {
				Komponente.this.fireKomponentenEreignis();
			}

			public void componentResized(ComponentEvent arg0) {
				Komponente.this.fireKomponentenEreignis();
			}

			public void componentShown(ComponentEvent arg0) {
				Komponente.this.fireKomponentenEreignis();
			}
		});
		// this.mouseListenerErzeugen();
		this.mausListenerErzeugen();
		// mauslistenerErzeugenAlternativ();
		this.setzePosition(x, y);
		this.setzeGroesse(b, h);
		this.betteEinIn(le);
		jc.setVisible(sichtbar);
		meineKomponente.repaint();
		meineKomponente.validate();
//		Toolkit.getDefaultToolkit().sync();
	}
	
	protected TastenLauscherVerwalter tlv;

	/** Ereignisbearbeitung registriert einen TastenLauscher */
	public synchronized void setzeTastenLauscher(TastenLauscher l) {
		if (tlv == null) {
			tlv = new TastenLauscherVerwalter(this);
		}
		tlv.setzeTastenLauscher(l);
	}

	/** Ereignisbearbeitung entfernt einen TastenLauscher */
	public synchronized void entferneTastenLauscher(TastenLauscher l) {
		if (tlv != null) {
			tlv.entferneTastenLauscher(l);
		}
	}

	/**
	 * 
	 * @return liefert die Elternkomponente der Komponente
	 */
	public Komponente elternKomponente() {
		return elternKomponente;
	}

	protected void setzeElternKomponente(Komponente komponente) {
		elternKomponente = komponente;
	}

	public void betteEin(Komponente e) {
		if (e == null || e.elternKomponente == this) {
			return;
		}
		// alte Elternkomponente merken
		Komponente ealt = e.elternKomponente();
		meineKomponente.add(e.getSwingComponent());
		e.setzeElternKomponente(this);
		ealt.getSwingComponent().repaint(0);
		this.getSwingComponent().repaint(0);
	}

	/** Bettet die Komponente in eine Elternkomponente c ein */
	public void betteEinIn(Komponente c) {
		if (c == null) {
			return;
		}
		elternKomponente = c;
		try {
			Container alterContainer = meineKomponente.getParent();
			kenntContainer = c.meineKomponente;
			if (c instanceof ScrollFlaeche) {
				JViewport jw = ((JScrollPane) kenntContainer).getViewport();
				jw.add(meineKomponente);
				((JScrollPane) kenntContainer).setViewport(jw);
				kenntContainer.validate();
				kenntContainer.repaint(0);
			} else if (c instanceof TextBereich) {
				JViewport jw = ((JScrollPane) kenntContainer).getViewport();
				((JTextArea) jw.getComponent(0)).add(meineKomponente);
				kenntContainer.validate();
				kenntContainer.repaint(0);
			} else {
				// kenntContainer.setLayout(null);
				kenntContainer.add(meineKomponente);
			}
			if (c instanceof WahlBoxGruppenFlaeche && this instanceof WahlBox) {
				// aber erst einmal sehen, ob sie vorher in einer anderen
				// WahlBoxGruppe war
				if (((WahlBox) this).kenntWahlBoxGruppe != null) {
					((WahlBox) this).entferneWahlBoxGruppe();
				}
				// WahlBox in WahlBoxGruppe der WahlBoxGruppenFlaeche einbinden
				((WahlBoxGruppenFlaeche) c).registriereWahlBox((WahlBox) this);
			}
			if (alterContainer != null) {
				alterContainer.repaint();
			}
			if (kenntContainer != null) {
				kenntContainer.repaint(0);
			}

		} catch (Exception e) {
			System.out.println("Einbettungsfehler");
		}
	}

	/** positioniert die Komponente mit der linken oberen Ecke auf (x,y)) */
	public void setzePosition(double x, double y) {
		meineKomponente.setLocation((int) Math.round(x), (int) Math.round(y));
		// Probeweise deaktiviert
		// if (meineKomponente.getParent() != null) {
		// meineKomponente.getParent().validate();
		// }
		this.x = x;
		this.y = y;
	}

	/** */
	protected void syncAndRepaint(int x, int y, int b, int h) {
		// if (kenntLeinwand != null) {
		// //kenntLeinwand.sync();
		// Rectangle r = new Rectangle(x, y, b, h);
		// JPanel jp = (JPanel) kenntLeinwand.meineKomponente;
		// Component[] c = jp.getComponents();
		// for (int i = 0; i < c.length; i++) {
		// if (r.intersects(c[i].getBounds())) {
		// c[i].repaint();
		// }
		// }
		// // kenntLeinwand.meineKomponente.repaint();
		// }
	}

	/**
	 * ändert Breite und Hoehe der Komponente falls breite oder hoehe kleiner
	 * als 0 ist hat der Aufruf keine Wirkung
	 */
	public void setzeGroesse(double b, double h) {
		if (b < 0 || h < 0) {
			return;
		}
		meineKomponente.setSize((int) Math.round(b), (int) Math.round(h));
		if (!(meineKomponente instanceof JTextArea)) {
			meineKomponente.setPreferredSize(new Dimension((int) Math.round(b),
					(int) Math.round(h)));
		}
		if (meineKomponente.getParent() != null) {
			meineKomponente.getParent().validate();
		}
	}

	/** ändert die Hintergrundfarbe der Komponente zu farbe */
	public void setzeHintergrundFarbe(Color farbe) {
		if (meineKomponente instanceof JComponent) {
			((JComponent) meineKomponente).setOpaque(true);
		}
		meineKomponente.setBackground(farbe);

	}

	/** setzt die Schriftgröße */
	public void setzeSchriftGroesse(int g) {
		try {
			String name = meineKomponente.getFont().getName();
			int stil = meineKomponente.getFont().getStyle();
			meineKomponente.setFont(new Font(name, stil, g));
			// meineKomponente.getToolkit().sync();
		} catch (Exception e) {
			System.out.println("falsche Schriftgroesse");
		}
	}

	/** ändert die Schriftfarbe des Elementes */
	public void setzeSchriftFarbe(Color farbe) {
		meineKomponente.setForeground(farbe);
	}

	/**
	 * setzt den Schriftstil, vorgegebene Stile sind in Basis: Schrift.FETT oder
	 * Schrift.KURSIV oder Schrift.KURSIVFETT oder Schrift.STANDARDSTIL
	 */
	public void setzeSchriftStil(int stil) {
		try {
			String name = meineKomponente.getFont().getName();
			int groesse = meineKomponente.getFont().getSize();
			meineKomponente.setFont(new Font(name, stil, groesse));
		} catch (Exception e) {
			System.out.println("falscher Schriftstil");
		}
	}

	/**
	 * setzt die Schriftart, vorgegebene Arten sind in basis: Schrift.HELVETICA
	 * oder Schrift.TIMESROMAN oder Schrift.STANDARDSCHRIFTART
	 */
	public void setzeSchriftArt(String a) {
		try {
			int stil = meineKomponente.getFont().getStyle();
			int groesse = meineKomponente.getFont().getSize();
			meineKomponente.setFont(new Font(a, stil, groesse));
		} catch (Exception e) {
			System.out.println("falsche Schriftart");
		}
	}

	/**
	 * zeigt das Oberflaechenelement an, wenn der Parameter auf true gesetzt
	 * wird, sonst wird es unsichtbar
	 */
	public void setzeSichtbar(boolean sb) {
		meineKomponente.setVisible(sb);
		

	}
	
	public boolean istSichtbar(){
		return meineKomponente.isVisible();
	}

	/**
	 * Schaltet die Funktionstüchtigkeit des Steuerelementes an (Parameterwert
	 * true) oder ab (false)
	 */
	public void setzeBenutzbar(boolean bb) {
		meineKomponente.setEnabled(bb);
	}

	/** setzt den Fokus auf diese Komponente */
	public void setzeFokus() {
		meineKomponente.requestFocus();
	}

	/** liefert die horizontale Koordinate der linken oberen Ecke */
	public double hPosition() {
		return Math.abs(x - meineKomponente.getLocation().x) < 1 ? x
				: meineKomponente.getLocation().x;

	}

	/** liefert vertikale Koordinate der linken oberen Ecke */
	public double vPosition() {
		return Math.abs(y - meineKomponente.getLocation().y) < 1 ? y
				: meineKomponente.getLocation().y;
	}

	/** liefert die Komponentenbreite */
	public int breite() {
		return meineKomponente.getSize().width;
	}

	/** liefert die Komponentenhoehe */
	public int hoehe() {
		return meineKomponente.getSize().height;
	}

	/*
	 * void writeObject(ObjectOutputStream oos) throws IOException {
	 * oos.defaultWriteObject(); }
	 * 
	 * void readObject(ObjectInputStream ois) throws ClassNotFoundException,
	 * IOException { ois.defaultReadObject(); }
	 */

	public Container getSwingComponent() {
		Container c;
		return meineKomponente;
	}

	/**
	 * entfernt die Komponente. Diese ist nicht mehr benutzbar.
	 */
	public void gibFrei() {
		this.setzeSichtbar(false);
		Container c = meineKomponente.getParent();
		c.remove(meineKomponente);
	}

//	public void methodenListe() {
//		Method[] m = this.getClass().getMethods();
//		for (int i = 0; i < m.length; i++) {
//			System.out.println(m[i].getName());
//		}
//	}

	/** registriert einen KomponentenLauscher */
	public synchronized void setzeKomponentenLauscher(KomponentenLauscher l) {
		Vector v = komponentenLauscher == null ? new Vector(2)
				: (Vector) komponentenLauscher.clone();
		if (!v.contains(l)) {
			v.addElement(l);
			komponentenLauscher = v;
		}
	}

	/** entfernt einen KomponentenLauscher */
	public synchronized void entferneKomponentenLauscher(KomponentenLauscher l) {
		if (komponentenLauscher != null && komponentenLauscher.contains(l)) {
			Vector v = (Vector) komponentenLauscher.clone();
			v.removeElement(l);
			komponentenLauscher = v;
		}
	}

	protected void fireKomponentenEreignis() {
		if (komponentenLauscher != null) {
			Vector listeners = komponentenLauscher;
			int count = listeners.size();
			for (int i = 0; i < count; i++)

				((KomponentenLauscher) listeners.elementAt(i))
						.bearbeiteKomponentenVeraenderung(this);

		}
	}

	/** registriert einen MausLauscher */
	public synchronized void setzeMausLauscherStandard(MausLauscherStandard l) {
		Vector v = mausListeners == null ? new Vector(2)
				: (Vector) mausListeners.clone();
		if (!v.contains(l)) {
			v.addElement(l);
			mausListeners = v;
		}
	}

	/** entfernt einen MausLauscher */
	public synchronized void entferneMausLauscherStandard(MausLauscherStandard l) {
		if (mausListeners != null && mausListeners.contains(l)) {
			Vector v = (Vector) mausListeners.clone();
			v.removeElement(l);
			mausListeners = v;
		}
	}

	/** registriert einen MausLauscher */
	public synchronized void setzeMausLauscherErweitert(MausLauscherErweitert l) {
		Vector v = mausListenersX == null ? new Vector(2)
				: (Vector) mausListenersX.clone();
		if (!v.contains(l)) {
			v.addElement(l);
			mausListenersX = v;
		}
	}

	/** entfernt einen MausLauscher */
	public synchronized void entferneMausLauscherErweitert(
			MausLauscherErweitert l) {
		if (mausListenersX != null && mausListenersX.contains(l)) {
			Vector v = (Vector) mausListenersX.clone();
			v.removeElement(l);
			mausListenersX = v;
		}
	}

	protected void mausListenerErzeugen() {

		Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {
			boolean gleich;
			boolean enthalten;

			public void eventDispatched(AWTEvent event) {
				Component aktuell;
				Komponente aktuellK;
//				System.out.println(event.getSource());

				if (!(mausListenersX.isEmpty() && mausListeners.isEmpty())
						&& event instanceof MouseEvent) {

					MouseEvent e = (MouseEvent) event;
					Component quelle = (Component) e.getSource();
					aktuellK = Komponente.this;
					aktuell = aktuellK.getSwingComponent();
					Component p = quelle;
//					System.out.println(quelle);
					gleich = quelle == aktuell;
					enthalten = gleich;
					if (!gleich) {
						while (p != null) {
							if (p == aktuell) {
								enthalten = true;
								break;
							}
							p = p.getParent();
						}
					}
					if (enthalten) {
						e = SwingUtilities
								.convertMouseEvent(quelle, e, aktuell);
						switch (e.getID()) {
						case MouseEvent.MOUSE_CLICKED:
							if (!e.isMetaDown()) {
								if (e.getClickCount() > 1) {
									Komponente.this.fireDoppelKlick(aktuellK, e
											.getX(), e.getY());
								} else {
									Komponente.this.fireMausKlick(aktuellK, e
											.getX(), e.getY());

								}
							} else {
								Komponente.this.fireMausKlickRechts(aktuellK, e
										.getX(), e.getY());
							}
							break;
						case MouseEvent.MOUSE_DRAGGED:
							Komponente.this.fireMausBewegt(aktuellK, e.getX(),
									e.getY());
							Komponente.this.fireMausGezogen(aktuellK, e.getX(),
									e.getY());
							break;
						case MouseEvent.MOUSE_ENTERED:
							// System.out.println(quelle);
							if (gleich) {
								Komponente.this.fireMausHinein(aktuellK, e
										.getX(), e.getY());
							}
							break;
						case MouseEvent.MOUSE_EXITED:
							if (gleich) {
								Komponente.this.fireMausHeraus(aktuellK, e
										.getX(), e.getY());
							}
							break;
						case MouseEvent.MOUSE_MOVED:
							Komponente.this.fireMausBewegt(aktuellK, e.getX(),
									e.getY());
							break;
						case MouseEvent.MOUSE_PRESSED:
							if (!e.isMetaDown()) {
								Komponente.this.fireMausDruck(aktuellK, e
										.getX(), e.getY());
							} else {

								Komponente.this.fireMausDruckRechts(aktuellK, e
										.getX(), e.getY());
							}
							break;
						case MouseEvent.MOUSE_RELEASED:
							if (!e.isMetaDown()) {
								Komponente.this.fireMausLos(aktuellK, e.getX(),
										e.getY());
							} else {
								Komponente.this.fireMausLosRechts(aktuellK, e
										.getX(), e.getY());
							}
							break;
						}
					}
				}
			}
		}, AWTEvent.MOUSE_EVENT_MASK + AWTEvent.MOUSE_MOTION_EVENT_MASK);

	}

	protected void mouseListenerErzeugen() {
		meineKomponente.addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent e) {
				if (!e.isMetaDown()) {
					if (e.getClickCount() > 1) {
						Komponente.this.fireDoppelKlick(Komponente.this, e
								.getX(), e.getY());
					} else {

						Komponente.this.fireMausKlick(Komponente.this,
								e.getX(), e.getY());
					}

				} else {

					Komponente.this.fireMausKlickRechts(Komponente.this, e
							.getX(), e.getY());
				}
			}

			public void mousePressed(MouseEvent e) {
				if (!e.isMetaDown()) {
					Komponente.this.fireMausDruck(Komponente.this, e.getX(), e
							.getY());
				} else {

					Komponente.this.fireMausDruckRechts(Komponente.this, e
							.getX(), e.getY());
				}
			}

			public void mouseReleased(MouseEvent e) {
				if (!e.isMetaDown()) {
					Komponente.this.fireMausLos(Komponente.this, e.getX(), e
							.getY());
				} else {

					Komponente.this.fireMausLosRechts(Komponente.this,
							e.getX(), e.getY());
				}
			}

			public void mouseEntered(MouseEvent e) {

				Komponente.this.fireMausHinein(Komponente.this, e.getX(), e
						.getY());
			}

			public void mouseExited(MouseEvent e) {

				Komponente.this.fireMausHeraus(Komponente.this, e.getX(), e
						.getY());
			}

		});
		meineKomponente.addMouseMotionListener(new MouseMotionListener() {

			public void mouseDragged(MouseEvent e) {

				Komponente.this.fireMausBewegt(Komponente.this, e.getX(), e
						.getY());
				Komponente.this.fireMausGezogen(Komponente.this, e.getX(), e
						.getY());
			}

			public void mouseMoved(MouseEvent e) {
				Komponente.this.fireMausBewegt(Komponente.this, e.getX(), e
						.getY());
			}
		});
	}

	protected void fireMausDruck(Komponente e, int x, int y) {
		if (mausListeners != null) {
			Vector listeners = mausListeners;
			int count = listeners.size();
			for (int i = 0; i < count; i++) {
				((MausLauscherStandard) listeners.elementAt(i))
						.bearbeiteMausDruck(e, x, y);
			}
		}
	}

	protected void fireMausDruckRechts(Komponente e, int x, int y) {
		if (mausListeners != null) {
			Vector listeners = mausListeners;
			int count = listeners.size();
			for (int i = 0; i < count; i++) {
				((MausLauscherStandard) listeners.elementAt(i))
						.bearbeiteMausDruckRechts(e, x, y);
			}
		}
	}

	protected void fireMausLos(Komponente e, int x, int y) {
		if (mausListeners != null) {
			Vector listeners = mausListeners;
			int count = listeners.size();
			for (int i = 0; i < count; i++) {
				((MausLauscherStandard) listeners.elementAt(i))
						.bearbeiteMausLos(e, x, y);
			}
		}
	}

	protected void fireMausLosRechts(Komponente e, int x, int y) {
		if (mausListeners != null) {
			Vector listeners = mausListeners;
			int count = listeners.size();
			for (int i = 0; i < count; i++) {
				((MausLauscherStandard) listeners.elementAt(i))
						.bearbeiteMausLosRechts(e, x, y);
			}
		}
	}

	protected void fireMausBewegt(Komponente e, int x, int y) {
		if (mausListeners != null) {
			Vector listeners = mausListeners;
			int count = listeners.size();
			for (int i = 0; i < count; i++) {
				((MausLauscherStandard) listeners.elementAt(i))
						.bearbeiteMausBewegt(e, x, y);
			}
		}
	}

	protected void fireMausGezogen(Komponente e, int x, int y) {
		if (mausListeners != null) {
			Vector listeners = mausListeners;
			int count = listeners.size();
			for (int i = 0; i < count; i++) {
				((MausLauscherStandard) listeners.elementAt(i))
						.bearbeiteMausGezogen(e, x, y);
			}
		}
	}

	protected void fireMausKlick(Komponente e, int x, int y) {
		if (mausListenersX != null) {
			Vector listeners = mausListenersX;
			int count = listeners.size();
			for (int i = 0; i < count; i++) {
				((MausLauscherErweitert) listeners.elementAt(i))
						.bearbeiteMausKlick(e, x, y);
			}
		}
	}

	protected void fireMausKlickRechts(Komponente e, int x, int y) {
		if (mausListenersX != null) {
			Vector listeners = mausListenersX;
			int count = listeners.size();
			for (int i = 0; i < count; i++) {
				((MausLauscherErweitert) listeners.elementAt(i))
						.bearbeiteMausKlickRechts(e, x, y);
			}
		}
	}

	protected void fireDoppelKlick(Komponente e, int x, int y) {
		if (mausListenersX != null) {
			Vector listeners = mausListenersX;
			int count = listeners.size();
			for (int i = 0; i < count; i++) {
				((MausLauscherErweitert) listeners.elementAt(i))
						.bearbeiteDoppelKlick(e, x, y);
			}
		}
	}

	protected void fireMausHinein(Komponente e, int x, int y) {
		if (mausListenersX != null) {
			Vector listeners = mausListenersX;
			int count = listeners.size();
			for (int i = 0; i < count; i++) {
				((MausLauscherErweitert) listeners.elementAt(i))
						.bearbeiteMausHinein(e, x, y);
			}
		}
	}

	protected void fireMausHeraus(Komponente e, int x, int y) {
		if (mausListenersX != null) {
			Vector listeners = mausListenersX;
			int count = listeners.size();
			for (int i = 0; i < count; i++) {
				((MausLauscherErweitert) listeners.elementAt(i))
						.bearbeiteMausHeraus(e, x, y);
			}
		}
	}

	/**
	 * 
	 * @return liefert die Hintergrundfarbe der Komponente
	 */
	public Color hintergrundFarbe() {
		return meineKomponente.getBackground();
	}

	/**
	 * 
	 * @return liefert die Schriftfarbe der Komponente
	 */
	public Color schriftFarbe() {
		return meineKomponente.getForeground();
	}

	/**
	 * 
	 * @return liefert die Schriftgröße der Komponente
	 */
	public int schriftGroesse() {
		return meineKomponente.getFont().getSize();
	}

	/**
	 * Mit Hilfe dieser Methode kann man festlegen, ob das Objekt vor oder
	 * hinter anderen Objekten erscheint, wenn diese sich überlappen. Je
	 * niedriger der Wert für z ist, desto weiter oben wird das Objekt
	 * angeordnet. 0 ist der niedrigste z-Wert Der größt mögliche z-Wert wird
	 * durch die Anzahl der in der Spritewelt untergebrachten Komponenten
	 * bestimmt. Befinden sich <b>zum Zeitpunkt des Aufrufs der Methode</b> n
	 * Komponenten in der Spritewelt so ist n-1 der höchste wählbar z-Wert.
	 * Komponenten mit gleichem z-Wert überdecken sich in Abhängigkeit von der
	 * Erstellungsreihenfolge: Ist Objekt a vor Objekt b erzeugt worden, so
	 * liegt a über b, bei gleichem z-Wert.
	 * 
	 * @param z
	 */
	public void setzeZOrdnung(int z) {
		this.getSwingComponent().getParent().setComponentZOrder(
				this.getSwingComponent(), z);
		this.getSwingComponent().repaint();

	}

	/**
	 * liefert die zOrdnung der Komponente
	 * 
	 * @see basiX.Komponente.setzeZOrdnung
	 */
	public int getZOrdnung() {
		return this.getSwingComponent().getParent().getComponentZOrder(
				this.getSwingComponent());
	}

	/**
	 * setzt einen Rand
	 */
	public void setzeRand(Color farbe, int breite) {
		if (this.getSwingComponent() instanceof JComponent) {
			((JComponent) this.getSwingComponent()).setBorder(new LineBorder(
					farbe,breite));
		}
	}

	/**
	 * entfernt einen Rand
	 */
	public void entferneRand() {
		if (this.getSwingComponent() instanceof JComponent) {
			((JComponent) this.getSwingComponent()).setBorder(null);
		}
	}

	
}
