/*
 * ActiveListEventMultiplexer.java
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

package org.kineticsystem.commons.data.model;

// Java classes.

import javax.swing.event.EventListenerList;

/**
 * This object is used as an <tt>ActiveList</tt> event multiplexer.
 * When you register an <tt>ActiveListeListener</tt> to receive events from an
 * <tt>ActiveList</tt> you create a coupling that can create problems with the
 * Garbage Collector. The listener can be finalize by the Garbage Collector when
 * there are no more references to the object itself.
 * <i>For this reason the event listener can be destroyed together with the
 * event source or after it is unregistered.</i>
 * This multiplexer create a central point, a node, acting as a bridge between
 * an <tt>ActiveList</tt> and a group of <tt>ActiveListListener</tt>. This is
 * useful to unregister a set of listeners in one step when a class containing
 * many <tt>ActiveListeListener</tt> must be finalized.
 * Suppose you have an <tt>ActiveList</tt> with global scope in your
 * application. Suppose you have many dialogs allowing you to modify the list
 * content. The dialogs contain probably a lot of <tt>ActiveListeListener</tt>.
 * To dispose the dialog you should unregister all listeners to allow the
 * Garbage Collector do its work. To simplify the job you can create a event
 * multiplexer, one per dialog, and register it with the main list. All your
 * dialog components must be registered with the multiplexer and no more with
 * the list. In this way, when you dispose the dialog, you just need to
 * unregister the multiplexer.
 * @author Giovanni Remigi
 * @version $Revision: 9 $
 */
public class ActiveListEventMultiplexer implements ActiveListListener {

    /* /////////////////////////////////////////////////////////////////////////
     * Private variables.
     */
    
    /** The list of all registered listener. */
    private EventListenerList listenerList;
    
    /* /////////////////////////////////////////////////////////////////////////
     * ActiveListListener interface implementation.
     */
    
    /** {@inheritDocs} */
    public void contentsChanged(ActiveListEvent event) {
        fireContentsChanged(event);
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * Event management.
     */
    
    /** {@inheritDoc} */
    public void addActiveListListener(ActiveListListener listener) {
        listenerList.add(ActiveListListener.class, listener);
    }
    
    /** {@inheritDoc} */
    public void removeActiveListListener(ActiveListListener listener) {
        listenerList.remove(ActiveListListener.class, listener);
    }
    
    /** {@inheritDoc} */
    public void fireContentsChanged(ActiveListEvent event) {
        
        // Guaranteed to return a non-null array.

        Object[] listeners = listenerList.getListenerList();

        // Process the listeners last to first, notifying those that are
        // interested in this event.

        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == ActiveListListener.class) {
                ((ActiveListListener) listeners[i+1])
                    .contentsChanged(event);
            }          
        }
    }
}