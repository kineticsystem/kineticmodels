/*
 * ContactFilterPane.java
 *
 * Created on 27 April 2006, 16.16
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
import java.util.*;
import java.util.regex.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

// Apache classes.

import org.apache.commons.logging.*;

// Commons layout.

import org.kineticsystem.commons.layout.*;

// Application classes.

import org.kineticsystem.commons.data.model.*;
import org.kineticsystem.commons.data.model.bean.*;
import org.kineticsystem.commons.data.model.swing.adapters.*;
import org.kineticsystem.commons.random.bean.*;

/**
 * This component is used to show, filter and order a list of contacts.
 * @author Giovanni Remigi
 * $Revision: 20 $
 */
public class ContactFilterPane extends JPanel {
    
    /** Serial version number. */
    private static final long serialVersionUID = 1L;
    
    /** Log framework. */
    private static Log logger = LogFactory.getLog(ContactFilterPane.class);
    
    /* /////////////////////////////////////////////////////////////////////////
     * Private components.
     */
    
    /** The component containing the filter regular expression. */
    private JTextField filterText;
    
    /** The combobox to select the contact property to filter. */
    private JComboBox filterCombo;
    
    /** The button to execute a filter. */
    private JButton filterButton;
    
    /** The table showing contacts. */
    private JTable table;
    
    /** The table model. */
    private TableModelAdapter tableModel;
    
    private TableRowSorter<TableModel> sorter;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constructors.
     */
    
    /**
     * Default constructor.
     * @param contacts The list of contacts to sort and filter.
     */
    public ContactFilterPane(ActiveList<RandomContact> source) {
        
//      source = new SortedList<RandomContact>(source);
//      ((SortedList<RandomContact>) source).setComparator(new BeanComparator("name"));

        
        // Light table format.
        
        TableStructure lightTableFormat = new BeanTableStructure(
            new String[] {null, null, null, null},
            new String[] {"Name", "Surname", "Age", "Email"});

        TableCellRenderer[] lightTableRenderers = new TableCellRenderer[] {
            new ColorTableCellRenderer("name", null),
            new ColorTableCellRenderer("surname", null),
            new ColorTableCellRenderer("age", null),
            new ColorTableCellRenderer("email", null)
        };
        
        Comparator[] lightTableComparators = new Comparator[] {
            new BeanComparator("name"),
            new BeanComparator("surname"),
            new BeanComparator("age"),
            new BeanComparator("email")
        };
        
        String lightTableMessage = "Show name, surname, age and email";
        
        // Full table format.
        
        TableStructure fullTableFormat = new BeanTableStructure(
            new String[] {null, null, null, null, null, null},
            new String[] {"Name", "Surname", "Address", "Country",
                "Continent", "Email"});

        TableCellRenderer[] fullTableRenderers = new TableCellRenderer[] {
            new ColorTableCellRenderer("name", null),
            new ColorTableCellRenderer("surname", null),
            new ColorTableCellRenderer("address", null),
            new ColorTableCellRenderer("country", null),
            new ColorTableCellRenderer("continent", null),
            new ColorTableCellRenderer("email", null)
        };
        
        Comparator[] fullTableComparators = new Comparator[] {
            new BeanComparator("name"),
            new BeanComparator("surname"),
            new BeanComparator("address"),
            new BeanComparator("country"),
            new BeanComparator("continent"),
            new BeanComparator("email")
        };
        
        String fullTableMessage = "Show name, surname, address, country, "
            + "continent and email";
        
        // Table model and table view.
        
        tableModel = new TableModelAdapter(source, lightTableFormat);
        
        table = new JTable(tableModel);
        sorter = new TableRowSorter<TableModel>(tableModel);
        for (int i = 0; i< lightTableComparators.length; i++) {
            sorter.setComparator(i, lightTableComparators[i]);
            table.getColumnModel().getColumn(i)
                .setCellRenderer(lightTableRenderers[i]);
        }
        table.setRowSorter(sorter);

        JScrollPane tableScrollPane = new JScrollPane(table,
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        // Table structure selector.
        
        DefaultComboBoxModel tableFormatComboModel = new DefaultComboBoxModel(
            new TableModeItem[] {
                new TableModeItem(lightTableMessage, lightTableFormat,
                    lightTableRenderers, lightTableComparators),
                new TableModeItem(fullTableMessage, fullTableFormat, 
                    fullTableRenderers, fullTableComparators)
        });
        
        JComboBox formatCombo = new JComboBox(tableFormatComboModel);
        formatCombo.setToolTipText("Select a format.");
        formatCombo.addActionListener(new FormatComboActionListener());
        
        // Table filter selector.
 
        DefaultComboBoxModel filterComboModel = new DefaultComboBoxModel(
            new FilterComboItem[] {
                new FilterComboItem("Address", "address"),
                new FilterComboItem("Country", "country"),
                new FilterComboItem("Email", "email"),
                new FilterComboItem("Name", "name"),
                new FilterComboItem("Surname", "surname")
        });
        
        filterCombo = new JComboBox(filterComboModel);
        filterCombo.setToolTipText("Select a filter.");
        filterCombo.setSelectedIndex(3);
        
        filterText = new JTextField("");
        filterText.setToolTipText("Type a regular expression.");
        filterText.getDocument().addDocumentListener(
            new FilterDocumentListener());
        
        filterButton = new JButton("Filter");
        filterButton.setToolTipText("Press to filter.");
        filterButton.addActionListener(new FilterButtonActionListener());
        
        // Layout.
        
        Cell cell = new Cell();
        TetrisLayout filterLayout = new TetrisLayout(1, 3);
        filterLayout.setColWeight(0, 100);
        filterLayout.setColWeight(1, 0);
        filterLayout.setColWeight(2, 0);
        
        JPanel filterPane = new JPanel(filterLayout);
        filterPane.add(filterText, cell);
        filterPane.add(filterCombo, cell);
        filterPane.add(filterButton, cell);
        
        TetrisLayout tableLayout = new TetrisLayout(3, 1);
        tableLayout.setRowWeight(0, 0);
        tableLayout.setRowWeight(1, 0);
        tableLayout.setRowWeight(2, 100);
        
        setLayout(tableLayout);
        add(filterPane, cell);
        add(formatCombo, cell);
        add(tableScrollPane, cell);
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * Controller inner classes.
     */
    
    /** This class manages format selections throw the combo box. */
    class FormatComboActionListener implements ActionListener {
        
        public void actionPerformed(ActionEvent e) {
           JComboBox cb = (JComboBox) e.getSource();
           TableModeItem item = (TableModeItem) cb.getSelectedItem();
           
           tableModel.setStructure(item.structure);
           
           sorter  = new TableRowSorter<TableModel>(tableModel);
           for (int i = 0; i< item.comparators.length; i++) {
               table.getColumnModel().getColumn(i).setCellRenderer(item
                   .renderers[i]);
               sorter.setComparator(i, item.comparators[i]);
           }
           table.setRowSorter(sorter);
        } 
    }
    
    /** This is the controller to validate a filter when entered. */
    class FilterDocumentListener implements DocumentListener {
        
        public void changedUpdate(DocumentEvent e) {
            validate(); 
        }

        public void insertUpdate(DocumentEvent e) {
            validate();
        }

        public void removeUpdate(DocumentEvent e) {
            validate();
        }

        private void validate() {
            String regExp = filterText.getText();
            boolean isValid = true;
            try {
                Pattern.compile(regExp);
            } catch (PatternSyntaxException ex) {
                isValid = false;
            }
            filterButton.setEnabled(isValid);
        }
    }
    
    /** This is the controller of the filter button. */
    class FilterButtonActionListener implements ActionListener {
        
        public void actionPerformed(ActionEvent evt) {
            String property = ((FilterComboItem) filterCombo.getSelectedItem())
                .value;
            
            Filter filter = new Filter(property, filterText.getText());
            sorter.setRowFilter(filter);
        }
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * Service inner classes.
     */
    
    /** This is used to aggregate and store information about a filter item. */
    private class FilterComboItem {
        
        public String name;
        public String value;
        
        public FilterComboItem(String name, String value) {
            this.name = name;
            this.value = value;
        }
        
        public String toString() {
            return name;
        }
    }
    
    /** This class is used to switch between table structures. */
    private class TableModeItem {
        
        private String name;
        private TableStructure structure;
        private TableCellRenderer[] renderers;
        private Comparator[] comparators;
        
        public TableModeItem(String name, TableStructure structure,
                TableCellRenderer[] renderers, Comparator[] comparators) {
            this.name = name;
            this.structure = structure;
            this.renderers = renderers;
            this.comparators = comparators;
        }
        
        public String toString() {
            return name;
        }
    }
    
    /** Filter used to filter table names. */
    private class Filter extends RowFilter<Object,Integer> {
        
        /** Filter using reflection. */
        private BeanRegExpFilter beanFilter;
        
        /**
         * Constructor.
         * @param regex Regular expression to be used.
         */
        public Filter(String property, String regex) {
            beanFilter = new BeanRegExpFilter(property, regex);
        }
        
        /**
         * Exclude or include rows.
         * @param entry The current row to be filter.
         */
        public boolean include(RowFilter.Entry<? extends Object, 
                ? extends Integer> entry) {
            
            boolean result = false;
            
            try {
                result = beanFilter.evaluate(entry.getValue(0));
            } catch (Exception ex) {
                logger.error(ex);
            }
            return result;
        }
    }
}