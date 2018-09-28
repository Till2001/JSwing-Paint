package basiX;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import basiX.swing.BufferedCanvas;

public class ReiterFeld extends Komponente {

	public ReiterFeld(double x, double y, double b, double h, Komponente f) {
		super(new JTabbedPane(), x, y, b, h,
				f instanceof Fenster ? ((Fenster) f).leinwand() : f);
	}

	public void fuegeReiterHinzu(String titel, Komponente k) {
		((JTabbedPane) this.getSwingComponent()).addTab(titel, k
				.getSwingComponent());
	}

}
