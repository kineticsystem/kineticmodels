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

import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;

// Application classes.

import org.kineticsystem.commons.layout.Cell;
import org.kineticsystem.commons.layout.DefaultConnector;
import org.kineticsystem.commons.layout.RatioConnector;
import org.kineticsystem.commons.layout.TetrisLayout;

/**
 * This is pane simulating a simple form.
 * @author Giovanni Remigi
 * $Revision: 41 $
 */
public class NestedLayoutPane extends JPanel {
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constants.
     */
    
    /** Class serial version number. */
    private static final long serialVersionUID = 1L;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constructors.
     */
    
    /** Default constructor. */
    public NestedLayoutPane() {
        
        // Create grid layout.
        
        JPanel gridPane = new JPanel();
        TetrisLayout gridLayout = new TetrisLayout(4, 4);
        gridLayout.setGap(2);
        gridLayout.setHorizontalGap(1, 20);
        gridLayout.setHorizontalGap(3, 20);
        gridPane.setLayout(gridLayout);
        
        // Setup grid layout.
        
        for (int i = 0; i < 4; i++) {
            gridLayout.setRowWeight(i, 1);
        }  
        for (int i = 0; i < 4; i++) {
            gridLayout.setColWeight(i, 1);
            gridLayout.setColConnector(i, new DefaultConnector(DefaultConnector
                .MAX));
        }  
        
        // Define constraints.    
            
        Cell cell = new Cell();
        cell.setCols(Cell.MAX_VALUE);
        cell.setFill(Cell.HORIZONTAL);
        gridPane.add(new JTextField("Write something..."), cell);
        
        // Add components to grid.
        
        String[] months = new String[] {
            "January",
            "February",
            "March",
            "April",
            "May",
            "June",
            "July",
            "August",
            "September",
            "October",
            "November",
            "December"
        };
        
        
        cell.setCols(1);
        cell.setFill(Cell.BOTH);
        for (int i = 0; i < months.length; i++) {
           JToggleButton button = new JToggleButton(months[i]);
           gridPane.add(button);
        }
        
        gridPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        
        // Create the form layout.
        
        TetrisLayout formLayout = new TetrisLayout(5, 4);
        setLayout(formLayout);
        
        // Setup form layout.
        
        formLayout.setColWeight(0, 0);
        formLayout.setColWeight(1, 0);
        formLayout.setColWeight(2, 0);
        formLayout.setColWeight(3, 1);
        formLayout.setRowWeight(0, 0);
        formLayout.setRowWeight(1, 0);
        formLayout.setRowWeight(2, 0);
        formLayout.setRowWeight(3, 0);
        formLayout.setRowWeight(4, 1);
        formLayout.setColConnector(0, new RatioConnector(new int[] {1},
            new double[] {2}));
        formLayout.setGap(5);
        formLayout.setHorizontalGap(2, 20);
        formLayout.setVerticalGap(3, 20);
        
        // Add form components.
        
        cell.setFill(Cell.BOTH);
        cell.setRows(5);
        cell.setCols(1);
        JScrollPane scroll = new JScrollPane(
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );
        scroll.setPreferredSize(new Dimension(0, 0));
        scroll.setViewportView(new JTree());
        
        add(scroll, cell);
        
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
        add(new JLabel("Select a month:"), cell);
        cell.setFill(Cell.BOTH);
        add(gridPane, cell);
        
        setBorder(new EmptyBorder(5, 5, 5, 5));
    }
}
