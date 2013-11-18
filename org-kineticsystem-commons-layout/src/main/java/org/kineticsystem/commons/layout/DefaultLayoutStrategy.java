/*
 * DefaultLayoutStrategy.java
 *
 * Created on July 25, 2005, 7:52 PM
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

import java.awt.Component;

// Apache classes.

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The default implementation of the <code>LayoutStrategy</code> interface. The
 * algorithm looks for the first component free cell in the layout and than it
 * tries to add the component in that place. If the component is too big to fit
 * in the selected place, an exception is thrown and the main program is
 * terminated. The default searching algorithm starts from the upper left corner
 * and scans the layout from left to right and from top to bottom. You can alter
 * this flow by the <code>setFlow</code> method.
 * @author Giovanni Remigi
 * $Revision: 37 $
 */
public class DefaultLayoutStrategy implements LayoutStrategy {
    
    /* /////////////////////////////////////////////////////////////////////////
     * Public constants.
     */
    
    /** Constant indicating a left-right and top-down flow. */
    public static final int LEFT_RIGHT_TOP_DOWN_FLOW = 1;
    
    /** Constant indicating a right-left and top-down flow. */
    public static final int RIGHT_LEFT_TOP_DOWN_FLOW = 2;
    
    /** Constant indicating a left-right and bottom-up flow. */
    public static final int LEFT_RIGHT_BOTTOM_UP_FLOW = 3;
    
    /** Constant indicating a right-left and bottom-up flow. */
    public static final int RIGHT_LEFT_BOTTOM_UP_FLOW = 4;
    
    /** Constant indicating a top-down and left-right flow. */
    public static final int TOP_DOWN_LEFT_RIGHT_FLOW = 5;
    
    /** Constant indicating a top-down and right-left flow. */
    public static final int TOP_DOWN_RIGHT_LEFT_FLOW = 6;
    
    /** Constant indicating a bottom-up and left-right flow. */
    public static final int BOTTOM_UP_LEFT_RIGHT_FLOW = 7;
    
    /** Constant indicating a bottom-up and right-left flow. */
    public static final int BOTTOM_UP_RIGHT_LEFT_FLOW = 8;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Log framework.
     */
    
    /** Apache log framework. */
    private static Log logger = LogFactory.getLog(DefaultLayoutStrategy.class);
    
    /* /////////////////////////////////////////////////////////////////////////
     * Private variables.
     */
    
    /**
     * This variable is used to store the upper row of the last laid out
     * component.
     */
    private int row;
    
    /**
     * This variable is used to store the upper row of the last laid out
     * component.
     */
    private int col;
    
    /** The layout number of rows. */
    private int layoutRows;
    
    /** The layout number of columns. */
    private int layoutCols;
    
    /** A dummy grid used to support the layout algorithm. */
    private boolean[][] grid;
    
    /**
     * <p>This variable specifies the insertion flow of components onto the
     * layout.</p>
     * <p>
     * Possible values are:<br/>
     * <code>LEFT_RIGHT_TOP_DOWN_FLOW</code>,<br/>
     * <code>RIGHT_LEFT_TOP_DOWN_FLOW</code>,<br/>
     * <code>LEFT_RIGHT_BOTTOM_UP_FLOW</code>,<br/>
     * <code>RIGHT_LEFT_BOTTOM_UP_FLOW</code>,<br/>
     * <code>TOP_DOWN_LEFT_RIGHT_FLOW</code>,<br/>
     * <code>TOP_DOWN_RIGHT_LEFT_FLOW</code>,<br/>
     * <code>BOTTOM_UP_LEFT_RIGHT_FLOW</code>,<br/>
     * <code>BOTTOM_UP_RIGHT_LEFT_FLOW</code>.
     * </p>
     * <p>The default value is <code>LEFT_RIGHT_TOP_DOWN_FLOW</code>.</p>
     */
    private int flow;
    
    /**
     * Working variable indicating the vertical direction of the internal
     * component-free cell searching algorithm.
     */
    private int dr;
    
    /**
     * Working variable indicating the horizontal direction of the internal
     * component-free cell searching algorithm.
     */
    private int dc;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constructors.
     */
    
    /** Default constructor. */
    public DefaultLayoutStrategy() {
        flow = LEFT_RIGHT_TOP_DOWN_FLOW;
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * Setter methods.
     */

    /**
     * <p>Specify the insertion flow of components onto the layout.</p>
     * <p>
     * @param flow The component insertion flow. Possible values are:<br/>
     *     <code>LEFT_RIGHT_TOP_DOWN_FLOW</code>,<br/>
     *     <code>RIGHT_LEFT_TOP_DOWN_FLOW</code>,<br/>
     *     <code>LEFT_RIGHT_BOTTOM_UP_FLOW</code>,<br/>
     *     <code>RIGHT_LEFT_BOTTOM_UP_FLOW</code>,<br/>
     *     <code>TOP_DOWN_LEFT_RIGHT_FLOW</code>,<br/>
     *     <code>TOP_DOWN_RIGHT_LEFT_FLOW</code>,<br/>
     *     <code>BOTTOM_UP_LEFT_RIGHT_FLOW</code>,<br/>
     *     <code>BOTTOM_UP_RIGHT_LEFT_FLOW</code>.
     *     </p>
     *     <p>The default value is <code>LEFT_RIGHT_TOP_DOWN_FLOW</code>.</p>
     */
    public void setFlow(int flow) {
        this.flow = flow;
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * LayoutStrategy interface implementation.
     */
    
    /**
     * Initialize all structures needed by the layout algorithm.
     * @param layoutRows The layout number of rows.
     * @param layoutCols The layour number of columns.
     */
    public void initialize(int layoutRows, int layoutCols) {
        
        this.layoutRows = layoutRows;
        this.layoutCols = layoutCols;
        
        // Setup the dummy grid (boolean values are setted to false by default).
        
        grid = new boolean[layoutRows][layoutCols];
        switch (flow) {
            case LEFT_RIGHT_TOP_DOWN_FLOW:
            case TOP_DOWN_LEFT_RIGHT_FLOW:
                row = 0;
                col = 0;
                dr = 1;
                dc = 1;
                break;
            case RIGHT_LEFT_TOP_DOWN_FLOW:
            case TOP_DOWN_RIGHT_LEFT_FLOW:
                row = 0;
                col = layoutCols - 1;
                dr = 1;
                dc = -1;
                break;
            case LEFT_RIGHT_BOTTOM_UP_FLOW:
            case BOTTOM_UP_LEFT_RIGHT_FLOW:
                row = layoutRows - 1;
                col = 0;
                dr = -1;
                dc = 1;
                break;
            case RIGHT_LEFT_BOTTOM_UP_FLOW:
            case BOTTOM_UP_RIGHT_LEFT_FLOW:
                row = layoutRows - 1;
                col = layoutCols - 1;
                dr = -1;
                dc = -1;
                break;
        }
    }
    
    /**
     * <p>Place component onto the layout.</p>
     * <p>In the default behaviour the algorithm scans the layout from left to
     * right and from top to bottom to find the first free space where to place
     * the given component.</p>.
     * <p>You can change the default behaviour setting a different component
     * insertion flow.</p>
     * @param comp The component to be laid out.
     * @param constraints The component constraints.
     */
    public void addComponent(Component comp, Cell constraints) {
        
        if (comp == null) {
            logger.fatal("Cannot add to layout: cannot add null components!");
            System.exit(TetrisLayout.FATAL_ERROR);
        }
        
        // Find the first component-free cell in the layout grid.
        
        switch (flow) {
            case LEFT_RIGHT_TOP_DOWN_FLOW:
            case RIGHT_LEFT_TOP_DOWN_FLOW:
            case LEFT_RIGHT_BOTTOM_UP_FLOW:
            case RIGHT_LEFT_BOTTOM_UP_FLOW:
                while ((row >= 0) && (row < layoutRows) && grid[row][col]) {
                    col += dc;
                    if ((col < 0) || (col >= layoutCols)) {
                        row += dr;
                        col = (layoutCols + (col % layoutCols)) % layoutCols;
                    }
                }
                break;
            case TOP_DOWN_LEFT_RIGHT_FLOW:
            case TOP_DOWN_RIGHT_LEFT_FLOW:
            case BOTTOM_UP_LEFT_RIGHT_FLOW:
            case BOTTOM_UP_RIGHT_LEFT_FLOW:
                while ((col >= 0) && (col < layoutCols) && grid[row][col]) {
                    row += dr;
                    if ((row < 0) || (row >= layoutRows)) {
                        col += dc;
                        row = (layoutRows +(row % layoutRows)) % layoutRows;
                    }
                }
                break;
        }
        
        // Open constraints.
        
        int rows = constraints.getRows();
        int cols = constraints.getCols();
        
        // Maximize the component upon request.
        
        if (rows == Cell.MAX_VALUE) {
            int r = row;
            while ((r >= 0) && (r < layoutRows) && !grid[r][col]) {
                r += dr;
            }
            rows = Math.abs(r - row);
        }
        if (cols == Cell.MAX_VALUE) {
            int c = col;
            while ((c >= 0) && (c < layoutCols) && !grid[row][c]) {
                c += dc;
            }
            cols = Math.abs(c - col);
        }
        
        // Validate constraints.
        
        if (!(row >= 0) || !(row < layoutRows)
                || !((row + dr * (rows - 1)) >= 0) 
                || !((row + dr * (rows - 1)) < layoutRows)) {
            logger.fatal("Cannot add to layout: "
                + "component vertical dimension exceeds layout margins!");
            System.exit(TetrisLayout.FATAL_ERROR);
        }
        
        if (!(col >= 0) || !(col < layoutCols)
                || !((col + dc * (cols - 1)) >= 0) 
                || !((col + dc * (cols - 1)) < layoutCols)) {
            logger.fatal("Cannot add to layout: "
                + "component horizontal dimension exceeds layout margins!");
            System.exit(TetrisLayout.FATAL_ERROR);
        }
        
        // Span the component inside the layout grid.
        
        for (int c = 0; c < cols; c++) {
            for (int r = 0; r < rows; r++) {
                grid[row + dr * r][col + dc * c] = true;
            }
        }
        
        // Update constraints.
        
        if (dr == 1) {
            constraints.setRow(row);
        } else {
            constraints.setRow(row - rows + 1);
        }
        if (dc == 1) {
            constraints.setCol(col);
        } else {
            constraints.setCol(col - cols + 1);
        }
        constraints.setRows(rows);
        constraints.setCols(cols);
    }  
}
