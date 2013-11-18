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
import org.kineticsystem.commons.layout.RatioConnector;
import org.kineticsystem.commons.layout.TetrisLayout;

/**
 * This is a simulation of a grid layout with variable columns and rows.
 * @author Giovanni Remigi
 * $Revision: 41 $
 */
public class VariableGridLayoutPane extends JPanel {
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constants.
     */
    
    /** Class serial version number. */
    private static final long serialVersionUID = 1L;
    
    /** The grid size. */
    private static int GRID_SIZE = 3;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constructors.
     */
    
    /** Default constructor. */
    public VariableGridLayoutPane() {
        
        // Create the layout.
        
        TetrisLayout layout = new TetrisLayout(GRID_SIZE, GRID_SIZE);
        
        // Setup the layout.
        
        layout.setGap(5);
        for (int i = 0; i < GRID_SIZE; i++) {
            layout.setRowWeight(i, 1);
            layout.setColWeight(i, 1);
            layout.setColConnector(i, new RatioConnector(new int[] {0},
                new double[] {(i + 1)}));
        }
        
        setLayout(layout);    
        
        // Define constraints.    
            
        Cell cell = new Cell();
        
        // Add components to the layout.
        
        String[] months = new String[] {
            "January",
            "February",
            "March",
            "April",
            "May",
            "June",
            "July",
            "August",
            "September"
        };
        
        for (int i = 0; i < (GRID_SIZE * GRID_SIZE); i++) {
            JToggleButton button = new JToggleButton();
            button.setText(months[i]);
            add(button, cell);
        }
        
        setBorder(new EmptyBorder(5, 5, 5, 5));
    }
}
