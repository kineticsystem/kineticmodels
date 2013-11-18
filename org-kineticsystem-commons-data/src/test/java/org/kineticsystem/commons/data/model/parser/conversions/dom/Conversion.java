/*
 * Conversion.java
 *
 * Created on 18 February 2006, 11.15
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

/**
 * Class representing a single matching test. It contains a list of input
 * events and the corresponding output sequence of events.
 * @author Giovanni Remigi
 * @version $Revision: 9 $
 */
public class Conversion {
    
    /* /////////////////////////////////////////////////////////////////////////
     * Properties.
     */
    
    /** The input list. */
    private EventSequence input;
    
    /** The output list. */
    private EventSequence output;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constructors.
     */
    
    /** Default constructor. */
    public Conversion() {
        
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * Getter and setter methods.
     */
    
    public void setInputSequence(EventSequence input) {
        this.input = input;
    }
    
    public EventSequence getInputSequence() {
        return input;
    }
    
    public void setOutputSequence(EventSequence output) {
        this.output = output;
    }
    
    public EventSequence getOutputSequence() {
        return output;
    }
}