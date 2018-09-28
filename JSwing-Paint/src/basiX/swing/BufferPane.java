package basiX.swing;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.Vector;



public class BufferPane implements PenPane{
	public static final int STATISCH = 1;
	public static final int PENEBENE = 2;
	public static final int ANIMEBENE = 3;
	private int zOrder, typ, id;
	private BufferedImage bufferedImage;
	private Color fillColor = new Color (255,255,255,0);
	private boolean hgGeaendert=false;
	private Vector<PaneListener> paneListeners = new Vector<PaneListener>();

	public BufferPane(int typ, int zorder, int id) {
		this(typ, zorder, id, null);
	}

	public BufferPane(int typ, int zorder, int id, BufferedImage daten) {
		super();
		this.zOrder = zorder;
		this.typ = typ;
		this.id = id;
		this.bufferedImage = daten;
	}

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}

	
	public Color getFillColor() {
		return fillColor;
	}


	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
//		this.clear();
//		hgGeaendert=true;
		this.fireFillColorChanged();		
	}


	public void clear(){
//		System.out.println("clear");
		if (bufferedImage!=null){
			Graphics2D g=(Graphics2D)bufferedImage.getGraphics();
			g.setComposite(AlphaComposite.Src);
			g.setColor(fillColor);
			g.fillRect(0,0, bufferedImage.getWidth(), bufferedImage.getHeight());			
		}
		this.fireFillColorChanged();
	}

	public int getzOrder() {
		return zOrder;
	}

	public void setzOrder(int zOrder) {
		this.zOrder = zOrder;
	}

	public int getTyp() {
		return typ;
	}

	public void setTyp(int typ) {
		this.typ = typ;
	}

	public BufferedImage getBufferedImage() {
		return bufferedImage;
	}

	public void setBufferedImage(BufferedImage daten) {
		this.bufferedImage = daten;
		this.fireImageChanged();
	}
	
	
	
	public Graphics getBufferedImageGraphics(){
		return bufferedImage!=null?bufferedImage.getGraphics():null;
	}
	
	public void prepareImage(int width, int height, ImageObserver obs){
		if (bufferedImage != null){
			
			if (bufferedImage.getWidth() < width
					|| bufferedImage.getHeight() < height) {
				BufferedImage neu = new BufferedImage(Math.max(bufferedImage
						.getWidth(),width), Math.max(bufferedImage
						.getHeight(),height),
						BufferedImage.TYPE_INT_ARGB);
				Graphics2D g2d = (Graphics2D) neu.getGraphics();
				if (hgGeaendert) {
					g2d.setComposite(AlphaComposite.Src);
					g2d.setColor(fillColor);
					g2d.fillRect(0, 0, neu.getWidth(), neu.getHeight());
					hgGeaendert = false;
				}
				g2d.drawImage(bufferedImage, 0, 0, obs);
				bufferedImage = neu;
				}
//			else {					
//					if (hgGeaendert) {
//						Graphics2D g2d = (Graphics2D) bufferedImage.getGraphics();
//						g2d.setComposite(AlphaComposite.Src);
//						g2d.setColor(fillColor);
//						g2d.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
//						hgGeaendert = false;
//					}
//				}
		} else {			
			bufferedImage = new BufferedImage(width,height,
					BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2d = (Graphics2D) bufferedImage.getGraphics();
			g2d.setComposite(AlphaComposite.Src);
			g2d.setColor(fillColor);
			g2d.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
			hgGeaendert = false;
		}
	}
	
	
	/**  */
	protected void fireFillColorChanged() {		
			for (PaneListener listeners : paneListeners) {
				listeners.paneChanged(this);
			}		
	}
	
	protected void fireImageChanged() {
		for (PaneListener listeners : paneListeners) {
			listeners.paneChanged(this);
		}	
	}


	/** registriert einen PaneListener */
	public synchronized void addPaneListener(
			PaneListener l) {
		if (!paneListeners.contains(l)) {
			paneListeners.addElement(l);
		}
	}

	/** entfernt einen PaneListener */
	public synchronized void removePaneCanvasListener(
			PaneListener l) {
		if (paneListeners != null && paneListeners.contains(l)) {			
			paneListeners.removeElement(l); 
		}
	}

	
	
	
}
