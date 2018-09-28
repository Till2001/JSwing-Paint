package basiX;

import java.util.*;
import java.awt.AWTException;
import java.awt.Color;
import java.awt.Robot;
import java.awt.Toolkit;
import java.lang.System;

/**
 * Hilfebibliothek
 * 
 * @author Georg Dick
 * @version 16.01.2005
 */
public class Hilfe {
	private static Calendar calendar = new GregorianCalendar();

	/** lässt dem Betriebssystem etwas Zeit zur Bearbeitung anderer Aufgaben */
	public static void kurzePause() {
		try {
			Thread.sleep(1);
		} catch (Exception e) {
		}
	}
	/** lässt dem Betriebssystem i Millisekunden Zeit zur Bearbeitung anderer Aufgaben */
	public static void pause(int i) {
		try {
			Thread.sleep(i);
		} catch (Exception e) {
		}
	}
	/**
	 * 
	 * @param x
	 * @param y
	 * @return liefert die Farbe des Pixels mit den Bildschirmkoordinaten (x,y)
	 */
	public Color farbeVon(int x, int y) {
		try {
			Robot r;
			r = new Robot();
			return r.getPixelColor(x, y);
		} catch (AWTException e) {
			e.printStackTrace();
		}
		return null;
	}

	/** liefert eine ganzzahlige Zufallszahl im Intervall [von..bis] */
	public static int zufall(int von, int bis) {
		return (int) (Math.random() * (bis - von + 1)) + von;
	}

	/**
	 * erzeugt eine Verz&ouml;gerung ( zeit in Millisekunden )
	 */
	public static void warte(long zeit) {
		long start = System.currentTimeMillis();
		while (System.currentTimeMillis() - start < zeit) {
		}
	}

	

	/** liefert eine Zufallszahl aus dem Intervall [0..1[ */
	public static double zufall() {
		return Math.random();
	}

	/** liefert das Quadrat einer Zahl */
	public static double quadrat(double von) {
		return von * von;
	}

	/**
	 * liefert den Betrag einer Zahl
	 * 
	
	 */
	public static int betrag(int von) {
		if (von < 0)
			return -von;
		return von;
	}

	/**
	 * liefert die Wurzel einer positiven Zahl. Bei einer negativen Zahl wird
	 * als Fehlerhinweis eine -1 zurückgegeben
	 */
	public static double wurzel(double von) {
		if (von >= 0) {
			return Math.sqrt(von);
		}
		return -1;
	}

	/** liefert den Betrag einer Zahl */
	public static double betrag(double von) {
		if (von < 0)
			return -von;
		return von;
	}

	/**
	 * liefert den Sinus, Parameter Winkel in Grad
	 */
	public static double sinus(double a) {
		return Math.sin(a * Math.PI / 180);
	}

	/**
	 * liefert den Cosinus, Parameter Winkel in Grad
	 */
	public static double cosinus(double a) {
		return Math.cos(a * Math.PI / 180);
	}

	/**
	 * liefert den Tangens, Parameter Winkel in Grad
	 */
	public static double tangens(double a) {
		return Math.tan(a * Math.PI / 180);
	}

	/** liefert den Arcussinus: Ergebnis in Grad */
	public static double arcsinus(double a) {
		return Math.asin(a) * 180 / Math.PI;
	}

	/** liefert den Arcuscosinus: Ergebnis in Grad */
	public static double arccosinus(double a) {
		return Math.acos(a) * 180 / Math.PI;
	}

	/** liefert den Arcustangens: Ergebnis in Grad */
	public static double arctangens(double a) {
		return Math.atan(a) * 180 / Math.PI;
	}

	/** liefert die aktuelle Stunde */
	public static int stunde() {
		calendar.setTime(new Date());
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	/** liefert die aktuelle Minute */
	public static int minute() {
		calendar.setTime(new Date());
		return calendar.get(Calendar.MINUTE);
	}

	/** liefert die aktuelle Sekunde */
	public static int sekunde() {
		calendar.setTime(new Date());
		return calendar.get(Calendar.SECOND);
	}

	/** liefert die aktuelle Millisekunde */
	public static int millisekunde() {
		calendar.setTime(new Date());
		return calendar.get(Calendar.MILLISECOND);
	}

	/** liefert das Datum als Zeichenkette: Tag.Monat.Jahr */
	public static String datum() {
		calendar.setTime(new Date());
		return calendar.get(Calendar.DATE) + "."
				+ (calendar.get(Calendar.MONTH) + 1) + "."
				+ calendar.get(Calendar.YEAR);
	}

	/** liefert die Zeit als Zeichenkette: Stunde:Minute:Sekunde */
	public static String zeit() {
		calendar.setTime(new Date());
		return calendar.get(Calendar.HOUR_OF_DAY) + ":"
				+ calendar.get(Calendar.MINUTE) + ":"
				+ calendar.get(Calendar.SECOND);
	}

	/** liefert den Tag im Monat als int */
	public static int tag() {
		calendar.setTime(new Date());
		return calendar.get(Calendar.DATE);
	}

	/** liefert den Monat als int */
	public static int monat() {
		calendar.setTime(new Date());
		return calendar.get(Calendar.MONTH) + 1;
	}

	/** liefert das Kalenderjahr als int */
	public static int jahr() {
		calendar.setTime(new Date());
		return calendar.get(Calendar.YEAR);
	}

	/** realisiert die Exponentialfunktion */
	public static double exp(double a) {
		return Math.exp(a);
	}

	/** liefert den natürlichen Logarithmus */
	public static double ln(double a) {
		return Math.log(a);
	}

	/** liefert die Potenz a hoch b */
	public static double x_hoch_y(double x, double y) {
		return Math.pow(x, y);
	}

	/**
	 * wandelt einen String in eine ganze Zahl, falls der String eine solche
	 * darstellt, sonst den Wert 0
	 * 
	 * 
	 */
	public static int ganzzahlVon(String s) {
		try {
			return Integer.parseInt(s);
		} catch (Exception nfe) {
			return 0;
		}
	}

	/**
	 * wandelt einen String in eine ganze Zahl, falls der String eine solche
	 * darstellt, sonst den Wert 0
	 */
	public static int ganzZahlVon(String s) {
		try {
			return Integer.parseInt(s);
		} catch (Exception nfe) {
			return 0;
		}
	}

	/**
	 * stellt fest, ob ein String eine ganze Zahl darstellt.
	 * 
	 * 
	 */
	public static boolean istGanzzahl(String s) {
		try {
			Integer.parseInt(s);
			return true;
		} catch (Exception nfe) {
			return false;
		}
	}

	/**
	 * Stellt fest, ob ein String eine ganze Zahl darstellt.
	 */
	public static boolean istGanzZahl(String s) {
		try {
			Integer.parseInt(s);
			return true;
		} catch (Exception nfe) {
			return false;
		}
	}

	/**
	 * stellt fest, ob ein String eine Zahl darstellt
	 */
	public static boolean istZahl(String s) {
		try {
			Double.valueOf(s).doubleValue();
			return true;
		} catch (Exception nfe) {
			return false;
		}
	}

	/**
	 * wandelt einen String in eine Zahl, falls der String eine Zahl darstellt,
	 * sonst liefert sie den Wert 0.0
	 */
	public static double zahlVon(String s) {
		try {
			return Double.valueOf(s).doubleValue();
		} catch (Exception nfe) {
			return 0.;
		}
	}

	/**
	 * liefert zu jedem Zeichens c eine Codenummer.
	 * 
	 */
	public static int ord(char c) {
		return (int) c;
	}

	/**
	 * liefert das Zeichen zum Code i
	 */
	public static char zeichen(int i) {
		return (char) i;
	}
	
	public static int monitorBreite(){
		return Toolkit.getDefaultToolkit().getScreenSize().width;
	}
	
	public static int monitorHoehe(){
		
	return Toolkit.getDefaultToolkit().getScreenSize().height;}
}
