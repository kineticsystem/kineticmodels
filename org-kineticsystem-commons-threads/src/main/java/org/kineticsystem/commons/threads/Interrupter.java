/*
 * Interrupter.java
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

/**
 * <p>Any switch implementing this interface and linked to a monitor can request
 * the interruption of the monitored thread.</p>
 * <p>When the method <code>isCancelled</code> of any linked switch return true,
 * the monitor requests the interruption of the monitored thread. After the
 * thread died, the monitor fires a <MonitorEvent> to all linked
 * <code>MonitorListener</code> and destroys itself.
 * @author Giovanni Remigi
 * $Revision: 24 $
 */
public interface Interrupter {
    
    /**
     * Must return true to request an interruption of the monitored thread.
     * @return True to request an interruption of the monitored thread.
     */
    public boolean isCancelled();
    
    /** Reset the switch value. */
    public void reset();
}
