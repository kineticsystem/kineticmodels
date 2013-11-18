/*
 * TetrisLayout.java
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

// Java classes.

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

// Apache classes.

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Tetris layout.
 * @author Giovanni Remigi
 * $Revision: 148 $
 */
public class TetrisLayout implements LayoutManager2 {
    
    /* /////////////////////////////////////////////////////////////////////////
     * Log framework.
     */
    
    /** Apache log framework. */
    private static Log logger = LogFactory.getLog(TetrisLayout.class);
    
    /* /////////////////////////////////////////////////////////////////////////
     * Private constants.
     */
    
    /** Exit constant returned by a fatal error. */
    static final int FATAL_ERROR = -1;
    
    /** Default space between components. */
    private static final int DEFAULT_GAP = 2;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Testing layout colors.
     */
    
    /** Color used to draw the spanning layout in a debugging session. */
    private Color spanningLayoutColor;
    
    /** Color used to draw the internal layout in a debugging session. */
    private Color internalLayoutColor;
    
    /** Color used to draw the external layout in a debugging session. */
    private Color externalLayoutColor;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Private variables.
     */
    
    /** Vertical spaces between rows. */
    private Gap[] rGaps;
    
    /** Horizontal spaces between columns. */
    private Gap[] cGaps;
    
    /** Layout columns. */
    private Bar[] cBars;
    
    /** Layout rows. */
    private Bar[] rBars;
    
    /** Column size functions used in laying out components. */
    private SplittedLine[] cSizes;
    
    /** Row size functions used in laying out components. */
    private SplittedLine[] rSizes;
    
    /**
     * Component constraints: this map keeps track of the relation between a
     * component and its layout contraints.
     */
    private HashMap<Component, Cell> constraintsMap;
    
    /**
     * A copy of all component dimensions: preferred, minimum and maximum
     * ones.
     */
    private HashMap<Component, Size> componentSizes;
    
    /** The component incapsulating the layout algorithm. */
    private LayoutStrategy strategy;
    
    /** The size of the container. */
    private Dimension containerSize;
    
    /** The width of the container without gaps and border. */
    private double preferredGapFreeWidth;
    
    /** The height of the container without gaps and border. */
    private double preferredGapFreeHeight;
    
    /** The width of the container without gaps and border. */
    private double minGapFreeWidth;
    
    /** The height of the container without gaps and border. */
    private double minGapFreeHeight;
    
    /** The width of the container without gaps and border. */
    private double maxGapFreeWidth;
    
    /** The height of the container without gaps and border. */
    private double maxGapFreeHeight;
    
    /** The container beeing laid out. */
    private Container container;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constructors.
     */
    
    /**
     * Create a new layout with given rows and columns.
     * @param rows The number of rows.
     * @param cols The number of columns.
     */
    public TetrisLayout(int rows, int cols) {
        
        if ((rows <= 0) || (cols <= 0)) {
            logger.fatal("Cannot create layout: "
                + "need positive row and column number!");
            System.exit(TetrisLayout.FATAL_ERROR);
        }
        
        // Setup testing colors.
        
        spanningLayoutColor = Color.ORANGE;
        internalLayoutColor = Color.BLUE;
        externalLayoutColor = Color.RED;
        
        // Setup global variables and structures.
        
        cBars = Bar.createArray(cols);
        rBars = Bar.createArray(rows);
        
        // Set horizontal gaps and positions.

        cSizes = SplittedLine.createArray(cols + 1);
        cGaps = Gap.createArray(cols + 1);
        for (int i = 0; i < cols + 1; i++) {
            cGaps[i].setSize(DEFAULT_GAP);
        }
        cGaps[0].setSize(0);
        cGaps[cols].setSize(0);
        
        // Set vertical gaps and positions.
        
        rSizes = SplittedLine.createArray(rows + 1);
        rGaps = Gap.createArray(rows + 1);
        for (int i = 0; i < rows + 1; i++) {
            rGaps[i].setSize(DEFAULT_GAP);
        }
        rGaps[0].setSize(0);
        rGaps[rows].setSize(0);
        
        constraintsMap = new HashMap<Component, Cell>();
        componentSizes = new HashMap<Component, Size>();
        
        preferredGapFreeWidth = 0;
        preferredGapFreeHeight = 0;
        
        // Setup default layout algorithm.
        
        strategy = new DefaultLayoutStrategy();
        strategy.initialize(rBars.length, cBars.length);
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * Setter methods.
     */
    
    /**
     * Implement <code>LayoutStrategy</code> interface to create your own
     * layout algorithm and plug it in before adding components.
     * @param strategy The <code>LayoutStrategy</code> implementing you custom
     *        layout algorithm.
     */
    public void setLayoutStrategy(LayoutStrategy strategy) {
        
        if (strategy == null) {
            this.strategy = new DefaultLayoutStrategy();
        } else {
            this.strategy = strategy;
        }
        strategy.initialize(rBars.length, cBars.length);
    }
    
    /**
     * Set space between components.
     * @param gap Absolute space between components.
     */
    public void setGap(int gap) {
        
        if (gap < 0) {
            logger.fatal("Cannot set parameter: "
                + "space between component must be a positive number!");
            System.exit(TetrisLayout.FATAL_ERROR);
        }
        for (int i = 1; i < cBars.length; i++) {
            cGaps[i].setSize(gap);
        }
        for (int i = 1; i < rBars.length; i++) {
            rGaps[i].setSize(gap);
        }
    }
    
    /**
     * Set vertical space between components.
     * @param gap Absolute vertical space between components.
     */
    public void setVerticalGap(int gap) {
        
        if (gap < 0) {
            logger.fatal("Cannot set parameter: "
                + "space between component must be a positive number!");
            System.exit(TetrisLayout.FATAL_ERROR);
        }
        for (int i = 1; i < rBars.length; i++) {
            rGaps[i].setSize(gap);
        }
    }
    
    /**
     * Set vertical space between components.
     * @param gapIndex the index of the gap to set.
     * @param gap Absolute vertical space between components.
     */
    public void setVerticalGap(int gapIndex, int gap) {
        
        if ((gapIndex < 0) || (gapIndex > rBars.length)) {
            logger.fatal("Cannot set parameter: "
                + "gap index " + gapIndex + " out of range!");
            System.exit(TetrisLayout.FATAL_ERROR);
        }
        if (gap < 0) {
            logger.fatal("Cannot set parameter: "
                + "space between component must be a positive number!");
            System.exit(TetrisLayout.FATAL_ERROR);
        }
        rGaps[gapIndex].setSize(gap);
    }
    
    /**
     * Set horizontal space between components.
     * @param gap Absolute horizontal space between components.
     */
    public void setHorizontalGap(int gap) {
        
        if (gap < 0) {
            logger.fatal("Cannot set parameter: "
                + "space between component must be a positive number!");
            System.exit(TetrisLayout.FATAL_ERROR);
        }
        for (int i = 1; i < cBars.length; i++) {
            cGaps[i].setSize(gap);
        }
    }
    
    /**
     * Set horizontal space between components.
     * @param gapIndex the index of the gap to set.
     * @param gap Absolute horizontal space between components.
     */
    public void setHorizontalGap(int gapIndex, int gap) {
        
        if ((gapIndex < 0) || (gapIndex > cBars.length)) {
            logger.fatal("Cannot set parameter: "
                + "gap index " + gapIndex + " out of range!");
            System.exit(TetrisLayout.FATAL_ERROR);
        }
        if (gap < 0) {
            logger.fatal("Cannot set parameter: "
                + "space between component must be a positive number!");
            System.exit(TetrisLayout.FATAL_ERROR);
        }
        cGaps[gapIndex].setSize(gap);
    }
    
    /**
     * Set the weight of a column. This parameter effects the way a given column
     * is resized during the overall resize of the layout.
     * @param col The given column index.
     * @param weight A positive value determining the way the given column is
     *        resized during the resize of the layout. If this value is 0, the
     *        column is not resized.
     */
    public void setColWeight(int col, double weight) {
        
        if ((col < 0) || (col > cBars.length)) {
            logger.fatal("Cannot set parameter: column index out of range!");
            System.exit(TetrisLayout.FATAL_ERROR);
        }
        if (weight < 0) {
            logger.fatal("Cannot set parameter: "
                + "column" + col + " weight must be a positive value!");
            System.exit(TetrisLayout.FATAL_ERROR);
        }
        cBars[col].setWeight(weight);
    }
    
    /**
     * Set the weight of a row. This parameter effects the way a given row is
     * resized during the overall resize of the layout.
     * @param row The given row index.
     * @param weight A positive value determining the way the given row is
     *        resized during the resize of the layout. If this value is 0, the
     *        row is not resized.
     */
    public void setRowWeight(int row, double weight) {
        
        if ((row < 0) || (row > rBars.length)) {
            logger.fatal("Cannot set parameter: row index out of range!");
            System.exit(TetrisLayout.FATAL_ERROR);
        }
        if (weight < 0) {
            logger.fatal("Cannot set parameter: "
                + "row " + row + " weight must be a positive value!");
            System.exit(TetrisLayout.FATAL_ERROR);
        }
        rBars[row].setWeight(weight);
    }
    
    /**
     * <p>Add a <code>Connector</code> to the specified column.</p>
     * <p>In a first step, the preferred and minimum size of a layout column is
     * evaluated using preferred and minimum sizes of laid out components.</p>
     * <p>After this first evaluation you can use a connector to force a column
     * size based on precalculated sizes and gaps of the other columns.</p>
     * <p>Remember that a column size can never go under the minimum 
     * precalculated column size.</p>
     * @param col The given column index.
     * @param connector The connector evaluating the column size.
     */
    public void setColConnector(int col, Connector connector) {
        cBars[col].setConnector(connector);
    }
    
    /**
     * <p>Add a <code>Connector</code> to the specified row.</p>
     * <p>In a first step, the preferred and minimum size of a layout row is
     * evaluated using preferred and minimum sizes of laid out components.</p>
     * <p>After this first evaluation you can use a connector to force a row
     * size based on precalculated sizes and gaps of the other rows.</p>
     * <p>Remember that a row size can never go under the minimum precalculated
     * row size.</p>
     * @param row The given row index.
     * @param connector The connector evaluating the row size.
     */
    public void setRowConnector(int row, Connector connector) {
        rBars[row].setConnector(connector);
    }
    
    /** 
     * Set the specified component constraint object.
     * @param comp The component whom contraints are applied to.
     * @param constraints The component constaints.
     */
    public void setConstraints(Component comp, Cell constraints) {
        
        Cell cell = (Cell) constraints.clone();
        
        // Lay out the component using the Strategy Pattern.
        
        strategy.addComponent(comp, cell);
        
        if (comp == null) {
            logger.fatal("Cannot add to layout: cannot add null components!");
            System.exit(TetrisLayout.FATAL_ERROR);
        }

        // Store the component.

        constraintsMap.put(comp, cell);
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * Private methods.
     */
    
    /**
     * Get component at given layout position looking inside the component list.
     * @param row A row in the layout.
     * @param col A column in the layout.
     */
    private Component componentAt(int row, int col) {
        
        boolean found = false;
        Component comp = null;
        Iterator<Map.Entry<Component,Cell>> iter 
            = constraintsMap.entrySet().iterator();
        while (!found && iter.hasNext()) {
            Map.Entry<Component,Cell> entry = iter.next();
            comp = (Component) entry.getKey();
            Cell cell = (Cell) entry.getValue();
            if ((row >= cell.getRow())
                    && (row < (cell.getRow() + cell.getRows()))
                    && (col >= cell.getCol())
                    && (col < (cell.getCol() + cell.getCols()))) {
                found = true;
            }
        }
        return comp;
    }
    
    /**
     * Calculate the preferred size dimensions for the specified panel given
     * the components in the specified parent container. Calculates incremental
     * column width rates and row heights, dimensions used during component
     * laying out. 
     * @param parent The component to be laid out.
     */
    private void setup(Container parent) {
        
        container = parent;
        Insets insets = parent.getInsets();
        
        // FIRST PHASE: DEFAULT EVALUATION OF COLUMN AND ROW SIZES.
        
        // Evaluate column widths.
        
        for (int c = 0; c < cBars.length; c++) {
            if (cBars[c].getSize() == Bar.COMPONENT_PREFERRED_SIZE) {
                for (int r = 0; r < rBars.length; r++) {
                    Component comp = componentAt(r, c);
                    Cell cell = (Cell) constraintsMap.get(comp);
                    if (comp.isVisible()
                            && ((cell.getCols() + cell.getCol() - 1) == c)) {
                        Size cd = (Size) componentSizes.get(comp);
                        cBars[c].setPreferredSize(Math.max(
                            cd.getPreferredWidth(),
                            cBars[c].getPreferredSize())
                        );
                        cBars[c].setMinimumSize(Math.max(
                            cd.getMinimumWidth(),
                            cBars[c].getMinimumSize())
                        );
                        cBars[c].setMaximumSize(Double.MAX_VALUE);
                    }
                }
            } else {
                cBars[c].setPreferredSize(cBars[c].getSize());
            }
            
            // Check all components containing column c and store their sizes.
        
            for (Map.Entry<Component,Cell> entry : constraintsMap.entrySet()) {

                Component comp = (Component) entry.getKey();
                Cell cell = (Cell) entry.getValue();
                if (((cell.getCol() + cell.getCols()) >= c)
                        && (cell.getCol() <= c)) {
                    Size d = (Size) componentSizes.get(comp);
                    d.setPreferredWidth(d.getPreferredWidth() 
                        - cBars[c].getPreferredSize());
                    d.setMinimumWidth(d.getMinimumWidth() 
                        - cBars[c].getMinimumSize());
                }
            }
        }
        
        // Evaluate row heights.
        
        for (int r = 0; r < rBars.length; r++) {
            if (rBars[r].getSize() == Bar.COMPONENT_PREFERRED_SIZE) {
                for (int c = 0; c < cBars.length; c++) {
                    Component comp = componentAt(r, c);
                    Cell cell = (Cell) constraintsMap.get(comp);
                    if (comp.isVisible()
                            && ((cell.getRows() + cell.getRow() - 1) == r)) {
                        Size cd = (Size) componentSizes.get(comp);
                        rBars[r].setPreferredSize(Math.max(
                            cd.getPreferredHeight(),
                            rBars[r].getPreferredSize())
                        );
                        rBars[r].setMinimumSize(Math.max(
                            cd.getMinimumHeight(),
                            rBars[r].getMinimumSize())
                        );
                        rBars[r].setMaximumSize(Double.MAX_VALUE);
                    }
                }
            } else {
                rBars[r].setWeight(rBars[r].getSize());
            } 
        
            // Check all components containing row r and store their sizes.
            
            for (Map.Entry<Component,Cell> entry : constraintsMap.entrySet()) {
                
                Component comp = (Component) entry.getKey();
                Cell cell = (Cell) entry.getValue();
                if (((cell.getRow() + cell.getRows()) >= r) 
                        && (cell.getRow() <= r)) {
                    Size d = (Size) componentSizes.get(comp);
                    d.setPreferredHeight(d.getPreferredHeight() 
                        - rBars[r].getPreferredSize());
                    d.setMinimumHeight(d.getMinimumHeight() 
                        - rBars[r].getMinimumSize());
                }
            }
        }
        
        // SECOND PHASE: OVERRIDE DEFAULT EVALUATION OF COLUMN AND ROW SIZES.
        
        /*
         * Override previous evaluation of column and row sizes using
         * connectors.
         */
        
        initBarConnectors(cBars, cGaps);
        initBarConnectors(rBars, rGaps);
        
        // Evaluates container widths and heights.
        
        for (int i = 0; i < cBars.length; i++) {
            minGapFreeWidth += cBars[i].getMinimumSize();
            maxGapFreeWidth += cBars[i].getMaximumSize();
            preferredGapFreeWidth += cBars[i].getPreferredSize();
        }
        for (int i = 0; i < rBars.length; i++) {
            minGapFreeHeight += rBars[i].getMinimumSize();
            maxGapFreeHeight += rBars[i].getMaximumSize();
            preferredGapFreeHeight += rBars[i].getPreferredSize();
        }
        
        // Adds insets and gaps.
        
        initGaps(cGaps);
        initGaps(rGaps);
        
        double preferredWidth = preferredGapFreeWidth + insets.left 
            + insets.right + cGaps[cBars.length].getIncSize();
        double preferredHeight = preferredGapFreeHeight + insets.top
            + insets.bottom + rGaps[rBars.length].getIncSize();
        
        // Sets preferred container size.
        
        containerSize = new Dimension((int) Math.round(preferredWidth),
            (int) Math.round(preferredHeight));
        
        // THIRD PHASE: EVALUATE ROW AND COLUMN SIZE FUNCTIONS.
        
        initSizes(cSizes, cBars, preferredGapFreeWidth);
        initSizes(rSizes, rBars, preferredGapFreeHeight);
    }
    
    /**
     * Evaluate the incremental sum of a sequence of gaps starting from the left
     * and moving to the right.
     * @param gaps The array of gaps.
     */
    private void initGaps(Gap[] gaps) {
        gaps[0].setIncSize(gaps[0].getSize());
        for (int i = 1; i < gaps.length; i++) {
            gaps[i].setIncSize(gaps[i].getSize() + gaps[i - 1].getIncSize());
        }
    }
    
    /**
     * Override the default preferred size of columns and rows using
     * <code>Connector</code> objects given in the initialization phase.
     * A preferred size can't go under the previously evaluated minimum size.
     * @param bars The rows or columns we want to initialize.
     * @param gaps Gaps between rows or columns.
     */
    private void initBarConnectors(Bar[] bars, Gap[] gaps) {
        
        double[] tmpPreferredSizes;
        double[] tmpSizes;
        int[] tmpGaps;
        
        tmpPreferredSizes = new double[bars.length];
        tmpSizes = new double[bars.length];
        tmpGaps = new int[bars.length + 1];
        for (int i = 0; i < bars.length; i++) {
            tmpSizes[i] = bars[i].getPreferredSize();
        }
        for (int i = 0; i < bars.length + 1; i++) {
            tmpGaps[i] = gaps[i].getSize();
        }
        
        // Don't go under previously evaluated minimum sizes.
        
        for (int i = 0; i < bars.length; i++) {
            if (bars[i].getConnector() != null) {
                tmpPreferredSizes[i] = Math.max(
                    bars[i].getConnector().getSize(tmpSizes, tmpGaps),
                    bars[i].getMinimumSize()
                );
            }
        }
        for (int i = 0; i < bars.length; i++) {
            if (bars[i].getConnector() != null) {
                bars[i].setPreferredSize(tmpPreferredSizes[i]);
            }
        }
    }
    
    /**
     * Evaluate functions decribing row and column sizes.
     * @param splitted The array of object describing row and column sizes.
     * @param bars The array of rows or columns.
     * @param preferredGapFreeSize The current size of the main panel exluding
     *        gaps and insets.
     */
    private void initSizes(SplittedLine[] splitted,
            Bar[] bars, double preferredGapFreeSize) {
        
        double maxStart = preferredGapFreeSize;
        double tmpEnd = preferredGapFreeSize;
        
        double minEnd = preferredGapFreeSize;
        double tmpStart = preferredGapFreeSize;

        Line[] lines = Line.createArray(bars.length + 1);
        for (int i = 1; i < bars.length + 1; i++) {
            Point p = new Point(preferredGapFreeSize,
                bars[i - 1].getPreferredSize());
            lines[i].setSlope(bars[i - 1].getWeight());
            lines[i].setPoint(p);
            lines[i].setMax(bars[i - 1].getMaximumSize());
            lines[i].setMin(bars[i - 1].getMinimumSize());
        }
        Line[] clone;
        
        clone = Line.cloneArray(lines);
        while (maxStart != 0) {
            Line.normalize(clone);
            maxStart = Line.getMaxStart(clone);
            
            if (maxStart != tmpEnd) {
                Line s = new Line(0, new Point(tmpEnd, 0));
                s.setStart(maxStart);
                s.setEnd(tmpEnd);
                tmpEnd = maxStart;
                for (int i = 0; i < clone.length; i++) {
                    s.add(clone[i]);
                    splitted[i].add((Line) s.clone());
                    clone[i].setPoint(new Point(maxStart,
                        clone[i].evaluate(maxStart)));
                }
            }
            for (int i = 0; i < clone.length; i++) {
                if (clone[i].isChecked()) {
                    clone[i].setSlope(0);
                }
            }
        }
        
        clone = Line.cloneArray(lines);
        while (minEnd != Double.MAX_VALUE) {
            Line.normalize(clone);
            minEnd = Line.getMinEnd(clone);
            
            if (tmpStart != minEnd) {
                Line s = new Line(0, new Point(tmpStart, 0));
                s.setStart(tmpStart);
                s.setEnd(minEnd);
                tmpStart = minEnd;
                for (int i = 0; i < clone.length; i++) {
                    s.add(clone[i]);
                    splitted[i].add((Line) s.clone());
                    clone[i].setPoint(new Point(minEnd,
                        clone[i].evaluate(minEnd)));
                }
            }
            for (int i = 0; i < clone.length; i++) {
                if (clone[i].isChecked()) {
                    clone[i].setSlope(0);
                }
            }
        }
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * LayoutManager2 interface implementation.
     */
    
    /**
     * Add the specified component to the layout, using the specified
     * constraint object.
     * @param comp The component to be added
     * @param constraints Where/how the component is added to the layout.
     */
    public void addLayoutComponent(Component comp, Object constraints) {
        
        if (constraints == null) {
            constraints = new Cell();
        }
        if (constraints instanceof Cell) {
            
            Cell cell = (Cell) constraints;
            if (cell.getRows() < 0 || cell.getCols() < 0) {
                logger.fatal("Cannot add to layout: "
                    + "cell must have positive row and column number!");
                System.exit(TetrisLayout.FATAL_ERROR);
            }
            setConstraints(comp, cell);
            
            // Store a copy of all component dimensions.
            
            Size cd = new Size();
            cd.setMaximumWidth(comp.getMaximumSize().getWidth());
            cd.setMaximumHeight(comp.getMaximumSize().getHeight());
            cd.setMinimumWidth(comp.getMinimumSize().getWidth());
            cd.setMinimumHeight(comp.getMinimumSize().getHeight());
            cd.setPreferredWidth(comp.getPreferredSize().getWidth());
            cd.setPreferredHeight(comp.getPreferredSize().getHeight());
            
            // Check dimensions.
            
            if (cd.getMinimumWidth() > cd.getPreferredWidth()) {
                logger.warn("Component minimum width is greater than preferred"
                    + " width: set value to preferred size!\n" + cd);
                cd.setMinimumWidth(cd.getPreferredWidth());
            }
            if (cd.getMaximumWidth() < cd.getPreferredWidth()) {
                logger.warn("Component maximum width is less than preferred"
                    + " width: set value to preferred size!\n" + cd);
                cd.setMaximumWidth(cd.getPreferredWidth());
            }
            if (cd.getMinimumHeight() > cd.getPreferredHeight()) {
                logger.warn("Component minimum height is greater than preferred"
                    + " height: set value to preferred size!\n" + cd);
                cd.setMinimumHeight(cd.getPreferredHeight());
            }
            if (cd.getMaximumHeight() < cd.getPreferredHeight()) {
                logger.warn("Component maximum height is less than preferred"
                    + " height: set value to preferred size!\n" + cd);
                cd.setMaximumHeight(cd.getPreferredHeight());
            }
            
            componentSizes.put(comp, cd);
            
        } else if (constraints != null) {
            logger.fatal("Cannot add to layout: constraint must be a Cell!");
            System.exit(TetrisLayout.FATAL_ERROR);
        }
    }
    
    /**
     * Return the alignment along the x axis. This specifies how the component
     * would like to be aligned relative to other components.  The value should
     * be a number between 0 and 1 where 0 represents alignment along the
     * origin, 1 is aligned the furthest away from the origin, 0.5 is centered,
     * etc.
     * @param target The component container.
     * @return A number between 0 and 1 representing the alignment.
     */
    public float getLayoutAlignmentX(Container target) {
        return 0.5f;
    }
    
    /**
     * Return the alignment along the y axis. This specifies how the component
     * would like to be aligned relative to other components.  The value should
     * be a number between 0 and 1 where 0 represents alignment along the
     * origin, 1 is aligned the furthest away from the origin, 0.5 is centered,
     * etc.
     * @param target The component container.
     * @return A number between 0 and 1 representing the alignment.
     */
    public float getLayoutAlignmentY(Container target) {
        return 0.5f;
    }
    
    /**
     * Invalidate the layout, indicating that if the layout manager has cached
     * information it should be discarded (not implemented yet).
     * @param target The component container.
     */
    public void invalidateLayout(Container target) {
        // Not implemented.
    }
    
    /** 
     * Return the maximum size of this container.
     * @param parent The container to be laid out.
     * @return The maximum container size dimension.
     */
    public Dimension maximumLayoutSize(Container parent) {
        if (containerSize == null) {
            setup(parent);
        }
        
        Insets insets = parent.getInsets();
        double maxWidth = maxGapFreeWidth + insets.left 
            + insets.right + cGaps[cBars.length].getIncSize();
        double maxHeight = maxGapFreeHeight + insets.top
            + insets.bottom + rGaps[rBars.length].getIncSize();
        
        // Return minimal container size.
        
        Dimension d = new Dimension((int) Math.round(maxWidth),
            (int) Math.round(maxHeight));
        return d;
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * LayoutManager interface implementation.
     */
    
    /**
     * Add the specified component with the specified name to the layout. This
     * does nothing in TetrisLayout, since constraints are required.
     * @param name The component name.
     * @param comp The component to be inserted.
     */
    public void addLayoutComponent(String name, Component comp) {
        // Do nothing.
    }
    
    /**
     * Lay out components in the specified container.
     * @param parent The container which needs to be laid out.
     */
    public void layoutContainer(Container parent) {
        
        /* 
         * This method synchronizes on the tree lock of the component. This tree
         * lock is an object that can be used to provide thread-safe access to
         * the layout manager in case different threads are simultaneously
         * adding or removing components. The tree lock object is used as a
         * synchronization point for all of the methods associated with laying
         * out containers and invalidating components, and it is good
         * programming practice to use it to ensure a thread-safe
         * implementation.
         */
        
        synchronized (parent.getTreeLock()) {
            
            // Layout components.
            
            if (containerSize == null) {
                setup(parent);
            }
            
            Insets insets = parent.getInsets();

            int componentNumber = parent.getComponentCount();
            if (componentNumber == 0) {
                return;
            }
            
            Dimension size = parent.getSize();
            
            cGaps[0].setIncSize(cGaps[0].getSize());
            for (int i = 1; i < cBars.length + 1; i++) {
                cGaps[i].setIncSize(cGaps[i].getSize() 
                    + cGaps[i - 1].getIncSize());
            }
            rGaps[0].setIncSize(rGaps[0].getSize());
            for (int i = 1; i < rBars.length + 1; i++) {
                rGaps[i].setIncSize(rGaps[i].getSize() 
                    + rGaps[i - 1].getIncSize());
            }
            
            int actualGapFreeWidth = Math.max(size.width - insets.left
                - insets.right - cGaps[cBars.length].getIncSize(), 0);
            int actualGapFreeHeight = Math.max(size.height - insets.top 
                - insets.bottom - rGaps[rBars.length].getIncSize(), 0);
            
            for (Map.Entry<Component,Cell> entry : constraintsMap.entrySet()) {

                Component comp = (Component) entry.getKey();
                Cell cell = (Cell) entry.getValue();
                if (cell != null) {
                    
                    // Actual cell position.
                    
                    int x0 = (int) Math.round(
                        cSizes[cell.getCol()]
                        .evaluate(actualGapFreeWidth)
                    );
                    int y0 = (int) Math.round(
                        rSizes[cell.getRow()]
                        .evaluate(actualGapFreeHeight)
                    );
                    int x1 = (int) Math.round(
                        cSizes[cell.getCol() + cell.getCols()]
                        .evaluate(actualGapFreeWidth)
                    );
                    int y1 = (int) Math.round(
                        rSizes[cell.getRow() + cell.getRows()]
                        .evaluate(actualGapFreeHeight)
                    );
                    
                    // Actual cell dimension.
                    
                    int w = x1 - x0;
                    int h = y1 - y0;

                    // Component position correction.
                    
                    int xCorrection = insets.left + cGaps[cell.getCol()]
                        .getIncSize();
                    int yCorrection = insets.top + rGaps[cell.getRow()]
                        .getIncSize();
                    
                    // Component dimension correction.
                    
                    int wCorrection 
                        = cGaps[cell.getCol() + cell.getCols() - 1].getIncSize() 
                        - cGaps[cell.getCol()].getIncSize();
                    int hCorrection 
                        = rGaps[cell.getRow() + cell.getRows() - 1].getIncSize() 
                        - rGaps[cell.getRow()].getIncSize() ;
                    
                    // Preferred component dimension.
                    
                    int wComp = comp.getPreferredSize().width;
                    int hComp = comp.getPreferredSize().height;
                    
                    int fill = cell.getFill();
                    int anchor = cell.getAnchor();
                    
                    switch(fill) {
                        case Cell.NONE: {
                            if (wComp > w) {
                                wComp = w;
                            }
                            if (hComp > h) {
                                hComp = h;
                            }
                            switch (anchor) {
                                case Cell.FIRST_LINE_START:
                                    x0 += xCorrection;
                                    y0 += yCorrection;
                                    break;
                                case Cell.PAGE_START:
                                    x0 += xCorrection + (w - wComp) / 2;
                                    y0 += yCorrection;
                                    break;
                                case Cell.FIRST_LINE_END:
                                    x0 += xCorrection + w + wCorrection - wComp;
                                    y0 += yCorrection;
                                    break;
                                case Cell.LINE_START:
                                    x0 += xCorrection;
                                    y0 += yCorrection + (h - hComp) / 2;
                                    break;
                                case Cell.CENTER:
                                    x0 += xCorrection + (w - wComp) / 2;
                                    y0 += yCorrection + (h - hComp) / 2;
                                    break;
                                case Cell.LINE_END:
                                    x0 += xCorrection + w + wCorrection - wComp;
                                    y0 += yCorrection + (h - hComp) / 2;
                                    break;
                                case Cell.LAST_LINE_START:
                                    x0 += xCorrection;
                                    y0 += yCorrection + h + hCorrection - hComp;
                                    break;
                                case Cell.PAGE_END:
                                    x0 += xCorrection + (w - wComp) / 2;
                                    y0 += yCorrection + h + hCorrection - hComp;
                                    break;
                                case Cell.LAST_LINE_END:
                                    x0 += xCorrection + w + wCorrection - wComp;
                                    y0 += yCorrection + h - hCorrection - hComp;
                                    break;
                            }
                            w = Math.min(w, wComp) + wCorrection;
                            h = Math.min(h, hComp) + hCorrection;
                            break;
                        }
                        case Cell.PROPORTIONAL: {
                            double ratio = Math.min(
                                (double) ((double) w / (double) wComp),
                                (double) ((double) h / (double) hComp)
                            );
                            wComp = (int) Math.round(wComp * ratio);
                            hComp = (int) Math.round(hComp * ratio);
                            switch (anchor) {
                                case Cell.FIRST_LINE_START:
                                    x0 += xCorrection;
                                    y0 += yCorrection;
                                    break;
                                case Cell.PAGE_START:
                                    x0 += xCorrection + (w - wComp) / 2;
                                    y0 += yCorrection;
                                    break;
                                case Cell.FIRST_LINE_END:
                                    x0 += xCorrection + wCorrection + w - wComp;
                                    y0 += yCorrection;
                                    break;
                                case Cell.LINE_START:
                                    x0 += xCorrection;
                                    y0 += yCorrection + (h - hComp) / 2;
                                    break;
                                case Cell.CENTER:
                                    x0 += xCorrection + (w - wComp) / 2;
                                    y0 += yCorrection + (h - hComp) / 2;
                                    break;
                                case Cell.LINE_END:
                                    x0 += xCorrection + wCorrection + w - wComp;
                                    y0 += yCorrection + (h - hComp) / 2;
                                    break;
                                case Cell.LAST_LINE_START:
                                    x0 += xCorrection;
                                    y0 += yCorrection + hCorrection + h - hComp;
                                    break;
                                case Cell.PAGE_END:
                                    x0 += xCorrection + (w - wComp) / 2;
                                    y0 += yCorrection + hCorrection + h - hComp;
                                    break;
                                case Cell.LAST_LINE_END:
                                    x0 += xCorrection + wCorrection + w - wComp;
                                    y0 += yCorrection + hCorrection + h - hComp;
                                    break;
                            }
                            w = Math.min(w, wComp) + wCorrection;
                            h = Math.min(h, hComp) + hCorrection;
                            break;
                        }
                        case Cell.BOTH: {
                            x0 += xCorrection;
                            y0 += yCorrection;
                            w += wCorrection;
                            h += hCorrection;
                            break;
                        }
                        case Cell.HORIZONTAL: {
                            if (hComp > h) {
                                hComp = h;
                            }
                            switch(anchor) {
                                case Cell.FIRST_LINE_START:
                                case Cell.PAGE_START:
                                case Cell.FIRST_LINE_END:
                                    y0 += yCorrection;
                                    break;
                                case Cell.LINE_START:
                                case Cell.CENTER:
                                case Cell.LINE_END:
                                    y0 += yCorrection + (h - hComp) / 2;
                                    break;
                                case Cell.LAST_LINE_START:
                                case Cell.PAGE_END:
                                case Cell.LAST_LINE_END:
                                    y0 += yCorrection + h + hCorrection - hComp;
                                    break;
                            }
                            x0 += xCorrection;
                            w += wCorrection;
                            h = Math.min(h, hComp) + hCorrection;
                            break;
                        }
                        case Cell.VERTICAL: {
                            if (wComp > w) {
                                wComp = w;
                            }
                            switch(anchor) {
                                case Cell.FIRST_LINE_START:
                                case Cell.LINE_START:
                                case Cell.LAST_LINE_START:
                                    x0 += xCorrection;
                                    break;
                                case Cell.PAGE_START:
                                case Cell.CENTER:
                                case Cell.PAGE_END:
                                    x0 += xCorrection + (w - wComp) / 2;
                                    break;
                                case Cell.FIRST_LINE_END:
                                case Cell.LINE_END:
                                case Cell.LAST_LINE_END:
                                    x0 += xCorrection + w + wCorrection - wComp;
                                    break;
                            }
                            y0 += yCorrection;
                            w = Math.min(w, wComp) + wCorrection;
                            h += hCorrection;
                            break;
                        }
                    }
                    comp.setBounds(x0, y0, w, h);
                }
            }
        }
    }
    
    /** 
     * Return the minimum size of this container.
     * @param parent The container to be laid out.
     * @return The minimum container size dimension.
     */
    public Dimension minimumLayoutSize(Container parent) {
        
        if (containerSize == null) {
            setup(parent);
        }
        
        Insets insets = parent.getInsets();
        double minWidth = minGapFreeWidth + insets.left 
            + insets.right + cGaps[cBars.length].getIncSize();
        double minHeight = minGapFreeHeight + insets.top
            + insets.bottom + rGaps[rBars.length].getIncSize();
        
        // Return minimal container size.
        
        Dimension d = new Dimension((int) Math.round(minWidth),
            (int) Math.round(minHeight));
        return d;
    }
    
    /**
     * Calculate the preferred size dimensions for the specified panel given
     * the components in the specified parent container. Used by
     * <code>pack</code> method.
     * @param parent The component to be laid out.
     * @return The preferred size dimensions.
     */
    public Dimension preferredLayoutSize(Container parent) {
        if (containerSize == null) {
            setup(parent);
        }
        return containerSize;
    }
    
    /**
     * Removes the specified component from the layout (not implemented yet).
     * @param comp The component to be removed
     */
    public void removeLayoutComponent(Component comp) {
        // Not implemented.
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * Debugging methods.
     */
    
    /**
     * <p>This method draws the layout on a graphics context. Used to test the
     * layout during the design process. Override the 
     * <code>paint(Graphics g)</code> of the container to display the layout.
     * Here is an example code where <code>layout</code> is a 
     * <code>TestrisLayout</code>.</p>
     * <pre>
     * JPanel panel = new JPanel(layout) {
     *     public void paint(Graphics g) {
     *         super.paint(g);
     *         ((TetrisLayout) getLayout()).drawLayout(g);
     *     }
     * };
     * </pre>
     * @param g The graphics context.
     */
    public void drawLayout(Graphics g) {
        
        // Draw the spanning layout.
        
        g.setColor(spanningLayoutColor);
        
        Insets insets = container.getInsets();
        Dimension size = container.getSize();
        
        int actualGapFreeWidth = Math.max(size.width - insets.left
            - insets.right - cGaps[cBars.length].getIncSize(), 0);
        int actualGapFreeHeight = Math.max(size.height - insets.top
            - insets.bottom - rGaps[rBars.length].getIncSize(), 0);
        
        for (Cell cell : constraintsMap.values()) {

            if (cell != null) {

                /*
                 * Calculate the center of the first and the last cell spanned
                 * by the current component.
                 */
                
                int cx0 = (int) Math.round((
                    cSizes[cell.getCol()].evaluate(actualGapFreeWidth)
                    + cSizes[cell.getCol() + 1].evaluate(actualGapFreeWidth)
                ) / 2);                 
                int cy0 = (int) Math.round((
                    rSizes[cell.getRow()].evaluate(actualGapFreeHeight)
                    + rSizes[cell.getRow() + 1].evaluate(actualGapFreeHeight)
                ) / 2);
                int cx1 = (int) Math.round((
                    cSizes[cell.getCol() + cell.getCols()]
                    .evaluate(actualGapFreeWidth)
                    + cSizes[cell.getCol() + cell.getCols() - 1]
                    .evaluate(actualGapFreeWidth)
                ) / 2) + 1;
                int cy1 = (int) Math.round((
                    rSizes[cell.getRow() + cell.getRows()]
                    .evaluate(actualGapFreeHeight)
                    + rSizes[cell.getRow() + cell.getRows() - 1]
                    .evaluate(actualGapFreeHeight)
                ) / 2) + 1;

                // Actual cell dimension.
                
                int w = cx1 - cx0;
                int h = cy1 - cy0;
                    
                // Component position correction.
                
                int xCorrection = insets.left + cGaps[cell.getCol()]
                    .getIncSize();
                int yCorrection = insets.top + rGaps[cell.getRow()]
                    .getIncSize();

                // Component dimension correction.
                
                int wCorrection = 
                    cGaps[cell.getCol() + cell.getCols() - 1].getIncSize()
                    - cGaps[cell.getCol()].getIncSize();
                int hCorrection = 
                    rGaps[cell.getRow() + cell.getRows() - 1].getIncSize()
                    - rGaps[cell.getRow()].getIncSize();
                
                cx0 += xCorrection;
                cy0 += yCorrection;
                w += wCorrection;
                h += hCorrection;
                
                int radius = 6;
                g.drawRect(cx0 + 1, cy0 + 1, w - 1, h - 1);
                g.drawOval(cx0 + 1 - radius/2, cy0 + 1 - radius/2, radius,
                    radius);
                g.drawOval(cx0 + w - radius/2, cy0 + h - radius/2, radius,
                    radius);
            }
        }
            
        // Draw the internal layout.
        
        g.setColor(internalLayoutColor);    
            
        int x2 = (int) Math.round(
            cSizes[cSizes.length - 1].evaluate(actualGapFreeWidth) 
            + cGaps[cSizes.length - 1].getIncSize() 
            + insets.left
        ) - 1;
        int y2 = (int) Math.round(
            rSizes[rSizes.length - 1].evaluate(actualGapFreeHeight) 
            + rGaps[rSizes.length - 1].getIncSize() 
            + insets.top
        ) - 1;
        
        for (int i = 1; i < cSizes.length - 1; i++) {
            int x = (int) Math.round(
                cSizes[i].evaluate(actualGapFreeWidth) + insets.left 
                + cGaps[i - 1].getIncSize()
            );
            g.drawLine(x, insets.top, x, y2);
            g.drawLine(x + cGaps[i].getSize(), insets.top, 
                x + cGaps[i].getSize(), y2);
        }
        for (int i = 1; i < rSizes.length - 1; i++) {
            int y = (int) Math.round(
                rSizes[i].evaluate(actualGapFreeHeight) + insets.top 
                + rGaps[i - 1].getIncSize()
            );
            g.drawLine(insets.left, y, x2, y);
            g.drawLine(insets.left, y + rGaps[i].getSize(), 
                x2, y + rGaps[i].getSize());
        }
        
        // Draw the layout internal frame.
        
        g.setColor(externalLayoutColor);
        g.drawRect(insets.left, insets.top, x2 - insets.left, 
            y2 - insets.top);
        
        // Draw layout external frame.
        
        g.drawRect(0, 0, x2 + insets.right, y2 + insets.bottom);
    }
}