/*
 * EventRunner.java
 *
 * Created on 25 February 2006, 16.46
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

package org.kineticsystem.commons.data.model.swing;

// Java classes.

import java.awt.EventQueue;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.event.EventListenerList;

// Application classes.

import org.kineticsystem.commons.data.model.ActiveList;
import org.kineticsystem.commons.data.model.ActiveListEvent;
import org.kineticsystem.commons.data.model.ActiveListEventAssembler;
import org.kineticsystem.commons.data.model.ActiveListListener;
import org.kineticsystem.commons.data.model.ActiveListUtility;
import org.kineticsystem.commons.data.model.DefaultAssembler;

/**
 * This object is used to schedule the execution of a set of <tt>ActiveList</tt>
 * events by the AWT event-dispatching thread.
 * @author Giovanni Remigi
 * @version $Revision: 43 $
 */
@Deprecated
class OldEventRunner<E> implements Runnable, ActiveListListener {
    
    /* /////////////////////////////////////////////////////////////////////////
     * Private variables.
     */
    
    /**
     * True if the object has been scheduled for execution inside the AWT
     * event-dispatching thread queue, false otherwise. It is not volatile
     * because it is protected by the <tt>ActiveList</tt> lock.
     */
    private boolean scheduled;
    
    /** The list of all registered listener. */
    private EventListenerList listenerList;
    
    /** The source list generating <tt>ActiveListEvent</tt> events. */
    private ActiveList<E> srcList;
    
    /**
     * The cached list that can be safetly read by the AWT event-dispatching
     * thread.
     */
    private ActiveList<E> dstList;
    
    /**
     * The component used to collect synchronous <tt>ActiveListEvent</tt> events
     * from the underlying <tt>ActiveList</tt> list, to manage contradictions,
     * to compact and send them asynchronously to the AWT event-dispatching
     * thread.
     */
    private ActiveListEventAssembler assembler;
    
    /** Service variable used to temporarely store working events. */
    private java.util.List<ActiveListEvent> events;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constructors and initializing methods.
     */
    
    /**
     * Constructor.
     * @param source The underlying <tt>ActiveList</tt> list sending events
     *     whose effetcs must be under the control of the AWT event-dispatching
     *     thread.
     */
    public OldEventRunner(ActiveList<E> srcList, ActiveList<E> dtsList) {
        this.srcList = srcList;
        this.dstList = dtsList;
        listenerList = new EventListenerList();
        assembler = new DefaultAssembler();
        scheduled = false;
        events = new LinkedList<ActiveListEvent>();
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * ActiveList method overriding.
     */
    
    public void addActiveListListener(ActiveListListener listener) {
        listenerList.add(ActiveListListener.class, listener);
    }
    
    public void removeActiveListListener(ActiveListListener listener) {
        listenerList.remove(ActiveListListener.class, listener);
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * Runnable interface implementation.
     */
    
    /**
     * This method is executed by the AWT event-dispatching thread. When
     * executed the cached list is synchronized with the actual list and events
     * are fired to update the user interface.
     */
    public void run() {
        
        srcList.getReadWriteLock().readLock().lock();
        
        try {

            // Retrieve all events.
            
            while (!assembler.isEmpty()) {
                events.add(assembler.pop());
            }
            
            // Synchronize the swing list with the source multithreated list.
            
            boolean activated = dstList.isActive();
            dstList.setActive(false);
            ActiveListUtility.synchronize(srcList, dstList, events);
            dstList.setActive(activated);
            
            scheduled = false;
            
        } finally {
            srcList.getReadWriteLock().readLock().unlock();
        }
        
        // Fire events inside the AWT event-dispatching thread.
        
        int seq = 0;
        int lastSeq = events.size() - 1;
        
        Iterator<ActiveListEvent> iter = events.iterator();
        while (iter.hasNext()) {
            
            ActiveListEvent event = iter.next();
            iter.remove();

            // Change event source list and fire the new event.

            ActiveListEvent bridgeEvent = new ActiveListEvent(dstList);
            bridgeEvent.setType(event.getType());
            bridgeEvent.setX(event.getX());
            bridgeEvent.setY(event.getY());
            bridgeEvent.setSequenceNumber(seq++);
            bridgeEvent.setLastSequenceNumber(lastSeq);
            dstList.fireContentsChanged(bridgeEvent);
        }
    }

    /* /////////////////////////////////////////////////////////////////////////
     * ActiveListListener interface implementation.
     */
    
    /**
     * Collect <tt>ActiveListEvent</tt> events from the underlying
     * <tt>ActiveList</tt> source list, resolve contradictions and compact them 
     * using an embedded <tt>ActiveListEventAssembler</tt> and finally schedule
     * their execution by the AWT event-dispatching thread.
     * @param event The collected event.
     */
    public void contentsChanged(ActiveListEvent event) {
        
        assembler.push(event);
        if (!scheduled) {
            scheduled = true;
            EventQueue.invokeLater(this);
        }
    }
}