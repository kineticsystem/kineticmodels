/*
 * MonitorListener.java
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

package org.kineticsystem.commons.threads;

// Java classes.

import java.util.EventListener;

/**
 * Implement this interface if you want to receive events from a thread monitor.
 * @author Giovanni Remigi
 * $Revision: 38 $
 */
public interface MonitorListener extends EventListener {
    
    /**
     * This method is invoked my a thread monitor at regular intervals.
     * @param e The monitor event.
     */
    public void processChanged(MonitorEvent e);
}
