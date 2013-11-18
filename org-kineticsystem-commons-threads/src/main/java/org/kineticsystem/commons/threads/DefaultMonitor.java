/*
 * DefaultMonitor.java
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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.event.EventListenerList;

/**
 * <p>Using this object you can monitor a thread activity.<p/> 
 * <p>The monitored thread must extend the <code>MonitoredThread</code> class.
 * This object, a monitor, must be used together with one or more
 * <code>MonitorListener</code>. A listener uses information coming from the
 * monitored thread to display them in any possible way. The monitor is used
 * together with one or more <code>Interrupter</code> too. An interrupter is a
 * switch that can request the interruption of the monitored thread.<p>
 * <p>Here is a code example using a <code>MonitorWindow</code> to display
 * information:</p>
 * <p><pre>
 * MonitoredThread mt = new MyMonitoredThread();
 * mt.start();
 * MonitorWindow mw = new MonitorWindow();
 * mw.setModal(true);
 * mw.setLocationRelativeTo(null);
 * DefaultMonitor monitor = new DefaultMonitor(mt);
 * monitor.addMonitorListener(mw);
 * monitor.addInterrupter(mw);
 * monitor.start();
 * mw.setVisible(true);
 * </p></pre>
 * <p>After you instantiated and started a <code>MonitoredThread</code> you must
 * create a monitor that catches information, stored inside the thread, by the
 * <code>getInfo</code> method of <code>Monitorable</code> interface.
 * The monitor sends <code>MonitorEvent</code> to all registered
 * <code>MonitorListener</code>. The <code>MonitorWindow</code> is a custom
 * <code>MonitorListener</code>. You can add <code>Interrupter</code> to the
 * main monitor by the method <code>addInterrupter</code>. The monitor tests the
 * <code>isCancelled</code> methods of all interrupters: if one of them returns
 * true, the monitor requests an interruption depending either the thread is
 * interruptable or not.</p>
 * <p>The monitor checks the thread status and the switches status too, sends
 * monitoring events to all registered listener and, if requested, tries to call
 * the <code>interrupt</code> method of the monitored thread.</p>
 * @author Giovanni Remigi
 * $Revision: 161 $
 */
public class DefaultMonitor implements ActionListener {
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constants.
     */
            
    /** The monitoring time slot in milliseconds. */
    public int DEFAULT_MONITORING_TIME = 20;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Private variables.
     */
    
    /** The list containing all the registered listeners. */
    private EventListenerList listeners;
    
    /** The list of switches used to interrupt the monitored thread. */
    private LinkedList<Interrupter> interrupters;
    
    /** The internal timer used to monitor the process at regular intervals. */
    private javax.swing.Timer monitor;
    
    /** The monitored thread. */
    private MonitoredThread thread;
    
    /** True if the user has requested for an interruption. */
    private boolean interrupting;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constructors and initializing methods.
     */
    
    /**
     * The main constructor.
     * @param thread The thread to be monitored.
     * 
     */
    public DefaultMonitor(MonitoredThread thread) {
        this.thread = thread;
        listeners = new EventListenerList();
        interrupters = new LinkedList<Interrupter>();
        
        interrupting = false;
        
        monitor = new javax.swing.Timer(DEFAULT_MONITORING_TIME, this);
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * Public methods.
     */
    
    /** Start the monitoring process. */
    public void start() {
        // Set all interrupter status to false.
        Iterator<Interrupter> iter = interrupters.iterator();
        while (iter.hasNext()) {
            ((Interrupter) iter.next()).reset();
        }
        // Start the monitor.
        monitor.start();
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * ActionListener interface implementation.
     */
    
    /**
     * This method is invoked by the internal Timer inside the AWT 
     * event-dispatching thread.
     * @param e The timer event.
     */
    public void actionPerformed(ActionEvent e) {
        
        /*
         * Check the status of interrupters. If one of these is true then an
         * interruption is requested.
         */
        
        boolean cancelled = false;
        Iterator<Interrupter> iter = interrupters.iterator();
        Interrupter intr;
        while (iter.hasNext()) {
            intr = (Interrupter) iter.next();
            cancelled = cancelled || intr.isCancelled();
        }
        
        // Execute (if any) the requested interruption.
        if (!interrupting && cancelled) {
            thread.interrupt();
            interrupting = true;
        }
        
        // Monitor the thread status.
        MonitorEvent me;
        if (!thread.isAlive()) { // The thread has been interrupted.
            monitor.stop();
            me = new MonitorEvent(this, MonitorEvent.LAST_EVENT,
                thread.getInfo());
        } else {                 // The thread is still alive.
            me = new MonitorEvent(this, MonitorEvent.DEFAULT_EVENT,
                thread.getInfo());
        }
        fireProcessChanged(me);
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * Listener related methods.
     */
    
    /** 
     * Add a new <code>MonitorListener</code>.
     * @param listener The listener to be registered.
     */
    public void addMonitorListener(MonitorListener listener) {
        listeners.add(MonitorListener.class, listener);
    }

    /** 
     * Remove a previously registered <code>MonitorListener</code>.
     * @param listener The listener to be removed.
     */
    public void removeMonitorListener(MonitorListener listener) {
         listeners.remove(MonitorListener.class, listener);
    }
    
    /**
     * Return all registered listeners.
     * @return The registered listeners.
     */
    public MonitorListener[] getMonitorListeners() {
        return (MonitorListener[]) listeners
            .getListeners(MonitorListener.class);
    }
    
    /**
     * Fire an event to all registered listener.
     * @param evt The monitor event.
     */ 
    public void fireProcessChanged(MonitorEvent evt) {
        Object[] list = listeners.getListenerList();
        for (int i = list.length -2; i >= 0; i -=2) {
            if (list[i] == MonitorListener.class) {
                ((MonitorListener) list[i+1]).processChanged(evt);
            }  
        }
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * Interrupter related methods.
     */
    
    /** 
     * You can request an interruption using one or more switches. 
     * @param intr The switch to be added.
     */
    public void addInterrupter(Interrupter intr) {
        interrupters.add(intr);
    }

    /** 
     * Remove a previously added switch.
     * @param intr The previously added switch.
     */
    public void removeInterrupter(MonitorListener intr) {
         interrupters.remove(intr);
    }
}
