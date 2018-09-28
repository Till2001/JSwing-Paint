package jsp;

import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.print.attribute.standard.JobHoldUntil;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import basiX.Dialog;
import basiX.Fenster;
import basiX.Maus;
import basiX.MausLauscherStandard;
import basiX.Stift;

public class Paint implements MausLauscherStandard {
	private Fenster f;
	private Maus m;
	private Stift s;
	private Werkzeugkasten w;
	private JFrame jf;
	
	
	
	
	public Paint() {
		f = new Fenster("by Till.W", 1000, 800);
		m = new Maus();
		f.setzeMausLauscherStandard(this);
		s = new Stift();
		jf = f.getMeinJFrame();
		w = new Werkzeugkasten(s, f);
		createmenubar();
		
		
		
	}
	
	
	
	
	
	
	
	public static void main(String[] args) {
		Paint p = new Paint();
		
	}

	private void createmenubar() {
		final int SHORTCUT_MASK = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
		
		JMenuBar menubar = new JMenuBar();
		jf.setJMenuBar(menubar);
		
		JMenu menu;
		JMenuItem item;

		//Datei Menü Anfang
		
		menu = new JMenu("Datei");
		menubar.add(menu);
		
		
		
		
		
		//Zeichenfläche Löschen
		item = new JMenuItem("Zeichenfläche Leeren");
		item.setIcon(new ImageIcon(getClass().getResource("icons/canvas.png")));
		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,SHORTCUT_MASK));
		item.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(Dialog.entscheidung("Entscheidung", "Wollen sie die Zeichenfläche leeren?")==true) {
				f.loescheAlles();
				}
			}
		});
		menu.add(item);
		
		//Bild Speichern Funktion
		item = new JMenuItem("Speichern",new ImageIcon(getClass().getResource("icons/save.png")));
		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,SHORTCUT_MASK));
		item.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				f.speichereZeichenflaeche();
			}
		});
		menu.add(item);
		
		//Bild Laden Funktion
		item = new JMenuItem("Laden",new ImageIcon(getClass().getResource("icons/open.png")));
		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,SHORTCUT_MASK));
		item.addActionListener(new ActionListener() {
					
		public void actionPerformed(ActionEvent e) {
			f.ladeBildInZeichenflaeche();
			}
		});
		menu.add(item);
		
		//Datei Menü Ende

		menubar.add(Box.createHorizontalGlue());
		
		
		
		
		//
		
		JButton ColorButton = new JButton("");
		ColorButton.setBackground(s.farbe());
		ColorButton.setOpaque(true);
		menubar.add(ColorButton);
		w.cbkennen(ColorButton);
		
		//
		
		
		//Ende Knopf Anfang
		
		menu = new JMenu("Optionen");
		menubar.add(menu);
		
		
		
		item = new JMenuItem("Ende",new ImageIcon(getClass().getResource("icons/exit.png")));
		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, SHORTCUT_MASK));
		item.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(Dialog.entscheidung("Entscheidung", "Wollen sie das Programm wirklich schließen?")==true) {
					System.exit(0);
				}
			}
		});
		menu.add(item);
		
		

		//Ende Knopf Ende
		
		
		
		
		jf.pack();
		jf.setVisible(true);
		
		
		
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
