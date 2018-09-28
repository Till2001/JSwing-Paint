package basiX;
/**
 * ListBoxLauscher 
 * Anmeldung/Abmeldung über setzeListBoxLauscher/entferneListBoxLauscher
 * bei ListBox-Objekten
 */
public interface ListBoxLauscher {
    /**
     * Antwort auf ListBoxAuswahl-Ereignis für ListBoxLauscher. Das ListBoxAuswahl-Ereignis wird ausgelöst, wenn in der Liste
     * durch Mausklick oder Return ein Listenelement ausgewählt wurde.
     */
    public void bearbeiteListBoxAuswahl(ListBox l);
}
