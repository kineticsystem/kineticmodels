/*
 * SplittedLine.java
 *
 * Created on 16 giugno 2005, 18.15
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

// Java classes.

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


/**
 * A composition of one or more <code>Line</code>.
 * It is internally used by the <code>TetrisLayout</code> algorithm.
 * @author Giovanni Remigi
 * $Revision: 148 $
 */
class SplittedLine {
    
    /* /////////////////////////////////////////////////////////////////////////
     * Private variables.
     */
    
    /** The collection of segments composing the line. */
    private List<Line> segments;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constructors.
     */
    
    /** Default constructor. */
    public SplittedLine() {
        segments = new LinkedList<Line>();
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
        StringBuffer value = new StringBuffer();
        value.append(super.toString() + "[\n");
        value.append("    segments=\n");
        for (Line line : segments) {
            value.append("        " + line + "\n");
        }
        value.append("]");
        return value.toString();
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * Public methods.
     */
    
    /**
     * Add a segment to the line.
     * @param segment The segment that composes the line.
     */
    public void add(Line segment) {
        segments.add(segment);
    }
    
    /**
     * Remove a segment from the line.
     * @param segment The segment to be removed.
     */
    public void remove(Line segment) {
        segments.remove(segment);
    }
    
    /** 
     * Return all the segments of the splitted line.
     * @return All the segments of the splitted line.
     */
    public List<Line> getSegments() {
        return segments;
    }
    
    /**
     * Evaluate the y-cooordinate by the given x-coordinate.
     * @param xValue The x-coordinate.
     * @return The y-coordinate corresponding to the given x-coordinate.
     */
    public double evaluate(double xValue) {
        double value = 0;
        Iterator<Line> iter = segments.iterator();
        boolean isInBounds = false;
        while (iter.hasNext() && !isInBounds) {
            Line segment = iter.next();
            if ((xValue >= segment.getStart()) && (xValue < segment.getEnd())) {
                value = segment.evaluate(xValue);
                isInBounds = true;
            }
        }
        return value;
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * Generic methods.
     */
    
    /**
     * Factory method that return an array of initialized objects.
     * @param size The length of the array.
     * @return An array of initialized objects.
     */
    public static SplittedLine[] createArray(int size) {
        SplittedLine[] objs = new SplittedLine[size];
        for (int i = 0; i < size; i++) {
            objs[i] = new SplittedLine();
        }
        return objs;
    }    
}
