/*
 * LayoutTest.java
 *
 * Created on February 8, 2004, 2:53 PM
 *
 * Copyright (C) 2004 Remigi Giovanni
 * g.remigi@kineticsystem.org
 * www.kineticsystem.org
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any
 * later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License 
 * along with this program; if not, write to the Free Software Foundation, Inc.,
 * 675 Mass Ave, Cambridge, MA 02139, USA.
 */

package org.kineticsystem.commons.layout.demo;

// Java classes.

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.WindowConstants;

/**
 * Test class showing different layout capabilities.
 * @author Giovanni Remigi
 * $Revision: 41 $
 */
public class LayoutTest {
    
    /* /////////////////////////////////////////////////////////////////////////
     * Main method.
     */
    
    /**
     * The main method.
     * @param args the command line arguments.
     */
    public static void main(String[] args) {
        
        // Create the main window.
        
        DialogChooser chooser = new DialogChooser();
        chooser.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        chooser.pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension size = chooser.getPreferredSize();
        chooser.setLocation(screenSize.width/2 - (size.width/2),
            screenSize.height/2 - (size.height/2));
        
        chooser.setResizable(false);
        chooser.setVisible(true);
    }
}
