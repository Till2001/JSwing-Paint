package basiX.swing;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.RescaleOp;
import java.util.Vector;

public class PicturePane extends BufferPane {
private Vector<PictAnim> bilder = new Vector<PictAnim>();
	public PicturePane(int typ, int zorder, int id) {
		super(typ, zorder, id);
		// TODO Auto-generated constructor stub
	}

	public PicturePane(int typ, int zorder, int id, BufferedImage daten) {
		super(typ, zorder, id, daten);
		// TODO Auto-generated constructor stub
	}

	public void addPicture(PictAnim np) {
		if (!bilder.contains(np)) {
			bilder.add(np);
		}
		this.fireNewPicture(this,np);
	}

	private void fireNewPicture(PicturePane picturePane, PictAnim np) {
		// TODO Auto-generated method stub
		
	}

	public void removePicture(PictAnim np) {
		bilder.remove(np);
		this.firePictureRemoved(this,np);
	}
	
	private void firePictureRemoved(PicturePane picturePane, PictAnim np) {
		// TODO Auto-generated method stub
		
	}
	
//	private float[] scales = { 1f, 1f, 1f, 0.5f };
//	private float[] offsets = new float[4];
//	private RescaleOp rop;
//
//	private void setOpacity(float opacity) {
//		scales[3] = opacity;
//		rop = new RescaleOp(scales, offsets, null);
//	}
	private boolean clearBeforeShow = true;
	@Override
	public void prepareImage(int width, int height, ImageObserver obs) {
//		System.out.println("Bilder aufbereiten");
		if(clearBeforeShow) {super.clear();}
		super.prepareImage(width, height, obs);
		try {
			Graphics2D g2d = (Graphics2D) this.getBufferedImage().getGraphics();
			for (PictAnim p : bilder) {
				p.draw(g2d, obs);
//				if (p.getTransparency() != 1f) {
//					this.setOpacity(p.getTransparency());
//					g2d.drawImage(p.getRecentImage(), rop, p.getX(), p
//							.getY());
//				} else {
//					g2d.drawImage(p.getRecentImage(), p.getX(), p.getY(),
//							obs);
//				}
			}
		} catch (java.util.ConcurrentModificationException ex) {
			// wird hingenommen
		}
	}

	public boolean isClearBeforeShow() {
		return clearBeforeShow;
	}

	public void setClearBeforeShow(boolean clearBeforeShow) {
		this.clearBeforeShow = clearBeforeShow;
	}
}
