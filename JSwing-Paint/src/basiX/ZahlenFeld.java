package basiX;

import java.awt.event.*;
import java.awt.*;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.*;

import basiX.vw.*;

/* letzte Änderung 6.11.2009 */
/**
 * Zahlenfelder sind spezielle TextFelder. Sie lassen nur den Eintrag von Zahlen
 * zu
 */
public class ZahlenFeld extends TextFeld {
	public boolean fehler=false;

//	/**  */
//	private class DocuLis implements DocumentListener {
//
//		Runnable fehlerbehebung = new Runnable() {
//			public void run() {
//				try{				
//						setzeText(zahl(text()));						
//				}
//				catch(Exception e){
//					e.printStackTrace();
//				}
//			}
//		};
//		
//		public String zahl(String t) {
//			while (!(t.equals("") || Hilfe.istZahl(t) || t.equals("-"))) {
//				t = t.substring(0, t.length() - 1);
//			}
//			return t;
//		}
//
//		@Override
//		public void changedUpdate(DocumentEvent arg0) {
//			caupdate();
//
//		}
//
//		@Override
//		public void insertUpdate(DocumentEvent arg0) {
//			try {
//				if (text().equals("") || Hilfe.istZahl(text())
//						|| text().equals("-")) {
//					caupdate();
//					
//				} else {			
////					System.out.println("Fehler");
//					SwingUtilities.invokeLater(fehlerbehebung);// Gaaaanz wichtig
//					// wird erst nach der Eventbehandlung von insertUp. ausgeführt
//					// ansonsten wäre das Dokument für Änderungen blockiert
//					
//				}
//			} catch (Exception e) {
//				
//			}
//		}
//
//		@Override
//		public void removeUpdate(DocumentEvent arg0) {
//			caupdate();
//
//		}
//	}

	
	
	
	
	
	
	
	/**
	 * erzeugt auf einem Fenster ein ZahlenFeld mit linker oberer Ecke (x,y)
	 * vorgegebener Breite und Hoehe, das Fenster muss vorher erzeugt sein
	 */
	public ZahlenFeld(double x, double y, double b, double h) {
		super(x, y, b, h, DasFenster.hauptLeinwand());
		erzeugeZahlenDokument();
		//this.documentListenerAustauschen();
	}

	private void erzeugeZahlenDokument() {
		try {
			((JTextField) meineKomponente).getDocument()
					.removeDocumentListener(dokuListener);
		} catch (Exception e) {
		}
		
		((JTextField) meineKomponente).setDocument(new PlainDocument() 
		{ @Override
		  public void insertString(int offs, String str, AttributeSet a) 
		    throws BadLocationException 
		  { 
	
		    String currentText = getText(0, getLength()); 
		    String beforeOffset = currentText.substring(0, offs); 
		    String afterOffset = currentText.substring(offs, currentText.length()); 
		    String proposedResult = beforeOffset + str + afterOffset; 
		    if (!(Hilfe.istZahl(proposedResult)||proposedResult.equals("")||
		    		proposedResult.equals("-"))){
		    	return;
		    }
//		    for(int i=0;i<proposedResult.length();i++) 
//		    { 
//		      if(!Character.isDigit(proposedResult.charAt(i))) 
//		      { 
//		        return; 
//		      }             
//		    } 
		    super.insertString(offs, str, a); 
		  } 
	
		});
		((JTextField) meineKomponente).getDocument().addDocumentListener(dokuListener);
	}

	/**
	 * erzeugt auf einem Fensterobjekt ein ZahlenFeld mit linker oberer Ecke
	 * (10,10 der Breite 10 und der Höhe 10) das Fenster muss vorher erzeugt
	 * sein
	 */
	public ZahlenFeld() {
		super(10, 10, 10, 10, DasFenster.hauptLeinwand());
		erzeugeZahlenDokument();
//		this.documentListenerAustauschen();
	}

	/**
	 * erzeugt auf dem Fenster f ein ZahlenFeld mit linker oberer Ecke (x,y)
	 * vorgegebener Breite und Hoehe, das Fenster muss vorher erzeugt sein
	 * 
	 */
	public ZahlenFeld(double x, double y, double b, double h, Fenster f) {
		super(x, y, b, h, f.leinwand());
		erzeugeZahlenDokument();
//		this.documentListenerAustauschen();
	}

	/**
	 * erzeugt auf einem Containerobjekt le ein ZahlenFeld mit linker oberer
	 * Ecke (x,y) vorgegebener Breite und Hoehe, der Container muss vorher
	 * erzeugt sein
	 * 
	 */
	public ZahlenFeld(double x, double y, double b, double h, Komponente le) {
		super(x, y, b, h, le);
		erzeugeZahlenDokument();
//		this.documentListenerAustauschen();
	}

//	protected void documentListenerAustauschen() {
//		try {
//			((JTextField) meineKomponente).getDocument()
//					.removeDocumentListener(cl);
//		} catch (Exception e) {
//		}
//		cl = new DocuLis();
//		((JTextField) meineKomponente).getDocument().addDocumentListener(cl);
//	}

	/** belegt das Feld mit der Zahl z */
	public void setzeZahl(double z) {
		try {
			if (z == (int) z) {
				super.setzeText(new Integer((int) z).toString());
			} else {
				super.setzeText("" + z);
			}
		} catch (Exception e) {
			this.setzeText("" + z);
		}
	}

	/**
	 * belegt das Feld mit der Zahl z
	 * 
	 * 
	 */
	public void setzeZahl(int z) {
		super.setzeText("" + z);
	}

	/** schreibt den Text s in das Feld, falls dieser eine Zahl darstellt */
	public void setzeText(String s) {
		if (Hilfe.istZahl(s) || s.equals("") || s.equals("-")) {
			super.setzeText(s);
		}
	}

	/** liefert die Zahl im Feld, eine Null, falls das Feld leer ist */
	public double zahl() {
		if (this.text().equals("-")||this.text().equals("")) {
			return 0.0;
		}
		return Hilfe.zahlVon(this.text());
	}

	/** liefert die Zahl im Feld, eine Null, falls das Feld leer ist */
	public int ganzZahl() {
		if (this.text().equals("-")||this.text().equals("")) {
			return 0;
		}
		return Hilfe.ganzzahlVon(this.text());
	}

	/** liefert die Zahl im Feld, eine Null, falls das Feld leer ist */
	public int ganzzahl() {
		return this.ganzZahl();
	}
	
	/** fügt Text an den bestehenden Text an */
	public void fuegeAn(String s) {
		if (this.text().equals("")){
			this.setzeText(s);
		} else
		this.setzeText(this.text() + s);
	}

	/**
	 * hängt Text an den bestehenden an
	 * 
	 */
	public void fuegeAn(char s) {
		this.fuegeAn(s+"");
	}


}
