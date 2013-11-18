/*
 * BasicList.java
 *
 * Created on 8 May 2006, 16.46
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
import java.lang.reflect.InvocationTargetException;

// Apache commons libraries.

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

// Application classes.

import org.kineticsystem.commons.data.model.ActiveList;
import org.kineticsystem.commons.data.model.ActiveListEvent;
import org.kineticsystem.commons.data.model.DefaultActiveList;

/**
 * This is a simply <tt>ActiveList</tt> implementation sending events inside the
 * AWT event-dispatching queue. An event is immediately processed by the GUI
 * without the double-buffering optimization implemented in the
 * <tt>DataList</tt>.
 * @author Giovanni Remigi
 * @version $Revision: 33 $
 * @see org.kineticsystem.commons.data.model.DataList
 */
public class BasicList<E> extends DefaultActiveList<E> {
    
    /* /////////////////////////////////////////////////////////////////////////
     * Log framework.
     */
    
    /** Apache log framework. */
    private static Log logger = LogFactory.getLog(BasicList.class);
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constructors.
     */
    
    /** Default constructor. */
    public BasicList() {
        super();
    }
    
    /**
     * Constructor with list.
     * @param list The wrapped list.
     */
    public BasicList(ActiveList<E> list) {
        super(list);
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * Public methods.
     */
    
    /**
     * Notify all registered listeners with the given event. The event is fired
     * directly inside the AWT event-dispatching thread.
     * @param event The event containing information about changes occurred
     *     inside the list.
     */
    @Override
    public void fireContentsChanged(ActiveListEvent event) {
        
        if (EventQueue.isDispatchThread()) {
            
            /*
             * Flag the event as "0=last" for compatibility with GroupingProxy
             * and SortingProxy classes.
             */
            
            event = (ActiveListEvent) event.clone();
            event.setSequenceNumber(0);
            
            super.fireContentsChanged(event);
            
        } else {
            Launcher runner = new Launcher(event);
            try {
                EventQueue.invokeAndWait(runner); // Wait until completion.
            } catch (InterruptedException ex) {
                logger.warn(ex);
            } catch (InvocationTargetException ex) {
                logger.error(ex);
            }
        }
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * Nested classes.
     */
    
    /**
     * This class is used to send an event inside the AWT event-dispatching
     * queue.
     */
    private class Launcher implements Runnable {
        
        /** The event to be fired. */
        private ActiveListEvent event;
        
        /**
         * Constructor.
         * @param event The event to be fired.
         */
        public Launcher(ActiveListEvent event) {
            this.event = event;
        }
        
        /** Runnable interface implementation. */
        public void run() {
            fireContentsChanged(event);
        }
    }
}