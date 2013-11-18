/*
 * BeanTableCellRenderer.java
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

package org.kineticsystem.commons.data.view.renderer;

// Java classes.

import java.awt.Component;
import java.text.Format;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

// Apache classes.

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This is a default JList cell renderer. It extracts a property value from
 * the underlying object and renders the result on the screen with a specified
 * format.
 * @author Giovanni Remigi
 * @version $Revision: 9 $
 */
@SuppressWarnings("serial")
public class BeanTableCellRenderer extends DefaultTableCellRenderer {

    /* /////////////////////////////////////////////////////////////////////////
     * Log framework.
     */
    
    /** Apache log framework. */
    private static Log logger = LogFactory.getLog(BeanTableCellRenderer.class);
    
    /* /////////////////////////////////////////////////////////////////////////
     * Private variables.
     */
    
    /** The current bean property value to be displayed. */
    private String propertyName;
    
    /** If given this is used to properly format the current object. */
    private Format format;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constructors.
     */
    
    /**
     * Constructor.
     * @param propertyName The current bean property value to be displayed.
     */
    public BeanTableCellRenderer(String propertyName) {    
        this.propertyName = propertyName;
        this.format = null;
    }
    
    /**
     * Constructor.
     * @param propertyName The current bean property value to be displayed.
     * @param format Property value formatter.
     */
    public BeanTableCellRenderer(String propertyName, Format format) {    
        this.propertyName = propertyName;
        this.format = format;
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * DefaultTableCellRenderer methods overriding.
     */
    
    /** {@inheritDoc} */
    @Override
    public Component getTableCellRendererComponent(JTable table, Object obj,
             boolean isSelected, boolean hasFocus, int row, int column) {
        
        JLabel label = (JLabel) super.getTableCellRendererComponent(table, obj,
            isSelected, hasFocus, row, column);
        
        try {
            String text = BeanRenderUtils.getPropertyValue(obj, propertyName,
                format);
            label.setText(text);  
            
        } catch (Exception ex) {
            logger.error(ex);
        }
        
        return label;
    }
}