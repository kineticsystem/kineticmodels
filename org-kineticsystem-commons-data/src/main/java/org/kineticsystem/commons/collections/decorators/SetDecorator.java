/*
 * SetDecorator.java
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

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * <p><tt>Set</tt> decorator. See Decorator Pattern.</p>
 * <p>This is useful to extend a class at run-time without knowing the class
 * implementation.</p>
 * <p>Suppose you need to add new functionality to an <tt>HashSet</tt> object.
 * Suppose you need then to add the same functionality to a <tt>TreeSet</tt>, a
 * <tt>LinkedHashSet</tt> and so on. Doing so you add a lot of new classes one
 * per <tt>Set</tt> implementation.</p>
 * <p>A decorator simplify this situation. You just need to add new
 * functionality to the decorator and the wrap any <tt>Set</tt> implementation
 * inside the <tt>SetDecorator</tt> extended class. You must think about the
 * decorator as if it was a hat on the top of a <tt>Set</tt> implementation.</p>
 * <p>An example follows: a log functionality is added to a decorator to keep
 * track of inserted objects.</p>
 * <pre>
 * public class ExtendedSet extends SetDecorator {
 * 
 *     public boolean add(Object obj) {
 *         log.info("Adding a new value");
 *         return super.add(obj);
 *     }
 * }
 * 
 * Set set = new HashSet();
 * Set logSet = new ExtendedSet(set);
 * </pre>
 * @author Giovanni Remigi
 * @version $Revision: 42 $
 */
public abstract class SetDecorator<E> implements Set<E> {

    /* /////////////////////////////////////////////////////////////////////////
     * Private variables.
     */
    
    /** The decorated map. */
    private Set<E> adaptee;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constructors.
     */
    
    /**
     * Constructor.
     * @param adaptee The decorated set.
     */
    public SetDecorator(Set<E> adaptee) {

        if (adaptee == null) {
            throw new NullPointerException("Set cannot be null");
        }
        this.adaptee = adaptee;
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * Set interface implementation.
     */
    
    /**
     * Adds the specified element to this set if it is not already present
     * (optional operation).
     * @param e The element to be added.
     * @return True if this set contains the specified element.
     */
    public boolean add(E e) {
        return adaptee.add(e);
    }

    /**
     * Adds all of the elements in the specified collection to this set if
     * they're not already present (optional operation).
     * @param c The collection to be added.
     * @return true if this set changed as a result of the call.
     */
    public boolean addAll(Collection<? extends E> c) {
        return adaptee.addAll(c);
    }

    /** Removes all of the elements from this set (optional operation). */
    public void clear() {
        adaptee.clear();
    }

    /**
     * Returns true if this set contains the specified element.
     * @param o Element whose presence in this set is to be tested.
     * @return True if this set contains the specified element.
     */
    public boolean contains(Object o) {
        return adaptee.contains(o);
    }

    /**
     * Returns true if this set contains all of the elements of the specified
     * collection.
     * @param c Collection to be checked for containment in this set.
     * @return True if this set contains all of the elements of the specified
     *     collection.
     */
    public boolean containsAll(Collection<?> c) {
        return adaptee.containsAll(c);
    }

    /**
     * Compares the specified object with this set for equality.
     * @param o Object to be compared for equality with this set.
     * @return True if the specified Object is equal to this set.
     */
    public boolean equals(Object o) {
        return adaptee.equals(o);
    }

    /**
     * Returns the hash code value for this set.
     * @return The hash code value for this set.
     */
    public int hashCode() {
        return adaptee.hashCode();
    }

    /**
     * Returns true if this set contains no elements.
     * @return True if this set contains no elements.
     */
    public boolean isEmpty() {
        return adaptee.isEmpty();
    }

    /**
     * Returns an iterator over the elements in this set.
     * @return An iterator over the elements in this set.
     */
    public Iterator<E> iterator() {
        return adaptee.iterator();
    }

    /**
     * Removes the specified element from this set if it is present (optional
     * operation).
     * @param o Object to be removed from this set, if present.
     * @return True if the set contained the specified element.
     */
    public boolean remove(Object o) {
        return adaptee.remove(o);
    }

    /**
     * Removes from this set all of its elements that are contained in the
     * specified collection (optional operation).
     * @param c Collection that defines which elements will be removed from this
     *     set.
     * @return True if this set changed as a result of the call.
     */
    public boolean removeAll(Collection<?> c) {
        return adaptee.removeAll(c);
    }

    /**
     * Retains only the elements in this set that are contained in the specified
     * collection (optional operation).
     * @param c Collection that defines which elements this set will retain.
     * @return True if this collection changed as a result of the call.
     */
    public boolean retainAll(Collection<?> c) {
        return adaptee.retainAll(c);
    }

    /**
     * Returns the number of elements in this set (its cardinality).
     * @return The number of elements in this set (its cardinality).
     */
    public int size() {
        return adaptee.size();
    }

    /**
     * Returns an array containing all of the elements in this set.
     * @return an array containing all of the elements in this set.
     */
    public Object[] toArray() {
        return adaptee.toArray();
    }

    /**
     * Returns an array containing all of the elements in this set; the runtime
     * type of the returned array is that of the specified array.
     * @param a the array into which the elements of this set are to be stored,
     *     if it is big enough; otherwise, a new array of the same runtime type
     *     is allocated for this purpose.
     * @return An array containing the elements of this set.
     */
    public <T> T[] toArray(T[] a) {
        return adaptee.toArray(a);
    }
}