/*
 * DebugGlassPane.java
 *
 * Created on 19 gennaio 2006, 19.17
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

package org.kineticsystem.commons.layout;

// Java classes.

import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.LayoutManager;
import javax.swing.JComponent;

/**
 * <p>This is a glass pane that can be used to debug layouts and nested layouts
 * too.<p>
 * <p>Add the glass pane to a <code>JDialog</code> or a <code>JFrame</code></p>
 * object to draw all nested layouts. Follow the next example:</p>
 * <pre>
 * ...
 * JPanel main = new JPanel();
 * this.setContentPane(main);
 *
 * DebugGlassPane debugPane = new DebugGlassPane();
 * debugPane.setContainer(main);
 * setGlassPane(debugPane);
 * debugPane.setVisible(true);
 * ...
 * </pre>
 * @author Giovanni Remigi
 * $Revision: 37 $
 */
public class DebugGlassPane extends JComponent {
    
    /** Class serial number. */
    private final static long serialVersionUID = 1L;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Properties
     */
    
    /** This is the main container to debug. */
    private Container root;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constructors.
     */
    
    /** Default constructor. */
    public DebugGlassPane() {
        root = null;
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * Getter and setter methods.
     */
    
    /**
     * Set the main container to debug.
     * @param root The main container to debug.
     */
    public void setContainer(Container root) {
        this.root = root;
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * JComponent overridden methods.
     */
    
    /**
     * Draw the layout.
     * @param g The current graphic context.
     */
    public void paint(Graphics g) {
        if (root != null) {
            recursivePaint(root, g);
        }
        g.dispose();
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * Private methods.
     */
    
    /**
     * Recursively draw all nested layouts.
     * @param comp The component to debug.
     * @param g The current graphic context.
     */
    private void recursivePaint(Component comp, Graphics g) {
        
        if (comp instanceof Container) {
            
            int x = comp.getX();
            int y = comp.getY();
            g.translate(x, y);
            
            Container cont = (Container) comp;
            
            // Draw container layout
            
            LayoutManager layout = cont.getLayout();
            if (layout instanceof TetrisLayout) {
                ((TetrisLayout) layout).drawLayout(g);
            }
            
            // Draw sub-containers layouts.
            
            Component[] comps = cont.getComponents();
            for (int i = 0; i < comps.length; i++) {
                Component child = cont.getComponent(i);
                recursivePaint(child, g);
            }

            g.translate(-x, -y);
        }
    }
}
