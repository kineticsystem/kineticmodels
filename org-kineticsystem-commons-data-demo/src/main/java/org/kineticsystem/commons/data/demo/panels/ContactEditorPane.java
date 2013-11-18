/*
 * ContactEditorPane.java
 *
 * Created on 15 maggio 2006, 17.30
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

package org.kineticsystem.commons.data.demo.panels;

// Java classes.

import java.awt.*;
import javax.swing.*;
import javax.swing.text.*;

// Application classes.

import org.kineticsystem.commons.data.controller.*;
import org.kineticsystem.commons.data.view.*;
import org.kineticsystem.commons.layout.*;
import org.kineticsystem.commons.random.bean.*;

/**
 *
 * @author Giovanni Remigi
 * $Revision: 152 $
 */
public class ContactEditorPane extends JPanel implements Editor {
    
    /** Class version number. */
    private static final long serialVersionUID = 1L;
    
    // The edited object.
    private RandomContact contact;
    
    /* /////////////////////////////////////////////////////////////////////////
     * GUI components.
     */
    
    /** Contact name field. */
    private JTextField nameField;
    
    /** Contact surname field. */
    private JTextField surnameField;
    
    private DataNavigator dataNavigator;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constructors.
     */
    
    /** Default constructor */
    public ContactEditorPane() {
        initComponents();
    }
    
    /** Components initialization. */
    private void initComponents() {
        TetrisLayout layout = new TetrisLayout(2, 2);
        layout.setColWeight(0, 0);
        layout.setColWeight(1, 100);
        setLayout(layout);
        
        nameField = new JTextField(10);
        surnameField = new JTextField(10);
        
        AbstractDocument doc;
        doc = (AbstractDocument) nameField.getDocument();
        doc.setDocumentFilter(new FixedSizeFilter(50));
        doc = (AbstractDocument) surnameField.getDocument();
        doc.setDocumentFilter(new FixedSizeFilter(50));
        
        Cell c = new Cell();
        c.setAnchor(Cell.FIRST_LINE_START);
        
        c.setFill(Cell.NONE);
        add(new JLabel("Name:"), c);
        c.setFill(Cell.BOTH);
        c.setCols(Cell.MAX_VALUE);
        add(nameField, c);
        
        c.setFill(Cell.NONE);
        c.setCols(1);
        add(new JLabel("Surname:"), c);
        c.setFill(Cell.BOTH);
        c.setCols(Cell.MAX_VALUE);
        add(surnameField, c);
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * Editor interface implementation.
     */
    
    /** {@inheritDoc} */
    public boolean creationRequested() {
        contact = new RandomContact();
        nameField.setText(contact.getName());
        surnameField.setText(contact.getSurname());
        return true;
    }

    public void creationExecuted() {
        
    }
    
    public boolean creationChecked() {
        return true;
    }
    
    public void creationCancelled() {
        
    }
    
    /** {@inheritDoc} */
    public boolean changeRequested() {
        contact = (RandomContact) dataNavigator.getNavigator()
            .getSelectedObject();
        nameField.setText(contact.getName());
        surnameField.setText(contact.getSurname());
        return true;
    }
    
    public void changeExecuted() {
        
    }
    
    public void changeCancelled() {
        
    }
    
    /** {@inheritDoc} */
    public boolean changeChecked() {
        return true;
    }
    
    /** {@inheritDoc} */
    public boolean removalRequested() {
        Window window = SwingUtilities.getWindowAncestor(this);
        int result = JOptionPane.showConfirmDialog(window,
            "Delete selected item?", "Request", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            return true;
        } else {
            return false;
        }
    }
    
    public void removalExecuted() {
        
    }
    
    public void removalCancelled() {
        
    }
    
    /** {@inheritDoc} */
    public void setObject(Object obj) {
        contact = (RandomContact) obj;
        if (contact != null) {
            nameField.setText(contact.getName());
            surnameField.setText(contact.getSurname());
        } else {
            nameField.setText("");
            surnameField.setText("");
        }
    }
    
    /** {@inheritDoc} */
    public Object getObject() {
        contact.setName(nameField.getText());
        contact.setSurname(surnameField.getText());
        return contact;
    }
    
    /** {@inheritDoc} */
    public void setDataNavigator(DataNavigator dataNavigator) {
        
        DataNavigatorMediator ecm = new DataNavigatorMediator();
        ecm.setNataDavigator(dataNavigator);
        nameField.addKeyListener(ecm);
        surnameField.addKeyListener(ecm);
        this.dataNavigator = dataNavigator;
    }
}