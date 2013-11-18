/*
 * MonitorWindow.java
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
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.MessageFormat;
import java.util.ResourceBundle;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

/**
 * This window can be used together with a <code>DefaultMonitor</code> object.
 * It is a <code>MonitorListener</code> and it can display all information
 * retrieved by the monitor. It is an <code>Interrupter</code> too and it can
 * request the interruption of the monitored thread throw the monitor itself.
 * @author Giovanni Remigi
 * $Revision: 156 $
 */
public class JMonitorWindow extends JDialog implements Interrupter,
        MonitorListener, ActionListener {
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constants.
     */
    
    /** Serial number. */
    private static final long serialVersionUID = 1L;
    
    /** The resource bundle used for localizing the display. */
    private static final String BUNDLE
       = "org.kineticsystem.commons.threads.bundle.MonitorBundle";
    
    /** The latency time to elapse before the dialog is shown. */
    public final int DEFAULT_LATENCY_TIME = 1500;
      
    /* /////////////////////////////////////////////////////////////////////////
     * Private variables.
     */
    
    /** 
     * True if the user has requested an the interruption of the monitored
     * thread. 
     */
    private boolean cancelled;
    
    /** This object contains all information obtained by the monitor. */
    private MonitorInfo info;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Graphic components.
     */
    
    /** The progress bar. */
    private JProgressBar progressBar;
    
    /** The label used to show messages. */
    private JLabel messageLabel;
    
    /** The interruption button. */
    private JButton cancelBtn;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constructor and initializing methods.
     */
    
    /**
     * Constructor with window owner.
     * @param owner The waiting window owner.
     */
    public JMonitorWindow(Frame owner) {
        super(owner);
        initComponents();
    }
    
    /**
     * Constructor with window owner.
     * @param owner The waiting window owner.
     */
    public JMonitorWindow(Dialog owner) {
        super(owner);
        initComponents();
    }
    
    /** The default constructor. */
    public JMonitorWindow() {
        super();
        initComponents();
    }
    
    /** Initializing method. */
    private void initComponents() {
        
        info = new MonitorInfo();
        
        cancelled = false;
        
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        
        String title = (String) ResourceBundle.getBundle(BUNDLE)
            .getObject("MonitorTitle");
        setTitle(title);

        addWindowListener(new WindowAdapter() {
            
            // The window can be closed only if the thread is interruptible.
            public void windowClosing(WindowEvent e) {
                close();
            }
        });
        
        progressBar = new JProgressBar();
        progressBar.setBorderPainted(true);
        progressBar.setStringPainted(true);
        progressBar.setPreferredSize(new Dimension(300,
            progressBar.getPreferredSize().height));
        progressBar.setMinimum(0);
        progressBar.setMaximum(100);    
        
        Icon icon = UIManager.getIcon("OptionPane.informationIcon");
        
        messageLabel = new JLabel();
        messageLabel.setHorizontalAlignment(JLabel.LEFT);

        JPanel content = new JPanel(new BorderLayout(5, 5));
        content.add(messageLabel, BorderLayout.CENTER);
        content.add(progressBar, BorderLayout.SOUTH);
        content.add(new JLabel(icon), BorderLayout.WEST);
        
        JPanel buttons = new JPanel(new FlowLayout());
        
        String cancelLabel = (String) ResourceBundle.getBundle(BUNDLE)
            .getObject("CancelAction");
        cancelBtn = new JButton(cancelLabel);
        cancelBtn.setEnabled(false);
        cancelBtn.addActionListener(this);
        buttons.add(cancelBtn);
        
        JPanel mainPane = new JPanel(new BorderLayout());
        mainPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        
        mainPane.add(content, BorderLayout.CENTER);
        mainPane.add(buttons, BorderLayout.SOUTH);
        getContentPane().add(mainPane, BorderLayout.CENTER);
        
        getRootPane().setDefaultButton(cancelBtn);
        getRootPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        
        setResizable(false);
        pack();
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * MonitorListener interface implementation.
     */
    
    /**
     * This method is invoked by a thread monitor at regular intervals.
     * @param e The monitor event.
     */
    public void processChanged(MonitorEvent e) {

        if (e.getType() == MonitorEvent.LAST_EVENT) {
            progressBar.setIndeterminate(false);
            dispose();
        } else {
            
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
                
                String title = (String) ResourceBundle.getBundle(BUNDLE)
                    .getObject("MonitorTitle");
                setTitle(title);
                progressBar.setIndeterminate(true);
                messageLabel.setText(info.getMessage());
                
                String message = (String) ResourceBundle.getBundle(BUNDLE)
                    .getObject("IndeterminateMessage");
                progressBar.setString(message);
                
            } else {
                            
                String title = info.getDescription();
                setTitle(title);
   
                progressBar.setIndeterminate(false);
                progressBar.setValue(info.getValue());
                   
                String message = (String) ResourceBundle.getBundle(BUNDLE)
                    .getObject("DeterminateMessage");
                message = MessageFormat.format(message, new Object[] {
                    new Integer(info.getValue()), 
                    new Integer(100)
                });
                progressBar.setString(message);
                messageLabel.setText(info.getMessage());
            }
            cancelBtn.setEnabled(info.isInterruptable() && !cancelled);
        }
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * Interrupter interface implementation.
     */
    
    /**
     * It returns true when the user request the interruption of the monitored
     * thread.
     * @return True if the user request an interrption.
     */
    public boolean isCancelled() {
        return cancelled;
    }
    
    /** Swith off the interrupter. */
    public void reset() {
        cancelled = false;
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * ActionListener interface implementation.
     */
    
    /**
     * This method is invoked when the user click the interruption button.
     * @param evt The interruption event.
     */
    public void actionPerformed(ActionEvent evt) {
        close();
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * Service methods.
     */
    
    /**
     * This method requests the interruption of the monitored thread. To
     * stop the thread two events must simultaneously occur: the thread must
     * be interruptable; no previously interruption must have been requested;
     */
    private void close() {
        if ((info != null) && info.isInterruptable() && !cancelled) {
            cancelBtn.setEnabled(false);
            cancelled = true;
        }
    }
}
