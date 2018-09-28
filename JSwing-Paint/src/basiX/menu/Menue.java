package basiX.menu;


import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class Menue {
	JMenu menu;
	public Menue(String name){
		menu = new JMenu(name);
	}
	
	public void setzeMenueTaste(char c){
		menu.setMnemonic(c);
	}

	public JMenu getMenu() {
		return menu;
	}
	
	public void fuegeMenuepunktHinzu(Menuepunkt mp){
		menu.add(mp.getMenueitem());
	}
	public void fuegeMenuepunktHinzu(String name,char ausloesetaste){
		menu.add(new JMenuItem(name, ausloesetaste));
	}

	public void fuegeMenuepunktHinzu(String name, char ausloesetaste,
			MenuePunktLauscher mpl) {
		Menuepunkt jm =new Menuepunkt(name, ausloesetaste);
		jm.setzeMenuePunktLauscher(mpl);	
		menu.add(jm.getMenueitem());
	}
	
	
 /*
  * 
  * // Menüelemente
		JMenu menu;

		// Erzeugen der Menüleiste
		menuBar = new JMenuBar();

		// Erstes Hauptmenü: Datei
		menu = new JMenu("Datei");

		menu.setMnemonic(KeyEvent.VK_D);
		menu.getAccessibleContext().setAccessibleDescription("Dateimenü");
		menuBar.add(menu);

		// Menüeinträge
		menuItemNE = new JMenuItem("Neuer Entwurf", KeyEvent.VK_N);
		menuItemNE.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
				ActionEvent.ALT_MASK));
		menuItemNE.getAccessibleContext().setAccessibleDescription(
				"Neuer Entwurf");
		menu.add(menuItemNE);
		menuItemNE.addActionListener(this);
		menuItemES = new JMenuItem("Entwurf speichern", KeyEvent.VK_S);
		menuItemES.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				ActionEvent.ALT_MASK));
		menuItemES.getAccessibleContext().setAccessibleDescription(
				"Entwurf speichern");
		menu.add(menuItemES);
		menuItemES.addActionListener(this);

		menuItemESU = new JMenuItem("Entwurf speichern unter", KeyEvent.VK_U);
		menuItemESU.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U,
				ActionEvent.ALT_MASK));
		menuItemESU.getAccessibleContext().setAccessibleDescription("");
		menu.add(menuItemESU);
		menuItemESU.addActionListener(this);

		menuItemEL = new JMenuItem("Entwurf laden", KeyEvent.VK_L);
		menuItemEL.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,
				ActionEvent.ALT_MASK));
		menuItemEL.getAccessibleContext().setAccessibleDescription("");
		menu.add(menuItemEL);
		menuItemEL.addActionListener(this);

		menuItemQBlueJ = new JMenuItem("Quelltext generieren und speichern",
				KeyEvent.VK_Q);
		menuItemQBlueJ.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,
				ActionEvent.ALT_MASK));
		menuItemQBlueJ.getAccessibleContext().setAccessibleDescription("");
		menu.add(menuItemQBlueJ);
		menuItemQBlueJ.addActionListener(this);

		((JFrame) this.getSwingComponent()).setJMenuBar(menuBar);
		((JFrame) this.getSwingComponent()).repaint();

  */
}
