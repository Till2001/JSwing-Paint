package basiX;

import java.awt.*;

/**
 *Farbkonstante
 * Beispiel: Farbe.ROT steht für die Farbe Rot
 * Beispiel: rgb(100,20,35) erzeugt eine Mischfarbe. Die Farbewerte für rot,
 * grün und blau
 *@author Georg Dick
 *@version 8.10.2009
 */
public class Farbe {
    public final static Color SCHWARZ = new Color(Color.black.getRed(),Color.black.getGreen(),Color.black.getBlue(),255);
    public final static Color BLAU =new Color(Color.blue.getRed(),Color.blue.getGreen(),Color.blue.getBlue(),255); 
    public final static Color CYAN = new Color(Color.cyan.getRed(),Color.cyan.getGreen(),Color.cyan.getBlue(),255);
    public final static Color DUNKELGRAU =new Color(Color.darkGray.getRed(),Color.darkGray.getGreen(),Color.darkGray.getBlue(),255); 
    public final static Color GRAU = new Color(Color.gray.getRed(),Color.gray.getGreen(),Color.gray.getBlue(),255);
    public final static Color GRUEN = new Color(Color.green.getRed(),Color.green.getGreen(),Color.green.getBlue(),255);
    public final static Color GR\u00dcN =GRUEN;
    public final static Color HELLGRAU =new Color(Color.lightGray.getRed(),Color.lightGray.getGreen(),Color.lightGray.getBlue(),255); 
    public final static Color MAGENTA = new Color(Color.magenta.getRed(),Color.magenta.getGreen(),Color.magenta.getBlue(),255);
    public final static Color ORANGE =new Color(Color.orange.getRed(),Color.orange.getGreen(),Color.orange.getBlue(),255); 
    public final static Color PINK =new Color(Color.pink.getRed(),Color.pink.getGreen(),Color.pink.getBlue(),255);
    public final static Color ROT = new Color(Color.red.getRed(),Color.red.getGreen(),Color.red.getBlue(),255);
    public final static Color WEISS =new Color(Color.white.getRed(),Color.white.getGreen(),Color.white.getBlue(),255);
    public final static Color GELB = new Color(Color.yellow.getRed(),Color.yellow.getGreen(),Color.yellow.getBlue(),255);
    public final static Color DURCHSICHTIG = new Color (255,255,255,0);
    

    /**
     *	rgb(rotanteil,gr&uuml;nantiel,blauanteil) liefert eine Mischfarbe.
     * Beispiel: rgb(100,0,0) erzeugt eine mittleres Rot. Die Farbewerte für rot,
 * grün und blau müssen im Bereich 0..255 liegen.
     */
    public final static Color rgb(double r, double g, double b) {
        return new Color((int)r,(int)g,(int)b,255);
    }
    /**
     *	rgb(rotanteil,gr&uuml;nantiel,blauanteil,transparenzgrad) liefert eine Mischfarbe.
     * Beispiel: rgb(100,0,0,50) erzeugt ein deutlich transparentes mittleres Rot. Die Parameterwerte 
     * müssen im Bereich 0..255 liegen. Undurchsichtig ist ein Transparenzgrad von 255 
     */
    public final static Color rgba(double r, double g, double b, double a) {
        return new Color((int)r,(int)g,(int)b,(int)a);
    }
}
