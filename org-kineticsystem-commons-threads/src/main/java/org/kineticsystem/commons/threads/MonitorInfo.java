/*
 * MonitorInfo.java
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

import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * This is an object that can be used to monitor the status of a method.
 * It is passed to the method as a parameter and the method updates the monitor
 * content with its current status.
 * The monitor can be organized in a tree-like structure so that a method,
 * having a monitor as a parameter, can add additional monitors to be passed to
 * sub methods. The root of the tree-like structure will then return the whole
 * status of methods and sub methods.
 * Because a monitored method can use threads, it is important that the monitor
 * is thread safe. All setter and getter methods are synchronized by a
 * <tt>ReadWriteLock</tt>, the same lock that is returned by the method
 * <tt>getLock</tt>, and thread-safe. To guarantee a thread-safe behavior when
 * using a sequence of getter and setter methods call, a lock has to be
 * acquired.
 * @author Giovanni Remigi
 * $Revision: 157 $
 */
public class MonitorInfo {
    
    /* /////////////////////////////////////////////////////////////////////////
     * Synchronization.
     */
    
    /** 
     * Internal lock used to guarantee synchronization of <tt>getter</tt> and 
     * <tt>synchronize</tt> methods.
     */
    private ReadWriteLock lock;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constants.
     */

    /**
     * The status of the method accepting a monitor as parameter. It can be
     * <tt>NEW</tt>, <tt>RUNNING</tt>, <tt>FINISHED</tt>, <tt>INTERRUPTED</tt>,
     * <tt>FAILED</tt>.
     */
    public static enum Status {
        
        /** The default value when a monitor info is created. */ 
        
        NEW,
        
        /** The monitored method is processing data. */
        
        RUNNING,  
        
        /** The monitored method has finished. */
        
        TERMINATED,    
        
        /** The monitored method has been interrupted interrupted. */
        
        INTERRUPTED, 
        
        /** The monitored method has stopped working due to an error. */
        
        FAILED
    };
    
    /** The resource bundle for localized messages. */
    private static final String BUNDLE
       = "org.kineticsystem.commons.threads.bundle.MonitorBundle";
    
    /** The maximum value accepted by default. */
    private static final int MAX_VALUE = 100;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Variables.
     */
    
    /** A string that describes the current activity of the monitored thread. */
    private String message;
    
    /** Unchangeable description of the monitored thread. */
    private String description;
    
    /** True if the monitored thread is interruptible. */
    private boolean interruptable;
    
    /** 
     * If the monitored thread is determinate, this variable indicates the 
     * current activity advancement status in percentage from 0 to 100.
     */
    private int value;
    
    /** True if the project is determinate, false otherwise. */
    private boolean indeterminate;
    
    /** 
     * The state of the monitored thread. Possible values are:
     * <code>Status.NEW</code>,
     * <code>Status.RUNNING</code>,
     * <code>Status.TERMINATED</code>,
     * <code>Status.INTERRUPTED</code>,
     * <code>Status.FAILED</code>.
     */
    private Status state;
    
    /**
     * A monitor has a tree like structure. When we are monitoring the activity
     * of different nested methods called in serial or parallel flow we use
     * additional monitors.
     */
    private List<MonitorInfo> children;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constructors.
     */
    
    /** Default constructor. */
    public MonitorInfo() {
        
        lock = new ReentrantReadWriteLock();
        message = (String) ResourceBundle.getBundle(BUNDLE)
            .getObject("DefaultThreadMessage");
        description = (String) ResourceBundle.getBundle(BUNDLE)
            .getObject("DefaultThreadDescription");  
        interruptable = false;
        value = 0;
        indeterminate = false;
        state = Status.NEW;
        children = new LinkedList<MonitorInfo>();
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * Getter and setter methods.
     */
    
    /**
     * Return the internal lock used to guarantee a thread-safe behavior of a
     * single call to a setter or getter method. When calling a sequence of
     * setter and getter methods use this method to acquire the internal lock.
     * @return The internal lock.
     */
    public ReadWriteLock getLock() {
        return lock;
    }
    
    /** 
     * This method returns a message describing the activity currently processed
     * by the monitored thread. It can be displayed in a waiting window inside a
     * progress bar to show the user some information about the current
     * activity. The message can change dynamically and its value is
     * constantly checked my the internal monitor timer.
     * If any monitor combined together in a tree-like structure is
     * <tt>INTERRUPTED</tt> or <tt>INTERRUPTED</tt> a default message is
     * displayed no matter the actual message is. In all other cases the
     * message of the root monitor is displayed.
     * This method is thread-safe.
     * @return The string describing the activity currently processed by the
     *     monitored thread.
     */
    public String getMessage() {
        
        try {
            lock.readLock().lock();
            
            String result = message;
            
            if (!children.isEmpty()) {
                for (MonitorInfo monitor : children) {
                    if (monitor.getState() == MonitorInfo.Status.FAILED) {
                        message = (String) ResourceBundle.getBundle(BUNDLE)
                            .getObject("FailedMessage");
                        break;
                    } else if (monitor.getState() == MonitorInfo.Status
                            .INTERRUPTED) {
                        message = (String) ResourceBundle.getBundle(BUNDLE)
                            .getObject("CancellingMessage");;
                        break;
                    }
                }
            }
            return result;
            
        } finally {
            lock.readLock().unlock();
        }
    }
    
    /**
     * This method sets the message describing the activity currently processed
     * by the monitored thread. This method is thread-safe.
     * @param message The string describing the activity currently processed by
     *     the monitored thread.
     */
    public void setMessage(String message) {
        
        try {
            lock.writeLock().lock();
        
            this.message = message;
            
        } finally {
            lock.writeLock().unlock();
        }
    }
    
    /**
     * This method returns a constant message describing the activity of the
     * monitored thread. When monitor are combined together in a tree-like
     * structure the description of the root monitor is returned.
     * This method is thread-safe.
     * @return A constant description of the activity of the monitored thread.
     */
    public String getDescription() {
        
        try {
            lock.readLock().lock();
            
            return description;
            
        } finally {
            lock.readLock().unlock();
        }
    }
    
    /**
     * This method sets a constant description of the activity of the monitored
     * thread. This method is thread-safe.
     * @param description A constant description of the activity of the
     *     monitored thread.
     */
    public void setDescription(String description) {
        
        try {
            lock.writeLock().lock();
            
            this.description = description;
            
        } finally {
            lock.writeLock().unlock();
        }
    }
    
    /**
     * Return true if all the monitored method are interruptible, false 
     * otherwise. This method is thread-safe. 
     * @return True if the thread is interruptible, false otherwise.
     */
    public boolean isInterruptable() {
        
        try {
            lock.readLock().lock();
        
            boolean result = interruptable;
            if (!children.isEmpty()) {
                for (MonitorInfo monitor : children) {
                    result = result && monitor.isInterruptable();
                }
            }
            return result;
            
        } finally {
            lock.readLock().unlock();
        }
    }
    
    /**
     * This method sets the interruptible flag of the monitored thread. When
     * this flag is true, this means that the monitored thread is interruptible.
     * This method is thread-safe.
     * @param interruptable True if the monitored thread is interruptible, false
     *     otherwise.
     */
    public void setInterruptable(boolean interruptable) {
        
        try {
            lock.writeLock().lock();
            
            this.interruptable = interruptable;
            
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * Returned the percentage of the operation performed by the monitored
     * method.
     * This value is valid only if all monitors combined in a tree-like
     * structure are not indeterminate. 
     * This method is thread-safe.
     * @return A value greater then zero that indicates the current activity
     *     step of the monitored monitor.
     */
    public int getValue() {
        
        try {
            lock.readLock().lock();
        
            int n = children.size();
            
            double result = 0;
            if (n > 0) {
                for (MonitorInfo monitor : children) {
                    result += monitor.getValue();
                }
                result = result / (1 + n);
            }
            result += value / (1 + n);
            
            return (int) result;
            
        } finally {
            lock.readLock().unlock();
        }
    }
    
    /**
     * Set the current current activity advancement status in percentage from 0
     * to 100: a value grater than 100 is set to 100 while a value less than 0
     * is set to 0. This method is thread-safe.
     * @param value A value greater then zero that represents the current
     *     activity advancement status in percentage of the monitored thread.
     */
    public void setValue(int value) {
        
        try {
            lock.writeLock().lock();
        
            if (value > MAX_VALUE) {
                this.value = MAX_VALUE;
            } else if (value < 0) {
                this.value = 0;
            } else {
                this.value = value;
            }
            
        } finally {
            lock.writeLock().unlock();
        }
    }
    
    /**
     * This method returns true only if at least one of monitors combined in a
     * tree-like structure is indeterminate.
     * This method is thread-safe.
     * @return True if the thread is determinate, false otherwise.
     */
    public boolean isIndeterminate() {
        
        try {
            lock.readLock().lock();
        
            boolean result = indeterminate;
            if (!children.isEmpty()) {
                for (MonitorInfo monitor : children) {
                    result = result || monitor.isIndeterminate();
                }
            }
            return result;
            
        } finally {
            lock.readLock().unlock();
        }
    }
    
    /**
     * Used to indicate that the monitored thread is determinate or
     * indeterminate. This method is thread-safe.
     * @param indeterminate True if the monitored thread must be indeterminate,
     *     false otherwise.
     */
    public void setIndeterminate(boolean indeterminate) {
        
        try {
            lock.writeLock().lock();
        
            this.indeterminate = indeterminate;
            
        } finally {
            lock.writeLock().unlock();
        }
    }
    
    /**
     * Return the state of the monitored thread: alive, TERMINATED, interrupted
     * but still alive, fault. This method is thread-safe.
     * @return The state of the monitored thread. Possible values are:
     *     <code>Status.NEW</code>,
     *     <code>Status.RUNNING</code>,
     *     <code>Status.TERMINATED</code>,
     *     <code>Status.INTERRUPTED</code>,
     *     <code>Status.FAILED</code>.
     */
    public Status getState() {
        try {
            lock.readLock().lock();
            
            return state;
            
        } finally {
            lock.readLock().unlock();
        }
    }
    
    /**
     * Set the state of the monitored method:  alive, TERMINATED, interrupted
     * but still alive, fault. This method is thread-safe.
     * @param state The state of the monitored thread. Available values are:
     *     <code>Status.NEW</code>,
     *     <code>Status.RUNNING</code>,
     *     <code>Status.TERMINATED</code>,
     *     <code>Status.INTERRUPTED</code>,
     *     <code>Status.FAILED</code>.
     */
    public void setState(Status state) {
        
        try {
            lock.writeLock().lock();
        
            this.state = state;
            
        } finally {
            lock.writeLock().unlock();
        }
    }
    
    /**
     * Add an additional monitor in a tree like structure. This method is
     * thread-safe.
     * @param info The child monitor.
     */
    public void addChild(MonitorInfo info) {
        
        try {
            lock.writeLock().lock();
        
            children.add(info);
            
        } finally {
            lock.writeLock().unlock();
        }
    }
}
