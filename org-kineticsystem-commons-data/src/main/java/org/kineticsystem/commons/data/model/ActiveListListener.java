/*
 * ActiveListListener.java
 *
 * Created on 25 February 2006, 16.52
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

// Java classes.

import java.util.EventListener;

/**
 * This is a listener for the <tt>ActiveList</tt> object.
 * @author Giovanni Remigi
 * @version $Revision: 36 $
 */
public interface ActiveListListener extends EventListener {
    
    /**
     * Fired when the contents of the list has changed, an interval has been
     * inserted in the data model or removed from the data model.
     * @param event The event containing all information about the model
     *     change.
     */
    public void contentsChanged(ActiveListEvent event);
}
