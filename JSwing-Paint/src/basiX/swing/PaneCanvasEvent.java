package basiX.swing;

import java.util.*;

/**
 * Copyright:     Copyright (c) 2009
 * @author Georg Dick
 * @version 1.0
 *  wird erzeugt, wenn PaneCanvas neu gezeichnet (paint) wurde 
 *  */
public class PaneCanvasEvent extends EventObject {

	

/** wird aufgerufen, wenn PaneCanvas neu gezeichnet (paint) wurde */
  public PaneCanvasEvent(Object source) {
    super(source);
  }

}