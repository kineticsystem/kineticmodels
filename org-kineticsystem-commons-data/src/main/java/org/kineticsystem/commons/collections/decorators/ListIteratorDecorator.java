/*
 * AbstractListIteratorDecorator.java
 *
 * Created on 24 october 2005, 21.55
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

package org.kineticsystem.commons.collections.decorators;

// Java classes.

import java.util.Iterator;
import java.util.ListIterator;

/**
 * This is an <tt>ListIterator</tt> decorator.
 * @see "Decorator Pattern"
 * @author Giovanni Remigi
 * @version $Revision: 34 $
 */
public abstract class ListIteratorDecorator<E>
        implements ListIterator<E> {
    
    /* /////////////////////////////////////////////////////////////////////////
     * Private variables.
     */
    
    /** The decorated list iterator. */
    private ListIterator<E> iterator;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constructors.
     */
    
    /** 
     * Constructor.
     * @param iterator The decorated list iterator.
     */
    public ListIteratorDecorator(ListIterator<E> iterator) {
        if (iterator == null) {
            String msg = "Decorated iterator cannot be null!";
            throw new IllegalArgumentException(msg);
        }
        this.iterator = iterator;
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * Getter and setter methods.
     */
    
    /**
     * Return the decorated iterator.
     * @return The decorated iterator.
     */
    protected Iterator<E> getIterator() {
        return iterator;
    }
    
    /**
     * Set the iterator to be decorated.
     * @param The iterator to be decorated.
     * @throws IllegalArgumentException If the iterator is null.
     */
    protected void setIterator(ListIterator<E> iterator) {
        if (iterator == null) {
            String msg = "Decorated iterator cannot be null!";
            throw new IllegalArgumentException(msg);
        }
        this.iterator = iterator;
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * ListIterator interface implementation.
     */
    
    /** {@inheritDoc} */
    public void add(E element) {
        iterator.add(element);
    }
    
    /** {@inheritDoc} */
    public boolean hasNext() {
        return iterator.hasNext();
    }
    
    /** {@inheritDoc} */
    public boolean hasPrevious() {
        return iterator.hasPrevious();
    }
    
    /** {@inheritDoc} */
    public E next() {
        return iterator.next();
    }
    
    /** {@inheritDoc} */
    public int nextIndex() {
        return iterator.nextIndex();
    }
    
    /** {@inheritDoc} */
    public E previous() {
        return iterator.previous();
    }
    
    /** {@inheritDoc} */
    public int previousIndex() {
        return iterator.previousIndex();
    }
    
    /** {@inheritDoc} */
    public void remove() {
        iterator.remove();     
    }

    /** {@inheritDoc} */
    public void set(E element) {
        iterator.set(element);
    }
}
