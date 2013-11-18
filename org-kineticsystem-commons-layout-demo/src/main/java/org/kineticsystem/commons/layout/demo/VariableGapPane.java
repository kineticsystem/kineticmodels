/*
 * BorderLayoutPane.java
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
 * Test cell gaps in a grid layout.
 * @author Giovanni Remigi
 * $Revision: 41 $
 */
public class VariableGapPane extends JPanel {
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constants.
     */
    
    /** Class serial version number. */
    private static final long serialVersionUID = 1L;
    
    /** The grid size. */
    private static int GRID_SIZE = 4;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constructors.
     */
    
    /** Default constructor. */
    public VariableGapPane() {
        
        // Create the layout.
        
        TetrisLayout layout = new TetrisLayout(GRID_SIZE, GRID_SIZE);
        
        // Setup the layout.
        
        layout.setGap(5);
        for (int i = 0; i < GRID_SIZE; i++) {
            layout.setRowWeight(i, 1);
            layout.setColWeight(i, 1);
        }
        layout.setHorizontalGap(1, 20);
        layout.setHorizontalGap(GRID_SIZE - 1, 20);
        layout.setVerticalGap(1, 20);
        layout.setVerticalGap(GRID_SIZE - 1, 20);
        
        setLayout(layout);    
        
        // Define constraints.    
            
        Cell cell = new Cell();
        
        // Add components to the layout.
        
        for (int i = 0; i < (GRID_SIZE * GRID_SIZE); i++) {
            JToggleButton button = new JToggleButton();
            button.setText("Button" + i);
            add(button, cell);
        }
        
        setBorder(new EmptyBorder(5, 5, 5, 5));
    }
}
