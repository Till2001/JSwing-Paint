package basiX;

import java.awt.AWTEvent;
import java.awt.event.ActionEvent;

public class KnopfEvent extends AWTEvent {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = -4261829848843438738L;
	private Knopf knopf;
	public KnopfEvent(Knopf k){
		super(k.getSwingComponent(),ActionEvent.ACTION_PERFORMED);
		this.knopf = k;
	}
	public Knopf getKnopf(){
		return knopf;
	}
}
