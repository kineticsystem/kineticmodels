/*
 * UniqueListPane.java
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

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

// Kinetic Models classes.

import org.kineticsystem.commons.data.model.*;
import org.kineticsystem.commons.data.model.mapping.GroupAggregator;
import org.kineticsystem.commons.data.model.mapping.GroupMapping;
import org.kineticsystem.commons.data.model.swing.adapters.*;
import org.kineticsystem.commons.data.view.renderer.*;
import org.kineticsystem.commons.random.bean.*;

/**
 * This is a pane with a list containing only unique elements obtained from the
 * main active list.
 * @author Giovanni Remigi
 * @version $Revision: 20 $
 */
@SuppressWarnings("serial")
public class UniqueListPane extends JPanel {

    /* /////////////////////////////////////////////////////////////////////////
     * Variables.
     */
    
    /**
     * This is the adapter to adapt the <tt>ActiveList</tt> to the
     * <tt>ListModel</tt> interface.
     */
    private ListModelAdapter listModel;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constructors.
     */
    
    /**
     * Constructor.
     * @param source This is the source list being modified ate the same time
     *     by many threads.
     */
    public UniqueListPane(ActiveList<RandomContact> source) {
        
        ContactDefaultAggregator nameAggregator
            = new ContactDefaultAggregator("name");
        ContactDefaultAggregator surnameAggregator
            = new ContactDefaultAggregator("surname");
        ContactDefaultAggregator countryAggregator
            = new ContactDefaultAggregator("country");
        
        class UniqueAggregator {
            public GroupAggregator<RandomContact> aggregator;
            public String label;
            public ListCellRenderer renderer;
            public UniqueAggregator(GroupAggregator<RandomContact> aggregator,
                    String label, ListCellRenderer renderer) {
                this.aggregator = aggregator;
                this.label = label;
                this.renderer = renderer;
            }
            public String toString() {
                return label;
            }
        };
        
        // List model and list view.
        
        final GroupMapping<RandomContact> uniqueContacts
            = new GroupMapping<RandomContact>(source);
        uniqueContacts.setAggregator(nameAggregator);
        
        listModel = new ListModelAdapter(uniqueContacts.getTarget());
        final JList view = new JList(listModel);
        
        JScrollPane listScrollPane = new JScrollPane(view,
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        // List format selector.
        
        DefaultComboBoxModel listFormatComboModel = new DefaultComboBoxModel(
            new UniqueAggregator[] {
                new UniqueAggregator(nameAggregator, "Show unique names", 
                    new BeanListCellRenderer("delegate.name")),
                new UniqueAggregator(surnameAggregator, "Show unique surnames", 
                    new BeanListCellRenderer("delegate.surname")),
                new UniqueAggregator(countryAggregator, "Show unique countries",  
                    new BeanListCellRenderer("delegate.country"))
            }
        );
        view.setCellRenderer(new BeanListCellRenderer("delegate.name"));
        
        ActionListener listComboListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               JComboBox cb = (JComboBox) e.getSource();
               UniqueAggregator item = (UniqueAggregator) cb.getSelectedItem();
               uniqueContacts.setAggregator(item.aggregator);
               view.setCellRenderer(item.renderer);
            } 
        };
        JComboBox listFormatCombo = new JComboBox(listFormatComboModel);
        listFormatCombo.setToolTipText("Select a format.");
        listFormatCombo.addActionListener(listComboListener);
        
        // Setup the GUI.
        
        setLayout(new BorderLayout());
        add(listFormatCombo, BorderLayout.PAGE_START);
        add(listScrollPane, BorderLayout.CENTER);
    }
}