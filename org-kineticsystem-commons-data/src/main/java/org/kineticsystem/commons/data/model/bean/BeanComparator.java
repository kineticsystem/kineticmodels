/*
 * BeanComparator.java
 *
 * Created on 15 April 2006, 10.39
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

package org.kineticsystem.commons.data.model.bean;

// Java classes.

import java.util.Comparator;

// Apache commons libraries.

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This object is used to compare two items delegating the comparison to a
 * specified bean property. The object returned by the this property must
 * implement the <tt>Comparable</tt> interface.
 * @author Giovanni Remigi
 * @version $Revision: 147 $
 * @see java.lang.Comparable
 */
public class BeanComparator implements Comparator<Object> {
    
    /* /////////////////////////////////////////////////////////////////////////
     * Log framework.
     */
    
    /** Apache log framework. */
    private static Log logger = LogFactory.getLog(BeanComparator.class);
    
    /* /////////////////////////////////////////////////////////////////////////
     * Private properties.
     */
    
    /** Bean property to be used by the comparator. */
    private String[] propertyNames;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constructors.
     */
    
    /** Default constructor. */
    public BeanComparator(String propertyName) {
        this.propertyNames = new String[] {propertyName};
    }
    
    /**
     * Constructor accepting multiple properties used for ordering.
     * @param propertyNames The list of properties used by the comparison. The
     *     comparison goes from the most significant property, the first one, to
     *     the less signignificant, the last one.
     */
    public BeanComparator(String[] propertyNames) {
        this.propertyNames = propertyNames;
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * Comparator interface implementation.
     */
    
    /**
     * Compare the two elements delegating the comparison to the specified
     * property.
     * @param obj1 The first element to compare.
     * @param obj2 The second element to compare.
     * @return An integer value as specified by the <tt>Comparator</tt>
     *     interface.
     * @see java.util.Comparator
     */
    @SuppressWarnings("unchecked")
    public int compare(Object obj1, Object obj2) {
        
        int i = 0;
        int comparison = 0;
        
        while ((comparison == 0) && (i < propertyNames.length)) {

            String propertyName = propertyNames[i++];
            
            try {
                Object v1 = PropertyUtils.getProperty(obj1, propertyName);
                Object v2 = PropertyUtils.getProperty(obj2, propertyName);

                if ((v1 instanceof Comparable) && (v2 instanceof Comparable)) {
                    Comparable c1 = (Comparable) v1;
                    Comparable c2 = (Comparable) v2;
                    comparison = c1.compareTo(c2);
                }
            } catch (Exception ex) {
                logger.error(ex);
            }
        }
        
        return comparison;
    }
}
