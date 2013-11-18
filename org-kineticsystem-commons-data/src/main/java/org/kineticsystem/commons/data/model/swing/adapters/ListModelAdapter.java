/*
 * ListModelAdapter.java
 *
 * Created on 27 February 2006, 22.26
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

import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

// Application classes.

import org.kineticsystem.commons.data.model.ActiveList;

/**
 * This object is used to adapt the <tt>ListModel</tt> interface to the
 * <tt>DataList</tt> interface so that it can be used by a <tt>JList</tt>
 * component.
 * @author Giovanni Remigi
 * @version $Revision: 145 $
 */
public class ListModelAdapter implements ListModel {
    
    /* /////////////////////////////////////////////////////////////////////////
     * Private variables.
     */
    
    /** The adaptee. */
    protected ActiveList<?> adaptee;
    
    /**
     * Component used to transform <tt>ActiveListEvent</tt> events to
     * <tt>ListDataEvent</tt> events. It receives events from the
     * <tt>DataList</tt> adaptee, transforms and sends them to all registered
     * <tt>ListDataListener</tt> listeners.
     */
    private ListDataListenerDispatcher dispatcher;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constructors and initializing methods.
     */
    
    /** Default constructor. */
    public ListModelAdapter(ActiveList<?> adaptee) {
        this.adaptee = adaptee;
        init();
    }
    
    /** Initializing method. */
    private void init() {
        if (adaptee == null) {
            throw new NullPointerException("Adaptee cannot be null!");
        }
        dispatcher = new ListDataListenerDispatcher(this);
        this.adaptee.addActiveListListener(dispatcher);
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * ListModel interface implementation.
     */
    
    /** {@inheritDoc} */
    public void addListDataListener(ListDataListener listener) {
        dispatcher.addListDataListener(listener);
    }
    
    /** {@inheritDoc} */
    public void removeListDataListener(ListDataListener listener) {
        dispatcher.removeListDataListener(listener);
    }
    
    /** {@inheritDoc} */
    public Object getElementAt(int index) {
        Object obj = adaptee.get(index);
        return obj;
    }
    
    /** {@inheritDoc} */
    public int getSize() {
        return adaptee.size();
    }
}