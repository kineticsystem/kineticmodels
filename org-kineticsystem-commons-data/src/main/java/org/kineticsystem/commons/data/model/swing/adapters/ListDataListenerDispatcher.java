/*
 * ListDataListenerProxy.java
 *
 * Created on 25 February 2006, 16.52
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
package org.kineticsystem.commons.data.model.swing.adapters; 

// Java classes.

import javax.swing.event.EventListenerList;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

// Application classes.

import org.kineticsystem.commons.data.model.ActiveListEvent;
import org.kineticsystem.commons.data.model.ActiveListListener;

/**
 * This component is used to transform <tt>ActiveListEvent</tt> events to
 * <tt>ListDataEvent</tt> events. It receives event from a <tt>DataList</tt>
 * adaptee, transforms and sends them to all registered
 * <tt>ListDataListener</tt> listeners. Is is internally used as an event proxy
 * by the <tt>ListModelAdapter</tt>.
 * @author Giovanni Remigi
 * @version $Revision: 44 $
 */
class ListDataListenerDispatcher implements ActiveListListener {
    
    /* /////////////////////////////////////////////////////////////////////////
     * Private variables.
     */
    
    /** The event source. */
    private Object source;
    
    /** The list of all registered listeners. */
    private EventListenerList listenerList;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constructors.
     */
    
    public ListDataListenerDispatcher(Object source) {
        this.source = source;
        listenerList = new EventListenerList();
    }
    
    public void addListDataListener(ListDataListener listener) {
        listenerList.add(ListDataListener.class, listener);
    }
    
    public void removeListDataListener(ListDataListener listener) {
        listenerList.remove(ListDataListener.class, listener);
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * ActiveListListener interface implementation.
     */
    
    public void contentsChanged(ActiveListEvent event) {
        switch (event.getType()) {
            case ActiveListEvent.CONTENTS_CHANGED: {
                
                ListDataEvent e = new ListDataEvent(source,
                    ListDataEvent.CONTENTS_CHANGED,
                    event.getX(), event.getY());
                fireContentsChanged(e);
                break;
            }
            case ActiveListEvent.INTERVAL_ADDED: {
                
                ListDataEvent e = new ListDataEvent(source,
                    ListDataEvent.INTERVAL_ADDED,
                    event.getX(), event.getY());
                fireIntervalAdded(e);
                break;
            }
            case ActiveListEvent.INTERVAL_REMOVED: {
                
                ListDataEvent e = new ListDataEvent(source,
                    ListDataEvent.INTERVAL_REMOVED,
                    event.getX(), event.getY());
                fireIntervalRemoved(e);
                break;
            }
        }
    }   
    
    /* /////////////////////////////////////////////////////////////////////////
     * Private methods.
     */
    
    void fireContentsChanged(ListDataEvent e) {

        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == ListDataListener.class) {
                ((ListDataListener) listeners[i + 1]).contentsChanged(e);
            }          
        }
    }

    void fireIntervalAdded(ListDataEvent e) {
        
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == ListDataListener.class) {
                ((ListDataListener) listeners[i + 1]).intervalAdded(e);
            }          
        }
    }

    void fireIntervalRemoved(ListDataEvent e) {
        
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == ListDataListener.class) {
                ((ListDataListener) listeners[i + 1]).intervalRemoved(e);
            }          
        }
    }
}
