/*
 * ComboBoxModelAdapter.java
 *
 * Created on 2 April 2006, 0.52
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

import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;

// Application classes.

import org.kineticsystem.commons.data.model.ActiveList;
import org.kineticsystem.commons.data.model.ActiveListEvent;
import org.kineticsystem.commons.data.model.ActiveListListener;

/**
 * This is an adapter to adapt the an <tt>ActiveList<tt> to a
 * <tt>ComboBoxModel</tt>.
 * @author Giovanni Remigi
 * @version $Revision: 145 $
 */
public class ComboBoxModelAdapter implements ComboBoxModel, ActiveListListener {

    /* /////////////////////////////////////////////////////////////////////////
     * Variables.
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

    /** The selected item. */
    private Object selected;

    /* /////////////////////////////////////////////////////////////////////////
     * Constructors and initializing methods.
     */

    /** Default constructor. */
    public ComboBoxModelAdapter(ActiveList<?> adaptee) {
        this.adaptee = adaptee;
        init();
    }
    
    /** Initializing method. */
    private void init() {
        if (adaptee == null) {
            throw new NullPointerException("Adaptee cannot be null!");
        }
        if (!adaptee.isEmpty()) {
            selected = adaptee.get(0);
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
    
    /* /////////////////////////////////////////////////////////////////////////
     * ComboBoxModel interface implementation.
     */
    
    /** {@inheritDoc} */
    public Object getSelectedItem() {
        return selected;
    }
    
    /** {@inheritDoc} */
    public void setSelectedItem(Object anItem) {
        selected = anItem;
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * ActiveListListener interface implementation.
     */
    
    /** 
     * When the model has changed, the selection must change consequently.
     * @param event The event thrown by the model.
     */
    public void contentsChanged(ActiveListEvent event) {
        if (adaptee.isEmpty()) {
            selected = null;
        } else if (!adaptee.contains(selected)) {
            selected = adaptee.get(0);
        }
    }
}