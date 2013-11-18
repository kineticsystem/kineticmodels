/*
 * DialogChooser.java
 *
 * Created on February 8, 2004, 2:53 PM
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

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

// Application classes.

import org.kineticsystem.commons.layout.Cell;
import org.kineticsystem.commons.layout.DefaultConnector;
import org.kineticsystem.commons.layout.TetrisLayout;

/**
 * Simple option window showing different layout capabilities.
 * @author Giovanni Remigi
 * $Revision: 150 $
 */
public class DialogChooser extends JDialog {
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constants.
     */
    
    /** Class serial version number. */
    private static final long serialVersionUID = 1L;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Components.
     */
    
    /** Actions for selecting test. */
    private LinkedList<Action> actions;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constructor.
     */
    
    /** Default constructor. */
    public DialogChooser() {
        
        // Create the layout.
        
        TetrisLayout layout = new TetrisLayout(5, 3);
        for (int i = 0; i < 3; i++) {
            layout.setColConnector(i, new DefaultConnector(DefaultConnector
                .MAX));
        }
        
        // Setup the layout.
        
        layout.setGap(5);
        
        // Dialog configuration.
        
        setTitle("TetrisLayout test");
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
           public void windowClosing(WindowEvent e) {
               System.exit(1);
           }
        });
        
        JPanel pane = new JPanel();
        pane.setLayout(layout);
        
        // Create actions.
        
        actions = new LinkedList<Action>();

        // Test cases.
        
        Action action;
        
        action = new TestAction("BorderLayout simulation",
            "org.kineticsystem.commons.layout.demo.BorderLayoutPane");
        actions.add(action);
        
        action = new TestAction("GridLayout simulation",
            "org.kineticsystem.commons.layout.demo.GridLayoutPane");
        actions.add(action);    
        
        action = new TestAction("Weighed grid layout test", 
            "org.kineticsystem.commons.layout.demo.WeighedGridLayoutPane");
        actions.add(action);
        
        action = new TestAction("Variable grid layout test", 
            "org.kineticsystem.commons.layout.demo.VariableGridLayoutPane");
        actions.add(action);
        
        action = new TestAction("Variable gap test", 
            "org.kineticsystem.commons.layout.demo.VariableGapPane");
        actions.add(action);
        
        action = new TestAction("Horizontal pack test", 
            "org.kineticsystem.commons.layout.demo.HorizontalPackPane");
        actions.add(action);
        
        action = new TestAction("Vertical pack test", 
            "org.kineticsystem.commons.layout.demo.VerticalPackPane");
        actions.add(action);
        
        action = new TestAction("Anchor and fill test",
            "org.kineticsystem.commons.layout.demo.AnchorFillPane");
        actions.add(action);
        
        action = new TestAction("Simple form test",
            "org.kineticsystem.commons.layout.demo.SimpleFormPane");
        actions.add(action);
        
        action = new TestAction("Nested layout test",
            "org.kineticsystem.commons.layout.demo.NestedLayoutPane");
        actions.add(action);
        
        action = new TestAction("Power grid test",
            "org.kineticsystem.commons.layout.demo.PowerGridPane");
        actions.add(action);
        
        action = new TestAction("Vortex grid test",
            "org.kineticsystem.commons.layout.demo.VortexGridPane");
        actions.add(action);
        
        action = new TestAction("Horizontal mirror test",
            "org.kineticsystem.commons.layout.demo.HMirrorPane");
        actions.add(action);
        
        action = new TestAction("Vertical mirror test test",
            "org.kineticsystem.commons.layout.demo.VMirrorPane");
        actions.add(action);
        
        action = new TestAction("Mirror test",
            "org.kineticsystem.commons.layout.demo.HVMirrorPane");
        actions.add(action);
        
        // Define constraints.
        
        Cell cell = new Cell();
        
        // Add components.
        
        Iterator<Action> iter = actions.iterator();
        while (iter.hasNext()) {
            action = (Action) iter.next();
            JButton button = new JButton(action);
            pane.add(button, cell);
        }
        
        pane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(pane);
        
        // Menu.
        
        JMenuBar menuBar;
        JMenu menu;
        JMenuItem item;
        
        menuBar = new JMenuBar();
        
        menu = new JMenu("Test");
        iter = actions.iterator();
        while (iter.hasNext()) {
            action = (Action) iter.next();
            item = new JMenuItem(action);
            menu.add(item);
        }
        menuBar.add(menu);
        
        menu = new JMenu("Help");
        item = new JMenuItem(new AboutAction());
        menu.add(item);
        menuBar.add(menu);
        
        setJMenuBar(menuBar);
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * Nested classes.
     */
    
    /** Action executing a test case. */
    private class TestAction extends AbstractAction {
        
        /** Class serial version number. */
        private static final long serialVersionUID = 1L;        
        
        /** Test class to being executed. */
        private String className;
        
        /** The action title. */
        private String title;
        
        /** Il costruttore predefinito. */
        public TestAction(String title, String className) {
            putValue(Action.NAME, title);
            this.title = title;
            this.className = className;
        }
        
        /**
         * Execute an action.
         * @param e The action event.
         */
        public void actionPerformed(ActionEvent event) {
            
            JPanel pane = null;
            try {
                 pane = (JPanel) Class.forName(className).newInstance();
            } catch (Exception ex) {
                System.out.println("Unexpected exception!");
            }
            
            // Show the correspondig test dialog.

            JDialog dialog = new BaseDialog(pane);
            dialog.setTitle(title);
            dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            dialog.pack();
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            Dimension size = dialog.getPreferredSize();
            dialog.setLocation(screenSize.width/2 - (size.width/2),
                screenSize.height/2 - (size.height/2));
            dialog.setVisible(true);
        }
    }
    
    /** Show info. */
    private class AboutAction extends AbstractAction {
        
        /** Class serial version number. */
        private static final long serialVersionUID = 1L;        
        
        /** Il costruttore predefinito. */
        public AboutAction() {
            putValue(Action.NAME, "About");
        }
        
        /**
         * Execute an action.
         * @param e The action event.
         */
        public void actionPerformed(ActionEvent event) {
            AboutDialog dialog = new AboutDialog();
            dialog.setModal(true);
            dialog.setVisible(true);
        }
    } 
}
