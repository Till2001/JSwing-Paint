package basiX;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

import basiX.vw.*;

/**
 * Die Klasse Maus erlaubt den Zugriff auf aktuelle Mauszust&auml;nde (Position,
 * Tastenzustände) und Mausereignisse (Klicks und Bewegungen) Jedes Mausobjekt
 * ist an eine Komponente gebunden etwa bzgl. der Koordinatenangaben.
 * 
 * @author Georg Dick
 * @version 5.1.2010
 */
public class Maus {
	private boolean linksunten = false;
	private boolean rechtsunten = false;
	private boolean doppelt = false;
	private boolean istMac = System.getProperty("os.name").equals("Mac OS");
	private int maxPufferLaenge = 20;
	private Vector<MausKlick> klicks = new Vector<MausKlick>(maxPufferLaenge);
	private Vector<MausBewegung> moves = new Vector<MausBewegung>(
			maxPufferLaenge);
	// private int z = 0;
	// private Container merkeContainer;
	private Component merkeComponent;
	protected int mausx = Integer.MIN_VALUE;
	protected int mausy = Integer.MIN_VALUE;
	private MausKlick aktuellerKlick = null;
	private MausBewegung aktuelleBewegung;
	private MouseListener meinMausListener = null;
	private MouseMotionListener meinMausMotionListener = null;

	/**
	 * erzeugt ein Mausobjekt. Das Mausobjekt darf erst nach Erzeugen eines
	 * Fensters selbst erzeugt werden. Die Koordinaten der Maus sind erst
	 * gültig, sobald die Maus einmal bewegt wurde, sie beziehen sich auf das
	 * zuvor erzeugte Fenster.
	 */
	public Maus() {

		if (DasFenster.hauptLeinwand() != null) {
			merkeComponent = DasFenster.hauptLeinwand().meineKomponente;
			this.mauslistenererzeugen();
			// merkeContainer.addMouseListener(new MouseListener() {
			//
			// public void mouseClicked(MouseEvent e) {
			// // linksunten = false;
			// // rechtsunten = false;
			// if ((e.getModifiers() & MouseEvent.BUTTON1_MASK) != 0) {
			// linksunten = false;
			// }
			// if ((e.getModifiers() & MouseEvent.BUTTON3_MASK) != 0) {
			// rechtsunten = false;
			// }
			// // info(""+e.getModifiers());
			// // System.out.println(e.getModifiers()+" "+linksunten +"
			// // released
			// // "+rechtsunten+" "+e.isMetaDown());
			// }
			//
			// public void mousePressed(MouseEvent e) {
			// linksunten = (e.getModifiers() & MouseEvent.BUTTON1_MASK) != 0
			// || linksunten;
			// rechtsunten = (e.getModifiers() & MouseEvent.BUTTON3_MASK) != 0
			// || rechtsunten;
			// }
			//
			// public void mouseReleased(MouseEvent e) {
			//
			// if ((e.getModifiers() & MouseEvent.BUTTON1_MASK) != 0) {
			// linksunten = false;
			// }
			// if ((e.getModifiers() & MouseEvent.BUTTON3_MASK) != 0) {
			// rechtsunten = false;
			// }
			// }
			//
			// @Override
			// public void mouseEntered(MouseEvent arg0) {
			// // TODO Auto-generated method stub
			//					
			// }
			//
			// @Override
			// public void mouseExited(MouseEvent arg0) {
			// // TODO Auto-generated method stub
			//					
			// }
			//
			//				
			// });
			// merkeContainer.addMouseMotionListener(new MouseMotionListener() {
			// public void mouseMoved(MouseEvent e) {
			//
			// linksunten = false;
			// rechtsunten = false;
			// }
			//
			// public void mouseDragged(MouseEvent e) {
			// // linksunten = !e.isMetaDown()|| linksunten;
			// // rechtsunten = e.isMetaDown()|| rechtsunten;
			// linksunten = (e.getModifiers() & MouseEvent.BUTTON1_MASK) != 0;
			// rechtsunten = (e.getModifiers() & MouseEvent.BUTTON3_MASK) != 0;
			// // System.out.println(e.getModifiers()+" "+linksunten +"
			// // released
			// // "+rechtsunten+" "+e.isMetaDown());
			// // info(""+e.getModifiers());
			// }
			// });

		}

	}

	/**
	 * erzeugt ein Mausobjekt für die Komponente b.
	 * 
	 * 
	 */
	public Maus(Komponente c) {
		merkeComponent = c.getSwingComponent();
		this.mauslistenererzeugen();
		// merkeComponent.addMouseListener(new MouseListener() {
		//
		// public void mouseClicked(MouseEvent e) {
		// // linksunten = false;
		// // rechtsunten = false;
		// if ((e.getModifiers() & MouseEvent.BUTTON1_MASK) != 0) {
		// linksunten = false;
		// }
		// if ((e.getModifiers() & MouseEvent.BUTTON3_MASK) != 0) {
		// rechtsunten = false;
		// }
		// // info(""+e.getModifiers());
		// // System.out.println(e.getModifiers()+" "+linksunten +"
		// // released
		// // "+rechtsunten+" "+e.isMetaDown());
		// }
		//
		// public void mousePressed(MouseEvent e) {
		// linksunten = (e.getModifiers() & MouseEvent.BUTTON1_MASK) != 0
		// || linksunten;
		// rechtsunten = (e.getModifiers() & MouseEvent.BUTTON3_MASK) != 0
		// || rechtsunten;
		// }
		//
		// public void mouseReleased(MouseEvent e) {
		//
		// if ((e.getModifiers() & MouseEvent.BUTTON1_MASK) != 0) {
		// linksunten = false;
		// }
		// if ((e.getModifiers() & MouseEvent.BUTTON3_MASK) != 0) {
		// rechtsunten = false;
		// }
		// }
		//
		// @Override
		// public void mouseEntered(MouseEvent e) {
		// // TODO Auto-generated method stub
		//				
		// }
		//
		// @Override
		// public void mouseExited(MouseEvent e) {
		// // TODO Auto-generated method stub
		//				
		// }
		//
		//			
		// });
		// merkeComponent.addMouseMotionListener(new MouseMotionListener() {
		// public void mouseMoved(MouseEvent e) {
		// linksunten = false;
		// rechtsunten = false;
		// }
		//
		// public void mouseDragged(MouseEvent e) {
		// // linksunten = !e.isMetaDown()|| linksunten;
		// // rechtsunten = e.isMetaDown()|| rechtsunten;
		// linksunten = (e.getModifiers() & MouseEvent.BUTTON1_MASK) != 0;
		// rechtsunten = (e.getModifiers() & MouseEvent.BUTTON3_MASK) != 0;
		// // System.out.println(e.getModifiers()+" "+linksunten +"
		// // released
		// // "+rechtsunten+" "+e.isMetaDown());
		// // info(""+e.getModifiers());
		// }
		// });

	}

	/**  */
	public boolean spezialKlick() {
		if (istMac) {
			return doppelt;
		} else {
			return rechtsunten;
		}
	}

	// /** */
	// public Maus(Komponente b, int ox, int oy) {
	//		
	// merkeComponent = b.getSwingComponent();
	// merkeComponent.addMouseListener(new MouseAdapter() {
	//
	// public void mouseClicked(MouseEvent e) {
	// // linksunten = false;
	// // rechtsunten = false;
	// if ((e.getModifiers() & MouseEvent.BUTTON1_MASK) != 0) {
	// linksunten = false;
	// }
	// if ((e.getModifiers() & MouseEvent.BUTTON3_MASK) != 0) {
	// rechtsunten = false;
	// }
	// // info(""+e.getModifiers());
	// // System.out.println(e.getModifiers()+" "+linksunten +"
	// // released
	// // "+rechtsunten+" "+e.isMetaDown());
	// }
	//
	// public void mousePressed(MouseEvent e) {
	// linksunten = (e.getModifiers() & MouseEvent.BUTTON1_MASK) != 0
	// || linksunten;
	// rechtsunten = (e.getModifiers() & MouseEvent.BUTTON3_MASK) != 0
	// || rechtsunten;
	// }
	//
	// public void mouseReleased(MouseEvent e) {
	//
	// if ((e.getModifiers() & MouseEvent.BUTTON1_MASK) != 0) {
	// linksunten = false;
	// }
	// if ((e.getModifiers() & MouseEvent.BUTTON3_MASK) != 0) {
	// rechtsunten = false;
	// }
	// }
	//
	//			
	// });
	// merkeComponent.addMouseMotionListener(new MouseMotionListener() {
	// public void mouseMoved(MouseEvent e) {
	//
	// linksunten = false;
	// rechtsunten = false;
	// }
	//
	// public void mouseDragged(MouseEvent e) {
	// // linksunten = !e.isMetaDown()|| linksunten;
	// // rechtsunten = e.isMetaDown()|| rechtsunten;
	// linksunten = (e.getModifiers() & MouseEvent.BUTTON1_MASK) != 0;
	// rechtsunten = (e.getModifiers() & MouseEvent.BUTTON3_MASK) != 0;
	// // System.out.println(e.getModifiers()+" "+linksunten +"
	// // released
	// // "+rechtsunten+" "+e.isMetaDown());
	// // info(""+e.getModifiers());
	// }
	// });
	//
	// }

	/** setzt eine Komponente als Bezug für die Mauskoordinaten */
	public void setzeKomponente(Komponente k) {
		Komponente l;
		if (k instanceof Fenster) {
			l = ((Fenster) k).leinwand();
		} else {
			l = k;
		}
		if (merkeComponent != null) {
			mauslistenerentfernen();
			merkeComponent = null;
		}

		merkeComponent = l.meineKomponente;
		mauslistenererzeugen();

	}

	private void mauslistenerentfernen() {
		if (merkeComponent != null) {
			merkeComponent.removeMouseListener(meinMausListener);
			merkeComponent.removeMouseMotionListener(meinMausMotionListener);
		}
	}

	private void mauslistenererzeugen() {
		if (meinMausListener == null) {
			meinMausListener = new MouseListener() {

				public void mouseClicked(MouseEvent e) {
					// linksunten = false;
					// rechtsunten = false;
					mausx = e.getX();
					mausy = e.getY();
					if ((e.getModifiers() & MouseEvent.BUTTON1_MASK) != 0) {
						klicks.add(new MausKlick(e.getX(), e.getY(), true));
						if (maxPufferLaenge < klicks.size()) {
							klicks.remove(0);
						}
						linksunten = false;

					}
					if ((e.getModifiers() & MouseEvent.BUTTON3_MASK) != 0) {
						klicks.add(new MausKlick(e.getX(), e.getY(), false));
						if (maxPufferLaenge < klicks.size()) {
							klicks.remove(0);
						}
						rechtsunten = false;
					}
					// info(""+e.getModifiers());
					// System.out.println(e.getModifiers()+" "+linksunten +"
					// released
					// "+rechtsunten+" "+e.isMetaDown());
				}

				public void mousePressed(MouseEvent e) {
					mausx = e.getX();
					mausy = e.getY();
					linksunten = (e.getModifiers() & MouseEvent.BUTTON1_MASK) != 0
							|| linksunten;
					rechtsunten = (e.getModifiers() & MouseEvent.BUTTON3_MASK) != 0
							|| rechtsunten;
				}

				public void mouseReleased(MouseEvent e) {
					mausx = e.getX();
					mausy = e.getY();

					if ((e.getModifiers() & MouseEvent.BUTTON1_MASK) != 0) {
						linksunten = false;
					}
					if ((e.getModifiers() & MouseEvent.BUTTON3_MASK) != 0) {
						rechtsunten = false;
					}
				}

				
				public void mouseEntered(MouseEvent e) {
					mausx = e.getX();
					mausy = e.getY();
					moves.add(new MausBewegung(e.getX(), e.getY(), true, false,
							false));
					if (maxPufferLaenge < moves.size()) {
						moves.remove(0);
					}

				}

				
				public void mouseExited(MouseEvent e) {
					mausx = e.getX();
					mausy = e.getY();
					moves.add(new MausBewegung(e.getX(), e.getY(), false, true,
							false));
					if (maxPufferLaenge < moves.size()) {
						moves.remove(0);
					}

				}

			};
		}
		if (meinMausMotionListener == null) {
			meinMausMotionListener = new MouseMotionListener() {

				public void mouseMoved(MouseEvent e) {
					mausx = e.getX();
					mausy = e.getY();
					linksunten = false;
					rechtsunten = false;

					moves.add(new MausBewegung(e.getX(), e.getY(), false,
							false, false));
					if (maxPufferLaenge < moves.size()) {
						moves.remove(0);
					}

				}

				public void mouseDragged(MouseEvent e) {
					mausx = e.getX();
					mausy = e.getY();
					// linksunten = !e.isMetaDown()|| linksunten;
					// rechtsunten = e.isMetaDown()|| rechtsunten;
					linksunten = (e.getModifiers() & MouseEvent.BUTTON1_MASK) != 0;
					rechtsunten = (e.getModifiers() & MouseEvent.BUTTON3_MASK) != 0;

					moves.add(new MausBewegung(e.getX(), e.getY(), false,
							false, true));
					if (maxPufferLaenge < moves.size()) {
						moves.remove(0);
					}

					// System.out.println(e.getModifiers()+" "+linksunten +"
					// released
					// "+rechtsunten+" "+e.isMetaDown());
					// info(""+e.getModifiers());
				}
			};
		}
		merkeComponent.addMouseListener(meinMausListener);
		merkeComponent.addMouseMotionListener(meinMausMotionListener);
	}

	/**
	 * gibt die verwendeten Ressourcen frei, die Maus ist anschließend nicht
	 * mehr verwendbar
	 */
	public void gibFrei() {

	}

	/**
	 * setzt die Mausposition
	 */
	public void setzePosition(int mx, int my) {
		try {
			Robot m = new Robot();
			m.mouseMove(mx + merkeComponent.getLocationOnScreen().x, my
					+ merkeComponent.getLocationOnScreen().y);
		} catch (Exception e) {
		}
	}

	/** liefert die horizontale Koordinate der Mausposition ("x-Position") */
	public int hPosition() {
		if (mausx == Integer.MIN_VALUE) {
			try {
				if (merkeComponent != null) {
					return MouseInfo.getPointerInfo().getLocation().x
							- merkeComponent.getLocationOnScreen().x;
				}

			} catch (Exception e) {
			}
		}
		try {
			int mx = Integer.MIN_VALUE;
			int my = Integer.MIN_VALUE;
			if (merkeComponent != null) {
				int posx = merkeComponent.getLocationOnScreen().x;
				int posy = merkeComponent.getLocationOnScreen().y;
				mx = MouseInfo.getPointerInfo().getLocation().x
				- posx;
				my = MouseInfo.getPointerInfo().getLocation().y
						- posy;
				if (mx >= 0 &&  mx <= merkeComponent.getWidth() && my >= 0 &&  my <= merkeComponent.getHeight() ){
					return mx;
				}
			}
		} catch (Exception e) {			
		}
		return mausx;
	}

	/** liefert die vertikale Koordinate der Mausposition ("y-Position") */
	public int vPosition() {
		if (mausy == Integer.MIN_VALUE) {
			try {
				if (merkeComponent != null) {
					return MouseInfo.getPointerInfo().getLocation().y
							- merkeComponent.getLocationOnScreen().y;
				}

			} catch (Exception e) {
			}
		}
		try {
			int mx = Integer.MIN_VALUE;
			int my = Integer.MIN_VALUE;
			if (merkeComponent != null) {
				int posx = merkeComponent.getLocationOnScreen().x;
				int posy = merkeComponent.getLocationOnScreen().y;
				mx = MouseInfo.getPointerInfo().getLocation().x
				- posx;
				my = MouseInfo.getPointerInfo().getLocation().y
						- posy;
				if (mx >= 0 &&  mx <= merkeComponent.getWidth() && my >= 0 &&  my <= merkeComponent.getHeight() ){
					return my;
				}
			}
		} catch (Exception e) {			
		}
		return mausy;
	}

	/**
	 * liefert den Wert true, wenn die linke Maustaste gedrückt ist
	 * 
	 */
	public boolean istGedrueckt() {

		return linksunten;
	}

	/**
	 * liefert den Wert true, wenn die rechte Maustaste gedrückt ist
	 */
	public boolean istRechtsGedrueckt() {
		return rechtsunten;
	}

	/**
	 * liefert den Wert true, wenn die linke Maustaste gedrückt ist
	 * 
	 */
	public boolean istGedr\u00fcckt() {
		return istGedrueckt();
	}

	/**
	 * liefert den Wert true, wenn die rechte Maustaste gedrückt ist
	 * 
	 */
	public boolean istRechtsGedr\u00fcckt() {
		return istRechtsGedrueckt();
	}

	/**  */
	public void info(String s) {
		if (Einstellungen.DEBUGMODUS)
			System.out.println(s);
	}

	/**
	 * liefert den Wert true, wenn der Pufferspeicher für Mausklicks nicht leer
	 * ist. Das bedeutet, dass in der Vergangenheit ein Mausklick erfolgt ist,
	 * der noch nicht abgefragt worden ist. Die Eigenschaften des Mausklicks
	 * (rechte Maustatste, linke Maustaste, Ort des Mausklicks) liefert die
	 * Anfrage getKlick() mittels eines MausKlick-Objektes.
	 * 
	 * @return
	 */
	public boolean wurdeGeklickt() {
		return !klicks.isEmpty();
	}

	/**
	 * liefert die horizontale Position des letzten Mausklicks
	 * 
	 * @return
	 */
	public double klickHPosition() {
		if (aktuellerKlick != null) {
			return aktuellerKlick.getX();
		} else {
			return Double.POSITIVE_INFINITY;
		}
	}

	/**
	 * liefert die vertikale Position des letzten Mausklicks
	 * 
	 * @return
	 */
	public double klickVPosition() {
		if (aktuellerKlick != null) {
			return aktuellerKlick.getY();
		} else {
			return Double.POSITIVE_INFINITY;
		}
	}

	/**
	 * liefert den Wert true, wenn die rechte Maustaste geklickt wurde
	 * 
	 * @return
	 */
	public boolean klickRechts() {
		if (aktuellerKlick != null) {
			return !aktuellerKlick.isLinks();
		} else {
			return false;
		}
	}

	/**
	 * liefert den Wert true, wenn die linke Maustaste geklickt wurde
	 * 
	 * @return
	 */
	public boolean klickLinks() {
		if (aktuellerKlick != null) {
			return aktuellerKlick.isLinks();
		} else {
			return false;
		}
	}

	/**
	 * liefert den Wert true, wenn der Pufferspeicher für Mausbewegungen nicht
	 * leer ist. Das bedeutet, dass in der Vergangenheit eine Mausbewegung
	 * erfolgt ist, die noch nicht abgefragt worden ist. Die Eigenschaften der
	 * Mausbewegung (hinein, heraus, gezogen, Endpunkt der Bewegung) liefert die
	 * Anfrage getBewegung() mittels eines MausBewegung-Objektes.
	 * 
	 * @return
	 */
	public boolean wurdeBewegt() {
		return !moves.isEmpty();
	}

	/**
	 * liefert ein MausKlick-Objekt, wenn der Pufferspeicher für Mausklicks
	 * nicht leer ist. Das bedeutet, dass in der Vergangenheit ein Mausklick
	 * erfolgt ist, der noch nicht abgefragt worden ist. Die Eigenschaften des
	 * Mausklicks (rechte Maustatste, linke Maustaste, Ort des Mausklicks)
	 * entnimmt man dem MausKlick-Objekt. Ist der Pufferspeicher leer, so wird
	 * der Wert null zurück gegeben.
	 * 
	 * @return
	 */
	public MausKlick holeKlick() {
		if (!klicks.isEmpty()) {
			MausKlick k = klicks.get(0);
			aktuellerKlick = k;
			klicks.remove(0);
			return k;
		}
		return null;
	}

	/**
	 * liefert ein MausBewegung-Objekt, wenn der Pufferspeicher für
	 * Mausbewegungen nicht leer ist. Das bedeutet, dass in der Vergangenheit
	 * eine Mausbewegung erfolgt ist, die noch nicht abgefragt worden ist. Die
	 * Eigenschaften der Mausbewegung (hinein, heraus, gezogen, Endpunkt der
	 * Bewegung) entnimmt man dem MausBewegung-Objekt. Ist der Pufferspeicher
	 * leer, so wird der Wert null zurück gegeben.
	 * 
	 * @return
	 */
	public MausBewegung holeBewegung() {
		if (!moves.isEmpty()) {
			MausBewegung k = moves.get(0);
			aktuelleBewegung = k;
			moves.remove(0);
			return k;
		}
		return null;
	}

	/**
	 * liefert die horizontale Koordinate der Position nach der aktuell
	 * bearbeiteten Mausbewegung
	 * 
	 * @return
	 */
	public int bewegtNachHPosition() {
		if (aktuelleBewegung != null) {
			return aktuelleBewegung.getX();
		} else {
			return Integer.MIN_VALUE;
		}
	}

	/**
	 * liefert die vertikale Koordinate der Position nach der aktuell
	 * bearbeiteten Mausbewegung
	 * 
	 * @return
	 */
	public int bewegtNachVPosition() {
		if (aktuelleBewegung != null) {
			return aktuelleBewegung.getY();
		} else {
			return Integer.MIN_VALUE;
		}
	}

	/**
	 * legt fest, wie viele Klick- bzw. Bewegungsereignisse maximal in den
	 * Puffer geschrieben werden. Der Puffer enthält immer die aktuellsten
	 * Ereignisse. Vorgabe ist der Wert 20. Wird der Wert beispielsweise auf 1
	 * gesetzt, so wird nur das aktuellste Klick- und Bewegungsereignis
	 * gespeichert. Wird ein Wert unterhalb von 1 übergeben, so wird der Puffer
	 * auf die Länge 1 gesetzt.
	 */
	public void setzePufferLaenge(int l) {
		maxPufferLaenge = (l > 0) ? l : 1;
		// gegebenenfalls Puffer verkleinern
		int loeschzahl = moves.size() - maxPufferLaenge;
		for (int i = loeschzahl - 1; i >= 0; i--) {
			moves.remove(i);
		}
		loeschzahl = klicks.size() - maxPufferLaenge;
		for (int i = loeschzahl - 1; i >= 0; i--) {
			klicks.remove(i);
		}
	}

	/**
	 * 
	 * @return liefert die maximale Anzahl von Klick bzw. Bewegungsereignissen
	 *         der Maus, die zwischengespeichert werden
	 */
	public int getPufferLaenge() {
		return maxPufferLaenge;
	}

}
