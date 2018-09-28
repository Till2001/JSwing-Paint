package basiX;

import java.awt.Dimension;

import javax.swing.JEditorPane;
import javax.swing.JScrollPane;

public class TextEditor extends Komponente {
	
	/**
	 * @param x
	 * @param y
	 * @param b
	 * @param h
	 * @param f
	 */
	public TextEditor(double x, double y, double b, double h, Komponente f) {
		super(new JEditorPane(){ public boolean getScrollableTracksViewportWidth() {
            return false;}}
        , x, y, b, h, f instanceof Fenster ? ((Fenster)f).leinwand():f);
	    	this.meineKomponente.validate();
	    	
	    	
	}
	
	
	
	
}
