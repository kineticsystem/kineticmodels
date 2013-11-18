/*
 * ContactDefaultAggregator.java
 *
 * Created on 27 April 2006, 12.13
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

import java.util.*;

// Application classes.

import org.kineticsystem.commons.data.model.bean.*;
import org.kineticsystem.commons.data.model.mapping.GroupAggregator;
import org.kineticsystem.commons.random.bean.*;

/**
 * This is an aggregator that simply groups items based on the given bean
 * properties. When required it returns the number of items of a group.
 * @author Giovanni Remigi
 * $Revision: 20 $
 */
public class ContactDefaultAggregator implements GroupAggregator<RandomContact> {
    
    /* /////////////////////////////////////////////////////////////////////////
     * Private variables.
     */
    
    /** The delegate comparator used to group items. */
    private BeanComparator comparator;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constructors.
     */
    
    /**
     * Constructor accepting one bean property used in ordering.
     * @param propertyName The bean propertiy used by the comparison
     */
    public ContactDefaultAggregator(String propertyName) {
        comparator = new BeanComparator(propertyName);
    }
    
    /**
     * Constructor accepting multiple bean properties used in ordering.
     * @param propertyNames The list of bean properties used by the comparison.
     *     The comparison goes from the most significant property, the first
     *     one, to the less signignificant, the last one.
     */
    public ContactDefaultAggregator(String[] propertyNames) {
        comparator = new BeanComparator(propertyNames);
    }

    /* /////////////////////////////////////////////////////////////////////////
     * Aggregator interface implementation.
     */
    
    /** {inheritDoc} */
    public Object getValue(List<RandomContact> contacts) {
        return contacts.size();
    }
    
    /** {inheritDoc} */
    public int compare(RandomContact contact1 , RandomContact contact2) {
        return comparator.compare(contact1, contact2);
    }
}
