/*
 * MonitorBar.java
 *
 * Created on June 18, 2003, 7:20 PM
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

package org.kineticsystem.commons.threads;

// Java classes.

import java.awt.BorderLayout;
import java.text.MessageFormat;
import java.util.ResourceBundle;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

/**
 * This is a simple progress bar that can show information about the monitored
 * thread. It is a <code>MonitorListener</code> and must be used together with a
 * <code>DefaultMonitor</code>.
 * @author Giovanni Remigi
 * $Revision: 162 $
 */
public class JMonitorBar extends JPanel implements MonitorListener {
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constants.
     */
    
    /** Version number. */
    private static final long serialVersionUID = 1L;    
    
    /** The resource bundle. */
    private static final String BUNDLE
       = "org.kineticsystem.commons.threads.bundle.MonitorBundle";
      
    /* /////////////////////////////////////////////////////////////////////////
     * Private variables.
     */
    
    /** This object contains all information obtained by the monitor. */
    private MonitorInfo info;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Graphic components.
     */
    
    /** The progress bar. */
    private JProgressBar progressBar;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constructors and initializing methods.
     */
    
    /** The default constructor. */
    public JMonitorBar() {
        initComponents();
    }
    
    /** The initialization method. */
    private void initComponents() {
        
        info = new MonitorInfo();
        
        setLayout(new BorderLayout());
        
        progressBar = new JProgressBar();
        progressBar.setBorderPainted(true);
        progressBar.setStringPainted(true);
        progressBar.setMinimum(0);
        progressBar.setMaximum(100);
        
        add(progressBar, BorderLayout.CENTER);
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * MonitorListener interface implementation.
     */
    
    /**
     * This method is invoked my a thread monitor at regular intervals.
     * @param e The monitor event.
     */
    public void processChanged(MonitorEvent e) {
            
        // Clone the monitor information to avoid synchronization problems.
         
        try {
            
            e.getInfo().getLock().readLock().lock();
            
            info.setMessage(e.getInfo().getMessage());
            info.setDescription(e.getInfo().getDescription());
            info.setInterruptable(e.getInfo().isInterruptable());
            info.setValue(e.getInfo().getValue());
            info.setIndeterminate(e.getInfo().isIndeterminate());
            info.setState(e.getInfo().getState());
            
        } finally {
            e.getInfo().getLock().readLock().unlock();
        }
        
        if (info.isIndeterminate()) {
            
            progressBar.setIndeterminate(true);
            
            String message = (String) ResourceBundle.getBundle(BUNDLE)
                .getObject("IndeterminateMessage");
            progressBar.setString(message);
                
        } else {
   
            progressBar.setIndeterminate(false);
            progressBar.setValue(info.getValue());
               
            String message = (String) ResourceBundle.getBundle(BUNDLE)
                .getObject("DeterminateMessage");
            message = MessageFormat.format(message, new Object[] {
                new Integer(info.getValue()), 
                new Integer(100)
            });
            progressBar.setString(message);
        }
        
        if (e.getType() == MonitorEvent.LAST_EVENT) {
            progressBar.setString("Done");
        }
    }
}
