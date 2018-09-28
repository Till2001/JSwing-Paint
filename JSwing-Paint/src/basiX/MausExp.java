package basiX;

import java.awt.AWTEvent;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Vector;

import basiX.vw.Einstellungen;
/**
 * experimentell für künftige Entwicklungen
 * momentan ohne Funktion
 * @author georg
 *
 */
public class MausExp {
	/**
	 * 
	

	/**
	 * erzeugt ein Mausobjekt.
	 */
	public MausExp() {
		Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {
			public void eventDispatched(AWTEvent event) {
				System.out.println(((MouseEvent) event).getID());	
				System.out.println(event.getSource());
				if (((MouseEvent) event).getID() == MouseEvent.MOUSE_PRESSED) {
						System.out.println("lb");		
				}
							
			}
			
		}, AWTEvent.MOUSE_EVENT_MASK + AWTEvent.MOUSE_MOTION_EVENT_MASK);
	}

	
	
	

}
