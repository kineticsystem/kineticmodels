/*
 * Bar.java
 *
 * Created on 10 giugno 2005, 12.29
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

package org.kineticsystem.commons.layout;

/**
 * This class represents a layout colum or row. It is internally used by the
 * <code>TetrisLayout</code> algorithm.
 * @author Giovanni Remigi
 * $Revision: 7 $
 */
class Bar {
    
    /* /////////////////////////////////////////////////////////////////////////
     * public constants.
     */
    
    /**
     * This constant asks the bar to evaluate its size base on the preferred
     * size of the contained components.
     */
    public static final int COMPONENT_PREFERRED_SIZE = -1;
    
    /**
     * This constant asks the bar to evaluate its size base on the minimum
     * size of the contained components.
     */
    public static final int COMPONENT_MINIMUM_SIZE = -2;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Private variables.
     */
    
    /**
     * Set the weight of a column/row. This parameter effects the way a given
     * column/row is resized during the overall resize of the layout.
     */
    private double weight;
    
    /**
     * The initial absolute size of the given column/row. If size equals to
     * <code>COMPONENT_PREFERRED_SIZE</code> than the column/row size is set to
     * the contained component preferred size.
     */
    private double size;
    
    /**
     * The preferred size of a column/row evaluated using contained component
     * preferred size.
     */
    private double preferredSize;
    
    /**
     * The minimum size of a column/row evaluated using contained component
     * minimum size.
     */
    private double minimumSize;
    
    /**
     * The maximum size of a column/row evaluated using contained component
     * maximum size.
     */
    private double maximumSize;
    
    /**
     * This object is used to automatically evauated the initial size of a
     * column/row linked to the size of one or more columns/rows.
     */
    private Connector connector;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constructors.
     */
    
    /** Default constructor. */
    public Bar() {
        weight = 1;
        size = COMPONENT_PREFERRED_SIZE;
        preferredSize = 0;
        minimumSize = 0;
        maximumSize = Double.MAX_VALUE;
        connector = null;
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * Extensions.
     */
    
    /**
     * Return a description of the internal state of the current instance. Very
     * useful in debugging.
     * @return A description of the object.
     */
    public String toString() {
        return super.toString()
            .concat("[")
            .concat("weight=" + weight + ",")
            .concat("size=" + size + ",")
            .concat("minimumSize=" + minimumSize + ",")
            .concat("preferredSize=" + preferredSize + ",")
            .concat("maximumSize=" + maximumSize + "]");
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * Getter and setter methods.
     */
    
    /**
     * Return the weight of a column/row. This parameter effects the way a given
     * column/row is resized during the overall resize of the layout.
     * @return The weight of a column/row.
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Set the weight of a column/row. This parameter effects the way a given
     * column/row is resized during the overall resize of the layout.
     * @param weight The weight of a column/row.
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    /**
     * The preferred size of a column/row evaluated using contained component
     * preferred size.
     * @return The preferred size of a column/row.
     */
    public double getPreferredSize() {
        return preferredSize;
    }

    /**
     * The preferred size of a column/row evaluated using contained component
     * preferred size.
     * @param preferredSize The preferred size of a column/row.
     */
    public void setPreferredSize(double preferredSize) {
        this.preferredSize = preferredSize;
    }

    /**
     * The minimum size of a column/row evaluated using contained component
     * minimum size.
     * @return The minimum size of a column/row.
     */
    public double getMinimumSize() {
        return minimumSize;
    }

    /**
     * The minimum size of a column/row evaluated using contained component
     * minimum size.
     * @param minimumSize The minimum size of a column/row.
     */
    public void setMinimumSize(double minimumSize) {
        this.minimumSize = minimumSize;
    }
    
    /**
     * The maximum size of a column/row evaluated using contained component
     * maximum size.
     * @return The maximum size of a column/row.
     */
    public double getMaximumSize() {
        return maximumSize;
    }

    /**
     * The maximum size of a column/row evaluated using contained component
     * maximum size.
     * @param maximumSize The maximum size of a column/row.
     */
    public void setMaximumSize(double maximumSize) {
        this.maximumSize = maximumSize;
    }
    
    /**
     * Get the <code>Connector</code> used to automatically evauated the initial
     * size of a column/row linked to the size of one or more columns/rows.
     * @return The <code>Connector</code> object used to evaluate the initial
     *         size of a column/row.
     */
    public Connector getConnector() {
        return connector;
    }

    /**
     * Set the <code>Connector</code> used to automatically evauated the initial
     * size of a column/row linked to the size of one or more columns/rows.
     * @param connector The <code>Connector</code> object used to evaluate the
     *        initial size of a column/row.
     */
    public void setConnector(Connector connector) {
        this.connector = connector;
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * Generic methods.
     */
    
    /**
     * Factory method that return an array of initialized objects.
     * @param size The length of the array.
     * @return An array of initialized objects.
     */
    public static Bar[] createArray(int size) {
        Bar[] objs = new Bar[size];
        for (int i = 0; i < size; i++) {
            objs[i] = new Bar();
        }
        return objs;
    }
}
