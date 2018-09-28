package basiX;

//import java.awt.*;
//import java.awt.geom.AffineTransform;
//import java.awt.geom.Rectangle2D;
//import java.awt.geom.Rectangle2D.Double;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.awt.image.BufferedImage;
import java.util.Vector;

import javax.swing.event.MouseInputAdapter;

//import basis.*;
import basiX.swing.BufferedCanvas;
import basiX.swing.Picture;
import basiX.vw.DasFenster;

import java.awt.Point;

/**
 * Ein SpriteBild ist eine rechteckige Komponente, die ein oder mehrere Pixelbilder
 * anzeigen kann. Das jeweils sichtbare Pixelbild kann skaliert, gedreht und
 * gespiegelt werden. Ferner kann ein SpriteBild feststellen, ob das jeweils angezeigte Bild sich
 * mit einem anderen überschneidet, dabei können transparente Bereiche des SpriteBildes
 * unberücksichtigt bleiben.
 * SpriteBilder können auf Wunsch mit der Maus verschoben werden.
 * 
 * @author Georg Dick
 * 
 */
public class SpriteBild extends Bild {
	private Vector<Picture> pictures = new Vector<Picture>();
	private int nr = 0;


	public SpriteBild() {
		this(0, 0, DasFenster.hauptLeinwand(),true);
	}
	public SpriteBild(boolean sichtbar) {
		this(0, 0, DasFenster.hauptLeinwand(),sichtbar);
	}
	public SpriteBild(String pfad) {
		this(pfad, 0, 0, DasFenster.hauptLeinwand(),true);
	}
	public SpriteBild(String pfad,boolean sichtbar) {
		this(pfad, 0, 0, DasFenster.hauptLeinwand(),sichtbar);
	}
	public SpriteBild(double x, double y, Komponente p) {
		super(x, y, p,true);
		pictures.add(this.getPicture());
	}
	public SpriteBild(double x, double y, Komponente p, boolean sichtbar) {
		super(x, y, p, sichtbar);
		pictures.add(this.getPicture());
	}

	/**
	 * erzeugt ein Bild
	 * 
	 * @param pfad
	 *            Dateipfad für das Bild
	 * @param x
	 *            x-Koordinate der linken obere Ecke des Rechtecks, in dem das
	 *            Bild liegt
	 * @param y
	 *            y-Koordinate der linken obere Ecke des Rechtecks, in dem das
	 *            Bild liegt
	 * @param p
	 *            Komponente in die das Bild eingebettet werden soll.
	 */
	public SpriteBild(String pfad, double x, double y, Komponente p) {
		super(pfad, x, y, p,true);
		pictures.add(this.getPicture());
	}

	/**
	 * erzeugt ein Bild
	 * 
	 * @param pfad
	 *            Dateipfad für das Bild
	 * @param x
	 *            x-Koordinate der linken obere Ecke des Rechtecks, in dem das
	 *            Bild liegt
	 * @param y
	 *            y-Koordinate der linken obere Ecke des Rechtecks, in dem das
	 *            Bild liegt
	 * @param p
	 *            Komponente in die das Bild eingebettet werden soll.
	 */
	public SpriteBild(String pfad, double x, double y, Komponente p, boolean sichtbar) {
		super(pfad, x, y, p,sichtbar);
		pictures.add(this.getPicture());
	}

	

	/**
	 * erzeugt ein Bild auf einem Fenster
	 * 
	 * @param pfad
	 *            Dateipfad für das Bild
	 * @param x
	 *            x-Koordinate der linken obere Ecke des Rechtecks, in dem das
	 *            Bild liegt
	 * @param y
	 *            y-Koordinate der linken obere Ecke des Rechtecks, in dem das
	 *            Bild liegt
	 */
	public SpriteBild(String pfad, double x, double y) {
		this(pfad, x, y, DasFenster.hauptLeinwand(),true);
	}
	/**
	 * erzeugt ein Bild auf einem Fenster
	 * 
	 * @param pfad
	 *            Dateipfad für das Bild
	 * @param x
	 *            x-Koordinate der linken obere Ecke des Rechtecks, in dem das
	 *            Bild liegt
	 * @param y
	 *            y-Koordinate der linken obere Ecke des Rechtecks, in dem das
	 *            Bild liegt
	 */
	public SpriteBild(String pfad, double x, double y,boolean sichtbar) {
		this(pfad, x, y, DasFenster.hauptLeinwand(),sichtbar);
	}
	/**
	 * erzeugt ein Bild
	 * 
	 * @param pfad
	 *            Array von Dateipfaden auf Bilder für das Bild
	 * @param x
	 *            x-Koordinate der linken obere Ecke des Rechtecks, in dem das
	 *            Sprite liegt
	 * @param y
	 *            x-Koordinate der linken obere Ecke des Rechtecks, in dem das
	 *            Sprite liegt
	 * @param p
	 *            Komponente in die das Bild eingebettet werden soll.
	 */
	public SpriteBild(String[] pfad, double x, double y, Komponente p) {
		this(pfad,x,y,p,true);
	}
	/**
	 * erzeugt ein Bild
	 * 
	 * @param pfad
	 *            Array von Dateipfaden auf Bilder für das Bild
	 * @param x
	 *            x-Koordinate der linken obere Ecke des Rechtecks, in dem das
	 *            Sprite liegt
	 * @param y
	 *            x-Koordinate der linken obere Ecke des Rechtecks, in dem das
	 *            Sprite liegt
	 * @param p
	 *            Komponente in die das Bild eingebettet werden soll.
	 */
	public SpriteBild(String[] pfad, double x, double y, Komponente p,boolean sichtbar) {
		this(pfad[0],x,y,p);
		pictures = new Vector<Picture>();
		for (int i = 0; i < pfad.length; i++) {
			Picture b = new Picture(pfad[i]);
			b.setId(i);
			pictures.add(b);
			if (i == 0) {
				bild = b;
			}
		}
		this.bildInit(sichtbar);
	}
	/**
	 * erzeugt ein Bild
	 * 
	 * @param bild
	 *            Imageobjekt für das Bild
	 * @param x
	 *            x-Koordinate der linken obere Ecke des Rechtecks, in dem das
	 *            Bild liegt
	 * @param y
	 *            y-Koordinate der linken obere Ecke des Rechtecks, in dem das
	 *            Bild liegt
	 * @param p
	 *            Komponente in die das Bild eingebettet werden soll.
	 */
	public SpriteBild(Picture bild, double x, double y, Komponente p) {
		super(bild, x, y, p,true );
		pictures.add(bild);
	}
	/**
	 * erzeugt ein Bild
	 * 
	 * @param bild
	 *            Imageobjekt für das Bild
	 * @param x
	 *            x-Koordinate der linken obere Ecke des Rechtecks, in dem das
	 *            Bild liegt
	 * @param y
	 *            y-Koordinate der linken obere Ecke des Rechtecks, in dem das
	 *            Bild liegt
	 * @param p
	 *            Komponente in die das Bild eingebettet werden soll.
	 */
	public SpriteBild(Picture bild, double x, double y, Komponente p,boolean sichtbar) {
		super(bild, x, y, p,sichtbar );
		pictures.add(bild);
	}

	/**
	 * erzeugt ein Bild auf dem zuletzt erzeugten Fenster. Dies muss vorhanden
	 * sein.
	 * 
	 * @param bild
	 *            Imageobjekt für das Bild
	 * @param x
	 *            x-Koordinate der linken obere Ecke des Rechtecks, in dem das
	 *            Bild liegt
	 * @param y
	 *            y-Koordinate der linken obere Ecke des Rechtecks, in dem das
	 *            Bild liegt
	 * 
	 */
	public SpriteBild(Picture b, int x, int y) {
		this(b, x, y, DasFenster.hauptLeinwand(),true);
	}
	/**
	 * erzeugt ein Bild auf dem zuletzt erzeugten Fenster. Dies muss vorhanden
	 * sein.
	 * 
	 * @param bild
	 *            Imageobjekt für das Bild
	 * @param x
	 *            x-Koordinate der linken obere Ecke des Rechtecks, in dem das
	 *            Bild liegt
	 * @param y
	 *            y-Koordinate der linken obere Ecke des Rechtecks, in dem das
	 *            Bild liegt
	 * 
	 */
	public SpriteBild(Picture b, int x, int y,boolean sichtbar) {
		this(b, x, y, DasFenster.hauptLeinwand(),sichtbar);
	}
	/**
	 * liefert das aktuell dargestellte PixelBild als Image-Objekt
	 */
	public Picture holeBild() {
		return bild;
	}

	/**
	 * setzt ein SpriteBild und fügt es den Bildern der SpriteKomponente hinzu.
	 * Das Bild wird anschließend dargestellt.
	 */
	@Override
	public void setzeBilddaten(Picture bild) {
		this.bild = bild;
		pictures.add(bild);
		bildInit(this.istSichtbar());
	}

	/**
	 * Lädt ein Bild über einen Dateidialog
	 */
	@Override
	public void ladeBild() {
		this.bild = new Picture(1, 1);
		this.bild.loadImageByDialog();
		pictures.add(bild);
		bildInit(this.istSichtbar());
	}
    
	

	/**
	 * wählt aus, welches Bild zur Anzeige kommt. Es wird die ID der
	 * SpriteBilder ausgewertet. Wird ein unzulässiger Wert übergeben, so
	 * erfolgt keine Änderung der Anzeige
	 * 
	 * @param pid
	 */
	public void waehleBild(int pid) {
		double w = this.bildWinkel();
		for (int i = 0; i < pictures.size(); i++) {
			if (pictures.get(i).getId() == pid) {
				bild = pictures.get(i);
				nr = i;
				bild.rotateTo(w);
				malGrundlage.setBufferedImage(bild.getRecentImage());
				malGrundlage.repaint();
				return;
			}
		}
	}

	/**
	 * bringt das nächste Pixelbild zur Anzeige Die Reihenfolge entspricht der
	 * Reihenfolge der Zuweisung der Pixelbilder zum Bild-Objekt. Nach Anzeige
	 * des letzten Bildes wird das erste angezeigt ("Rotation")
	 */
	public void waehleNaechstesBild() {
		nr++;
		nr = nr % pictures.size();
		// System.out.println(nr+ " von "+bilder.size());
		bild = pictures.get(nr);
		bild.rotateTo(this.bildWinkel());
		malGrundlage.setBufferedImage(bild.getRecentImage());
		malGrundlage.repaint();
	}

	/**
	 * sorgt dafür, dass alle Pixelbilder die gleichen Einstellungen (Winkel,
	 * Skalierung, Spiegelung) wie das aktuelle angezeigte Pixelbild haben
	 */
	public void synchronisiereBilder() {
		Picture im;
		for (int i = 0; i < pictures.size(); i++) {
			im = pictures.get(i);
			im.rotateToScaleToFlip(bild.getRotationAngle(), bild
					.getScaleFactorX(), bild.getScaleFactorY(),
					bild.getFlipInfo());
		}
	}

	/**
	 * fügt das Bild hinzu, dessen Pfad angegeben wird
	 */
	@Override
	public void ladeBild(String pfad) {
		bild = new Picture(pfad);
		pictures.add(bild);
		bildInit(true);
	}

	/**
	 * fügt das Bild hinzu, dessen Pfad angegeben wird. Dieses Bild erhält
	 * zusätzlich eine ID-Nummer
	 */
	@Override
	public void ladeBild(String pfad, int id) {
		bild = new Picture(pfad);
		bild.setId(id);
		pictures.add(bild);
		bildInit(true);
	}

	/** ermöglicht es, das Bild mit der Maus zu verschieben 
	 * 
	 * @param mmv
	 */
		public void setzeMitMausVerschiebbar(boolean mmv){
			if (mb==null && mmv ){
				mb = new Mausbewegung();
//				mb.setzeAktiv(mmv);
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
			private java.awt.Point mp;
//			private boolean aktiv = false;
//			public void setzeAktiv(boolean aktiv){
//				this.aktiv=aktiv;
//			}
			MouseAdapter madapt1,madapt2; 
			Mausbewegung() {
				madapt1 = new MouseAdapter() {
					public void mousePressed(MouseEvent e) {
//						if (aktiv && !e.isMetaDown()) {
						if ( !e.isMetaDown()) {
							unten = true;
							mp = SpriteBild.this.meineKomponente.getParent().getLocationOnScreen();
//							altx = getWelt().mausHPosition();
//							alty = getWelt().mausVPosition();
							altx =  e.getXOnScreen()-mp.x;
							alty =  e.getYOnScreen()-mp.y;
						}
					}

					public void mouseReleased(MouseEvent e) {
//						if (aktiv && !e.isMetaDown()) {
							if ( !e.isMetaDown()) {
							unten = false;
						}
					}
				};
				madapt2 = new MouseAdapter() {
					public void mouseDragged(MouseEvent e) {
					if (unten){
//						if (unten && aktiv) {
							mp = SpriteBild.this.meineKomponente.getParent().getLocationOnScreen();
							aktx =  e.getXOnScreen()-mp.x;
							akty =  e.getYOnScreen()-mp.y;
//							aktx = getWelt().mausHPosition();
//							akty = getWelt().mausVPosition();
							if (altx != aktx || alty != akty) {
								double neux = SpriteBild.this.hPosition()
										+ (aktx - altx);
								double neuy = SpriteBild.this.vPosition()
										+ (akty - alty);
								SpriteBild.this.setzePosition(neux, neuy);
								altx = aktx;
								alty = akty;
							}
						}
					}	
				};
				SpriteBild.this.meineKomponente.addMouseListener(madapt1);
//				meineKomponente.addMouseListener(new MouseAdapter() {
//					public void mousePressed(MouseEvent e) {
//						if (!e.isMetaDown()) {
//							unten = true;
//							mp = meineKomponente.getParent().getLocationOnScreen();
////							altx = getWelt().mausHPosition();
////							alty = getWelt().mausVPosition();
//							altx =  e.getXOnScreen()-mp.x;
//							alty =  e.getYOnScreen()-mp.y;
//						}
//					}
	//
//					public void mouseReleased(MouseEvent e) {
//						if (!e.isMetaDown()) {
//							unten = false;
//						}
//					}
//				});
				SpriteBild.this.meineKomponente.addMouseMotionListener(madapt2);
//				meineKomponente.addMouseMotionListener(new MouseAdapter() {
	//
	//
//					public void mouseDragged(MouseEvent e) {
//						if (unten && aktiv) {
//							mp = meineKomponente.getParent().getLocationOnScreen();
//							aktx =  e.getXOnScreen()-mp.x;
//							akty =  e.getYOnScreen()-mp.y;
////							aktx = getWelt().mausHPosition();
////							akty = getWelt().mausVPosition();
//							if (altx != aktx || alty != akty) {
//								double neux = hPosition()
//										+ (aktx - altx);
//								double neuy = vPosition()
//										+ (akty - alty);
//								setzePosition(neux, neuy);
//								altx = aktx;
//								alty = akty;
//							}
//						}
//					}		
//				});
				

			}

			public void entferneListener(){
				SpriteBild.this.meineKomponente.removeMouseListener(madapt1);
				SpriteBild.this.meineKomponente.removeMouseMotionListener(madapt2);
			}
		}
//	/**
//	 * 
//	 * @return liefert einen zuletzt erkannten Kollisionspunkt. Wenn keine Kollision
//	 *         stattgefunden hat oder der Punkt gelöscht wurde wird <b>null</b>
//	 *         geliefert.
//	 */
//	public Punkt getKollisionspunkt() {
//		return kollisionspunkt;
//	}

	// Ereignisbearbeitung
		
//		private transient Vector<SpriteLauscher> spriteLauscher;
//		
//		/** registriert einen Reglerlauscher */
//		public synchronized void setzeReglerLauscher(SpriteLauscher l) {
//			Vector<SpriteLauscher> v = spriteLauscher == null ? new Vector<SpriteLauscher>(2)
//					: (Vector<SpriteLauscher>) spriteLauscher.clone();
//			if (!v.contains(l)) {
//				v.addElement(l);
//				spriteLauscher = v;
//			}
//		}
//
//		/** entfernt einen Reglerlauscher */
//		public synchronized void entferneReglerLauscher(SpriteLauscher l) {
//			if (spriteLauscher != null && spriteLauscher.contains(l)) {
//				Vector<SpriteLauscher> v = (Vector<SpriteLauscher>)spriteLauscher.clone();
//				v.removeElement(l);
//				spriteLauscher = v;
//			}
//		}
//		
//		/**  */
//		protected void fireSpriteEvent(SpriteEvent se) {
//			if (spriteLauscher != null) {				
//				for (SpriteLauscher sl: spriteLauscher)
//					{sl.bearbeiteSpriteEreignis(se);}
//			}
//		}
//		
//		
//	
//		@Override
//		protected void bildInit(boolean visible) {			
//			super.bildInit(visible);
//			this.fireSpriteEvent(new SpriteEvent(this,SpriteEvent.EVNeueGroesse,-1,-1,this.breite(),this.hoehe()));
//		}
}
