package basiX;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import basiX.vw.*;

/**
 * Passwortfelder verhalten sich wie TextFelder. Zusätzlich kann man ein Echo-Zeichen
 * festlegen, das an Stelle der eigentlichen Eingabe angezeigt wird.
 * TextFelder sind Oberflaechenelemente, die es dem Benutzer ermöglichen
 * einzeilige Tastatureingaben vorzunehmen. Darüberhinaus können sie auch zur
 * Anzeige von einzeiligen Zeichenketten (Text, Zahlen etc.) genutzt werden. Mit
 * Hilfe der Maus können Textstellen markiert und gelöscht werden. Nach Druck
 * auf die Return-Taste verliert der TextBereich seinen Fokus und nimmt keine
 * weiteren Tastendrucke mehr entgegen. Zur Ereignisbearbeitung: Ob der Text des
 * Bereiches geändert wurde, kann über die Anfrage textWurdeGeaendert ermittelt
 * werden. Ob der die Return-Taste gedrueckt wurde, kann über die Anfrage
 * returnWurdeGedrueckt ermittelt werden. Alternativ wird jede Textänderung bzw.
 * der Druck der Return-Taste einem angemeldeten TextFeldLauscher signalisiert.
 * siehe: Auftrag setzeTextFeldLauscher(TextFeldLauscher l) siehe: Interface
 * TextFeldLauscher
 * 
 */
public class PasswortFeld extends Komponente implements Serializable {
	/**
	 * 
	 */
	private class DokuLis implements DocumentListener {
		
		public void changedUpdate(DocumentEvent e) {
			PasswortFeld.this.caupdate();
		}

		
		public void insertUpdate(DocumentEvent e) {
			PasswortFeld.this.caupdate();
		}

		
		public void removeUpdate(DocumentEvent e) {
			PasswortFeld.this.caupdate();
		}
	}

	protected DocumentListener dokuListener;
	private boolean wurdeGeaendert = false;
	private boolean returnWurdeGedrueckt = false;
	private boolean fokusVerloren = false;

	/**
	 */
	private transient Vector passwortFeldLauscher;

	/**
	 * erzeugt auf einem Fenster einen Textfeld mit linker oberer Ecke (x,y)
	 * vorgegebener Breite und Hoehe, das Fenster muss vorher erzeugt sein
	 */
	public PasswortFeld(double x, double y, double b, double h) {
		this(x, y, b, h, DasFenster.hauptLeinwand());
	}

	/**
	 * erzeugt auf einem Fensterobjekt einen Textfeld mit linker oberer Ecke
	 * (10,10 der Breite 10 und der Höhe 10) das Fenster muss vorher erzeugt
	 * sein
	 */
	public PasswortFeld() {
		this(10, 10, 10, 10, DasFenster.hauptLeinwand());
	}

	/**
	 * erzeugt auf dem Fenster f einen Textfeld mit linker oberer Ecke (x,y)
	 * vorgegebener Breite und Hoehe, das Fenster muss vorher erzeugt sein
	 * 
	 * 
	 */
	public PasswortFeld(double x, double y, double b, double h, Fenster f) {
		this(x, y, b, h, f.leinwand());
	}

	/**
	 * erzeugt Texfeldereignis
	 * 
	 * 
	 */
	protected void caupdate() {
		wurdeGeaendert = true;
		this.fireTextFeld(this, false, true, false);
		this.meineKomponente.getParent().validate();
	}

	public PasswortFeld(double x, double y, double b, double h, Komponente le) {
//		super(new JTextField(), x, y, b, h, le);
		super(new JPasswordField(), x, y, b, h, le);
		((JPasswordField)this.meineKomponente).setEchoChar((char)0);
		try {
			dokuListener = new DokuLis();
			((JPasswordField) meineKomponente).getDocument()
					.addDocumentListener(dokuListener);// muss benannt werden, da in
			// abgeleiteter Klasse zu ändern
//			((JTextField) meineKomponente)
//					.addCaretListener(new CaretListener() {
//
//						@Override
//						public void caretUpdate(CaretEvent arg0) {
//							TextFeld.this.meineKomponente.getParent().repaint();
//						}
//
//					});
			((JPasswordField) meineKomponente).addKeyListener(new KeyAdapter() {

				public void keyReleased(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						returnWurdeGedrueckt = true;
						((JPasswordField) meineKomponente).transferFocus();
						PasswortFeld.this.fireTextFeld(PasswortFeld.this, true, false,
								false);
					}

				}

			});
			((JPasswordField) meineKomponente)
					.addFocusListener(new FocusListener() {

						
						public void focusGained(FocusEvent arg0) {
							// TODO Auto-generated method stub

						}

						
						public void focusLost(FocusEvent arg0) {
							PasswortFeld.this.fokusVerloren = true;
							PasswortFeld.this.fireTextFeld(PasswortFeld.this, false,
									false, true);
						}

					});

		} catch (Exception e) {
			System.out
					.println(" TextFeld konnte nicht erstellt werden, da zuvor kein");
			System.out.println(" Fensterobjekt o.a. erstellt wurde. ");
			System.out.println(" Bitte erst dieses erzeugen");
		}
		
	}
/** 
 * legt das Echo-Zeichen für die sichtbare Ausgabe fest, beispielsweise '*' 
 * @param echo Echo-Zeichen
 */
	public void setzeEchoZeichen(char echo){
		((JPasswordField)this.meineKomponente).setEchoChar(echo);
		((JPasswordField)this.meineKomponente).setEchoChar((char)0);
	}
	
	/** 
	 * loescht das Echo-Zeichen. Der eingebenen Text wird lesbar.
	 */
	public void loescheEchoZeichen(){
		((JPasswordField)this.meineKomponente).setEchoChar((char)0);
	}
	
	// private TastenLauscherVerwalter tlv;
	//
	// /** Ereignisbearbeitung registriert einen TastenLauscher */
	// public synchronized void setzeTastenLauscher(TastenLauscher l) {
	// if (tlv == null) {
	// tlv = new TastenLauscherVerwalter(((JTextField) meineKomponente));
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

	/** Schaltet die Editierbarkeit an (Parameterwert true) oder ab (false) */
	public void setzeEditierbar(boolean ed) {
		((JPasswordField) meineKomponente).setEditable(ed);
	}

	String thrs;
	/** ersetzt den Inhalt durch den Text des Parameters */
	public void setzeText(String s) {
		thrs = s;
		
		((JPasswordField) meineKomponente).getDocument().removeDocumentListener(dokuListener);
		// WorkAround gegen gelegentliches Einfrieren
//		Hilfe.kurzePause();
//		if (((JPasswordField) meineKomponente).isVisible()) {
//			((JPasswordField) meineKomponente).setVisible(false);
//			((JPasswordField) meineKomponente).setText(s);
//			((JPasswordField) meineKomponente).setVisible(true);
//
//		} else {
//			((JPasswordField) meineKomponente).setText(s);
//		}
		try {
			SwingUtilities.invokeLater(new Runnable(){
				public void run(){
//				((JPasswordField) meineKomponente).validate();
				((JPasswordField) meineKomponente).setText(thrs);}
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		((JPasswordField) meineKomponente).getDocument().addDocumentListener(dokuListener);
	}

	/**
	 * ersetzt den Inhalt durch den Text des Parameters
	 */
	public void setzeText(char s) {
		this.setzeText(s + "");
	}

	/** fügt Text an den bestehenden Text an */
	public void fuegeAn(String s) {
		this.setzeText(this.text() + s);
	}

	/**
	 * hängt Text an den bestehenden an
	 * 
	 */
	public void fuegeAn(char s) {
		this.setzeText(this.text() + s);
	}

	/** markiert den gesamten Text */
	public void markiereAlles() {
		((JPasswordField) meineKomponente).selectAll();
	}

	/** markiert den Text von Position i bis j */
	public void markiere(int i, int j) {
		((JPasswordField) meineKomponente).requestFocus();
		((JPasswordField) meineKomponente).select(i, j);
	}

	/** registriert einen TextFeldLauscher */
	public synchronized void setzePasswortFeldLauscher(PasswortFeldLauscher l) {
		Vector v = passwortFeldLauscher == null ? new Vector(2)
				: (Vector) passwortFeldLauscher.clone();
		if (!v.contains(l)) {
			v.addElement(l);
			passwortFeldLauscher = v;
		}
	}

	/** entfernt einen Textfeldlauscher */
	public synchronized void entfernePasswortFeldLauscher(PasswortFeldLauscher l) {
		if (passwortFeldLauscher != null && passwortFeldLauscher.contains(l)) {
			Vector v = (Vector) passwortFeldLauscher.clone();
			v.removeElement(l);
			passwortFeldLauscher = v;
		}
	}

	/**
	 * liefert genau dann wahr, wenn der Text seit dem letzten Aufruf dieser
	 * Methode geändert wurde.
	 */
	public boolean textWurdeGeaendert() {
		boolean merke = wurdeGeaendert;
		wurdeGeaendert = false;
		return merke;
	}

	/**
	 * liefert genau dann wahr, wenn die Return-Taste seit dem letzten Aufruf
	 * dieser Methode geändert wurde.
	 */
	public boolean returnWurdeGedrueckt() {
		boolean merke = returnWurdeGedrueckt;
		returnWurdeGedrueckt = false;
		return merke;
	}

	/**
	 * liefert genau dann wahr, wenn die Return-Taste seit dem letzten Aufruf
	 * dieser Methode geändert wurde.
	 */
	public boolean fokusVerloren() {
		boolean merke = fokusVerloren;
		fokusVerloren = false;
		return merke;
	}

	/** liefert den Text */
	public String text() {
		return new String(((JPasswordField) meineKomponente).getPassword());
	}

	/** liefert den markierten Inhalt */
	public String markierterText() {
		return ((JPasswordField) meineKomponente).getSelectedText();
	}

	/**  */
	protected void fireTextFeld(PasswortFeld e, boolean returngedrueckt,
			boolean wurdeGeaendert, boolean fokusverloren) {
		if (passwortFeldLauscher != null) {
			Vector listeners = passwortFeldLauscher;
			int count = listeners.size();
			for (int i = 0; i < count; i++) {
				if (returngedrueckt) {
					((PasswortFeldLauscher) listeners.elementAt(i))
							.bearbeiteReturnGedrueckt(e);
				}
				if (wurdeGeaendert) {
					((PasswortFeldLauscher) listeners.elementAt(i))
							.bearbeiteTextVeraenderung(e);
				}
				if (fokusverloren) {
					((PasswortFeldLauscher) listeners.elementAt(i))
							.bearbeiteFokusVerloren(e);
				}

			}
		}
	}

}