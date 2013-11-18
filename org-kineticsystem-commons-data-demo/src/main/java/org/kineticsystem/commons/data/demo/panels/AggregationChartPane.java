/*
 * ContactChartPanel.java
 *
 * Created on 26 aprile 2006, 18.41
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

import javax.swing.*;

// JFreeChartClasses.

import org.jfree.chart.*;
import org.jfree.chart.axis.*;
import org.jfree.chart.plot.*;
import org.jfree.data.category.*;

// Application classes.

import org.kineticsystem.commons.data.demo.aggregator.*;
import org.kineticsystem.commons.data.model.*;
import org.kineticsystem.commons.data.model.mapping.*;
import org.kineticsystem.commons.layout.*;
import org.kineticsystem.commons.random.*;
import org.kineticsystem.commons.random.bean.*;

/**
 * This is a panel with a pie chart showing an aggregated list of contacts.
 * @author Giovanni Remigi
 * $Revision: 26 $
 */
@SuppressWarnings("serial")
public class AggregationChartPane extends JPanel implements ActiveListListener {
    
    /* /////////////////////////////////////////////////////////////////////////
     * Variables.
     */
    
    /** The monitored list of grouped contacts. */
    private GroupMapping<RandomContact> groups;
    
    /** The set of values to be used as X axis. */
    private DefaultCategoryDataset dataset;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constructors.
     */
    
    /**
     * Constructor.
     * @param source This is the source list being modified ate the same time
     *     by many threads.
     */
    public AggregationChartPane(ActiveList<RandomContact> source) {
        
        // Define aggregators.
        
        GroupAggregator[] aggregators = new GroupAggregator[] {
            new ContactsPerContinentAggr(),
            new AvgAgePerContinentAggr(),
            new MaxAgePerContinentAggr(),
            new MinAgePerContinentAggr()
        };
        
        // Aggregator selector.
        
        DefaultComboBoxModel groupComboModel = new DefaultComboBoxModel(
            aggregators);
        final JComboBox groupCombo = new JComboBox(groupComboModel);
        groupCombo.setSelectedIndex(1);
        groupCombo.setToolTipText("Select an aggregation function.");
        
        // Create the dataset.
        
        dataset = new DefaultCategoryDataset();
        List<Country> countries = RandomContactGenerator.getCountries();
        Set<String> continents = new TreeSet<String>();
        
        for (Country country : countries) {
            continents.add(country.getContinent());
        }
        
        for (String continent : continents) {
            dataset.setValue(0, continent, "");
        }
        
        // Define the aggregated list.
        
        groups = new GroupMapping<RandomContact>(source);
        groups.setAggregator(aggregators[0]);
        groups.getTarget().addActiveListListener(this);
        
        // Create the chart.

        JFreeChart chart = ChartFactory.createBarChart(
            "",
            "Continent",
            groups.getAggregator().toString(),
            dataset,
            PlotOrientation.VERTICAL,
            true, // legend?
            true, // tooltips?
            false // URLs?
        );
        final ValueAxis axis = chart.getCategoryPlot().getRangeAxis();
        axis.setAutoRange(true);
        axis.setRange(0, 250);
        
        ChartPanel chartPanel = new ChartPanel(chart);
        
        // Create the selector.
        
        ActionListener groupComboListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               JComboBox cb = (JComboBox) e.getSource();
               GroupAggregator aggr = (GroupAggregator) cb.getSelectedItem();
               groups.setAggregator(aggr);
               axis.setLabel(aggr.toString());
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
        add(chartPanel, cell);
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * ActiveListListener interface implementation.
     */
    
    /** {inheritDoc} */
    public void contentsChanged(ActiveListEvent event) {
        for (Group<RandomContact> group : groups.getTarget()) {
            String continent = group.getItems().get(0).getContinent();
            int value = ((Integer) group.getAggregation()).intValue();
            dataset.setValue(value, continent, "");
        }
    }
}

//        DefaultPieDataset dataset = new DefaultPieDataset();
//        for (Group<RandomContact> group : groups) {
//            String continent = group.getDelegate().getContinent();
//            int value = ((Integer) group.getAggregation()).intValue();
//            dataset.setValue(continent, value);
//        }
//        JFreeChart chart = ChartFactory.createPieChart(
//            groups.getAggregator().toString(),
//            dataset,
//            true, // legend?
//            true, // tooltips?
//            false // URLs?
//        );
