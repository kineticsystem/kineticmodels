/*
 * Editor.java
 *
 * Created on June 27, 2004, 2:46 PM
 *
 * Copyright (C) 2004 Remigi Giovanni
 * g.remigi@kineticsystem.org
 * www.kineticsystem.org
 *
 * This program is free software; you can redistribute it and/or change it under
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

// Apache commons classes.

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

// Application classes.

import org.kineticsystem.commons.data.model.ActiveList;
import org.kineticsystem.commons.data.model.ActiveListEvent;
import org.kineticsystem.commons.data.model.ActiveListListener;

/**
 * This class is used as a generic editor to edit, remove, create objects in a
 * given list. It is basically a controller that can be managed (or not) by a
 * GUI component. It is used together with an <tt>EditorManager<tt> to allow or
 * deny some actions at given cases before modifying the underlying list.
 * @author Giovanni Remigi
 * @version $Revision: 146 $
 * @see org.kineticsystem.commons.data.controller.Editor
 */
public class DataNavigator implements NavigatorListener, ActiveListListener {
    
    /* /////////////////////////////////////////////////////////////////////////
     * Log framework.
     */
    
    /** Logging system. */
    private static Log logger = LogFactory.getLog(DataNavigator.class);
    
    /* /////////////////////////////////////////////////////////////////////////
     * Component states.
     */
    
    /**
     * This state indicates that the component is currently used to navigate the
     * model and at least one element is selected.
     */
    public static final byte DEFAULT_STATE = 1;
    
    /**
     * This state indicates that the component is currently modifying a new
     * element to be inserted in the model.
     */
    public static final byte CREATION_STATE = 2;
    
    /**
     * This state indicates that the component is currently modifying an
     * element to be updated in the model.
     */
    public static final byte EDITING_STATE = 4;
    
    /** 
     * This state indicates that the component is not showing any object because
     * the model is empty.
     */
    public static final byte EMPTY_STATE = 8;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Variables.
     */
    
    /**
     * The component internal state. Possible values are:
     * <ul>
     * <li><tt>DEFAULT_STATE</tt></li>;
     * <li><tt>CREATION_STATE</tt></li>;
     * <li><tt>EDITING_STATE</tt></li>;
     * <li><tt>EMPTY_STATE</tt></li>.
     * </ul>
     */
    private byte state;
    
    /** The list of all registered listeners. */
    private EventListenerList listenerList;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Connected components.
     */
    
    /**
     * The navigation controller.
     * @see org.kineticsystem.commons.data.controller.Navigator
     */
    private Navigator navigator;
    
    /**
     * The object manager.
     * @see org.kineticsystem.commons.data.controller.Editor
     */
    private Editor editor;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constructors.
     */
    
    /**
     * Constructor.
     * @param navigator The list navigator.
     * @param editor The object manager.
     */
    public DataNavigator() {
        this.editor = null;
        navigator = new Navigator();
        navigator.addNavigatorListener(this);
        listenerList = new EventListenerList();
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * Getter and setter methods.
     */
    
    /** 
     * Return the list navigator controller, che component to manage the
     * navigation logic.
     * @return The navigator controller.
     */
    public Navigator getNavigator() {
        return navigator;
    }
    
    /**
     * The manager internal state. Possible values are:
     * <ul>
     * <li><tt>DEFAULT_STATE</tt></li>;
     * <li><tt>CREATION_STATE</tt></li>;
     * <li><tt>EDITING_STATE</tt></li>;
     * <li><tt>EMPTY_STATE</tt></li>.
     * </ul> 
     * @return The manager internal state.
     */
    public int getState() {
        return state;
    }
    
    /**
     * Return the model associated to the manager.
     * @return The model associated to the manager.
     */
    public ActiveList<?> getModel() {
        return navigator.getModel();
    }
    
    /**
     * Set a new model.
     * @param model The new model.
     * @throws NullPointerException If the given model is null.
     */
    public void setModel(ActiveList<?> model) {
        navigator.setModel(model);
    }
    
    /**
     * Set the editor used to edit the selected object.
     * @param editorManager The manager used to edit the selected object.
     */
    public void setEditor(Editor editor) {
        this.editor = editor;
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * Editor action methods methods.
     */
    
    /** Enter the object creation phase if allowed by the associated manager. */
    public void create() {
        
        if (((state == DEFAULT_STATE) || (state == EMPTY_STATE))
                && (editor != null)) {
            
            if (editor.creationRequested()) { // Ask the manager. 
               logger.info("Create a new object.");
               state = CREATION_STATE;
               fireEditorStateChanged();
            }
        }
    }
    
    /** Save the created object if allowed by the associated manager. */
    @SuppressWarnings("unchecked")
    public void executeCreation() {
        
        if ((state == CREATION_STATE) && (editor != null)) {

            if (editor.creationChecked()) { // Ask the manager.
                
                Object obj = editor.getObject();
                ActiveList model = navigator.getModel();
                model.getReadWriteLock().writeLock().lock();
                try {
                    model.add(obj);
                } finally {
                    model.getReadWriteLock().writeLock().unlock();
                }
                editor.creationExecuted();
                navigator.moveLast();
                logger.info("Creation executed.");
            }
        }
    }
    
    /** Cancel the creation of a new object object. */
    public void cancelCreation() {
        
        if ((editor != null) && (state == CREATION_STATE)) {
            navigator.reset();
            navigator.fireObjectSelected();
            editor.creationCancelled();
            logger.info("Creation cancelled.");
        }
    }
    
    /** 
     * Enter the selected object editing phase if allowed by the associated
     * manager.
     */
    public void change() {
        
        if ((state == DEFAULT_STATE) && (editor != null)) {
            
            if (editor.changeRequested()) { // Ask the manager. 
               logger.info("Change a selected object.");
               state = EDITING_STATE;
               fireEditorStateChanged();
            }
        }
    }
    
    /** Save the updated object if allowed by the associated manager. */
    @SuppressWarnings("unchecked")
    public void executeChange() {
        if ((state == EDITING_STATE) && (editor != null)) {
            
            if (editor.changeChecked()) { // Ask the manager.
                ActiveList model = navigator.getModel();
                int position = navigator.getPosition();
                model.getReadWriteLock().writeLock().lock();
                try {
                    model.set(position, editor.getObject());
                    editor.changeExecuted();
                } finally {
                    model.getReadWriteLock().writeLock().unlock();
                }
                logger.info("Change executed.");
            }
        }
    }
    
    /** Cancel the change of the selected object. */
    public void cancelChange() {
        
        if ((editor != null) && (state == EDITING_STATE)) {
                
            navigator.reset();
            navigator.fireObjectSelected();
            editor.changeCancelled();
            logger.info("Change cancelled.");
        }
    }
    
    /** 
     * Remove the currently selected object if allowed by the associated 
     * manager. 
     */
    public void remove() {
        
        if ((state == DEFAULT_STATE) && (editor != null)) {
            
            if (editor.removalRequested()) { // Ask the manager. 
                
                ActiveList<?> model = navigator.getModel();
                int position = navigator.getPosition();
                model.getReadWriteLock().writeLock().lock();
                try {
                    model.remove(position);
                } finally {
                    model.getReadWriteLock().writeLock().unlock();
                }

                editor.setObject(navigator.getSelectedObject());
                editor.removalExecuted();
                logger.info("Removal executed.");
            }
        }
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * EditorListeners management.
     */

    /** 
     * Register a new <tt>EditorListener</tt>.
     * @param listener The <tt>EditorListener</tt> to be registered.
     */
    public void addEditorListener(DataNavigatorListener listener) {
        listenerList.add(DataNavigatorListener.class, listener);
    }

    /** 
     * Remove a previously registered <tt>EditorListener</tt>.
     * @param listener The <tt>EditorListener</tt> to be removed.
     */
    public void removeEditorListener(DataNavigatorListener listener) {
         listenerList.remove(DataNavigatorListener.class, listener);
    }

    /**
     * 
     * Notify all <tt>EditorListeners</tt> about a change in the manager state.
     */ 
    private void fireEditorStateChanged() {
        
        DataNavigatorEvent e = new DataNavigatorEvent(this, state);
        
        // Guaranteed to return a non-null array.
        
        Object[] listeners = listenerList.getListenerList();
        
        // Process the listeners last to first, notifying those that are
        // interested in this event.
        
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == DataNavigatorListener.class) {
                ((DataNavigatorListener) listeners[i+1]).editorStateChanged(e);
            }
        }  
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * NavigatorListener interface implementation.
     */
    
    /** {@inheritDoc} */
    public void objectSelected(NavigatorEvent e) {
        Object obj = e.getSelectedObject();
        if (obj != null) {
            state = DEFAULT_STATE;
        } else {
            state = EMPTY_STATE;
        }
        
        if (editor != null) {
            editor.setObject(obj);
        }
        fireEditorStateChanged();
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * ActiveListListener interface implementation.
     */
    
    /** {inheritDoc} */
    public void contentsChanged(ActiveListEvent event) {
        // Implement this method.
    }
}