/*
 * NavigatorListModelMediator.java
 *
 * Created on July 24, 2004, 8:41 PM
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

package org.kineticsystem.commons.data.controller;

// Java classes.

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * This object is used to synchronize selections between <tt>JNavigators</tt>
 * and <tt>JTables</tt> or <tt>JLists</tt>.
 * @author Giovanni Remigi
 * @version $Revision: 145 $
 */
public class NavigatorListModelMediator implements NavigatorListener,
        ListSelectionListener {
    
    /* /////////////////////////////////////////////////////////////////////////
     * variables.
     */  
    
    /** 
     * This variable is used to avoid cycling events between components,
     * situation that usually terminate with a stack overflow error.
     */
    private boolean eventFired = false;
    
    /* /////////////////////////////////////////////////////////////////////////
     * GUI components.
     */
    
    /** The navigator. */
    private Navigator navigator;
    
    /** The list containing <code>ListSelectionModel</code> objects. */
    private List<ListSelectionModel> listSelectionModels;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constructors.
     */
    
    /** Default constructors. */
    public NavigatorListModelMediator() {
        listSelectionModels = new LinkedList<ListSelectionModel>();
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * Public methods.
     */
    
    /**
     * Aggancia un navigatore al mediatore.
     * @param navigator Il navigatore da coordinare con altri componenti.
     */
    public void setNavigator(Navigator navigator) {
        this.navigator = navigator;
        navigator.addNavigatorListener(this);
    }
    
    public void addListSelectionModel(ListSelectionModel lsm) {
        listSelectionModels.add(lsm);
        lsm.addListSelectionListener(this);
    }
    
    public void removeListSelectionModel(ListSelectionModel lsm) {
        lsm.removeListSelectionListener(this);
        listSelectionModels.remove(lsm);
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * NavigatorListener interface implementation.
     */
    
    /**
     * This method is called when an object is selected by the navigator.
     * @param event The navigator event.
     */
    public void objectSelected(NavigatorEvent event) {
        if (!eventFired) {
            eventFired = true;
            int index = event.getPosition();
            if (index != NavigatorEvent.UNKNOWN_POSITION) {
                ListSelectionModel model;
                Iterator<ListSelectionModel> iter 
                    = listSelectionModels.iterator();
                while (iter.hasNext()) {
                    model = iter.next();
                    model.setSelectionInterval(index, index);
                }
            }
            eventFired = false;
        }
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * ListSelectionListener interface implementation.
     */
    
    /**
     * This method is called when the <tt>JTable</tt> or <tt>JList</tt>
     * selection changes. When it happens the navigator is positioned on the
     * last element of selection.
     * @param event The <tt>ListSelectionModel</tt> event.
     * @see javax.swing.ListSelectionModel
     */
    public void valueChanged(ListSelectionEvent event) {
        
        if (!eventFired) {
            eventFired = true; // Lock.
            ListSelectionModel lsm = (ListSelectionModel) event.getSource();
            
            if (!event.getValueIsAdjusting()) {
                
                if (!lsm.isSelectionEmpty()) {

                    // Move the navigator to the last element of the selection.

                    navigator.moveTo(lsm.getLeadSelectionIndex());
                }
            }
            eventFired = false; // Unlock.
        }
    }
}