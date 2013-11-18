/*
 * DefaultAssemblerTest.java
 *
 * Created on 15 February 2006, 18.32
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

package org.kineticsystem.commons.data.model;

// Java classes.

import java.io.*;

// JUnit classes.

import junit.framework.*;
import junit.textui.*;

// Application classes.

import org.kineticsystem.commons.data.model.parser.conversions.*;
import org.kineticsystem.commons.data.model.parser.conversions.dom.*;

/**
 * Test unit for <tt>DefaultAssembler</tt> class. It reads some common
 * input and expected output sequences and use them to test the algorithm
 * of the <tt>EventListAssembler</tt>.
 * @author Giovanni Remigi
 * @version $Revision: 9 $
 */
public class DefaultAssemblerTest extends TestCase {
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constants.
     */
    
    /** Directory containing xml test cases. */
    private final String RESOURCE_DIR
        = "org/kineticsystem/commons/data/model/resources/";
    
    /** Resource containing input and expected output in xml format. */
    private final String ADD_ADD_TEST_RESOURCE = "assembler-add-add-test.xml";
    private final String ADD_DEL_TEST_RESOURCE = "assembler-add-del-test.xml";
    private final String ADD_MOD_TEST_RESOURCE = "assembler-add-mod-test.xml";
    private final String DEL_ADD_TEST_RESOURCE = "assembler-del-add-test.xml";
    private final String DEL_DEL_TEST_RESOURCE = "assembler-del-del-test.xml";
    private final String DEL_MOD_TEST_RESOURCE = "assembler-del-mod-test.xml";
    private final String MOD_ADD_TEST_RESOURCE = "assembler-mod-add-test.xml";
    private final String MOD_DEL_TEST_RESOURCE = "assembler-mod-del-test.xml";
    private final String MOD_MOD_TEST_RESOURCE = "assembler-mod-mod-test.xml";
    private final String COMPLEX_TEST_RESOURCE = "assembler-complex-test.xml";
            
    /* /////////////////////////////////////////////////////////////////////////
     * Constructor.
     */
    
    /**
     * Constructor.
     * @param name The test case name.
     */
    public DefaultAssemblerTest(String name) {
        super(name); 
    }
       
    /**
     * Simple Factory method.
     * Return a runnable JUnit test suite.
     */
    public static Test suite() {
        TestSuite ts = new TestSuite(DefaultAssemblerTest.class);
        return ts;
    }

    /**
     * Main method.
     * @param args Array of arguments.
     */
    public static void main(String args[]) {
        TestRunner.run(DefaultAssemblerTest.class);
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * Overriden methods.
     */
    
    /** Prepare test working structure. */
    public void setUp() throws Exception {

    }
    
    /** Reset. */
    public void tearDown() throws Exception {
        
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * Test methods.
     */
    
    /**
     * Test assembler algorithm correctness. It reads a set of cases with input
     * and expected output from an external xml file and test them with the
     * current algorithm implementation.
     * @throws Exception
     */
    public void testAlgorithm() throws Exception {
        
        ConversionsParser parser = new ConversionsParser();
        
        int index = 0;
        String[] resources = new String[10];
        
        resources[index++] = RESOURCE_DIR + ADD_ADD_TEST_RESOURCE;
        resources[index++] = RESOURCE_DIR + ADD_DEL_TEST_RESOURCE;
        resources[index++] = RESOURCE_DIR + ADD_MOD_TEST_RESOURCE; 
        resources[index++] = RESOURCE_DIR + DEL_ADD_TEST_RESOURCE;
        resources[index++] = RESOURCE_DIR + DEL_DEL_TEST_RESOURCE;
        resources[index++] = RESOURCE_DIR + DEL_MOD_TEST_RESOURCE;
        resources[index++] = RESOURCE_DIR + MOD_ADD_TEST_RESOURCE;
        resources[index++] = RESOURCE_DIR + MOD_DEL_TEST_RESOURCE;
        resources[index++] = RESOURCE_DIR + MOD_MOD_TEST_RESOURCE;
        resources[index++] = RESOURCE_DIR + COMPLEX_TEST_RESOURCE;
        
        for (int i = 0; i < resources.length; i++) {
            
            /*
             * Retrieve the list containing a set of cases with input and
             * expected output, readed from an external xml file and used to
             * test the current algorithm implementation.
             */

            InputStream resource = getClass().getClassLoader()
                .getResourceAsStream(resources[i]);
            assertNotNull("Cannot retrieve external resource: \"" + resources[i]
                + "\"", resource);
            
            ConversionsDocument document = parser.parseDocument(resource);
            resource.close();
            
            // Instantiante the assembler to be tested.

            ActiveListEventAssembler assembler = new DefaultAssembler();

            for (Conversion conv : document.getConversions()) {

                String storedString = "";
                String assembledString = "";

                EventSequence input = conv.getInputSequence();
                EventSequence output = conv.getOutputSequence();

                // Remove all assembler events.

                while (!assembler.isEmpty()) {
                    assembler.pop();
                }

                System.out.println("Input sequence: ");
                for (ActiveListEvent event : input) {
                    System.out.println(ActiveListEventUtils.toString(event));
                    assembler.push(event);
                }

                System.out.println("Output sequence: ");
                for (ActiveListEvent event : output) {
                    storedString += ActiveListEventUtils.toString(event);
                    System.out.println(ActiveListEventUtils.toString(event));
                }

                System.out.println("Assembler sequence: ");
                while (!assembler.isEmpty()) {
                    ActiveListEvent event = assembler.pop();
                    assembledString += ActiveListEventUtils.toString(event);
                    System.out.println(ActiveListEventUtils.toString(event));
                    
                    // TODO: test the events order too (strictly ordered).
                }

                assertEquals(storedString, assembledString);
            }
        }
    }
}