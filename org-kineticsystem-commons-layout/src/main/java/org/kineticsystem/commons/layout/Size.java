/*
 * Size.java
 *
 * Created on 16 giugno 2005, 12.30
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
 * This Java Bean is used to store a copy of all component dimensions and
 * internally used by the <code>TetrisLayout</code> algorithm.
 * @author Giovanni Remigi
 * $Revision: 7 $
 */
class Size {
    
    /* /////////////////////////////////////////////////////////////////////////
     * Private variables.
     */
    
    /** The minimum component width. */
    private double minimumWidth;
    
    /** The minimum component height. */
    private double minimumHeight;
    
    /** The preferred component width. */
    private double preferredWidth;
    
    /** The preferred component height. */
    private double preferredHeight;
    
    /** The maximum component width. */
    private double maximumWidth;
    
    /** The maximum component height. */
    private double maximumHeight;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constructors.
     */
    
    /** Default constructor. */
    public Size() {
        minimumWidth = 0;
        minimumHeight = 0;
        preferredWidth = 0;
        preferredHeight = 0;
        maximumWidth = Double.MAX_VALUE;
        maximumHeight = Double.MAX_VALUE;
    }

    /* /////////////////////////////////////////////////////////////////////////
     * Extensions.
     */
    
    /**
     * Return a description of the internal state of the current instance.
     * @return A description of the object.
     */
    public String toString() {
        return super.toString()
            .concat("[")
            .concat("minimumWidth=" + minimumWidth + ",")
            .concat("preferredWidth=" + preferredWidth + ",")
            .concat("maximumWidth=" + maximumWidth + ",")
            .concat("minimumHeight=" + minimumHeight + ",")
            .concat("preferredHeight=" + preferredHeight + ",")
            .concat("maximumHeight=" + maximumHeight + "]");
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * Getter and setter methods.
     */
    
    public double getMinimumWidth() {
        return minimumWidth;
    }

    public void setMinimumWidth(double minimumWidth) {
        this.minimumWidth = minimumWidth;
    }

    public double getMinimumHeight() {
        return minimumHeight;
    }

    public void setMinimumHeight(double minimumHeight) {
        this.minimumHeight = minimumHeight;
    }

    public double getPreferredWidth() {
        return preferredWidth;
    }

    public void setPreferredWidth(double preferredWidth) {
        this.preferredWidth = preferredWidth;
    }

    public double getPreferredHeight() {
        return preferredHeight;
    }

    public void setPreferredHeight(double preferredHeight) {
        this.preferredHeight = preferredHeight;
    }

    public double getMaximumWidth() {
        return maximumWidth;
    }

    public void setMaximumWidth(double maximumWidth) {
        this.maximumWidth = maximumWidth;
    }

    public double getMaximumHeight() {
        return maximumHeight;
    }

    public void setMaximumHeight(double maximumHeight) {
        this.maximumHeight = maximumHeight;
    }

    /* /////////////////////////////////////////////////////////////////////////
     * Generic methods.
     */
    
    /**
     * Factory method that return an array of initialized objects.
     * @param size The length of the array.
     * @return An array of initialized objects.
     */
    public static Size[] createArray(int size) {
        Size[] objs = new Size[size];
        for (int i = 0; i < size; i++) {
            objs[i] = new Size();
        }
        return objs;
    }
}
