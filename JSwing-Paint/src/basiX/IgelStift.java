package basiX;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.EventListener;

import javax.swing.event.MouseInputAdapter;

import basiX.swing.BufferedCanvas;
import basiX.util.Vektor2D;

public class IgelStift extends Stift {
	private SpriteBild igelSpriteBild;
	private float alphawertBild=0.8f;

	/**
	 * Erzeugt einen IgelStift auf einem Fenster
	 * 
	 */
	public IgelStift() {
		this("/basiX/images/Turtle.gif");
	}

	/**
	 * Erzeugt einen IgelStift auf einem Fenster. Die zugehörige Bilddatei wird
	 * per Pfad übergeben.Ist der Pfad null oder ungültig, so wird ein
	 * Standardbild vewendet.
	 * 
	 * @param pfad
	 */
	public IgelStift(String pfad) {
		super();
		try {
			igelSpriteBild = new SpriteBild(pfad, 0, 0, this.getMalflaeche());
		} catch (Exception e) {
			igelSpriteBild = new SpriteBild("/basiX/gui/images/Turtle.gif", 0,
					0, this.getMalflaeche());
		}
		igelSpriteBild.setzeTransparenzGrad(alphawertBild);
		// entfernt alle Mauslistener. Damit gibt das Sprite die Mausevents an seinen Parent ungefiltert weiter und dieser kann
		// sie verarbeiten.
		this.mauslistenerentfernen();
		this.mausmotionlistenerentfernen();
		this.bewegeBis(0,0);	
	}
	
	protected void mauslistenerentfernen(){
		MouseListener[] el  = igelSpriteBild.getSwingComponent().getListeners(MouseListener.class);
		for (int i=0; i<el.length;i++){
			igelSpriteBild.getSwingComponent().removeMouseListener(el[i]);
		}
	}
	protected void mausmotionlistenerentfernen(){
		MouseMotionListener[] el  = igelSpriteBild.getSwingComponent().getListeners(MouseMotionListener.class);
		for (int i=0; i<el.length;i++){
			igelSpriteBild.getSwingComponent().removeMouseMotionListener(el[i]);
		}
	}
	/**
	 * Erzeugt einen IgelStift auf einer StiftFlaeche sofern es sich um eine
	 * Komponente handelt. Auf einem SpriteBild kann eine IgelStift nicht
	 * erzeugt werden.
	 * 
	 * @param w
	 */
	public IgelStift(StiftFlaeche w) {
		this("/basiX/images/Turtle.gif", w);
	}

	/**
	 * Erzeugt einen IgelStift auf einer StiftFlaeche sofern es sich um eine
	 * Komponente handelt. Auf einem SpriteBild kann eine IgelStift nicht
	 * erzeugt werden. Die verwendete Bilddatei wird über den Pfad ausgewählt.
	 * Ist der Pfad null oder ungültig, so wird ein Standardbild vewendet.
	 * 
	 * @param pfad
	 * @param w
	 */
	public IgelStift(String pfad, StiftFlaeche w) {
		super(w);
		try {
			igelSpriteBild = new SpriteBild(pfad, 0, 0, this.getMalflaeche());
		} catch (Exception e) {
			igelSpriteBild = new SpriteBild("/basiX/gui/images/Turtle.gif", 0,
					0, this.getMalflaeche());
		}
		igelSpriteBild.setzeTransparenzGrad(alphawertBild);
		// entfernt alle Mauslistener. Damit gibt das Sprite die Mausevents an seinen Parent ungefiltert weiter und dieser kann
		// sie verarbeiten.
		this.mauslistenerentfernen();
		this.mausmotionlistenerentfernen();
		this.bewegeBis(0,0);	}
		/**
	 * Der IgelStift wird angewiesen auf der Unterlage zu malen. 
	 * 
	 * @param w Malflaeche für den Stift            
	 * 
	 */
	@Override
	public void maleAuf(BasisStiftFlaeche sf) {
		super.maleAuf(sf);
		if (igelSpriteBild!=null){
		igelSpriteBild.betteEinIn((Komponente)sf);
		igelSpriteBild.setzePosition(this.hPosition()- igelSpriteBild.mittelpunkt().getX(),
				this.vPosition()- igelSpriteBild.mittelpunkt().getY());
		igelSpriteBild.setzeBildWinkelOhneGrößenAnpassung(this.winkel());}
	}
	/**
	 * 
	 * @param a legt Durchsichtigkeit fest : 0: vollständig durchsichtig .. 1 undurchsichtig
	 */
	public void setzeAlpha(float a){
		alphawertBild = a;
		igelSpriteBild.setzeTransparenzGrad(alphawertBild);
	}
	/**
	 * Ersetzt das angezeigte Bild durch eines, das über den Pfad angegeben wird
	 * @param pfad
	 */
	public void setzeBild(String pfad){
		this.igelSpriteBild.ladeBild(pfad);
		igelSpriteBild.setzeTransparenzGrad(alphawertBild);
		igelSpriteBild.setzePosition(this.hPosition()- igelSpriteBild.mittelpunkt().getX(),
				this.vPosition()- igelSpriteBild.mittelpunkt().getY());
		igelSpriteBild.setzeBildWinkelOhneGrößenAnpassung(this.winkel());
		
		
	}
	
	
	/**
	 * zeigt oder verbirgt das Igelbild *
	 * 
	 * @param b
	 */
	public void setzeSichtbar(boolean b) {
		igelSpriteBild.setzeSichtbar(b);
	}

	@Override
	public void bewegeBis(double px, double py) {
		super.bewegeBis(px, py);
		igelSpriteBild.setzePosition(px - igelSpriteBild.mittelpunkt().getX(), 
				py- igelSpriteBild.mittelpunkt().getY());
//		igelSprite.setzeBildWinkel(this.winkel());
	}
	
	@Override
	public void bewegeAuf(double px, double py) {
		super.bewegeAuf(px, py);
		igelSpriteBild.setzePosition(px - igelSpriteBild.mittelpunkt().getX(), 
				py- igelSpriteBild.mittelpunkt().getY());
		igelSpriteBild.setzeBildWinkelOhneGrößenAnpassung(this.winkel());
	}

	@Override
	public void bewegeUm(double pl) {
		super.bewegeUm(pl);
//		igelSprite.setzeBildWinkel(this.winkel());
//		igelSprite.setzeBewegungsRichtung(winkel());
		igelSpriteBild.setzePosition(this.hPosition() - igelSpriteBild.mittelpunkt().getX(), 
				this.vPosition()- igelSpriteBild.mittelpunkt().getY());
	}

	@Override
	public void bewegeUm(Vektor2D v) {
		super.bewegeUm(v);
		igelSpriteBild.setzeBildWinkelOhneGrößenAnpassung(winkel());
	//	igelSprite.setzeBewegungsRichtung(winkel());
		igelSpriteBild.setzePosition(this.hPosition() - igelSpriteBild.mittelpunkt().getX(), 
				this.vPosition()- igelSpriteBild.mittelpunkt().getY());
	}

	@Override
	public void dreheBis(double w) {
		super.dreheBis(w);
		igelSpriteBild.setzeBildWinkelOhneGrößenAnpassung(w);
		//igelSprite.setzeBewegungsRichtung(winkel());
		
	}

	@Override
	public void dreheUm(double w) {
		super.dreheUm(w);
		igelSpriteBild.setzeBildWinkelOhneGrößenAnpassung(this.winkel());
		// igelSprite.setzeBewegungsRichtung(winkel());
	}

	@Override
	public void gibFrei() {
		super.gibFrei();
		igelSpriteBild.gibFrei();
	}

	

	@Override
	public void schreibe(String ps) {
		super.schreibe(ps);
		igelSpriteBild.setzePosition(
				this.hPosition() - igelSpriteBild.mittelpunkt().getX(), this
						.vPosition()
						- igelSpriteBild.mittelpunkt().getY());
		igelSpriteBild.setzeBildWinkelOhneGrößenAnpassung(this.winkel());
	}

	

	@Override
	public void zeichneKreisBogen(double radius, double drehwinkel) {
		super.zeichneKreisBogen(radius, drehwinkel);
		igelSpriteBild.setzeBildWinkelOhneGrößenAnpassung(this.winkel());
	}

	@Override
	public void zeichneRechteck(double pbreite, double phoehe) {
		super.zeichneRechteck(pbreite, phoehe);
		igelSpriteBild.setzePosition(
				this.hPosition() - igelSpriteBild.mittelpunkt().getX(), this
						.vPosition()
						- igelSpriteBild.mittelpunkt().getY());
		igelSpriteBild.setzeBildWinkelOhneGrößenAnpassung(this.winkel());
	}

	@Override
	public void dreheInRichtung(double x, double y) {
		super.dreheInRichtung(x, y);
		igelSpriteBild.setzePosition(
				this.hPosition() - igelSpriteBild.mittelpunkt().getX(), this
						.vPosition()
						- igelSpriteBild.mittelpunkt().getY());
		igelSpriteBild.setzeBildWinkelOhneGrößenAnpassung(this.winkel());
	}

	@Override
	public void restauriereZustand() {
		super.restauriereZustand();
		igelSpriteBild.setzePosition(
				this.hPosition() - igelSpriteBild.mittelpunkt().getX(), this
						.vPosition()
						- igelSpriteBild.mittelpunkt().getY());
		igelSpriteBild.setzeBildWinkelOhneGrößenAnpassung(this.winkel());
		
	}

	@Override
	public void zeichneKreis(double pradius) {
		super.zeichneKreis(pradius);
		igelSpriteBild.setzePosition(
				this.hPosition() - igelSpriteBild.mittelpunkt().getX(), this
						.vPosition()
						- igelSpriteBild.mittelpunkt().getY());
		igelSpriteBild.setzeBildWinkelOhneGrößenAnpassung(this.winkel());
	}

	@Override
	public void zeichneLinie(double x, double y) {
		super.zeichneLinie(x, y);
		igelSpriteBild.setzePosition(
				this.hPosition() - igelSpriteBild.mittelpunkt().getX(), this
						.vPosition()
						- igelSpriteBild.mittelpunkt().getY());
		igelSpriteBild.setzeBildWinkelOhneGrößenAnpassung(this.winkel());
	}

	@Override
	public void zeichnePolygon(double[] x, double[] y) {
		super.zeichnePolygon(x, y);
		igelSpriteBild.setzePosition(
				this.hPosition() - igelSpriteBild.mittelpunkt().getX(), this
						.vPosition()
						- igelSpriteBild.mittelpunkt().getY());
		igelSpriteBild.setzeBildWinkelOhneGrößenAnpassung(this.winkel());
	}

	@Override
	public void zeichnePolygon(Vektor2D[] xy) {
		super.zeichnePolygon(xy);
		igelSpriteBild.setzePosition(
				this.hPosition() - igelSpriteBild.mittelpunkt().getX(), this
						.vPosition()
						- igelSpriteBild.mittelpunkt().getY());
		igelSpriteBild.setzeBildWinkelOhneGrößenAnpassung(this.winkel());
	}

	

}
