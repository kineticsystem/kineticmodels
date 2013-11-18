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
public class DefaultConnector implements Connector {
    
    /* /////////////////////////////////////////////////////////////////////////
     * Private constants.
     */
    
    /** 
     * Used to set a column/row size equals to the average size of all layout
     * columns/rows.
     */
    public static final int AVERAGE = 0;
    
    /** 
     * Used to set a column/row size equals to the max size of all layout
     * columns/rows.
     */
    public static final int MAX = 1;
    
    /** 
     * Used to set a column/row size equals to the min size of all layout
     * columns/rows.
     */
    public static final int MIN = 2;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Private variables.
     */
    
    /** The evaluation mode. */
    private int evaluation;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constructors.
     */
    
    /**
     * Default constructor.
     * @param evaluation The evaluation mode: possible values are:
     *        <ul>
     *        <li><code>AVERAGE;</code></li>
     *        <li><code>MAX;</code></li>
     *        <li><code>MIN.</code></li>     
     */
    public DefaultConnector(int evaluation) {
        this.evaluation = evaluation;
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
        switch (evaluation) {
            case AVERAGE:
                for(int i = 0; i < sizes.length; i++) {
                    size += sizes[i];
                }
                size = size / (double) sizes.length;
                break;
            case MAX:
                for(int i = 0; i < sizes.length; i++) {
                    size = Math.max(size, sizes[i]);
                }
                break;
            case MIN:
                for(int i = 0; i < sizes.length; i++) {
                    size = Math.min(size, sizes[i]);
                }
                break;
        }
        return size;
    }
}
