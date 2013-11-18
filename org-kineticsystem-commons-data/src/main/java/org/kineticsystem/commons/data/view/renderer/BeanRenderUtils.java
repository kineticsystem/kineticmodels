/*
 * RenderUtils.java
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

import java.text.Format;

// Apache classes.

import org.apache.commons.beanutils.PropertyUtils;

/**
 * @author giovanni.remigi
 * @version $Revision: 9 $
 */
public class BeanRenderUtils {

    /* /////////////////////////////////////////////////////////////////////////
     * Constructors.
     */
    
    /** Private constructor. */
    private BeanRenderUtils() {
        // Avoid construction.
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * Public methods.
     */
    
    static String getPropertyValue(Object obj, String propertyName,
            Format format) throws Exception {
        
        String text = null; 
        Object value = null;
        
        if (propertyName == null) {
            value = obj;
        } else {
            if (obj != null) {
                value = PropertyUtils.getProperty(obj, propertyName);
            } else {
                value = null;
            }
        }
        
        if (value != null) {
            if (format != null) {
                text = format.format(value);
            } else {
                text = value.toString();
            }
        }
        
        return text;
    }
}