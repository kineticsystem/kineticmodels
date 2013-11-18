/*
 * ActiveList.java
 *
 * Created on 4 November 2005, 22.24
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

import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;

/**
 * Any list allowing concurrent modifications and firing events must implement
 * this interface.
 * @author Giovanni Remigi
 * @version $Revision: 43 $
 */
public interface ActiveList<E> extends List<E> {
    
    /** The read/write lock used to allow concurrency. */
    public ReadWriteLock getReadWriteLock();
    
    /** 
     * Register a new event listener.
     * @param listener The new event listener.
     */
    public void addActiveListListener(ActiveListListener listener);
    
    /** 
     * Remove a previously registered event listener.
     * @param listener The previously registered event listener.
     */
    public void removeActiveListListener(ActiveListListener listener); 
    
    /**
     * Activate or deactivate the list: while a list is active it sends events
     * to all registered listener. This method can be used to avoid performance
     * problems when heavily working on the list.
     * @param active True if the list can send events, false otherwise.
     */
    public void setActive(boolean activated);
    
    /**
     * Return true if the list is activated, false otherwise.
     * @return True if the list is activated, false otherwise.
     */
    public boolean isActive();
    
    /**
     * Manually notify all registered listeners with the given event. This is
     * useful to better control the performance of the model.
     * @param event The event containing information about changes occurred
     *     inside the list.
     */
    public void fireContentsChanged(ActiveListEvent event);
}