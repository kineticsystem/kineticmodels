/*
 * Connector.java
 *
 * Created on June 9, 2005, 6:18 PM
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
 * Implement this interface if you want to calculate the preferred size of a
 * column or row based on the preferred size of some other columns/rows and
 * gaps. This can be useful if the preferred size of a column/row is zero and
 * you don't want to use absolute values to redefine it.
 * @author Giovanni Remigi
 * $Revision: 7 $
 */
public interface Connector {
    
    /**
     * Calculate the preferred size of a column/row.
     * @param sizes An array containig all the preferred sizes of the layout
     *        columns/rows.
     * @param gaps An array containing all the layout gaps between columns/rows.
     *        The number of gaps is the number of columns/rows plus one.
     */
    public double getSize(double[] sizes, int[] gaps);
    
}
