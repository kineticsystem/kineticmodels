/*
 * BaseDialog.java
 *
 * Created on 12 giugno 2005, 12.18
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
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

// Application classes.

import org.kineticsystem.commons.layout.DebugGlassPane;

/**
 * This is a base dialog containing a panel with an applied
 * <code>TetrisLayout</code> and some debugging action menu.
 * @author Giovanni Remigi
 * $Revision: 41 $
 */
public class BaseDialog extends JDialog {
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constants.
     */
    
    /** Class serial version number. */
    private static final long serialVersionUID = 1L;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Components.
     */
    
    /** The pane with TetrisLayout applied. */
    private JPanel pane;
    
    /** An info label. */
    private JLabel label;
    
    /** This is a glass pane useful in debugging sessions. */
    private DebugGlassPane debugPane;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constructors.
     */
    
    /** Default constructor. */
    public BaseDialog(JPanel pane) {
        
        this.pane = pane;

        // Viewport.
        
        // TODO: resolve a possible bug with viewport.
        
//        Dimension preferredSize = pane.getPreferredSize();
//        pane.setPreferredSize(pane.getMinimumSize());
//        
//        JScrollPane scrollPane = new JScrollPane(pane);
//        scrollPane.setBorder(new EmptyBorder(new Insets(0, 0, 0, 0)));
//        scrollPane.setPreferredSize(preferredSize);
        
        JPanel main = new JPanel(new BorderLayout());
        main.add(pane, BorderLayout.CENTER);

        // Debug glass pane.
        
        debugPane = new DebugGlassPane();
        debugPane.setContainer(main);
        setGlassPane(debugPane);
        debugPane.setVisible(false);
        
        // Info pane.
        
        label = new JLabel();
        JPanel labelPane = new JPanel(new BorderLayout());
        labelPane.setBorder(new EtchedBorder());
        labelPane.add(label, BorderLayout.CENTER);
        main.add(labelPane, BorderLayout.SOUTH);
        
        // Menu.
        
        JMenuBar menuBar;
        JMenu menu;
        JMenuItem item;
        
        menuBar = new JMenuBar();
        
        menu = new JMenu("Debug");
        item = new JMenuItem(new DebugAction());
        menu.add(item);
        menuBar.add(menu);
        
        setJMenuBar(menuBar);
        setContentPane(main);
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * Nested classes.
     */
    
    /** Enable/disable layout debug. */
    private class DebugAction extends AbstractAction {
        
        /** Class serial version number. */
        private static final long serialVersionUID = 1L;        
        
        /** Il costruttore predefinito. */
        public DebugAction() {
            if (debugPane.isVisible()) {
                putValue(Action.NAME, "Disable");
                label.setText("Debug enabled");
            } else {
                putValue(Action.NAME, "Enable");
                label.setText("Debug disabled");
            }
            pane.repaint();
        }
        
        /**
         * Execute the action.
         * @param e The action event.
         */
        public void actionPerformed(ActionEvent event) {
            if (debugPane.isVisible()) {
                putValue(Action.NAME, "Enable");
                debugPane.setVisible(false);
                label.setText("Debug disabled");
            } else {
                putValue(Action.NAME, "Disable");
                debugPane.setVisible(true);
                label.setText("Debug enabled");
            }
            pane.repaint();
        }
    } 
}
