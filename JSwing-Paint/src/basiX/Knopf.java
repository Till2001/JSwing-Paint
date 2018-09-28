package basiX;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import basiX.vw.*;

/**
 * Knöpfe sind Oberflächenelemente, die eine Aufschrift tragen und mit der Maus
 * angeklickt werden können. Sie dienen so dem Benutzer dazu Aktionen
 * auszulösen. Zur Ereignisbearbeitung: Ob ein Knopfdruck erfolgt ist, kann über
 * die Anfrage wurdeGedrueckt ermittelt werden. Alternativ kann jeder Knopfdruck
 * einem angemeldeten KnopfLauscher signalisiert werden. siehe: Auftrag
 * setzeKnopfLauscher(KnopfLauscher l) siehe: Interface KnopfLauscher
 * 
 */
/**
 * Experimentelle Version, bei der die Ereignisantworten grundsätzlich in eigene
 * Threads gesteckt werden Damit wird verhindert, dass das System auf das
 * Beenden der Ereignisantwort wartet und währenddessen alle
 * GUI-Aktualisierungen blockiert
 */
public class Knopf extends Komponente implements Serializable {
	/**
	 * Vielleicht umgeht man so das Hilfe.kurzePause, kl muss im Konstruktor
	 * noch erzeugt werden
	 */
	private KLauscher kl;

	class KLauscher implements KnopfLauscher {
		Knopf kich;

		public KLauscher(Knopf ich) {
			ich.setzeKnopfLauscher(this);
			kich = ich;
		}

		public void bearbeiteKnopfDruck(Knopf k) {
			kich.wurdeGedrueckt = true;
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 8892943514364412033L;

	/**
	 * 
	 */
	/**
	 * Konstanten zur Ausrichtung des Inhaltes
	 */
	public final static int RECHTS = SwingConstants.RIGHT;

	public final static int LINKS = SwingConstants.LEFT;

	public final static int MITTE = SwingConstants.CENTER; // Standard

	public boolean ereignisBearbeitungInThreads = true;

	/**
	 * legt fest, ob bei einem Knopf die Ereignisbearbeitung nach einem
	 * Knopfdruck in Threads ablaufen soll oder nicht. Standard ist "true". Von
	 * Bedeutung ist dies, wenn Zeichenoperationen in der Ereignisantwort
	 * durchgeführt werden, deren Ablauf erkennbar sein soll. Ohne den Einsatz
	 * von Threads würde die Aktualsierung der Zeichnung auf das Ende des
	 * Ereignisantwort warten, da dann auch der Knopf wieder im nichtgedrückten
	 * Zustand gezeichnet wird.
	 * 
	 * @param eBiT
	 */
	public void setEreignisBearbeitungInThreads(boolean eBiT) {
		ereignisBearbeitungInThreads = eBiT;
	}

	private boolean wurdeGedrueckt = false;

	private transient Vector knopfLauscher;

	/**
	 * erzeugt auf einem Fenster einen Knopf mit Aufschrift s, mit linker oberer
	 * Ecke (x,y) vorgegebener Breite b und Hoehe h, das Fenster muss vorher
	 * erzeugt sein
	 */
	public Knopf(String s, double x, double y, double breite, double hoehe) {
		this(s, x, y, breite, hoehe, DasFenster.hauptLeinwand());
	}

	/**
	 * erzeugt auf einem Fensterobjekt einen Knopf mit Aufschrift s, mit linker
	 * oberer Ecke (10,10) der vorgegebenen Breite 100 und Hoehe 20, ein Fenster
	 * muss vorher erzeugt sein
	 * 
	 */
	public Knopf() {
		this("", 10, 10, 100, 20, DasFenster.hauptLeinwand());
	}

	/**
	 * erzeugt auf einem Fensterobjekt einen Knopf mit Aufschrift s, mit linker
	 * oberer Ecke (10,10) der vorgegebenen Breite 100 und Hoehe 20, ein Fenster
	 * muss vorher erzeugt sein
	 * 
	 */
	public Knopf(String s) {
		this(s, 10, 10, 100, 20, DasFenster.hauptLeinwand());
	}

	/**
	 * erzeugt auf einem Fenster f einen Knopf mit Aufschrift s, mit linker
	 * oberer Ecke (x,y) vorgegebener Breite b und Hoehe h, das Fenster muss
	 * vorher erzeugt sein
	 * 
	 */
	public Knopf(String s, double x, double y, double b, double h, Fenster f) {
		this(s, x, y, b, h, f.leinwand());
	}

	/**
	 * erzeugt auf einem Containerobjekt le einen Knopf mit Aufschrift s, mit
	 * linker oberer Ecke (x,y) vorgegebener Breite b und Hoehe h, der Container
	 * muss vorher erzeugt sein
	 * 
	 */
	public Knopf(String s, double x, double y, double b, double h, Komponente le) {
		super(new JButton(s), x, y, b, h, le);

		try {
			((JButton) meineKomponente)
					.addKeyListener(new java.awt.event.KeyAdapter() {
						public void keyPressed(java.awt.event.KeyEvent e) {
							if (e.getKeyCode() == KeyEvent.VK_ENTER) {

								try {
									Robot r = new Robot();
									r.keyPress(KeyEvent.VK_SPACE);
								} catch (AWTException e1) {

								}

							}

						}

						public void keyReleased(java.awt.event.KeyEvent e) {
							if (e.getKeyCode() == KeyEvent.VK_ENTER) {
								try {
									Robot r = new Robot();
									r.keyRelease(KeyEvent.VK_SPACE);
								} catch (AWTException e1) {
								}

							}
						}
					});
			((JButton) meineKomponente)
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							// wurdeGedrueckt = true;
							// Knopf.this.fireKnopf(Knopf.this);
							// System.out.println("Action"+actionevent.getID()+"
							// "+actionevent.getSource());

							// Hängt ein KnopfEvent in die EventQueue. Damit
							// wartet das System nicht mit
							// der Neuzeichnung des Knopfes auf die Abarbeitung
							// des Events,funktioniert aber nicht!

							// SwingUtilities.invokeLater(new Runnable() {
							// public void run() {
							// Toolkit.getDefaultToolkit().getSystemEventQueue().
							// postEvent(new KnopfEvent( Knopf.this ));
							// }
							// });

							SwingUtilities.invokeLater(new Runnable() {
								public void run() {
									wurdeGedrueckt = true;
									// System.out.println("K");
									Knopf.this.fireKnopf(Knopf.this);

								}
							});
						}
					});
			// -->kl= new KLauscher(this);
		} catch (Exception e) {
			System.out
					.println(" Knopf konnte nicht erstellt werden, da zuvor kein");
			System.out.println(" Fensterobjekt o.a. erstellt wurde. ");
			System.out.println(" Bitte erst dieses erzeugen");
		}
		// Toolkit.getDefaultToolkit().addAWTEventListener( ael,
		// AWTEvent.ACTION_EVENT_MASK);
	}

	// AWTEventListener ael = new AWTEventListener() {
	// public void eventDispatched( AWTEvent event ) {
	// if (event instanceof KnopfEvent
	// && ((KnopfEvent)event).getKnopf()==Knopf.this
	// ){
	// Knopf.this.fireKnopf(Knopf.this);
	// }
	// }
	// };

	// private TastenLauscherVerwalter tlv;
	//
	// /** Ereignisbearbeitung registriert einen TastenLauscher */
	// public synchronized void setzeTastenLauscher(TastenLauscher l) {
	// if (tlv == null) {
	// tlv = new TastenLauscherVerwalter(meineKomponente);
	// }
	// tlv.setzeTastenLauscher(l);
	// }
	//
	// /** Ereignisbearbeitung entfernt einen TastenLauscher */
	// public synchronized void entferneTastenLauscher(TastenLauscher l) {
	// if (tlv != null) {
	// tlv.entferneTastenLauscher(l);
	// }
	// }

	/** verändert die Aufschrift des Knopfes */
	public void setzeText(String s) {
		((JButton) meineKomponente).setText(s);
	}

	/** Ereignisbearbeitung registriert einen KnopfLauscher */
	public synchronized void setzeKnopfLauscher(KnopfLauscher l) {
		Vector v = knopfLauscher == null ? new Vector(2)
				: (Vector) knopfLauscher.clone();
		if (!v.contains(l)) {
			v.addElement(l);
			knopfLauscher = v;
		}
	}

	/** Ereignisbearbeitung entfernt einen KnopfLauscher */
	public synchronized void entferneKnopfLauscher(KnopfLauscher l) {
		if (knopfLauscher != null && knopfLauscher.contains(l)) {
			Vector v = (Vector) knopfLauscher.clone();
			v.removeElement(l);
			knopfLauscher = v;
		}
	}

	/**
	 * Ereignisbearbeitung liefert genau dann wahr, wenn der Knopf seit dem
	 * letzten Aufruf dieser Methode gedrueckt wurde.
	 */
	public boolean wurdeGedrueckt() {
		boolean merke = wurdeGedrueckt;
		wurdeGedrueckt = false;
		return merke;
	}

	/** liefert die Aufschrift */
	public String text() {
		return ((JButton) meineKomponente).getText();
	}

	/*
	 * void writeObject(ObjectOutputStream oos) throws IOException {
	 * oos.defaultWriteObject(); }
	 * 
	 * void readObject(ObjectInputStream ois) throws ClassNotFoundException,
	 * IOException { ois.defaultReadObject(); }
	 */

	/**
	 * In Abhängigkeit von ereignisBearbeitungInThreads werden Ereignisantworten
	 * in eigene Threads gesteckt. Damit wird verhindert, dass das System auf
	 * das Beenden der Ereignisantwort wartet und währenddessen alle
	 * GUI-Aktualisierungen blockiert
	 */
	Vector listeners;

	Knopf keke;

	int iiii;

	protected void fireKnopf(Knopf e) {
		if (ereignisBearbeitungInThreads) {
			keke = e;
			if (knopfLauscher != null) {
				listeners = knopfLauscher;
				int count = listeners.size();
				for (int i = 0; i < count; i++)
					iiii = i;
				new Thread() {
					public void run() {
						((KnopfLauscher) listeners.elementAt(iiii))
								.bearbeiteKnopfDruck(keke);
					}
				}.start();
			}
		} else {
			if (knopfLauscher != null) {
				listeners = knopfLauscher;
				int count = listeners.size();
				for (int i = 0; i < count; i++)

					((KnopfLauscher) listeners.elementAt(i))
							.bearbeiteKnopfDruck(e);

			}
		}
	}

	/** Standardversion von fireKnopf */
	// protected void fireKnopf(Knopf e) {
	// if (knopfLauscher != null) {
	// Vector listeners = knopfLauscher;
	// int count = listeners.size();
	// for (int i = 0; i < count; i++)
	//			
	// ((KnopfLauscher) listeners.elementAt(i)).bearbeiteKnopfDruck(e);
	//						
	// }
	// }
	public void setzeIcon(String string) {
		((JButton) this.getSwingComponent()).setIcon(new ImageIcon(this
				.getClass().getResource(string)));

	}

	/**
	 * Knopf.LINKS, Knopf.MITTE, Knopf.RECHTS
	 * 
	 * @param ausrichtung
	 */
	public void setzeAusrichtung(int ausrichtung) {
		((JButton) this.getSwingComponent())
				.setHorizontalAlignment(ausrichtung);

	}

	/**
	 * Setzt den Tooltip für diesen Knopf
	 * 
	 * @param toolTip
	 *            Den Tooltip den man sehen soll.
	 * @author c.schuerhoff
	 */
	public void setToolTipText(String toolTip) {
		((JComponent) getSwingComponent()).setToolTipText(toolTip);
	}

	/**
	 * @return Den aktuellen Tooltip.
	 * @author c.schuerhoff
	 */
	public String getToolTipText() {
		return ((JComponent) getSwingComponent()).getToolTipText();
	}
}