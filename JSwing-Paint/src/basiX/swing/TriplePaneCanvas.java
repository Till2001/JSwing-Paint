package basiX.swing;

import java.awt.*;
import java.io.*;
import java.awt.image.BufferedImage;

/**
 * Mai 2011 Ein TriplePaneCanvas ist eine von PaneCanvas abgeleitete Komponente mit drei
 * Ebenen auf der Bilder oder Zeichnungen dargestellt werden können. 
 * Die untere Ebene dient zur Darstellung eines Hintergrundes
 * Auf den beiden darüber liegenden Ebenen können Bilder (PictAnim-Objekte) dargetsellt 
 * werden und Stiftzeichnungen erfolgen. 
 * 
 */
public class TriplePaneCanvas extends PaneCanvas implements Serializable {

	private PicturePane obereEbene;
	private PicturePane mittlereEbene;

	/**
	 * erzeugt einen TriplePaneCanvas mit 10 Pixel Breite und Höhe 
	 */
	public TriplePaneCanvas() {
		this(10, 10);
	}

	/**
	 * erzeugt einen TriplePaneCanvas mit den angegebenen Dimensionen.
	 * 
	 */
	public TriplePaneCanvas(double b, double h) {
		super(b, h);
		this.setLayout(null);
		this.setFocusable(true);
		this.setOpaque(false);
		try {
			mittlereEbene = new PicturePane(BufferPane.ANIMEBENE, 1, 2);
			this.addPane(mittlereEbene);
			obereEbene = new PicturePane(BufferPane.ANIMEBENE, 2, 3);
			this.addPane(obereEbene);
			this.setPreferredSize(new Dimension((int) b, (int) h));
			this.repaint();
			this.sync();
		} catch (Exception e) {
			System.out
					.println(" TriplePaneCanvas konnte nicht erstellt werden");
		}
	}

	public PicturePane getTopPane() {
		return obereEbene;
	}

	public PicturePane getMiddlePane() {
		return mittlereEbene;
	}

	public Graphics getTopPaneGraphics() {
		return obereEbene.getBufferedImage().getGraphics();

	}
	public Graphics getMiddlePaneGraphics() {
		return mittlereEbene.getBufferedImage().getGraphics();

	}
	public void addPictureToMiddlePane(PictAnim np) {
		mittlereEbene.addPicture(np);
	}

	public void removePictureFromMiddlePane(PictAnim np) {
		mittlereEbene.removePicture(np);
	}

	public void addPictureToTopPane(PictAnim np) {
		obereEbene.addPicture(np);
	}

	public void removePictureFromTopPane(PictAnim np) {
		obereEbene.removePicture(np);
	}
	
//	public BufferPane getPenPane(int nr) {
//		int i = 0;
//		for (BufferPane bp : getPanes()) {
//			if (bp.getTyp() == BufferPane.PENEBENE) {
//				i++;
//				if (i == nr) {
//					return bp;
//				}
//			}
//		}
//		return null;
//	}

	public void clearTopPane() {
		this.obereEbene.clear();
	}
	
	
}
