package basiX.swing;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import basiX.Farbe;

/**
 * Mai 2011 Ein PaneCanvas ist eine von JPanel abgeleitete Komponente auf der
 * Bilder oder Zeichnungen dargestellt werden können. Ein PaneCanvas kann
 * beliebig viele Ebenen (BufferPane) besitzen, auf die separat gezeichnet oder
 * auf denen Bilder abgelegt werden können. Die Ebenen sind gemäß ihrer
 * Z-Ordnung übereinander gelegt. Um flimmerfreie Animationen zu unterstützen,
 * kann ein PaneCanvas so eingestellt werden (immediateMode auf false setzen,
 * true ist die Standardeinstellung), dass Änderungen auf den Ebenen erst durch
 * einen entsprechenden Auftrag sichtbar werden(flipVisible). Nach der Erzeugung
 * enthält ein PaneCanvas genau eine Ebene. Wenn weitere Ebenen hinzugefügt
 * werden, bleibt diese Ebene als unterste Ebene bestehen. Der BufferedCanvas
 * unterstützt * die Darstellung von Bildern mit Transparenz und kann auch
 * selbst mit * unterschiedlichem Transparenzgrad über eine andere Komponenten
 * gelegt werden. Zur Ereignisbearbeitung: Ob der BufferedCanvas durch
 * Überdecken mit einem Fenster, Größenveränderung oder ähnliches vom System neu
 * gezeichnet wurde, kann einem angemeldeten PaneCanvasListener signalisiert
 * werden.
 * 
 * @see addBufferedCanvasListener(DoubleBufferedCanvasListener l)
 * @see removeBufferedCanvasListener(DoubleBufferedCanvasListener l)
 * @see DoubleBufferedCanvasListener
 */
public class PaneCanvas extends JPanel implements Serializable, PaneListener {
	protected boolean nochnichtsichtbargewesen = true;
	protected boolean leinwandneugezeichnet;

	private Color zhintergrundfarbe = Color.WHITE;
	private float alphawert = 1.0f;

	protected Toolkit toolkit;
	private boolean syncAktiv = false;
	private BufferedImage visibleImg;
	private BufferPane basisEbene;
	private Vector<BufferPane> ebenen = new Vector<BufferPane>();
	private boolean immediateMode = true;
	private Vector<PaneCanvasListener> paneCanvaslistener = new Vector<PaneCanvasListener>();

	public Vector<BufferPane> getPanes() {
		return ebenen;
	}

	public boolean isImmediateMode() {
		return immediateMode;
	}

	public void setImmediateMode(boolean immediateMode) {
		this.immediateMode = immediateMode;
		if (immediateMode) {
			this.repaint();
		}
	}

//	@Override
//	public void setSize(int width, int height){
//		super.setSize(width, height);
//		boolean merke = immediateMode;
//		immediateMode = true;
//		this.repaint();
//		immediateMode = merke;		
//	}
//	@Override
//	public void setSize(Dimension d){
//		this.setSize(d.width,d.height);
//	}
	
	public BufferPane createBufferPane(int typ, int zOrder, int id) {
		return this.createBufferPane(typ, zOrder, id, null);
	}

	public BufferPane createBufferPane(int typ, int zOrder, int id, BufferedImage im) {
		BufferPane neuE = new BufferPane(typ, zOrder, id);
		neuE.setBufferedImage(im);
		int i=0;
		for (i = 0; i < ebenen.size(); i++) {
			if (zOrder < ebenen.elementAt(i).getzOrder()) {
				break;
			}
		}
		ebenen.add(i, neuE);
		neuE.addPaneListener(this);
		return neuE;
	}
	public BufferPane createPicturePane(int typ, int zOrder, int id) {
		return this.createPicturePane(typ, zOrder, id, null);
	}

	public BufferPane createPicturePane(int typ, int zOrder, int id, BufferedImage im) {
		PicturePane neuE = new PicturePane(typ, zOrder, id);
		neuE.setBufferedImage(im);
		int i=0;
		for (i = 0; i < ebenen.size(); i++) {
			if (zOrder < ebenen.elementAt(i).getzOrder()) {
				break;
			}
		}
		ebenen.add(i, neuE);
		neuE.addPaneListener(this);
		return neuE;
	}
	
	public void addPane(BufferPane eb) {
		int i=0;
		for (i = 0; i < ebenen.size(); i++) {
			if (eb.getzOrder() < ebenen.elementAt(i).getzOrder()) {
				break;
			}
		}
		// System.out.println("ins" + i);
		ebenen.add(i, eb);
		eb.addPaneListener(this);
	}

	/**
	 * erzeugt einen Canvas mit 10 Pixel Breite und Höhe und der Unterstützung
	 * von Transparenz
	 */
	public PaneCanvas() {
		this(10, 10);
	}

	/**
	 * erzeugt einen Canvas mit den angegebenen Dimensionen und einem
	 * BufferedImage welches Transparenz unterstützt.
	 * 
	 */
	public PaneCanvas(double b, double h) {
		super();
		this.setLayout(null);
		this.setFocusable(true);
		this.setOpaque(false);
		try {
			visibleImg = new BufferedImage((int) b, (int) h,
					BufferedImage.TYPE_INT_ARGB);
			basisEbene = new BufferPane(BufferPane.STATISCH, 0, 1, visibleImg);
			this.addPane(basisEbene);
			toolkit = Toolkit.getDefaultToolkit();			
			this.setPreferredSize(new Dimension((int) b, (int) h));
			basisEbene.addPaneListener(this);
			this.repaint();
			this.sync();
		} catch (Exception e) {
			System.out.println(" PaneCanvas konnte nicht erstellt werden");
		}
	}

	public void setAlpha(float a) {
		alphawert = a;
		this.repaint();
	}

	public float getAlpha() {
		return alphawert;
	}

	/** */
	@Override
	public synchronized void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (this.immediateMode) {
			this.visibleImg = this.prepareVisibleImage();
		} else {
			BufferedImage bufferedImage;
			int breite = Math.max(this.getSize().width, visibleImg.getWidth());
			int hoehe = Math.max(this.getSize().height, visibleImg.getHeight());
			if (this.getSize().width > visibleImg.getWidth()
					|| this.getSize().height > visibleImg.getHeight()) {

				bufferedImage = new BufferedImage(breite, hoehe,
						BufferedImage.TYPE_INT_ARGB);
				Graphics2D gh = (Graphics2D) bufferedImage.getGraphics();
				gh.setComposite(AlphaComposite.Src);
				gh.setColor(zhintergrundfarbe);
				gh.fillRect(0, 0, breite, hoehe);
				gh.drawImage(visibleImg, 0, 0, this);
				visibleImg = bufferedImage;
			}
		}
		Composite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				alphawert);
		((Graphics2D) g).setComposite(alpha);
		g.drawImage(visibleImg, 0, 0, this);
		if (syncAktiv) {
			this.sync();
		}// 
		this.fireCanvasGezeichnet();
		
	}

	/** setzt die Hintergrundfarbe für den PaneCanvas */
	public void setBackground(Color c) {
		if (this.basisEbene != null) {
			this.basisEbene.setFillColor(c);
			this.basisEbene.clear();
		}
		zhintergrundfarbe = c;
	}

	/**  */
	protected void fireCanvasGezeichnet() {

		for (PaneCanvasListener listeners : paneCanvaslistener) {
			listeners.paneCanvasRepainted(this);
		}
	}

	/** registriert einen Listener */
	public synchronized void addPaneCanvasListener(PaneCanvasListener l) {

		if (!paneCanvaslistener.contains(l)) {
			paneCanvaslistener.addElement(l);
		}
	}

	/** entfernt einen Listener */
	public synchronized void removePaneCanvasCanvasListener(
			BufferedCanvasListener l) {
		if (paneCanvaslistener != null && paneCanvaslistener.contains(l)) {
			paneCanvaslistener.removeElement(l);
		}
	}

	/**  */
	public void sync() {
		if (toolkit == null) {
			toolkit = Toolkit.getDefaultToolkit();
		}
		toolkit.sync();
	}

	public BufferedImage getBufferedImage() {
		return visibleImg;
	}

	public BufferedImage getBackgroundImage() {
		return basisEbene.getBufferedImage();
	}

	public Graphics getBufferedImageGraphics() {
		return basisEbene.getBufferedImage().getGraphics();
	}

	public boolean setImageIcon(String pfad) {
		try {
			ImageIcon ii = new ImageIcon(getClass().getResource(pfad));
			this.setImageIcon(ii);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public void setImageIcon(ImageIcon ii) {
		try {
			int breite = ii.getIconWidth();
			int hoehe = ii.getIconHeight();
			BufferedImage bi = new BufferedImage(breite, hoehe,
					BufferedImage.TYPE_INT_ARGB);
			Graphics2D graph = (Graphics2D) bi.getGraphics();
			graph.drawImage(ii.getImage(), 0, 0, this);
			basisEbene.setBufferedImage(bi);
		} catch (Exception e) {
			System.out.println(e + " Laden des Bildes fehlgeschlagen");
		}
	}

	public void setBufferedImage(BufferedImage bimg) {
		basisEbene.setBufferedImage(bimg);
		
	}

	public void flipVisible() {
		this.visibleImg = this.prepareVisibleImage();
		this.repaint();
		// this.sync();
	}

	public BufferedImage prepareVisibleImage() {
		// Voraussetzung Ebenen nach ZOrder geordnet
		// Größenanpassung aller Ebenen
		BufferedImage bgImage = new BufferedImage(this.getWidth(), this
				.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) bgImage.getGraphics();
		
		try {
			for (BufferPane e : ebenen) {
//				System.out.println(e);
				e.prepareImage(this.getWidth(), this.getHeight(), this);
				g.drawImage(e.getBufferedImage(), 0, 0, null);
			}
		} catch (Exception e) {

		}
		return bgImage;
		
	}

	public Picture getCrop(int x, int y, int breite, int hoehe) {
		if (x < 0) {
			x = 0;
		}
		if (y < 0) {
			y = 0;
		}
		if (x > visibleImg.getWidth() || y > visibleImg.getHeight()) {
			BufferedImage crop = new BufferedImage(1, 1,
					BufferedImage.TYPE_INT_ARGB);
			Picture cropPicture = new Picture(crop);
			return cropPicture;
		}
		if (breite + x >= visibleImg.getWidth()) {
			breite = visibleImg.getWidth() - x;
		}
		if (hoehe + y >= visibleImg.getHeight()) {
			hoehe = visibleImg.getHeight() - y;
		}
		BufferedImage crop = new BufferedImage(breite, hoehe,
				BufferedImage.TYPE_INT_ARGB);
		crop.getGraphics().drawImage(
				visibleImg.getSubimage(x, y, breite, hoehe), 0, 0, null);
		Picture cropPicture = new Picture(crop);
		return cropPicture;
	}

	
	public void paneChanged(BufferPane l) {
		if (this.immediateMode) {
			// System.out.println(l+" changed");
			this.repaint();
		}
	}

	public Graphics getTopPaneGraphics() {
		return basisEbene.getBufferedImageGraphics();
	}

	public BufferPane getBackPane() {
		return this.basisEbene;
	}

	public void clearAllPanes() {
		boolean merke = immediateMode;
		if (merke) {
			immediateMode = false;
		}
		for (BufferPane e : ebenen) {
			e.clear();
		}
		immediateMode = merke;
		if (this.immediateMode) {
			// System.out.println(l+" changed");
			this.repaint();
		}
	}

}
