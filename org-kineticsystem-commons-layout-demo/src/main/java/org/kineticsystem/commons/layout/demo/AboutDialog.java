/*
 * AboutDialog.java
 *
 * Created on August 23, 2003, 11:44 AM
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

package org.kineticsystem.commons.layout.demo;

// Java classes.

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

/**
 * About dialog.
 * @author Giovanni Remigi
 * $Revision: 41 $
 */
public class AboutDialog extends JDialog {
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constants.
     */
    
    /** Numero di versione della classe. */
    private static final long serialVersionUID = 1L;    
     
    /* /////////////////////////////////////////////////////////////////////////
     * Constructors and initializing methods.
     */
    
    /** Default constructor. */
    public AboutDialog() {
        setTitle("About");
        initComponents();
    }
    
    /** Initialize components. */
    private void initComponents() {
        
        // Read the about file.
        
        String name = "org/kineticsystem/commons/layout/demo/About.html";
        StringBuffer buffer = new StringBuffer();
        try {
            InputStream inStream = ClassLoader.getSystemClassLoader()
                .getResourceAsStream(name);
            InputStreamReader isr = new InputStreamReader(inStream);
            Reader in = new BufferedReader(isr);
            int ch;
            while ((ch = in.read()) > -1) {
                buffer.append((char) ch);
            }
            in.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        // Layout components.

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        
        JPanel aboutPanel = new JPanel();
        aboutPanel.setLayout(new BorderLayout());
        aboutPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        
        JEditorPane aboutEditor = new JEditorPane();
        aboutEditor.setEditable(false);
        aboutEditor.setFocusable(false);
        aboutEditor.setContentType("text/html");
        aboutEditor.setText(buffer.toString());
        
        JScrollPane aboutScrollPane = new JScrollPane(aboutEditor);
        aboutPanel.add(aboutScrollPane, BorderLayout.CENTER);
        
        getContentPane().add(aboutPanel, BorderLayout.CENTER);
        
        pack();
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension size = getPreferredSize();
        setLocation(screenSize.width/2 - (size.width/2),
            screenSize.height/2 - (size.height/2));
    }
}