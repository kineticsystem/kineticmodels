/*
 * GroupMapping.java
 *
 * Created on 13 aprile 2006, 18.36
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

package org.kineticsystem.commons.data.model.mapping;

// Java classes.

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

// Application classes.

import org.kineticsystem.commons.data.model.ActiveList;
import org.kineticsystem.commons.data.model.ActiveListEvent;
import org.kineticsystem.commons.data.model.ActiveListListener;
import org.kineticsystem.commons.data.model.ActiveListUtility;
import org.kineticsystem.commons.data.model.DefaultActiveList;

/**
 * This is a mapping used to virtually filter and sort a source list. The result
 * is returned into a target list.
 * @author Giovanni Remigi
 * @version $Revision:  $
 */
public class GroupMapping<S> implements Mapping<S,Group<S>>,
        ActiveListListener {

    /** The source list to be sorted. */
    private ActiveList<S> sourceList;
    
    /** The target list containing the sorted items. */
    private ActiveList<Group<S>> targetList;
    
    /**
     * The mapping implementing the sorting map between the source and the
     * target list.
     */
    private List<GroupMappingKey<S>> mapping;
    
    /** The filter used to filter out source list elements. */
    private Filter<S> itemFilter;
    
    /** */
    private GroupAggregator<S> itemAggregator;
    
    /** Service comparator based on the item comparator. */
    private Comparator<GroupMappingKey<S>> keyComparator;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constructor and initializing methods.
     */
    
    /**
     * Create a mapping between a source list and a target filtered and ordered
     * list. The target list is filtered and ordered using the provided filter
     * and comparator if not null.
     * If the filter is null the target list will contain the same items of the
     * source list.
     * If the comparator is null the target list order will be the same of the
     * source list.
     */
    public GroupMapping(ActiveList<S> sourceList) {
        this.itemFilter = null;
        this.itemAggregator = null;
        this.keyComparator = null;
        this.sourceList = sourceList;
        this.targetList = new DefaultActiveList<Group<S>>();
        this.mapping = new ArrayList<GroupMappingKey<S>>();
        this.sourceList.addActiveListListener(this);
        this.targetList.addActiveListListener(this);
    }
    
    /** Sort the target list with the new comparator. */
    public void rebuild() {
        
        // Get the target list length.
        
        int targetListOldSize = targetList.size(); 
        
        // Recreate the mapping.

        mapping.clear();

        if (itemFilter == null) { // Without filter.
            for (S sourceItem : sourceList) {
                GroupMappingKey<S> key = new GroupMappingKey<S>();
                key.getSource().add(sourceItem);
                mapping.add(key);
            }
        } else {
            for (S sourceItem : sourceList) { // With filter.
                if (itemFilter.evaluate(sourceItem)) {
                    GroupMappingKey<S> key = new GroupMappingKey<S>();
                    key.getSource().add(sourceItem);
                    mapping.add(key);
                }
            }
        }
        
        // Sort the mapping.

        if (keyComparator != null) {
            Collections.sort(mapping, keyComparator);
        }
        
        // Group the mapping and recreate the target list.
        
        targetList.setActive(false); // Disable events.
        targetList.clear();
        
        if (itemAggregator == null) { // Create a group per item.
            for (GroupMappingKey<S> key : mapping) {
                Group<S> group = new Group<S>(this);
                targetList.add(group);
                group.setItems(key.getSource());
                key.setTarget(group);
            }
        } else { // Create a group for equivalent items.
            Group<S> group = null;
            S sourceOldItem = null;
            Iterator<GroupMappingKey<S>> iter = mapping.iterator();
            while (iter.hasNext()) {
                GroupMappingKey<S> key = iter.next();
                S sourceItem = key.getSource().get(0);
                if ((sourceOldItem == null)
                        || (itemAggregator.compare(sourceItem, sourceOldItem) != 0)) {
                    group = new Group<S>(this);
                    group.setItems(key.getSource());
                    targetList.add(group);
                    key.setTarget(group);
                } else {
                    group.getItems().add(sourceItem);
                    iter.remove();
                }
                sourceOldItem = sourceItem;
            }
        }

        // Get the new target list length.
        
        int targetListNewSize = targetList.size();
        
        // Fire events to update the user interface.

        targetList.setActive(true); // Enable events.
        List<ActiveListEvent> events = ActiveListUtility.difference(
                targetListOldSize, targetListNewSize, targetList);
        for (ActiveListEvent event : events) {
            targetList.fireContentsChanged(event);
        }  
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * Getter and setter methods.
     */
    
    /**
     * Return the <tt>Comparator</tt> used to virtually sort the source list.
     * @return The <tt>Comparator</tt> used to virtually sort the source list.
     * @see java.util.Comparator
     */
    public GroupAggregator<S> getAggregator() {
        return itemAggregator;
    }
    
    /**
     * Set the <tt>Comparator</tt> used to virtually sort the source list.
     * @param comparator The <tt>Comparator</tt> used to virtually sort the
     *     source list.
     * @see java.util.Comparator
     */
    public void setAggregator(GroupAggregator<S> aggregator) {
        this.itemAggregator = aggregator;
        this.keyComparator = new Comparator<GroupMappingKey<S>>() {
            public int compare(GroupMappingKey<S> key1, GroupMappingKey<S> key2) {
                return itemAggregator.compare(key1.getSource().get(0),
                    key2.getSource().get(0));
            }
        };
    }
    
    /**
     * Return the <tt>Filter</tt> used to virtually filter the source list.
     * @return The <tt>Filter</tt> used to virtually filter the source list.
     */
    public Filter<S> getFilter() {
        return itemFilter;
    }
    
    /**
     * Set the <tt>Filter</tt> used to virtually filter the source list.
     * @param filter The <tt>Filter</tt> used to virtually filter the source
     *     list.
     */
    public void setFilter(Filter<S> filter) {
        this.itemFilter = filter;
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * Mapping interface implementation.
     */
    
    /**
     * Return the original source list.
     * @return The source list.
     */
    public ActiveList<S> getSource() {
        return sourceList;
    }
    
    /**
     * Return the filtered and ordered target list.
     * @return The filtered and ordered target list.
     */
    public ActiveList<Group<S>> getTarget() {
        return targetList;
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * ActiveListListener interface implementation.
     */
    
    /** {@inheritDoc} */
    public void contentsChanged(ActiveListEvent event) {
        
        // Event dispatcher.
        
        if (event.getSource() == sourceList) {
            sourceListContentsChanged(event);
        } else {
            targetListContentsChanged(event);
        }
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * Private methods.
     */
    
    /**
     * Update the mapping and the target list when a change occurs in the source
     * list.
     * @param event The source list change event.
     */
    private void sourceListContentsChanged(ActiveListEvent event) {
        if (event.getType() == ActiveListEvent.INTERVAL_REMOVED) {
            if (event.getSequenceNumber() == event.getLastSequenceNumber()) {
                rebuild();
            }
        } else if (event.getType() == ActiveListEvent.CONTENTS_CHANGED) {
            if (event.getSequenceNumber() == event.getLastSequenceNumber()) {
                rebuild();
            }
        } else if (event.getType() == ActiveListEvent.INTERVAL_ADDED) {
            
            for (int index = event.getX(); index <= event.getY() ; index++) {
                S item = sourceList.get(index);
                if ((itemFilter == null) || itemFilter.evaluate(item)) {
                    
                    ActiveListEvent targetListEvent = new ActiveListEvent(this);
                    
                    GroupMappingKey<S> key = new GroupMappingKey<S>();
                    key.getSource().add(item);
                    
                    int position = Collections.binarySearch(mapping, key,
                        keyComparator);
                    if (position < 0) { // Key not found.
                        position = (position >= 0) ? position : ~position;
                        mapping.add(position, key);
                        Group<S> group = new Group<S>(this);
                        group.setItems(key.getSource());
                        key.setTarget(group);
                        targetList.setActive(false);
                        targetList.add(position, group);
                        targetList.setActive(true);
                        targetListEvent.setType(ActiveListEvent.INTERVAL_ADDED);
                    } else { // Key found.
                        position = (position >= 0) ? position : ~position;
                        mapping.get(position).getSource().add(item);
                        targetListEvent.setType(ActiveListEvent.CONTENTS_CHANGED);
                    }
                    
                    targetListEvent.setX(position);
                    targetListEvent.setY(position);
                    targetListEvent.setLastSequenceNumber(0);
                    targetListEvent.setSequenceNumber(0);
                    targetList.fireContentsChanged(targetListEvent);
                }
            }
        } 
    }
    
    /**
     * Update the mapping and the source list when a change occurs in the target
     * list.
     * @param event The target list change event.
     */
    private void targetListContentsChanged(ActiveListEvent event) {
        
    }
}