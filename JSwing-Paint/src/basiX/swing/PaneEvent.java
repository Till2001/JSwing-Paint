package basiX.swing;

import java.util.*;

/**
 * Copyright (c) 2009
 * @author Georg Dick
 * @version 1.0
 *  wird erzeugt, wenn PaneCanvas neu gezeichnet (paint) wurde 
 *  */
public class PaneEvent extends EventObject {

	

/** wird aufgerufen, wenn PaneCanvas neu gezeichnet (paint) wurde */
  public PaneEvent(Object source) {
     super(source);
  }

}