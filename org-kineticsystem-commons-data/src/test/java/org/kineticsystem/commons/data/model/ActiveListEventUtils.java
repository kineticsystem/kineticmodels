/*
 * ActiveListEventUtils.java
 *
 * Created on 5 March 2006, 23.13
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

package org.kineticsystem.commons.data.model;

/**
 *
 * @author Giovanni Remigi
 * @version $Revision: 9 $
 */
public class ActiveListEventUtils {
    
    public static String toString(ActiveListEvent event) {
        String out = "";
        if (event.getType() == ActiveListEvent.INTERVAL_ADDED) {
            out = "ADD";
        } else if (event.getType() == ActiveListEvent.INTERVAL_REMOVED) {
            out = "DEL";
        } else if (event.getType() == ActiveListEvent.CONTENTS_CHANGED) {
            out = "MOD";
        }
        out += "(" + event.getX() + ", " + event.getY() + ")";
        return out;
    }
}
