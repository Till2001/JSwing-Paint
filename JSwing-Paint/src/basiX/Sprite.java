package basiX;

//import java.awt.*;
//import java.awt.geom.AffineTransform;
//import java.awt.geom.Rectangle2D;
//import java.awt.geom.Rectangle2D.Double;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.event.MouseInputAdapter;

//import basis.*;
import basiX.swing.BufferedCanvas;
import basiX.swing.Picture;
import basiX.util.Vektor2D;
import basiX.vw.DasFenster;

/**
 * Ein Sprite ist eine rechteckige Komponente, die ein oder mehrere Bilder
 * anzeigen kann, die sich bewegen kann und Kollisionen mit anderen Sprites
 * erkennen kann. Sprites haben eine Ausrichtung, rechts 0°, oben 90° usw. die
 * über drehe-Methoden verändert werden kann. Auf Wunsch kann die Ausrichtung
 * auch durch ein entsprechend gedrehtes Bild angezeigt werden.
 * 
 * 
 * @author Georg
 * 
 */
public class Sprite {
	private SpriteBild b;
	private Vector<Sprite> sprites = new Vector<Sprite>();
	private double winkel = 0;
	private boolean richtungAnzeigen = false;

	public Sprite() {
		this(0, 0, DasFenster.hauptLeinwand());
	}

	public Sprite(String pfad) {
		this(pfad, 0, 0, DasFenster.hauptLeinwand());
	}

	public Sprite(double x, double y, Komponente p) {
		b = new SpriteBild("/basis/images/dummy.gif", x, y, p);

	}

	public void waehleNaechstesBild() {
		b.waehleNaechstesBild();
	}

	/**
	 * erzeugt eine Spritekomponente
	 * 
	 * @param pfad
	 *            Array von Pfaden auf Bilder für das Sprite
	 * @param x
	 *            x-Koordinate der linken obere Ecke des Rechtecks, in dem das
	 *            Sprite liegt
	 * @param y
	 *            x-Koordinate der linken obere Ecke des Rechtecks, in dem das
	 *            Sprite liegt
	 * @param p
	 *            Komponente in die das Sprite eingebettet werden soll.
	 */
	public Sprite(String[] pfad, double x, double y, Komponente p) {
		b = new SpriteBild(pfad, x, y, p);
	}

	/**
	 * erzeugt eine Spritekomponente
	 * 
	 * @param pfad
	 *            Pfad auf Image für das Sprite
	 * @param x
	 *            x-Koordinate der linken obere Ecke des Rechtecks, in dem das
	 *            Sprite liegt
	 * @param y
	 *            y-Koordinate der linken obere Ecke des Rechtecks, in dem das
	 *            Sprite liegt
	 * @param p
	 *            Komponente in die das Sprite eingebettet werden soll.
	 */
	public Sprite(String pfad, double x, double y, Komponente p) {
		b = new SpriteBild(pfad, x, y, p);

	}

	/**
	 * erzeugt eine Spritekomponente auf einem Fenster
	 * 
	 * @param pfad
	 *            Pfad auf Image für das Sprite
	 * @param x
	 *            x-Koordinate der linken obere Ecke des Rechtecks, in dem das
	 *            Sprite liegt
	 * @param y
	 *            y-Koordinate der linken obere Ecke des Rechtecks, in dem das
	 *            Sprite liegt
	 */
	public Sprite(String pfad, double x, double y) {
		this(pfad, x, y, DasFenster.hauptLeinwand());
	}

	/**
	 * erzeugt eine Spritekomponente auf dem zuletzt erzeugten Fenster. Dies
	 * muss vorhanden sein.
	 * 
	 * @param bild
	 *            SpriteBild für die SpriteKomponente
	 * @param x
	 *            x-Koordinate der linken obere Ecke des Rechtecks, in dem das
	 *            Sprite liegt
	 * @param y
	 *            x-Koordinate der linken obere Ecke des Rechtecks, in dem das
	 *            Sprite liegt
	 * 
	 */
	public Sprite(Picture p, int x, int y) {
		b = new SpriteBild(p, x, y, DasFenster.hauptLeinwand());

	}

	/**
	 * erzeugt eine Spritekomponente auf dem zuletzt erzeugten Fenster. Dies
	 * muss vorhanden sein.
	 * 
	 * @param bild
	 *            SpriteBild für die SpriteKomponente
	 * @param x
	 *            x-Koordinate der linken obere Ecke des Rechtecks, in dem das
	 *            Sprite liegt
	 * @param y
	 *            x-Koordinate der linken obere Ecke des Rechtecks, in dem das
	 *            Sprite liegt
	 * 
	 */
	public Sprite(Picture p, int x, int y, Komponente k) {
		b = new SpriteBild(p, x, y, k);

	}

	public void setzeRichtungAnzeigen(boolean ra) {
		if (this.richtungAnzeigen == ra) {
			return;
		}
		this.richtungAnzeigen = ra;
		if (this.richtungAnzeigen) {

			b.setzeBildWinkelOhneGrößenAnpassung(winkel);

		} else {
			b.setzeBildWinkelOhneGrößenAnpassung(0);
		}
	}

	public boolean richtungWirdAngezeigt() {
		return this.richtungAnzeigen;
	}
/** ersetzt das Bild des Sprites
 * @param bild
 */
	public void setzeBild(SpriteBild bild) {
		if (bild == null) {
			return;
		}
		double merkex = b.hPosition();
		double merkey = b.vPosition();
		if (this.istMitMausVerschiebbar()) {
			this.setzeMitMausVerschiebbar(false);
			b.gibFrei();
			b = bild;
			this.setzeMitMausVerschiebbar(true);
		} else {
			b.gibFrei();
			b = bild;
		}
		this.bewegeBis(merkex, merkey);
	}

	public SpriteBild holeSpriteBild() {
		return b;
	}

	// protected void fakeMouseListenerErzeugen() {
	// // wenn kein Mouselistener vorhanden, werden die Mausevents an parent
	// // geleitet,
	// // damit ist dann die Herkunft nicht mehr identifizierbar// Siehe Bug ID
	// // 4413412
	// this0.addMouseListener(new MouseInputAdapter() {
	// public void mousePressed(MouseEvent e) {
	// System.out.println("runter");
	// }
	//
	// public void mouseReleased(MouseEvent e) {
	// System.out.println("hoch");
	// }
	// });
	// this0.addMouseMotionListener(new MouseInputAdapter() {
	// });
	//
	// }

	/**
	 * liefert den Wert true, wenn das Sprite selbst mit einem Objekt s im
	 * nichttransparenten Bereich kollidiert. s muss der Klasse Sprite oder
	 * einer daraus abgeleiteten Klasse angehören. In diesem Fall wird ein
	 * Kollisionspunkt berechnet, der erfragt werden kann.
	 * 
	 * @param s
	 * @return
	 */
	public Sprite kollisionErkanntMitObjektderKlasse(Class<?> s) {
		Sprite[] ks = this.kollisionSprites();
		for (int i = 0; i < ks.length; i++) {
			if (s.isInstance(ks[i])) {
				return ks[i];
			}
		}
		return null;
	}

	/**
	 * bewegt das Sprite in der gegebenen Richtung um die Schrittlänge pl
	 * hierbei wird auch die Schrittlänge angepasst
	 * 
	 */
	public void bewegeUm(double pl) {
		Vektor2D bew = new Vektor2D(winkel, pl);
		this.bewegeUm(bew.getDx(), bew.getDy());
	}

	/**
	 * bewegt das Sprite in der gegebenen Richtung um die Schrittlänge pl
	 * hierbei wird auch die Schrittlänge angepasst
	 * 
	 */
	public void bewegeUm(double dx, double dy) {
		this.bewegeBis(b.hPosition() + dx, b.vPosition() + dy);
	}

	/**
	 * bewegt das Sprite auf die Position (x,y).
	 */
	public void bewegeBis(double px, double py) {
		double altx = b.hPosition();
		double alty = b.vPosition();
		b.setzePosition(px, py);
		winkelAnzeigeAnpassen();
		this.fireSpriteEvent(new SpriteEvent(this,SpriteEvent.EVNeuePosition,altx,alty,px,py));
	}

	private void winkelAnzeigeAnpassen() {
		if (this.richtungAnzeigen && b.bildWinkel() != winkel) {
			b.setzeBildWinkelOhneGrößenAnpassung(winkel);
		} else {
			if (!this.richtungAnzeigen && b.bildWinkel() != 0) {
				b.setzeBildWinkelOhneGrößenAnpassung(0);
			}
		}
	}

	public void lerneSpritesKennen(Vector<Sprite> s) {
		for (Sprite sp : s) {
			if (!sprites.contains(sp)) {
				sprites.add(sp);
			}
		}
	}

	/**
	 * macht s der SpriteKomponente bekannt
	 * 
	 * @param s
	 */
	public void lerneSpriteKennen(Sprite s) {
		if (!sprites.contains(s)) {
			sprites.add(s);
		}
	}

	/**
	 * entfernt s aus der Bekanntschaft des Sprite
	 * 
	 * @param s
	 */
	public void vergissSprite(Sprite s) {
		sprites.remove(s);
	}

	/**
	 * 
	 * @return liefert alle bekannten Sprites als Feld
	 */
	public Sprite[] kenntSprites() {
		return (Sprite[]) sprites.toArray();
	}

	/**
	 * liefert alle dem Sprite bekannten Spritekomponenten, die mit dem Sprite
	 * kollidieren in einem Feld
	 * 
	 * @return
	 */
	public Sprite[] kollisionSprites() {
		Vector<Sprite> s = new Vector<Sprite>();
		for (int i = 0; i < sprites.size(); i++) {
			// System.out.println(sprites.get(i));
			if (sprites.get(i) != this
					&& b.kollisionErkanntMit(sprites.get(i).b)) {
				s.add(sprites.get(i));
			}
		}
		Sprite[] ks = new Sprite[s.size()];
		for (int i = 0; i < s.size(); i++) {
			ks[i] = s.get(i);
		}
		return ks;
	}

	/**
	 * @return liefert alle Sprites aus dem übergebenen Spritefeld, die mit dem
	 *         Sprite kollidieren
	 */

	public Sprite[] kollisionSprites(Sprite[] sp) {
		Vector<Sprite> s = new Vector<Sprite>();
		for (int i = 0; i < sp.length; i++) {
			if (sp[i] != this && b.kollisionErkanntMit(sp[i].b)) {
				s.add(sprites.get(i));
			}
		}
		Sprite[] ks = new Sprite[s.size()];
		for (int i = 0; i < s.size(); i++) {
			ks[i] = s.get(i);
		}
		return ks;
	}

	public void dreheUm(double dwinkel) {
		double merke = winkel;
		winkel += dwinkel;
		winkelAnzeigeAnpassen();
		this.fireSpriteEvent(new SpriteEvent(this,SpriteEvent.EVNeuerWinkel,merke,winkel));
	}

	public void dreheBis(double winkel) {
		double merke = winkel;
		this.winkel = winkel;
		winkelAnzeigeAnpassen();
		this.fireSpriteEvent(new SpriteEvent(this,SpriteEvent.EVNeuePosition,merke,winkel));
	}

	/**
	 * Dreht das Sprite so, dass es auf den Punkt (x,y) ausgerichtet ist.
	 * Bezugspunkt für die Drehung ist nicht die linke obere Ecke, sondern der
	 * Mittelpunkt des Bildes.
	 * 
	 * @param x
	 * @param y
	 */
	public void richteAuf(double x, double y) {
		Vektor2D rv = new Vektor2D();
		rv.setzeDxUndDy(x - b.mittelpunkt().getX(), y - b.mittelpunkt().getY());
		this.dreheBis(rv.getRichtung());
	}

	/**
	 * 
	 * @return liefert die horizontale Position (linke obere Ecke)
	 */
	public double hPosition() {
		return b.hPosition();
	}

	/**
	 * 
	 * @return liefert die vertikale Position (linke obere Ecke)
	 */
	public double vPosition() {
		return b.vPosition();
	}
	public void setzeSichtbar(boolean sichtbar){
		b.setzeSichtbar(sichtbar);
	}
	public boolean istSichtbar(){
		return b.istSichtbar();
	}
	/**
	 * ermöglicht es, das Sprite mit der Maus zu verschieben
	 * 
	 * @param mmv
	 */
	public void setzeMitMausVerschiebbar(boolean mmv) {
		if (mb == null && mmv) {
			mb = new Mausbewegung();
			// mb.setzeAktiv(mmv);
		}
		if (mb != null && !mmv) {
			mb.entferneListener();
			mb = null;
		}
	}

	public boolean istMitMausVerschiebbar() {
		return mb != null;
	}

	private Mausbewegung mb;

	class Mausbewegung {
		private boolean unten = false;
		private double aktx;
		private double akty;
		private double altx = 0;
		private double alty = 0;
		private Point mp;
		// private boolean aktiv = false;
		// public void setzeAktiv(boolean aktiv){
		// this.aktiv=aktiv;
		// }
		MouseAdapter madapt1, madapt2;

		Mausbewegung() {
			madapt1 = new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					// if (aktiv && !e.isMetaDown()) {
					if (!e.isMetaDown()) {
						unten = true;
						mp = b.meineKomponente.getParent()
								.getLocationOnScreen();
						// altx = getWelt().mausHPosition();
						// alty = getWelt().mausVPosition();
						altx = e.getXOnScreen() - mp.x;
						alty = e.getYOnScreen() - mp.y;
					}
				}

				public void mouseReleased(MouseEvent e) {
					// if (aktiv && !e.isMetaDown()) {
					if (!e.isMetaDown()) {
						unten = false;
					}
				}
			};
			madapt2 = new MouseAdapter() {
				public void mouseDragged(MouseEvent e) {
					if (unten) {
						// if (unten && aktiv) {
						mp = b.meineKomponente.getParent()
								.getLocationOnScreen();
						aktx = e.getXOnScreen() - mp.x;
						akty = e.getYOnScreen() - mp.y;
						// aktx = getWelt().mausHPosition();
						// akty = getWelt().mausVPosition();
						if (altx != aktx || alty != akty) {
							double neux = b.hPosition() + (aktx - altx);
							double neuy = b.vPosition() + (akty - alty);
							b.setzePosition(neux, neuy);
							altx = aktx;
							alty = akty;
						}
					}
				}
			};
			b.meineKomponente.addMouseListener(madapt1);
			// meineKomponente.addMouseListener(new MouseAdapter() {
			// public void mousePressed(MouseEvent e) {
			// if (!e.isMetaDown()) {
			// unten = true;
			// mp = meineKomponente.getParent().getLocationOnScreen();
			// // altx = getWelt().mausHPosition();
			// // alty = getWelt().mausVPosition();
			// altx = e.getXOnScreen()-mp.x;
			// alty = e.getYOnScreen()-mp.y;
			// }
			// }
			//
			// public void mouseReleased(MouseEvent e) {
			// if (!e.isMetaDown()) {
			// unten = false;
			// }
			// }
			// });
			b.meineKomponente.addMouseMotionListener(madapt2);
			// meineKomponente.addMouseMotionListener(new MouseAdapter() {
			//
			//
			// public void mouseDragged(MouseEvent e) {
			// if (unten && aktiv) {
			// mp = meineKomponente.getParent().getLocationOnScreen();
			// aktx = e.getXOnScreen()-mp.x;
			// akty = e.getYOnScreen()-mp.y;
			// // aktx = getWelt().mausHPosition();
			// // akty = getWelt().mausVPosition();
			// if (altx != aktx || alty != akty) {
			// double neux = hPosition()
			// + (aktx - altx);
			// double neuy = vPosition()
			// + (akty - alty);
			// setzePosition(neux, neuy);
			// altx = aktx;
			// alty = akty;
			// }
			// }
			// }
			// });

		}

		public void entferneListener() {
			b.meineKomponente.removeMouseListener(madapt1);
			b.meineKomponente.removeMouseMotionListener(madapt2);
		}
		// public boolean istAktiv() {
		// return aktiv;
		// }

	}
	private transient Vector<SpriteLauscher> spriteLauscher;
	
	/** registriert einen Reglerlauscher */
	public synchronized void setzeReglerLauscher(SpriteLauscher l) {
		Vector<SpriteLauscher> v = spriteLauscher == null ? new Vector<SpriteLauscher>(2)
				: (Vector<SpriteLauscher>) spriteLauscher.clone();
		if (!v.contains(l)) {
			v.addElement(l);
			spriteLauscher = v;
		}
	}

	/** entfernt einen Reglerlauscher */
	public synchronized void entferneReglerLauscher(SpriteLauscher l) {
		if (spriteLauscher != null && spriteLauscher.contains(l)) {
			Vector<SpriteLauscher> v = (Vector<SpriteLauscher>)spriteLauscher.clone();
			v.removeElement(l);
			spriteLauscher = v;
		}
	}
	
	/**  */
	protected void fireSpriteEvent(SpriteEvent se) {
		if (spriteLauscher != null) {				
			for (SpriteLauscher sl: spriteLauscher)
				{sl.bearbeiteSpriteEreignis(se);}
		}
	}

	public boolean kollisionErkanntMit(Sprite sk1) {
		
		return this.b.kollisionErkanntMit(sk1.holeSpriteBild());
	}
	
	

//	@Override
//	protected void bildInit(boolean visible) {			
//		super.bildInit(visible);
//		this.fireSpriteEvent(new SpriteEvent(this,SpriteEvent.EVNeueGroesse,-1,-1,this.breite(),this.hoehe()));
//	}
}
