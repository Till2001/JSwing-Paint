package jsp;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Stack;

import javax.print.attribute.standard.JobHoldUntil;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import basiX.Dialog;
import basiX.Fenster;
import basiX.Hilfe;
import basiX.Leinwand;
import basiX.Maus;
import basiX.MausLauscherStandard;
import basiX.Stift;

public class Paint implements MausLauscherStandard {
	private Fenster f;
	private Maus m;
	private Stift s;
	private Werkzeugkasten w;
	private JFrame jf;
	private int penstate = 0,tx,ty;
	private JButton ColorButton;
	private Leinwand lw;
	
	
	public Paint() {
		f = new Fenster("by Till.W", 1000, 800);
		lw = new Leinwand(0, 0, 1000, 800);
		m = new Maus();
		f.setzeMausLauscherStandard(this);
		s = new Stift(lw);
		s.setzeLinienBreite(10);
		jf = f.getMeinJFrame();
		w = new Werkzeugkasten(s, f,this);
		
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
		menu.setIcon(new ImageIcon(getClass().getResource("icons/data.png")));
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

		//Geometrie Menü Anfang
		
		menu = new JMenu("Geometrie");
		menu.setIcon(new ImageIcon(getClass().getResource("icons/shapes.png")));
		menubar.add(menu);
		
		item = new JMenuItem("Viereck",new ImageIcon(getClass().getResource("icons/square.png")));
		item.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				
			}
		});
		menu.add(item);
		
		item = new JMenuItem("Dreieck",new ImageIcon(getClass().getResource("icons/triangle.png")));
		item.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				
			}
		});
		menu.add(item);
		
		item = new JMenuItem("Kreis",new ImageIcon(getClass().getResource("icons/circle.png")));
		item.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				
			}
		});
		menu.add(item);
		
		//Geometrie Menü Anfang
		
		
		menubar.add(Box.createHorizontalGlue());
		
		
		
		
		//
		
		ColorButton = new JButton("");
		ColorButton.setBackground(s.farbe());
		ColorButton.setOpaque(true);
		menubar.add(ColorButton);
		w.cbkennen(ColorButton);
		
		//
		
		
		//Ende Knopf Anfang
		
		menu = new JMenu("Optionen");
		menu.setIcon(new ImageIcon(getClass().getResource("icons/cog.png")));
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

	
	
	
	
	
	
	
	
	

	public void setzepenstate(int pPenstate) {
		penstate = pPenstate;
	}
	
	
	

	public void bearbeiteMausDruck(Object o, int x, int y) {
		switch(penstate) {
		case 0: //Normal und Radieren
			s.runter();
			break;
		case 1: //pipette
			s.setzeFarbe(f.farbeVon(x, y));
			ColorButton.setBackground(s.farbe());
			setzepenstate(0);
			break;
		case 2: //Füllen
				Stack<Integer> stx = new Stack<Integer>();
				Stack<Integer> sty = new Stack<Integer>();
				Color c = lw.farbeVon(x, y);
				stx.add(x);
				sty.add(y);

				while(!stx.isEmpty()||!sty.isEmpty()) {
					tx = stx.pop();
					ty = sty.pop();
					if(lw.farbeVon(tx, ty).equals(c)) {
						lw.setzeFarbeBei(tx, ty, s.farbe());
						stx.push(tx+1); 
						sty.push(ty);
						stx.push(tx-1); 
						sty.push(ty);
						stx.push(tx); 
						sty.push(ty+1);
						stx.push(tx); 
						sty.push(ty-1);
						Dialog.info("", "");
					}
					
				}
				
				//Maus = Fenster 
				//Lw = Weird
//				int ttx = (int) f.hPosition();
//				int tty = (int) f.vPosition();
//				int tttx = (int) lw.hPosition();
//				int ttty = (int) lw.vPosition();

				
			break;

		
		}
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
