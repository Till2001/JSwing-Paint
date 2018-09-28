package basiX;


/*
 * Version 12.10.2012
 */
import java.awt.Toolkit;
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Hilfsklasse zur Erzeugung von Dialogfenstern.
 * Die Methoden sind zum groessten Teil statisch, es muss also kein Objekt erzeugt werden.
 * Die Nachrichten können direkt an die Klasse Dialog gesendet werden.
 * 
 * @author BeA
 * @version 10.2012
 */
public class Dialog {
	Clip clip;
	AudioInputStream audioInputStream;
	BufferedInputStream bis;
	AudioFormat af;
	int size;
	byte[] audio;
	DataLine.Info info;
/**
 * Das Dialogfenster enthält einen Schieberegler.
 * @param pTitel ist der Titel des Dialogfensters.
 * @param pText ist der Text des Dialogfensters.
 * @param min ist der minimale Wert des Schiebereglers.
 * @param max ist der maximale Wert des Schiebereglers.
 * @param aktuell ist der erste aktuelle Wert des Schiebereglers.
 * @param diff ist der Abstand der Markierungen auf der Achse.
 * @return Diese Methode gibt einen int-Wert  zurück.
 */
	public static int schieberegler(String pTitel, String pText, int min, int max,
			int aktuell, int diff) {
		JFrame parent = new JFrame();
		JOptionPane optionPane = new JOptionPane();
		JSlider slider = getSlider(optionPane, min, max, aktuell, diff);
		optionPane.setMessage(new Object[] { pText, slider });
		optionPane.setMessageType(JOptionPane.QUESTION_MESSAGE);
		optionPane.setOptionType(JOptionPane.OK_CANCEL_OPTION);
		JDialog dialog = optionPane.createDialog(parent, pTitel);
		dialog.setVisible(true);
		return slider.getValue();
	}

	private void stateChanged(ChangeEvent e) {
		
	}

	private static JSlider getSlider(final JOptionPane optionPane, int min,
			int max, int akt, int diff) {
		if (akt < min || akt > max) {
			akt = min;
		}
		JSlider slider = new JSlider(min, max, akt);
		slider.setMajorTickSpacing(diff);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);

		ChangeListener changeListener = new ChangeListener() {
			public void stateChanged(ChangeEvent changeEvent) {
				JSlider theSlider = (JSlider) changeEvent.getSource();
				if (!theSlider.getValueIsAdjusting()) {
					optionPane.setInputValue(new Integer(theSlider.getValue()));
				}
			}

		};
		slider.addChangeListener(changeListener);
		return slider;
	}

/**
 * Die Methode ist nicht statisch, es muss ein Objekt erzeugt werden.
 * Das Dialogfenster enthält ein beliebiges Objekt.
 * @param pTitel ist der Titel des Dialogfensters.
 * @param obj ist ein beliebiges Objekt, welches das Fenster enthalten soll.
 * @param pPfad ist der Dateiname eines Bildchens, welches das Fenster enthalten soll.
 * Das Bild sollte im selben Paket sein. 
 */
	public void neu(String pTitel, Object obj, String pPfad) {
		ImageIcon ii = new ImageIcon(getClass().getResource(pPfad));
		JOptionPane.showMessageDialog(null, obj, pTitel, 0, ii);

	}
	/**
	 * Die Methode ist nicht statisch, es muss ein Objekt erzeugt werden.
	 * Das Fenster enthält ein Bild, Text und spielt eine wav-Datei ab.
	 * @param pTitel ist der Titel des Dialogfenstes.
	 * @param pText ist der Text des Fensters.
	 * @param pBild ist der Dateinam des Bildes, welches sich im selben Paket befinden sollte.
	 * @param pSound ist der Dateinam der wav-Datei, welche sich im selben Paket befinden sollte.
	 */	
	
	public void sound(String pTitel, String pText, String pBild, String pSound){
		ImageIcon ii = new ImageIcon(getClass().getResource(pBild));
		
		try {
			audioInputStream = AudioSystem.getAudioInputStream(getClass()
					.getResource(pSound));
			bis = new BufferedInputStream(audioInputStream);
			af = audioInputStream.getFormat();
			size = (int) (af.getFrameSize() * audioInputStream.getFrameLength());
			audio = new byte[size];
			info = new DataLine.Info(Clip.class, af, size);
			bis.read(audio, 0, size);

		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			clip = (Clip) AudioSystem.getLine(info);
			clip.open(af, audio, 0, size);
			clip.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		JOptionPane.showMessageDialog(null, pText, pTitel, 0, ii);
	}
	
/**
 * Die Methode ist nicht statisch, es muss ein Objekt erzeugt werden.
 *Das Dialogfenster enthält ein Bild, welches sich im selben Paket befinden kan.
 * @param pTitel ist der Titel des Dialogfensters.
 * @param pText ist ein Text
 * @param pPfad ist der Dateiname. 
 * 
 */
	
	public void bild(String pTitel, String pText, String pPfad) {
		ImageIcon ii = new ImageIcon(getClass().getResource(pPfad));
		JOptionPane.showMessageDialog(null, pText, pTitel, 0, ii);

	}

	
/**
 * Mit Hilfe dieser Methode kann eine Zeichenkette als txt-Datei gespeichert werden.
 * Der Speicherort ist der Speicherort der eigenen Klasse.
 * @param pDatei ist der Dateiname, der um txt ergänzt wird.
 * @param pstr ist die Zeichenkette.
 */
	public static void str2txt(String pDatei, String pstr) {
		String s;
		try {
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(pDatei + ".txt")));
			for (int i = 0; i < pstr.length(); i++) {
				s = "" + pstr.charAt(i);
				System.out.println(s);
				out.write(s, 0, s.length());
				// out.newLine();

			}
			out.close();
		} catch (IOException ex) {
			Dialog.info("Fehler", ex.toString());
		}

	}
	
	/**
	 * Diese Methode importiert eine txt-Datei als String.
	 * @param Dateiname bzw. Pfad der txt-Datei
	 * @return String des Dateiinhaltes
	 */
	
	public static String txt2str(String Dateiname){
		 Scanner sc=null;
			File f= new File(Dateiname);
			String ausgabe="";
			try{
				sc= new Scanner(f);
			}catch(FileNotFoundException e){
				Dialog.fehler("Fehler"," Datei wurde nicht gefunden!");
			}
			while (sc.hasNextLine()){
				ausgabe=ausgabe+sc.nextLine();
			}
			return ausgabe;
		}

/**
 * Mit Hilfe dieser Methode kann eine Zeichenkette als txt-Datei gespeichert werden.
 * Der Dateiname ist "sicherung" ergänzt um dei Uhrzeit und ".txt"
 * Der Speicherort ist der Speicherort der eigenen Klasse.
 * @param pstr ist die zu speichende Zeichenkette.
 */
	public static void str2txt(String pstr) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(new Date());
		String zeit = calendar.get(Calendar.HOUR_OF_DAY) + ""
				+ calendar.get(Calendar.MINUTE) + calendar.get(Calendar.SECOND);
		String s;
		try {
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream("sicherung" + zeit + ".txt")));
			for (int i = 0; i < pstr.length(); i++) {
				s = "" + pstr.charAt(i);
				System.out.println(s);
				out.write(s, 0, s.length());
				// out.newLine();

			}
			out.close();
		} catch (IOException ex) {
			Dialog.info("Fehler", ex.toString());
		}

	}
/**
 * Das Dialogfenster enthält eine Listauswahl mit den Einträgen
 * neu,öffnen,speichern,einfügen,löschen und beenden.
 * @param pTitel ist der Titel des Dialogfensters.
 * @return Die Methode gibt als Zeichenkette das gewählte Element zurück.
 */
	public static String dateiMenue(String pTitel) {
		String[] menue;
		menue = new String[6];
		menue[0] = "neu";
		menue[1] = "öffnen";
		menue[2] = "speichern";
		menue[3] = "einfügen";
		menue[4] = "löschen";
		menue[5] = "beenden";
		JComboBox auswahl = new JComboBox(menue);
		JOptionPane
				.showMessageDialog(null, auswahl, pTitel, 0, new ImageIcon());
		return (String) auswahl.getSelectedItem();

	}
/**
 * Das Dialogfenster enthält eine Listauswahl mit den Einträgen,
 * die als Feld von Zeichenketten übergeben worden sind. 
 * @param pTitel ist der Titel des Dialogfensters.
 * @param item ist das String-Feld mit den Einträgen.
 * @return Die Methode gibt als Zeichenkette das gewählte Element zurück.
 */
	public static String auswahl(String pTitel, String[] item) {
		JComboBox auswahl = new JComboBox(item);
		auswahl.setEditable(true);
		JOptionPane
				.showMessageDialog(null, auswahl, pTitel, 0, new ImageIcon());
		return (String) auswahl.getSelectedItem();

	}
/**
 * Das Dialogfenster enthält eine Listauswahl mit 5 Einträgen,
 * die als Strings bei item0 bis item4 übergeben worden sind
 * @param pTitel ist der Titel des Dialogfensters.
 * @param item0 ist das 1. Element.
 * @param item1 ist das 2. Element.
 * @param item2 ist das 3. Element.
 * @param item3 ist das 4. Element.
 * @param item4 ist das 5. Element.
 * @return
 */
	public static String auswahl(String pTitel, String item0, String item1,
			String item2, String item3, String item4) {
		String[] item = new String[5];
		item[0] = item0;
		item[1] = item1;
		item[2] = item2;
		item[3] = item3;
		item[4] = item4;
		JComboBox auswahl = new JComboBox(item);
		auswahl.setEditable(true);
		JOptionPane
				.showMessageDialog(null, auswahl, pTitel, 0, new ImageIcon());
		return (String) auswahl.getSelectedItem();

	}
/**
 * Öffnet das Dialogfenster zum Öffnen einer Datei.
 * @return Die Methode gibt den vom Anwender gewählten Pfad zur Datei als String zurück.
 */
	public static String oeffnen() {
		try {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showOpenDialog(null);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				return file.getPath();

			}
		} catch (Exception e) {
			return e.toString();

		}
		return null;
	}
	/**
	 * Öffnet das Dialogfenster zum Speichern einer Datei.
	 * @return Die Methode gibt den vom Anwender gewählten Pfad zur Datei als String zurück.
	 */
	public static String speichern() {
		try {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showSaveDialog(null);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				return file.getPath();

			}
		} catch (Exception e) {
			return e.toString();

		}
		return null;

	}
/**
 * Das Dialogfenster enthält ein Textfeld.Die Buchstaben werden aber als Kreise dargestellt.
 * @param pTitel ist der Titel des Dialogfensters.
 * @return Diese Methode gibt das Passwort im Klartext zurück.
 */
	public static String passwort(String pTitel) {
		JPasswordField jpf = new JPasswordField();
		JOptionPane.showMessageDialog(null, jpf, pTitel,
				JOptionPane.QUESTION_MESSAGE);
		return jpf.getText();

	}
/**
 *  Dialogfenster zur Ausgabe einer Information.
 * @param pTitel ist der Titel des Fensters.
 * @param pText ist der Info-Text.
 */
	public static void info(String pTitel, String pText) {
		JOptionPane.showMessageDialog(null, pText, pTitel,
				JOptionPane.INFORMATION_MESSAGE);
	}
/**
 * Dialogfenster zur Ausgabe einer Fehlerinformation.
 * @param pTitel ist der Titel des Fensters.
 * @param pText ist der Info-Text.
 */
	public static void fehler(String pTitel, String pText) {
		Toolkit.getDefaultToolkit().beep();
		JOptionPane.showMessageDialog(null, pText, pTitel,
				JOptionPane.ERROR_MESSAGE);

	}
/**
 * Dialogfenster zur Eingabe eines Textes.
 * @param pTitel ist der Titel des Fensters.
 * @param pText ist der Frage-Text.
 * @return Die Methode gibt den eingegebenen Text als String zurück.
 */
	public static String eingabe(String pTitel, String pText) {
		String wert = JOptionPane.showInputDialog(null, pText, pTitel,
				JOptionPane.QUESTION_MESSAGE);
		if (wert == null) {
			wert = "Abbruch";
		}
		return wert;
	}
	/**
	 * Dialogfenster zur Eingabe einer Ganzzahl (int).
	 * @param pTitel ist der Titel des Fensters.
	 * @param pText ist der Frage-Text.
	 * @return Die Methode gibt die eingegebene Zahl als int zurück.
	 */
	public static int eingabeINT(String pTitel, String pText) {

		try {
			return Integer.parseInt(JOptionPane.showInputDialog(null, pText,
					pTitel, JOptionPane.QUESTION_MESSAGE));

		} catch (Exception e) {
			if (e.toString().endsWith("null")) {
				JOptionPane.showMessageDialog(null,
						"Es wurde auf Abbrechen gedrückt"
								+ " \n Eingabe wird auf 47447 gesetzt!",
						"Keine Eingabe!", JOptionPane.ERROR_MESSAGE);
				return 47447;
			} else {
				JOptionPane.showMessageDialog(null, e, "Typabweichung!",
						JOptionPane.ERROR_MESSAGE);
			}
		}
		return eingabeINT(pTitel, pText);
	}
	/**
	 * Dialogfenster zur Eingabe einer Komma-Zahl (double).
	 * Diese Zahlen müssen mit einem Punkt eingegeben werden.
	 * @param pTitel ist der Titel des Fensters.
	 * @param pText ist der Frage-Text.
	 * @return Die Methode gibt die eingegebene Zahl als double zurück.
	 */
	public static double eingabeDOUBLE(String pTitel, String pText) {
		try {
			return Double.parseDouble((JOptionPane.showInputDialog(null, pText,
					pTitel, JOptionPane.QUESTION_MESSAGE)));

		} catch (java.lang.NullPointerException e0) {

			JOptionPane.showMessageDialog(null,
					"Es wurde auf Abbrechen gedrückt"
							+ " \n Eingabe wird auf 47.447 gesetzt!",
					"Keine Eingabe!", JOptionPane.ERROR_MESSAGE);
			return 47.447;
		} catch (Exception e1) {
			if (e1.toString().endsWith("ring")) {
				JOptionPane.showMessageDialog(null,
						"Bitte machen Sie eine Eingabe!", "Typabweichung!",
						JOptionPane.ERROR_MESSAGE);
			} else {
				JOptionPane
						.showMessageDialog(
								null,
								"String ist kein Double. \nDouble werden übrigens mit einem Punkt geschrieben!",
								"Typabweichung!", JOptionPane.ERROR_MESSAGE);

			}
		}
		return eingabeDOUBLE(pTitel, pText);

	}
	
	/**
	 * Dialogfenster zur Eingabe eines Buchstaben (char).
	 * Es sollte nur ein Buchstabe eingegeben werden. 
	 * Bei mehreren Buchstaben wird der erste Buchstabe genommen.
	 * @param pTitel ist der Titel des Fensters.
	 * @param pText ist der Frage-Text.
	 * @return Die Methode gibt den Buchstaben zurück.
	 */
	public static char eingabeCHAR(String pTitel, String pText) {
		
		try {
		return JOptionPane.showInputDialog(null, pText,
					pTitel, JOptionPane.QUESTION_MESSAGE).charAt(0);

		} catch (java.lang.NullPointerException e0) {

			JOptionPane.showMessageDialog(null,
					"Es wurde auf Abbrechen gedrückt"
							+ " \n Eingabe wird auf A gesetzt!",
					"Keine Eingabe!", JOptionPane.ERROR_MESSAGE);
			return 'A';
		} catch (Exception e1) {
			if (e1.toString().endsWith("ring")) {
				JOptionPane.showMessageDialog(null,
						"Bitte machen Sie eine Eingabe!", "Typabweichung!",
						JOptionPane.ERROR_MESSAGE);
			}
		}
		return eingabeCHAR(pTitel, pText);

	}
	/**
	 * Dialogfenster zur Eingabe einer Ja/Nein Entscheidung.
	 * @param pTitel ist der Titel des Fensters.
	 * @param pText ist der Frage-Text.
	 * @return Die Methode gibt Entscheidung als booleschen Wert (true/false) zurück.
	 */
	public static boolean entscheidung(String pTitel, String pText) {
		int eingabe = JOptionPane.showConfirmDialog(null, pText, pTitel,
				JOptionPane.YES_NO_OPTION);
		if (eingabe == JOptionPane.YES_OPTION) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Das Dialogfenster soll eigentlich ein Textfeld mit Rollbalken enthalten.
	 * @param pTitel ist der Titel des Dialogfensters.
	 * @param pText ist der Text im Textfeld.
	 */
	
	public static void vielText(String pTitel, String pText) {
		JTextArea jta = new JTextArea(30,60);
		jta.setLineWrap(true);
		jta.setWrapStyleWord(true);
		jta.setText(pText);
		jta.setEditable(false);
		JScrollPane jsp = new JScrollPane(jta);
		jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED); 
		jsp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		JOptionPane.showMessageDialog(null, jsp, pTitel, 0, new ImageIcon());
		}
	
	/**
	 * Die Methode ist nicht statisch.
	 * Dialogfenster zur Eingabe einer Ja/Nein Entscheidung.
	 * @param pTitel ist der Titel des Fensters.
	 * @param pText ist der Frage-Text.
	 * @param pPfad ist der Pfad zum Bild.
	 * @return Die Methode gibt Entscheidung als booleschen Wert (true/false) zurück.
	 */
	public boolean entscheidungMitBild(String pTitel, String pText, String pPfad) {
		ImageIcon ii = new ImageIcon(getClass().getResource(pPfad));
		int eingabe = JOptionPane.showConfirmDialog(null, pText, pTitel,
				JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,ii);
		if (eingabe == JOptionPane.YES_OPTION) {
			return true;
		} else {
			return false;
		}
	}

}
