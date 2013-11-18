/*
 * ConversionsDocument.java
 *
 * Created on 18 February 2006, 12.01
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

package org.kineticsystem.commons.data.model.parser.conversions.dom;

/*
 * @author Giovanni Remigi
 * @version $Revision: 9 $
 */
public class ConversionsDocument {
    
    /* /////////////////////////////////////////////////////////////////////////
     * Private variables.
     */
    
    /*
     * List containing a set of cases with input and expected output, readed
     * from an external xml file and used to test the current algorithm
     * implementation.
     */
    private Conversions conversions;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Default constructor.
     */
    
    /** Costruttore predefinito. */
    public ConversionsDocument() {

    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * Getter and setter methods
     */
    
    /**
     * Set the list containing a set of cases with input and expected output,
     * readed from an external xml file and used to test the current algorithm
     * implementation.
     * @param conversions The list containing a set of cases with input and
     *     expected output.
     */
    public void setConversions(Conversions conversions) {
        this.conversions = conversions;
    }
    
    /**
     * Return the list containing a set of cases with input and expected output,
     * readed from an external xml file and used to test the current algorithm
     * implementation.
     * @return The list containing a set of cases with input and expected
     *     output.
     */
    public Conversions getConversions() {
        return conversions;
    }
}
