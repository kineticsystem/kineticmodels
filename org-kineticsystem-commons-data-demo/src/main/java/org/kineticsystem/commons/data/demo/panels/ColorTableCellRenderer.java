/*
 * ColorTableCellRenderer.java
 *
 * Created on June 18, 2003, 7:20 PM
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

package org.kineticsystem.commons.data.demo.panels;

// Java classes.

import java.awt.*;
import java.text.*;
import javax.swing.*;

// KineticSystem classes.

import org.kineticsystem.commons.data.view.renderer.*;

/**
 * @author Giovanni Remigi
 * @version $Revision: 8 $
 */
@SuppressWarnings("serial")
public class ColorTableCellRenderer extends BeanTableCellRenderer {
    
    /** Odd row color. */
    private final Color ODD_COLOR = Color.WHITE;
    
    /** Even row color. */
    private final Color EVEN_COLOR = new Color(255, 255, 200);
    
    /**
     * Constructor.
     * @param propertyName The current bean property value to be displayed.
     */
    public ColorTableCellRenderer(String propertyName) {    
        super(propertyName);
    }
    
    /**
     * Constructor.
     * @param propertyName The current bean property value to be displayed.
     * @param format Property value formatter.
     */
    public ColorTableCellRenderer(String propertyName, Format format) {    
        super(propertyName, format);
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * DefaultTableCellRenderer methods overriding.
     */
    
    /** {@inheritDoc} */
    @Override
    public Component getTableCellRendererComponent(JTable table, Object obj,
             boolean isSelected, boolean hasFocus, int row, int column) {
        
        Component comp = super.getTableCellRendererComponent(table, obj,
            isSelected, hasFocus, row, column);
        
        if ((row & 1) != 1) {
            if (!isSelected) {
                comp.setBackground(EVEN_COLOR);
            }
        } else {
            if (!isSelected) {
                comp.setBackground(ODD_COLOR);
            }
        }
        
        return comp;
    }
}