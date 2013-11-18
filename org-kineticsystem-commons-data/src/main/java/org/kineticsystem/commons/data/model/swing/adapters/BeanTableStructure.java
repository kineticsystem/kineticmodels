/*
 * BeanTableFormat.java
 *
 * Created on 31 March 2006, 17.55
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

// Apache commons libraries.

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This object is used to extract values from bean properties using Java Bean
 * Introspection to display them in a table component. For the purposes of this
 * class, five formats for referencing a particular property value of a bean are
 * defined, with the layout of an identifying String in parentheses:
 * <ul>
 * <li>
 * <b>Simple</b> (name) - The specified name identifies an individual property
 * of a particular JavaBean. The name of the actual getter or setter method to
 * be used is determined using standard JavaBeans instrospection, so that a
 * property named "xyz" will have a getter method named getXyz() or (for boolean
 * properties only) isXyz(), and a setter method named setXyz().
 * </li>
 * <li>
 * <b>Nested</b> (name1.name2.name3) The first name element is used to select a
 * property getter, as for simple references above. The object returned for this
 * property is then consulted, using the same approach, for a property getter
 * for a property named name2, and so on. The property value that is ultimately
 * retrieved or modified is the one identified by the last name element.
 * </li>
 * <li>
 * <b>Indexed</b> (name[index]) - The underlying property value is assumed to be
 * an array, or this JavaBean is assumed to have indexed property getter and
 * setter methods. The appropriate (zero-relative) entry in the array is 
 * selected. List objects are now also supported for read/write. You simply need
 * to define a getter that returns the list.
 * </li>
 * <li>
 * <b>Mapped</b> (name(key)) - The JavaBean is assumed to have a property getter
 * and setter methods with an additional attribute of type
 * <tt>java.lang.String</tt>.
 * </li>
 * <li>
 * <b>Combined</b> (name1.name2[index].name3(key)) - Combining mapped, nested,
 * and indexed references is also supported.
 * </li>
 * @author Giovanni Remigi
 * @version $Revision: 33 $
 */
public class BeanTableStructure implements TableStructure {
    
    /* /////////////////////////////////////////////////////////////////////////
     * Log framework.
     */
    
    /** Apache log framework. */
    private static Log logger = LogFactory.getLog(BeanTableStructure.class);
    
    /* /////////////////////////////////////////////////////////////////////////
     * Private variables.
     */
    
    /**
     * The list of properties used to retrieve a bean value corresponding to
     * a given column index by Java Bean Introspection.
     */
    private String[] propertyNames;
    
    /** The names of the columns to be displayed by a table component. */
    private String[] columnLabels;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constructors.
     */
    
    /**
     * Create a formatter retrieving property values by a bean using Java Bean
     * Introspection.
     * @param propertyNames This is a list of property names to extract values
     *     from a given bean. A property name can be specified in five different
     *     formats as described above.
     * @param columnLabels The names of the columns to be displayed by a table
     *     component.
     */
    public BeanTableStructure(String[] propertyNames, String[] columnLabels) {
        this.propertyNames = propertyNames;
        this.columnLabels = columnLabels;
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * TableFormat interface implementation.
     */
    
    /** {@inheritDoc} */
    public int getColumnCount() {
        return columnLabels.length;
    }
    
    /** {@inheritDoc} */
    public String getColumnLabel(int column) {
        return columnLabels[column];
    }
    
    /** {@inheritDoc} */
    public Object getColumnValue(Object obj, int column) {

        Object value = null;
        if (propertyNames[column] == null) {
            value = obj; // Return the object itself.
        } else {
            try {
                value = PropertyUtils.getProperty(obj,
                    propertyNames[column]);
            } catch (Exception ex) {
                logger.error(ex);
            }
        }
        return value;
    }
    
    /** {@inheritDoc} */
    public Object setColumnValue(Object modifiedObject, Object modifiedValue,
            int column) {
        
        try {
            PropertyUtils.setProperty(modifiedObject, propertyNames[column],
                modifiedValue);
        } catch (Exception ex) {
            logger.error(ex);
        }
        return modifiedObject;
    }
}