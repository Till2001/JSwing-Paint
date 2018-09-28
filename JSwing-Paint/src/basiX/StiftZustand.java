package basiX;

import java.awt.Color;
import java.awt.Font;

public class StiftZustand {
	private String aktuellfont;
	private int schriftstil;
	private int schriftgroesse;
	private Font schriftart;
	private Color farbe;
	private int linienbreite;
	private double stiftx;
	private double stifty;
	private int zmuster;
	private boolean hoch;
	private double winkel;
	private int schreibmodus;	
	
	
	public StiftZustand(String aktuellfont, int schriftstil,
			int schriftgroesse, Font schriftart, Color farbe, int linienbreite,
			double stiftx, double stifty, int zmuster, boolean hoch,
			double winkel, int schreibmodus) {
		this.aktuellfont = new String(aktuellfont);
		this.schriftstil = schriftstil;
		this.schriftgroesse = schriftgroesse;
		this.schriftart = schriftart;
		this.farbe = new Color(farbe.getRed(),farbe.getGreen(),farbe.getBlue(),farbe.getAlpha());
		this.linienbreite = linienbreite;
		this.stiftx = stiftx;
		this.stifty = stifty;
		this.zmuster = zmuster;
		this.hoch = hoch;
		this.winkel = winkel;
		this.schreibmodus = schreibmodus;
	}
	
	public String getAktuellfont() {
		return aktuellfont;
	}
	public void setAktuellfont(String aktuellfont) {
		this.aktuellfont = aktuellfont;
	}
	public int getSchriftstil() {
		return schriftstil;
	}
	public void setSchriftstil(int schriftstil) {
		this.schriftstil = schriftstil;
	}
	public int getSchriftgroesse() {
		return schriftgroesse;
	}
	public void setSchriftgroesse(int schriftgroesse) {
		this.schriftgroesse = schriftgroesse;
	}
	public Font getSchriftart() {
		return schriftart;
	}
	public void setSchriftart(Font schriftart) {
		this.schriftart = schriftart;
	}
	public Color getFarbe() {
		return farbe;
	}
	public void setFarbe(Color farbe) {
		this.farbe = farbe;
	}
	public int getLinienbreite() {
		return linienbreite;
	}
	public void setLinienbreite(int linienbreite) {
		this.linienbreite = linienbreite;
	}
	public double getStiftx() {
		return stiftx;
	}
	public void setStiftx(double stiftx) {
		this.stiftx = stiftx;
	}
	public double getStifty() {
		return stifty;
	}
	public void setStifty(double stifty) {
		this.stifty = stifty;
	}
	public int getZmuster() {
		return zmuster;
	}
	public void setZmuster(int zmuster) {
		this.zmuster = zmuster;
	}
	public boolean isHoch() {
		return hoch;
	}
	public void setHoch(boolean hoch) {
		this.hoch = hoch;
	}
	public double getWinkel() {
		return winkel;
	}
	public void setWinkel(double winkel) {
		this.winkel = winkel;
	}
	public int getSchreibmodus() {
		return schreibmodus;
	}
	public void setSchreibmodus(int schreibmodus) {
		this.schreibmodus = schreibmodus;
	}
	
}
