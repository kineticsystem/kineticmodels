/*
 * FixedSizeFilter.java
 *
 * Created on September 24, 2003, 12:23 AM
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

package org.kineticsystem.commons.data.view;

// Java classes.

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

// Apache commons classes.

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/** 
 * This is a filter used to limit the maximum number of characters that can be
 * inserted in a <tt>JTextFields</tt>.
 * @author Giovanni Remigi
 * @version $Revision: 36 $
 */
public class FixedSizeFilter extends DocumentFilter {
    
    /** Logging framework. */
    private static Log logger = LogFactory.getLog(FixedSizeFilter.class);
    
    /* /////////////////////////////////////////////////////////////////////////
     * Private variables.
     */
    
    /** Max number of available characters. */
    private int maxSize;

    /* /////////////////////////////////////////////////////////////////////////
     * Constructors.
     */
    
    /**
     * Constructors.
     * @param limit Max number of available characters.
     */
    public FixedSizeFilter(int limit) {
        maxSize = limit;
    }

    /* /////////////////////////////////////////////////////////////////////////
     * DocumentFilter class overriding.
     */
    
    /** {@inheritDoc} */
    @Override
    public void insertString(DocumentFilter.FilterBypass fb, int offset, 
            String str, AttributeSet attr) throws BadLocationException {
        replace(fb, offset, 0, str, attr);
    }

    /** {@inheritDoc} */
    @Override
    public void replace(DocumentFilter.FilterBypass fb, int offset, int length,
            String str, AttributeSet attrs) throws BadLocationException {
        try {
            int newLength;
            if (str == null) {
                newLength = fb.getDocument().getLength() - length;
            } else {
                newLength = fb.getDocument().getLength() - length 
                    + str.length();
            }
            if (newLength <= maxSize) {
                fb.replace(offset, length, str, attrs);
            }
        } catch (NullPointerException ex) {
            logger.error(ex);
        } catch (BadLocationException ex) {
            throw new BadLocationException("New characters exceed max size "
                + "of document", offset);
        }
    }
}