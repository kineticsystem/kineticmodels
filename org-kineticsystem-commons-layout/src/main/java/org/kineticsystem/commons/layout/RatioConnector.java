/*
 * RatioConnector.java
 *
 * Created on June 9, 2005, 6:23 PM
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
 * Default implementation of <code>Connector</code> interface.
 * @author Giovanni Remigi
 * $Revision: 7 $
 */
public class RatioConnector implements Connector {
    
    /* /////////////////////////////////////////////////////////////////////////
     * Private variables.
     */
    
    /** The set of column/row size used in the evaluation. */
    private int[] indexes;
    
    /** The set of column/row size percentages used in the evaluation. */
    private double[] ratios;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constructors.
     */
    
    public RatioConnector(int index) {
        this.indexes = new int[] {index};
        this.ratios = new double[] {1};
    }
    
    public RatioConnector(int index, double ratio) {
        this.indexes = new int[] {index};
        this.ratios = new double[] {ratio};
    }
    
    public RatioConnector(int[] indexes) {
        this.indexes = indexes;
        this.ratios = new double[indexes.length];
        for(int i = 0; i < indexes.length; i++) {
            ratios[i] = 1;
        }     
    }
    
    public RatioConnector(int[] indexes, double[] ratios) {
        this.indexes = indexes;
        this.ratios = ratios;
    }

    /* /////////////////////////////////////////////////////////////////////////
     * Connector interface implementation.
     */
    
    /**
     * Calculate the preferred size of a column/row.
     * @param sizes An array containig all the preferred sizes of the layout
     *        columns/rows.
     * @param gaps An array containing all the layout gaps between columns/rows.
     *        The number of gaps is the number of columns/rows plus one.
     */
    public double getSize(double[] sizes, int[] gaps) {
        double size = 0;
        for(int i = 0; i < indexes.length; i++) {
            size += sizes[indexes[i]] * ratios[i];
        }
        return size;
    }
}
