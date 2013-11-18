/*
 * DefaultActiveList.java
 *
 * Created on 25 February 2006, 16.46
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

package org.kineticsystem.commons.data.model;

// Java classes.

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.swing.event.EventListenerList;

/**
 * This is the default implementation of the <tt>ActiveList</tt> interface. By
 * default it is implemented by an <tt>ArrayList</tt>.
 * The implementation can be delegated to a <tt>List</tt> instance. In this
 * case the list behaves like a <i>Decorator</i> of the given list.
 * All methods implementing the <tt>List</tt> interface are thread-safe and they
 * all guarantee safe synchronization using the read/write lock available throw
 * the method <tt>getReadWriteLock</tt>.
 * @author Giovanni Remigi
 * @version $Revision: 155 $
 */
public class DefaultActiveList<E> implements ActiveList<E> {
    
    /* /////////////////////////////////////////////////////////////////////////
     * Private variables.
     */
    
    /** True if the list can send events, false otherwise. */
    private boolean activated;
    
    /** Lock to allow multithreaded access to the list data. */
    private ReadWriteLock lock;
    
    /** The list of all registered listener. */
    private EventListenerList listenerList;
    
    /**
     * The internal representation of the list. By default the list is
     * implemented by an <tt>ArrayList</tt>.
     */
    protected List<E> list;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constructors and initializing methods.
     */
    
    /** Default constructor. */
    public DefaultActiveList() {
        this.list = new ArrayList<E>(); // Default implementation.
        init();
    }
    
    /**
     * Decorate the given list to make it firing events when modified.
     * @param list The list to be decorated.
     */   
    public DefaultActiveList(List<E> list) {
        this.list = list;
        init();
    }
    
    /** Initialization methods containing common initialization tasks. */
    private void init() {
        activated = true;
        lock = new ReentrantReadWriteLock();
        listenerList = new EventListenerList();
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * ActiveList interface implementation.
     */
    
    /** {@inheritDoc} */
    public ReadWriteLock getReadWriteLock() {
        return lock;
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
    
    /** 
     * This method is thread-safe.
     * {@inheritDoc} 
     */
    public boolean add(E element) {
          
        try {
            lock.writeLock().lock();
            
            int size = list.size();
            boolean value = list.add(element);
            if (activated) {
                ActiveListEvent event = new ActiveListEvent(this);
                event.setType(ActiveListEvent.INTERVAL_ADDED);
                event.setX(size);
                event.setY(size);
                fireContentsChanged(event);
            }
            return value;
        } finally {
            lock.writeLock().unlock();
        }
    }
    
    /** 
     * This method is thread-safe.
     * {@inheritDoc} 
     */
    public void add(int index, E element) {
        
        try {
            lock.writeLock().lock();
            
            list.add(index, element);
            if (activated) {
                ActiveListEvent event = new ActiveListEvent(this);
                event.setType(ActiveListEvent.INTERVAL_ADDED);
                event.setX(index);
                event.setY(index);
                fireContentsChanged(event);
            }
        } finally {
            lock.writeLock().unlock();
        }
    }
    
    /** 
     * This method is thread-safe.
     * {@inheritDoc} 
     */
    public boolean addAll(Collection<? extends E> c) {
        
        try {
            lock.writeLock().lock();

            int index = list.size();
            boolean isChanged = list.addAll(c);
            if (isChanged && activated) {
                ActiveListEvent event = new ActiveListEvent(this);
                event.setType(ActiveListEvent.INTERVAL_ADDED);
                event.setX(index);
                event.setY(index + c.size());
                fireContentsChanged(event);
            }
            return isChanged;
        } finally {
            lock.writeLock().unlock();
        }
    }
    
    /** 
     * This method is thread-safe.
     * {@inheritDoc} 
     */
    public boolean addAll(int index, Collection<? extends E> c) {
        
        try {
            lock.writeLock().lock();
        
            boolean isChanged = list.addAll(index, c);
            if (isChanged && activated) {
                ActiveListEvent event = new ActiveListEvent(this);
                event.setType(ActiveListEvent.INTERVAL_ADDED);
                event.setX(index);
                event.setY(index + c.size());
                fireContentsChanged(event);
            }
            return isChanged;
        } finally {
            lock.writeLock().unlock();
        }
    }
    
    /** 
     * This method is thread-safe.
     * {@inheritDoc} 
     */
    public void clear() {

        try {
            lock.writeLock().lock();
        
            if (!list.isEmpty()) {
                int size = list.size();
                list.clear();
                if (activated) {
                    ActiveListEvent event = new ActiveListEvent(this);
                    event.setType(ActiveListEvent.INTERVAL_REMOVED);
                    event.setX(0);
                    event.setY(size - 1);
                    fireContentsChanged(event);
                }
            }
        } finally {
            lock.writeLock().unlock();
        }
    }
    
    /** 
     * This method is thread-safe.
     * {@inheritDoc} 
     */
    public boolean contains(Object element) {
        
        try {
            lock.readLock().lock();

            return list.contains(element);
        } finally {
            lock.readLock().unlock();
        }
    }
    
    /** 
     * This method is thread-safe.
     * {@inheritDoc} 
     */
    public boolean containsAll(Collection<?> c) {
        
        try {
            lock.readLock().lock();

            return list.containsAll(c);
        } finally {
            lock.readLock().unlock();
        }
    }
    
    /** 
     * This method is thread-safe.
     * {@inheritDoc} 
     */
    public boolean equals(Object element) {
        
        try {
            lock.readLock().lock();

            return list.equals(element);
        } finally {
            lock.readLock().unlock();
        }
    }
    
    /** 
     * This method is thread-safe.
     * {@inheritDoc} 
     */
    public E get(int index) {
        
        try {
            lock.readLock().lock();

            return list.get(index);
        } finally {
            lock.readLock().unlock();
        }
    }
    
    /** 
     * This method is thread-safe.
     * {@inheritDoc} 
     */
    public int hashCode() {
        
        try {
            lock.readLock().lock();

            return list.hashCode();
        } finally {
            lock.readLock().unlock();
        }
    }
    
    /** 
     * This method is thread-safe.
     * {@inheritDoc} 
     */
    public int indexOf(Object element) {
        
        try {
            lock.readLock().lock();

            return list.indexOf(element);
        } finally {
            lock.readLock().unlock();
        }
    }
    
    /** 
     * This method is thread-safe.
     * {@inheritDoc} 
     */
    public boolean isEmpty() {
        
        try {
            lock.readLock().lock();

            return list.isEmpty();
        } finally {
            lock.readLock().unlock();
        }
    }
    
    /** 
     * This method is thread-safe.
     * {@inheritDoc} 
     */
    public Iterator<E> iterator() {
        
        try {
            lock.readLock().lock();

            return new ActiveListIterator<E>(list.iterator());
        } finally {
            lock.readLock().unlock();
        }
    }
    
    /** 
     * This method is thread-safe.
     * {@inheritDoc} 
     */
    public int lastIndexOf(Object element) {
        
        try {
            lock.readLock().lock();

            return list.lastIndexOf(element);
        } finally {
            lock.readLock().unlock();
        }
    }
    
    /** 
     * This method is thread-safe.
     * {@inheritDoc} 
     */
    public ListIterator<E> listIterator() {
        
        try {
            lock.readLock().lock();

            return new ActiveListListIterator<E>(list.listIterator());
        } finally {
            lock.readLock().unlock();
        }
    }    
    
    /** 
     * This method is thread-safe.
     * {@inheritDoc} 
     */
    public ListIterator<E> listIterator(int index) {
        
        try {
            lock.readLock().lock();

            return new ActiveListListIterator<E>(list.listIterator(index));
        } finally {
            lock.readLock().unlock();
        }
    }
    
    /** 
     * This method is thread-safe.
     * {@inheritDoc} 
     */
    public E remove(int index) {
        
        try {
            lock.writeLock().lock();

            E element = list.remove(index);
            if (activated) {
                ActiveListEvent event = new ActiveListEvent(this);
                event.setType(ActiveListEvent.INTERVAL_REMOVED);
                event.setX(index);
                event.setY(index);
                fireContentsChanged(event);
            }
            return element;
        } finally {
            lock.writeLock().unlock();
        }
    }

    /** 
     * This method is thread-safe.
     * {@inheritDoc} 
     */
    public boolean remove(Object o) {
        
        try {
            lock.writeLock().lock();

            boolean value = false;
            int index = list.indexOf(o);
            if (index != -1) {
                value = list.remove(o);
                if (activated) {
                    ActiveListEvent event = new ActiveListEvent(this);
                    event.setType(ActiveListEvent.INTERVAL_REMOVED);
                    event.setX(index);
                    event.setY(index);
                    fireContentsChanged(event);
                }
            }
            return value;
        } finally {
            lock.writeLock().unlock();
        }
    }
    
    /** 
     * This method is thread-safe.
     * {@inheritDoc} 
     */
    public boolean removeAll(Collection<?> c) {
        
        try {
            lock.writeLock().lock();

            if (c == null) {
                String msg = "Collection to be removed cannot be null!";
                throw new NullPointerException(msg);
            }
    
            boolean value = false;
            for (Object o : c) {
                int index = list.indexOf(o);
                if (index != -1) {
                    value = value || list.remove(o);
                    if (activated) {
                        ActiveListEvent event = new ActiveListEvent(this);
                        event.setType(ActiveListEvent.INTERVAL_REMOVED);
                        event.setX(index);
                        event.setY(index);
                        fireContentsChanged(event);
                    }
                }
            }

            return value;
        } finally {
            lock.writeLock().unlock();
        }
    }

    /** {@inheritDoc} */
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("Method not implemented!");
    }

    /** 
     * This method is thread-safe.
     * {@inheritDoc} 
     */
    public E set(int index, E element) {
        
        try {
            lock.writeLock().lock();

            E previousElement = list.set(index, element);
            if (activated) {
                ActiveListEvent event = new ActiveListEvent(this);
                event.setType(ActiveListEvent.CONTENTS_CHANGED);
                event.setX(index);
                event.setY(index);
                fireContentsChanged(event);
            }
            return previousElement;
        } finally {
            lock.writeLock().unlock();
        }
    }
    
    /** 
     * This method is thread-safe.
     * {@inheritDoc} 
     */
    public int size() {
        
        try {
            lock.readLock().lock();

            return list.size();
        } finally {
            lock.readLock().unlock();
        }
    }
    
    /** 
     * This method is thread-safe.
     * {@inheritDoc} 
     */
    public List<E> subList(int fromIndex, int toIndex) {
        
        try {  
            lock.readLock().lock();

            return list.subList(fromIndex, toIndex);
        } finally {
            lock.readLock().unlock();
        }
    }

    /** 
     * This method is thread-safe.
     * {@inheritDoc} 
     */
    public Object[] toArray() {
        try {  
            lock.readLock().lock();

            return list.toArray();
        } finally {
            lock.readLock().unlock();
        }
    }
    
    /** 
     * This method is thread-safe.
     * {@inheritDoc} 
     */
    public <T> T[] toArray(T[] a) {

        try {  
            lock.readLock().lock();

            return list.toArray(a);
        } finally {
            lock.readLock().unlock();
        }
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * Embedded classes.
     */
    
    /**
     * This is an <tt>ActiveList</tt> <tt>Iterator</tt> sending events when
     * items are removed from or added to the embedded list being iterated.
     */
    @SuppressWarnings("hiding")
    private class ActiveListIterator<E> implements Iterator<E> {

        /**
         * The iterator of the list implementation wrapped by the
         * <tt>ActiveList</tt>.
         */
        private Iterator<E> iterator;

        /** Index of element to be returned by subsequent call to next. */
        protected int index;
        
        /**
         * Constructor.
         * @param iterator The iterator of the list wrapped by the current
         *     <tt>ActiveList</tt>.
         */
        public ActiveListIterator(Iterator<E> iterator) {

            if (iterator == null) {
                String msg = "Decorated iterator cannot be null!";
                throw new IllegalArgumentException(msg);
            }
            this.iterator = iterator;
            this.index = 0;
        }

        /* /////////////////////////////////////////////////////////////////////
         * Iterator interface implementation.
         */

        /** {@inheritDoc} */
        public boolean hasNext() {
            return iterator.hasNext();
        }

        /** {@inheritDoc} */
        public E next() {
            E element = iterator.next();
            index++;
            return element;
        }

        /**
         * Remove the current list element and send an event to all registered
         * listeners.
         */
        public void remove() {

            iterator.remove();
            
            ActiveListEvent event = new ActiveListEvent(list);
            event.setType(ActiveListEvent.INTERVAL_REMOVED);
            event.setX(index);
            event.setY(index);
            
            index--;
            
            fireContentsChanged(event);
        }
    }
    
    /**
     * This is an <tt>ActiveList</tt> <tt>ListIterator</tt> sending events when
     * items are removed from or added to the embedded list being iterated.
     */
    @SuppressWarnings("hiding")
    private class ActiveListListIterator<E> extends ActiveListIterator<E>
            implements ListIterator<E> {
        
        /**
         * The iterator of the list implementation wrapped by the
         * <tt>ActiveList</tt>.
         */
        private ListIterator<E> iterator;
        
        /**
         * Constructor.
         * @param iterator The iterator of the list wrapped by the current
         *     <tt>ActiveList</tt>.
         */
        public ActiveListListIterator(ListIterator<E> iterator) {
            super(iterator);
            this.iterator = iterator;
            this.index = iterator.nextIndex();
        }
        
        /* /////////////////////////////////////////////////////////////////////
         * ListIterator interface implementation.
         */

        /** {@inheritDoc} */
        public void add(E element) {
            
            iterator.add(element);
            
            ActiveListEvent event = new ActiveListEvent(list);
            event.setType(ActiveListEvent.CONTENTS_CHANGED);
            event.setX(index);
            event.setY(index);
            
            index++;
            
            fireContentsChanged(event);
        }

        /** {@inheritDoc} */
        public boolean hasPrevious() {
            return iterator.hasPrevious();
        }

        /** {@inheritDoc} */
        public int nextIndex() {
            return iterator.nextIndex();
        }

        /** {@inheritDoc} */
        public E previous() {
            E element = iterator.previous();
            index--;
            return element;
        }

        /** {@inheritDoc} */
        public int previousIndex() {
            return iterator.previousIndex();
        }

        /** {@inheritDoc} */
        public void set(E element) {
            
            iterator.set(element);
            
            ActiveListEvent event = new ActiveListEvent(list);
            event.setType(ActiveListEvent.CONTENTS_CHANGED);
            event.setX(index);
            event.setY(index);
            
            fireContentsChanged(event);
        }
    }
}