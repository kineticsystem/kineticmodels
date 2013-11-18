/*
 * NavigatorEvent.java
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

import java.util.EventObject;

/** 
 * This event is fired by a list navigator when a new object is selected.
 * @author Giovanni Remigi
 * @version $Revision: 34 $
 */
public class NavigatorEvent extends EventObject {
    
    /* /////////////////////////////////////////////////////////////////////////
     * Costants.
     */
    
    /** Class version number. */
    private static final long serialVersionUID = 1L;
    
    /** 
     * Value returned by <code>getPosition</code> method when no object is
     * selected.
     */
    public static int UNKNOWN_POSITION = -1;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Properties.
     */
    
    /** The current selected object. */
    private Object object;
    
    /** The position of the current selected object. */
    private int index;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constructors.
     */
    
    /** 
     * Constructor. 
     * @param source The event source.
     * @param object The selected object.
     * @param index The position of the selected object.
     */
    public NavigatorEvent(Object source, Object object, int index) {
        super(source);
        this.object = object;
        this.index = index;
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * Getter and setter methods.
     */
    
    /** 
     * Return the selected object.
     * @return The selected object.
     */
    public Object getSelectedObject() {
        return object;
    }
    
    /** 
     * Return the position of the selected object.
     * @return The position of the selected object.
     */
    public int getPosition() {
        return index;
    }
}