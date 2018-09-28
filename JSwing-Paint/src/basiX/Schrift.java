package basiX;

import java.awt.*;

/**
 *Konstante f&uuml;r Schriftart und Schriftstile. Beispiel für den Schriftstil
 * fett: Schrift.FETT
 * 
 *@author Georg Dick
 *@version 22.7.98
 */
public class Schrift // Konstante f&uuml;r Schrift
{
	public static final String DIALOG = "Dialog";
	public static final String ARIAL = "Arial";
	public static final String COMIC = "Comic Sans MS";
	public static final String HELVETICA = "Helvetica";
	public static final String LUCIDA = "Lucida Sans";
	public static final String TIMESROMAN = "Times New Roman";
	public static final String STANDARDSCHRIFTART = DIALOG;
	public static final int KURSIV = Font.ITALIC;
	public static final int FETT = Font.BOLD;
	public static final int KURSIVFETT = Font.ITALIC + Font.BOLD;
	public static final int STANDARDSTIL = Font.PLAIN;
	public static final int STANDARDGROESSE = 12;
	public static final Font STANDARDSCHRIFT = new Font(STANDARDSCHRIFTART,
			STANDARDSTIL, STANDARDGROESSE);
	

	public static void listeFonts() {
		for (String fonts : GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getAvailableFontFamilyNames())
			System.out.println(fonts);
	}
}
