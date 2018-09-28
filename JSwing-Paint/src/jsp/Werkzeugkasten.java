package jsp;

 

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSlider;
import javax.swing.KeyStroke;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import basiX.Fenster;
import basiX.Stift;



public class Werkzeugkasten {
    private JFrame fenster;
   private Fenster malFenster;
    private Stift stift;

    public Werkzeugkasten(Stift pStift, Fenster pMalFenster) {
        malFenster = pMalFenster;
        stift = pStift;
        fensterErzeugen();
        

    }

    private void fensterErzeugen() {
        // TODO Auto-generated method stub
        fenster = new JFrame();
        fenster.setLocation(1000, 0);
        menuezeileErzeugen(fenster);
        
        JButton knopf = new JButton("BLAU");
        knopf.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                stift.setzeFarbe(Color.BLUE);
                
            }
        });
        fenster.add(knopf, BorderLayout.EAST);
        
        knopf = new JButton("ROT");
        knopf.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                stift.setzeFarbe(Color.RED);
            }
        });
        
        fenster.add(knopf, BorderLayout.WEST);
        
        JSlider slider = new JSlider();
        slider.addChangeListener(new ChangeListener() {
            
            @Override
            public void stateChanged(ChangeEvent e) {
                //System.out.println(e.toString());
                stift.setzeLinienBreite((int)((JSlider)e.getSource()).getValue());
            }
        });
        fenster.add(slider, BorderLayout.CENTER);
    
        fenster.pack();
        fenster.setVisible(true);
        fenster.setDefaultCloseOperation(fenster.EXIT_ON_CLOSE);
        
    }
    
    /**
     * Die Menüzeile des Hauptfensters erzeugen.
     * 
     * @param fenster  das Fenster, in das die Menüzeile eingefügt werden soll.
     */
    private void menuezeileErzeugen(JFrame fenster)
    {
        final int SHORTCUT_MASK =
            Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();

        JMenuBar menuezeile = new JMenuBar();
        fenster.setJMenuBar(menuezeile);
        
        JMenu menue;
        JMenuItem eintrag;
        
        // Das Datei-Menü erzeugen
        menue = new JMenu("Datei");
        menuezeile.add(menue);
        
        eintrag = new JMenuItem("Öffnen...");
            eintrag.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, SHORTCUT_MASK));
            eintrag.addActionListener(new ActionListener() {
                               public void actionPerformed(ActionEvent e) { dateiOeffnen(); }
                           });
        menue.add(eintrag);

        eintrag = new JMenuItem("Schließen");
            eintrag.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, SHORTCUT_MASK));
            eintrag.addActionListener(new ActionListener() {
                               public void actionPerformed(ActionEvent e) { schliessen(); }
                           });
        menue.add(eintrag);
        menue.addSeparator();

        eintrag = new JMenuItem("Speichern");
            eintrag.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, SHORTCUT_MASK));
            eintrag.addActionListener(new ActionListener() {
                               public void actionPerformed(ActionEvent e) { speichern(); }
                           });
        menue.add(eintrag);
        menue.addSeparator();
        
        eintrag = new JMenuItem("Beenden");
            eintrag.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, SHORTCUT_MASK));
            eintrag.addActionListener(new ActionListener() {
                               public void actionPerformed(ActionEvent e) { beenden(); }
                           });
        menue.add(eintrag);


        

    }

    protected void beenden() {
        // TODO Auto-generated method stub
        
        System.exit(0);
    }

    protected void speichern() {
        // TODO Auto-generated method stub
        malFenster.speichereZeichenflaeche();
    }

    protected void schliessen() {
        // TODO Auto-generated method stub
        malFenster.loescheAlles();
    }

    protected void dateiOeffnen() {
        // TODO Auto-generated method stub
        malFenster.ladeBildInZeichenflaeche();
    }
}
