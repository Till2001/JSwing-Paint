package basiX.swing;

import javax.swing.JFrame;

import basiX.Fenster;

public class JFrameMitKenntFenster extends JFrame  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8701846845107215669L;
	private Fenster kenntFenster;
	public JFrameMitKenntFenster(){
		super();
	}
	public JFrameMitKenntFenster(Fenster kenntFenster){
		super();
		this.kenntFenster=kenntFenster;
	}
	public Fenster getFenster(){
		return kenntFenster;
	}
	

}
