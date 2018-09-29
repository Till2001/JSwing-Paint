package jsp;

 

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import basiX.Dialog;
import basiX.Fenster;
import basiX.Stift;



public class Werkzeugkasten {
    private JFrame fenster;
    private Fenster malFenster;
    private Stift stift;
    private JButton cb;
    private Paint p;

    public Werkzeugkasten(Stift pStift, Fenster pMalFenster,Paint pPaint) {
        malFenster = pMalFenster;
        stift = pStift;
        fensterErzeugen();
        p = pPaint;
        
        
        

    }

    private void fensterErzeugen() {
        fenster = new JFrame();
        fenster.setLocation(1000, 0);

        
        
        
        JButton knopf = new JButton("Radieren",new ImageIcon(getClass().getResource("icons/eraser.png")));
        knopf.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                stift.radiere();
                p.setzepenstate(0);
            }
        });
        
        fenster.add(knopf, BorderLayout.WEST);
        
        
        JPanel jpanel = new JPanel(new GridLayout(0, 1));
        knopf = new JButton("",new ImageIcon(getClass().getResource("icons/pipette.png")));
        knopf.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				p.setzepenstate(1);
			}
		});
        jpanel.add(knopf);
        
        
        knopf = new JButton("",new ImageIcon(getClass().getResource("icons/paint_bucket.png")));
        knopf.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				p.setzepenstate(2);
			}
		});
        jpanel.add(knopf);
        
        fenster.add(jpanel,BorderLayout.EAST);
        
        
        
        
        knopf = new JButton("Normaler Stift",new ImageIcon(getClass().getResource("icons/pen.png")));
        knopf.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				stift.normal();
				p.setzepenstate(0);
			}
		});
        fenster.add(knopf,BorderLayout.SOUTH);
        	
        
        
        
        knopf = new JButton("Farbe",new ImageIcon(getClass().getResource("icons/colour.png")));
        knopf.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				Color c = JColorChooser.showDialog(null, null, null);
				stift.setzeFarbe(c);
				cb.setBackground(c);
			}
		});
        fenster.add(knopf,BorderLayout.NORTH);
        
        JSlider slider = new JSlider(1,25,10);
        slider.addChangeListener(new ChangeListener() {
        	
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
