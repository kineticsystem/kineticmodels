/*
 * NavigatorPane.java
 *
 * Created on 12 May 2006, 16.59
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

package org.kineticsystem.commons.data.demo.panels;

// Java classes.

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

// Application classes.

import org.kineticsystem.commons.data.controller.*;
import org.kineticsystem.commons.data.model.*;
import org.kineticsystem.commons.data.model.swing.adapters.*;
import org.kineticsystem.commons.data.view.*;
import org.kineticsystem.commons.layout.*;
import org.kineticsystem.commons.random.bean.*;

/**
 *
 * @author Giovanni Remigi
 * $Revision: 152 $
 */
@SuppressWarnings("serial")
public class NavigatorPane extends JPanel {
    
    private JTable table;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constructors.
     */
    
    /** Default constructor */
    public NavigatorPane(ActiveList<RandomContact> source) {
              
        JDataNavigator jeditor = new JDataNavigator();
        jeditor.setAutoEnabled(true);
        jeditor.setEditingEnabled(true);
        jeditor.setActionEnabled(JDataNavigator.CREATE_ACTION_NAME, false);
        
        ContactEditorPane editorPane = new ContactEditorPane();
        jeditor.getDataNavigator().setEditor(editorPane);
        editorPane.setDataNavigator(jeditor.getDataNavigator());
        
        jeditor.getDataNavigator().setModel(source);
        
        TableStructure format = new BeanTableStructure(
                new String[] {null, null, null, null, null},
                new String[] {"Name", "Surname", "Age", "Email", "Continent"}) {
            
            public String toString() {
                return "Show name, surname and email";
            }
        };

        TableModel model = new TableModelAdapter(source, format);
        
        TableCellRenderer[] renderers = new TableCellRenderer[] {
            new ColorTableCellRenderer("name", null),
            new ColorTableCellRenderer("surname", null),
            new ColorTableCellRenderer("age", null),
            new ColorTableCellRenderer("email", null),
            new ColorTableCellRenderer("continent", null)
        };
        
        table = new JTable(model);
        for (int i = 0; i< renderers.length; i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(renderers[i]);
        }
        
        JScrollPane tableScrollPane = new JScrollPane(table,
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        ListSelectionModel selector = table.getSelectionModel();
        selector.addListSelectionListener(new ListSelectionListener() {
            
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()) {

                    // Scroll the table as selection changes.

                    ListSelectionModel selection = table.getSelectionModel();  
                    int leadSelectionIndex = selection.getLeadSelectionIndex();

                    Rectangle rect = table.getCellRect(leadSelectionIndex, 0,
                        true);
                    table.scrollRectToVisible(rect);
                }
            }
        });
        
        NavigatorListModelMediator mediator = new NavigatorListModelMediator();
        mediator.setNavigator(jeditor.getDataNavigator().getNavigator());
        mediator.addListSelectionModel(selector);

        TetrisLayout layout = new TetrisLayout(3, 1);
        layout.setRowWeight(0, 0);
        layout.setRowWeight(1, 0);
        layout.setRowWeight(2, 100);
        
        setLayout(layout);
        
        Cell cell = new Cell();
        
        add(editorPane, cell);
        add(jeditor, cell);
        add(tableScrollPane, cell);
    }
}