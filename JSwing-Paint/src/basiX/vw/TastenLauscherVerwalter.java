/*
 * Erstellt am 01.10.2005
 *
 */
package basiX.vw;

import java.awt.AWTEvent;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

import basiX.*;

/**
 * @author G Dick
 * 
 */
public class TastenLauscherVerwalter 
//implements KeyListener 
{
	private Komponente besitzer;

	private Vector tastenLauscher;

	private void keylistenererzeugen(){
		Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {
			public void eventDispatched(AWTEvent event) {
				if (((KeyEvent) event).getID() == KeyEvent.KEY_PRESSED) {
					char t = zeichenVon((KeyEvent) event);
					fireTastenDruck(besitzer, t);									
				}
//				if (((KeyEvent) event).getID() == KeyEvent.KEY_RELEASED) {
//					
//				}				
			}
			
		}, AWTEvent.KEY_EVENT_MASK);
	}
	
	public TastenLauscherVerwalter() {
	}

	public TastenLauscherVerwalter(Komponente besitzer) {
		this.keylistenererzeugen();
		this.besitzer=besitzer;
		
//		if (besitzer instanceof Fenster){
//			((Fenster)besitzer).leinwand().getSwingComponent().addKeyListener(this);
//		} else {
//		besitzer.getSwingComponent().addKeyListener(this);}
	}

	/** Ereignisbearbeitung registriert einen TastenLauscher */
	public synchronized void setzeTastenLauscher(TastenLauscher l) {
		Vector v = tastenLauscher == null ? new Vector(2)
				: (Vector) tastenLauscher.clone();
		if (!v.contains(l)) {
			v.addElement(l);
			tastenLauscher = v;
		}
	}

	/** Ereignisbearbeitung entfernt einen TastenLauscher */
	public synchronized void entferneTastenLauscher(TastenLauscher l) {
		if (tastenLauscher != null && tastenLauscher.contains(l)) {
			Vector v = (Vector) tastenLauscher.clone();
			v.removeElement(l);
			tastenLauscher = v;
		}
	}

	/**  */
	public void fireTastenDruck(Komponente e, KeyEvent k) {
		if (tastenLauscher != null) {
			Vector listeners = tastenLauscher;
			int count = listeners.size();
			for (int i = 0; i < count; i++) {
				((TastenLauscher) listeners.elementAt(i)).bearbeiteTaste(e,
						this.zeichenVon(k));
			}
		}
	}

	/**  */
	public void fireTastenDruck(Komponente e, char t) {
		if (tastenLauscher != null) {
			Vector listeners = tastenLauscher;
			int count = listeners.size();
			for (int i = 0; i < count; i++) {
				((TastenLauscher) listeners.elementAt(i)).bearbeiteTaste(e, t);
			}
		}
	}

	public void leiteTastenDruckWeiter(Komponente e, char t) {
		if (tastenLauscher != null) {
			Vector listeners = tastenLauscher;
			int count = listeners.size();
			for (int i = 0; i < count; i++) {
				if (((TastenLauscher) listeners.elementAt(i)) != e) {
					((TastenLauscher) listeners.elementAt(i)).bearbeiteTaste(e,
							t);
				}
			}
		}
	}

	public char zeichenVon(KeyEvent k) {
		char c = k.getKeyChar();
		switch (k.getKeyCode()) {

		case 27:
			return Zeichen.ESC;

		case KeyEvent.VK_DOWN:
			return Zeichen.PFEILUNTEN;

		case KeyEvent.VK_END:
			return Zeichen.ENDE;

		case KeyEvent.VK_F1:
			return Zeichen.F1;

		case KeyEvent.VK_F2:
			return Zeichen.F2;

		case KeyEvent.VK_F3:
			return Zeichen.F3;

		case KeyEvent.VK_F4:
			return Zeichen.F4;

		case KeyEvent.VK_F5:
			return Zeichen.F5;

		case KeyEvent.VK_F6:
			return Zeichen.F6;

		case KeyEvent.VK_F7:
			return Zeichen.F7;

		case KeyEvent.VK_F8:
			return Zeichen.F8;

		case KeyEvent.VK_F9:
			return Zeichen.F9;

		case KeyEvent.VK_F10:
			return Zeichen.F10;

		case KeyEvent.VK_F11:
			return Zeichen.F11;

		case KeyEvent.VK_F12:
			return Zeichen.F12;

		case KeyEvent.VK_HOME:
			return Zeichen.POS1;

		case KeyEvent.VK_LEFT:
			return Zeichen.PFEILLINKS;

		case KeyEvent.VK_PAGE_DOWN:
			return Zeichen.BILDUNTEN;

		case KeyEvent.VK_PAGE_UP:
			return Zeichen.BILDAUF;

		case KeyEvent.VK_RIGHT:
			return Zeichen.PFEILRECHTS;

		case KeyEvent.VK_UP:
			return Zeichen.PFEILOBEN;
		default:
			return c;
		}
	}

//	public void keyTyped(KeyEvent arg0) {
//
//	}
//
//	public void keyPressed(KeyEvent k) {
//		this.fireTastenDruck(besitzer, k);
//
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
//	 */
//	public void keyReleased(KeyEvent arg0) {
//
//	}

}