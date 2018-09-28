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
 * anzeigen kann, die sich bewegen kann und Kollisionen mit anderen Sprites erkennen kann.
 * 
 * 
 * @author Georg
 *
 */
public class SpriteKomponente extends SpriteBild  {
	private Vector<SpriteKomponente> sprites = new Vector<SpriteKomponente>();
	
	
	public SpriteKomponente() {
		this(0, 0, DasFenster.hauptLeinwand());
	}
	
	public SpriteKomponente(String pfad){
		this(pfad,0,0,DasFenster.hauptLeinwand());
	}
	
	public SpriteKomponente(double x, double y, Komponente p) {
		super(x, y,  p);
		bewegung = new Vektor2D();
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
	public SpriteKomponente(String[] pfad, double x, double y, Komponente p) {
		super(pfad, x, y, p);
		bewegung = new Vektor2D();
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
	public SpriteKomponente(String pfad, double x, double y, Komponente p) {
		super(pfad, x, y, p);
		bewegung = new Vektor2D();
	}
/**
 * erzeugt eine Spritekomponente auf einem Fenster
 * @param pfad Pfad auf Image für das Sprite
 * @param x  x-Koordinate der linken obere Ecke des Rechtecks, in dem das
	 *            Sprite liegt
 * @param y y-Koordinate der linken obere Ecke des Rechtecks, in dem das
	 *            Sprite liegt
 */
	public SpriteKomponente(String pfad, double x, double y) {
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
	public SpriteKomponente(Picture b, int x, int y) {
		super(b, x, y, DasFenster.hauptLeinwand());
		bewegung = new Vektor2D();
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
	public SpriteKomponente(Picture b, int x, int y,Komponente k) {
		super(b, x, y, k);
		bewegung = new Vektor2D();
	}
	

	


	// Darstellung von Bewegungsrichtung und Geschwindigkeit
	private Vektor2D bewegung;

	/**
	 * 
	 * @return liefert den Standardbewegungsvektor der SpriteKomponente
	 */
	public Vektor2D holeBewegung() {
		return bewegung;
	}

	/**
	 * setzt den Standardbewegungsvektor der SpriteKomponente
	 * 
	 * @param bewegung
	 */
	public void setzeBewegung(Vektor2D bewegung) {
		this.bewegung = bewegung;
	}

	
	

	
//	protected void fakeMouseListenerErzeugen() {
//		// wenn kein Mouselistener vorhanden, werden die Mausevents an parent
//		// geleitet,
//		// damit ist dann die Herkunft nicht mehr identifizierbar// Siehe Bug ID
//		// 4413412
//		this0.addMouseListener(new MouseInputAdapter() {
//			public void mousePressed(MouseEvent e) {
//				System.out.println("runter");
//			}
//
//			public void mouseReleased(MouseEvent e) {
//				System.out.println("hoch");
//			}
//		});
//		this0.addMouseMotionListener(new MouseInputAdapter() {
//		});
//
//	}
	
	
	
	
	


	




	

	/**
	 * liefert den Wert true, wenn die Spritekomponente selbst mit der
	 * Komponente s im nichttransparenten Bereich kollidiert In diesem Fall wird
	 * ein Kollisionspunkt berechnet, der erfragt werden kann.
	 * 
	 * @param s
	 * @return
	 */
	public SpriteKomponente kollisionErkanntMitObjektderKlasse(Class<?> s) {
		SpriteKomponente[] ks = this.kollisionSprites();
		for (int i = 0; i < ks.length; i++) {
			if (s.isInstance(ks[i])) {
				return ks[i];
			}
		}
		return null;

	}

	/**
	 * bewegt das Sprite gemäß Richtung und Schrittlänge des
	 * Bewegungsvektors(bzw. dx und dy).
	 * 
	 */
	public void bewege() {
		this.setzePosition(this.hPosition() + bewegung.getDx(),
				this.vPosition() + bewegung.getDy());
	}

	/**
	 * bewegt das Sprite in der gegebenen Richtung um die Schrittlänge pl
	 * hierbei wird auch die Schrittlänge angepasst
	 * 
	 */
	public void bewegeUm(double pl) {
		bewegung.setzeLaenge(pl);
		// System.out.println(pl+" "+bewegung.getDx()+" "+bewegung.getDy());
		this.bewege();

	}

	/**
	 * setzt die Richtung für eine Bewegung (Angabe als Winkel)
	 * 
	 * @param winkel
	 */
	public void setzeBewegungsRichtung(double winkel) {
		bewegung.setzeRichtung(winkel);
	}

	/**
	 * bewegt das Sprite um dx und dy in x, bzw- y-Richtung die Werte werden für
	 * zukünftige Bewegungen übernommen
	 * 
	 */
	public void bewegeBis(double px, double py) {
		double[] dxdy = new double[2];
		dxdy[0] = px - this.hPosition();
		dxdy[1] = py - this.vPosition();
		bewegung.setzeDxUndDy(dxdy);
		this.bewege();

	}

	public void setzeSprites(Vector<SpriteKomponente> s) {
		sprites = s;
	}

	/**
	 * macht s der SpriteKomponente bekannt
	 * 
	 * @param s
	 */
	public void setzeSprite(SpriteKomponente s) {
		if (!sprites.contains(s)) {
			sprites.add(s);
		}
	}

	/**
	 * entfernt s aus der Bekanntschaft der SpriteKomponente
	 * 
	 * @param s
	 */
	public void entferneSprite(SpriteKomponente s) {
		sprites.remove(s);
	}

	/**
	 * 
	 * @return liefert alle bekannten Sprites als Feld
	 */
	public SpriteKomponente[] sprite() {
		return (SpriteKomponente[]) sprites.toArray();
	}

	/**
	 * liefert alle dem Sprite bekannten Spritekomponenten, die mit dem Sprite
	 * kollidieren
	 * 
	 * @return
	 */
	public SpriteKomponente[] kollisionSprites() {
		Vector<SpriteKomponente> s = new Vector<SpriteKomponente>();
		for (int i = 0; i < sprites.size(); i++) {
			// System.out.println(sprites.get(i));
			if (sprites.get(i) != this
					&& this.kollisionErkanntMit(sprites.get(i))) {
				s.add(sprites.get(i));
			}
		}
		SpriteKomponente[] ks = new SpriteKomponente[s.size()];
		for (int i = 0; i < s.size(); i++) {
			ks[i] = s.get(i);
		}
		return ks;
	}

	/**
	 * liefert alle Sprites aus dem übergebenen Spritefeld, die mit dem
	 * Sprite kollidieren
	 * 
	 * @return
	 */
	
	public SpriteKomponente[] kollisionSprites(SpriteKomponente[] sp) {
		Vector<SpriteKomponente> s = new Vector<SpriteKomponente>();
		for (int i = 0; i < sp.length; i++) {
			if (sp[i] != this && this.kollisionErkanntMit(sp[i])) {
				s.add(sprites.get(i));
			}
		}
		SpriteKomponente[] ks = new SpriteKomponente[s.size()];
		for (int i = 0; i < s.size(); i++) {
			ks[i] = s.get(i);
		}
		return ks;
	}

	public void dreheUm(double winkel) {
		this.bewegung.setzeRichtung(this.bewegung.getRichtung() + winkel);
		// this.setzeBildWinkel(this.bewegung.getRichtung());
	}
	public void dreheBis(double winkel) {
		this.bewegung.setzeRichtung(winkel);
		// this.setzeBildWinkel(this.bewegung.getRichtung());
	}

	
	//
	public void setzeMitMausVerschiebbar(boolean mmv){
		if (mb==null && mmv ){
			mb = new Mausbewegung();
//			mb.setzeAktiv(mmv);
		}
		if (mb!=null && !mmv){
			mb.entferneListener();
			mb=null;
		}
	}
	public boolean istMitMausVerschiebbar(){		
			return mb!=null;		
	}
	
	private Mausbewegung mb;
	class Mausbewegung {
		private boolean unten = false;
		private double aktx;
		private double akty;
		private double altx = 0;
		private double alty = 0;
		private Point mp;
//		private boolean aktiv = false;
//		public void setzeAktiv(boolean aktiv){
//			this.aktiv=aktiv;
//		}
		MouseAdapter madapt1,madapt2; 
		Mausbewegung() {
			madapt1 = new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
//					if (aktiv && !e.isMetaDown()) {
					if ( !e.isMetaDown()) {
						unten = true;
						mp = meineKomponente.getParent().getLocationOnScreen();
//						altx = getWelt().mausHPosition();
//						alty = getWelt().mausVPosition();
						altx =  e.getXOnScreen()-mp.x;
						alty =  e.getYOnScreen()-mp.y;
					}
				}

				public void mouseReleased(MouseEvent e) {
//					if (aktiv && !e.isMetaDown()) {
						if ( !e.isMetaDown()) {
						unten = false;
					}
				}
			};
			madapt2 = new MouseAdapter() {
				public void mouseDragged(MouseEvent e) {
				if (unten){
//					if (unten && aktiv) {
						mp = meineKomponente.getParent().getLocationOnScreen();
						aktx =  e.getXOnScreen()-mp.x;
						akty =  e.getYOnScreen()-mp.y;
//						aktx = getWelt().mausHPosition();
//						akty = getWelt().mausVPosition();
						if (altx != aktx || alty != akty) {
							double neux = hPosition()
									+ (aktx - altx);
							double neuy = vPosition()
									+ (akty - alty);
							setzePosition(neux, neuy);
							altx = aktx;
							alty = akty;
						}
					}
				}	
			};
			meineKomponente.addMouseListener(madapt1);
//			meineKomponente.addMouseListener(new MouseAdapter() {
//				public void mousePressed(MouseEvent e) {
//					if (!e.isMetaDown()) {
//						unten = true;
//						mp = meineKomponente.getParent().getLocationOnScreen();
////						altx = getWelt().mausHPosition();
////						alty = getWelt().mausVPosition();
//						altx =  e.getXOnScreen()-mp.x;
//						alty =  e.getYOnScreen()-mp.y;
//					}
//				}
//
//				public void mouseReleased(MouseEvent e) {
//					if (!e.isMetaDown()) {
//						unten = false;
//					}
//				}
//			});
			meineKomponente.addMouseMotionListener(madapt2);
//			meineKomponente.addMouseMotionListener(new MouseAdapter() {
//
//
//				public void mouseDragged(MouseEvent e) {
//					if (unten && aktiv) {
//						mp = meineKomponente.getParent().getLocationOnScreen();
//						aktx =  e.getXOnScreen()-mp.x;
//						akty =  e.getYOnScreen()-mp.y;
////						aktx = getWelt().mausHPosition();
////						akty = getWelt().mausVPosition();
//						if (altx != aktx || alty != akty) {
//							double neux = hPosition()
//									+ (aktx - altx);
//							double neuy = vPosition()
//									+ (akty - alty);
//							setzePosition(neux, neuy);
//							altx = aktx;
//							alty = akty;
//						}
//					}
//				}		
//			});
			

		}
		public void entferneListener(){
			meineKomponente.removeMouseListener(madapt1);
			meineKomponente.removeMouseMotionListener(madapt2);
		}
//		public boolean istAktiv() {
//			return aktiv;
//		}
	}

	

}
