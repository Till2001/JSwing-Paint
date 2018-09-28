package basiX.menu;

import java.util.Vector;

import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;

public class Menuepunkt {
	private JMenuItem menueitem;
	private boolean wurdeGewaehlt;
	private Vector<MenuePunktLauscher> menuePunktLauscher;
	public JMenuItem getMenueitem() {
		return menueitem;
	}
	public Menuepunkt(String name,char ausloesetaste){
		menueitem = new JMenuItem(name, ausloesetaste);
		wurdeGewaehlt = false;
		menueitem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						wurdeGewaehlt = true;
						// System.out.println("K");
						Menuepunkt.this.fireAuswahl(Menuepunkt.this);

					}
				});
			}
		});
	}
	
	
	
	
	
	protected void fireAuswahl(Menuepunkt menuepunkt) {
		if (menuePunktLauscher==null){
			return;
		}
		for (MenuePunktLauscher l:menuePunktLauscher){
			l.bearbeiteMenuepunktAuswahl(this);
		}
		
	}
	/** Ereignisbearbeitung registriert einen MenuePunktLauscher */
	public synchronized void setzeMenuePunktLauscher(MenuePunktLauscher l) {
		if (menuePunktLauscher == null){
			menuePunktLauscher =  new Vector<MenuePunktLauscher>();
		}
		if (!menuePunktLauscher.contains(l)) {
			menuePunktLauscher.addElement(l);
		}
	}

	/** Ereignisbearbeitung entfernt einen MenuePunktLauscher */
	public synchronized void entferneMenuePunktLauscher(MenuePunktLauscher l) {
		if (menuePunktLauscher != null && menuePunktLauscher.contains(l)) {
			menuePunktLauscher.removeElement(l);
		}
	}
	public String name() {
		
		return menueitem.getText();
	}
}
