package org.kineticsystem.commons.data.demo;

// Java classes.

import java.awt.*;
import java.awt.event.*;

/**
 * This class is used to dispose a window.
 * @author Giovanni Remigi
 * $Revision: 8 $
 */
class WindowTerminator extends WindowAdapter {
    
    /* /////////////////////////////////////////////////////////////////////////
     * Private variable.
     */
    
    /** The window to dispose. */
    private Window window;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constructors.
     */
    
    /** Default constructor. */
    public WindowTerminator(Window window) {
        this.window = window;
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * WindowAdapter methods overriding.
     */
    
    /** 
     * Fired when the main frame is closed.
     * @param e Closing event.
     */
    public void windowClosing(WindowEvent e) {
        if (window != null) {
            window.dispose();
        }
    }
}