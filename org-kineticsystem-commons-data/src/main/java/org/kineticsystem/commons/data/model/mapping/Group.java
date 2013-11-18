/*
 * Group.java
 *
 * Created on 24 April 2006, 14.18
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

package org.kineticsystem.commons.data.model.mapping;

// Application classes.

import org.kineticsystem.commons.data.model.ActiveList;
import org.kineticsystem.commons.data.model.DefaultActiveList;

/**
 * This object represents a group of items from a collection of items. This
 * class is public to permit Swing components access getter methods by
 * reflection. 
 * @author Giovanni Remigi
 * @version $Revision: 9 $
 */
public class Group<S> {

    /* /////////////////////////////////////////////////////////////////////////
     * Private properties.
     */
    
    private ActiveList<S> items;
    
    private GroupMapping<S> mapping;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constructor.
     */
    
    /**
     * Default constructor.
     * @param list The list of groups this group belongs to.
     */
    public Group(GroupMapping<S> mapping) {
        this.mapping = mapping;
        this.items = new DefaultActiveList<S>();
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * Getter methods.
     */
    
    /**
     * Return true if the group is empty, false otherwise.
     * @return True if the group is empty, false otherwise.
     */
    public ActiveList<S> getItems() {
        return items;
    }
    
    public void setItems(ActiveList<S> items) {
        this.items = items;
    }
    
    /**
     * Return an object as result of some custom aggregation function on the
     * group. The method delegate the evaluation to the connected
     * <tt>GroupingProxy</tt> by the usage of an <tt>Aggregator</tt>.
     * @see org.kineticsystem.commons.data.model.proxies.Aggregator
     * @see org.kineticsystem.commons.data.model.proxies.GroupingProxy
     */
    public Object getAggregation() {
        
        // Evaluate a function on group items.
        
        return mapping.getAggregator().getValue(items);
    }
    
    public Object getDelegate() {
        if (items != null) {
            return items.get(0);
        } else {
            return null;
        }
    }
}