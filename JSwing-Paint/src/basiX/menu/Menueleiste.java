package basiX.menu;

import javax.swing.*;

import basiX.Fenster;

public class Menueleiste {
	JMenuBar leiste;

	public Menueleiste(Fenster f) {
		leiste = new JMenuBar();
		((JFrame) f.getSwingComponent()).setJMenuBar(leiste);
		((JFrame) f.getSwingComponent()).repaint();
	}

	public void fuegeMenueHinzu(Menue m) {
		leiste.add(m.getMenu());
	}
	public JMenuBar getMenueleiste(){
		return leiste;
	}
}
