/*
 * ContactFilter.java
 *
 * Created on 27 April 2006, 11.03
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

// Application classes.

import org.kineticsystem.commons.data.model.bean.*;
import org.kineticsystem.commons.data.model.mapping.Filter;
import org.kineticsystem.commons.random.bean.*;

/**
 * This object is used to filter a list of contacts using regular expressions.
 * @author Giovanni Remigi
 * $Revision: 20 $
 */
public class ContactFilter implements Filter<RandomContact> {
    
    /**
     * The object delegated to filter the list of contact using reflection on a
     * contact property and regular expressions.
     */
    private BeanRegExpFilter predicate;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constructors.
     */
    
    /** Default constructor. */
    public ContactFilter(String propertyName, String regExp) {
        predicate = new BeanRegExpFilter(propertyName, regExp);
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * Filter interface implementation.
     */
    
    /** {@inheritDoc} */
    public boolean evaluate(RandomContact contact) {
        return predicate.evaluate(contact);
    }
}
