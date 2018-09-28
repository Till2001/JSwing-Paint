package basiX.util;

import java.io.FileWriter;
import java.io.IOException;

import basiX.*;

public class AusfJars implements KnopfLauscher {
	// Deklaration
	private Fenster fenster;
	private BeschriftungsFeld bpUD;
	private BeschriftungsFeld bfBeisp;
	private TextFeld tFDateiname;
	private BeschriftungsFeld bfKPfad;
	private BeschriftungsFeld bfBeispiel_malenpaketStartMalen;
	private BeschriftungsFeld bf3;
	private TextFeld tfmain;
	private BeschriftungsFeld bfbasispfad;
	private BeschriftungsFeld bfbasisexpl;
	private TextFeld tFbpfadexpl;
	private BeschriftungsFeld bfprojektverzeichnis;
	private BeschriftungsFeld bfprojektverzeichnisexpl;
	private TextFeld tFprojektverzeichnis;
	private BeschriftungsFeld bfXML;
	private BeschriftungsFeld bfBeispiel_DSkriptmalenXML;
	private TextFeld tfxml;
	private Knopf kXMLDatei_erzeugen;
	private Knopf kEnde;

	// Konstruktor
	public AusfJars() {
		fenster = new Fenster("ANT-Skript für ausführbare Dateien", 630, 670);
		bpUD = new BeschriftungsFeld(
				"Pfad und Dateiname für das ausführbare Programm", 10, 30, 300,
				30);
		bfBeisp = new BeschriftungsFeld(
				"Beispiel: C:/meineJAVAProgramme/Malen.jar", 330, 30, 290, 30);
		tFDateiname = new TextFeld(10, 70, 610, 40);
		bfKPfad = new BeschriftungsFeld(
				"Klassenpfad und Klassenname der Klasse mit der main-Methode",
				10, 130, 380, 30);
		bfBeispiel_malenpaketStartMalen = new BeschriftungsFeld(
				"Beispiel: malenpaket.StartMalen", 410, 130, 370, 30);
		bf3 = new BeschriftungsFeld(
				"malenpaket ist das Package und StartMalen die Java-Klasse mit der main-Methode",
				10, 160, 480, 30);
		tfmain = new TextFeld(10, 200, 610, 40);
		bfprojektverzeichnis = new BeschriftungsFeld(
				"Pfad zum Projektverzeichnis: Pfad zum Workspace + Name des Projektes", 10, 240, 450, 30);
		bfprojektverzeichnisexpl = new BeschriftungsFeld(
				"Beispiel: D:/workspace/MalenProjekt", 10, 270, 250, 30);
		tFprojektverzeichnis = new TextFeld(10, 310, 610, 40);
		bfbasispfad = new BeschriftungsFeld(
				"Pfad und Dateiname der Basis-Bibliothek", 10, 390, 240, 30);
		bfbasisexpl = new BeschriftungsFeld(
				"Beispiel: D:/eclipse/basis/basis100131.jar", 280, 390, 250, 30);
		tFbpfadexpl = new TextFeld(10, 420, 610, 40);
		bfXML = new BeschriftungsFeld(
				"Pfad und Dateiname des ANT-Skripts mit Endung .XML", 10, 480,
				330, 30);
		bfBeispiel_DSkriptmalenXML = new BeschriftungsFeld(
				"Beispiel: D:/Skript/malen.XML", 340, 480, 240, 30);
		tfxml = new TextFeld(10, 530, 610, 40);
		kXMLDatei_erzeugen = new Knopf("XML-Datei erzeugen", 10, 600, 190, 40);
		kEnde = new Knopf("Ende", 450, 600, 170, 40);
		kXMLDatei_erzeugen.setzeKnopfLauscher(this);
		kEnde.setzeKnopfLauscher(this);
	} // Ende Konstruktor

	

	public void bearbeiteKnopfDruck(Knopf komponente) {
		if (komponente == kXMLDatei_erzeugen) {
			this.bearbeiteKnopfDruckkXMLDatei_erzeugen();
		}
		if (komponente == kEnde) {
			fenster.gibFrei();
		}
	}

	private void bearbeiteKnopfDruckkXMLDatei_erzeugen() {
		String xml="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n";
		xml=xml+"<project default=\"create_run_jar\" name=\"Create Runnable Jar\">\n";
		xml=xml+"    <target name=\"create_run_jar\">\n";
		xml=xml+"        <jar destfile=\""+tFDateiname.text()+"\" filesetmanifest=\"mergewithoutmain\">\n";
		xml=xml+"            <manifest>\n";
		xml=xml+"                <attribute name=\"Main-Class\" value=\""+tfmain.text() +"\"/>\n";
		xml=xml+"                <attribute name=\"Class-Path\" value=\".\"/>\n";
		xml=xml+"            </manifest>\n";
		xml=xml+"            <fileset dir=\""+tFprojektverzeichnis.text()+"/bin+\"/>\n";
		xml=xml+"            <zipfileset excludes=\"META-INF/*.SF\" src=\""+ tFbpfadexpl.text()+"\"/>\n";
		xml=xml+"        </jar>\n";
		xml=xml+"    </target>\n";
		xml=xml+"</project>\n";
		this.speichereAntSkript(tfxml.text(), xml);		
	}
	
	private void speichereAntSkript(String dateiname, String text) {
		FileWriter fw = null;
		if (!dateiname.toLowerCase().endsWith(".xml")){
			dateiname=dateiname+".xml";
		}
		try {
			fw = new FileWriter(dateiname);
			fw.write( text);
		} catch (IOException e) {
		} finally {
			try {
				if (fw != null)
					fw.close();
			} catch (IOException e) {
			}
		}			
	}
	
}// Ende Klasse
