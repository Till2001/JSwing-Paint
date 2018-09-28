package basiX;

public class MausBewegung {
	private int x,y;
	private boolean herein;
	private boolean heraus;
	private boolean gezogen;
	public MausBewegung(int x, int y,boolean herein,boolean heraus,boolean gezogen){
		this.x=x;this.y=y;this.herein=herein;this.heraus=heraus;this.gezogen=gezogen;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public boolean isHerein() {
		return herein;
	}
	public boolean isHeraus() {
		return heraus;
	}
	public boolean isGezogen() {
		return gezogen;
	}
	

}
