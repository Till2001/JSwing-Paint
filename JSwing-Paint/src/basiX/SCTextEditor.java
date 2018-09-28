package basiX;

import javax.swing.JEditorPane;
import javax.swing.JScrollPane;

public class SCTextEditor extends Komponente {
	private JEditorPane meineEingebetteteKomponente;

	/**
	 * @param x
	 * @param y
	 * @param b
	 * @param h
	 * @param f
	 */
	public SCTextEditor(double x, double y, double b, double h, Komponente f) {
		super(new JScrollPane(new JEditorPane()), x, y, b, h, f instanceof Fenster ? ((Fenster)f).leinwand():f);
	
    	this.meineKomponente.validate();
        try {
            meineEingebetteteKomponente = (JEditorPane)((JScrollPane)meineKomponente).getViewport().getComponent(0);  			
        } catch (Exception e) {
            System.out.println(e);
        }	
	}
	
	public JEditorPane getEmbeddedSwingComponent(){
		return meineEingebetteteKomponente;
	}
}
