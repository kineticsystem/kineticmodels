/*
 * HorizontalPackPane.java
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
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.EmptyBorder;

// Application classes.

import org.kineticsystem.commons.layout.Cell;
import org.kineticsystem.commons.layout.TetrisLayout;

/**
 * This is a test dialog showing an horizontal pack evaluation.
 * @author Giovanni Remigi
 * $Revision: 41 $
 */
public class HorizontalPackPane extends JPanel {
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constants.
     */
    
    /** Class serial version number. */
    private static final long serialVersionUID = 1L;
    
    /** Unit size for component creation. */
    private static final int UNIT = 30;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constructors.
     */
    
    /** Default constructor. */
    public HorizontalPackPane() {

        // Create the layout
        
        TetrisLayout layout = new TetrisLayout(2, 4);
        
        // Setup the layout.
        
        layout.setGap(5);
        setLayout(layout); 
        
        // Create components.
        
        JToggleButton button1 = new JToggleButton("A");
        button1.setPreferredSize(new Dimension(2 * UNIT, 1 * UNIT));
        JToggleButton button2 = new JToggleButton("B");
        button2.setPreferredSize(new Dimension(4 * UNIT, 1 * UNIT));
        JToggleButton button3 = new JToggleButton("C");
        button3.setPreferredSize(new Dimension(2 * UNIT, 1 * UNIT));
        JToggleButton button4 = new JToggleButton("D");
        button4.setPreferredSize(new Dimension(2 * UNIT, 1 * UNIT));
        JToggleButton button5 = new JToggleButton("E");
        button5.setPreferredSize(new Dimension(2 * UNIT, 1 * UNIT));
        JToggleButton button6 = new JToggleButton("F");
        button6.setPreferredSize(new Dimension(10 * UNIT, 1 * UNIT));

        // Add components to the layout.
       
        Cell cell = new Cell();
        cell.setFill(Cell.NONE);
        
        cell.setRows(1);
        cell.setCols(1);
        add(button1, cell);
        
        cell.setRows(1);
        cell.setCols(1);
        add(button2, cell);

        cell.setRows(1);
        cell.setCols(1);
        add(button3, cell);
        
        cell.setRows(1);
        cell.setCols(1);
        add(button4, cell);
        
        cell.setRows(1);
        cell.setCols(1);
        add(button5, cell);
        
        cell.setRows(1);
        cell.setCols(Cell.MAX_VALUE);
        add(button6, cell);
        
        setBorder(new EmptyBorder(5, 5, 5, 5));
    }
}
