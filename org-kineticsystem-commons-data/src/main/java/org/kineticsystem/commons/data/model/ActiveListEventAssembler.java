/*
 * ActiveListEventAssembler.java
 *
 * Created on 24 February 2006, 7.52
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
 * This object is used to asynchronously collect events from the
 * <tt>ActiveListModel</tt>, pack them in the right way and synchronously send
 * all together when requested.
 * @author Giovanni Remigi
 * @version $Revision: 9 $
 */
public interface ActiveListEventAssembler {
    
    /**
     * Return false is the assembler doesn't contain any event, false otherwise.
     * @return False is the assembler doesn't contain any event, false
     *     otherwise.
     */
    public boolean isEmpty();

    /**
     * Get the first event of the list.
     * @return The first event of the list (if any).
     */
    public ActiveListEvent pop();

    /**
     * Add the given event to the list.
     * @param event The event to be packed inside the list.
     */
    public void push(ActiveListEvent event); 
}
