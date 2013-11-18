/*
 * TableFormat.java
 *
 * Created on 2 March 2006, 21.38
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

package org.kineticsystem.commons.data.model.swing.adapters;

/**
 * This object is basically a formatter used to extract values from a list of
 * objects to be displayed by a table component.
 * @author Giovanni Remigi
 * @version $Revision: 9 $
 */
public interface TableStructure {
    
    /**
     * Get the number of columns to display.
     * @return The number of columns to display.
     * @see javax.swing.table.TableModel
     */
    public int getColumnCount();

    /**
     * Get the name of the column at the given position.
     * @param column The column number.
     * @see javax.swing.table.TableModel
     */
    public String getColumnLabel(int column);
    
    /**
     * Gets the value of the specified field for the specified object. This
     * is the value that will be passed to the editor and renderer for the
     * column. If you have defined a custom renderer, you may choose to return
     * simply the given object.
     * This kind of solution should be considered when a particual field of the
     * given object is used to set a property for the whole row containing the
     * object. In this way you can i.e. set the color of a table row based on
     * a property value of the given object.
     * @param obj The object containing property values to be displaied by
     *     the table component.
     * @param column The column index used to extract a property value from
     *     the given object.
     * @return The value to be displayed by the table component at the given
     *     column index. It could be the given object itself.
     */
    public Object getColumnValue(Object obj, int column);
    
    /**
     * Modified a property of the given object.
     * @param modifiedObject The object to be modified.
     * @param modifiedValue The new property value.
     * @param column The column corresponding to the property to be modified.
     * @return The modified object.
     */
    public Object setColumnValue(Object modifiedObject, Object modifiedValue,
        int column);
}
