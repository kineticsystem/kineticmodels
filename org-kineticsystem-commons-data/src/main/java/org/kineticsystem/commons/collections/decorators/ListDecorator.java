/*
 * AbstractListDecorator.java
 *
 * Created on 28 october 2005, 19.45
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

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * This is an <tt>List</tt> decorator.
 * @see "Decorator Pattern"
 * @author Giovanni Remigi
 * @version $Revision: 34 $
 */
public abstract class ListDecorator<E> implements List<E> {
    
    /* /////////////////////////////////////////////////////////////////////////
     * Private variables.
     */    
    
    /** The decorated list. */
    private List<E> list;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constructors.
     */
    
    /** 
     * Constructor.
     * @param list The decorated list.
     * @throws IllegalArgumentException If the list is null.
     */
    public ListDecorator(List<E> list) {
        if (list == null) {
            String msg = "Decorated list cannot be null!";
            throw new IllegalArgumentException(msg);
        }
        this.list = list;
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * Getter and setter methods.
     */
    
    /** 
     * Get the decorated list.
     * @return The decorated list.
     */
    protected List<E> getList() {
        return list;
    }
    
    /** 
     * Set the list to be decorated.
     * @param list The list to be decorated..
     * @throws IllegalArgumentException If the list is null.
     */
    protected void setList(List<E> list) {
        if (list == null) {
            String msg = "Decorated list cannot be null!";
            throw new IllegalArgumentException(msg);
        }
        this.list = list;
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * List interface implementation.
     */

    /** {@inheritDoc} */
    public boolean add(E element) {
        return list.add(element);
    }

    /** {@inheritDoc} */
    public void add(int index, E element) {
        list.add(index, element);
    }
    
    /** {@inheritDoc} */
    public boolean addAll(Collection<? extends E> c) {
        return list.addAll(c);
    }   
    
    /** {@inheritDoc} */
    public boolean addAll(int index, Collection<? extends E> c) {
        return list.addAll(index, c);
    }  
    
    /** {@inheritDoc} */
    public void clear() {
        list.clear();
    }
    
    /** {@inheritDoc} */
    public boolean contains(Object element) {
        return list.contains(element);
    }
    
    /** {@inheritDoc} */
    public boolean containsAll(Collection<?> c) {
        return list.containsAll(c);
    }
    
    /** {@inheritDoc} */
    public boolean equals(Object element) {
        return list.equals(element);
    }
    
    /** {@inheritDoc} */
    public E get(int index) {
        return list.get(index);
    }
    
    /** {@inheritDoc} */
    public int hashCode() {
        return list.hashCode();
    }
    
    /** {@inheritDoc} */
    public int indexOf(Object element) {
        return list.indexOf(element);
    }
    
    /** {@inheritDoc} */
    public boolean isEmpty() {
        return list.isEmpty();
    }
    
    /** {@inheritDoc} */
    public Iterator<E> iterator() {
        return list.iterator();
    }
    
    /** {@inheritDoc} */
    public int lastIndexOf(Object element) {
        return list.lastIndexOf(element);
    }
    
    /** {@inheritDoc} */
    public ListIterator<E> listIterator() {
        return list.listIterator();
    }    
    
    /** {@inheritDoc} */
    public ListIterator<E> listIterator(int index) {
        return list.listIterator(index);
    }
    
    /** {@inheritDoc} */
    public boolean remove(Object element) {
        return list.remove(element);
    } 
    
    /** {@inheritDoc} */
    public E remove(int index) {
        return list.remove(index);
    }
    
    /** {@inheritDoc} */
    public boolean removeAll(Collection<?> c) {
        return list.removeAll(c);
    }
    
    /** {@inheritDoc} */
    public boolean retainAll(Collection<?> c) {
        return list.retainAll(c);
    }

    /** {@inheritDoc} */
    public E set(int index, E element) {
        return list.set(index, element);
    }

    /** {@inheritDoc} */
    public int size() {
        return list.size();
    }
    
    /** {@inheritDoc} */
    public List<E> subList(int fromIndex, int toIndex) {
        return list.subList(fromIndex, toIndex);
    }

    /** {@inheritDoc} */
    public Object[] toArray() {
        return list.toArray();
    }
    
    /** {@inheritDoc} */
    public <T> T[] toArray(T[] a) {
        return list.toArray(a);
    }
}
