package basiX.swing;

import java.util.*;

/**
 * Copyright:     Copyright (c) 2009
 * @author Georg Dick
 * @version 1.0
 *  wird erzeugt, wenn BufferdCanvas neu gezeichnet (paint) wurde 
 *  */
public class BufferedCanvasEvent extends EventObject {

	

/** wird aufgerufen, wenn BufferdCanvas neu gezeichnet (paint) wurde */
  public BufferedCanvasEvent(Object source) {
    super(source);
  }

}