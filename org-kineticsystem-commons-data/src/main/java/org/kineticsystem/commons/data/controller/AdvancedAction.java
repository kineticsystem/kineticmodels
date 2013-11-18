/*
 * AdvancedAction.java
 *
 * Created on October 6, 2003, 5:32 PM
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

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.KeyStroke;
import javax.swing.event.EventListenerList;

/**
 * This is similar to a standard action button except that it is associated to
 * any possible mouse click. It also add more convenient setter and getter
 * methods to access internal properties.
 * @author Giovanni Remigi
 * @version $Revision: 160 $
 */
public abstract class AdvancedAction implements Action, MouseListener  {
    
    /* /////////////////////////////////////////////////////////////////////////
     * Private constants.
     */
    
    /** The key associate to the "enable" property. */
    private static final String ENABLED_KEY = "enabled";
    
    /* /////////////////////////////////////////////////////////////////////////
     * Variables.
     */
    
    /** The map of properties associated to the action. */
    private Map<String,Object> values;
    
    /** The list of all registered listeners. */
    private EventListenerList listenersList = new EventListenerList();
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constructors.
     */
    
    /** Default constructor. */
    public AdvancedAction() {
        values = new HashMap<String,Object>();
        values.put(ENABLED_KEY, new Boolean(true));
        firePropertyChange(ENABLED_KEY, null, new Boolean(true));
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * Action interface implementation.
     */
    
    /**
     * Set a property and its lookup key.
     * @param key The key to be used to retrieve the property.
     * @param value The property.
     */
    public void putValue(String key, Object value) {
        Object oldValue = values.get(key);
        values.put(key, value);
        firePropertyChange(key, oldValue, value);
    }
    
    /** 
     * Return the action property associate to the given key.
     * @param key The searching key.
     * @return The property.
     */
    public Object getValue(String key) {
        return values.get(key);
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * ActionListener interface implementation.
     */
    
    /**
     * This method doesn't do anything.
     * @param event The source action event.
     */
    public void actionPerformed(ActionEvent event) {
        
    }

    /** 
     * Return true if the action is enabled, false otherwise.
     * @return True if the action is enabled, false otherwise.
     */
    public boolean isEnabled() {
        return ((Boolean) values.get(ENABLED_KEY)).booleanValue();
    }
    
    /** 
     * Enable or disable the action.
     * @param value True to disable, false otherwise.
     */
    public void setEnabled(boolean value) {
        Boolean oldValue = (Boolean) values.get(ENABLED_KEY);
        Boolean newValue = new Boolean(value);
        values.put(ENABLED_KEY, newValue);
        firePropertyChange(ENABLED_KEY, oldValue, newValue);
    }
    
    /**
     * Register a new listener.
     * @param listener The listener to be registered.
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        listenersList.add(PropertyChangeListener.class, listener);
    }
    
    /** 
     * Remove a previously registered listener.
     * @param listener The listener to be removed.
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        listenersList.remove(PropertyChangeListener.class, listener);
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * Getter and setter methods.
     */

    /**
     * Get the KeyStroke to be used as the accelerator for the action.
     * @return The KeyStroke to be used as the accelerator for the action.
     */
    public KeyStroke getAcceleratorKey() {
        KeyStroke accelerator = (KeyStroke) values.get(ACCELERATOR_KEY);
        return accelerator;
    }
    
    /**
     * Set the KeyStroke to be used as the accelerator for the action.
     * @param accelerator The KeyStroke to be used as the accelerator for the
     *     action.
     */
    public void setAcceleratorKey(KeyStroke accelerator) {
        KeyStroke oldValue = (KeyStroke) values.get(ACCELERATOR_KEY);
        KeyStroke newValue = accelerator;
        values.put(ACCELERATOR_KEY, newValue);
        firePropertyChange(ACCELERATOR_KEY, oldValue, newValue);
    }
    
    /**
     * Get the command String for the ActionEvent that will be created when an
     * Action is going to be notified as the result of residing in a Keymap.
     * @return The command String for the ActionEvent that will be created when
     *     an Action is going to be notified as the result of residing in a
     *     Keymap.
     */
    public String getActionCommandKey() {
        String key = (String) values.get(ACTION_COMMAND_KEY);
        return key;
    }
    
    /**
     * Set the command String for the ActionEvent that will be created when an
     * Action is going to be notified as the result of residing in a Keymap.
     * associated with a JComponent.
     * @param key
     */
    public void setActionCommandKey(String key) {
        String oldValue = (String) values.get(ACTION_COMMAND_KEY);
        String newValue = key;
        values.put(ACTION_COMMAND_KEY, newValue);
        firePropertyChange(ACTION_COMMAND_KEY, oldValue, newValue);
    }
    
    /**
     * Return a long String description for the action, could be used for
     * context-sensitive help.
     * @return  a long String description for the action, could be used for
     *     context-sensitive help.
     */
    public String getLongDescription() {
        String description = (String) values.get(LONG_DESCRIPTION);
        return description;
    }
    
    /**
     * Set a long String description for the action, could be used for
     * context-sensitive help.
     * @param description
     */
    public void setLongDescription(String description) {
        String oldValue = (String) values.get(LONG_DESCRIPTION);
        String newValue = description;
        values.put(LONG_DESCRIPTION, newValue);
        firePropertyChange(LONG_DESCRIPTION, oldValue, newValue); 
    }

    /**
     * Get a KeyEvent to be used as the mnemonic for the action.
     * @return A KeyEvent to be used as the mnemonic for the action.
     */
    public String getMnemonicKey() {
        String key = (String) values.get(MNEMONIC_KEY);
        return key;
    }
    
    /**
     * Set a KeyEvent to be used as the mnemonic for the action.
     * @param description
     */
    public void setMnemonicKey(String key) {
        String oldValue = (String) values.get(MNEMONIC_KEY);
        String newValue = key;
        values.put(MNEMONIC_KEY, newValue);
        firePropertyChange(MNEMONIC_KEY, oldValue, newValue); 
    }
    
    /**
     * Get a short String description for the action, used for tooltip text.
     * @return A short String description for the action, used for tooltip text.
     */
    public String getShortDescription() {
        String description = (String) values.get(SHORT_DESCRIPTION);
        return description;
    }
    
    /**
     * Set a short String description for the action, used for tooltip text.
     * @param description
     */
    public void setShortDescription(String description) {
        String oldValue = (String) values.get(SHORT_DESCRIPTION);
        String newValue = description;
        values.put(SHORT_DESCRIPTION, newValue);
        firePropertyChange(SHORT_DESCRIPTION, oldValue, newValue); 
    }
    
    /**
     * Get the String name for the action, used for a menu or button.
     * @return The String name for the action, used for a menu or button.
     */
    public String getName() {
        String name = (String) values.get(NAME);
        return name;
    }
    
    /**
     * Set the String name for the action, used for a menu or button.
     * @param name
     */
    public void setName(String name) {
        String oldValue = (String) values.get(NAME);
        String newValue = name;
        values.put(NAME, newValue);
        firePropertyChange(NAME, oldValue, newValue);  
    }
    
    /**
     * Get a small Icon, such as ImageIcon, for the action, used for toolbar
     * buttons.
     * @return A small Icon, such as ImageIcon, for the action, used for toolbar
     *     buttons.
     */
    public Icon getSmallIcon() {
        Icon icon = (Icon) values.get(SMALL_ICON);
        return icon;
    }
    
    /**
     * Set a small Icon, such as ImageIcon, for the action, used for toolbar
     * buttons.
     * @param icon
     */
    public void setSmallIcon(Icon icon) {
        Icon oldValue = (Icon) values.get(SMALL_ICON);
        Icon newValue = icon;
        values.put(SMALL_ICON, newValue);
        firePropertyChange(SMALL_ICON, oldValue, newValue); 
    }

    /* /////////////////////////////////////////////////////////////////////////
     * MouseListener interface implementation.
     */
    
    /** {@inheritDoc} */
    public void mouseClicked(MouseEvent e) {

    }

    /** {@inheritDoc} */
    public void mouseEntered(MouseEvent e) {

    }

    /** {@inheritDoc} */
    public void mouseExited(MouseEvent e) {

    }

    /** {@inheritDoc} */
    public void mousePressed(MouseEvent e) {

    }

    /** {@inheritDoc} */
    public void mouseReleased(MouseEvent e) {

    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * Private methods.
     */
    
    /** 
     * When an action property is changed, an event is sent to all registered
     * listeners.
     * @param propertyName The property name.
     * @param oldValue The old property value.
     * @param newValue The new property value.
     */ 
    private void firePropertyChange(String propertyName, Object oldValue,
            Object newValue) {

        PropertyChangeEvent e = new PropertyChangeEvent(this, propertyName,
            oldValue,  newValue);
        
        // Guaranteed to return a non-null array.
        
        Object[] listeners = listenersList.getListenerList();
        
        // Process the listeners last to first, notifying those that are
        // interested in this event.
        
        for (int i = listeners.length -2; i >= 0; i -=2) {
            if (listeners[i] == PropertyChangeListener.class) {
                ((PropertyChangeListener) listeners[i+1]).propertyChange(e);
            }
        }
    }
}