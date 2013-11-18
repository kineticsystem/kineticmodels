/*
 * AggregationTablePane.java
 *
 * Created on June 18, 2003, 7:20 PM
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

import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

// Kinetic Models classes.

import org.kineticsystem.commons.data.demo.aggregator.*;
import org.kineticsystem.commons.data.model.*;
import org.kineticsystem.commons.data.model.mapping.*;
import org.kineticsystem.commons.data.model.swing.adapters.*;
import org.kineticsystem.commons.layout.*;
import org.kineticsystem.commons.random.bean.*;

/**
 * @author Giovanni Remigi
 * @version $Revision: 26 $
 */
@SuppressWarnings("serial")
public class AggregationTablePane extends JPanel {
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constructors.
     */
    
    /**
     * Constructor.
     * @param source This is the source list being modified ate the same time
     *     by many threads.
     */
    public AggregationTablePane(ActiveList<RandomContact> source) {
        
        // Aggregators.
        
        GroupAggregator<RandomContact> avgAgePerContinent 
            = new AvgAgePerContinentAggr();
        GroupAggregator<RandomContact> itemsPerContinent 
            = new ContactsPerContinentAggr();
        
        // Table cell renderers.
        
        TableStructure avgAgePerContinentFormat = new BeanTableStructure(
                new String[] {"delegate", null},
                new String[] {"Continent", "Average age"}
        );
        
        TableStructure itemsPerContinentFormat = new BeanTableStructure(
            new String[] {"delegate", null},
            new String[] {"Continent", "Count"}
        );
        
        final TableCellRenderer[] renderers = new TableCellRenderer[] {
            new ColorTableCellRenderer("continent"),
            new ColorTableCellRenderer("aggregation")
        };
        
        // Aggregator selector.
        
        class AggregationItem {
            public GroupAggregator<RandomContact> aggregator;
            public TableStructure structure;
            public AggregationItem(GroupAggregator<RandomContact> aggregator,
                    TableStructure structure) {
                this.aggregator = aggregator;
                this.structure = structure;
            }
            public String toString() {
                return aggregator.toString();
            }
        };
        
        DefaultComboBoxModel groupComboModel = new DefaultComboBoxModel(
            new AggregationItem[] {
                new AggregationItem(avgAgePerContinent,
                    avgAgePerContinentFormat),
                new AggregationItem(itemsPerContinent,
                    itemsPerContinentFormat)
        });
        
        final JComboBox groupCombo = new JComboBox(groupComboModel);
        groupCombo.setSelectedIndex(1);
        groupCombo.setToolTipText("Select an aggregation function.");
        
        // View.
        
        final GroupMapping<RandomContact> groupedContacts
            = new GroupMapping<RandomContact>(source);
        groupedContacts.setAggregator(itemsPerContinent);
        
        final TableModelAdapter groupedTableModel = new TableModelAdapter(
            groupedContacts.getTarget(), itemsPerContinentFormat);
        
        final JTable jGroupedTable = new JTable(groupedTableModel);
        for (int i = 0; i< renderers.length; i++) {
            jGroupedTable.getColumnModel().getColumn(i)
                .setCellRenderer(renderers[i]);
        }
        
        JScrollPane groupedTableScrollPane = new JScrollPane(jGroupedTable,
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        ActionListener groupComboListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               JComboBox cb = (JComboBox) e.getSource();
               AggregationItem item = (AggregationItem) cb.getSelectedItem();
               groupedTableModel.setStructure(item.structure);
               groupedContacts.setAggregator(item.aggregator);
               for (int i = 0; i< renderers.length; i++) {
                   jGroupedTable.getColumnModel().getColumn(i)
                       .setCellRenderer(renderers[i]);
               }
            }
        };
        groupCombo.addActionListener(groupComboListener);
        
        // Layout the GUI.
        
        Cell cell = new Cell();
        
        TetrisLayout groupTableLayout = new TetrisLayout(2, 1);
        groupTableLayout.setRowWeight(0, 0);
        groupTableLayout.setRowWeight(1, 100);
        
        setLayout(groupTableLayout);
        add(groupCombo, cell);
        add(groupedTableScrollPane, cell);
    }
}