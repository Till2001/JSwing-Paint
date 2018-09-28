/**
 * TextFeldLauscher 
 * Anmeldung/Abmeldung über setzeTextFeldLauscher/entferneTextFeldLauscher
 * bei TextFeld-Objekten
 */package basiX;

import java.util.*;
/**
 * TextFeldLauscher 
 * Anmeldung/Abmeldung über setzeTextFeldLauscher/entferneTextFeldLauscher
 * bei TextFeld-Objekten
 */
public interface TextFeldLauscher extends EventListener {
    /**
     * Antwort auf ReturnGedrueckt-Ereignis für TextFeldLauscher.
     * Das ReturnGedrueckt-Ereignis wird ausgelöst, nachdem der Benutzer
     * die Return-Taste gedrückt hat. Das Textfeld gibt den Fokus an das nächste Oberflaechenelement ab.
     */
    public void bearbeiteReturnGedrueckt(TextFeld t);

    /**
     * Antwort auf TextVeraenderung-Ereignis für TextFeldLauscher. Das TextVeraenderung-Ereignis wird ausgelöst, wenn der Text
     * in der Textzeile durch den Benutzer verändert wurde
     */
    public void bearbeiteTextVeraenderung(TextFeld t);
    /**
     * Antwort auf FokusVerloren-Ereignis für TextFeldLauscher. Das FokusVerloren-Ereignis wird ausgelöst, wenn das Textfeld den Fokus abgibt.
     */
    public void bearbeiteFokusVerloren(TextFeld t);
}
