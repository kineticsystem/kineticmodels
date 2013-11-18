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

/**
 * @author Giovanni Remigi
 * @version $Revision: 9 $
 */
class SortMappingKey<S> implements MappingKey<S,S> {

    /* /////////////////////////////////////////////////////////////////////////
     * Properties.
     */
    
    private S sourceItem;
    
    private S targetItem;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constructors.
     */
    
    public SortMappingKey() {
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * Getter and setter methods.
     */
    
    public S getSource() {
        return sourceItem;
    }
    
    public void setSource(S sourceItem) {
        this.sourceItem = sourceItem;
    }
    
    public S getTarget() {
        return targetItem;
    }
    
    public void setTarget(S targetItem) {
        this.targetItem = targetItem;
    }
}