/*
 * MonitorEvent.java
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

import java.util.EventObject;

/**
 * This event is fired by a <code>DefaultMonitor</code> during a monitoring
 * process. It contains information about the monitored thread.
 * @author Giovanni Remigi
 * $Revision: 38 $
 */
public class MonitorEvent extends EventObject {
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constants.
     */
    
    /** Version number. */
    private static final long serialVersionUID = 1L;
    
    /** 
     * This constant denotes a default monitoring event. Throw the getter
     * methods it is possible to retrieve additional information about the
     * monitored thread.
     */
    public static final int DEFAULT_EVENT = 1;
    
    /**
     * This constant denotes the interruption of a monitoring process. When
     * such an event occurs the getter methods, except the
     * <code>getEventType</code> method, can contain wrong information.
     */
    public static final int LAST_EVENT = 2; 
    
    /* /////////////////////////////////////////////////////////////////////////
     * Private variables.
     */
    
    /** This object incapsulate information of the monitored thread. */
    private MonitorInfo info;
    
    /** The event type: <code>DEFAULT_EVENT</code>, <code>LAST_EVENT</code>. */
    private int type;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constructors.
     */
    
    /**
     * The main constructor.
     * @param source The event source.
     * @param type The event type. Possible value are:
     *     <code>DEFAULT_EVENT</code>;
     *     <code>LAST_EVENT</code>.
     * @param info The object incapsulating the monitored thread information.
     */
    public MonitorEvent(Object source, int type, MonitorInfo info) {
        super(source);
        this.type = type;
        this.info = info;
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * Getter methods.
     */
    
    /**
     * Return the event type. Possible value are:
     *     <code>DEFAULT_EVENT</code>;
     *     <code>LAST_EVENT</code>.
     * @return The event type.
     */
    public int getType() {
        return type;
    }
    
    /**
     * Return the object incapsulating the monitored thread information.
     * @return The object incapsulating the monitored thread information.
     */
    public MonitorInfo getInfo() {
        return info;
    }
}