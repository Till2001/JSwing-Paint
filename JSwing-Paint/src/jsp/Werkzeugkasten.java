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
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import basiX.Fenster;
import basiX.Stift;



public class Werkzeugkasten {
    private JFrame fenster;
    private Fenster malFenster;
    private Stift stift;
    private JButton cb;

    public Werkzeugkasten(Stift pStift, Fenster pMalFenster) {
        malFenster = pMalFenster;
        stift = pStift;
        fensterErzeugen();
        
        

    }

    private void fensterErzeugen() {
        fenster = new JFrame();
        fenster.setLocation(1000, 0);
        
        JButton knopf = new JButton("BLAU");
        knopf.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                stift.setzeFarbe(Color.BLUE);
                
            }
        });
        fenster.add(knopf, BorderLayout.EAST);
        
        
        
        knopf = new JButton("ROT");
        knopf.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                stift.setzeFarbe(Color.RED);
            }
        });
        
        fenster.add(knopf, BorderLayout.WEST);
        
        
        knopf = new JButton("Farbe");
        knopf.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Color c = JColorChooser.showDialog(null, null, null);
				stift.setzeFarbe(c);
				cb.setBackground(c);
			}
		});
        fenster.add(knopf,BorderLayout.NORTH);
        
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
    
    public void cbkennen(JButton pcb) {
    	cb = pcb;
    }

}
