/*
 * NavigatorListener.java
 *
 * Created on August 16, 2003, 12:27 AM
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

package org.kineticsystem.commons.data.controller;

// Java classes.

import java.util.EventListener;

/** 
 * Any components listening for list navigation events must implement this
 * interface.
 * @author Giovanni Remigi
 * @version $Revision: 34 $
 * @see org.kineticsystem.commons.data.controller.Navigator
 */
public interface NavigatorListener extends EventListener {
    
    /**
     * This method is called when a new object is selected by the list
     * navigator component.
     * @param event The navigator event.
     */
    public void objectSelected(NavigatorEvent event);
}