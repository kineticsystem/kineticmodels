/*
 * Cell.java
 *
 * Created on February 8, 2004, 2:53 PM
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
 * This object memorizes the size of a rectangle. Any component in the
 * TestrisLayout lays on a grid. The Cell sets the component size in terms of
 * rows and columns.
 * @author Giovanni Remigi
 * $Revision: 7 $
 */
public class Cell implements Cloneable {
    
    /* /////////////////////////////////////////////////////////////////////////
     * Public constants for component anchor.
     */
    
    /** 
     * Place the component in the corner of its display area where the first
     * line of text on a page would normally begin for the current
     * <code>ComponentOrienation</code>.
     */
    public static final int FIRST_LINE_START = 0;
     
    /**
     * Place the component centered along the edge of its display area 
     * associated with the start of a page for the current
     * <code>ComponentOrienation</code>.
     */
    public static final int PAGE_START = 1;
    
    /**
     * Place the component in the corner of its display area where the first 
     * line of text on a page would normally end for the current 
     * <code>ComponentOrienation</code>.
     */
    public static final int FIRST_LINE_END = 2;
          
    /** 
     * Place the component centered along the edge of its display area
     * associated with the start of a page for the current
     * <code>ComponentOrienation</code>.
     */
    public static final int LINE_START = 3;
    
    /** Put the component in the center of its display area. */
    public static final int CENTER = 4;
    
    /**
     * Place the component centered along the edge of its display area where
     * lines of text would normally end for the current 
     * <code>ComponentOrienation</code>.
     */
    public static final int LINE_END = 5;
    
    /**
     * Place the component in the corner of its display area where the last line
     * of text on a page would normally start for the current
     * <code>ComponentOrienation</code>.
     */
    public static final int LAST_LINE_START = 6;
    
    /**
     * Place the component centered along the edge of its display area 
     * associated with the end of a page for the current
     * <code>ComponentOrienation</code>.
     */
    public static final int PAGE_END = 7;
    
    /**
     * Place the component in the corner of its display area where the last line
     * of text on a page would normally end for the current
     * <code>ComponentOrienation</code>.
     */
    public static final int LAST_LINE_END = 8;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Public constants for component filling.
     */
    
    /** Maintain component preferred dimensions. */
    public static final int NONE = 0;
    
    /** Extend component horizontal dimension to the max free space. */
    public static final int HORIZONTAL = 1;
    
    /** Extend component in vertical dimension. */
    public static final int VERTICAL = 2;
    
    /** Extend both component dimensions to the max free space. */
    public static final int BOTH = 3;
    
    /**
     * Extend both component dimensions to the max free space maintaining the
     * component ratio.
     */
    public static final int PROPORTIONAL = 4;
   
    /* /////////////////////////////////////////////////////////////////////////
     * Public constants for component dimension.
     */
    
    /** 
     * This constant, used as parameter in the constructor, tells the component
     * to maximize its dimensions.
     */
    public static int MAX_VALUE = 0;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Private variables.
     */
    
    /** The first rectangle row position. */
    private int row;

    /** The first rectangle column position. */
    private int col;
    
    /** Rectangle row number. */
    private int rows;
    
    /** Rectangle colum number. */
    private int cols;
    
   /** 
    * <p>This field determines where and how, within the display area, to place
    * the component.</p>
    * <p>
    * Possible values are:<br/>
    * <code>ALL</code>,<br/>
    * <code>FIRST_LINE_START</code>,<br/>
    * <code>PAGE_START</code>,<br/>
    * <code>FIRST_LINE_END</code>,<br/>
    * <code>LINE_START</code>,<br/>
    * <code>CENTER</code>,<br/>
    * <code>LINE_END</code>,<br/>
    * <code>LAST_LINE_START</code>,<br/>
    * <code>PAGE_END</code>,<br/>
    * <code>LAST_LINE_END</code>,<br/>
    * <code>ALL_FIRST_LINE</code>,<br/>
    * <code>ALL_LINE</code>,<br/>
    * <code>ALL_LAST_LINE</code>,<br/>
    * <code>ALL_LINE_START</code>,<br/>
    * <code>ALL_PAGE</code>,<br/>
    * <code>ALL_LINE_END</code>.
    * </p>
    * <p>The default value is <code>PAGE_START</code>.</p>
    */
    private int anchor;
    
    /**
     * <p>
     * Possible values are:<br/>
     * <code>NONE</code>,<br/>
     * <code>HORIZONTAL</code>,<br/>
     * <code>VERTICAL</code>,<br/>
     * <code>BOTH</code>,<br/>
     * <code>PROPORTIONAL</code>.
     * </p>
     * <p>The default value is <code>BOTH</code>.</p>
     */
    private int fill;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constructors.
     */
    
    /** Default constructor. */
    public Cell() {
        this(0, 0, 1, 1);
    }
    
    /** 
     * Create a new rectangle.
     * @param row The first rectangle row position.
     * @param col The first rectangle column position.
     * @param rows The rectangle row number.
     * @param cols The rectangle colum number.
     */
    private Cell(int row, int col, int rows, int cols) {
        
        if ((rows < 0) || (cols < 0)) {
            throw new IllegalArgumentException("Cannot create instance: "
                + "cell must have positive row and column numbers!");
        }
        this.row = row;
        this.col = col;
        this.rows = rows;
        this.cols = cols;
        this.fill = BOTH;
        this.anchor = FIRST_LINE_START;
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
            .concat("row=" + row + ",")
            .concat("col=" + col + ",")
            .concat("rows=" + rows + ",")
            .concat("cols=" + cols + ",")
            .concat("anchor=" + anchor + ",")
            .concat("fill=" + fill + "]");
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * Getter and setter methods.
     */
    
    /**
     * @return Possible values are:<br/>
     *     <code>NONE</code>,<br/>
     *     <code>HORIZONTAL</code>,<br/>
     *     <code>VERTICAL</code>,<br/>
     *     <code>BOTH</code>,<br/>
     *     <code>PROPORTIONAL</code>.
     *     <p>The default value is <code>BOTH</code>.</p>
     */
    public int getFill() {
        return fill;
    }
    
    /**
     * @param fill Possible values are:<br/>
     *     <code>NONE</code>,<br/>
     *     <code>HORIZONTAL</code>,<br/>
     *     <code>VERTICAL</code>,<br/>
     *     <code>BOTH</code>,<br/>
     *     <code>PROPORTIONAL</code>.
     *     <p>The default value is <code>BOTH</code>.</p>
     */
    public void setFill(int fill) {
        if ((fill < 0) || (fill > 4)) {
            throw new IllegalArgumentException("Cannot set parameter: "
                + "costant out of range!");
        }
        this.fill = fill;
    }
    
   /** 
    * <p>Get a variable that determines where and how, within the display
    * area, to place the component.</p>
    * @return Possible values are:<br/>
    *     <code>ALL</code>,<br/>
    *     <code>FIRST_LINE_START</code>,<br/>
    *     <code>PAGE_START</code>,<br/>
    *     <code>FIRST_LINE_END</code>,<br/>
    *     <code>LINE_START</code>,<br/>
    *     <code>CENTER</code>,<br/>
    *     <code>LINE_END</code>,<br/>
    *     <code>LAST_LINE_START</code>,<br/>
    *     <code>PAGE_END</code>, <br/>
    *     <code>LAST_LINE_END</code>,<br/>
    *     <code>ALL_FIRST_LINE</code>,<br/>
    *     <code>ALL_LINE</code>,<br/>
    *     <code>ALL_LAST_LINE</code>,<br/>
    *     <code>ALL_LINE_START</code>,<br/>
    *     <code>ALL_PAGE</code>,<br/>
    *     <code>ALL_LINE_END</code>.
    *     <p>The default value is <code>PAGE_START</code>.</p>
    */
    public int getAnchor() {
        return anchor;
    }
    
   /** 
    * <p>Set the variable that determines where and how, within the display
    * area, to place the component.</p>
    * @param anchor Possible values are:<br/>
    *     <code>ALL</code>,<br/>
    *     <code>FIRST_LINE_START</code>,<br/>
    *     <code>PAGE_START</code>,<br/>
    *     <code>FIRST_LINE_END</code>,<br/>
    *     <code>LINE_START</code>,<br/>
    *     <code>CENTER</code>,<br/>
    *     <code>LINE_END</code>,<br/>
    *     <code>LAST_LINE_START</code>,<br/>
    *     <code>PAGE_END</code>,<br/>
    *     <code>LAST_LINE_END</code>,<br/>
    *     <code>ALL_FIRST_LINE</code>,<br/>
    *     <code>ALL_LINE</code>,<br/>
    *     <code>ALL_LAST_LINE</code>,<br/>
    *     <code>ALL_LINE_START</code>,<br/>
    *     <code>ALL_PAGE</code>,<br/>
    *     <code>ALL_LINE_END</code>.
    */
    public void setAnchor(int anchor) {
        if ((anchor < 0) || (anchor > 8)) {
            throw new IllegalArgumentException("Cannot set parameter: "
                + "costant out of range!");
        }
        this.anchor = anchor;
    }
    
    /** 
     * Return the first rectangle row position.
     * @return The first rectangle row position.
     */
    public int getRow() {
        return row;
    }
    
    /** 
     * Set the first rectangle row position.
     * @param row The first rectangle row position.
     */
    public void setRow(int row) {
        this.row = row;
    }
    
    /** 
     * Return the first rectangle column position.
     * @return The first rectangle column position.
     */
    public int getCol() {
        return col;
    }
    
    /** 
     * Set the first rectangle column position.
     * @param col The first rectangle column position.
     */
    public void setCol(int col) {
        this.col = col;
    }
    
    /** 
     * Return the rectangle row number.
     * @return The rectangle row number.
     */
    public int getRows() {
        return rows;
    }
    
    /** 
     * Set the the rectangle row number.
     * @param rows The rectangle row number.
     */
    public void setRows(int rows) {
        if ((rows < 0)) {
            throw new IllegalArgumentException("Cannot set parameter: "
                + "needs positive row number!");
        }
        this.rows = rows;
    }
    
    /** 
     * Return the rectangle column number.
     * @return The rectangle column number.
     */
    public int getCols() {
        return cols;
    }
    
    /** 
     * Set the the rectangle column number.
     * @param cols The rectangle column number.
     */
    public void setCols(int cols) {
        if ((cols < 0)) {
            throw new IllegalArgumentException("Cannot set parameter: "
                + "needs positive column number!");
        }
        this.cols = cols;
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * Cloneable interface implementation.
     */
    
    /**
     * Return a clone of the object.
     * @return The clone of the current object.
     */
    public Object clone() {
        Cell cell = new Cell();
        cell.setRow(row);
        cell.setCol(col);
        cell.setRows(rows);
        cell.setCols(cols);
        cell.setAnchor(anchor);
        cell.setFill(fill);
        return cell;
    }
}