package basiX;

public class MausKlick {
	private int x;
	private int y;
	private boolean links;
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public boolean isLinks() {
		return links;
	}
	public MausKlick(int x, int y, boolean b) {
		this.x=x;this.y=y;this.links = b;
	}

}
