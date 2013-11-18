/*
 * Navigator.java
 *
 * Created on August 13, 2003, 1:42 PM
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

import javax.swing.event.EventListenerList;

// Application classes.

import org.kineticsystem.commons.data.model.ActiveList;
import org.kineticsystem.commons.data.model.ActiveListEvent;
import org.kineticsystem.commons.data.model.ActiveListListener;

/** 
 * This is the controller for a generic list navigator component. It provides a
 * set of methods to control the navigation over a list of elements.
 * @author Giovanni Remigi
 * @version $Revision: 145 $
 */
public class Navigator implements ActiveListListener {
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constants.
     */
    
    /** 
     * This is the position stored by the navigator when no object is selected.
     */
    private int UNKNOWN_POSITION = -1;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Action flags.
     */
    
    /**
     * True when it is possible to jump to the first element of the list, false
     * otherwise.
     */
    private boolean moveFirstEnabled;
    
    /**
     * True when it is possible to move to the previous element of the list,
     * false otherwise.
     */
    private boolean moveBackEnabled;
    
    /**
     * True when it is possible to next to the previous element of the list,
     * false otherwise.
     */
    private boolean moveForwardEnabled;
    
    /**
     * True when it is possible to jump to the last element of the list, false
     * otherwise.
     */
    private boolean moveLastEnabled;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Private variables.
     */
    
    /** The component model. */
    private ActiveList<?> objects;
    
    /** The currently selected element. */
    private Object object;
    
    /** The position of the currently selected element. */
    private int objectIndex;
    
    /** The list of all registered listeners. */
    private EventListenerList listenerList;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Costructors.
     */
    
    /** Default constructor. */
    public Navigator() {
        
        this.objects = null;
        this.object = null;
        this.objectIndex = UNKNOWN_POSITION;
        this.listenerList = new EventListenerList();
        
        moveFirstEnabled = false;
        moveBackEnabled = false;
        moveForwardEnabled = false;
        moveLastEnabled = false;
    }

    /* /////////////////////////////////////////////////////////////////////////
     * NavigatorListener management.
     */

    /** 
     * Add a <tt>NavigatorListener</tt>. 
     * @param listener The <tt>NavigatorListener</tt> to be added.
     */
    public void addNavigatorListener(NavigatorListener listener) {
        listenerList.add(NavigatorListener.class, listener);
    }

    /** 
     * Remove a <tt>NavigatorListener</tt>. 
     * @param listener The <tt>NavigatorListener</tt> to be removed.
     */
    public void removeNavigatorListener(NavigatorListener listener) {
         listenerList.remove(NavigatorListener.class, listener);
    }

    /**
     * When a new element is selected by the navigator, a navigation event is
     * fired to all the registered <tt>NavigatorListener</tt>.
     */ 
    public void fireObjectSelected() {
        
        NavigatorEvent e = new NavigatorEvent(this, object, objectIndex);
        
        // Guaranteed to return a non-null array.

        Object[] listeners = listenerList.getListenerList();

        // Process the listeners last to first, notifying those that are
        // interested in this event.

        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == NavigatorListener.class) {
                ((NavigatorListener) listeners[i+1]).objectSelected(e);
            }          
        } 
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * Getter and setter methods.
     */
    
    /** 
     * Set a new navigator model.
     * @param model The new model.
     * @throws NullPointerException If the given model is null.
     */
    public void setModel(ActiveList<?> model) {
        
        if (model == null) {
            throw new NullPointerException("List cannot be null!");
        }
        
        if (objects != null) {
            objects.removeActiveListListener(this);
        }
        objects = model;
        objects.addActiveListListener(this);
        
        if (objects.isEmpty()) {
            objectIndex = NavigatorEvent.UNKNOWN_POSITION;
            object = null;
        } else {
            objectIndex = 0;
            object = objects.get(objectIndex);
        }
        reset();
        fireObjectSelected();
    }
    
    /** 
     * Return the current model.
     * @return The current model.
     */
    public ActiveList<?> getModel() {
        return objects;
    }
    
    /** 
     * Return the currently select element.
     * @return The selected element.
     */
    public Object getSelectedObject() {
        return object;
    }
    
    /** 
     * Return the position (0 module) of the selected element in the list.
     * @return The selected element position.
     */
    public int getPosition() {
        return objectIndex;
    }
    
    /** 
     * Return the number of elements in the list.
     * @return The number of elements in the list.
     */
    public int getSize() {
        if (objects != null) {
            return objects.size();
        } else {
            return 0;
        }
    }
    
    /**
     * Return true if it is possible to move to the next element of the list.
     * @return True if it is possible to move to the next element of the list.
     */
    public boolean isMoveForwardEnabled() {
        return moveForwardEnabled;
    }
    
    /**
     * Return true if it is possible to jump to the last element of the list.
     * @return True if it is possible to jump to the last element of the list.
     */
    public boolean isMoveLastEnabled() {
        return moveLastEnabled;
    }
    
    /**
     * Return true if it is possible to jump to the first element of the list.
     * @return True if it is possible to jump to the first element of the list.
     */
    public boolean isMoveFirstEnabled() {
        return moveFirstEnabled;
    }
    
    /**
     * Return true if it is possible to move to the previous element of the
     * list.
     * @return True if it is possible to move to the previous element of the
     *     list.
     */
    public boolean isMoveBackEnabled() {
        return moveBackEnabled;
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * Navigation methods.
     */
    
    /** 
     * Jump to the first element of the list. If the list is empty the method
     * doesn't do anything.
     */
    public void moveFirst() {
        if ((objects != null) && !objects.isEmpty()) {
            objectIndex = 0;
            object = objects.get(objectIndex);
            reset();
            fireObjectSelected();
        }
    }
    
    /** 
     * Move to the previous element of the list. If the list is empty or the
     * previous element doesn't exist, the method doesn't do anything.
     */
    public void moveBack() {
        if ((objects != null) && !objects.isEmpty()
                && (objectIndex > 0)) {
            objectIndex--;
            object = objects.get(objectIndex);
            reset();
            fireObjectSelected();
        }
    }
    
    /** 
     * Move to the next element of the list. If the list is empty or the next
     * element doesn't exist, the method doesn't do anything.
     */
    public void moveNext() {
        if ((objects != null) && !objects.isEmpty()
                && (objectIndex < (objects.size() - 1))) {
            objectIndex++;
            object = objects.get(objectIndex);
            reset();
            fireObjectSelected();
        }
    }
    
    /** 
     * Jump to the last element of the list. If the list is empty the method
     * doesn't do anything.
     */
    public void moveLast() {
        if ((objects != null) && !objects.isEmpty()) {
            objectIndex = objects.size() - 1;
            object = objects.get(objectIndex);
            reset();
            fireObjectSelected();
        }
    }
    
    /** 
     * Move to the given object. If the object is not contained by the list the
     * method doesn't do anything.
     * @param o The object to be selected.
     */
    public void moveTo(Object o) {
        
        if ((objects != null) && !objects.isEmpty()) {
            if (objects.contains(o)) {
                object = o;
                objectIndex = objects.indexOf(o);
                reset();
                fireObjectSelected();
            }
        }
    }

    /** 
     * Move to the given position. If the position is out of range, the method
     * doesnìt do anything.
     * @param index The position to move to.
     */
    public void moveTo(int index) {
        
        if ((objects != null) && !objects.isEmpty()) {
            if ((index >= 0) && (index < objects.size())) {
                objectIndex = index;
                object = objects.get(objectIndex);
                reset();
                fireObjectSelected();
            }
        }
    }

    /** Update the state of the controller. */
    public void reset() {
        
        if ((objects == null) || objects.isEmpty() || (object == null)) {
            
            moveForwardEnabled = false;
            moveBackEnabled= false;
            moveLastEnabled = false;
            moveFirstEnabled = false;
            
        } else {

            moveForwardEnabled = (objectIndex < (objects.size() - 1));
            moveBackEnabled = (objectIndex > 0);
            moveLastEnabled = moveForwardEnabled;
            moveFirstEnabled = moveBackEnabled;
        }
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * ActiveListListener interface implementation.
     */
    
    /** {inheritDoc} */
    public void contentsChanged(ActiveListEvent event) {
        
        int length = event.getY() - event.getX() + 1;
        
        switch (event.getType()) { 
            case ActiveListEvent.INTERVAL_ADDED: {
                
                if (objectIndex == NavigatorEvent.UNKNOWN_POSITION) {
                    objectIndex = 0;
                    object = objects.get(objectIndex);
                } else if (objectIndex >= event.getX()) { 
                    objectIndex = objectIndex  + length;
                }           
                break;
            }  
            case ActiveListEvent.INTERVAL_REMOVED: {
                
                if (objectIndex > event.getY()) {
                    
                    objectIndex = objectIndex  - length;
                    
                } else if ((objectIndex >= event.getX())
                        && (objectIndex <= event.getY())) {
                    
                    /*
                     * Previous selected object deleted: the navigator always
                     * tries to remain as close as possible to the previous
                     * object position.
                     */
                    
                    if (objects.isEmpty()) {
                        objectIndex = NavigatorEvent.UNKNOWN_POSITION;
                        object = null;
                    } else {
                        if (event.getX() > 0) {
                            objectIndex = event.getX() - 1;
                        } else {
                            objectIndex = 0;
                        }
                        object = objects.get(objectIndex);
                    }
                }
                break; 
            }
        }
        
        reset();
        fireObjectSelected();
    }
}