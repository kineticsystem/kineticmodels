/*
 * Accelerator.java
 *
 * Created on October 6, 2003, 4:16 PM
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

package org.kineticsystem.commons.threads;

/** 
 * This thread executes instructions inside the <code>execute</code> method at
 * time intervals evaluated by the method <code>getTime</code>. It can be used
 * i.e. to execute instructions time by time faster.
 * @author Giovanni Remigi
 * $Revision: 4 $
 */
public abstract class Accelerator extends Thread {
    
    /* /////////////////////////////////////////////////////////////////////////
     * Private constants.
     */
    
    /**
     * This costant is used in the default implementation of the
     * <code>getTime</code> method. It represents the maximum time interval
     * between an execution and the next one (in milliseconds).
     */
    private int MAX_TIME = 256;
    
    /**
     * This costant is used in the default implementation of the
     * <code>getTime</code> method. It represents the minimum time interval
     * between an execution and the next one (in milliseconds).
     */
    private int MIN_TIME = 1;
    
    /**
     * This constants represents the number of execution that must be run before
     * reevaluating the execution time.
     */
    private int MAX_COUNTER_LIMIT = 4;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Private variables.
     */
    
    /** 
     * Service variable internally used by the default implementation of the
     * <code>getTime</code> method.
     */
    private int counter;
    
    /** 
     * Service variable internally used by the default implementation of the
     * <code>getTime</code> method.
     */
    private int counterLimit;
    
    /** The current time interval between an execution and the next one. */
    private int time;
   
    /* /////////////////////////////////////////////////////////////////////////
     * Default constructors.
     */
    
    /** The default constructor. */
    public Accelerator() {
        time = MAX_TIME;
        counter = 0;
        counterLimit = MAX_COUNTER_LIMIT;
        super.setPriority(Thread.NORM_PRIORITY);
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * Overriding methods.
     */
    
    /** The thread activity. */
    @Override
    public void run() {
        counter = 0;
        counterLimit = MAX_COUNTER_LIMIT;
        while (!interrupted()) {
            execute();
            try {
                sleep(time);
            } catch (InterruptedException ie) {
                interrupt();
            }
            // Get the new waiting time interval.
            time = getTime(time);
        }
    }
    
    /** This method is invoked at precalculated time intervals. */
    protected abstract void execute();
    
    /**
     * This method is used to calculate the next waiting time interval based on
     * the previous one. Override it to provide a different behaviour.
     * @param time The previous time interval.
     * @return The new calculated time interval.
     */
    protected int getTime(int time) {
        if (counter > counterLimit) {
            counter = 0;
            counterLimit = counterLimit << 1;
            time = time >>> 1;
            if (time < MIN_TIME) {
                time = MIN_TIME;
            }
        }
        counter ++;
        return time;
    }
}