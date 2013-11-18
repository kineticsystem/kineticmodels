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
import org.kineticsystem.commons.layout.DefaultConnector;
import org.kineticsystem.commons.layout.TetrisLayout;

/**
 * This is a simulation of a powerful grid layout.
 * @author Giovanni Remigi
 * $Revision: 41 $
 */
public class VortexGridPane extends JPanel {
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constants.
     */
    
    /** Class serial version number. */
    private static final long serialVersionUID = 1L;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constructors.
     */
    
    /** Default constructor. */
    public VortexGridPane() {
        
        // Create grid layout.
        
        TetrisLayout gridLayout = new TetrisLayout(3, 3);
        setLayout(gridLayout);
        
        // Setup grid layout.
        
        gridLayout.setGap(2);
        for (int i = 0; i < 3; i++) {
            gridLayout.setRowWeight(i, 1);
            gridLayout.setColWeight(i, 1);
            gridLayout.setColConnector(i, new DefaultConnector(DefaultConnector
                .MAX));
        } 
        
        // Define constraints.    
            
        Cell cell = new Cell();
        
        // Add components to grid.
        
        String[] months = new String[] {
            "Button1",
            "Button2",
            "Button3",
            "Button4",
            "Button5"
        };
        
        JToggleButton button;
        
        cell.setRows(1);
        cell.setCols(2);
        button = new JToggleButton();
        button.setText(months[0]);
        add(button, cell);
        
        cell.setRows(2);
        cell.setCols(1);
        button = new JToggleButton();
        button.setText(months[1]);
        add(button, cell);
        
        cell.setRows(2);
        cell.setCols(1);
        button = new JToggleButton();
        button.setText(months[2]);
        add(button, cell);
        
        cell.setRows(1);
        cell.setCols(1);
        button = new JToggleButton();
        button.setText(months[3]);
        add(button, cell);
        
        cell.setRows(1);
        cell.setCols(2);
        button = new JToggleButton();
        button.setText(months[4]);
        add(button, cell);
        
        setBorder(new EmptyBorder(5, 5, 5, 5));
    }
}
