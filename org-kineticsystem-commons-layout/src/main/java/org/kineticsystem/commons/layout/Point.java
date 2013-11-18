/*
 * Point.java
 *
 * Created on 1 luglio 2005, 18.28
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
 * This object represent a bidimensional point used inside the layout algorithm.
 * @author Giovanni Remigi
 * $Revision: 7 $
 */
class Point {
    
    /* /////////////////////////////////////////////////////////////////////////
     * Private variables.
     */
    
    /** The x cohordinate. */
    private double x;
    
    /** The y cohordinate. */
    private double y;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constructors.
     */    
    
    /**
     * Create a new point.
     * @param x The x cohordinate.
     * @param y The y chorodinate.
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
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
            .concat("x=" + x + ",")
            .concat("y=" + y + "]");
    }    
    
    /* /////////////////////////////////////////////////////////////////////////
     * Getter and setter methods.
     */  
    
    /** 
     * Return the x cohordinate.
     * @return The x cohordinate.
     */
    public double getX() {
        return x;
    }

    /** 
     * Set the x cohordinate.
     * @param x The x cohordinate.
     */
    public void setX(double x) {
        this.x = x;
    }
    
    /** 
     * Return the y cohordinate.
     * @return The y cohordinate.
     */
    public double getY() {
        return y;
    }
    
    /** 
     * Set the y cohordinate.
     * @param y The y cohordinate.
     */
    public void setY(double y) {
        this.y = y;
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * Generic methods.
     */
    
//    /**
//     * Add the given point to the current point.
//     * @param point The addend.
//     */
//    public void add(Point point) {
//        x += point.getX();
//        y += point.getY();
//    }
//
//    /**
//     * Substract the given point to the current point.
//     * @param point The subtrahend.
//     */    
//    public void sub(Point point) {
//        x -= point.getX();
//        y -= point.getY();
//    }
}
