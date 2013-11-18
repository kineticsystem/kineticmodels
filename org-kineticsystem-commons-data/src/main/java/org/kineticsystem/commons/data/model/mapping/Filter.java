/*
 * Filter.java
 *
 * Created on 15 April 2006, 23.39
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
 * This interface is used to evaluate if an object satisfied a given condition.
 * It is mainly used in filtering and grouping items of a given list.
 * @author Giovanni Remigi
 * @version $Revision: 9 $
 */
public interface Filter<E> {
    
    /**
     * Return true if the given object satisfied the implemented condition,
     * false otherwise.
     * @param obj The object to be evaluated.
     * @param True if the given object satisfied the implemented condition,
     *     false otherwise.
     */
    public boolean evaluate(E obj);
    
}