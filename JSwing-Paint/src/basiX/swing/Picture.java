package basiX.swing;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Locale;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import basiX.util.Point;

import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException; 
//import java.awt.print.PrinterGraphics;
//import java.awt.print.PrinterJob;

import javax.print.*;
import javax.print.attribute.*;
import javax.print.attribute.standard.*;


/**
 * Pictureobjekte verwalten Pixelbilder.
 * Pixelbilder können aus Dateien geladen werden oder per BufferedImage übermittelt werden.
 * Ferner sind Transformationen (Rotation, Spiegelung, Streckung) möglich.
 * Die Transformationen erzeugen ein weiteres BufferedImage (recent), so dass das Original (Basic) nicht
 * verändert wird. 
 * Transformationen können verknüpft werden. 
 * Das erzeugte Bild kann gespeichert, gedruckt und als BufferedImage ausgeliefert werden. Ferner kann ein
 * Picture-Objekt einen Klon von sich erzeugen.
 * 
 * @author Georg Dick
 *
 */
public class Picture implements Printable{
	final static int FLIPHORIZONTAL = 1;
	final static int FLIPVERTICAL = 2;
	final static int FLIPHORIZONTALANDVERTICAL = 3;
	final static int NOFLIP = 0;
	// Dateifilter für Laden- Speichern von Bildern.
	public static FileFilter filefilter = new FileFilter() {
		public boolean accept(File f) {
			return f.isDirectory()
					|| f.getName().toLowerCase().endsWith(".png")
					|| f.getName().toLowerCase().endsWith(".gif")
					|| f.getName().toLowerCase().endsWith(".jpg")
					|| f.getName().toLowerCase().endsWith(".bmp");
		}

		public String getDescription() {
			return "PNG, GIF, JPG, BMP";
		}
	};
	protected BufferedImage aktuellesBild;
	protected BufferedImage bildvorlage;
	private String pfad2Bild = "";
	private int id = -1;// ermöglicht eine Kennung zur Unterscheidung von
	// Pictureobjekten
	private double winkel = 0;// Winkel in dem das dargestellte BufferedImage
	// gegenüber dem originalen gedreht ist
	private int rotationsMittelpunktX; // für das Image
	private int rotationsMittelpunktY; // für das Image
	private int cSpiegelung = NOFLIP; // 0: keine Spiegelung, 1: Spiegelung nur
	// an
	// der horizontalen Achse, 2: Spiegelung nur
	// an der vertikalen Achse, 3: horizontal und vertikal
	// gibt den aktuellen Skalierungsfaktor in x bzw. y-Richtung an
	private double sfaktorx = 1, sfaktory = 1;
	private boolean rotationmitAnpassung = false;
	private String path2SavedImage = "";

	// Bilder zu setzen

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPicturePath() {
		return pfad2Bild;
	}

	public String getLastSavedPicturePath() {
		return path2SavedImage;
	}

	public double getScaleFactorX() {
		return sfaktorx;
	}

	public double getScaleFactorY() {
		return sfaktory;
	}

	/**
	 * erzeugt ein Pictureobjekt
	 * 
	 * @param pfad
	 *            Pfad auf Image
	 * 
	 * 
	 */
	public Picture(String pfad) {
		this(pfad, -1);
	}

	/**
	 * Erzeugt ein Picture
	 * 
	 * @param pfad
	 *            Pfad für ein Image für das Picture
	 * @param id
	 *            Id
	 */
	public Picture(String pfad, int id) {
		this.id = id;
		sfaktorx = 1;
		sfaktory = 1;
		bildvorlage = this.loadImageFile(pfad);
		aktuellesBild = bildvorlage;
		rotationsMittelpunktX = (int) (aktuellesBild.getWidth() / 2);
		rotationsMittelpunktY = (int) (aktuellesBild.getHeight() / 2);
	}

	public Picture(){
		this(10,10);
	}
	
	public Picture(double w, double h) {
		sfaktorx = 1;
		sfaktory = 1;
		// pics[0] = this.skaliere(this.loadPic(pfad),skalierungx,skalierungy);
		aktuellesBild = new BufferedImage((int) w, (int) h,
				BufferedImage.TYPE_INT_ARGB);
//		GraphicsEnvironment.getLocalGraphicsEnvironment()
//				.getDefaultScreenDevice().getDefaultConfiguration()
//				.createCompatibleImage((int) w, (int) h, Transparency.BITMASK);
		bildvorlage = aktuellesBild;
		pfad2Bild = "";
		rotationsMittelpunktX = (int) Math.round(aktuellesBild.getWidth() / 2.);
		rotationsMittelpunktY = (int) Math
				.round(aktuellesBild.getHeight() / 2.);
	}

	

	public Picture(BufferedImage bi) {
		sfaktorx = 1;
		sfaktory = 1;
		bildvorlage = bi;
		aktuellesBild = bildvorlage;
		pfad2Bild = "";
		rotationsMittelpunktX = (int) Math.round(aktuellesBild.getWidth() / 2.);
		rotationsMittelpunktY = (int) Math
				.round(aktuellesBild.getHeight() / 2.);
	}

	// /**
	// * wählt aus, welches Bild zur Anzeige kommt nr beginnt bei 0!
	// *
	// * @param nr
	// */
	// public BufferedImage waehleBild(int nr) {
	// try {
	//
	// aktuellesBild = this.brotiere(bildvorlage, winkel,
	// rotationsMittelpunktX, rotationsMittelpunktY);
	// bildaktuell = false;
	// return aktuellesBild;
	// } catch (Exception e) {
	// bildaktuell = false;
	// return aktuellesBild;
	// }
	// }

	/**
	 * Lädt die PixelBildvorlage
	 */
	public void loadImage(String path) {
		aktuellesBild = this.loadImageFile(path);
		bildvorlage = aktuellesBild;
	}

	/**
	 * Speichert das Bild mittels Dateidialog
	 */
	public boolean saveByDialog() {
		try {
			String start = "";
			if (this.getLastSavedPicturePath() != null
					&& !this.getLastSavedPicturePath().equals("")) {
				start = this.getLastSavedPicturePath();
			} else {
				if (this.getPicturePath() != null
						&& !this.getPicturePath().equals("")) {
					start = this.getPicturePath();
				}
			}

			JFileChooser fc = new JFileChooser(start.equals("") ? null : start);
			fc.setFileFilter(filefilter);
			int returnVal = fc.showSaveDialog(null);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				// System.out.println(file.getPath()+" "+file.getName());
				if (this.saveRecent(file.getPath())) {

					return true;
				}
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	
	/**
	 * Öffnet Dateidialog zum Öffnen einer png-,jpg- oder gif-Bilddatei. Diese
	 * wird eingelesen und und auf der Zeichenfläche angezeigt
	 */
	public boolean loadImageByDialog() {
		try {
			String start = "";
			if (this.getLastSavedPicturePath() != null
					&& !this.getLastSavedPicturePath().equals("")) {
				start = this.getLastSavedPicturePath();
			} else {
				if (this.getPicturePath() != null
						&& !this.getPicturePath().equals("")) {
					start = this.getPicturePath();
				}
			}

			JFileChooser fc = new JFileChooser(start.equals("") ? null : start);
			fc.setFileFilter(filefilter);
			int returnVal = fc.showOpenDialog(null);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				System.out.println(fc.getSelectedFile().getPath());
				aktuellesBild = ImageIO.read(fc.getSelectedFile());
				bildvorlage = aktuellesBild;

			} else {
				return false;
			}

		} catch (Exception exc) {
			return false;
		}
		return true;
	}

	private BufferedImage rotateBI(BufferedImage bimg, double winkel,
			double rx, double ry) {
		if (rotationmitAnpassung) {
			return rotateWithDimensionAdjustment(bimg, winkel, rx, ry);
		}
		return rotateWithoutDimensionAdjustment(bimg, winkel, rx, ry);
	}

	/**
	 * Liefert ein neues Bild, das durch Rotation des gebenenen Bildes um den
	 * gegebenen Mittelpunkt entsteht. Die Größe des Bildes entspricht dem
	 * Ausgangsbild. Dies kann dazu führen, dass Teile des ursprünglichen Bildes
	 * im neuen Bild abgeschnitten sind. Das Ausgangsbild selbst wird nicht
	 * verändert.
	 * 
	 * @param bimg
	 * @param winkel
	 * @param rx
	 * @param ry
	 * @return
	 */
	private BufferedImage rotateWithoutDimensionAdjustment(BufferedImage bimg,
			double winkel, double rx, double ry) {
		BufferedImage neuimg = new BufferedImage(bimg.getWidth(), bimg.getHeight(),
				BufferedImage.TYPE_INT_ARGB);
//		GraphicsEnvironment
//				.getLocalGraphicsEnvironment()
//				.getDefaultScreenDevice()
//				.getDefaultConfiguration()
//				.createCompatibleImage(
//				// (int)(xmax-xmin),(int)(ymax-ymin),
//						bimg.getWidth(), bimg.getHeight(), Transparency.BITMASK);
		Graphics2D g = (Graphics2D) neuimg.getGraphics();
		// g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		// RenderingHints.VALUE_ANTIALIAS_ON);
		// speichern der aktuellen Transformation
		AffineTransform saveAT = g.getTransform();
		// Transformation ausführen
		g.rotate(-Math.toRadians(winkel), rx, ry);
		// while(!g.drawImage(bimg, 0, 0, null));
		g.drawImage(bimg, 0, 0, null);
		g.setTransform(saveAT);
		return neuimg;
	}

	/**
	 * Liefert ein neues Bild, das durch Rotation des Ausgangsbildes um den
	 * gegebenen Mittelpunkt entsteht. Die Größe des neuen Bildes wird so
	 * gewählt, dass das rotierte Ausgangsbild gerade komplett hineinpasst. Das
	 * Ausgangsbild selbst wird nicht verändert.
	 * 
	 * @param bimg
	 * @param winkel
	 * @param rx
	 * @param ry
	 * @return
	 */
	private BufferedImage rotateWithDimensionAdjustment(BufferedImage bimg,
			double winkel, double rx, double ry) {
		AffineTransform rot = new AffineTransform();
		rot.rotate(-Math.toRadians(winkel), 0, 0);
		double[] matrix = new double[4];
		// Die Drehmatrix
		// m00 m01
		// m10 m11
		// wird in der Form eines eindimensionalen Feldes
		// m00 m10 m01 m11 geliefert
		// Achtung der Parameter dient der Rückgabe!
		rot.getMatrix(matrix);
		double[] x = new double[4];
		double[] y = new double[4];
		// Die vier Bildecken (Ursprung: Rotationsmittelpunkt!)
		x[0] = -rx;
		x[1] = bimg.getWidth() - rx;
		x[2] = x[0];
		x[3] = x[1];
		y[0] = -ry;
		y[1] = y[0];
		y[2] = bimg.getHeight() - ry;
		y[3] = y[2];

		// Multiplikation mit der Matrix
		double xmin = matrix[0] * x[0] + matrix[2] * y[0];
		double ymin = matrix[1] * x[0] + matrix[3] * y[0];
		double xmax = xmin;
		double ymax = ymin;
		double xtemp, ytemp;
		for (int i = 1; i < 4; i++) {
			xtemp = matrix[0] * x[i] + matrix[2] * y[i];
			ytemp = matrix[1] * x[i] + matrix[3] * y[i];

			if (xtemp < xmin) {
				xmin = xtemp;
			} else if (xtemp > xmax) {
				xmax = xtemp;
			}
			if (ytemp < ymin) {
				ymin = ytemp;
			} else if (ytemp > ymax) {
				ymax = ytemp;
			}
		}
		int breite = (int) Math.round(xmax - xmin < bimg.getWidth() ? bimg
				.getWidth() : xmax - xmin);
		int hoehe = (int) Math.round(ymax - ymin < bimg.getHeight() ? bimg
				.getHeight() : ymax - ymin);

		// System.out.println(xmin+" "+xmax+ " "+ymin+" "+ymax);
		int versatzx, versatzy;
		versatzx = (int) Math.round((breite - bimg.getWidth()) / 2.);
		versatzy = (int) Math.round((hoehe - bimg.getHeight()) / 2.);
		BufferedImage neuimg =new BufferedImage(breite, hoehe,
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) neuimg.getGraphics();
		// while (!g.drawImage(bimg, versatzx, versatzy, null));
		g.drawImage(bimg, versatzx, versatzy, null);
		this.rotationsMittelpunktX = (int) Math.round(neuimg.getWidth() / 2.);
		this.rotationsMittelpunktY = (int) Math.round(neuimg.getHeight() / 2.);
		return rotateWithoutDimensionAdjustment(neuimg, winkel,
				rotationsMittelpunktX, rotationsMittelpunktY);
	}

	private BufferedImage flipBI(BufferedImage bimg, boolean achseHorizontal) {
		AffineTransform spiegelung = new AffineTransform();
		if (achseHorizontal) {
			spiegelung.setTransform(1, 0, 0, -1, 0, bimg.getHeight());
		} else {
			spiegelung.setTransform(-1, 0, 0, 1, bimg.getWidth(), 0);
		}
		BufferedImage neuimg = new BufferedImage(bimg.getWidth(), bimg.getHeight(),
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) neuimg.getGraphics();

		AffineTransform saveAT = g.getTransform();
		g.transform(spiegelung);
		g.drawImage(bimg, 0, 0, null);
		g.setTransform(saveAT);
		return neuimg;
	}

	private BufferedImage scaleBI(BufferedImage bimg, double sx, double sy) {
		BufferedImage neuimg =new BufferedImage((int) Math.round(bimg.getWidth() * sx),
				(int) Math.round(bimg.getHeight() * sy),
				BufferedImage.TYPE_INT_ARGB);
					
		Graphics2D g = (Graphics2D) neuimg.getGraphics();
		AffineTransform saveAT = g.getTransform();
		g.scale(sx, sy);
		g.drawImage(bimg, 0, 0, null);
		g.setTransform(saveAT);
		rotationsMittelpunktX = (int) (neuimg.getWidth() / 2);
		rotationsMittelpunktY = (int) (neuimg.getHeight() / 2);
		return neuimg;
	}

	// /**
	// * stellt das Bild auf BufferedCanvas dar.
	// */
	// public void zeigeAuf(BufferedCanvas c) {
	// grundlage = c;
	// // if (!bildaktuell) {
	// // c.setBufferedImage(aktuellesBild);
	// // bildaktuell = true;
	// // }
	// c.setBufferedImage(aktuellesBild);
	// bildaktuell = true;
	// c.repaint();
	// }
	/**
	 * Liefert ein BufferedImage des aktuell dargestellten Bildes. Dies kann
	 * sich von der Bildvorlage durch angewandte Transformationen unterscheiden
	 */
	public BufferedImage getRecentImage() {
		return aktuellesBild;
	}

	// /**
	// * stellt das Bild auf Leinwand dar.
	// */
	// public void zeigeAuf(Leinwand c) {
	// grundlage = (BufferedCanvas) c.getSwingComponent();
	// // if (!bildaktuell) {
	// // c.setBufferedImage(aktuellesBild);
	// // bildaktuell = true;
	// // }
	// grundlage.setBufferedImage(aktuellesBild);
	// bildaktuell = true;
	// grundlage.repaint();
	// }

	/**
	 * setzt die Skalierungsfaktoren
	 */
	public void scale(double faktorx, double faktory) {
		switch (cSpiegelung) {
		case 0:
			aktuellesBild = bildvorlage;
			break;
		case 1:
			aktuellesBild = flipBI(bildvorlage, true);
			break;
		case 2:
			aktuellesBild = flipBI(bildvorlage, false);
			break;
		case 3:
			aktuellesBild = flipBI(bildvorlage, false);
			aktuellesBild = flipBI(aktuellesBild, true);
		}
		sfaktorx *= faktorx;
		sfaktory *= faktory;
		aktuellesBild = this.scaleBI(aktuellesBild, sfaktorx, sfaktory);
		aktuellesBild = rotateBI(aktuellesBild, winkel, rotationsMittelpunktX,
				rotationsMittelpunktY);
		rotationsMittelpunktX = (int) (aktuellesBild.getWidth() / 2);
		rotationsMittelpunktY = (int) (aktuellesBild.getHeight() / 2);
	}

	/**
	 * spiegelt das aktuelle Bild an der horizontalen oder vertikalen Achse
	 */
	public void flip(boolean achseHorizontal) {
		switch (cSpiegelung) {
		case 0:
			if (achseHorizontal) {
				cSpiegelung = 1;
				aktuellesBild = flipBI(bildvorlage, true);
			} else {
				cSpiegelung = 2;
				aktuellesBild = flipBI(bildvorlage, false);
			}

			break;
		case 1:
			if (achseHorizontal) {
				cSpiegelung = 0;
				aktuellesBild = bildvorlage;
			} else {
				cSpiegelung = 3;
				aktuellesBild = flipBI(bildvorlage, false);
				aktuellesBild = flipBI(aktuellesBild, true);
			}
			break;
		case 2:
			if (achseHorizontal) {
				cSpiegelung = 3;
				aktuellesBild = flipBI(bildvorlage, false);
				aktuellesBild = flipBI(aktuellesBild, true);
			} else {
				cSpiegelung = 0;
				aktuellesBild = bildvorlage;
			}
			break;
		case 3:
			if (achseHorizontal) {
				cSpiegelung = 2;
				aktuellesBild = flipBI(bildvorlage, false);
			} else {
				cSpiegelung = 1;
				aktuellesBild = flipBI(bildvorlage, true);
			}
		}
		aktuellesBild = this.scaleBI(aktuellesBild, sfaktorx, sfaktory);
		aktuellesBild = rotateBI(aktuellesBild, winkel, rotationsMittelpunktX,
				rotationsMittelpunktY);
	}

	public void rotate(double a) {
		this.rotateTo(a + this.winkel);
	}

	public void rotateTo(double a) {
		if (winkel != a) {
			winkel = a;
			while (winkel > 360) {
				winkel -= 360;
			}
			while (winkel < 0) {
				winkel += 360;
			}
		}
		switch (cSpiegelung) {
		case 0:
			aktuellesBild = bildvorlage;
			break;
		case 1:
			aktuellesBild = flipBI(bildvorlage, true);
			break;
		case 2:
			aktuellesBild = flipBI(bildvorlage, false);
			break;
		case 3:
			aktuellesBild = flipBI(bildvorlage, false);
			aktuellesBild = flipBI(aktuellesBild, true);
		}
		if (winkel == 0) {
			aktuellesBild = scaleBI(aktuellesBild, sfaktorx, sfaktory);
		} else {
			aktuellesBild = scaleBI(aktuellesBild, sfaktorx, sfaktory);
			aktuellesBild = rotateBI(aktuellesBild, winkel,
					rotationsMittelpunktX, rotationsMittelpunktY);
		}

	}

	/**
	 * 
	 * @return liefert den Winkel, auf den das Bild gedreht ist
	 */
	public double getRotationAngle() {
		return winkel;
	}

	/**
	 * 
	 * @return liefert den Mittelpunkt der Rotation des Bildes
	 */
	public Point getCenter() {
		return new Point(rotationsMittelpunktX, rotationsMittelpunktY);
	}

	// private BufferedImage[] ladeBilder(String path, int pics) {
	//
	// BufferedImage[] anim = new BufferedImage[pics];
	// BufferedImage source = null;
	//
	// URL pic_url = getClass().getClassLoader().getResource(path);
	//
	// try {
	// source = ImageIO.read(pic_url);
	// } catch (IOException e) {
	// }
	//
	// for (int x = 0; x < pics; x++) {
	// anim[x] = source.getSubimage(x * source.getWidth() / pics, 0,
	// source.getWidth() / pics, source.getHeight());
	// }
	//
	// return anim;
	// }

	private BufferedImage loadImageFile2(String path) {
		BufferedImage source = null;
		URL pic_url = getClass().getResource(path);

		try {
			source = ImageIO.read(pic_url);
		} catch (IOException e) {
			System.out.println(e);
		}
		return source;
	}
	
	private BufferedImage loadImageFile(String path) {
		BufferedImage source =null,neu = null;
		File f=null;
		URL pic_url = getClass().getResource(path);
		// Mischmasch wegen Pfadirritationen bei Verwendung einer
		// Entwicklungsumgebung
//		System.out.println(pic_url);
		if (pic_url == null) {
			f = new File(path);
//			 System.out.println("picurl = null");
		}
		// System.out.println("File existiert:"+f.exists());

		try {
			if (f==null){
			 source = ImageIO.read(pic_url);
			 pfad2Bild=pic_url.getPath();
			 } else {
			source = ImageIO.read(f);
			
			pfad2Bild = f.getAbsolutePath();}
			neu = new BufferedImage(source.getWidth(),source.getHeight(),BufferedImage.TYPE_INT_ARGB);
			Graphics g = neu.getGraphics();
			g.drawImage(source,0,0,null);
		} catch (IOException e) {
			System.out.println("Fehler "+e);
		}
		
		// System.out.println("P "+pfad2Bild);
//		return source;
		return neu;
	}

	private BufferedImage cloneBufferedImage(BufferedImage source) {
		BufferedImage neuimg = new BufferedImage(
						(int) Math.round(source.getWidth()),
						(int) Math.round(source.getHeight()),
						BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) neuimg.getGraphics();
		g.drawImage(source, 0, 0, null);
		return neuimg;
	}

	/**
	 * liefert einen deep-Clone des Pictureobjektes
	 * */
	public Picture getClone() {
		Picture clone = new Picture(1, 1);
		// protected BufferedImage aktuellesBild;
		clone.aktuellesBild = this.cloneBufferedImage(aktuellesBild);
		// protected BufferedImage bildvorlage;
		clone.bildvorlage = this.cloneBufferedImage(bildvorlage);
		// private String pfad2Bild;
		clone.pfad2Bild = "" + this.pfad2Bild;
		// private int id = -1;// ermöglicht es eine Kennung zur Unterscheidung
		// von
		clone.id = this.id;
		// private double winkel = 0;// Winkel in dem das dargestellte
		// BufferedImage
		// gegenüber dem originalen gedreht ist
		clone.winkel = this.winkel;
		// private int rotationsMittelpunktX; // für das Image
		clone.rotationsMittelpunktX = this.rotationsMittelpunktX;
		// private int rotationsMittelpunktY; // für das Image
		clone.rotationsMittelpunktY = this.rotationsMittelpunktY;
		// private int cSpiegelung = 0; // 0: keine Spiegelung, 1: Spiegelung
		// nur an
		// der horizontalen Achse, 2: Spiegelung nur
		// an der vertikalen Achse, 3: horizontal und vertikal
		clone.cSpiegelung = this.cSpiegelung;
		// gibt den aktuellen Skalierungsfaktor in x bzw. y-Richtung an
		// private double sfaktorx = 1, sfaktory = 1;
		clone.sfaktorx = this.sfaktorx;
		clone.sfaktory = this.sfaktory;
		return clone;
	}

	/**
	 * 
	 * @return liefert die Breite des aktuellen Pixelbildes, falls es keines
	 *         gibt wird -1 zurückgeliefert
	 */
	public double getWidth() {
		return aktuellesBild != null ? aktuellesBild.getWidth() : -1;
	}

	/**
	 * 
	 * @return liefert die Höhe des aktuellen Pixelbildes, falls es keines gibt
	 *         wird -1 zurückgeliefert
	 */
	public double getHeight() {
		return aktuellesBild != null ? aktuellesBild.getHeight() : -1;

	}

	public Graphics getBufferedImageGraphics() {
		if (aktuellesBild == null)
			return null;
		return aktuellesBild.getGraphics();
	}

	public int getFlipInfo() {
		return cSpiegelung;
	}

	public void setFlip(int cs) {
		cSpiegelung = cs;
		switch (cSpiegelung) {
		case 0:
			aktuellesBild = bildvorlage;
			break;
		case 1:
			aktuellesBild = flipBI(bildvorlage, true);
			break;
		case 2:
			aktuellesBild = flipBI(bildvorlage, false);
			break;
		case 3:
			aktuellesBild = flipBI(bildvorlage, false);
			aktuellesBild = flipBI(aktuellesBild, true);
		}
		aktuellesBild = this.scaleBI(aktuellesBild, sfaktorx, sfaktory);
		aktuellesBild = rotateBI(aktuellesBild, winkel, rotationsMittelpunktX,
				rotationsMittelpunktY);
	}

	public void rotateToScaleToFlip(double a, double fx, double fy, int cs) {
		cSpiegelung = cs;
		switch (cSpiegelung) {
		case 0:
			aktuellesBild = bildvorlage;
			break;
		case 1:
			aktuellesBild = flipBI(bildvorlage, true);
			break;
		case 2:
			aktuellesBild = flipBI(bildvorlage, false);
			break;
		case 3:
			aktuellesBild = flipBI(bildvorlage, false);
			aktuellesBild = flipBI(aktuellesBild, true);
		}
		if (winkel != a) {
			winkel = a;
			while (winkel > 360) {
				winkel -= 360;
			}
			while (winkel < 0) {
				winkel += 360;
			}

		}
		sfaktorx = fx;
		sfaktory = fy;
		if (winkel == 0) {
			aktuellesBild = scaleBI(aktuellesBild, sfaktorx, sfaktory);
		} else {
			aktuellesBild = scaleBI(aktuellesBild, sfaktorx, sfaktory);
			aktuellesBild = rotateBI(aktuellesBild, winkel,
					rotationsMittelpunktX, rotationsMittelpunktY);
		}

	}

	public void setRotationAdjustment(boolean b) {
		this.rotationmitAnpassung = b;

	}

	/**
	 * das aktuelle Pixelbild ersetzt die Bildvorlage alle
	 * Transformations-Parameter werden zurückgesetzt
	 */
	public void setRecentImageToBasicImage() {
		bildvorlage = aktuellesBild;
		sfaktorx = 1;
		sfaktory = 1;
		winkel = 0;
		cSpiegelung = Picture.NOFLIP;
	}

	/**
	 * 
	 * @param img
	 * @param out
	 * @param quality
	 * @throws IOException
	 */
	private static void writeImage(BufferedImage img, File out)
			throws IOException {
		ImageWriter writer = ImageIO.getImageWritersByFormatName("jpg").next();
		ImageOutputStream ios = ImageIO.createImageOutputStream(out);
		writer.setOutput(ios);
		ImageWriteParam iwparam = new JPEGImageWriteParam(Locale.getDefault());
		iwparam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		iwparam.setCompressionQuality(1f);
		writer.write(null, new IIOImage(img, null, null), iwparam);
		ios.flush();
		writer.dispose();
		ios.close();
	}

	/**
	 * speichert das aktuelle Pixelbild als png-Grafik eine fehlende Dateiendung
	 * wird automatisch ergänzt, nach erfolgreicher Operation wird true, sonst
	 * false zurück gegeben
	 */
	public boolean saveRecentAsPNG(String dateiname) {
		if (aktuellesBild == null) {
			return false;
		}
		if (!dateiname.toLowerCase().endsWith(".png")) {
			dateiname = dateiname + ".png";
		}
		String p = "";
		try {
			File out = new File(dateiname);
			ImageIO.write(aktuellesBild, "png", new File(dateiname));
			p = out.getAbsolutePath();
		} catch (Exception e) {
			return false;
		}
		if (!p.equals("")) {
			path2SavedImage = p;
		}
		return true;

	}

	/**
	 * speichert das aktuelle Pixelbild als gif-Grafik eine fehlende Dateiendung
	 * wird automatisch ergänzt, nach erfolgreicher Operation wird true, sonst
	 * false zurück gegeben
	 */
	public boolean saveRecentAsGIF(String dateiname) {
		if (aktuellesBild == null) {
			return false;
		}
		if (!dateiname.toLowerCase().endsWith(".gif")) {
			dateiname = dateiname + ".gif";
		}
		String p = "";
		try {
			File out = new File(dateiname);
			ImageIO.write(aktuellesBild, "gif", new File(dateiname));
			p = out.getAbsolutePath();
		} catch (Exception e) {
			return false;
		}
		if (!p.equals("")) {
			path2SavedImage = p;
		}
		return true;
	}

	/**
	 * speichert das aktuelle Pixelbild als bmp-Grafik eine fehlende Dateiendung
	 * wird automatisch ergänzt, nach erfolgreicher Operation wird true, sonst
	 * false zurück gegeben
	 */
	public boolean saveRecentAsBMP(String dateiname) {
		if (aktuellesBild == null) {
			return false;
		}
		if (!dateiname.toLowerCase().endsWith(".bmp")) {
			dateiname = dateiname + ".bmp";
		}
		String p = "";
		try {
			File out = new File(dateiname);
			ImageIO.write(aktuellesBild, "bmp", new File(dateiname));
			p = out.getAbsolutePath();
		} catch (Exception e) {
			return false;
		}
		if (!p.equals("")) {
			path2SavedImage = p;
		}
		return true;
	}

	/**
	 * speichert das aktuelle Pixelbild als jpg-Grafik hoher Qualität. Eine
	 * fehlende Dateiendung wird automatisch ergänzt, nach erfolgreicher
	 * Operation wird true, sonst false zurück gegeben
	 */
	public boolean saveRecentAsJPG(String dateiname) {
		if (aktuellesBild == null) {
			return false;
		}
		if (!dateiname.toLowerCase().endsWith(".jpg")) {
			dateiname = dateiname + ".jpg";
		}
		String p = "";
		File out = new File(dateiname);
		p = out.getAbsolutePath();
		try {
			writeImage(aktuellesBild, out);

		} catch (IOException e1) {
			e1.printStackTrace();
			return false;
		}
		if (!p.equals("")) {
			path2SavedImage = p;
		}
		return true;
	}

	public boolean saveRecent(String dateiname) {

		if (!(dateiname.toLowerCase().endsWith("gif")
				|| dateiname.toLowerCase().endsWith("jpg") || dateiname
				.toLowerCase().endsWith("png"))) {
			dateiname = dateiname + "jpg";
		}
		if (dateiname.toLowerCase().endsWith("gif")) {
			if (!saveRecentAsGIF(dateiname)) {
				return false;
			}
		} else if (dateiname.toLowerCase().endsWith("jpg")) {
			if (!saveRecentAsJPG(dateiname)) {
				return false;
			}
		} else if (dateiname.toLowerCase().endsWith("png")) {
			if (!saveRecentAsPNG(dateiname)) {
				return false;
			}
		} else if (dateiname.toLowerCase().endsWith("bmp")) {
			if (!saveRecentAsBMP(dateiname)) {
				return false;
			}
		}
		return true;
	}

	public boolean saveRecent(String dateiname, String typ) {
		typ = typ.toLowerCase();
		if (aktuellesBild == null) {
			return false;
		}
		if (!dateiname.toLowerCase().endsWith(typ)) {
			dateiname = dateiname + "." + typ;
		}
		return this.saveRecent(dateiname);
	}

	public boolean resave() {
		if (!path2SavedImage.equals("")) {
			return this.saveRecent(path2SavedImage);
		}
		if (!pfad2Bild.equals("")) {
			return this.saveRecent(pfad2Bild);
		}
		return false;
	}

	/**
	 * Ändert die Bildvorlage
	 * 
	 * @param bi
	 */
	public void setBasicImage(BufferedImage bi) {
		path2SavedImage = "";
		pfad2Bild = "";
		sfaktorx = 1;
		sfaktory = 1;
		bildvorlage = bi;
		aktuellesBild = bildvorlage;
		rotationsMittelpunktX = (int) Math.round(aktuellesBild.getWidth() / 2.);
		rotationsMittelpunktY = (int) Math
				.round(aktuellesBild.getHeight() / 2.);
	}
	public int iResMul = 4; // 1 = 72 dpi; 4 = 288 dpi
//	private double linksx;
//	private double rechtsx;
//	private double obeny;
//	private double unteny;
//	private boolean eigenesKoosys = false;

	public int print(Graphics g, PageFormat pf, int iPage)
			throws PrinterException {
		BufferedImage img = this.aktuellesBild;
		// final int FONTSIZE = 12;
		// final double PNT_MM = 25.4 / 72.;
		if (0 != iPage)
			return java.awt.print.Printable.NO_SUCH_PAGE;
		try {
			// int iPosX = 1;
			// int iPosY = 1;
			// int iWdth = (int) Math.round(pf.getImageableWidth() * iResMul) -
			// 3;
			// int iHght = (int) Math.round(pf.getImageableHeight() * iResMul) -
			// 3;
			// int iCrcl = Math.min(iWdth, iHght) - 4 * iResMul;
			Graphics2D g2 = (Graphics2D) g;
			// PrinterJob prjob = ((PrinterGraphics) g2).getPrinterJob();
			// g2.translate( pf.getImageableX(), pf.getImageableY() );
			// g2.scale( 1.0 / iResMul, 1.0 / iResMul );
			double x = pf.getImageableX();
			double y = pf.getImageableY();
			double w = pf.getImageableWidth();
			double h = pf.getImageableHeight();

			double sx = w / img.getWidth();
			double sy = h / img.getHeight();

			g2.translate(x, y);
			if (sx < sy) {
				sy = sx;
			} else {
				sx = sy;
			}
			g2.scale(sx, sy);
			g2.drawImage(img, 0, 0, null);

		} catch (Exception ex) {
			throw new PrinterException(ex.getMessage());
		}
		return java.awt.print.Printable.PAGE_EXISTS;
	}

	// private static double dbldgt(double d) {
	// return Math.round(d * 10.) / 10.; // show one digit after point
	// }

	public void print() {

		final String sCrLf = System.getProperty("line.separator");
		// final String sPrintFile = "PrintFile.ps";
		final String sErrNoPrintService = sCrLf
				+ "Es ist kein passender Print-Service installiert.";

		// Set DocFlavor and print attributes:
		DocFlavor flavor = DocFlavor.SERVICE_FORMATTED.PRINTABLE;
		PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
		aset.add(MediaSizeName.ISO_A4);

		try {

			// Print to PrintService (e.g. to Printer):
			PrintService prservDflt = PrintServiceLookup
					.lookupDefaultPrintService();
			PrintService[] prservices = PrintServiceLookup.lookupPrintServices(
					flavor, aset);
			if (null == prservices || 0 >= prservices.length)
				if (null != prservDflt) {
					// System.err.println( "Nur Default-Printer, da
					// lookupPrintServices fehlgeschlagen." );
					prservices = new PrintService[] { prservDflt };
				} else {
					System.err.println(sErrNoPrintService);
					return;
				}
			/*
			 * System.out.println( "Print-Services:" ); for( int i=0;
			 * i<prservices.length; i++ ) System.out.println( " " + i + ": " +
			 * prservices[i] + (( prservDflt != prservices[i] ) ? "" :
			 * " (Default)") );
			 */
			PrintService prserv = null;
			if (!Arrays.asList(prservices).contains(prservDflt))
				prservDflt = null;
			prserv = ServiceUI.printDialog(null, 50, 50, prservices,
					prservDflt, null, aset);
			if (null != prserv) {
				/*
				 * System.out.println( "Ausgewaehlter Print-Service:" );
				 * System.out.println( " " + prserv );
				 */
				// printPrintServiceAttributesAndDocFlavors( prserv );
				DocPrintJob pj = prserv.createPrintJob();
				Doc doc = new SimpleDoc(this, flavor, null);
				pj.print(doc, aset);
				// System.out.println( "Grafik ist erfolgreich gedruckt." );

			}
		} catch (PrintException pe) {
			System.err.println(pe);
		}
	}
	
	
	

}
