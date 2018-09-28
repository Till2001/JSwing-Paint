package basiX;

import java.awt.*;

/**
 * Oberflaechenelemente  dienen zur Gestaltung interaktiver grafischer Benutzungs
 * oberflächen. Sie sind sie rechteckig. In der Regel können Sie mit der Maus bedient werden, einige nehmen Tastendrucke an.
 * Folgende Attribute sind allen Oberflaechenelementen eigen: Position, Breite, Höhe, Farbe, Sichbarkeit, Benutzbarkeit
 * Sie können den Fokus haben und damit bestimmte Eingaben auf sich ziehen.
 */
public interface Oberflaechenelement {
    /** bestimmt Breite und Höhe in Pixeln */
    public void setzeGroesse(double b, double h);

    /** legt neue Position für die linke obere Ecke fest */
    public void setzePosition(double x, double y);

    /** ändert die Hintergrundfarbe des Elements zu farbe */
    public void setzeHintergrundFarbe(Color farbe);
    
    /** setzt die Sichtbarkeit */
    public void setzeSichtbar(boolean sb);

    /** Schaltet die Funktionstüchtigkeit des Oberflaechenelementes an (Parameterwert true) oder ab (false). */
    public void setzeBenutzbar(boolean bb);

    /** setzt den Fokus */
    public void setzeFokus();

    /** liefert die Breite in Pixeln */
    public int breite();

    /** liefert die Hoehe in Pixeln */
    public int hoehe();

    /** liefert die horizontale Koordinate der linken oberen Ecke */
    public int hPosition();

    /** liefert vertikale Koordinate der linken oberen Ecke */
    public int vPosition();
    
    /** gibt das Objekt frei */
    public void gibFrei();
    
  
}
