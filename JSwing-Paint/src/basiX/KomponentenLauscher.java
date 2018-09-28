package basiX;

import java.util.EventListener;




	/**
	 * KomponentenLauscher 
	 * Anmeldung/Abmeldung über setzeKomponentenLauscher/entferneKomponentenLauscher
	 * bei Komponenten
	 */
	public interface KomponentenLauscher extends EventListener {

	    /**
	     * Antwort auf KomponentenVeraenderung-Ereignis für KomponentenLauscher. 
	     * Das KomponentenLauscher-Ereignis wird ausgelöst, wenn eine Komponente
	     * verändert wurde
	     */
	    
		public void bearbeiteKomponentenVeraenderung(Komponente komponente);
}
