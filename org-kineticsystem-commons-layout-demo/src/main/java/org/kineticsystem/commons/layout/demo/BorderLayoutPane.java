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
 * This is a simulation of a <code>BorderLayout</code>.
 * @author Giovanni Remigi
 * $Revision: 41 $
 */
public class BorderLayoutPane extends JPanel {
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constants.
     */
    
    /** Class serial version number. */
    private static final long serialVersionUID = 1L;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constructors.
     */
    
    /** Default constructor. */
    public BorderLayoutPane() {
        
        // Create the layout.
        
        TetrisLayout layout = new TetrisLayout(3, 3);
        
        // Setup the layout.
        
        layout.setGap(2);
        layout.setRowWeight(0, 0);
        layout.setRowWeight(1, 1);
        layout.setRowWeight(2, 0);
        layout.setColWeight(0, 0);
        layout.setColWeight(1, 1);
        layout.setColWeight(2, 0);
        layout.setColConnector(1, new RatioConnector(new int[] {0, 2},
            new double[] {2, 2}));
        layout.setRowConnector(1, new RatioConnector(new int[] {0, 2},
            new double[] {2, 2}));
    
        setLayout(layout);    
            
        // Create components.
            
        JToggleButton northButton = new JToggleButton("North");
        JToggleButton westButton = new JToggleButton("West");
        JToggleButton centerButton = new JToggleButton("Center");
        JToggleButton eastButton = new JToggleButton("East");
        JToggleButton southButton = new JToggleButton("South");
        
        // Define constraints.    
            
        Cell cell = new Cell();
        cell.setCols(3);
        
        // Add components to the layout.
        
        add(northButton, cell);
        
        cell.setCols(1);
        add(westButton, cell);
        add(centerButton, cell);
        add(eastButton, cell);
        
        cell.setCols(3);
        add(southButton, cell);
        
        setBorder(new EmptyBorder(5, 5, 5, 5));
    }
}
