package basiX;


import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;



public class Tabelle extends Komponente {

	private JTable meineEingebetteteKomponente ;

	public Tabelle(double x, double y, double b, double h, int zeilen, int spalten, Komponente f) {		
		super(new JScrollPane(new JTable(zeilen,spalten )),x,y,b,h,f instanceof Fenster ? ((Fenster)f).leinwand():f);
    	this.meineKomponente.validate();      
          meineEingebetteteKomponente = (JTable)((JScrollPane)meineKomponente).getViewport().getComponent(0);          
	}
	
	public void fuegeSpalteHinzu(){
		meineEingebetteteKomponente.addColumn(new TableColumn());
	}
	
	public JTable getEmbeddedSwingComponent(){
		return meineEingebetteteKomponente;
	}
	
	
	
	
	

}
