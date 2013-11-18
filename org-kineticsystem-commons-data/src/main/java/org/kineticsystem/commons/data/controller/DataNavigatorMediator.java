/*
 * EditorComponentMediator.java
 *
 * Created on August 20, 2004, 12:33 PM
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

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.text.JTextComponent;

/**
 * This class is a used as listener for <tt>JTextField</tt>, 
 * <tt>JComboBox</tt>, <tt>JCheckBox</tt> GUI components. When the user edits
 * the content of one of these components, a change request is sent to the
 * underlying editor that changes automatically its internal state. Instead of
 * manually press the edit button of the editor, the user can simply type
 * something in a <tt>JTextField</tt> or change the value of a
 * <tt>JComboBox</tt> or <tt>JCheckBox</tt> to enter the editor editing state.
 * Register this listener with any of the listed GUI components to automatically
 * change the state of the given editor.
 * @author Giovanni Remigi
 * @version $Revision: 151 $
 */
public class DataNavigatorMediator implements ActionListener, KeyListener,
        ItemListener {
    
    /* /////////////////////////////////////////////////////////////////////////
     * Private variables.
     */
    
    /** The associated editor. */
    private DataNavigator dataNavigator;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constructors.
     */
    
    /** Default constructor. */
    public DataNavigatorMediator() {
        dataNavigator = null;
    }

    /* /////////////////////////////////////////////////////////////////////////
     * Setter methods.
     */
    
    /**
     * Set the editor that can react to gui components events.
     * @param editor The editor controller.
     */
    public void setNataDavigator(DataNavigator editor) {
        this.dataNavigator = editor;
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * ActionListener interface implementation.
     */
    
    /**
     * This is used to intercept changes in <tt>JCheckBox</tt> components.
     * @param event <tt>JCheckBox</tt> event.
     */
    public void actionPerformed(ActionEvent event) {
        update();
    }

    /* /////////////////////////////////////////////////////////////////////////
     * KeyListener interface implementation.
     */
    
    /**
     * This is used to intercept changes in <tt>JEditorPane</tt>,
     * <tt>JTextArea</tt>, <tt>JTextField</tt> components.
     * @param event <tt>JTextField</tt> event.
     */
    public void keyPressed(KeyEvent event) {

        // Check if a key event has changed a text component content.
        
        final JTextComponent textField = (JTextComponent) event.getComponent();
        final String oldText = textField.getText();
        
        // Add a "check" event at the end of the Event-dispatching thread queue.
        
        Runnable runner = new Runnable() {
            public void run() {
                String newText = textField.getText();
                if (!newText.equals(oldText)) {
                    update();
                    textField.setText(newText);
                }
            }
        };
        EventQueue.invokeLater(runner);
    }
    
   /**
     * Not implemented.
     * @param event <tt>JTextField</tt> event.
     */
    public void keyReleased(KeyEvent event) {
        // Not implemented.
    }
    
   /**
     * Not implemented.
     * @param event <tt>JTextField</tt> event.
     */
    public void keyTyped(KeyEvent e) {
        // Not implemented.
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * ItemListener interface implementation.
     */
    
    /**
    /**
     * This is used to intercept changes in <tt>JComboBox</tt> components.
     * @param event <tt>JComboBox</tt> event.
     */
    public void itemStateChanged(ItemEvent e) {
        Component cmp = (Component) e.getSource();
        if (cmp.isFocusOwner() && (e.getStateChange() == ItemEvent.SELECTED)) {
            update();
        }
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * Private methods.
     */
    
    /** If possible, enter the editor editing mode. */
    private void update() {
        if (dataNavigator != null) {
            switch(dataNavigator.getState()) {
                case DataNavigator.DEFAULT_STATE:
                    dataNavigator.change();
                    break;
                case DataNavigator.EMPTY_STATE:
                    dataNavigator.create();
                    break;
            }
        }
    }
}