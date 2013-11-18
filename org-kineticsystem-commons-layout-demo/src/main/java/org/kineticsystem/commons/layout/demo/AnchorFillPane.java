/*
 * AnchorFillPane.java
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

import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.EmptyBorder;

// Application classes.

import org.kineticsystem.commons.layout.Cell;
import org.kineticsystem.commons.layout.TetrisLayout;

/**
 * This is a test dialog showing all ways to anchor and fill a component inside
 * a layout cell.
 * @author Giovanni Remigi
 * $Revision: 41 $
 */
public class AnchorFillPane extends JPanel {
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constants.
     */
    
    /** Class serial version number. */
    private static final long serialVersionUID = 1L;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constructors.
     */
    
    /** Default constructor. */
    public AnchorFillPane() {

        // Cell anchor constants.
        
        int[] anchors = new int[] {
            Cell.FIRST_LINE_START,
            Cell.PAGE_START,
            Cell.FIRST_LINE_END,
            Cell.LINE_START,
            Cell.CENTER,
            Cell.LINE_END,
            Cell.LAST_LINE_START,
            Cell.PAGE_END,
            Cell.LAST_LINE_END
        };
        
        // Cell fill constants.
        
        int[] fills = new int[] {
            Cell.NONE,
            Cell.BOTH,
            Cell.VERTICAL,
            Cell.HORIZONTAL,
            Cell.PROPORTIONAL
        };
        
        // Create the layout.
        
        TetrisLayout layout = new TetrisLayout(fills.length, anchors.length);
        
        // Setup the layout.
        
        layout.setGap(2);
        setLayout(layout); 

        // Add components to the layout.

        Cell cell = new Cell();
        int fill = 0;
        int anchor = 0;
        
        for (int i = 0; i < (fills.length * anchors.length); i++) {
           cell.setAnchor(anchors[anchor]);
           cell.setFill(fills[fill]);
           JToggleButton button = new JToggleButton("Test");
           add(button, cell);
           
           anchor++;
           if (anchor == anchors.length) {
               anchor = 0;
               fill++;
           }
        }

        setBorder(new EmptyBorder(5, 5, 5, 5));
    }
}
