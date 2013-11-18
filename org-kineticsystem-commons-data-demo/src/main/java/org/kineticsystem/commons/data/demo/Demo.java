/*
 * Demo.java
 *
 * Created on 3 April 2006, 17.30
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

package org.kineticsystem.commons.data.demo;

// Java classes.

import java.awt.*;
import javax.swing.*;

/**
 * Main application class.
 * @author Giovanni Remigi
 * $Revision: 8 $
 */
public class Demo {
    
    /** Default constructor. */
    public Demo() {
        
        JFrame window = new MainFrame();

        window.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        window.pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension size = window.getPreferredSize();
        window.setLocation(screenSize.width/2 - (size.width/2),
            screenSize.height/2 - (size.height/2));
        
        window.setResizable(true);
        window.setTitle("Table demo");
        window.setVisible(true);
    }
    
    public static void main(String[] args) {
        new Demo();
    }
}