/*
 * AvgAgePerContinent.java
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

package org.kineticsystem.commons.data.demo.aggregator;

// Java classes.

import java.util.*;

// Kinetic Models classes.

import org.kineticsystem.commons.data.model.mapping.*;
import org.kineticsystem.commons.random.bean.*;

/**
 * @author Giovanni Remigi
 * @version $Revision: 26 $
 */
public class MaxAgePerContinentAggr implements GroupAggregator<RandomContact> {

    public Object getValue(java.util.List<RandomContact> contacts) {
        int max = 0;
        Iterator iter = contacts.iterator();
        while (iter.hasNext()) {
            int age = ((RandomContact) iter.next()).getAge();
            if (age > max) {
                max = age;
            }
        }
        return new Integer(max);
    }
    
    public int compare(RandomContact contact1, RandomContact contact2) {
        return contact1.getContinent().compareTo(contact2
            .getContinent());
    }
    
    public String toString() {
        return "Max age per continent";
    }
}
