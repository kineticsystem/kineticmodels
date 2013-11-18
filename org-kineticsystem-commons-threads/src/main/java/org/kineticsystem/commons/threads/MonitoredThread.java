/*
 * MonitoredThread.java
 *
 * Created on June 17, 2004, 9:41 PM
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
 * A default monitorable thread. Extend this class if you want create a thread
 * that can be monitored by a monitor.
 * @author Giovanni Remigi
 * $Revision: 88 $
 */
public abstract class MonitoredThread extends Thread 
        implements Monitorable {
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constructors.
     */
    
    /** Default constructor. */
    public MonitoredThread() {
    }
    
    /**
     * Constructor with a <code>Runnable</code> object.
     * @param runner The <code>Runnable</code> object that represens an activity
     *     to be executed by a thread.
     */
    public MonitoredThread(Runnable runner) {
        super(runner);
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * Monitorable interface implementation.
     */
    
    /**
     * Return an object containing information of the thread beeing monitored.
     * @return An object containing information of the thread beeing monitored.
     */
    public abstract MonitorInfo getInfo();
}