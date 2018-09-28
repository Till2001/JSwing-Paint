package jsp;

import basiX.Fenster;
import basiX.Maus;
import basiX.MausLauscherStandard;
import basiX.Stift;

public class Paint implements MausLauscherStandard {
	private Fenster f;
	private Maus m;
	private Stift s;
	private Werkzeugkasten w;
	
	
	
	
	public Paint() {
		f = new Fenster("by Till.W", 1280, 720);
		m = new Maus();
		f.setzeMausLauscherStandard(this);
		s = new Stift();
	}
	
	
	
	
	
	
	
	public static void main(String[] args) {
		Paint p = new Paint();
		
	}




	public void bearbeiteMausDruck(Object o, int x, int y) {
		s.runter();
	}

	public void bearbeiteMausDruckRechts(Object o, int x, int y) {
		
	}

	public void bearbeiteMausLos(Object o, int x, int y) {
		s.hoch();
	}

	public void bearbeiteMausLosRechts(Object o, int x, int y) {
		
	}

	public void bearbeiteMausBewegt(Object o, int x, int y) {
		s.bewegeAuf(x, y);
	}

	public void bearbeiteMausGezogen(Object o, int x, int y) {
		
	}
}
