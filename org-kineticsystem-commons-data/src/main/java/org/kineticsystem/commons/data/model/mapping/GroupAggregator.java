/*
 * Aggregator.java
 *
 * Created on 26 April 2006, 8.14
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

// Java classes.

import java.util.Comparator;
import java.util.List;

/**
 * This object is used to evaluate some kind of information on a given group of
 * object.
 * @author Giovanni Remigi
 * @version $Revision: 9 $s
 * @see org.kineticsystem.commons.data.model.proxies.GroupingProxy
 */
public interface GroupAggregator<S> extends Comparator<S> {
    
    /**
     * Return an object containing some evaluation on the given list of objects.
     * The returned object can contain not only the required values, but also
     * some description or label about connected to the evaluation.
     * @param object The list of objects to be analyzed.
     * @return The resulting object containing any kind of useful information on
     *     the given list of objects.
     */
    public Object getValue(List<S> elements);
}
