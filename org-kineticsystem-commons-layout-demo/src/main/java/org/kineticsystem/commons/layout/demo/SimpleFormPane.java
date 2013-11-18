/*
 * TestDialog.java
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

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.border.EmptyBorder;

// Application classes.

import org.kineticsystem.commons.layout.Cell;
import org.kineticsystem.commons.layout.RatioConnector;
import org.kineticsystem.commons.layout.TetrisLayout;

/**
 * This is pane simulating a simple form.
 * @author Giovanni Remigi
 * $Revision: 41 $
 */
public class SimpleFormPane extends JPanel {
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constants.
     */
    
    /** Class serial version number. */
    private static final long serialVersionUID = 1L;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constructors.
     */
    
    /** Default constructor. */
    public SimpleFormPane() {
        
        // Create the layout.
        
        TetrisLayout layout = new TetrisLayout(6, 3);
        
        // Setup the layout.
        
        layout.setColWeight(0, 0);
        layout.setColWeight(1, 0);
        layout.setColWeight(2, 1);
        layout.setRowWeight(0, 0);
        layout.setRowWeight(1, 0);
        layout.setRowWeight(2, 0);
        layout.setRowWeight(3, 0);
        layout.setRowWeight(4, 1);
        layout.setRowWeight(5, 0);
        layout.setColConnector(2, new RatioConnector(new int[] {0}, new double[] {3}));
        layout.setRowConnector(4, new RatioConnector(new int[] {0, 1, 2}));
        layout.setGap(5);
        layout.setHorizontalGap(1, 20);
        layout.setVerticalGap(3, 20);
        layout.setVerticalGap(5, 20);
        
        setLayout(layout);
        
        // Add components to the layout.

        Cell cell = new Cell();

        cell.setFill(Cell.BOTH);
        cell.setRows(3);
        cell.setCols(1);
        JToggleButton image = new JToggleButton("Image");
        image.setEnabled(false);
        add(image, cell);
        cell.setFill(Cell.NONE);
        cell.setAnchor(Cell.LINE_END);
        cell.setRows(1);
        cell.setCols(1);
        add(new JLabel("Name:"), cell);
        cell.setFill(Cell.HORIZONTAL);
        JTextField nameText = new JTextField();
        add(nameText, cell);
        cell.setFill(Cell.NONE);
        add(new JLabel("Surname:"), cell);
        cell.setFill(Cell.HORIZONTAL);
        add(new JTextField(), cell);
        cell.setFill(Cell.NONE);
        add(new JLabel("Address:"), cell);
        cell.setFill(Cell.BOTH);
        add(new JTextField(), cell);
        cell.setCols(3);
        cell.setFill(Cell.NONE);
        cell.setAnchor(Cell.LINE_START);
        add(new JLabel("Notes:"), cell);
        cell.setFill(Cell.BOTH);
        add(new JScrollPane(new JTextArea("This is a simple text.")), cell);
        cell.setFill(Cell.NONE);
        cell.setAnchor(Cell.CENTER);
        JButton confirm = new JButton("Confirm");
        confirm.setVisible(true);
        add(confirm, cell);
        
        setBorder(new EmptyBorder(5, 5, 5, 5));
    }
}
