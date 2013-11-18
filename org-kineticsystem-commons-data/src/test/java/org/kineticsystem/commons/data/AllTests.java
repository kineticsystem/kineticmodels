/*
 * AllTests.java
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

package org.kineticsystem.commons.data;

// JUnit classes.

import junit.textui.*;
import junit.framework.*;

// Java classes.

import org.kineticsystem.commons.data.model.*;

/**
 * Test case.
 * @author Giovanni Remigi
 * @version $Revision: 9 $
 */
public class AllTests extends TestCase {
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constructors and initializing methods.
     */
    
    /**
     * Constructor.
     * @param name The test case name.
     */
    public AllTests(String name) {
        super(name); 
    }
    
    /** 
     * Simple Factory method.
     * Return a runnable JUnit test suite.
     */
    public static Test suite() {
        
        TestSuite ts = new TestSuite(AllTests.class);
        ts.addTest(new TestSuite(DefaultAssemblerTest.class));
        ts.addTest(new TestSuite(DefaultAssemblerRandomTest.class));
        return ts;
    }

    /**
     * Launch this as a command line JUnit test suite.
     * @param args Command line arguments.
     */
    public static void main(String[] args ) {
        TestRunner.run(AllTests.suite());
    }
}