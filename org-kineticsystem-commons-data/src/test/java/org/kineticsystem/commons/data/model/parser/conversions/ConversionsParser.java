/*
 * ConversionsParser.java
 *
 * Created on 18 February 2006, 12.28
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

package org.kineticsystem.commons.data.model.parser.conversions;

// Java classes.
import java.io.*;
import org.xml.sax.*;

// Apache classes.
import org.apache.commons.digester.*;

// Application classes.
import org.kineticsystem.commons.data.model.*;
import org.kineticsystem.commons.data.model.parser.conversions.dom.*;

/**
 *
 * @author Giovanni Remigi
 * @version $Revision: 9 $
 */
public class ConversionsParser {
    
    /* /////////////////////////////////////////////////////////////////////////
     * Private variables.
     */
    
    /** The dom parser used to read the extrernal xml resource. */
    private Digester digester;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constructors and initializing methods.
     */
    
    /** Default constructor. */
    public ConversionsParser() {
        
        // Instantiate the parser to read the xml file.
        
        digester = new Digester();
        digester.setValidating(false);
        
        // Configure the parser.
        
        final AbstractObjectCreationFactory eventFactory 
                = new AbstractObjectCreationFactory() {
            
            public Object createObject(Attributes attributes) throws Exception {
                
                String typeAttr = attributes.getValue("type");
                String x = attributes.getValue("x");
                String y = attributes.getValue("y");
                
                int type = ActiveListEvent.INTERVAL_ADDED;
                
                if (typeAttr.equals("add")) {
                    type = ActiveListEvent.INTERVAL_ADDED;
                } else if (typeAttr.equals("del")) {
                    type = ActiveListEvent.INTERVAL_REMOVED;
                } else if (typeAttr.equals("mod")) {
                    type = ActiveListEvent.CONTENTS_CHANGED;
                }
                
                ActiveListEvent event = new ActiveListEvent(this);
                event.setType(type);
                event.setX(Integer.parseInt(x));
                event.setY(Integer.parseInt(y));
                
                return event;
            }
        };
        
        digester.addObjectCreate(
            "document",
            ConversionsDocument.class);
        
        digester.addObjectCreate(
            "document/conversions",
            Conversions.class);
        
        digester.addObjectCreate(
            "document/conversions/conversion",
            Conversion.class);
        
        digester.addObjectCreate(
            "document/conversions/conversion/input-sequence",
            EventSequence.class);
        
        digester.addObjectCreate(
            "document/conversions/conversion/output-sequence",
            EventSequence.class);
        
        digester.addFactoryCreate(
            "document/conversions/conversion/input-sequence/event", 
            eventFactory);
        
        digester.addFactoryCreate(
            "document/conversions/conversion/output-sequence/event", 
            eventFactory);
        
        digester.addSetNext(
            "document/conversions", 
            "setConversions");
        
        digester.addSetNext(
            "document/conversions/conversion/input-sequence/event", 
            "add");
        
        digester.addSetNext(
            "document/conversions/conversion/output-sequence/event", 
            "add");
        
        digester.addSetNext(
            "document/conversions/conversion/input-sequence", 
            "setInputSequence");
        
        digester.addSetNext(
            "document/conversions/conversion/output-sequence", 
            "setOutputSequence");
        
        digester.addSetNext(
            "document/conversions/conversion", 
            "add");
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * Public methods.
     */
    
    /**
     * Return an object containing a set of cases with input and expected output
     * to be used to test the <tt>EventListAssembler</tt> algorithm
     * implementation. All information are readed from the given xml resource.
     * @param stream The xml resource to be readed and parsed.
     */
    public ConversionsDocument parseDocument(InputStream stream)
            throws Exception {
        
        // Parse the file and create a dom structure.
        
        ConversionsDocument doc = (ConversionsDocument) digester.parse(stream);
        return doc;
    }
}
