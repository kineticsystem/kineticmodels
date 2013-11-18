/*
 * Line.java
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

/**
 * This class represents a line segment identified by an intercept, a slope and
 * two bounds. A segment with a slope equals to 0 corresponds to an horizontal
 * segment while a segment with a slope equals to 1 corresponds to a 45° one.
 * The segment is lower and upper bounded by two x-cooordinates: outside these
 * bounds the <code>evaluate</code> function returns a zero value.
 * It is internally used by the <code>TetrisLayout</code> algorithm.
 * @author Giovanni Remigi
 * $Revision: 7 $
 */
class Line implements Cloneable {
    
    /* /////////////////////////////////////////////////////////////////////////
     * Private variables.
     */

    /** The slope of the line containing the segment. */
    private double slope;
    
    /** Min x-cohordinate allowed value. */
    private double start;
    
    /** Max x-cohordinate allowed value. */
    private double end;
    
    /** Min y-cohordinate allowed value. */
    private double min;
    
    /** Max y-cohordinate allowed value. */
    private double max;
    
    /** Constraint point belonging to the segment and used in normalization. */
    private Point point;
    
    /**
     * This variable is used to check the segments that have reached their
     * minimum or maximum allowed values. The layout minimization and
     * maximization algorithm uses this value to set the segment slope to a zero
     * value.
     */
    private boolean checked;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constructors.
     */
    
    /** 
     * Default constructor. The default lower bound is 0 and the default upper
     * bound is <code>Double.MAX_VALUE</code>. The default slope is 1 and the
     * default intercept is 0.
     * 
     */
    public Line() {
        slope = 1;
        start = 0;
        end = Double.MAX_VALUE;
        setMin(0);
        setMax(Double.MAX_VALUE);
        point = new Point(0, 0);
        checked = false;
    }
    
    /** 
     * Default constructor. The default lower bound is 0 and the default upper
     * bound is <code>Double.MAX_VALUE</code>. The default slope is 1 and the
     * default intercept is 0.
     * 
     */
    public Line(double slope, double intercept) {
        this.slope = slope;
        start = 0;
        end = Double.MAX_VALUE;
        setMin(0);
        setMax(Double.MAX_VALUE);
        point = new Point(0, intercept);
        checked = false;
    }
    
    /** 
     * Default constructor. The default lower bound is 0 and the default upper
     * bound is <code>Double.MAX_VALUE</code>. The default slope is 1 and the
     * default intercept is 0.
     * 
     */
    public Line(double slope, double intercept, double start, double end) {
        this.slope = slope;
        this.start = start;
        this.end = end;
        setMin(0);
        setMax(Double.MAX_VALUE);
        point = new Point(0, intercept);
        checked = false;
    }
    
    /**
     * Create a segment with a given slope and a pass-throw point.
     * @param slope The slope of the segment.
     * @param p A point that belongs to the segment.
     */
    public Line(double slope, Point p) {
        this.slope = slope;
        start = 0;
        end = Double.MAX_VALUE;
        setMin(0);
        setMax(Double.MAX_VALUE);
        point = p;
        checked = false;
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
        double intercept = point.getY() - slope * point.getX();
        return super.toString()
            .concat("[")
            .concat("checked=" + checked + ",")
            .concat("intercept=" + intercept + ",")
            .concat("slope=" + slope + ",")
            .concat("start=" + start + ",")
            .concat("end=" + end + ",")
            .concat("min=" + min + ",")
            .concat("max=" + max + ",")
            .concat("point=" + point + "]");
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * Getter and setter methods.
     */

    /**
     * Return the slope of the line containing the segment.
     * @return The slope of the line containing the segment.
     */
    public double getSlope() {
        return slope;
    }

    /**
     * Set the slope of the line containing the segment.
     * @param slope The slope of the line containing the segment.
     */
    public void setSlope(double slope) {
        this.slope = slope;       
    }

    /**
     * Get the minimum x-cohordinate of the segment. Used internally by the
     * layout algorithm.
     * @return The minimum x-cohordinate of the segment.
     */
    public double getStart() {
        return start;
    }

    /**
     * Set the minimum x-cohordinate of the segment. Used internally by the
     * layout algorithm.
     * @param start The minimum x-cohordinate of the segment.
     */
    public void setStart(double start) {
        this.start = start;
    }

    /**
     * Get the maximum x-cohordinate of the segment. Used internally by the
     * layout algorithm.
     * @return The maximum x-cohordinate of the segment.
     */
    public double getEnd() {
        return end;
    }

    /**
     * Set the maximum x-cohordinate of the segment. Used internally by the
     * layout algorithm.
     * @param end The maximum x-cohordinate of the segment.
     */
    public void setEnd(double end) {
        this.end = end;
    }
    
    /**
     * Get the minimum y-cohordinate of the segment. Used internally by the
     * layout algorithm.
     * @return The minimum y-cohordinate of the segment.
     */
    public double getMin() {
        return min;
    }

    /**
     * Set the minimum y-cohordinate of the segment. Used internally by the
     * layout algorithm.
     * @param min The minimum y-cohordinate of the segment.
     */
    public void setMin(double min) {
        this.min = min;
    }

    /**
     * Get the maximum y-cohordinate of the segment. Used internally by the
     * layout algorithm.
     * @return The maximum y-cohordinate of the segment.
     */
    public double getMax() {
        return max;
    }

    /**
     * Set the maximum y-cohordinate of the segment. Used internally by the
     * layout algorithm.
     * @param end The maximum y-cohordinate of the segment.
     */
    public void setMax(double max) {
        this.max = max;
    }
    
    /**
     * Set a point belonging to the segment forcing intercept recalculation.
     * Slope does't change.
     * @param p The point belonging to the segment.
     */
    public void setPoint(Point p) {
        point = p;
    }
    
    /**
     * Get the stored point belonging to the segment.
     * @return The stored point belonging to the segment.
     */
    public Point getPoint() {
        return point;
    }
    
    /**
     * Return true if a segment value has reached its minimum or maximum
     * allowed value. The layout minimization and maximization algorithm uses
     * this value to set the segment slope to a zero value.
     * @return True if checked, false otherwise.
     */
    public boolean isChecked() {
        return checked;
    }

    /**
     * Check or uncheck a segment. The layout minimization and maximization
     * algorithm uses this value to set the segment slope to a zero value.
     * @param True if checked, false otherwise.
     */
    public void setChecked(boolean checked) {
        this.checked = checked;
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * Functions.
     */
    
    /**
     * Add the slope and the intercept of the given segment to the slope and 
     * intercept of the current segment. The x-cohordinate of the contraint
     * point of the current segment doesn't change.
     * @param segment The segment beeing added.
     */
    public void add(Line segment) {
        slope += segment.getSlope();
        point.setY(point.getY() + segment.getPoint().getY());
    }
    
    /**
     * Evaluate the y-cooordinate by the given x-coordinate. Evaluation is based
     * on slope and constraint point.
     * @param xValue The x-coordinate.
     * @return The y-coordinate corresponding to the given x-coordinate.
     */
    public double evaluate(double xValue) {
        double value = slope * (xValue - point.getX()) + point.getY();
        return value;
    }
    
    /**
     * Factory method that return an array of initialized objects.
     * @param size The length of the array.
     * @return An array of initialized objects.
     */
    public static Line[] createArray(int size) {
        Line[] objs = new Line[size];
        for (int i = 0; i < size; i++) {
            objs[i] = new Line(0, 0);
        }
        return objs;
    }
    
    /**
     * Normalize the slope of the given array of segments.
     * @param lines The array of segments beeing normalized.
     */
    public static void normalize(Line[] lines) {
        
        // Evaluate the whole slope.
        
        double totalSlope = 0;
        for (int i = 0; i < lines.length; i++) {
            totalSlope += lines[i].getSlope();
        }
        
        // Normalize the segment slopes.
        
        if (totalSlope != 0) {
            for (int i = 0; i < lines.length; i++) {
                lines[i].setSlope(lines[i].getSlope() / totalSlope);
            }  
        }
    }
    
    /**
     * Return the maximum x-cohordinate correspondig to the point in which a
     * segment has reached its minimum allowed value. The evaluation is done
     * for all the segments.
     * @param lines The segments.
     * @return The maximum x-cohordinate correspondig to the point in which a
     *         segment has reached its minimum allowed value
     */
    public static final double getMaxStart(Line[] lines) {
        
        for (int i = 0; i < lines.length; i++) {
            lines[i].setChecked(false);
        }
        
        double maxStart = 0;
        for (int i = 0; i < lines.length; i++) {
            double m = lines[i].getSlope();
            double min = lines[i].getMin();
            double x0 = lines[i].getPoint().getX();
            double y0 = lines[i].getPoint().getY();
            double tmpStart = 0;
            if (m != 0) {
                tmpStart = (m * x0 - y0 + min) / m;
                if (tmpStart > maxStart) {
                    maxStart = tmpStart;
                    for (int k = 0; k < lines.length; k++) {
                        lines[k].setChecked(false);
                    }
                    lines[i].setChecked(true);
                } else if (tmpStart == maxStart) {
                    lines[i].setChecked(true);
                }
            }
        }
        return maxStart;
    }
    
    /**
     * Return the minimum x-cohordinate correspondig to the point in which a
     * segment has reached its maximum allowed value. The evaluation is done
     * for all the segments.
     * @param lines The segments.
     * @return The minimum x-cohordinate correspondig to the point in which a
     *         segment has reached its maximum allowed value
     */
    public static final double getMinEnd(Line[] lines) {
        
        for (int i = 0; i < lines.length; i++) {
            lines[i].setChecked(false);
        }
        
        double minEnd = Double.MAX_VALUE;
        for (int i = 0; i < lines.length; i++) {
            double m = lines[i].getSlope();
            double max = lines[i].getMax();
            double x0 = lines[i].getPoint().getX();
            double y0 = lines[i].getPoint().getY();
            double tmpEnd = Double.MAX_VALUE;;
            if (m != 0) {
                tmpEnd = (m * x0 - y0 + max) / m;
                if (tmpEnd  < minEnd ) {
                    minEnd  = tmpEnd ;
                    for (int k = 0; k < lines.length; k++) {
                        lines[k].setChecked(false);
                    }
                    lines[i].setChecked(true);
                } else if (tmpEnd  == minEnd) {
                    lines[i].setChecked(true);
                }
            }
        }
        return minEnd ;
    }
    
    /**
     * Return a clone of the object.
     * @return The clone of the current object.
     */
    public Object clone() {
        Line sg = new Line();
        sg.setPoint(new Point(point.getX(), point.getY()));
        sg.setSlope(slope);
        sg.setStart(start);
        sg.setEnd(end);
        sg.setMax(max);
        sg.setMin(min);
        sg.setChecked(checked);
        return sg;
    }
    
    /**
     * Return a clone of an array of segments: not only the array but clone 
     * every single segments in it.
     * @return A clone of an array of segments.
     */
    public static Line[] cloneArray(Line[] lines) {
        Line[] clone = new Line[lines.length];
        for (int i = 0; i < lines.length; i++) {
            clone[i] = (Line) lines[i].clone();
        }
        return clone;
    }
}
