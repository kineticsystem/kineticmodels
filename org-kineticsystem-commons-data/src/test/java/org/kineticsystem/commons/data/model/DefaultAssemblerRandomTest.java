/*
 * DefaultAssemblerRandomTest.java
 *
 * Created on 5 March 2006, 17.27
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

import java.lang.reflect.*;
import java.util.*;

// JUnit classes.

import junit.framework.*;
import junit.textui.*;

/**
 * This class implement a brute force test on the <tt>DefaultAssembler</tt>
 * component. It executes one billion of random tests on a list of length 10.
 * For each test a random set of changes (delete, add, modify) is applied to the
 * given list. The maximum number of changes is 10 per list. The maximum length
 * of a change is 10.
 * @author Giovanni Remigi
 * @version $Revision: 9 $
 */
public class DefaultAssemblerRandomTest  extends TestCase {
    
    /** The length of the input list. */
    private static final int LIST_LENGTH = 10;
    
    /** The maximum number of changes that can be applied to the list. */
    private static final int MAX_EVENT_NUMBER = 10;
    
    /** The change maximum  interval. */
    private static final int MAX_INTERVAL_LENGTH = 10;
    
    /** The numbero of random test to be executed on the list. */
    private static final int TEST_NUMBER = 1000000;
    
    private static final int PRINT_COUNTER_INTERVAL = 100000;
    
    private static final boolean EQUALITY_TEST = true;
    
    private static final boolean ORDER_TEST = false;
    
    private static final boolean ADJACENCY_TEST = false;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constructor.
     */
    
    /**
     * Constructor.
     * @param name The test case name.
     */
    public DefaultAssemblerRandomTest(String name) {
        super(name);
    }
    
    /**
     * Simple Factory method.
     * Return a runnable JUnit test suite.
     */
    public static Test suite() {
        TestSuite ts = new TestSuite(DefaultAssemblerRandomTest.class);
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
     * Test methods.
     */

    /**
     * Test assembler algorithm correctness.
     * @throws Exception
     */
    public void testAlgorithm() throws Exception {
        
        int test = 0;
        int printCounter = 0;
        while (test < TEST_NUMBER) {
            if (printCounter == PRINT_COUNTER_INTERVAL) {
                System.out.println(test);
                printCounter = 0;
            }
            printCounter++;
            test++;
            ArrayList<Integer> source = new ArrayList<Integer>();
            int counter = 0;
            for (int i = 0; i < LIST_LENGTH; i++) {
                source.add(new Integer(counter++));
            }
            change(source);
        }
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * Private methods.
     */
    
    private void change(ArrayList<Integer> source) {
        
        // Clone original input list.
        
        ArrayList<Integer> list1 = new ArrayList<Integer>(source);
        int counter = list1.size();
        
        // Instantiate the component to be tested.
        
        DefaultAssembler assembler = new DefaultAssembler();
        
        // Execute a random number of sequential events on the original list.
        
        List<ActiveListEvent> events1 = new LinkedList<ActiveListEvent>();
        int eventsNumber = (int) Math.floor(MAX_EVENT_NUMBER * Math.random())
            + 1;
        for (int i = 0; i < eventsNumber; i++) {
            
            // Calculate a random event (delete, add, modify).
            
            int operationType = (int) Math.floor(3 * Math.random());
            switch(operationType) {
                
                case 0: { // Delete.
                    
                    if (list1.size() > 0) {
                        int x = (int) Math.floor(list1.size() * Math.random());
                        int y = (int) Math.floor((list1.size() - x)
                            * Math.random()) + x;
                        int intervalLength = y - x + 1;
                        for (int k = 0; k < intervalLength; k++) {
                            list1.remove(x);
                        }
                        
                        // Create event.
                        
                        ActiveListEvent event = new ActiveListEvent(this);
                        event.setType(ActiveListEvent.INTERVAL_REMOVED);
                        event.setX(x);
                        event.setY(y);
                        
                        events1.add(event); // Save event.
                        
                        assembler.push(event); // Push event into the assembler.
                    }
                    break;
                }
                case 1: { // Add.
                    
                    int x = (int) Math.floor(list1.size() * Math.random());
                    int intervalLength = (int) Math.floor(MAX_INTERVAL_LENGTH 
                        * Math.random()) + 1;
                    for (int k = 0; k < intervalLength; k++) {
                        Integer value = new Integer(counter++);
                        list1.add(x, value);
                    }

                    // Create event.
                    
                    ActiveListEvent event = new ActiveListEvent(this);
                    event.setType(ActiveListEvent.INTERVAL_ADDED);
                    event.setX(x);
                    event.setY(x + intervalLength - 1);
                    
                    events1.add(event); // Save event.
                    
                    assembler.push(event); // Push event into the assembler.
                    
                    break;
                }
                case 2:  { // Modify.
                    
                    if (list1.size() > 0) {
                        int x = (int) Math.floor(list1.size() * Math.random());
                        int y = (int) Math.floor((list1.size() - x)
                            * Math.random()) + x;
                        int intervalLength = y - x + 1;
                        for (int k = 0; k < intervalLength; k++) {
                            Integer value = new Integer(counter++);
                            list1.set(x + k, value);
                        }
                        
                        // Create event.
                        
                        ActiveListEvent event = new ActiveListEvent(this);
                        event.setType(ActiveListEvent.CONTENTS_CHANGED);
                        event.setX(x);
                        event.setY(y);
                        
                        events1.add(event); // Save event.
                        
                        assembler.push(event); // Push event into the assembler.
                    }
                    break;
                }
            }
        }

        /*
         * Retrieve the optimized list of events from the assembler. This is
         * done using reflection to access the private assembler list.
         */
        
        List<ActiveListEvent> events2 = new LinkedList<ActiveListEvent>();
        try {
            Field fields[] = DefaultAssembler.class.getDeclaredFields();
            for (int i = 0; i < fields.length; ++i) {
                if ("events".equals(fields[i].getName())) {
                    fields[i].setAccessible(true);
                    List tmp = (LinkedList) fields[i].get(assembler);
                    for (Iterator iter = tmp.iterator(); iter.hasNext();) {
                        events2.add((ActiveListEvent) iter.next());
                    }
                    break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        // Clone original input list.
        
        ArrayList<Integer> list2 = new ArrayList<Integer>(source);
        counter = list2.size();
        
        // Execute assembler optimized events on the original list.

        try {

            for (ActiveListEvent event : events2) {

                switch(event.getType()) {
                    case ActiveListEvent.INTERVAL_REMOVED: { // Delete.

                        int x = event.getX();
                        int y = event.getY();
                        int intervalLength = y - x + 1;
                        for (int k = 0; k < intervalLength; k++) {
                            list2.remove(x);
                        }
                        break;
                    }
                    case ActiveListEvent.INTERVAL_ADDED: { // Add.

                        int x = event.getX();
                        int y = event.getY();
                        int intervalLength = y - x + 1;
                        for (int k = 0; k < intervalLength; k++) {
                            list2.add(x + k, list1.get(x + k));
                        }

                        break;
                    }
                    case ActiveListEvent.CONTENTS_CHANGED:  { // Change.

                        int x = event.getX();
                        int y = event.getY();
                        int intervalLength = y - x + 1;
                        for (int k = 0; k < intervalLength; k++) {
                            list2.set(x + k, list1.get(x + k));
                        }
                        break;
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println("list1:" + list1);
            System.out.println("list2:" + list2);
            
            System.out.println("Original event sequence: ");
            System.out.println(serializeToString(events1));
            
            System.out.println("Assembler event sequence: ");
            System.out.println(serializeToString(events2));

            System.out.println();
            
            ex.printStackTrace();
            System.exit(0);
        }
        
        /*
         * Compare the list obtainted applying all events sequentially to the
         * original list and the one obtained applying events optimized by
         * the assembler component.
         */
        
        if (EQUALITY_TEST) {
            
            boolean isEquals = true;
            if (list1.size() != list2.size()) {
                isEquals = false;
            } else {
                Iterator iter1 = list1.iterator();
                Iterator iter2 = list2.iterator();
                while (iter1.hasNext()) {
                    Integer value1 = (Integer) iter1.next();
                    Integer value2 = (Integer) iter2.next();
                    if (!value1.equals(value2)) {
                        isEquals = false;
                    }
                }
            }
            if (!isEquals) {
                
                System.out.println("list1:" + list1);
                System.out.println("list2:" + list2);

                System.out.println("Original event sequence: ");
                System.out.println(serializeToString(events1));

                System.out.println("Assembler event sequence: ");
                System.out.println(serializeToString(events2));

                System.out.println();

                System.out.println("Equality test failed!");
                System.exit(0);
            }
        }
            
        // Adjacency test.
        
        if (ADJACENCY_TEST) {
            
            ActiveListEvent tmpEvent = null;
            boolean equals = false;
            for (ActiveListEvent event : events2) {
                if (tmpEvent != null) {
                    if (event.getType() == tmpEvent.getType()) {

                        if (event.getType() == ActiveListEvent.INTERVAL_ADDED) {
                            if (event.getX() <= (tmpEvent.getY() + 1)) {
                                equals = true;
                            }
                        } else  if (event.getType() == ActiveListEvent.CONTENTS_CHANGED) {
                            if (event.getX() <= (tmpEvent.getY() + 1)) {
                                equals = true;
                            }
                        } else if (event.getType() == ActiveListEvent.INTERVAL_REMOVED) {
                            if (event.getX() == tmpEvent.getX()) {
                                equals = true;
                            }
                        }
                    }
                }
                tmpEvent = event;
            }
            if (equals) {

                System.out.println("Original event sequence: ");
                System.out.println(serializeToString(events1));

                System.out.println("Assembler event sequence: ");
                System.out.println(serializeToString(events2));

                System.out.println("Adjacency test failed!");
                System.exit(0);
            }
        }
        
        // Ordering test.
        
        if (ORDER_TEST) {
            
            ActiveListEvent tmpEvent = null;
            boolean ordered = true;
            for (ActiveListEvent event : events2) {
                if (tmpEvent != null) {
                    if (event.getX() < tmpEvent.getX()) {
                        ordered = false;
                    }
                }
                tmpEvent = event;
            }
            if (!ordered) {

                System.out.println("Original event sequence: ");
                System.out.println(serializeToString(events1));

                System.out.println("Assembler event sequence: ");
                System.out.println(serializeToString(events2));

                System.out.println("Order test failed!");
                System.exit(0);
            }
        }
    }
    
    /**
     * Method used to convert the given list of event into a human readable
     * string.
     * @param events The list of events (delete, add, modify).
     * @param The string representation of the given list.
     */
    private String serializeToString(List<ActiveListEvent> events) {
        
        String out = "";
        for (ActiveListEvent event : events) {
            if (event.getType() == ActiveListEvent.INTERVAL_ADDED) {
                out += "ADD";
            } else if (event.getType() == ActiveListEvent.INTERVAL_REMOVED) {
                out += "DEL";
            } else if (event.getType() == ActiveListEvent.CONTENTS_CHANGED) {
                out += "MOD";
            }
            out += "[" + event.getX() + ", " + event.getY() + "]\n";
        }
        return out;
    }
}
