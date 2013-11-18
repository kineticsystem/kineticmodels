/*
 * RandomContactWriter.java
 *
 * Created on 4 April 2006, 11.40
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

package org.kineticsystem.commons.data.demo;

// Application classes.

import org.kineticsystem.commons.data.model.*;
import org.kineticsystem.commons.random.*;
import org.kineticsystem.commons.random.bean.*;

/**
 * This thread is used to randomly write, delete and modify a list of contacts
 * for testing purpose only.
 * @author Giovanni Remigi
 * $Revision: 8 $
 */
class RandomContactWriter extends Thread {
    
    /* /////////////////////////////////////////////////////////////////////////
     * Public constants.
     */
    
    /**
     * Variable indicating the time between a change and another one in
     * milliseconds.
     */
    public final int DEFAULT_WAITING_TIME = 10;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Private variables.
     */

    /** The list being randomy modified. */
    private ActiveList<RandomContact> list;
    
    /** The generator of random contacts. */
    private RandomContactGenerator contactGen;
    
    /** The generator of random changes on the contact list. */
    private RandomListChangeGenerator changeGen;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constructors.
     */
    
    /** Default constructor. */
    public RandomContactWriter() {
        contactGen = new RandomContactGenerator();
        changeGen = new RandomListChangeGenerator();
        changeGen.setMaxIntervalAddedLength(5);
        changeGen.setMaxIntervalRemovedLength(3);
        changeGen.setMaxIntervalModifiedLength(5);
        changeGen.setMaxListSize(1000);
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * Getter and setter methods.
     */
    
    /**
     * Set the contact list to be randomly modified by the thread.
     * @param list The list to be randomly modified.
     */
    public void setList(ActiveList<RandomContact> list) {
        this.list = list;
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * Runnable interface imlpementation.
     */
    
    /** Randomly modify the given contact list. */
    public void run() {
        while (!isInterrupted()) {
            
            list.getReadWriteLock().writeLock().lock();
            try {
                
                int size = list.size();
                RandomListChange change = changeGen.generate(size);
                
                int interval = change.getY() - change.getX() + 1;
                
                if (change.getType()  == RandomListChange.INTERVAL_REMOVED) {
                    
                    for (int i = 0; i < interval; i++) {
                        list.remove(change.getX());
                    }
                    
                }  else if (change.getType()  == RandomListChange.INTERVAL_ADDED) {
                    
                    for (int i = 0; i < interval; i++) {
                        RandomContact contact = contactGen.generateContact();
                        list.add(change.getX(), contact);
                    }
                    
                }  else if (change.getType() == RandomListChange.CONTENTS_CHANGED) {
                    
                    for (int i = 0; i < interval; i++) {
                        RandomContact contact = contactGen.generateContact();
                        list.set(change.getX(), contact);
                    }
                }
                
            }  finally {
                list.getReadWriteLock().writeLock().unlock();
            }
            
            try {
                Thread.sleep(DEFAULT_WAITING_TIME);
            } catch (InterruptedException ex) {
                interrupt(); // Set interrupted flag.
            }
        }
    }
}