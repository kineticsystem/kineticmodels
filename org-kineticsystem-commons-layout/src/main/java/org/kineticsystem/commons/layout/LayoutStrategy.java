/*
 * LayoutStrategy.java
 *
 * Created on July 25, 2005, 7:38 PM
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

/**
 * Implement this class if you want create you own layout algorithm.
 * @author Giovanni Remigi
 * $Revision: 37 $
 */
public interface LayoutStrategy {

    /**
     * This method is called immediately after the <code>LayoutStrategy</code>
     * instance is plugged into the <code>TetrisLayout</code>. It can be useful
     * to define some working structures variables used by the layout algorithm.
     * @param layoutRows
     * @param layoutCols
     */
    public void initialize(int layoutRows, int layoutCols);
    
    /**
     * <p>Implement this method if you want create your own algorithm to lay out
     * components onto the layout. Pass your custom <code>LayoutStrategy</code> 
     * class instance to the <code>TetrisLayout</code> class instance (see
     * Strategy Design Pattern).</p>
     * <p>Change the constraints object to change the way the given component is
     * placed on to the layout.</p>
     * <p>If you want you can change or substitute the given component too (i.e
     * example using the Decorator Pattern).</p>
     * @param comp The component to be laid out.
     * @param constraints The component constraints.
     */
    public void addComponent(Component comp, Cell constraints);
}
