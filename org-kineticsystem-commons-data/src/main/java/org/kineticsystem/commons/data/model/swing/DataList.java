/*
 * DataList.java
 *
 * Created on 25 April 2006, 8.53
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

package org.kineticsystem.commons.data.model.swing;

// Java classes.

import java.awt.EventQueue;
import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.concurrent.locks.ReadWriteLock;
import javax.swing.event.EventListenerList;

// Application classes.

import org.kineticsystem.commons.data.model.ActiveList;
import org.kineticsystem.commons.data.model.ActiveListEvent;
import org.kineticsystem.commons.data.model.ActiveListListener;
import org.kineticsystem.commons.data.model.DefaultActiveList;

/**
 * This object represents a list that can be safetely used with Swing
 * components. It uses a double buffering technique to provide data to Swing
 * standard and custom components. It contains two list: one read and written
 * by many threads at the same time and the second one synchronized with the
 * first one when required by the AWT event-dispatching thread.
 * @author Giovanni Remigi
 * @version $Revision: 43 $
 */
public class DataList<E> implements ActiveList<E> {
    
    /* /////////////////////////////////////////////////////////////////////////
     * Private variables.
     */
    
    /** True if the list can send events, false otherwise. */
    private boolean activated;
    
    /** The list of all registered listener. */
    private EventListenerList listenerList;
    
    /** Used to send events inside the AWT event-dispatching thread. */
    private EventRunner<E> eventRunner;
    
    /** The source list concurrently modifiable by any number of threads. */
    private ActiveList<E> sourceList;
    
    /**
     * This list is the one safetely read by the AWT event-dispatching thread.
     * User components cannot directly write in this list because it is
     * synhronized to the source list when required by the AWT .
     */
    private ActiveList<E> swingList;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Default constructor.
     */
    
    /**
     * Default constructor. This object wraps two list: one modifiable my one
     * or more threads and another one, carefully synchronized with the first
     * one, that fires events inside the AWT event-dispatching when modified.
     * The synchronization is executed and optimized by a notification inside
     * the AWT event-dispatching queue.
     */
    public DataList() {
        listenerList = new EventListenerList();
        activated = true;
        sourceList = new DefaultActiveList<E>();
        swingList = new DefaultActiveList<E>();
        eventRunner = new EventRunner<E>(this, sourceList, swingList);
        sourceList.addActiveListListener(eventRunner);
    }
    
    /**
     * Default constructor. This object wraps two list: one modifiable my one
     * or more threads and another one, carefully synchronized with the first
     * one, that fires events inside the AWT event-dispatching when modified.
     * The synchronization is executed and optimized by a notification inside
     * the AWT event-dispatching queue.
     */
    public DataList(ActiveList<E> list) {
        listenerList = new EventListenerList();
        activated = true;
        sourceList = list;
        swingList = new DefaultActiveList<E>();
        eventRunner = new EventRunner<E>(this, sourceList, swingList);
        sourceList.addActiveListListener(eventRunner);
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * ActiveList interface implementation.
     */
    
    /** {@inheritDoc} */
    public ReadWriteLock getReadWriteLock() {
        return sourceList.getReadWriteLock();
    }
    
    /** {@inheritDoc} */
    public void addActiveListListener(ActiveListListener listener) {
        listenerList.add(ActiveListListener.class, listener);
    }
    
    /** {@inheritDoc} */
    public void removeActiveListListener(ActiveListListener listener) {
        listenerList.remove(ActiveListListener.class, listener);
    }
    
    /** {@inheritDoc} */
    public void setActive(boolean activated) {
        this.activated = activated;
    }
    
    /** {@inheritDoc} */
    public boolean isActive() {
        return activated;
    }
    
    /** {@inheritDoc} */
    public void fireContentsChanged(ActiveListEvent event) {
        
        // Guaranteed to return a non-null array.

        Object[] listeners = listenerList.getListenerList();

        // Process the listeners last to first, notifying those that are
        // interested in this event.

        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == ActiveListListener.class) {
                ((ActiveListListener) listeners[i+1])
                    .contentsChanged(event);
            }          
        }
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * List interface implementation.
     */
    
    /** {@inheritDoc} */
    public boolean add(E element) {
        return sourceList.add(element);
    }
    
    /** {@inheritDoc} */
    public void add(int index, E element) {
        sourceList.add(index, element);
    }
    
    /** {@inheritDoc} */
    public boolean addAll(Collection<? extends E> c) {
        return sourceList.addAll(c);
    }

    /** {@inheritDoc} */
    public boolean addAll(int index, Collection<? extends E> c) {
        return sourceList.addAll(index, c);
    }
    
    /** {@inheritDoc} */
    public void clear() {
        sourceList.clear();
    }

    /** {@inheritDoc} */
    public boolean contains(Object element) {
        return workingList().contains(element);
    }
    
    /** {@inheritDoc} */
    public boolean containsAll(Collection<?> c) {
        return workingList().containsAll(c);
    }
    
    /** {@inheritDoc} */
    public boolean equals(Object element) {
        return workingList().equals(element);
    }
    
    /** {@inheritDoc} */
    public E get(int index) {
        return workingList().get(index);
    }
    
    /** {@inheritDoc} */
    public int hashCode() {
        return workingList().hashCode();
    }
    
    /** {@inheritDoc} */
    public int indexOf(Object element) {
        return workingList().indexOf(element);
    }
    
    /** {@inheritDoc} */
    public boolean isEmpty() {
        return workingList().isEmpty();
    }
    
    /** {@inheritDoc} */
    public Iterator<E> iterator() {
        return workingList().iterator();
    }
    
    /** {@inheritDoc} */
    public int lastIndexOf(Object element) {
        return swingList.lastIndexOf(element);
    }
    
    /** {@inheritDoc} */
    public ListIterator<E> listIterator() {
        return workingList().listIterator();
    }    
    
    /** {@inheritDoc} */
    public ListIterator<E> listIterator(int index) {
        return workingList().listIterator(index);
    }
    
    /** {@inheritDoc} */
    public E remove(int index) {
        return sourceList.remove(index);
    }

    /** {@inheritDoc} */
    public boolean remove(Object o) {
        return sourceList.remove(o);
    }
    
    /** {@inheritDoc} */
    public boolean removeAll(Collection<?> c) {
        return sourceList.removeAll(c);
    }

    /** {@inheritDoc} */
    public boolean retainAll(Collection<?> c) {
        return sourceList.retainAll(c);
    }

    /** {@inheritDoc} */
    public E set(int index, E element) {
        return sourceList.set(index, element);
    }
    
    /** {@inheritDoc} */
    public int size() {
        return workingList().size();
    }
    
    /** {@inheritDoc} */
    public java.util.List<E> subList(int fromIndex, int toIndex) {
        return workingList().subList(fromIndex, toIndex);
    }

    /** {@inheritDoc} */
    public Object[] toArray() {
        return workingList().toArray();
    }
    
    /** {@inheritDoc} */
    public <T> T[] toArray(T[] a) {
        return workingList().toArray(a);
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * New methods.
     */
    
    /**
     * Return the source list concurrently modified by any number of threads.
     * @return The source list concurrently modified by any number of threads.
     */
    ActiveList<E> getSourceList() {
        return sourceList;
    }

    /**
     * Return the list that can be safetely read by the AWT event-dispatching
     * thread. User components cannot directly write in this list because it is
     * synhronized to the source list when required by the AWT .
     */
    ActiveList<E> getSwingList() {
        return swingList;
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * Private methods.
     */
    
    /**
     * The <tt>LazyList</tt> wraps two different list, a source list and a swing
     * list, implementing a double buffering technique, and synchronizes them
     * upon request.
     * AWT event-dispatching thread can safetly read the swing list only. This
     * method returns the swing list if the calling thread is the AWT event
     * dispatching thread, otherwise returns the source list.
     * 
     * @return The swing list if the calling thread is the AWT event-dispatching
     *    thread, the source list otherwise.
     */
    private ActiveList<E> workingList() {
        if (EventQueue.isDispatchThread()) {
            return swingList;
        } else {
            return sourceList;
        }
    }
}
