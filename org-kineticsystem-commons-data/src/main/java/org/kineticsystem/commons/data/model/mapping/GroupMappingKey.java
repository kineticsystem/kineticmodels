/*
 * Key.java
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

package org.kineticsystem.commons.data.model.mapping;

// Application classes.

import org.kineticsystem.commons.data.model.ActiveList;
import org.kineticsystem.commons.data.model.DefaultActiveList;

/**
 * @author Giovanni Remigi
 * @version $Revision: 9 $
 */
class GroupMappingKey<S> implements MappingKey<ActiveList<S>,Group<S>>{

    /* /////////////////////////////////////////////////////////////////////////
     * Properties.
     */
    
    private ActiveList<S> source;
    
    private Group<S> target;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constructors.
     */
    
    public GroupMappingKey() {
        this.source = new DefaultActiveList<S>();
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * Getter and setter methods.
     */
    
    public ActiveList<S> getSource() {
        return source;
    }
    
    public void setSource(ActiveList<S> source) {
        this.source = source;
    }
    
    public Group<S> getTarget() {
        return target;
    }
    
    public void setTarget(Group<S> target) {
        this.target = target;
    }
}