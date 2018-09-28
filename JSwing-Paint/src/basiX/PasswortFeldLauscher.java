package basiX;

import java.util.EventListener;

/**
	 * PasswortFeldLauscher 
	 * Anmeldung/Abmeldung über setzePasswortFeldLauscher/entfernePasswortFeldLauscher
	 * bei PasswortFeld-Objekten
	 */
public interface PasswortFeldLauscher extends EventListener{
	
	
	    /**
	     * Antwort auf ReturnGedrueckt-Ereignis für PasswortFeldLauscher.
	     * Das ReturnGedrueckt-Ereignis wird ausgelöst, nachdem der Benutzer
	     * die Return-Taste gedrückt hat. Das PasswortFeld gibt den Fokus an das nächste Oberflaechenelement ab.
	     */
	    public void bearbeiteReturnGedrueckt(PasswortFeld t);

	    /**
	     * Antwort auf TextVeraenderung-Ereignis für PasswortFeldLauscher. Das TextVeraenderung-Ereignis wird ausgelöst, wenn der Text
	     * in der Textzeile durch den Benutzer verändert wurde
	     */
	    public void bearbeiteTextVeraenderung(PasswortFeld t);
	    /**
	     * Antwort auf FokusVerloren-Ereignis für PasswortFeldLauscher. Das FokusVerloren-Ereignis wird ausgelöst, wenn das PasswortFeld den Fokus abgibt.
	     */
	    public void bearbeiteFokusVerloren(PasswortFeld t);
	

}
