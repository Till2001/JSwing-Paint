package basiX.swing;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.RescaleOp;


public class PictAnim extends Picture {
	private int x = 0, y = 0;
	private float alpha = 1f;
	private Point kollisionspunkt = null;
	// Umgebendes Rechteck, enthält auch die Information für die Position auf
	// einer Unterlage
	// linke obere Ecke. Das ist für eventuelle Kollisionen von Bedeutung
	private Rectangle2D.Double umgebendesRechteck;
	// bei vereinfachter Kollisionserkennung wird nicht pixelgenau
	// geprüft, sondern nur ein Durchschnitt mit dem das Bild umgebenden
	// Rechteck
	private boolean vereinfachteKollisionerkennung=false;

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public void setTransparency(float alpha) {
		this.alpha = alpha;//		
	} 
	
	public PictAnim() {
		super();
	}

	public PictAnim(BufferedImage bi) {
		super(bi);
	}

	public PictAnim(double w, double h) {
		super(w, h);
	}

	public PictAnim(String pfad, int id) {
		super(pfad, id);
	}

	public PictAnim(String pfad) {
		super(pfad);
	}

	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
	

	public float getTransparency() {
		return this.alpha;
	}
	
	
	
	private Rectangle2D.Double getRechteck() {
		if (umgebendesRechteck == null) {
			umgebendesRechteck = new Rectangle2D.Double();
		}
		umgebendesRechteck.x = this.x;
		umgebendesRechteck.y = this.y;
		umgebendesRechteck.width = this.getWidth();
		umgebendesRechteck.height = this.getHeight();
		return umgebendesRechteck;
	}

	private Rectangle2D.Double getSubRec(Rectangle2D.Double source,
			Rectangle2D.Double part) {

		// Rechteck erzeugen
		Rectangle2D.Double sub = new Rectangle2D.Double();
		sub.x = source.x > part.x ? 0 : part.x - source.x;
		sub.y = source.y > part.y ? 0 : part.y - source.y;
		sub.width = part.width;
		sub.height = part.height;
		return sub;
	}
	
	/**
	 * liefert den Wert true, wenn das Bild selbst mit einem Bild b im Bereich
	 * kollidiert. In diesem Fall wird ein Kollisionspunkt berechnet, der
	 * erfragt werden kann. Bei vereinfachter Kollsisionserkennung wird
	 * lediglich überprüft, ob sich die das Bild umgebenden Rechtecke
	 * überschneiden. Andernfalls werden pixelgenau nach Überschneidungen im
	 * nichttransparenten Bereich der Bilder gesucht. Bei einer Kollision werden
	 * die Koordinaten eines Punktes als Kollisionspunkt notiert. Man kann über
	 * die Abfrage @see getKollisionspunkt() den Punkt erfragen.
	 * 
	 * @param bi
	 * @return
	 */
	public boolean collidesWith(PictAnim bi) {
		Rectangle2D.Double durchschnitt = (Double) this.getRechteck()
				.createIntersection(bi.getRechteck());
		if ((durchschnitt.width < 1) || (durchschnitt.height < 1)) {
			return false;
		}

		if (vereinfachteKollisionerkennung) {
			kollisionspunkt = new Point((int) durchschnitt.x,
					(int) durchschnitt.y);
			return true;
		}
		try {
			// Rechtecke in Bezug auf die jeweiligen Images
			Rectangle2D.Double durchschnittRechteckEigenesBild = getSubRec(
					this.umgebendesRechteck, durchschnitt);
			Rectangle2D.Double durchschnittRechteckAnderesBild = getSubRec(bi
					.getRechteck(), durchschnitt);

			BufferedImage durchschnittEigenesBild = this.getRecentImage()
					.getSubimage((int) durchschnittRechteckEigenesBild.x,
							(int) durchschnittRechteckEigenesBild.y,
							(int) durchschnittRechteckEigenesBild.width,
							(int) durchschnittRechteckEigenesBild.height);

			BufferedImage durchschnittAnderesBild = bi
					.getRecentImage().getSubimage(
							(int) durchschnittRechteckAnderesBild.x,
							(int) durchschnittRechteckAnderesBild.y,
							(int) durchschnittRechteckAnderesBild.width,
							(int) durchschnittRechteckAnderesBild.height);

			for (int x = 0; x < durchschnittEigenesBild.getWidth(); x += 1) {
				for (int y = 0; y < durchschnittAnderesBild.getHeight(); y += 1) {
					int rgb1 = durchschnittEigenesBild.getRGB(x, y);
					int rgb2 = durchschnittAnderesBild.getRGB(x, y);
					// Beide Pixel undurchsichtig?
					if ((((rgb1 >> 24) & 0xff) != 0)
							&& (((rgb2 >> 24) & 0xff) != 0)) {
						kollisionspunkt = new Point(
								(int) durchschnittRechteckEigenesBild.x + x,
								(int) durchschnittRechteckEigenesBild.y + y);
						return true;
					}
				}
			}
		} catch (Exception e) {
		}
		return false;
	}
	private float[] scales = { 1f, 1f, 1f, 0.5f };
	private float[] offsets = new float[4];
	private RescaleOp rop;

	private void setOpacity(float opacity) {
		scales[3] = opacity;
		rop = new RescaleOp(scales, offsets, null);
	}
	
	public void draw(Graphics2D g2d,ImageObserver obs){
		if (this.getTransparency() != 1f) {
			this.setOpacity(this.getTransparency());
			g2d.drawImage(this.getRecentImage(), rop, this.getX(), this
					.getY());
		} else {
			g2d.drawImage(this.getRecentImage(), this.getX(), this.getY(),
					obs);
		}
	}
	
}
