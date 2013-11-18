/*
 * JActionButton.java
 *
 * Created on April 6, 2002, 4:05 PM
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

package org.kineticsystem.commons.data.view;

// Java classes.

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.Action;
import javax.swing.JButton;

// Application classes.

import org.kineticsystem.commons.data.controller.AdvancedAction;

/**
 * This is a button holding one or more actions. If a list of actions is
 * associated with the button, the latest enabled one will be used as the
 * default one.
 * @author Giovanni Remigi
 * @version $Revision: 36 $
 */
@SuppressWarnings("serial")
public class JActionButton extends JButton implements PropertyChangeListener {
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constructors.
     */
    
    public JActionButton() {
        
    }
    
    /**
     * Constructor accepting an action.
     * @param action Action to be associated with the button.
     */
    public JActionButton(Action action) {
        setActions(new Action[] {action});
    }
    
    /**
     * Constructor accepting an array actions. The first specified action will
     * be the default one.
     * @param actions Actions to be associated with the button.
     */
    public JActionButton(Action[] actions) {
        setActions(actions);
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * JButton class overriding.
     */
    
    /** {@inheritDoc} */
    @Override
    public void setAction(Action action) {
        
        Action defaultAction = super.getAction();
        if (defaultAction instanceof AdvancedAction) {
            removeMouseListener((AdvancedAction) action);
        }
        
        if (action instanceof AdvancedAction) {
            addMouseListener((AdvancedAction) action);
        }
        super.setAction(action);
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * PropertyChangeListener interface implementation.
     */
    
    /** 
     * When an action is enabled, the button old action is replaced by this one.
     * @param event The source event.
     */
    public void propertyChange(PropertyChangeEvent event) {
        
        if ("enabled" == event.getPropertyName()) {
            if(((Boolean) event.getNewValue()).booleanValue()) {
                
                Action action = super.getAction();
                if (action instanceof AdvancedAction) {
                    removeMouseListener((AdvancedAction) action);
                }
                
                action = (Action) event.getSource();
                if (action instanceof AdvancedAction) {
                    addMouseListener((AdvancedAction) action);
                }
                
                setAction((Action) event.getSource());
            }
        }
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * Private methods.
     */
    
    /**
     * Set button actions.
     * @param actions The array of available actions.
     */
    public void setActions(Action[] actions) {
        
        if ((actions == null) || (actions.length == 0)) {
            throw new IllegalArgumentException("Must specify at least one "
                + "action!");
        }
        
        if (actions.length == 1) {
            setAction(actions[0]);
        } else {

            for (int i = 0; i < actions.length; i++) {
                actions[i].addPropertyChangeListener(this);

            }
            setAction(actions[0]);
        }
    }
}