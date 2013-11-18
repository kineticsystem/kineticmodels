/*
 * Gap.java
 *
 * Created on 16 giugno 2005, 17.26
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

package org.kineticsystem.commons.layout;

/**
 * This class represents a gap between two columns or rows. It is internally
 * used by the <code>TetrisLayout</code> algorithm.
 * @author Giovanni Remigi
 * $Revision: 7 $
 */
class Gap {
    
    /* /////////////////////////////////////////////////////////////////////////
     * Private variables.
     */
    
    /** The gap. */
    private int size;
    
    /** 
     * This is a working variable used to store the sum of more than one gap.
     */
    private int incSize;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constructors.
     */
    
    /** Default constructor. */
    public Gap() {
        size = 0;
        incSize = 0;
    }

    /* /////////////////////////////////////////////////////////////////////////
     * Extensions.
     */
    
    /**
     * Return a description of the internal state of the current instance. Very
     * useful in debugging.
     * @return A description of the object.
     */
    public String toString() {
        return super.toString()
            .concat("[")
            .concat("size=" + size + ",")
            .concat("incSize=" + incSize + "]");
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * Getter and setter methods.
     */
    
    /** 
     * Return the gap value.
     * @return The gap value.
     */
    public int getSize() {
        return size;
    }

    /** 
     * Set the gap value.
     * @param size The gap value.
     */
    public void setSize(int size) {
        this.size = size;
    }

    /** 
     * Return a previously stored variable used to sum a gap sequence.
     * Internally used by the layout algorithm.
     * @return A sum of a gap sequence.
     */
    public int getIncSize() {
        return incSize;
    }

    /** 
     * Store a variable used to sum a gap sequence. Internally used by the
     * layout algorithm.
     * @param incSize The sum of a gap sequence.
     */
    public void setIncSize(int incSize) {
        this.incSize = incSize;
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * Generic methods.
     */
    
    /**
     * Factory method that return an array of initialized objects.
     * @param size The length of the array.
     * @return An array of initialized objects.
     */
    public static Gap[] createArray(int size) {
        Gap[] objs = new Gap[size];
        for (int i = 0; i < size; i++) {
            objs[i] = new Gap();
        }
        return objs;
    } 
}
