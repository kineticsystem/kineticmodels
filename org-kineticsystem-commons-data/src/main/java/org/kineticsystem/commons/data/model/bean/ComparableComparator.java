/*
 * ComparableComparator.java
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

package org.kineticsystem.commons.data.model.bean;

// Java classes.

import java.util.Comparator;

/**
 * Simple <code>Comparator<code> interface implementation that delegates the
 * comparison to the <Comparable> interface implemented by the compared object. 
 * @author Giovanni Remigi
 * @version $Revision: 147 $
 */
public class ComparableComparator implements Comparator<Comparable<Object>> {

    /* /////////////////////////////////////////////////////////////////////////
     * Comparator interface implementation.
     */
    
    /**
     * Compare the given objects.
     * @param o1 The first object to be compared.
     * @param o2 The second object to be compared.
     * @return A positive, zero or negative value if the first object is grater
     *     than, equal, less then the second one respectively.
     * @throws ClassCastException when the first object doesn't implement the
     *     <Comparable> interface.
     */
    @SuppressWarnings("unchecked")
    public int compare(Comparable o1, Comparable o2) {
        return o1.compareTo(o2);
    }
}