/*
 * ContactsPerContinentAggr.java
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

// Kinetic Models classes.

import org.kineticsystem.commons.data.model.mapping.*;
import org.kineticsystem.commons.random.bean.*;

/**
 * @author Giovanni Remigi
 * @version $Revision: 27 $
 */
public class ContactsPerContinentAggr implements GroupAggregator<RandomContact> {

    public Object getValue(java.util.List<RandomContact> contacts) {
        return contacts.size();
    }
    
    public int compare(RandomContact contact1, RandomContact contact2) {
        return contact1.getContinent().compareTo(contact2
            .getContinent());
    }
    
    public String toString() {
        return "Number of items per continent";
    }
    
}
