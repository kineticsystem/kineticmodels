/*
 * Semaphore.java
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

package org.kineticsystem.commons.threads;

/**
 * <p>Simple numeric semaphor without thread queue.</p>
 * <p>To avoid thread starvation use a semaphore with queue.</p>
 * @author Giovanni Remigi
 * $Revision: 24 $
 */
public class NumericSemaphore {
    
    /* /////////////////////////////////////////////////////////////////////////
     * Private variables.
     */
    
    /**
     * Maximum number of thread which can simultaneously aquire the semaphore.
     */
    private int count;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constructors.
     */
    
    /**
     * Main constructor.
     * @param count The maxumum number of thread which can simultaneously aquire
     *     the semaphore.
     */
    public NumericSemaphore(int count) {
        this.count = count;
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * Public methods.
     */
    
    /** Acquire the semaphore: it corresponds to a <i>wait</i> operation. */
    public synchronized void aquire() {
        while (count == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        count--;
    }
    
    /** Release the semaphore: it corresponds to a <i>signal</i> operation. */
    public synchronized void release() {
        count++;
        notifyAll();
    }
}
