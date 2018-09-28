package basiX;

public interface ReglerLauscher {
	/**
     * Antwort auf ReglerBewegung-Ereignis für ReglerLauscher.
     * Das GleiterBewegung-Ereignis wird ausgelöst, wenn der eingestellte Reglerwert mit Hilfe der Maus verändert wurde
     */
    public void bearbeiteReglerBewegung(Regler r);
}
