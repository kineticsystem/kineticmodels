/*
 * TableModelListenerProxy.java
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
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

// Application classes.

import org.kineticsystem.commons.data.model.ActiveListEvent;
import org.kineticsystem.commons.data.model.ActiveListListener;

/**
 * This component is used to transform <tt>ActiveListEvent</tt> events to
 * <tt>TableModelEvent</tt> events. It receives events from a <tt>DataList</tt>
 * adaptee, transforms and sends them to all registered
 * <tt>ListDataListener</tt> listeners. It is internally used as an event proxy
 * by the <tt>TableModelAdapter</tt>.
 * @author Giovanni Remigi
 * @version $Revision: 170 $
 */
class TableModelListenerDispatcher implements ActiveListListener {
    
    /* /////////////////////////////////////////////////////////////////////////
     * Private variables.
     */
    
    /** The event source. */
    private TableModel source;
    
    /** The list of all registered listeners. */
    private EventListenerList listenerList;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constructors.
     */
    
    public TableModelListenerDispatcher(TableModel source) {
        this.source = source;
        listenerList = new EventListenerList();
    }
    
    public void addTableModelListener(TableModelListener listener) {
        listenerList.add(TableModelListener.class, listener);
    }
    
    public void removeTableModelListener(TableModelListener listener) {
        listenerList.remove(TableModelListener.class, listener);
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * ActiveListListener interface implementation.
     */
    
    public void contentsChanged(ActiveListEvent event) {
        switch (event.getType()) {
            case ActiveListEvent.CONTENTS_CHANGED: {

                TableModelEvent e = new TableModelEvent(source, event.getX(),
                    event.getY(), TableModelEvent.ALL_COLUMNS, 
                    TableModelEvent.UPDATE);
                fireTableChanged(e);
                break;
            }
            case ActiveListEvent.INTERVAL_ADDED: {
                
                TableModelEvent e = new TableModelEvent(source, event.getX(),
                    event.getY(), TableModelEvent.ALL_COLUMNS,
                    TableModelEvent.INSERT);
                fireTableChanged(e);
                break;
            }
            case ActiveListEvent.INTERVAL_REMOVED: {
                
                TableModelEvent e = new TableModelEvent(source, event.getX(),
                    event.getY(), TableModelEvent.ALL_COLUMNS, 
                    TableModelEvent.DELETE);
                fireTableChanged(e);
                break;
            }
        }
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * Package scope methods.
     */
    
    void fireTableChanged(TableModelEvent e) {

        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == TableModelListener.class) {
                ((TableModelListener) listeners[i+1]).tableChanged(e);
            }          
        }
    }
}