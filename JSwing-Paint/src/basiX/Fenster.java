package basiX;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

//import basis.Tastatur.Talau;
import basiX.swing.JFrameMitKenntFenster;
import basiX.vw.*;

/**
 * Ein Fensterobjekt ist das Modell eines rechteckigen Bereiches auf dem
 * angeschlossenen Computermonitor, also ein Fenster. Auf seiner Zeichenfläche
 * kann mit Stiften gezeichnet werden. Zu diesem Zweck ist die Zeichenebene auf
 * dem Fenster mit einem Koordinatensystem versehen, dessen Ursprung sich in der
 * oberen linken Ecke der Zeichenebene befindet und dessen Achsen horizontal
 * nach rechts und vertikal nach unten gerichtet sind. Einheiten sind Pixel. Ein
 * Mausklick auf den Schließen-Knopf in der rechten obere Ecke des Fensters
 * schließt dieses. Das erste in einer Anwendung erzeugte Fensterobjekt wird als
 * Hauptfenster der Anwendung betrachtet. Wird dieses geschlossen, so wird die
 * Anwendung ebenfalls beendet. Zur Ereignisbearbeitung: Ob eine Änderung in der
 * Darstellung des Fensters erfolgt ist, kann über die Anfrage
 * wurdeNeuGezeichnet ermittelt werden. Ferner kann das
 * Fensterschliessenereignis bearbeitet werden. Dazu muss das Interface
 * FensterLauscher implementiert werden, in dem die Methode
 * bearbeiteFensterWurdeGeschlossen enthalten sind.
 * 
 * @author Georg Dick
 * 
 */
public class Fenster extends Komponente implements Serializable, StiftFlaeche {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2514251533546492672L;
	/**
	 * 
	 */
	public static boolean erzeugungMitInvoke = true;
	private static boolean einfacheErzeugung = false;

	public static void setErzeugungMitInvoke(boolean emi) {
		erzeugungMitInvoke = emi;
	}

	// private Vector tastenLauscher;
	protected Dimension dimFenster = new Dimension(400, 300);
	protected Leinwand leinwand;
	protected EreignisAnwendung meineEreignisAnwendung = null;

	private boolean istMac = System.getProperty("os.name").equals("Mac OS");
	private boolean lwda = false;
	private transient Vector<FensterLauscher> fensterLauscher;
	private JFrameMitKenntFenster meinJFrame;

	public JFrameMitKenntFenster getMeinJFrame() {
		return meinJFrame;
	}

	/**
	 * Erzeugt ein Fenster. Er ist mit der Breite 400 und der Hoehe 300 Pixel
	 * initialisiert und sichtbar.
	 */
	public Fenster() {
		this("Fenster", 400, 300, true);
	}

	/**
	 * Erzeugt ein Fenster. Er ist mit der Breite 400 und der Hoehe 300 Pixel
	 * initialisiert und genau dann sichtbar, wenn der Parameter true ist.
	 * 
	 * 
	 */
	public Fenster(boolean sichtbar) {
		this("Fenster", 400, 300, sichtbar);
	}

	/**
	 * erzeugt ein Fenster mit vorgegebener Breite, Höhe und Sichtbarkeit.
	 * 
	 * 
	 */
	public Fenster(int breite, int hoehe, boolean sichtbar) {
		this("Fenster", breite, hoehe, sichtbar);
	}

	/**
	 * erzeugt ein Fenster mit vorgegebener Breite.
	 * 
	 * 
	 */
	public Fenster(int breite, int hoehe) {
		this("Fenster", breite, hoehe, true);
	}

	protected void binit(int breite, int hoehe, String titel, boolean sichtbar) {
		//meinJFrame.setVisible(false);
		meinJFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		leinwand = new Leinwand(meinJFrame, true);// nur sehr vorläufig!!
		// this.setContentPane(leinwand.meineKomponente);// nur sehr vorläufig!!
		leinwand.setzeLeinwandLauscher(new LeinwandLauscher() {
			public void leinwandWurdeGezeichnet(LeinwandIF l) {
				lwda = true;
			}
		});

		meinJFrame.setTitle(titel);
		if (meinJFrame.getContentPane() instanceof Component) {
			((Component) meinJFrame.getContentPane())
					.setPreferredSize(new Dimension(breite, hoehe));
		}
		meinJFrame.pack();

		/*
		 * erst mal nur um Titelleiste und Seitenränder festzustellen, also
		 * nicht weglassen (!), diese Zeile ist nicht überflüssig
		 */
		dimFenster = new Dimension(breite + meinJFrame.getInsets().left
				+ meinJFrame.getInsets().right, hoehe
				+ meinJFrame.getInsets().top + meinJFrame.getInsets().bottom);

		meinJFrame.setSize(dimFenster);

		//		
		//		
		//		
		leinwand.setzeHintergrundFarbe(Farbe.WEISS);
		meinJFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				// System.out.println("Ich schließe mich");
				if (Frame.getFrames().length == 1) {
					// System.out.println("Ich bin der letzte meiner Art");
					meinJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				}
			}
		});
		meinJFrame.setVisible(sichtbar);

		// try{
		// leinwand.paintComponent(leinwand.getGraphics());
		// } catch(Exception e){}
		// this.getToolkit().sync();
		// do {
		// info("warte");
		// } while (!lwda);
		// info("warte ende lwda");

		this.registriere(); // für Tastatur! (nicht mehr!!)

		// mausListenerErzeugen();
		meinJFrame.addComponentListener(new ComponentListener() {

			public void componentHidden(ComponentEvent arg0) {
				Fenster.this.fireKomponentenEreignis();
			}

			public void componentMoved(ComponentEvent arg0) {
				Fenster.this.fireKomponentenEreignis();
			}

			public void componentResized(ComponentEvent arg0) {
				Fenster.this.fireKomponentenEreignis();
			}

			public void componentShown(ComponentEvent arg0) {
				Fenster.this.fireKomponentenEreignis();
			}
		});

	}

	/**
	 * Das erste Fenster, das eine Anwendung erzeugt wird als Klassenobjekt
	 * gespeichert
	 * 
	 */
	protected void registriere() {
		DasFenster.setzeHauptFenster(this);
	}

	
	/**
	 * erzeugt ein Fenster mit vorgegebenem Titel, der angegebenen Breite und
	 * Höhe. Das Fenster ist sichtbar.
	 * 
	 * 
	 */
	public Fenster(String titel, int breite, int hoehe) {
		this(titel, breite, hoehe, true);
	}

	/**
	 * erzeugt ein Fenster mit vorgegebenem Titel, der angegebenen Breite und
	 * Höhe sowie vorgegebener Sichtbarkeit.
	 * 
	 * 
	 */
	int b;
	int h;
	String t;
	boolean s;
//	private boolean ready;
	

	public Fenster(String titel, int breite, int hoehe, boolean sichtbar) {
		super(true);
		t = titel;
		b = breite;
		h = hoehe;
		s = sichtbar;
		if (einfacheErzeugung ){
		 meineKomponente = meinJFrame = new
		 JFrameMitKenntFenster(Fenster.this);
		 binit(b, h, t, s);} else
		if (erzeugungMitInvoke) {
			try {
				SwingUtilities.invokeAndWait(new Runnable() {
					public void run() {
						meineKomponente = meinJFrame = new JFrameMitKenntFenster(
								Fenster.this);
						meineKomponente.setVisible(false);
						binit(b, h, t, s);
					}
				});
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		} else {
			try {
				new Thread() {
					public void run() {
						meineKomponente = meinJFrame = new JFrameMitKenntFenster(
								Fenster.this);
						binit(b, h, t, s);
					}
				}.start();
			} catch (Exception e) {
				e.printStackTrace();
			}

			while (!lwda) {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}// ende von else

	}

	/**
	 * 
	 */
//	public Dimension getPreferredSize() {
//		return this.dimFenster;
//	}

	/** setzt die Fenstergroesse */
	public void setzeGroesse(int breite, int hoehe) {

		dimFenster = new Dimension(breite + meinJFrame.getInsets().left
				+ meinJFrame.getInsets().right, hoehe
				+ meinJFrame.getInsets().top + meinJFrame.getInsets().bottom);
		meinJFrame.setSize(dimFenster);
		// this.pack();
		// this.setVisible(true);
		meinJFrame.getToolkit().sync();

	}

	/** Setzt die Position auf dem Monitor */
	public void setzePosition(int x, int y) {
		meinJFrame.setLocation(x, y);
	}

	/** liefert die horizontale Position auf dem Monitor */
	public double hPosition() {
		return meinJFrame.getLocationOnScreen().x;
	}

	/** liefert die vertikale Position auf dem Monitor */
	public double vPosition() {
		return meinJFrame.getLocationOnScreen().y;
	}

	/** setzt einen Fenstertitel */
	public void setzeTitel(String titel) {
		meinJFrame.setTitle(titel);
	}

	/** bestimmt die Sichtbarkeit des Fensters */
	public void setzeSichtbar(boolean sb) {
		meinJFrame.setVisible(sb);
	}

	/** setzt die angegebene Hintergrundfarbe (s. Klasse Farbe) */
	public void setzeHintergrundFarbe(Color c) {
		leinwand.setzeHintergrundFarbe(c);
		meinJFrame.repaint();
	}

	/** leert die Zeichenfläche. */
	public void loescheAlles() {
		leinwand.loescheAlles();
	}

	/**
	 * das Fenster wird geschlossen, abgebaut und falls es das Hauptfenster ist,
	 * wird die Anwendung beendet
	 */
	public void gibFrei() {
		meinJFrame.setVisible(false);
		leinwand.gibFrei();
		if (this == DasFenster.hauptFenster()) {
			DasFenster.setzeHauptFenster(null);
		}
		this.fireFenster(this);
		meinJFrame.dispose();
		if (this == DasFenster.anwendungsfenster()) {
			DasFenster.loescheAnwendungsfenster();
			System.exit(0);
		}
	}

	

	

	/** Ereignisbearbeitung registriert einen FensterLauscher */
	public synchronized void setzeFensterLauscher(FensterLauscher l) {
		Vector<FensterLauscher> v = fensterLauscher == null ? new Vector<FensterLauscher>(2)
				: (Vector<FensterLauscher>)fensterLauscher.clone();
		if (!v.contains(l)) {
			v.addElement(l);
			fensterLauscher = v;
		}
	}

	/** Ereignisbearbeitung entfernt einen FensterLauscher */
	public synchronized void entferneFensterLauscher(FensterLauscher l) {
		if (fensterLauscher != null && fensterLauscher.contains(l)) {
			Vector<FensterLauscher> v = (Vector<FensterLauscher>) fensterLauscher.clone();
			v.removeElement(l);
			fensterLauscher = v;
		}
	}

	
	/** registriert einen StandardMausLauscher */
	public synchronized void setzeMausLauscherStandard(MausLauscherStandard l) {
		this.leinwand().setzeMausLauscherStandard(l);
	}

	/** registriert einen erweiterten MausLauscher */
	public synchronized void setzeMausLauscherErweitert(MausLauscherErweitert l) {
		this.leinwand().setzeMausLauscherErweitert(l);
	}

	/** entfernt einen StandardMausLauscher */
	public synchronized void entferneMausLauscherStandard(MausLauscherStandard l) {
		this.leinwand().entferneMausLauscherStandard(l);
	}

	/** entfernt einen erweiterten MausLauscher */
	public synchronized void entferneMausLauscherErweitert(
			MausLauscherErweitert l) {
		this.leinwand().entferneMausLauscherErweitert(l);
	}

	/**
	 * liefert genau dann den Wert true, wenn das Fenster aufgrund einer
	 * Bewegung oder Größenänderung neu dargestellt wurde
	 */
	public boolean wurdeNeuGezeichnet() {
		return this.leinwand().wurdeNeuGezeichnet();
	}

	/** liefert die Breite der Zeichenebene. */
	public int breite() {
		return leinwand.breite();
	}

	/** liefert die H&ouml;he der Zeichenebene. */
	public int hoehe() {
		return leinwand.hoehe();
	}

	/** liefert die Farbe des Hintergrundes der Zeichenfläche */
	public Color hintergrundFarbe() {
		return leinwand.hintergrundFarbe();
	}

	/** liefert die Farbe Punktes (x,y) der Zeichenfläche */
	public Color farbeVon(int x, int y) {
		return leinwand.farbeVon(x, y);
	}


	/**
	 * für Debuggingzwecke
	 * 
	 * 
	 */
	private void info(String s) {
		if (Einstellungen.DEBUGMODUS)
			System.out.println(s);
	}

	/**  */
	public void bearbeiteAndereEreignisse() {
		if (meineEreignisAnwendung != null) {
			meineEreignisAnwendung.bearbeiteAndereEreignisse();
			// this.dispatchEvent(new AnderesEreignis(new
			// Event(this,java.awt.AWTEvent.RESERVED_ID_MAX+1,this)));
		}
	}

	/**
	 * 
	 * 
	 * 
	 */
	public void addAndereEreignisseListener(EreignisAnwendung ea) {
		this.meineEreignisAnwendung = ea;
	}

	/** liefert die Leinwand des Fensters */
	public Leinwand leinwand() {
		return this.leinwand;
	}

	/**  */
	protected void fireFenster(Fenster e) {
		if (fensterLauscher != null) {
			Vector listeners = fensterLauscher;
			int count = listeners.size();
			for (int i = 0; i < count; i++)
				((FensterLauscher) listeners.elementAt(i))
						.bearbeiteFensterWurdeGeschlossen(e);
		}
	}

	/**
	 * speichert die Zeichenfläche des Fensters jpg, png, gif oder bmp-Grafik.
	 * Bei unpassender Dateiendung wird eine jpg-Grafik erzeugt.
	 * Nach erfolgreicher Operation wird true, sonst
	 * false zurück gegeben
	 */
	public boolean speichereZeichenflaecheUnter(String dateiname) {
		return leinwand.speichereUnter(dateiname);
	}

	

	/**
	 * speichert die Zeichenfläche des Fensters als jpg,png, gif oder bmp-Grafik.  
	 * Zur Festlegung der Datei wird ein Dialog
	 * verwendet. Nach erfolgreicher Operation wird true, sonst false zurück
	 * gegeben
	 */
	public boolean speichereZeichenflaeche() {
		return leinwand.speichere();
	}

	

	/**
	 * Liest jpg, png, gif oder bmp-Datei und zeigt sie auf der Zeichenfläche an, nach
	 * erfolgreicher Operation wird true, sonst false zurück gegeben
	 */
	public boolean ladeBildInZeichenflaeche(String dateiname) {
		return this.leinwand.ladeBild(dateiname);
	}

	/**
	 * Liest jpg-oder png-Datei und zeigt sie auf der Zeichenfläche an, nach
	 * erfolgreicher Operation wird true, sonst false zurück gegeben
	 */
	public boolean ladeBildInZeichenflaeche() {
		return this.leinwand.ladeBild();
	}

	/**
	 * Druckt die Zeichenfläche.
	 * 
	 */
	public void druckeZeichenflaeche() {
		this.leinwand.drucke();
	}

	
	public Graphics getBufferedImageGraphics() {
		return this.leinwand.getBufferedImageGraphics();
	}

	
	public void aktualisiereDarstellung() {

	}

	/**
	 * Für Fenster kann die ZOrdnung nicht verändert werden
	 */
	public void setzeZOrdnung(int z) {

	}

	/**
	 * Fenster haben eine zOrdnung von -1
	 */
	public int getZOrdnung() {
		return -1;
	}
	// /**
	// * Bearbeitung des Maus-Ereignisses: linke Maustaste wurde gedrückt. Die
	// * Parameter enthalten die aktuelle Mausposition
	// */

	/**
	 * liefert den Fenstertitel
	 */
	public String titel() {		
		return meinJFrame.getTitle();
	}
	
	/**
	 * maximiert das Fenster, reduziert die Höhe jedoch um 30 Pixel.
	 */

	public void maximiereMitGrößenVerstellung(){
		 dimFenster = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
		 dimFenster.setSize(dimFenster.width, (dimFenster.height-30));
		 meinJFrame.setSize(dimFenster);

		 }

	/**
	 * maximiert das Fenster.
	 */

		public void maximiereOhneGrößenVerstellung(){
		 meinJFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		 }

	// /** Ereignisbearbeitung registriert einen TastenLauscher */
	// public synchronized void setzeTastenLauscher(TastenLauscher l) {
	// Vector v = tastenLauscher == null ? new Vector(2)
	// : (Vector) tastenLauscher.clone();
	// if (!v.contains(l)) {
	// v.addElement(l);
	// tastenLauscher = v;
	// }
	// }
	//
	// /** Ereignisbearbeitung entfernt einen TastenLauscher */
	// public synchronized void entferneTastenLauscher(TastenLauscher l) {
	// if (tastenLauscher != null && tastenLauscher.contains(l)) {
	// Vector v = (Vector) tastenLauscher.clone();
	// v.removeElement(l);
	// tastenLauscher = v;
	// }
	// }
	//
	// /** */
	// protected void fireTastenDruck(Komponente e,KeyEvent k) {
	// if (tastenLauscher != null) {
	// Vector listeners = tastenLauscher;
	// int count = listeners.size();
	// for (int i = 0; i < count; i++)
	// ((TastenLauscher) listeners.elementAt(i)).bearbeiteTaste(e,
	// this.zeichenVon(k));
	// }
	// }

}