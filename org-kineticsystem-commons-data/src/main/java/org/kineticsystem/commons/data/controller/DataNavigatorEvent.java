/*
 * EditorEvent.java
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
 * Event generated by an object editor when its iternal state has been changed.
 * @author Giovanni Remigi
 * @version $Revision: 145 $
 */
public class DataNavigatorEvent extends EventObject { 
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constants.
     */
    
    /** Class version number. */
    private static final long serialVersionUID = 1L;    
    
    /* /////////////////////////////////////////////////////////////////////////
     * Variables.
     */    
    
    /** The internal state of the editor. */
    private byte state;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constructors.
     */
    
    /** 
     * Event constructor..
     * @param source The event source.
     * @param state The state of the editor. Available values are:
     *     <ul>
     *     <li><tt>ObjectEditorCOntroller.DEFAULT_STATE;</tt></li>
     *     <li><tt>ObjectEditorCOntroller.INSERTION_STATE;</tt></li>
     *     <li><tt>ObjectEditorCOntroller.EDITING_STATE;</tt></li>
     *     <li><tt>ObjectEditorCOntroller.NULL_STATE.</tt></li>
     *     </ul>
     */
    public DataNavigatorEvent(Object source, byte state) {
        super(source);
        this.state = state;
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * Getter and setter method.
     */    
    
    /** 
     * Return the internal state of the editor. 
     * @return The internal state of the editor. Possible values are:
     *     <ul>
     *     <li><tt>ObjectEditorCOntroller.DEFAULT_STATE;</tt></li>
     *     <li><tt>ObjectEditorCOntroller.INSERTION_STATE;</tt></li>
     *     <li><tt>ObjectEditorCOntroller.EDITING_STATE;</tt></li>
     *     <li><tt>ObjectEditorCOntroller.NULL_STATE.</tt></li>
     *     </ul>
     */
    public byte getState() {
        return state;
    }  
}