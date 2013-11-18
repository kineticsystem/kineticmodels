/*
 * ActiveListEvent.java
 *
 * Created on 28 January 2006, 16.23
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

import java.util.EventObject;

/**
 * Event thrown by any <tt>EventList</tt>.
 * @author Giovanni
 * @version $Revision: 36 $
 */
public class ActiveListEvent extends EventObject implements Cloneable {
    
    /** Class serial number. */
    private final static long serialVersionUID = 42L;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Public constants.
     */
    
    /** Identifies one or more changes in the lists contents. */
    public static final int CONTENTS_CHANGED = 0;
    
    /** Identifies the addition of one or more contiguous items to the list. */
    public static final int INTERVAL_ADDED = 1;
         
    /** Identifies the removal of one or more contiguous items from the list. */
    public static final int INTERVAL_REMOVED = 2;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Properties.
     */
    
    /** 
     * The event type. Possible values are:
     * <ul>
     * <li>CONTENTS_CHANGED</li>
     * <li>INTERVAL_ADDED</li>
     * <li>INTERVAL_REMOVED</li>
     * </ul>
     */
    private int type;
    
    /**
     * An event can be part of a sequence of events being processed at the same
     * time. This is the event sequence number.
     */
    private int sequenceNumber;
    
    /**
     * An event can be part of a sequence of events being processed at the same
     * time. This is the last event sequence number.
     */
    private int lastSequenceNumber;
    
    private int x;
    
    private int y;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constructors.
     */
    
    /** Constructor. */
    public ActiveListEvent(Object source) {
        super(source);
    }

    /* /////////////////////////////////////////////////////////////////////////
     * Getter and setter methods.
     */
    
    public int getType() {
        return type;
    }
    
    public void setType(int type) {
        this.type = type;
    }
    
    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }   
    
    public int getLastSequenceNumber() {
        return lastSequenceNumber;
    }

    public void setLastSequenceNumber(int lastSequenceNumber) {
        this.lastSequenceNumber = lastSequenceNumber;
    }  
    
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }
    
    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    /* /////////////////////////////////////////////////////////////////////////
     * Cloneable interface implementation.
     */
    
    /** {@inheritDoc} */
    @Override
    public Object clone() {
        ActiveListEvent event = new ActiveListEvent(this.getSource());
        event.setX(this.x);
        event.setY(this.y);
        event.setType(this.type);
        return event;
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * Object class overriding methods.
     */
    
    /** {@inheritDoc} */
    @Override
    public String toString() {
        
        String typeDescription = "";
        switch (type) {
            case CONTENTS_CHANGED:
                typeDescription = "CONTENTS_CHANGED";
                break;
            case INTERVAL_ADDED:
                typeDescription = "INTERVAL_ADDED";
                break;
            case INTERVAL_REMOVED:
                typeDescription = "INTERVAL_REMOVED";
                break;
        }
                
        String out = super.toString()
            .concat("[")
            .concat("type=" + typeDescription + ",")
            .concat("x=" + x + ",")
            .concat("y=" + y + "]");
        return out;
    }    
}
