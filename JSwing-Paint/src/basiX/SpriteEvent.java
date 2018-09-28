package basiX;

import java.util.EventObject;

public class SpriteEvent extends EventObject {
	public final static int EVNeuePosition = 1000;
	public final static int EVNeuerWinkel = 1001;
	public final static int EVNeueGroesse = 1002;
	private double xalt, yalt, xneu, yneu, winkelalt, winkelneu;
	private double breitealt, hoehealt, breiteneu, hoeheneu;

	private int evID;

	public SpriteEvent(Object source, int evID, double alt, double neu) {
		super(source);
		this.evID = evID;
		if (evID == EVNeuerWinkel) {
			this.winkelalt = alt;
			this.winkelneu = neu;
		}
	}

	public SpriteEvent(Object source, int evID, double alt1, double alt2,
			double neu1, double neu2) {
		super(source);
		this.evID = evID;
		if (evID == EVNeuePosition) {
			this.xalt = alt1;
			this.yalt = alt2;
			this.xneu = neu1;
			this.yneu = neu2;
		} else if (evID == EVNeueGroesse) {
			this.breitealt = alt1;
			this.hoehealt = alt2;
			this.breiteneu = neu1;
			this.hoeheneu = neu2;
		}
	}

	public double getXalt() {
		return xalt;
	}

	public double getYalt() {
		return yalt;
	}

	public double getXneu() {
		return xneu;
	}

	public double getYneu() {
		return yneu;
	}

	public double getWinkelalt() {
		return winkelalt;
	}

	public double getWinkelneu() {
		return winkelneu;
	}

	public double getBreitealt() {
		return breitealt;
	}

	public double getHoehealt() {
		return hoehealt;
	}

	public double getBreiteneu() {
		return breiteneu;
	}

	public double getHoeheneu() {
		return hoeheneu;
	}

	public int getEvID() {
		return evID;
	}

	public boolean isPositionNeu() {
		return evID == EVNeuePosition;
	}

	public boolean isWinkelNeu() {
		return evID == EVNeuerWinkel;
	}

	public boolean isGroesseNeu() {
		return evID == EVNeueGroesse;
	}

}
