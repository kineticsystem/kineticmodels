/*
 * MapDecorator.java
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
import java.util.Map;
import java.util.Set;

/**
 * <p><tt>Map</tt> decorator. See Decorator Pattern.</p>
 * <p>This is useful to extend a class at run-time without knowing the class
 * implementation.</p>
 * <p>Suppose you need to add new functionality to an <tt>HashMap</tt> object.
 * Suppose you need then to add the same functionality to a <tt>TreeMap</tt>, a
 * <tt>LinkedHashMap</tt> and so on. Doing so you add a lot of new classes one
 * per <tt>Map</tt> implementation.</p>
 * <p>A decorator simplify this situation. You just need to add new
 * functionality to the decorator and the wrap any <tt>Map</tt> implementation
 * inside the <tt>MapDecorator</tt> extended class. You must think about the
 * decorator as if it was a hat on the top of a <tt>Map</tt> implementation.</p>
 * <p>An example follows: a log functionality is added to a decorator to keep
 * track of inserted objects.</p>
 * <pre>
 * public class ExtendedMap extends MapDecorator {
 *          
 *     public Object put(Object key, Object value) {
 *         log.info("Adding a new value");
 *         super.put(key, value);
 *     }
 * }
 * 
 * Map map = new HashMap();
 * Map logMap = new ExtendedMap(map);
 * </pre>
 * @author Giovanni Remigi
 * @version $Revision: 42 $
 */
public abstract class MapDecorator<K,V> implements Map<K,V> {

    /* /////////////////////////////////////////////////////////////////////////
     * Private variables.
     */
    
    /** The decorated map. */
    private Map<K,V> adaptee;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constructors.
     */
    
    /**
     * Constructor.
     * @param adaptee The decorated map.
     */
    public MapDecorator(Map<K,V> adaptee) {
        if (adaptee == null) {
            throw new NullPointerException("Map cannot be null");
        }
        this.adaptee = adaptee;
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * Map interface implementation.
     */
    
    /** Removes all mappings from this map (optional operation). */
    public void clear() {
        adaptee.clear();
    }
    
    /** 
     * Returns true if this map contains a mapping for the specified key.
     * @param key The specified key.
     * @return True if the map contains the specified key, false otherwise.
     */
    public boolean containsKey(Object key) {
        return adaptee.containsKey(key);
    }
    
    /** 
     * Returns true if this map maps one or more keys to the specified value.
     * @param value The value to be searched.
     * @return True if the amp contains the given value, false otherwise.
     */
    public boolean containsValue(Object value) {
        return adaptee.containsValue(value);
    }
    
    /**
     * Returns a set view of the mappings contained in this map.
     * @return The set view of the mappings contained in this map.
     */
    public Set<Map.Entry<K,V>> entrySet() {
        return adaptee.entrySet();
    }
    
    /**
     * Compares the specified object with this map for equality.
     * @param o The object to be compared.
     * @return True if the map is equal to the given object.
     */
    public boolean equals(Object o) {
        return adaptee.equals(o);
    }
    
    /**
     * Returns the value to which this map maps the specified key.
     * @param key The lookup key.
     * @return The corresponding value of null if none.
     */
    public V get(Object key) {
        return adaptee.get(key);
    }
    
    /**
     * Returns the hash code value for this map.
     * @return The hash code value for this map.
     */
    public int hashCode() {
        return adaptee.hashCode();
    }
    
    /**
     * Returns true if this map contains no key-value mappings.
     * @return True if this map contains no key-value mappings.
     */
    public boolean isEmpty() {
        return adaptee.isEmpty();
    }
    
    /**
     * Returns a set view of the keys contained in this map.
     * @return The set view of the keys contained in this map.
     */
    public Set<K> keySet() {
        return adaptee.keySet();
    }
    
    /**
     * Associates the specified value with the specified key in this map
     * (optional operation).
     * @param key The lookup key.
     * @param value The object to be associated to the key.
     */
    public V put(K key, V value) {
        return adaptee.put(key, value);
    }
    
    /**
     * Copies all of the mappings from the specified map to this map (optional
     * operation).
     * @param m The map containing values to be added to this map.
     */
    public void putAll(Map<? extends K,? extends V> m) {
        adaptee.putAll(m);
    }
    
    /**
     * Removes the mapping for this key from this map if it is present (optional
     * operation).
     * @param key The key to be removed from the map.
     * @return The removed object if any.
     */
    public V remove(Object key) {
        return adaptee.remove(key);
    }
    
    /**
     * Returns the number of key-value mappings in this map.
     * @return The map size.
     */
    public int size() {
        return adaptee.size();
    }
    
    /**
     * Returns a collection view of the values contained in this map.
     * @return A collection containing all map values.
     */
    public Collection<V> values() {
        return adaptee.values();
    }
}